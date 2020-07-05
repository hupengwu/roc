
package com.huawei.roc.mybatis.demo.dao;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 参考文章
 * https://blog.csdn.net/gxt521521/article/details/72365521
 * https://blog.csdn.net/qq_19306415/article/details/84455144
 * 
 * @author h00442047
 * @since 2019年12月28日
 */
public class DBTools {
    public static SqlSessionFactory sessionFactory;
    static {
        try {
            // 使用MyBatis提供的Resources类加载mybatis的配置文件
            Reader reader = Resources.getResourceAsReader("mybatis.sqlite3.config.xml");
            // 构建sqlSession的工厂
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 创建能执行映射文件中sql的sqlSession
    public static SqlSession getSession() {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {
        try {
            SqlSession session = DBTools.getSession();
            GoodsMapper mapper = session.getMapper(GoodsMapper.class);
            try {
                List<Goods> goodsList = mapper.selectAllGoods();
                System.out.println(goodsList.toString());
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
                session.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
