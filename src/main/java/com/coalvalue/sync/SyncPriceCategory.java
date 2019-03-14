package com.coalvalue.sync;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.entity.PriceCategory;
import com.coalvalue.domain.entity.SyncProperties;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.enumType.EchoSessionTypeEnum;
import com.coalvalue.enumType.ResourceType;
import com.coalvalue.notification.NotificationConsumer;
import com.coalvalue.repository.PriceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coalvalue.configuration.WebSocketConfig.*;
@Service
public class SyncPriceCategory {
    public String supportType = ResourceType.PRICE_CATEGORY.getText();

    @Autowired
    private PriceCategoryRepository priceCategoryRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Transactional
    public void diffSync_from_server(List<SyncProperties> syncPropertiesesMap, String echo_session, LocalDateTime lastSync) {

        List<SyncProperties> syncPropertieses_storage = syncPropertiesesMap.stream().filter(e->e.getItemType().equals(supportType)).collect(Collectors.toList());
        if(syncPropertieses_storage.size() == 0){
            return ;
        }


        List<String> uuids = syncPropertieses_storage.stream().map(e->e.getObjectUuid()).collect(Collectors.toList());
        Map<String,PriceCategory> map_inventory = priceCategoryRepository.findByUuidIn(uuids).
                stream().collect( Collectors .toMap(vo -> vo.getUuid(), Function.identity()));


        syncPropertieses_storage.stream().forEach(e-> {

            PriceCategory priceCategory_local = map_inventory.get(e.getObjectUuid());

            Map quotationPlan_remote = JSON.parseObject(e.getContent(),Map.class);
            PriceCategory priceCategory = createFromMap(quotationPlan_remote,priceCategory_local);

        });




        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
            public void afterCommit(){
                System.out.println("commit!!!");

                if(echo_session!= null && echo_session.equals(EchoSessionTypeEnum.Whole.getText())){

                }else{


                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&发送 价格调整通知 &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    Map content = new HashMap();

                    content.put("type", DataSynchronizationTypeEnum.PriceCategory.getText());
                    //content.put("distributor",priceCategory.getName());
                    content.put("plateNumber","");
                    // content.put("productName",priceCategory.getValue());

                    simpMessagingTemplate.convertAndSend(topic__COALPIT_DELIVERY_report, JSON.toJSON(content));
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&发送 价格调整通知 &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

                }

            }
        });





    }






    public PriceCategory createFromMap(Map inventory_map, PriceCategory priceCategory) {

        String uuid = (String)inventory_map.get("uuid");
        String value = (String)inventory_map.get("value");
        String probationaryValue = (String)inventory_map.get("probationaryValue");
        Integer seq = (Integer)inventory_map.get("seq");
        String status = (String)inventory_map.get("status");
        if(priceCategory == null){
            String name = (String)inventory_map.get("name");

            String objectUuid = (String)inventory_map.get("objectUuid");
            String productNo = (String)inventory_map.get("productNo");
            priceCategory = new PriceCategory();
            priceCategory.setObjectUuid(objectUuid);
            priceCategory.setProductNo(productNo);
            priceCategory.setName(name);
            priceCategory.setUuid(uuid);


        }

        priceCategory.setStatus(status);
        priceCategory.setValue(new BigDecimal(value));
        priceCategory.setExpectedValue(new BigDecimal(probationaryValue));
        priceCategory.setSeq(seq);
        return priceCategoryRepository.save(priceCategory);
    }
}
