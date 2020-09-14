package com.yellow.blog.controller;

import cn.hutool.core.lang.Assert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yellow.blog.common.lang.Result;
import com.yellow.blog.entity.MBlog;
import com.yellow.blog.service.MBlogService;
import com.yellow.blog.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Huang
 */
@RestController
public class BlogController {

    @Autowired
    MBlogService mBlogService;

    /**
     * 博客详情页
     */
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        PageHelper.startPage(currentPage,5);

        List<MBlog> mBlogList = mBlogService.queryBlogByStatus(0);

        PageInfo<MBlog> mBlogPageInfo = new PageInfo<>(mBlogList);
        return Result.succ(mBlogPageInfo);
    }



    /**
     * 博客内容
     */
    @GetMapping("/blog/{id}/detail")
    public Result list(@PathVariable(name="id") Long id){
        MBlog mBlog = mBlogService.queryById(id);
        Assert.notNull(mBlog,"该博客已经被删除");

        return Result.succ(mBlog);
    }

    /**
     * 博客编辑
     */
    @RequiresAuthentication
    @PostMapping("/blogs/edit")
    public Result list(@Validated @RequestBody MBlog mblog){


        MBlog temp =null;
        if(mblog.getId()!=null){
            temp = mBlogService.queryById(mblog.getId());
            //自能编辑自己文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()),"你没有权限编辑");
            BeanUtils.copyProperties(mblog,temp,"id","userId","created","status");
            mBlogService.update(temp);
        }else{
            //添加
            temp = new MBlog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(new Date());
            temp.setStatus(0);
            BeanUtils.copyProperties(mblog,temp,"id","userId","created","status");
            mBlogService.insert(temp);
        }


        return Result.succ(null);
    }
}
