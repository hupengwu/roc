package com.huawei.roc.channel.master.data.dao;

import java.util.List;
 
public interface GoodsMapper {
    public List<Goods> selectAllGoods() throws Exception;
    public List<Goods> selectRecommendGoods() throws Exception;
}
