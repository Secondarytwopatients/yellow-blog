package com.yellow.blog.dao;

import com.yellow.blog.entity.MBlog;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (MBlog)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-09 17:33:38
 */
public interface MBlogDao extends Mapper<MBlog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MBlog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MBlog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mBlog 实例对象
     * @return 对象列表
     */
    List<MBlog> queryAll(MBlog mBlog);

    /**
     * 新增数据
     *
     * @param mBlog 实例对象
     * @return 影响行数
     */
    int insert(MBlog mBlog);

    /**
     * 修改数据
     *
     * @param mBlog 实例对象
     * @return 影响行数
     */
    int update(MBlog mBlog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
