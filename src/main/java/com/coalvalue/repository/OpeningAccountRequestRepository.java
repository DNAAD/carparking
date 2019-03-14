package com.coalvalue.repository;



import com.coalvalue.domain.entity.OpeningAccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface OpeningAccountRequestRepository extends JpaRepository<OpeningAccountRequest, Integer> {



    OpeningAccountRequest findByUuid(String objectUuid);

}
