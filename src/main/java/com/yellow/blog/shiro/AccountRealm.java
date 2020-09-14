package com.yellow.blog.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.yellow.blog.entity.MUser;
import com.yellow.blog.service.MUserService;
import com.yellow.blog.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Huang
 */
@Component
public class AccountRealm extends AuthorizingRealm {


    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    MUserService mUserService;

    /**
     * 如果token是jwtToken类型
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 拿到用户之后获取权限信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 进行账号验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken)authenticationToken;

        String userId = jwtUtils.getClaimsByToken((String) jwtToken.getPrincipal()).getSubject();

        MUser mUser =  mUserService.queryById(Long.valueOf(userId));
        if(mUser==null){
            throw new UnknownAccountException("账户不存在");
        }
        if(mUser.getStatus()==-1){
            throw new LockedAccountException("账户已经锁定");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(mUser,profile);

        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
