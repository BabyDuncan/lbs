package com.babyduncan.dao;

import com.babyduncan.model.Shop;
import com.sun.tools.javac.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * Created by zhaoguohao on 2018/12/8.
 */
@Service
public class ShopDaoImpl implements ShopDao {

    private static final Logger logger = Logger.getLogger(ShopDaoImpl.class);

    private static SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    private static void init() throws IOException {
        String[] locations = {".", "classes", "bin", "target/classes", "target/test-classes"};

        String resource = "mybatis-config.xml";
        for (String location : locations) {
            File f = new File(location, resource);
            if (f.exists()) {
                Reader reader = new FileReader(f);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                logger.info("sqlSessionFactory build success!!");
            } else {
                logger.error("sqlSessionFactory build failed!!");
            }
        }

    }


    public Shop getShop(int id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Shop shop = session.selectOne(
                    "com.babyduncan.mapper.ShopMapper.selectOneShop", id);
            return shop;
        } finally {
            session.close();
        }
    }

    public boolean updateShop(Shop shop) {
        return false;
    }

    public List<Shop> getNearbyShopByLongitudeAndLatitude(double longitude, double latitude) {
        return null;
    }

    public List<Shop> getNearbyShopByGeoHash(String geohash) {
        return null;
    }
}
