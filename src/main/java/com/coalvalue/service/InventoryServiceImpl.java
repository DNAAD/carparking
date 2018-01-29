package com.coalvalue.service;

import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.InventoryStatusEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.enumType.SynchronizeEnum;
import com.coalvalue.repository.InstanceTransportRepository;
import com.coalvalue.repository.InventoryRepository;
import com.coalvalue.repository.InventoryTransferRepository;
import com.coalvalue.repository.StorageSpaceRepository;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.MobileInventoryController;
import com.coalvalue.web.valid.TripCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("inventoryService")
public class InventoryServiceImpl extends BaseServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private StorageSpaceRepository storageSpaceRepository;


    @Autowired
    private DistributorService distributorService;




    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;



    @Autowired
    private InstanceTransportRepository instanceTransportRepository;


    @Autowired
    private ProductService productService;







    @Override
    @Transactional
    public Inventory getStorageInventory(Product product, StorageSpace storageSpace) {


        Integer itemid  = product.getId();

        Integer companyId = product.getCompanyId();
        String  itemType =  ResourceType.COAL_PRODUCT.getText();



        Inventory inventory = inventoryRepository.findByStorageIdAndItemIdAndItemType(storageSpace.getId(),itemid,itemType);

        if(inventory == null){
            inventory = new Inventory();
            inventory.setItemId(itemid);


            inventory.setItemType(itemType);
            inventory.setQuantityOnHand(new BigDecimal(0));

            inventory.setStorageId(storageSpace.getId());
            inventory.setCompanyId(companyId);
            inventory = inventoryRepository.save(inventory);

        }


        return inventory;
    }

    @Override
    public List<Inventory> getInventory(Product product, Pageable pageable) {

        return inventoryRepository.findByItemIdAndItemType(product.getId(), ResourceType.COAL_PRODUCT.getText());
    }



    @Override
    public Inventory getInventory(Product product, StorageSpace storageSpace) {

        Inventory inventory = inventoryRepository.findByStorageIdAndItemIdAndItemType(storageSpace.getId(),product.getId(), ResourceType.COAL_PRODUCT.getText());

        return inventory;
    }

    @Override
    public Inventory getInventoryById(Integer inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }



    @Override
    public List<Inventory> getInventoryByStorage(StorageSpace a) {

        return inventoryRepository.findByStorageIdAndItemType(a.getId(), ResourceType.COAL_PRODUCT.getText());
    }

    @Override
    public List<Inventory> getInventoryByProductId(Integer productId) {

        return inventoryRepository.findByItemIdAndItemType(productId, ResourceType.COAL_PRODUCT.getText());


    }

    @Override
    public List<Inventory> getInventory(Product product, InventoryStatusEnum open) {
        return inventoryRepository.findByItemIdAndItemTypeAndStatus(product.getId(), ResourceType.COAL_PRODUCT.getText(),open.getText());

    }

    @Override
    public List<Map<String, Object>> getInventoryMap(Product product, InventoryStatusEnum open) {
        return getInventoryMap(product,open,null);
    }

    @Override
    public Map<String,Object> getInventoryPage(Product product, Pageable pageable) {


        Page<Inventory> pages = inventoryRepository.findByItemIdAndItemType(product.getId(), ResourceType.COAL_PRODUCT.getText(),pageable);

        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("totalElements",pages.getTotalElements());
        objectMap.put("totalPages",pages.getTotalPages());

        objectMap.put("totalElements",pages.getTotalElements());
        objectMap.put("content",getContent(pages,true));
        return objectMap;

    }



    private List<Map<String, Object>> getContent(Page<Inventory> result, boolean isMoobile){
        List<Map<String, Object>> content = new LinkedList<>();


        List<Map<String, Object>> maps = new ArrayList<>();

        for(Inventory inventory : result){
            content.add(getContentElement(inventory,true));
        }


        return content;
    }


    private Map<String, Object> getContentElement(Inventory inventory, boolean isMoobile){




            Map<String,Object> element = new HashMap<>();
            element.put("quantityOnHand", inventory.getQuantityOnHand());
            element.put("status", inventory.getStatus());
        element.put("id", inventory.getId());


            StorageSpace storageSpace = storageSpaceRepository.findById(inventory.getStorageId());


            String storageSpaceUrl =   null;//linkTo(methodOn(MobileStorageSpaceController.class).storageDetail(storageSpace.getId(),null,null)).withSelfRel().getHref();

            element.put("storageUrl", storageSpaceUrl);
        element.put("storageId", storageSpace.getId());
            element.put("storageAddress", storageSpace.getProvinceCityDistrictStreetString());
            element.put("storageName", storageSpace.getName());
            element.put("averageWaitingTime", storageSpace.getProvinceCityDistrictStreetString());

            element.put("loadingCount", storageSpace.getLoadingCount());
            element.put("pendingCount", storageSpace.getPendingCount());

            List<Integer> internal = Arrays.asList(1,2,3,4,5);


            element.put("timeliness", getTimeliness(internal,LocalDateTime.ofInstant(inventory.getModifyDate().toInstant(), ZoneId.systemDefault()))+"");





        return element;
    }
    @Override
    public List<Inventory> getInventory(Product product) {

        return getInventory(product.getId());

    }

    @Override
    public List<Map<String, Object>> getInventoryMap(Product product, InventoryStatusEnum open, Integer i) {

        List<Inventory> inventories = null;
        if(open != null){
            inventories = getInventory(product,open);
        }else{
            inventories = getInventory(product);
        }



        List<Map<String, Object>> maps = new ArrayList<>();

        for(Inventory inventory : inventories){

            List<Integer> internal = Arrays.asList(1,2,3,4,5);
            if(i != null){
                if(getTimeliness(internal,LocalDateTime.ofInstant(inventory.getModifyDate().toInstant(), ZoneId.systemDefault())) < i){
                    Map<String,Object> element = new HashMap<>();
                    maps.add(getContentElement(inventory,true));
                }
            }


        }


        return maps;

    }

    @Override
    public List<Inventory> getInventory(Integer productId) {

        return inventoryRepository.findByItemIdAndItemType(productId, ResourceType.COAL_PRODUCT.getText());

    }

    @Override
    public Page<Map> query(Object o, Pageable pageable) {

        Page<Inventory> pages =  inventoryRepository.findAll(pageable);
        Page<Map> page = pages.map(new Converter<Inventory, Map>() {
            public Map convert(Inventory objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileInventoryController.class).detail(objectEntity.getId(), null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);


/*                Distributor distributor = distributorService.getByNo(objectEntity.getCompanyNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);*/
                return mappedObject;
            }
        });
        return page;
    }

    @Override
    @Transactional
    public Inventory getInventory(String inventoryNo, String productCoalType, String productGranularity) {
        Inventory inventory = inventoryRepository.findByNo(inventoryNo);
        if(inventory == null){
            inventory = new Inventory();
            inventory.setGranularity(productCoalType);
            inventory.setCoalType(productGranularity);
            inventory.setNo(inventoryNo);
            inventory = inventoryRepository.save(inventory);


        }
        return inventory;
    }

    @Override
    public Inventory getById(Integer index) {



        return inventoryRepository.findById(index);
    }

    @Override
    public Page<Map> queryInventoryTransfer(Inventory distributor, Pageable pageable) {



        Page<InventoryTransfer> pages = inventoryTransferRepository.findByInventoryId(distributor.getId(),pageable);



        Page<Map> page = pages.map(new Converter<InventoryTransfer, Map>() {
            public Map convert(InventoryTransfer objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


/*                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getCollaboratorId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",companiesUrl);*/

                if(objectEntity.getInstanceId()!= null){
                    InstanceTransport instanceTransport = instanceTransportRepository.findById(objectEntity.getInstanceId());


                    mappedObject.put("plateNumber",instanceTransport.getPlateNumber());

                }


                if(objectEntity.getDistributor()!= null){
                    Distributor distributor = distributorService.getById(objectEntity.getDistributor());
                    String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(), null)).withSelfRel().getHref();

                    mappedObject.put("distributorUrl",distributorUrl);

                    mappedObject.put("distributor",distributor.getName()+distributor.getCompanyNo());
                }

                return mappedObject;
            }
        });
        return page;
    }

    @Override
    @Transactional
    public Inventory addInventory(Inventory inventory, BigDecimal amount) {


        InventoryTransfer inventoryTransfer = new InventoryTransfer();
        inventoryTransfer.setAmount(amount);

        inventoryTransfer.setDistributor(null);
        inventoryTransfer.setInstanceId(null);
        inventoryTransfer.setInventoryId(inventory.getId());

        inventory.setQuantityOnHand(amount);
        inventoryTransfer.setBalance(amount);


        inventoryTransfer = inventoryTransferRepository.save(inventoryTransfer);

        return inventory;

    }


    @Override
    public List<InventoryTransfer> getTransfers(Inventory distributor) {

        return inventoryTransferRepository.findByInventoryId(distributor.getId());
    }

    @Override
    public Inventory getInventoryByNo(String inventoryNo) {
        return inventoryRepository.findByNo(inventoryNo);
    }

    @Override
    public long getInventoryCount() {

        return inventoryTransferRepository.countBy();
    }

    @Override
    public List<Inventory> getInventory() {


        return inventoryRepository.findAll();
    }

    @Override
    public void update(Inventory inventory) {

    }

    @Override
    @Transactional
    public void createInventoryFromMap(Map inventory_new) {

     //  Date date = (Date)inventory_new.get("date");
        String no = (String)inventory_new.get("no");
        String productCoalType = (String)inventory_new.get("productCoalType");
        String productGranularity = (String)inventory_new.get("productGranularity");
        BigDecimal inventory_on_hand = (BigDecimal)inventory_new.get("inventory");
        BigDecimal quotation = (BigDecimal)inventory_new.get("quotation");
        Inventory inventory = new Inventory();
        inventory.setGranularity(productCoalType);
        inventory.setCoalType(productGranularity);
        inventory.setNo(no);
        inventory.setQuote(quotation);
        inventory.setQuantityOnHand(inventory_on_hand);
        inventory.setStatus(SynchronizeEnum.Been_synchronized.getText());

        inventory = inventoryRepository.save(inventory);




    }

    @Override
    @Transactional
    public Inventory edit(TripCreateForm locationCreateForm) {


        Inventory inventory = inventoryRepository.findById(locationCreateForm.getId());
        if(locationCreateForm.getInventory()!= null){
            inventory.setQuantityOnHand(locationCreateForm.getInventory());

            inventory.setStatus(SynchronizeEnum.Not_Been_synchronized.getText());
        }
        if(locationCreateForm.getQuotation()!= null){
            inventory.setQuote(locationCreateForm.getQuotation());
            inventory.setStatus(SynchronizeEnum.Not_Been_synchronized.getText());
        }



        inventory = inventoryRepository.save(inventory);
        return inventory;
    }


    private Integer getTimeliness(List<Integer> internal ,LocalDateTime testDate){
        LocalDateTime current = LocalDateTime.now();

        List<Map> periods  = new ArrayList<>();
        LocalDateTime beforeDate = current;
        for(int i = 0 ; i< internal.size(); i++){


            Integer integer  = internal.get(i);
            LocalDateTime date1 = current.minusHours(integer);
            if(isWithinRange(testDate,beforeDate,date1)){
                return i;
            }
   /*         Map map = new HashMap<>();
            map.put("beforeDate",beforeDate);
            map.put("afterDate",date1);
            periods.add(map);
            //Period period = Period.between(beforeDate, date1);
            beforeDate =date1;*/
        }

        return 1000;


    }

    boolean isWithinRange(LocalDateTime testDate,LocalDateTime startDate,LocalDateTime endDate) {
        return !(testDate.isBefore(endDate) || testDate.isAfter(startDate));
    }
}
