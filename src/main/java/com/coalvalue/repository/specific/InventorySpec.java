package com.coalvalue.repository.specific;


import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.dto.DeliveryOrderDto;
import com.coalvalue.dto.InventoryDto;
import com.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao yuan on 26/07/2015.
 */
public class InventorySpec {

    private InventoryDto bankCardDto;

    public InventorySpec(InventoryDto bankCardDto) {
        this.bankCardDto = bankCardDto;
    }

    public Specification<Inventory> querySynthesizedSpec() {


        Specification<Inventory> specification = (root, query, cb) -> {


            List<Predicate> predicateList = new ArrayList<>();



            if (bankCardDto.getStorageNo()!= null) {

                Predicate predicate_pro = cb.equal(root.<String>get("storageNo"), bankCardDto.getStorageNo());
                predicateList.add(cb.or(predicate_pro));


            }

            if (bankCardDto.getStatus()!= null) {

                Predicate predicate_pro = cb.equal(root.<String>get("status"), bankCardDto.getStatus());
                predicateList.add(cb.or(predicate_pro));


            }
            if (!StringUtil.isEmpty(bankCardDto.getSearchText())) {
                List<Predicate> predicateList_OR = new ArrayList<Predicate>();

                //  Predicate predicate = cb.like(root.<String>get("plateNumber"), "%"+bankCardDto.getSearchText().trim()+"%");
                //  predicateList.add(cb.or(predicate));
                Predicate predicate_pro = cb.like(root.<String>get("productName"), "%"+ bankCardDto.getSearchText().trim()+"%");
                Predicate predicate_idNumber = cb.like(root.<String>get("idNumber"), "%"+ bankCardDto.getSearchText().trim()+"%");
                Predicate predicate_plateNumber = cb.like(root.<String>get("plateNumber"), "%"+ bankCardDto.getSearchText().trim()+"%");
                predicateList_OR.add(cb.or(predicate_idNumber));
                predicateList_OR.add(cb.or(predicate_plateNumber));
                predicateList_OR.add(cb.or(predicate_pro));

                predicateList.add(cb.or(predicateList_OR.toArray(new Predicate[0])));
            }


            return cb.and(predicateList.toArray(new Predicate[0]));
        };
        return specification;
    }


}
