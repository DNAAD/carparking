package com.coalvalue.service;

import com.coalvalue.domain.Trip;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.Product;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.enumType.InventoryStatusEnum;
import com.coalvalue.web.valid.TripCreateForm;
import com.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface InventoryService extends BaseService {


    Inventory getStorageInventory(Product product, StorageSpace storageSpace);

    List<Inventory> getInventory(Product product, Pageable pageable);




    Inventory getInventory(Product product, StorageSpace storageSpace);

    Inventory getInventoryById(Integer inventoryId);


    List<Inventory> getInventoryByStorage(StorageSpace a);

    List<Inventory> getInventoryByProductId(Integer productId);

    List<Inventory> getInventory(Product product, InventoryStatusEnum open);

    List<Map<String,Object>> getInventoryMap(Product product, InventoryStatusEnum open);



    Map<String,Object> getInventoryPage(Product product, Pageable pageable);


    List<Inventory> getInventory(Product product);

    List<Map<String,Object>> getInventoryMap(Product product, InventoryStatusEnum open, Integer i);

    List<Inventory> getInventory(Integer productId);

    Page<Map> query(Object o, Pageable pageable);

    Inventory getInventory(String inventoryNo, String productCoalType, String productGranularity);


    Inventory getById(Integer index);

    Page<Map> queryInventoryTransfer(Inventory distributor, Pageable pageable);

    Inventory addInventory(Inventory inventory, BigDecimal amount);

    List<InventoryTransfer> getTransfers(Inventory distributor);

    Inventory getInventoryByNo(String inventoryNo);

    long getInventoryCount();


    List<Inventory> getInventory();

    void update(Inventory inventory);


    void createInventoryFromMap(Map inventory_new);


    Inventory edit(TripCreateForm locationCreateForm);


}
