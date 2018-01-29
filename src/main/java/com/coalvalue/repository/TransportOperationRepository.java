package com.coalvalue.repository;

import com.coalvalue.domain.entity.TransportOperation;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface TransportOperationRepository extends BaseJpaRepository<TransportOperation, Integer> {


    Page<TransportOperation> findBySpaceId(Integer id, Pageable pageable);

    long countBySpaceIdAndStatus(Integer id, String transportOperationStatusCreate);

    Page<TransportOperation> findByCanvassingId(Integer id, Pageable pageable);

    Page<TransportOperation> findByOrderId(Integer id, Pageable pageable);

    List<TransportOperation> findByCanvassingIdAndStatus(Integer id, String text);

    List<TransportOperation> findByDriverIdAndSpaceIdAndStatus(Integer id, Integer id1, String text);

    Page<TransportOperation> findBySpaceIdAndStatus(Integer id, String text, Pageable pageable);

    Page<TransportOperation> findBySpaceIdAndStatusIn(Integer id, List<String> statuses, Pageable pageable);



    long countBySpaceIdAndStatusAndCreateDateBetween(Integer id, String text, Date start, Date end);

    long countBySpaceIdAndStatusIn(Integer id, List<String> statues);

    TransportOperation findById(Integer fromTransport);

    @Query("select sum(to.netWeight) from com.coalvalue.domain.entity.TransportOperation to where to.status = :status and to.spaceId = :spaceId and to.createDate between :start and :end" )
    BigDecimal getQuantity(@Param("status") String text, @Param("spaceId") Integer id, @Param("start") Date start, @Param("end") Date end);



    @Query("select sum(to.netWeight*to.unitPrice) from com.coalvalue.domain.entity.TransportOperation to where to.status = :status and to.spaceId = :spaceId and to.createDate between :start and :end" )
    BigDecimal getTotalAmount(@Param("status") String text, @Param("spaceId") Integer id, @Param("start") Date start, @Param("end") Date end);


    long countByStatusAndCreateDateBetween(String text, Date start, Date end);



    @Query("select sum(to.netWeight*to.unitPrice) from com.coalvalue.domain.entity.TransportOperation to where to.status = :status and to.spaceId in :spaceIds and to.createDate between :start and :end" )
    BigDecimal getTotalAmount(@Param("status") String text, @Param("spaceIds") List<Integer> ids, @Param("start") Date start, @Param("end") Date end);

    @Query("select sum(to.netWeight) from com.coalvalue.domain.entity.TransportOperation to where to.status = :status and to.spaceId in :spaceIds and to.createDate between :start and :end" )
    BigDecimal getQuantity(@Param("status") String text, @Param("spaceIds") List<Integer> ids, @Param("start") Date start, @Param("end") Date end);

    long countBySpaceIdInAndStatusAndCreateDateBetween(Object o, String text, Date start, Date end);

    List<TransportOperation> findByDriverIdAndSpaceIdInAndStatus(Integer id, List<Integer> storageIds, String text);

    List<TransportOperation> findBySpaceIdInAndStatus(List<Integer> storageIds, String text);

    Page<TransportOperation> findBySpaceIdAndStatusInAndCargoType(Integer id, List<String> statues, String granularity, Pageable pageable);


    Integer countBySpaceIdAndStatusInAndCargoType(Integer id, List<String> statues, String text);


    Integer countBySpaceIdInAndStatus(List<Integer> ids, String text);

    Page<TransportOperation> findByPartnerId(Integer id, Pageable pageable);

    Integer countBySpaceIdAndStatusInAndProductIdAndProductType(Integer id, List<String> statues, Integer id1, String text);

    Page<TransportOperation> findBySpaceIdAndStatusInAndProductIdAndProductType(Integer id, List<String> statues, Integer granularity, String text, Pageable pageable);

    Page<TransportOperation> findByOrderByCreateDateDesc(Pageable pageRequest);


    Page<TransportOperation> findByStatusOrderByCreateDateDesc(String status, Pageable pageRequest);


    Page<TransportOperation> findBySpaceIdAndStatusOrderByCreateDateDesc(Integer spaceId, String status, Pageable pageRequest);

    Page<TransportOperation> findBySpaceIdOrderByCreateDateDesc(Integer spaceId, Pageable pageRequest);


    Page<TransportOperation> findByOrderIdAndStatusOrderByCreateDateDesc(Integer spaceId, String status, Pageable pageRequest);

    Page<TransportOperation> findByOrderIdOrderByCreateDateDesc(Integer spaceId, Pageable pageRequest);

    List<TransportOperation> findByIdIn(List<Integer> ids);

    List<TransportOperation> findByIdInAndOrderId(List<Integer> ids, Integer id);

    Page<TransportOperation> findByIdIn(List<Integer> ids, Pageable pageRequest);

    TransportOperation findTop1ByCanvassingIdOrderByCreateDateDesc(Integer id);



}
