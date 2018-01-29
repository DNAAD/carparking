package com.coalvalue.service;

import com.coalvalue.domain.entity.NoGenerator;
import com.coalvalue.enumType.NoTypeEnum;
import com.coalvalue.repository.NoGeneratorRepository;
import com.domain.entity.User;
import com.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by silence yuan on 2015/7/25.
 */

@Service("noGeneratorService")
public class NoGeneratorServiceImpl extends BaseServiceImpl implements NoGeneratorService {

    @Autowired
    private NoGeneratorRepository noGeneratorRepository;



    @Override
    public NoGenerator getOrderNo(Integer id) {
        return  get(id,NoTypeEnum.ORDER);
    }


    @Transactional
    NoGenerator get(Integer companyId, NoTypeEnum type){
        NoGenerator noGenerator = noGeneratorRepository.findByCompanyIdAndTypeName(companyId,type.getText());

        if(noGenerator == null){

            noGenerator = new NoGenerator();
            noGenerator.setTypeName(type.getText());
            noGenerator.setCompanyId(companyId);
            noGenerator.setValue(1);
            noGenerator.setOrderNo(0);
            noGenerator.setShipmentNo(0);
            noGenerator = noGeneratorRepository.save(noGenerator);
        }

        return noGenerator;
    }
}
