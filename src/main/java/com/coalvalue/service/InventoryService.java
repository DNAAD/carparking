package com.coalvalue.service;


import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.domain.entity.Product;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.dto.InventoryTransferDto;
import com.coalvalue.enumType.InventoryStatusEnum;
import com.coalvalue.web.valid.ProductCreateForm;
import com.coalvalue.web.valid.TripCreateForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface InventoryService extends BaseService {





    Inventory getInventory(Product product, StorageSpace storageSpace);

    Inventory getInventoryById(Integer inventoryId);


    List<Inventory> getInventoryByStorage(StorageSpace a);

    List<Inventory> getInventory(Product product, InventoryStatusEnum open);

    List<Map<String,Object>> getInventoryMap(Product product, InventoryStatusEnum open);


    List<Inventory> getInventory(Product product);

    List<Map<String,Object>> getInventoryMap(Product product, InventoryStatusEnum open, Integer i);

    List<Inventory> getInventory(String productId);

    Page<Map> query(StorageSpace o, Pageable pageable);

    Page<Map> queryForReport(StorageSpace storageSpace, Pageable pageable);

    Inventory getInventory(String inventoryNo, String productNo, String productCoalType, String productGranularity);


    Inventory getById(Integer index);

    Page<Map> queryInventoryTransfer(Inventory distributor, Pageable pageable);

    Inventory addInventory(Inventory inventory, BigDecimal amount);

    List<InventoryTransfer> getTransfers(Inventory distributor);

    Inventory getInventoryByNo(String inventoryNo);

    long getInventoryCount();




    Inventory edit(TripCreateForm locationCreateForm);


    Page<Map> queryTransfer(InventoryTransferDto o, Pageable pageable);

    void createFromMap(List<Map> inventory_map);


    List<Map> getInventoryMap_All(String stringValue);


    Page<Map> queryAll(Pageable pageRequest);


    void changeInventoryQuotation(String productNo, String quotation);

    void changeInventory(String inventoryNo, Double inventory);

    OperationResult changeGroupPrice(List<Map> priceCategories);


    Product commandProductEdit(ProductCreateForm locationCreateForm);

}
