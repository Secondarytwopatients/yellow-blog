package com.yellow.blog.controller;

import com.yellow.blog.common.lang.Result;
import com.yellow.blog.entity.MUser;
import com.yellow.blog.service.MUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (MUser)表控制层
 *
 * @author makejava
 * @since 2020-09-09 17:34:28
 */
@RestController
@RequestMapping("/mUser")
public class MUserController {
    /**
     * 服务对象
     */
    @Resource
    private MUserService mUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    @RequiresAuthentication
    public Result selectOne(Long id) {
        return Result.succ(mUserService.queryById(1L));
    }


    /**
     *
     *
     *  @param mUser
     *  @return 单条数据
     */
    @PostMapping("/save")
    public Result save(@Validated @RequestBody MUser mUser) {



        return Result.succ(mUser);
    }
}
