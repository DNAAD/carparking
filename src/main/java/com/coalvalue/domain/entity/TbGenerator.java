
package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


import javax.persistence.Column;
import javax.persistence.Table;


import javax.persistence.Entity;

/**
 * Created by yuan zhao  on 08/10/2015.
 */

@Entity
@Table(name = "tb_generator")

public class TbGenerator extends BaseDomain {

    @Column(name = "gen_inventory_transfer_no")
    private String genInventoryTransferNo;

    public String getGenInventoryTransferNo() {
        return genInventoryTransferNo;
    }

    public void setGenInventoryTransferNo(String genInventoryTransferNo) {
        this.genInventoryTransferNo = genInventoryTransferNo;
    }

    public TbGenerator() {
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

;
}

