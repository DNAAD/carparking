package com.coalvalue.service;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.*;
import com.coalvalue.repository.InventoryRepository;
import com.coalvalue.repository.InventoryTransferRepository;
import com.coalvalue.repository.PriceCategoryRepository;
import com.coalvalue.repository.ProductRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

//import org.springframework.security.core.Authentication;

/**
 * Created by zohu on 3/20/2015.
 */
@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PriceCategoryRepository priceCategoryRepository;



    @Autowired
    CompanyService companyService;



    @Autowired
    InventoryRepository inventoryRepository;


    @Autowired
    InventoryTransferRepository inventoryTransferRepository;






    @Override
    public Product getById(Integer productId) {
        Product product = productRepository.findById(productId).get();


        return product;
    }








    @Override
    public List<Product> queryProductsByCompany(Company company, CoalSupplyStatusEnum released) {

        return productRepository.findByCompanyIdAndStatus(company.getId(),released.getText());
    }

    @Override
    public Page<Product> queryProductsByCompany(Company company, CoalSupplyStatusEnum released, Pageable pageRequest) {
        return productRepository.findByCompanyIdAndStatus(company.getId(),released.getText(),pageRequest);
    }





    @Override
    @Transactional
    public PriceCategory createPriceCategory(PriceCategoryTypeEnum priceCategoryTypeEnum, Product product) {

        PriceCategory priceCategory = priceCategoryRepository.findByNameAndObjectUuidAndMajor(priceCategoryTypeEnum.getText(), product.getUuid(),true);
        if(priceCategory == null){
            priceCategory = new PriceCategory();

            priceCategory.setItemType(ResourceType.COAL_PRODUCT.getText());

            priceCategory.setName(priceCategoryTypeEnum.getText());
            priceCategory.setSeq(priceCategoryTypeEnum.getId());
            priceCategory.setMajor(true);
            priceCategory.setLastChangeTime(new Date());
            priceCategory = priceCategoryRepository.save(priceCategory);
        }
        return  priceCategory;
    }











    @Override
    public List<PriceCategory> getPriceCategory(Product product) {
        List<PriceCategory> priceCategories=  priceCategoryRepository.findByObjectUuid(product.getUuid());



        return priceCategories;
    }


    @Override
    public PriceCategory getPriceCategoryById(Integer productId) {
        return priceCategoryRepository.findById(productId).get();
    }






    @Override
    public List<Map<String, Object>> getPriceCategoryMap(Product product) {

        List<Map<String,Object>> maps = new ArrayList<>();

        for(PriceCategory priceCategory : getPriceCategory(product)){
            Map<String,Object> map = new HashMap<>();
            map.put("name",PriceCategoryTypeEnum.fromString(priceCategory.getName()).getDisplayText());

            map.put("lastChangeTime",priceCategory.getLastChangeTime());
            map.put("status",priceCategory.getStatus());
            map.put("value",priceCategory.getValue());
            maps.add(map);
        }
        return maps;
    }

    @Override
    public List<Product> getProductByCompanyId(Integer id) {
        List<Product> productTypes = productRepository.findByCompanyIdAndStatus(id,CoalSupplyStatusEnum.RELEASED.getText());

        return productTypes;

    }

    @Override
    public List<Product> getAllForInprocessCompany(List<Company> companies, CoalSupplyStatusEnum released) {




        List<Integer> companyIds = new ArrayList<>();
        for(Company company : companies){
            companyIds.add(company.getId());
        }
        List<Product> coalPromotion = productRepository.findByCompanyIdInAndStatus(companyIds,released.getText());
        return coalPromotion;
    }

    @Override
    public List<Product> getProductByCompanyId(Integer companyId, CoalSupplyStatusEnum released) {


        List<Product> coalPromotion = productRepository.findByCompanyIdAndStatus(companyId,released.getText());
        return coalPromotion;
    }



    @Override
    @Transactional
    public OperationResult changeQualityReport(Product product, QualityInspectionReport qualityInspectionReport, User user) {

        OperationResult operationResult = new OperationResult();

        if(qualityInspectionReport.getId().equals(product.getQualityReportId())){
            operationResult.setSuccess(false);
            operationResult.setResultMessage("相同的 quality report");
        }else {
            operationResult.setSuccess(true);
            product.setQualityReportId(qualityInspectionReport.getId());
            product = productRepository.save(product);
        }

        operationResult.setResultObject(product);
        return operationResult;
    }


    @Override
    public List<Product> getProductByCompanyId(Integer id, List<CoalSupplyStatusEnum> supplyStatusEnums) {


        List<String> strings = new ArrayList<>();
        for(CoalSupplyStatusEnum coalSupplyStatusEnum : supplyStatusEnums){
            strings.add(coalSupplyStatusEnum.getText());
        }

        return productRepository.findByCompanyIdAndStatusIn(id, strings);
    }

    @Override
    public PriceCategory getPriceCategory(Product product, PriceCategoryTypeEnum exMinePrice) {

        PriceCategory priceCategories=  priceCategoryRepository.findByObjectUuidAndName(product.getUuid(),exMinePrice.getText());

        return priceCategories;
    }









    @Override
    @Transactional
    public void updatePriceCategory(PriceCategory priceCategory) {
        priceCategoryRepository.save(priceCategory);
    }


}
