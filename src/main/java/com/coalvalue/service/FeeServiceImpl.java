package com.coalvalue.service;


import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.*;
import com.coalvalue.enumType.*;
import com.coalvalue.repository.AdvancedPaymentTransferRepository;
import com.coalvalue.repository.FeeRepository;
import com.coalvalue.repository.InventoryTransferRepository;
import com.coalvalue.repository.PriceCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("feeService")
public class FeeServiceImpl extends BaseServiceImpl implements FeeService {



    @Autowired
    private FeeRepository feeRepository;
    @Autowired
    private AdvancedPaymentTransferRepository advancedPaymentTransferRepository;
    @Autowired
    private SyncService syncService;

    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;


    @Autowired
    private PriceCategoryRepository priceCategoryRepository;

    @Override
    public void createFeesForOrder(Distributor coalOrder, PricingStrategyEnum pricingStrategyEnum) {

        Fee fee = new Fee();
        fee.setDistributorNo(coalOrder.getNo());
        fee.setType(FeesTypeEnum.Commodity_price.getText());
        if(pricingStrategyEnum.getText().equals(PricingStrategyEnum.SpotPrice.getText())){
            fee.setPricingStrategy(pricingStrategyEnum.getText());
            fee.setStatus(FeeStatusEnum.Effective.getText());

        }
        //fee.setTax(TaxInclusiveExclusiveEnum.Inclusive.getText());
        feeRepository.save(fee);


        fee = new Fee();
        fee.setDistributorNo(coalOrder.getNo());
        fee.setType(FeesTypeEnum.Service_charge.getText());
        fee.setStatus(FeeStatusEnum.NotEffective.getText());
        fee.setTax(TaxInclusiveExclusiveEnum.Exclusive.getText());

        feeRepository.save(fee);

    }

    @Override
    public Page<Map> queryFess(Distributor coalOrder, Pageable pageable) {

        return queryFess(coalOrder.getNo(),pageable);
    }

    @Override
    public Page<Map> queryFess(String coalOrder, Pageable pageable) {
        Page<Fee> pages = feeRepository.findByDistributorNo(coalOrder, pageable);
        ObjectMapper m = new ObjectMapper();

        Page<Map> page = pages.map(new Function<Fee, Map>() {
            public Map apply(Fee objectEntity) {
                Map<String,Object> mappedObject = m.convertValue(objectEntity,Map.class);

                mappedObject.put("type",FeesTypeEnum.fromString(objectEntity.getType()).getDisplayText());

                if(objectEntity.getTax()!= null){
                    mappedObject.put("tax",TaxInclusiveExclusiveEnum.fromString(objectEntity.getTax()).getDisplayText());
                }
                mappedObject.put("status",FeeStatusEnum.fromString(objectEntity.getStatus()).getDisplayText());
                mappedObject.put("createDate",objectEntity.getCreateDate());
                return mappedObject;
            }
        });
        return page;
    }
    @Override
    public OperationResult complete(Fee fee, BigDecimal amount, String tax) {
        if(tax!= null){

            fee.setTax(TaxInclusiveExclusiveEnum.fromString(tax).getText());
        }
        fee.setAmount(amount);
        OperationResult operationResult = new OperationResult();
        fee.setStatus(FeeStatusEnum.Effective.getText());
        fee = feeRepository.save(fee);
        operationResult.setSuccess(true);
        operationResult.setResultObject(fee);
        return operationResult;
    }

    @Override
    public boolean isEffective(String orderNo) {
        List<Fee> isExsit = feeRepository.findByDistributorNoAndStatus(orderNo,FeeStatusEnum.NotEffective.getText());
        return isExsit.size() ==0;
    }

    @Override
    @Transactional
    public OperationResult addFee(Distributor coalOrder, FeesTypeEnum feesTypeEnum) {
        Fee isExsit = feeRepository.findByDistributorNoAndType(coalOrder.getNo(),feesTypeEnum.getText());
        if(isExsit == null){
            isExsit = new Fee();
         //   isExsit.setOrderNo(coalOrder.getCompanyNo());
            isExsit.setType(feesTypeEnum.getText());
            isExsit.setStatus(FeeStatusEnum.NotEffective.getText());
            isExsit = feeRepository.save(isExsit);
        }
        OperationResult operationResult = new OperationResult();
        operationResult.setSuccess(true);
        operationResult.setResultObject(isExsit);
        return operationResult;
    }

    @Override
    public Fee syncAddFee(Fee fee_remote) {
        Fee fee = feeRepository.findByNo(fee_remote.getNo());
        if(fee== null){
            fee = new Fee();
            fee.setNo(fee_remote.getNo());
            fee.setType(fee_remote.getType());

            fee.setDistributorNo(fee_remote.getDistributorNo());

        }
        fee.setStatus(fee_remote.getStatus());
        fee.setAmount(fee_remote.getAmount());
        fee.setDiscount(fee_remote.getDiscount());
        fee.setPricingStrategy(fee_remote.getPricingStrategy());
        fee.setTax(fee_remote.getTax());
        fee = feeRepository.save(fee);
        return fee;
    }

    @Override
    public void addFee(InventoryTransfer inventoryTransfer, Distributor distributor, InstanceTransport instanceTransport, Inventory inventory){

        AdvancedPaymentTransfer advancedPaymentTransfer = new AdvancedPaymentTransfer();
        advancedPaymentTransfer.setAmount(inventoryTransfer.getAmount());

        advancedPaymentTransfer.setDistributorNo(distributor.getNo());
        advancedPaymentTransfer.setDebitCreditType(FinancialConstants.DEBIT.getValue());
        advancedPaymentTransfer.setInstanceId(instanceTransport.getId());
        advancedPaymentTransfer.setInventoryNo(inventory.getNo());

        advancedPaymentTransfer.setSyncStatus(SynchronizeEnum.SyncPending.getText());
        //advancedPaymentTransfer.setBalance(distributor.getAdvancedPaymentAmount().subtract(advancedPaymentTransfer.getAmount()));

        AdvancedPaymentTransfer advancedPaymentTransfer_last = advancedPaymentTransferRepository.findTop1ByDistributorNoAndSyncStatusOrderByCreateDateDesc(distributor.getNo(),SynchronizeEnum.Been_synchronized.getText());

/*        if(advancedPaymentTransfer_last== null){
            advancedPaymentTransfer.setBalance(advancedPaymentTransfer.getAmount().multiply(new BigDecimal(-1)));

        }else{
            advancedPaymentTransfer.setBalance(advancedPaymentTransfer_last.getBalance().subtract(advancedPaymentTransfer.getAmount()));


        }*/

        advancedPaymentTransfer = advancedPaymentTransferRepository.save(advancedPaymentTransfer);

       // syncService.createPrepayments(advancedPaymentTransfer);



        List<Fee> feees = feeRepository.findByDistributorNo(distributor.getNo());


        Optional<Fee> feeOptional_discount = feees.stream().filter(e->e.getType().equals(FeesTypeEnum.Discount.getText())).findAny();
        BigDecimal discount = new BigDecimal(0);
        if(feeOptional_discount.isPresent()){
            Fee fee= feeOptional_discount.get();
            if(fee.getStatus().equals(FeeStatusEnum.Effective.getText())){

                discount = inventoryTransfer.getWeight().multiply(fee.getAmount());


            }
        }


        Optional<Fee> feeOptional = feees.stream().filter(e->e.getType().equals(FeesTypeEnum.Commodity_price.getText())).findAny();
        Boolean check = false;

        BigDecimal commodity_price = new BigDecimal(0);
        BigDecimal taxAmount = new BigDecimal(0);

        if(feeOptional.isPresent()){
            Fee fee= feeOptional.get();
            if(fee.getStatus().equals(FeeStatusEnum.Effective.getText())){
                if(fee.getPricingStrategy().equals(PricingStrategyEnum.SpotPrice.getText())){


                    List<PriceCategory> priceCategories = priceCategoryRepository.findByProductNo(inventory.getProductNo());

                    if(fee.getTax().equals(TaxInclusiveExclusiveEnum.Inclusive.getText())){
                        Optional<PriceCategory> priceCategory_optional = priceCategories.stream().filter(e-> e.getName().equals(PriceCategoryTypeEnum.EX_MINE_PRICE.getText())).findFirst();
                        if(priceCategory_optional.isPresent()){

                            System.out.println("======================Inclusive"+priceCategory_optional.get().getValue());
                            commodity_price = inventoryTransfer.getWeight().multiply(priceCategory_optional.get().getValue());
                            inventoryTransfer.setUnitPrice(priceCategory_optional.get().getValue());
                            taxAmount = commodity_price.add(taxAmount);


                            inventoryTransfer.setDiscount(discount);
                            inventoryTransfer.setAmount(commodity_price.subtract(discount));
                            inventoryTransfer.setTaxAmount(taxAmount);
                            inventoryTransfer.setStatus(FinancialStatusEnum.checked.getText());
                        }

                        inventoryTransfer.setPricingStrategy(PricingStrategyEnum.SpotPrice.getText());

                        inventoryTransfer.setTax(TaxInclusiveExclusiveEnum.Inclusive.getText());

                        check = true;
                    }

                    if(fee.getTax().equals(TaxInclusiveExclusiveEnum.Exclusive.getText())){
                        Optional<PriceCategory> priceCategory_optional = priceCategories.stream().filter(e-> e.getName().equals(PriceCategoryTypeEnum.EX_MINE_PRICE_EXC_VAT.getText())).findFirst();
                        if(priceCategory_optional.isPresent()){
                            System.out.println("======================Exclusive"+priceCategory_optional.get().getValue());
                            commodity_price = inventoryTransfer.getWeight().multiply(priceCategory_optional.get().getValue());
                            inventoryTransfer.setUnitPrice(priceCategory_optional.get().getValue());

                            inventoryTransfer.setDiscount(discount);
                            inventoryTransfer.setAmount(commodity_price.subtract(discount));

                            inventoryTransfer.setStatus(FinancialStatusEnum.checked.getText());


                        }
                        inventoryTransfer.setTax(TaxInclusiveExclusiveEnum.Exclusive.getText());
                        inventoryTransfer.setPricingStrategy(PricingStrategyEnum.SpotPrice.getText());
                        check = true;
                    }


                }

                if(fee.getPricingStrategy().equals(PricingStrategyEnum.LongTermSupplyPrice.getText())){
                    inventoryTransfer.setUnitPrice(fee.getAmount());
                    commodity_price = inventoryTransfer.getWeight().multiply(fee.getAmount());
                    inventoryTransfer.setPricingStrategy(PricingStrategyEnum.LongTermSupplyPrice.getText());
                    check = true;
                }




            }

        }

        if(check){




        }else{
            inventoryTransfer.setDiscount(discount);
            inventoryTransfer.setStatus(FinancialStatusEnum.unsettled.getText());
        }

        inventoryTransfer.setReconcileStatus(ReconcileStatusEnum.NoReconciliation.getText());
        inventoryTransferRepository.save(inventoryTransfer);
    }

    @Override
    public PriceCategory getPriceCategory(Fee fee, String productNo) {


        List<PriceCategory> priceCategories = priceCategoryRepository.findByProductNoOrderBySeqAsc(productNo);

        return priceCategories.get(0);
    }

}
