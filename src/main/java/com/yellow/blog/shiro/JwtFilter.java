package com.yellow.blog.shiro;


import cn.hutool.json.JSONUtil;
import com.yellow.blog.common.lang.Result;
import com.yellow.blog.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Huang
 */
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        //如果没有jwt 跳过登录
        if(StringUtils.isEmpty(jwt)){
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        //如果没有jwt 跳过shiro登录处理
        if(StringUtils.isEmpty(jwt)){
            return true;
        }else{
            //校验jwt
            Claims claims = jwtUtils.getClaimsByToken(jwt);
            //判断是否为空 是否过期
            if(claims==null||jwtUtils.isTokenExpired(claims.getExpiration())){
                throw new ExpiredCredentialsException("token已经失效,请重新登录");
            }

            //执行登录
            return executeLogin(servletRequest,servletResponse);
        }

    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //覆写shiro executeLogin登录失败异常执行方法
        //获取异常信息
        Throwable throwable = e.getCause() == null ? e : e.getCause();

        Result failResult = Result.fail(throwable.getMessage());
        //异常信息使用hutool转换成json
        String json = JSONUtil.toJsonStr(failResult);
        //使用httpServletResponse返回给前端信息
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            httpServletResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;
    }


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control -Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access -Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader( "Access-Control-Allow-Headers" ,httpServletRequest.getHeader("Access-Control-Request-Headers"));
        //跨城时会首先发送一-个OPTIONS消求，这里我们给OPTIONS清求直接返回正常状态
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
//            return false;
//        }
        return super.preHandle(request, response);
    }
}
