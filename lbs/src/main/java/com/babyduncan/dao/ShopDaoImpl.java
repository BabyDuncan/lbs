package com.babyduncan.dao;

import com.babyduncan.model.Shop;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhaoguohao on 2018/12/8.
 */
@Service
public class ShopDaoImpl implements ShopDao {

    /**
     * 默认地球半径
     */
    private static double EARTH_RADIUS = 6371;

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

    public List<Shop> getAllShops() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Shop> shops = session.selectList("com.babyduncan.mapper.ShopMapper.selectAllShops");
            return shops;
        } finally {
            session.close();
        }
    }

    public boolean updateShop(Shop shop) {
        return false;
    }

    public List<Shop> getNearbyShopByLongitudeAndLatitude(double longitude, double latitude, int radius) {
        SqlSession session = sqlSessionFactory.openSession();
        Map hashmap = new HashMap<String, Double>();

        Map<String, double[]> fourPoint = getSquareFourPoint(longitude, latitude, radius);
        hashmap.put("minLatitude", fourPoint.get("leftBottomPoint")[0]);
        hashmap.put("minLongitude", fourPoint.get("rightTopPoint")[1]);
        hashmap.put("maxLatitude", fourPoint.get("rightTopPoint")[0]);
        hashmap.put("maxLongitude", fourPoint.get("leftBottomPoint")[1]);

        System.out.println(hashmap);
        try {
            List<Shop> shops = session.selectList("com.babyduncan.mapper.ShopMapper.getNearbyShopByLongitudeAndLatitude", hashmap);
            return shops;
        } finally {
            session.close();
        }
    }


    /**
     * 计算经纬度点对应正方形4个点的坐标
     *
     * @param longitude
     * @param latitude
     * @param distance
     * @return
     */
    public Map<String, double[]> getSquareFourPoint(double longitude,
                                                    double latitude, double distance) {
        Map<String, double[]> squareMap = new HashMap<String, double[]>();
        double dLongitude = 2 * (Math.asin(Math.sin(distance
                / (2 * EARTH_RADIUS))
                / Math.cos(Math.toRadians(latitude))));
        dLongitude = Math.toDegrees(dLongitude);
        double dLatitude = distance / EARTH_RADIUS;
        dLatitude = Math.toDegrees(dLatitude);
        double[] leftTopPoint = {latitude + dLatitude, longitude - dLongitude};
        double[] rightTopPoint = {latitude + dLatitude, longitude + dLongitude};
        double[] leftBottomPoint = {latitude - dLatitude,
                longitude - dLongitude};
        double[] rightBottomPoint = {latitude - dLatitude,
                longitude + dLongitude};
        squareMap.put("leftTopPoint", leftTopPoint);
        squareMap.put("rightTopPoint", rightTopPoint);
        squareMap.put("leftBottomPoint", leftBottomPoint);
        squareMap.put("rightBottomPoint", rightBottomPoint);
        return squareMap;
    }

    public List<Shop> getNearbyShopByGeoHash(String geohash) {
        return null;
    }
}
