package com.coalvalue.repository;


import com.coalvalue.domain.TemporaryQrcode;
import com.coalvalue.domain.entity.WxTemporaryQrcode;
import com.coalvalue.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface TemporaryQrcodeRepository extends BaseJpaRepository<TemporaryQrcode, Integer> {



    WxTemporaryQrcode findById(Integer id);



    TemporaryQrcode findByUniqueId(String u);


}
