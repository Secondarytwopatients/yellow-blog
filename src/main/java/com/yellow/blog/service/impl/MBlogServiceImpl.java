package com.yellow.blog.service.impl;

import com.yellow.blog.entity.MBlog;
import com.yellow.blog.dao.MBlogDao;
import com.yellow.blog.service.MBlogService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MBlog)表服务实现类
 *
 * @author makejava
 * @since 2020-09-09 17:33:40
 */
@Service("mBlogService")
public class MBlogServiceImpl implements MBlogService {
    @Resource
    private MBlogDao mBlogDao;
    /**
     * 查询createBlog
     *
     * @return 实例对象
     */
    @Override
    public List<MBlog> queryBlogByStatus(Integer status) {
        Example example = new Example(MBlog.class);
        example.setOrderByClause("created desc");
        example.createCriteria().andEqualTo("status",status);
        return this.mBlogDao.selectByExample(example);
    }
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MBlog queryById(Long id) {
        return this.mBlogDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MBlog> queryAllByLimit(int offset, int limit) {
        return this.mBlogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mBlog 实例对象
     * @return 实例对象
     */
    @Override
    public MBlog insert(MBlog mBlog) {
        this.mBlogDao.insert(mBlog);
        return mBlog;
    }

    /**
     * 修改数据
     *
     * @param mBlog 实例对象
     * @return 实例对象
     */
    @Override
    public MBlog update(MBlog mBlog) {
        this.mBlogDao.update(mBlog);
        return this.queryById(mBlog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.mBlogDao.deleteById(id) > 0;
    }
}
