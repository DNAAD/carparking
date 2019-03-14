package com.coalvalue.repository;


import com.coalvalue.domain.entity.SyncProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface SynchronizedRepository {


    SyncProperties findByNo(String synchronizedNo);

    List<SyncProperties> findBySyncStatus(String text);

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    SyncProperties findByObjectUuid(String uuid);


}
