package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.Product;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.repository.ProductRepository;
import com.coalvalue.repository.StorageSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class SyncProduct {
    public String supportType = ResourceType.COAL_PRODUCT.getText();

    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap) {

        List<SyncProperties> syncPropertieses_syncProduct = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_syncProduct.size() == 0){
            return ;
        }



        List<String> uuids = syncPropertieses_syncProduct.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,Product> map_item = productRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));
        for(SyncProperties syncProperties:syncPropertieses_syncProduct){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);
            Product product = map_item.get(syncProperties.getObjectUuid());
            createProductsFromMap(quotationPlan_remote,product);




        }

    }
/*
    @Transactional
    public void sync(List<SyncProperties> syncPropertieses_quotationPlan) {


        for(SyncProperties syncProperties:syncPropertieses_quotationPlan){

            Map quotationPlan_remote = JSON.parseObject(syncProperties.getContent(),Map.class);
            createProductsFromMap(quotationPlan_remote);




        }

    }*/





    @Transactional
    public void createProductsFromMap(Map inventory_map, Product product) {
        //  Date date = (Date)inventory_new.get("date");
        String no = (String)inventory_map.get("no");
        String objectUuid = (String)inventory_map.get("objectUuid");

        if(product == null){
            Product inventory = new Product();

            String productCoalType = (String)inventory_map.get("coalType");

            String productGranularity = (String)inventory_map.get("granularity");
            BigDecimal quotation = (BigDecimal)inventory_map.get("quotation");
            String status = (String)inventory_map.get("status");
            BigDecimal inventory_on_hand = (BigDecimal)inventory_map.get("inventory");

            inventory.setNo(no);

            inventory.setGranularity(productCoalType);
            inventory.setCoalType(productGranularity);
            inventory.setStatus(status);
            inventory.setUuid(objectUuid);
            inventory = productRepository.save(inventory);
        }

    }
}
