package com.coalvalue.service;



import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.BrandEnterpriseFeatureEnum;


import com.coalvalue.repository.CompanyRepository;
import com.coalvalue.repository.LiveInforInventoryRepository;
import com.coalvalue.repository.StorageSpaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("liveInformationService")
public class LiveInformationServiceImpl extends BaseServiceImpl implements LiveInformationService {




    @Autowired
    private LiveInforInventoryRepository liveInforInventoryRepository;
    @Autowired
    private StorageSpaceRepository storageSpaceRepository;


    @Autowired
    private ConfigurationService configurationService;


    @Autowired
    private CompanyRepository companyRepository;



    @Override
    public List<LiveInforInventory> getInventory() {
        return liveInforInventoryRepository.findAll();
    }

    @Override
    @Transactional
    public void update(Inventory inventory, InstanceTransport instanceTransport) {
        LiveInforInventory liveInforInventory = liveInforInventoryRepository.findByInventoryNo(inventory.getNo());
        if(liveInforInventory == null){
            liveInforInventory = createNewLiveInforInventory(inventory.getStorageNo(),inventory.getNo());

        }
        liveInforInventory.setLoadingCount(liveInforInventory.getLoadingCount()+1);

        liveInforInventory = liveInforInventoryRepository.save(liveInforInventory);
    }

    @Override
    @Transactional
    public void update(Inventory inventory, ReportDeliveryOrder reportDeliveryOrder) {
        LiveInforInventory liveInforInventory = liveInforInventoryRepository.findByInventoryNo(inventory.getNo());
        if(liveInforInventory == null){
            //liveInforInventory = createNewLiveInforInventory(inventory.getStorageId(),inventory.getNo());
        }


        liveInforInventory.setWaitingCount(liveInforInventory.getWaitingCount()+1);
        liveInforInventory = liveInforInventoryRepository.save(liveInforInventory);
    }

    @Override
    public LiveInforInventory getInventoryByNo(String no) {
        return liveInforInventoryRepository.findByInventoryNo(no);
    }


    private LiveInforInventory createNewLiveInforInventory(String storageId, String inventoryNo){
        LiveInforInventory liveInforInventory = new LiveInforInventory();


        Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyNo);
        liveInforInventory.setCompanyNo(configuration.getStringValue());
        liveInforInventory.setWaitingCount(0);
        liveInforInventory.setLoadingCount(0);
        /*
        StorageSpace storageSpace = storageSpaceRepository.findById(storageId).get();

        liveInforInventory.setStorageNo(storageSpace.getNo());

        liveInforInventory.setInventoryNo(inventoryNo);


        liveInforInventory.setLoadingCount(0);
        liveInforInventory.setAverageWaitingTime(new BigDecimal(0));
        liveInforInventory.setAverageLaytime(new BigDecimal(0));
        liveInforInventory.setWaitingCount(0);

        liveInforInventory = liveInforInventoryRepository.save(liveInforInventory);*/
        return liveInforInventory;
    }


/*    public List<BrandEnterprise> findAll() {
        return brandEnterpriseRepository.findAll(sortByIdAsc());
    }

    private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
    }*/
}
