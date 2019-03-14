package com.coalvalue.repository;


import com.coalvalue.domain.entity.AdvancedPaymentTransfer;
import com.coalvalue.domain.entity.LiveInforInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface LiveInforInventoryRepository extends JpaRepository<LiveInforInventory, Integer> {


    LiveInforInventory findByInventoryNo(String no);
}
