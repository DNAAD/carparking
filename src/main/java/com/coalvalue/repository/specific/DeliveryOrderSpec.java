package com.coalvalue.repository.specific;


import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.dto.DeliveryOrderDto;
import com.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao yuan on 26/07/2015.
 */
public class DeliveryOrderSpec {

    private DeliveryOrderDto deliveryOrderDto;

    public DeliveryOrderSpec(DeliveryOrderDto bankCardDto) {
        this.deliveryOrderDto = bankCardDto;
    }

    public Specification<ReportDeliveryOrder> queryMessagesSpec() {


        Specification<ReportDeliveryOrder> specification = (root, query, cb) -> {


            List<Predicate> predicateList = new ArrayList<>();





            if (!StringUtil.isEmpty(deliveryOrderDto.getSearchText())) {
              //  Predicate predicate = cb.like(root.<String>get("plateNumber"), "%"+bankCardDto.getSearchText().trim()+"%");
              //  predicateList.add(cb.or(predicate));
                Predicate predicate_pro = cb.like(root.<String>get("productName"), "%"+ deliveryOrderDto.getSearchText().trim()+"%");
                predicateList.add(cb.or(predicate_pro));

                return cb.or(predicateList.toArray(new Predicate[0]));
            }


/*            if(bankCardDto.getAccountName() != null) {
                Predicate  predicate = cb.equal(root.<Integer>get("accountName"), bankCardDto.getAccountName());
                predicateList.add(predicate);
            }
            if(bankCardDto.getAccountType() != null) {
                Predicate  predicate = cb.equal(root.<Integer>get("accountType"), bankCardDto.getAccountType());
                predicateList.add(predicate);
            }*/
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
        return specification;
    }

    public Specification<ReportDeliveryOrder> querySynthesizedSpec() {


        Specification<ReportDeliveryOrder> specification = (root, query, cb) -> {


            List<Predicate> predicateList = new ArrayList<>();

            if (deliveryOrderDto.getStorageNo()!= null) {
                //  Predicate predicate = cb.like(root.<String>get("plateNumber"), "%"+bankCardDto.getSearchText().trim()+"%");
                //  predicateList.add(cb.or(predicate));
                Predicate predicate_pro = cb.equal(root.<String>get("storageNo"), deliveryOrderDto.getStorageNo());
                predicateList.add(cb.or(predicate_pro));


            }

            if (!StringUtil.isEmpty(deliveryOrderDto.getStatus())) {
                //  Predicate predicate = cb.like(root.<String>get("plateNumber"), "%"+bankCardDto.getSearchText().trim()+"%");
                //  predicateList.add(cb.or(predicate));
                Predicate predicate_pro = cb.equal(root.<String>get("status"), deliveryOrderDto.getStatus());
                predicateList.add(cb.or(predicate_pro));


            }


            if (!StringUtil.isEmpty(deliveryOrderDto.getSearchText())) {
                List<Predicate> predicateList_OR = new ArrayList<Predicate>();

                //  Predicate predicate = cb.like(root.<String>get("plateNumber"), "%"+bankCardDto.getSearchText().trim()+"%");
                //  predicateList.add(cb.or(predicate));
                Predicate predicate_pro = cb.like(root.<String>get("productName"), "%"+ deliveryOrderDto.getSearchText().trim()+"%");
                Predicate predicate_idNumber = cb.like(root.<String>get("idNumber"), "%"+ deliveryOrderDto.getSearchText().trim()+"%");
                Predicate predicate_plateNumber = cb.like(root.<String>get("license"), "%"+ deliveryOrderDto.getSearchText().trim()+"%");
                predicateList_OR.add(cb.or(predicate_idNumber));
                predicateList_OR.add(cb.or(predicate_plateNumber));
                predicateList_OR.add(cb.or(predicate_pro));

                predicateList.add(cb.or(predicateList_OR.toArray(new Predicate[0])));
            }


            return cb.and(predicateList.toArray(new Predicate[0]));
        };
        return specification;
    }

    //Add by zhaoyuan 30/06/2015
    public static Specification<ReportDeliveryOrder> bankAccountsForSpecifcCompanySpec(Integer companyId) {
        Specification<ReportDeliveryOrder> specification = (root, query, cb) -> {
            return cb.equal(root.<String>get("corporationId"), companyId);
        };
        return specification;
    }
}
