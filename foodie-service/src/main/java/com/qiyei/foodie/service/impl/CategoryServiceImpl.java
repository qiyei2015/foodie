package com.qiyei.foodie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiyei.foodie.mapper.CategoryMapper;
import com.qiyei.foodie.mapper.ItemsMapper;
import com.qiyei.foodie.pojo.Category;
import com.qiyei.foodie.pojo.Items;
import com.qiyei.foodie.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper mCategoryMapper;

    @Autowired
    private ItemsMapper mItemsMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootCategory() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fatherId",0);
        return mCategoryMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> querySubCategoryByParent(Integer parentId) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fatherId",parentId);
        return mCategoryMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Items> queryItemsByCategory(Integer categoryId, int size) {
        Example example = new Example(Items.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("catId",categoryId);

        // PageHelper 使用非常简单，只需要设置页码和每页显示笔数即可
        PageHelper.startPage(0, size);
        //分页查询
        PageInfo<Items> pageInfo = new PageInfo<>(mItemsMapper.selectByExample(example));

        return pageInfo.getList();
    }
}
