package com.coalvalue.repository;


import com.coalvalue.domain.entity.PriceCategory;
import com.coalvalue.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByItemIdAndItemType(Integer productId, String productType);


    Optional<Product> findById(Integer productId);

    List<Product> findByCompanyId(Integer id);

    Page<Product> findByCompanyId(Integer id, Pageable pageRequest);

    List<Product> findByCompanyIdAndStatus(Integer id, String text);

    List<Product> findByCompanyIdIn(List<Integer> companyIds);

    List<Product> findByCompanyIdInAndStatus(List<Integer> companyIds, String text);

    List<Product> findByCompanyIdAndStatusIn(Integer id, List<String> strings);

    List<Product> findByGranularityAndStatus(String text, String text1);

    List<Product> findByCoalTypeAndGranularityAndStatus(String text, String text1, String text2);


    Page<Product> findByCompanyIdAndStatus(Integer id, String text, Pageable pageRequest);

    Product findByNo(String no);

    List<String> findUuidBy();

    Product findByUuid(String objectUuid);

    List<Product> findByUuidIn(List<String> uuids);
}
