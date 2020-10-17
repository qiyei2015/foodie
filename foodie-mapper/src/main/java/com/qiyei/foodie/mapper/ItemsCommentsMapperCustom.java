package com.qiyei.foodie.mapper;


import com.qiyei.foodie.my.mapper.MyMapper;
import com.qiyei.foodie.pojo.ItemsComments;
import com.qiyei.foodie.pojo.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public void saveComments(Map<String, Object> map);

    public List<CommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}