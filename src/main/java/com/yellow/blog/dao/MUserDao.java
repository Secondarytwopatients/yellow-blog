package com.yellow.blog.dao;

import com.yellow.blog.entity.MUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * (MUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-09 17:34:28
 */
public interface MUserDao extends Mapper<MUser> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MUser queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mUser 实例对象
     * @return 对象列表
     */
    List<MUser> queryAll(MUser mUser);

    /**
     * 新增数据
     *
     * @param mUser 实例对象
     * @return 影响行数
     */
    int insert(MUser mUser);

    /**
     * 修改数据
     *
     * @param mUser 实例对象
     * @return 影响行数
     */
    int update(MUser mUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}
