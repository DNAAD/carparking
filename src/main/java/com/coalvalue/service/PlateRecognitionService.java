package com.coalvalue.service;

//import com.LPR;
import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.Equipment;
import com.coalvalue.domain.entity.Location;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.web.valid.PlateRecognitionCreateForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by silence yuan on 2015/7/25.
 */
public interface PlateRecognitionService extends BaseService {



    OperationResult record(Equipment equipment, PlateRecognitionCreateForm canvassingCreateForm_);

 //   PlateRecognition createPlateRecognition(LPR.TH_PlateResult_Pointer.ByReference plateRecognition, String path_SHOW);


  //  void createPlateRecognition(LPR.TH_PlateResult_Pointer.ByReference pResult);


    Page<PlateRecognition> query(Object o, Pageable pageable);


    void analyis(PlateRecognition plateRecognition);


}
