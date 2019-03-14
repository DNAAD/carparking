package com.coalvalue.service;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.*;
import com.coalvalue.dto.InventoryDto;
import com.coalvalue.dto.InventoryTransferDto;
import com.coalvalue.enumType.*;
import com.coalvalue.notification.NotificationConsumer;
import com.coalvalue.repository.*;
import com.coalvalue.repository.specific.InventorySpec;
import com.coalvalue.repository.specific.InventoryTransferSpec;
import com.coalvalue.service.sync.DifferentialSyncService;
import com.coalvalue.web.MobileDistributorController;
import com.coalvalue.web.MobileInventoryController;
import com.coalvalue.web.valid.ProductCreateForm;
import com.coalvalue.web.valid.TripCreateForm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static com.coalvalue.configuration.WebSocketConfig.*;
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
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private DistributorService distributorService;
    @Autowired
    private LiveInformationService liveInformationService;


    @Autowired
    private PriceCategoryRepository priceCategoryRepository;

    @Autowired
    private DifferentialSyncService differentialSyncService;


    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;



    @Autowired
    private InstanceTransportRepository instanceTransportRepository;


    @Autowired
    private ProductService productService;









    @Override
    public Inventory getInventory(Product product, StorageSpace storageSpace) {

        Inventory inventory = inventoryRepository.findByStorageNoAndProductNo(storageSpace.getNo(),product.getNo());

        return inventory;
    }

    @Override
    public Inventory getInventoryById(Integer inventoryId) {
        return inventoryRepository.findById(inventoryId).get();
    }



    @Override
    public List<Inventory> getInventoryByStorage(StorageSpace a) {

        return inventoryRepository.findByStorageNo(a.getNo());
    }



    @Override
    public List<Inventory> getInventory(Product product, InventoryStatusEnum open) {
        return inventoryRepository.findByProductNoAndStatus(product.getNo(),open.getText());

    }

    @Override
    public List<Map<String, Object>> getInventoryMap(Product product, InventoryStatusEnum open) {
        return getInventoryMap(product,open,null);
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


            StorageSpace storageSpace = storageSpaceRepository.findByNo(inventory.getStorageNo());


            String storageSpaceUrl =   null;//linkTo(methodOn(MobileStorageSpaceController.class).storageDetail(storageSpace.getId(),null,null)).withSelfRel().getHref();

            element.put("storageUrl", storageSpaceUrl);
        element.put("storageId", storageSpace.getId());
            element.put("storageAddress", storageSpace.getProvinceCityDistrictStreetString());
            element.put("storageName", storageSpace.getName());
            element.put("averageWaitingTime", storageSpace.getProvinceCityDistrictStreetString());

            element.put("loadingCount", storageSpace.getLoadingCount());
            element.put("pendingCount", storageSpace.getPendingCount());

            List<Integer> internal = Arrays.asList(1,2,3,4,5);


            element.put("timeliness", getTimeliness(internal,inventory.getModifyDate()));





        return element;
    }
    @Override
    public List<Inventory> getInventory(Product product) {

        return getInventory(product.getNo());

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
                if(getTimeliness(internal,inventory.getModifyDate()) < i){
                    Map<String,Object> element = new HashMap<>();
                    maps.add(getContentElement(inventory,true));
                }
            }


        }


        return maps;

    }

    @Override
    public List<Inventory> getInventory(String no) {

        return inventoryRepository.findByProductNo(no);

    }

    @Override
    public Page<Map> query(StorageSpace storageSpace, Pageable pageable) {
        InventoryDto deliveryOrderDto = new InventoryDto();
        if(storageSpace!= null){
            deliveryOrderDto.setStorageNo(storageSpace.getNo());
        }

      //  deliveryOrderDto.setStatus(DeliveryOrderStatusEnum.Valid.getText());

        InventorySpec deliveryOrderSpec =  new InventorySpec(deliveryOrderDto);
        Specification<Inventory> spec = deliveryOrderSpec.querySynthesizedSpec();

        Page<Inventory>  pages = inventoryRepository.findAll(spec,pageable);


        Page<Map> page = pages.map(new Function<Inventory, Map>() {
            public Map apply(Inventory objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileInventoryController.class).detail(objectEntity.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);

                LiveInforInventory liveInforInventory = liveInformationService.getInventoryByNo(objectEntity.getNo());
                if(liveInforInventory!= null){
                    mappedObject.put("loadingCount",liveInforInventory.getLoadingCount());

                    mappedObject.put("waitingCount",liveInforInventory.getWaitingCount());

                    mappedObject.put("averageLaytime",liveInforInventory.getAverageLaytime());
                }





/*                Distributor distributor = distributorService.getByNo(objectEntity.getCompanyNo());
                String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",distributorUrl);*/
                return mappedObject;
            }
        });
        return page;
    }

    @Override
    public Page<Map> queryForReport(StorageSpace storageSpace, Pageable pageable) {
        InventoryDto inventoryDto = new InventoryDto();
        if(storageSpace!= null){
            inventoryDto.setStorageNo(storageSpace.getNo());
        }

        inventoryDto.setStatus(InventoryStatusEnum.OPEN.getText());
        InventorySpec inventorySpec =  new InventorySpec(inventoryDto);
        Specification<Inventory> spec = inventorySpec.querySynthesizedSpec();

        Page<Inventory>  pages = inventoryRepository.findAll(spec,pageable);


        Page<Map> page = pages.map(new Function<Inventory, Map>() {
            public Map apply(Inventory objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);



                System.out.println("0----"+ objectEntity.toString());
                List<PriceCategory> priceCategory = priceCategoryRepository.findByProductNoOrderBySeqAsc(objectEntity.getProductNo());

                String s = priceCategory.stream()
                        .filter(e->e.getStatus().equals(PriceCategoryStatusEnum.OPEN.getText()))
                        .map(e-> PriceCategoryTypeEnum.fromString(e.getName()).getDisplayText() +" /" + e.getValue()).collect(Collectors.toList()).toString();
                String expectedValue = priceCategory.stream()
                        .filter(e->e.getStatus().equals(PriceCategoryStatusEnum.OPEN.getText()))
                        .map(e-> PriceCategoryTypeEnum.fromString(e.getName()).getDisplayText() +" /" + e.getExpectedValue()).collect(Collectors.toList()).toString();

                    mappedObject.put("quote", s);
                    mappedObject.put("probationaryQuote",expectedValue);


                String companiesUrl = linkTo(methodOn(MobileInventoryController.class).detail(objectEntity.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);

                LiveInforInventory liveInforInventory = liveInformationService.getInventoryByNo(objectEntity.getNo());
                if(liveInforInventory!= null){
                    mappedObject.put("loadingCount",liveInforInventory.getLoadingCount());

                    mappedObject.put("waitingCount",liveInforInventory.getWaitingCount());

                    mappedObject.put("averageLaytime",liveInforInventory.getAverageLaytime());
                }
                mappedObject.put("quantityOnHand",objectEntity.getQuantityOnHand().setScale(0,BigDecimal.ROUND_HALF_UP));


                return mappedObject;
            }
        });
        return page;
    }






    @Override
    @Transactional
    public Inventory getInventory(String inventoryNo,String productNo,  String productCoalType, String productGranularity) {
        Inventory inventory = inventoryRepository.findByNo(inventoryNo);
        if(inventory == null){
            inventory = new Inventory();
            inventory.setGranularity(productCoalType);
            inventory.setCoalType(productGranularity);
            inventory.setNo(inventoryNo);
            inventory.setProductNo(productNo);
            inventory = inventoryRepository.save(inventory);


        }
        return inventory;
    }

    @Override
    public Inventory getById(Integer index) {



        return inventoryRepository.findById(index).get();
    }

    @Override
    public Page<Map> queryInventoryTransfer(Inventory distributor, Pageable pageable) {

        Page<InventoryTransfer> pages = inventoryTransferRepository.findByInventoryNo(distributor.getNo(),pageable);
        Page<Map> page = pages.map(new Function<InventoryTransfer, Map>() {
            public Map apply(InventoryTransfer objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


/*                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getCollaboratorId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",companiesUrl);*/


                    InstanceTransport instanceTransport = instanceTransportRepository.findByUuid(objectEntity.getInstanceUuid());

                    mappedObject.put("plateNumber",instanceTransport.getLicense());



                if(objectEntity.getDistributorNo()!= null){
                    Distributor distributor = distributorService.getByNo(objectEntity.getDistributorNo());
                    String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getNo(), null)).withSelfRel().getHref();

                    mappedObject.put("distributorUrl",distributorUrl);

                    mappedObject.put("distributor",distributor.getName()+distributor.getNo());
                }
                mappedObject.put("createDate",objectEntity.getCreateDate());


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

        inventoryTransfer.setDistributorNo(null);
        inventoryTransfer.setInstanceUuid(null);
        inventoryTransfer.setInventoryNo(inventory.getNo());

        inventory.setQuantityOnHand(amount);
        inventoryTransfer.setBalance(amount);


        inventoryTransfer = inventoryTransferRepository.save(inventoryTransfer);

        return inventory;

    }


    @Override
    public List<InventoryTransfer> getTransfers(Inventory distributor) {

        return inventoryTransferRepository.findByInventoryNo(distributor.getNo());
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

    public Inventory edit(TripCreateForm locationCreateForm) {


        Inventory inventory = edit_inner(locationCreateForm);

        differentialSyncService.syncImmediately();

        return inventory;
    }

    @Transactional
    public Inventory edit_inner(TripCreateForm locationCreateForm) {


        Inventory inventory = inventoryRepository.findById(locationCreateForm.getId()).get();
        if(locationCreateForm.getInventory()!= null){
            inventory.setQuantityOnHand(locationCreateForm.getInventory());
        }
        if(locationCreateForm.getQuotation()!= null){
            inventory.setQuote(locationCreateForm.getQuotation());
        }


        inventory = inventoryRepository.save(inventory);




        return inventory;
    }

    @Override
    public Page<Map> queryTransfer(InventoryTransferDto o, Pageable pageable) {



        InventoryTransferSpec bankAccountSpec =  new InventoryTransferSpec(o);
        Specification<InventoryTransfer> spec = bankAccountSpec.querySynthesizedSpec();

        Page<InventoryTransfer>  pages = inventoryTransferRepository.findAll(spec,pageable);



        Page<Map> page = pages.map(new Function<InventoryTransfer, Map>() {
            public Map apply(InventoryTransfer objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                mappedObject.put("createDate",objectEntity.getCreateDate());
/*                String companiesUrl = linkTo(methodOn(MobileDistributorController.class).detail(objectEntity.getCollaboratorId(), null, null)).withSelfRel().getHref();

                mappedObject.put("distributorUrl",companiesUrl);*/


                InstanceTransport instanceTransport = instanceTransportRepository.findByUuid(objectEntity.getInstanceUuid());
                mappedObject.put("plateNumber",instanceTransport.getLicense());

                if(objectEntity.getDistributorNo()!= null){
                    Distributor distributor = distributorService.getByNo(objectEntity.getDistributorNo());
                    String distributorUrl = linkTo(methodOn(MobileDistributorController.class).detail(distributor.getNo(), null)).withSelfRel().getHref();
                    mappedObject.put("distributorUrl",distributorUrl);
                    mappedObject.put("distributor",distributor.getName()+distributor.getNo());
                }

                return mappedObject;
            }
        });
        return page;

    }

    @Override
    @Transactional
    public void createFromMap(List<Map> inventory_maps) {
        //  Date date = (Date)inventory_new.get("date");
        List<Inventory> pages = inventoryRepository.findAll();

        Map<String,Inventory> storages = pages.stream().collect(Collectors.toMap(Inventory::getNo, e -> e));

        List<String> hava = new ArrayList<>();

        for(Map inventory_map: inventory_maps){
            String no = (String)inventory_map.get("no");
            String storageSpaceNo = (String)inventory_map.get("storageSpaceNo");
            String productCoalType = (String)inventory_map.get("coalType");
            String productGranularity = (String)inventory_map.get("granularity");
            BigDecimal quotation = (BigDecimal)inventory_map.get("quotation");
            String productNo = (String)inventory_map.get("productNo");


            List<Map> indicators = (List)inventory_map.get("indicators");


            BigDecimal inventory_on_hand = (BigDecimal)inventory_map.get("inventory");
            if(storages.keySet().contains(no)){


                Inventory inventory = storages.get(no);



                inventory.setGranularity(productCoalType);
                inventory.setCoalType(productGranularity);
                inventory.setProductNo(productNo);
                inventory.setStorageNo(storageSpaceNo);
                inventory.setQuantityOnHand(inventory_on_hand);
                inventory.setStatus(SynchronizeEnum.Been_synchronized.getText());
                inventory.setQuote(quotation);
                if(indicators!= null){

                    updateIndicators(inventory,indicators);
                }
                inventory = inventoryRepository.save(inventory);
                hava.remove(no);
        }else{

                Inventory inventory = new Inventory();
                inventory.setGranularity(productCoalType);
                inventory.setCoalType(productGranularity);
                inventory.setProductNo(productNo);
                inventory.setQuantityOnHand(inventory_on_hand);


                inventory.setStatus(SynchronizeEnum.Been_synchronized.getText());
                inventory.setStorageNo(storageSpaceNo);
                inventory.setNo(no);
                inventory.setStatus(SynchronizeEnum.Been_synchronized.getText());
                inventory.setQuote(quotation);
                if(indicators!= null){
                    updateIndicators(inventory,indicators);
                }
                inventory = inventoryRepository.save(inventory);

            }


        }

        for(String storageSpaceNo:hava){
            Inventory storageSpace = storages.get(storageSpaceNo);
            storageSpace.setStatus("invalid");
            inventoryRepository.save(storageSpace);
        }

    }

    private void updateIndicators(Inventory inventory, List<Map> indicators) {
        Map<String,Map> indicator_index_map = new HashMap<>();
        for(Map map: indicators){
            String name = (String)map.get("symbol");
            indicator_index_map.put(name,map);


        }

        List<QualityIndicatorEnum> symbiles = new ArrayList<>();
        symbiles.add(QualityIndicatorEnum.Qnetad);
        symbiles.add(QualityIndicatorEnum.Mt);
        symbiles.add(QualityIndicatorEnum.St);
        symbiles.add(QualityIndicatorEnum.Aad);
        symbiles.add(QualityIndicatorEnum.Vad);


        for(QualityIndicatorEnum indicatorEnum: symbiles){

            Map map = (Map)indicator_index_map.get(indicatorEnum.getSymbol());
            if(map != null){
                BigDecimal bigDecima1l = new BigDecimal(map.get("value").toString());

                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.Qnetad.getSymbol())){
                    inventory.setIndicator1(bigDecima1l);
                }
                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.Mt.getSymbol())){
                    inventory.setIndicator2(bigDecima1l);
                }
                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.St.getSymbol())){
                    inventory.setIndicator3(bigDecima1l);
                }
                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.Aad.getSymbol())){
                    inventory.setIndicator4(bigDecima1l);
                }
                if(indicatorEnum.getSymbol().equals(QualityIndicatorEnum.Vad.getSymbol())){
                    inventory.setIndicator5(bigDecima1l);
                }
            }


        }



    }

    @Override
    public List<Map> getInventoryMap_All(String  no) {
        List<Inventory> inventories = inventoryRepository.findAll();
        List<Map> maps = new ArrayList<>();

        for(Inventory inventory : inventories){
            Map mappedObject = new HashMap<>();


                mappedObject.put("no",inventory.getNo());
            mappedObject.put("coalType",inventory.getCoalType());
            mappedObject.put("granularity",inventory.getGranularity());

            mappedObject.put("producerNo",no);
            maps.add(mappedObject);
        }
        return maps;
    }

    @Override
    public Page<Map> queryAll(Pageable pageable) {
        Page<Inventory> pages =  inventoryRepository.findByStatus(SynchronizeEnum.Been_synchronized.getText(),pageable);
        Page<Map> page = pages.map(new Function<Inventory, Map>() {
            public Map apply(Inventory objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);


                String companiesUrl = linkTo(methodOn(MobileInventoryController.class).detail(objectEntity.getNo(), null)).withSelfRel().getHref();

                mappedObject.put("url",companiesUrl);

                LiveInforInventory liveInforInventory = liveInformationService.getInventoryByNo(objectEntity.getNo());
                if(liveInforInventory!= null){
                    mappedObject.put("loadingCount",liveInforInventory.getLoadingCount());

                    mappedObject.put("waitingCount",liveInforInventory.getWaitingCount());

                    mappedObject.put("averageLaytime",liveInforInventory.getAverageLaytime());
                }


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
    public void changeInventoryQuotation(String productNo, String quotation) {
        List<Inventory> inventories = inventoryRepository.findByProductNo(productNo);

        for(Inventory inventory: inventories){
            inventory.setQuote(new BigDecimal(quotation));
            inventoryRepository.save(inventory);
        }



/*        liveInformationService.update(inventory,deliveryOrder_from);
        behaviouralService.add_delivery_order(notificationData);*/

    }

    @Override
    @Transactional
    public void changeInventory(String inventoryNo, Double inventory) {
        Inventory inventories = inventoryRepository.findByNo(inventoryNo);


        inventories.setQuantityOnHand(new BigDecimal(inventory));
            inventoryRepository.save(inventories);


        Map map = new HashMap();
        Map content = new HashMap();

        map.put("id", 34);

        map.put("type", DataSynchronizationTypeEnum.Inventory.getText());

        content.put("distributor","333");
        content.put("plateNumber","4444");
        content.put("productName","6666");

        map.put("content", content);

        //    simpMessagingTemplate.convertAndSend("/topic/storage/" + map.get("id"), JSON.toJSON(content));
        simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report, JSON.toJSON(content));
    }

    @Override
    @javax.transaction.Transactional
    public OperationResult changeGroupPrice(List<Map> priceCategories) {

        System.out.println("-------priceCategories---priceCategories-----------"+priceCategories.toString());
        System.out.println("-------priceCategories---priceCategories-----------");
        for(Map price : priceCategories){
            Boolean beAdjusted = (Boolean)price.get("beAdjusted");
            if(beAdjusted){
                List<Inventory> inventories = inventoryRepository.findByProductNo((String)price.get("no"));
                for(Inventory inventory:inventories){
                    BigDecimal current = (BigDecimal)price.get("current");
                    inventory.setQuote(current);
                    inventoryRepository.save(inventory);
                }

            }

            System.out.println("---------------------"+price.toString());
        }

        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);
        return  operationResult;


    }

    @Override
    public Product commandProductEdit(ProductCreateForm locationCreateForm) {


        return null;


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
