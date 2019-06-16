package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * This api endpoint is used to get top 5 items by popularity
     *
     * @param restaurantId UUID for restaurant entity
     *
     * @return ResponseEntity<ItemListResponse> type object along with HttpStatus OK
     *
     * @throws RestaurantNotFoundException If validation on restaurant fails
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/item/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ResponseEntity<ItemListResponse> getItemsByPopularity(
            @PathVariable("restaurant_id") final String restaurantId)
            throws RestaurantNotFoundException
    {
        List<ItemEntity> itemEntityList = itemService.getItemsByPopularity(restaurantService.restaurantByUUID(restaurantId));

        ItemListResponse itemListResponse = new ItemListResponse();

        int count = 0;
        for (ItemEntity itemEntity : itemEntityList) {
            if (count < 5) {
                ItemList itemList = new ItemList()
                        .id(UUID.fromString(itemEntity.getUuid()))
                        .itemName(itemEntity.getItemName())
                        .price(itemEntity.getPrice())
                        .itemType(ItemList.ItemTypeEnum.fromValue(itemEntity.getType().getValue()));
                itemListResponse.add(itemList);
                count += 1;
            } else {
                break;
            }
        }

        return new ResponseEntity<ItemListResponse>(itemListResponse, HttpStatus.OK);
    }
}