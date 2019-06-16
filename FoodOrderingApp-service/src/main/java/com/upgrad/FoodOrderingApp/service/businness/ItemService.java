package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CategoryDao categoryDao;

    /**
     * Returns item for a given UUID
     *
     * @param uuid UUID of item entity
     *
     * @return ItemEntity object
     *
     * @throws ItemNotFoundException If validation on item fails
     */
    public ItemEntity getItemByUUID(String uuid) throws ItemNotFoundException {
        ItemEntity itemEntity = itemDao.getItemByUUID(uuid);
        if (itemEntity == null) {
            throw new ItemNotFoundException("INF-003", "No item by this id exist");
        }
        return itemEntity;
    }

    /**
     * Returns items for a given order
     *
     * @param orderEntity UUID of order entity
     *
     * @return List<OrderItemEntity> object
     */
    public List<OrderItemEntity> getItemsByOrder(OrderEntity orderEntity) {
        return orderItemDao.getItemsByOrder(orderEntity);
    }

    /**
     * Returns popular items for a restaurant
     *
     * @param restaurantEntity UUID of restaurant entity
     *
     * @return List<ItemEntity> object
     */
    public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurantEntity) {
        List<ItemEntity> itemEntityList = new ArrayList<>();
        for (OrderEntity orderEntity : orderDao.getOrdersByRestaurant(restaurantEntity)) {
            for (OrderItemEntity orderItemEntity : orderItemDao.getItemsByOrder(orderEntity)) {
                itemEntityList.add(orderItemEntity.getItem());
            }
        }

        // count all with map
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (ItemEntity itemEntity : itemEntityList) {
            Integer count = map.get(itemEntity.getUuid());
            map.put(itemEntity.getUuid(), (count == null) ? 1 : count + 1);
        }

        // sort map
        Map<String, Integer> treeMap = new TreeMap<String, Integer>(map);
        List<ItemEntity> sortedItemEntityList = new ArrayList<ItemEntity>();
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            sortedItemEntityList.add(itemDao.getItemByUUID(entry.getKey()));
        }
        Collections.reverse(sortedItemEntityList);

        return sortedItemEntityList;
    }

    /**
     * Returns items by for a given category of a restaurant
     *
     * @param restaurantUUID UUID of restaurant entity
     * @param categoryUUID UUID of category entity
     *
     * @return List<ItemEntity> object
     */
    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantUUID, String categoryUUID) {
        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUUID(restaurantUUID);
        CategoryEntity categoryEntity = categoryDao.getCategoryByUuid(categoryUUID);
        List<ItemEntity> restaurantItemEntityList = new ArrayList<ItemEntity>();

        for (ItemEntity restaurantItemEntity : restaurantEntity.getItems()) {
            for (ItemEntity categoryItemEntity : categoryEntity.getItems()) {
                if (restaurantItemEntity.getUuid().equals(categoryItemEntity.getUuid())) {
                    restaurantItemEntityList.add(restaurantItemEntity);
                }
            }
        }
        restaurantItemEntityList.sort(Comparator.comparing(ItemEntity::getItemName));

        return restaurantItemEntityList;
    }
}