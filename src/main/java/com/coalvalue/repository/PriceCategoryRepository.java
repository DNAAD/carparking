package com.coalvalue.repository;

import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.PriceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Integer> {


    Optional<PriceCategory> findById(Integer priceId);


    PriceCategory findByUuid(String objectUuid);

    List<PriceCategory> findByProductNo(String productNo);

    List<PriceCategory> findByProductNoOrderBySeqAsc(String productNo);

    List<PriceCategory> findByUuidIn(List<String> uuids);

    PriceCategory findByNameAndObjectUuidAndMajor(String text, String uuid, boolean b);

    List<PriceCategory> findByObjectUuid(String uuid);

    PriceCategory findByObjectUuidAndName(String uuid, String text);
}
