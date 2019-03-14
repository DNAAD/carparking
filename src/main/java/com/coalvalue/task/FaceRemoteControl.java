package com.coalvalue.task;

import com.coalvalue.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by silence yuan on 2015/7/25.
 */
@Service
public class FaceRemoteControl extends BaseServiceImpl {

    public static String FACE_PAGE_workbench = "FACE_PAGE_workbench";


    public void deal(ModelAndView modelAndView, String facePage) {
        if(FACE_PAGE_workbench.equals(facePage)){
            Map map = new HashMap();
            map.put("intelligentIdentification_tab", false);
            modelAndView.addObject("faceRemoteControl",map);

        }
    }
}
