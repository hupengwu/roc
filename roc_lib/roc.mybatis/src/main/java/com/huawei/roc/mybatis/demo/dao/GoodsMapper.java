package com.huawei.roc.mybatis.demo.dao;

import java.util.List;
 
public interface GoodsMapper {
    public List<Goods> selectAllGoods() throws Exception;
    public List<Goods> selectRecommendGoods() throws Exception;
}
