package com.coalvalue.service;


import com.coalvalue.configuration.MqttPublishSample;
import com.coalvalue.configuration.MqttReceiver;
//import com.coalvalue.domain.entity.Station;
import com.coalvalue.domain.entity.StorageSpace;
//import com.coalvalue.repository.StationRepository;
import com.coalvalue.enumType.SynchronizeEnum;
import com.coalvalue.repository.StorageSpaceRepository;

import com.coalvalue.web.MobileDeliveryOrderScanController;
import com.coalvalue.web.MobileReportController;
import com.coalvalue.web.MobileStorageController;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by silence yuan on 2015/6/28.
 */
@Service("storageService")
public class StorageServiceImpl extends BaseServiceImpl implements StorageService {

/*

    @Autowired
    private StationRepository stationRepository;
*/

    @Autowired
    private StorageSpaceRepository storageSpaceRepository;






    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private MqttPublishSample mqttPublishSample;
    @Autowired
    private MqttReceiver mqttReceiver;

    @Autowired
    private ConfigurationService configurationService;






    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);


/*

    @Override
    public Page<Station> queryStations(Integer userId, Pageable pageable) {
        return stationRepository.findAll(pageable);
    }


    @Override
    @Transactional
    public Station create(StationCreateForm lineCreateForm) {

        Station line = new Station();
        line.setName(lineCreateForm.getName());
        line.setDescription(lineCreateForm.getDescription());
        line.setCompanyId(lineCreateForm.getCompanyId());
        line.setStatus(lineCreateForm.getStatus());
        line.setLocationId(lineCreateForm.getLocationId());
        line.setType(lineCreateForm.getType());


        return stationRepository.save(line);
    }

    @Override
    @Transactional
    public Station edit(StationCreateForm locationCreateForm) {


        Station line = stationRepository.findOne(locationCreateForm.getId());
        if(line != null){
            line.setName(locationCreateForm.getName());
            line.setDescription(locationCreateForm.getDescription());
            line.setName(locationCreateForm.getName());

            line.setCompanyId(locationCreateForm.getCompanyId());
            line.setStatus(locationCreateForm.getStatus());
            line.setLocationId(locationCreateForm.getLocationId());
            line.setType(locationCreateForm.getType());

            return stationRepository.save(line);
        }else{
            return null;
        }

    }

    @Override
    public List<Station> getStations() {
        return stationRepository.findAll();
    }
*/

    @Override
    public StorageSpace getById(Integer index) {



        return storageSpaceRepository.findById(index).get();
    }

   /* @Override
    public Map getLocation(Station station) {


        Map map = new HashMap<>();

        map.put("longitude",station.getLongitude());
        map.put("latitude",station.getLatitude());
        return map;

    }

    @Override
    @Transactional
    public Station updateLongitudeLatitude(Station station, String longitude, String latitude) {
        station.setLongitude(longitude);
        station.setLatitude(latitude);
        return stationRepository.save(station);
    }

    @Override
    public List<Station> getByIds(List<Integer> ids) {
        return stationRepository.findByIdIn(ids);


    }
*/
    @Override
    public List<StorageSpace> getAll() {
        return storageSpaceRepository.findAll();
    }

    @Override
    public Page<Map> query(Object o, Pageable pageable) {


        Page<StorageSpace> pages = storageSpaceRepository.findAll(pageable);

        Page<Map> page = pages.map(new Function<StorageSpace, Map>() {
            public Map apply(StorageSpace objectEntity) {

                ObjectMapper m = new ObjectMapper();
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                mappedObject.put("createDate",objectEntity.getCreateDate());

                String url = linkTo(methodOn(MobileStorageController.class).detail(objectEntity.getId(), null,null)).withSelfRel().getHref();

                mappedObject.put("url",url);

                String reportUrl = linkTo(methodOn(MobileReportController.class).index(objectEntity.getNo(),null)).withSelfRel().getHref();

                mappedObject.put("reportUrl",reportUrl);


                String scanUrl = linkTo(methodOn(MobileDeliveryOrderScanController.class).index(objectEntity.getNo(),null)).withSelfRel().getHref();
                mappedObject.put("scanUrl",scanUrl);
                return mappedObject;
            }
        });
        return page;

    }

    @Override
    public Map get(StorageSpace deliveryOrder) {
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> mappedObject = m.convertValue(deliveryOrder,Map.class);

        return mappedObject;
    }


    @Override
    @Transactional
    public void createFromMap( List<Map> inventory_mapS) {




        List<StorageSpace> storageSpaces_ = storageSpaceRepository.findAll();
        Map<String,StorageSpace> storages = storageSpaces_.stream().collect(Collectors.toMap(StorageSpace::getNo,e->e));
        List<String> list = new ArrayList<>(storages.keySet());



        List<StorageSpace> new_storage = new ArrayList<>();
        for(Map inventory_map: inventory_mapS){
            String no = (String)inventory_map.get("no");

            if(storages.keySet().contains(no)){

                    String name = (String)inventory_map.get("name");
                    StorageSpace storageSpace = storages.get(no);
                    storageSpace.setName(name);
                storageSpace.setStatus(SynchronizeEnum.Been_synchronized.getText());
                list.remove(no);
            }else{
                String name = (String)inventory_map.get("name");
                StorageSpace storageSpace = new StorageSpace();
                storageSpace.setNo(no);
                storageSpace.setName(name);

                storageSpace.setStatus(SynchronizeEnum.Been_synchronized.getText());

                new_storage.add(storageSpace);
            }


        }

        for(String storageSpaceNo:list){
            StorageSpace storageSpace = storages.get(storageSpaceNo);
            storageSpace.setStatus("invalid");
            storageSpaceRepository.save(storageSpace);
        }



         storageSpaceRepository.saveAll(new_storage);
    }

    @Override
    public void liveInfo(StorageSpace objectEntity, Map<String, Object> mappedObject) {
        mappedObject.put("live_",1);
        mappedObject.put("live_",1);
        mappedObject.put("live_",1);
        mappedObject.put("live_",1);
        mappedObject.put("live_",1);
        mappedObject.put("live_",1);
        mappedObject.put("live_",1);
    }

}
