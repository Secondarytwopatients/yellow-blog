package com.yellow.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.yellow.blog.common.dto.LoginDto;
import com.yellow.blog.common.lang.Result;
import com.yellow.blog.entity.MUser;
import com.yellow.blog.service.MUserService;
import com.yellow.blog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * @author Huang
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    MUserService mUserService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        MUser mUser = new MUser();
        mUser.setUsername(loginDto.getUsername());
        MUser loginUser = mUserService.getOne(mUser);
        Assert.notNull(loginUser,"用户不存在");

        if (!loginUser.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码错误");
        }

        String jwt = jwtUtils.generateToken(loginUser.getId());

        //将生成的jwt放入http head头信息里
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", loginUser.getId())
                .put("username", loginUser.getUsername())
                .put("avatar", loginUser.getAvatar())
                .put("email", loginUser.getEmail()).map()
        );
    }
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
