package com.babyduncan.dao;

import com.babyduncan.model.Shop;
import java.util.List;

/**
 * Created by zhaoguohao on 2018/12/8.
 */
public interface ShopDao {


    Shop getShop(int id);

    public List<Shop> getAllShops();

    boolean updateShop(Shop shop);

    List<Shop> getNearbyShopByLongitudeAndLatitude(double longitude, double latitude);

    List<Shop> getNearbyShopByGeoHash(String geohash);

}
