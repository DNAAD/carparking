package com.coalvalue.repository.specific;


import com.coalvalue.domain.entity.InventoryTransfer;
import com.coalvalue.dto.InventoryTransferDto;
import com.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao yuan on 26/07/2015.
 */
public class InventoryTransferSpec {

    private InventoryTransferDto bankCardDto;

    public InventoryTransferSpec(InventoryTransferDto bankCardDto) {
        this.bankCardDto = bankCardDto;
    }

    public Specification<InventoryTransfer> querySynthesizedSpec() {


        Specification<InventoryTransfer> specification = (root, query, cb) -> {


            List<Predicate> predicateList = new ArrayList<>();



            if (bankCardDto.getReconcileStatus()!= null) {

                Predicate predicate_pro = cb.equal(root.<String>get("reconcileStatus"), bankCardDto.getReconcileStatus());
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
