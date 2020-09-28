package com.qiyei.foodie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiyei.common.enums.CommentLevel;
import com.qiyei.foodie.mapper.*;
import com.qiyei.foodie.pojo.*;
import com.qiyei.foodie.pojo.bo.ItemsBO;
import com.qiyei.foodie.pojo.vo.CommentLevelCountsVO;
import com.qiyei.foodie.pojo.vo.ItemsCommentsVO;
import com.qiyei.foodie.pojo.vo.ItemsVO;
import com.qiyei.foodie.pojo.vo.ShopCartVO;
import com.qiyei.foodie.service.IItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemsServiceImpl implements IItemsService {

    @Autowired
    private ItemsMapper mItemsMapper;

    @Autowired
    private ItemsCommentsMapper mItemsCommentsMapper;

    @Autowired
    private ItemsImgMapper mItemsImgMapper;

    @Autowired
    private ItemsParamMapper mItemsParamMapper;

    @Autowired
    private ItemsSpecMapper mItemsSpecMapper;

    @Autowired
    private UsersMapper mUsersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsVO queryItems(ItemsBO itemsBO) {

        Example example = new Example(Items.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("catId",itemsBO.getCategoryId());

        return queryItemsByExample(itemsBO, example);
    }
    
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsVO searchItems(ItemsBO itemsBO) {
        Example example = new Example(Items.class);
        Example.Criteria criteria = example.createCriteria();
        // TODO: 2020/9/28 模糊收索？ 
        criteria.andLike("itemName","%" + itemsBO.getKeywords() + "%");

        return queryItemsByExample(itemsBO, example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemsById(String id) {
        Example example = new Example(Items.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);

        return mItemsMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return mItemsImgMapper.selectByExample(example);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return mItemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return mItemsParamMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String id) {
        Example example = new Example(ItemsComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",id);

        List<ItemsComments> comments = mItemsCommentsMapper.selectByExample(example);

        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        if (comments != null) {
            for (ItemsComments comment : comments){
                commentLevelCountsVO.totalCounts++;
                switch (CommentLevel.fromType(comment.getCommentLevel())) {
                    case GOOD:
                        commentLevelCountsVO.goodCounts++;
                        break;
                    case NORMAL:
                        commentLevelCountsVO.normalCounts++;
                        break;
                    case BAD:
                        commentLevelCountsVO.badCounts++;
                        break;
                }
            }
        }
        return commentLevelCountsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsCommentsVO queryComment(String id) {
        Example example = new Example(ItemsComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);

        ItemsComments comment = mItemsCommentsMapper.selectOneByExample(example);

        ItemsCommentsVO commentVO = new ItemsCommentsVO();
        commentVO.setCommentLevel(comment.getCommentLevel());
        commentVO.setContent(comment.getContent());
        commentVO.setSpecName(comment.getSepcName());
        commentVO.setCreatedTime(comment.getCreatedTime());

        Users user = mUsersMapper.selectByPrimaryKey(comment.getUserId());
        if (user != null){
            commentVO.setNickname(user.getNickname());
            commentVO.setUserFace(user.getFace());
        }

        return commentVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopCartVO> queryItemsBySpecIds(List<String> ids) {
        return null;
    }

    private ItemsVO queryItemsByExample(ItemsBO itemsBO, Example example) {
        ItemsVO itemsVO = new ItemsVO();
        long total = mItemsMapper.selectCountByExample(example);
        PageHelper.startPage(itemsBO.getPage(), itemsBO.getPageSize());
        //分页查询
        PageInfo<Items> pageInfo = new PageInfo<>(mItemsMapper.selectByExample(example));

        itemsVO.setItems(pageInfo.getList());
        itemsVO.setTotal(total);
        itemsVO.setPage(itemsBO.getPage());
        itemsVO.setTotalPage((int)(total / itemsBO.getPageSize()) + 1);
        return itemsVO;
    }
}
