
package com.huawei.roc.mybatis;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.huawei.roc.mybatis.demo.dao.Goods;
import com.huawei.roc.mybatis.demo.dao.GoodsMapper;

public class Mybatis {
    /**
     * 会话工厂
     */
    private static SqlSessionFactory sessionFactory;

    public static void initSessionFactory(String resource) throws Exception {
        // 使用MyBatis提供的Resources类加载mybatis的配置文件
        Reader reader = Resources.getResourceAsReader(resource);
        // 构建sqlSession的工厂
        sessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    /**
     * 创建能执行映射文件中sql的sqlSession
     * 
     * @return
     */
    public static SqlSession getSession() {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {
        try {
            // SQLite3:mybatis.sqlite3.config.xml/sqlite3.properties
            // Mysql:mybatis.mysql.config.xml/mysql.properties
            Mybatis.initSessionFactory("mybatis.sqlite3.config.xml");
            SqlSession sqlSession = Mybatis.getSession();

            GoodsMapper mapper = sqlSession.getMapper(GoodsMapper.class);
            try {
                List<Goods> goodsList = mapper.selectAllGoods();
                System.out.println(goodsList.toString());
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
