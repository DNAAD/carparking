package com.coalvalue.repository;


import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface InstanceTransportRepository extends BaseJpaRepository<InstanceTransport, Integer> {



    Page<InstanceTransport> findByCollaboratorId(Integer id, Pageable pageRequest);

    List<InstanceTransport> findByTimeIntervalIdAndCollaboratorId(Integer id, Integer id1);

    List<InstanceTransport> findByQueuingIdAndIdGreaterThanEqual(Integer id, Integer id1);

    List<InstanceTransport> findByQueuingIdAndTransportIdGreaterThanEqual(Integer id, Integer id1);

    List<InstanceTransport> findByQueuingIdAndTransportIdLessThanEqual(Integer id, Integer id1);

    List<InstanceTransport> findByQueuingIdAndTransportIdLessThanEqualAndStatus(Integer id, Integer id1, String text);

    InstanceTransport findByQueuingIdAndTransportId(Integer id, Integer id1);

    InstanceTransport findByTransportId(Integer id);

    Page<InstanceTransport> findByStorageId(Integer id, Pageable pageable);

    Page<InstanceTransport> findByStorageIdAndStatusIn(Integer id, List<String> statuses, Pageable pageable);

    List<InstanceTransport> findByQueuingIdAndIdLessThanEqualAndStatus(Integer queuingId, Integer id, String text);


    InstanceTransport findById(Integer id);

    Page<InstanceTransport> findByQueuingIdAndIdLessThanEqualAndStatus(Integer id, Integer id1, String text, Pageable pageable);

    List<InstanceTransport> findByQueuingIdAndStatus(Integer id, String status);


    List<InstanceTransport> findByStorageIdAndStatus(Integer id, String text);


    InstanceTransport findByDeliveryOrderId(Integer id);
}
