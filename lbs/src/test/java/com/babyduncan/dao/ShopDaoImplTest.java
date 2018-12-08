package com.babyduncan.dao;

import com.babyduncan.model.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by zhaoguohao on 2018/12/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class ShopDaoImplTest {

    @Autowired
    ShopDao shopDao;

    @Test
    public void getShop() throws Exception {
        Shop shop = shopDao.getShop(1);
        System.out.println(shop);
        System.out.println(shopDao.getAllShops());
    }

    @Test
    public void updateShop() throws Exception {

    }

    @Test
    public void getNearbyShopByLongitudeAndLatitude() throws Exception {
        System.out.println(shopDao.getNearbyShopByLongitudeAndLatitude(39.944000, 116.427000));
    }

    @Test
    public void getNearbyShopByGeoHash() throws Exception {

    }

}