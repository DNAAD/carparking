package com.coalvalue.web;

import com.coalvalue.domain.OperationResult;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.domain.entity.User;
import com.coalvalue.repository.StorageSpaceRepository;
import com.coalvalue.service.PreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.security.core.Authentication;

//import com.coalvalue.repositorySecondary.ProductRepository;

/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/security"})
public class SecurityController {
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

        @Autowired
    private StorageSpaceRepository storageSpaceRepository;
    @Autowired
    private PreferenceService preferenceService;



    @RequestMapping(value = "/storage_select",method = RequestMethod.GET)
    public ModelAndView storage_select(HttpServletRequest request, Authentication authentication) {

        User user = (User) authentication.getPrincipal();


        String ret = "/select_storage";
        ModelAndView modelAndView = new ModelAndView(ret);

        String storageSearchUrl =   linkTo(methodOn(SecurityController.class).storages(null, null, null,null)).toUri().toString();
        modelAndView.addObject("storageSearchUrl",storageSearchUrl);




        String changeDefaultStorageUrl =   linkTo(methodOn(SecurityController.class).changeDefaultStorageUrl(null, null)).toUri().toString();
        modelAndView.addObject("changeDefaultStorageUrl",changeDefaultStorageUrl);

        return modelAndView;

    }

    @RequestMapping(value = "storages", method = RequestMethod.GET)
    @ResponseBody
    public Page<StorageSpace> storages(@RequestParam(value = "q", required = false) String searchTerm,
                                       @RequestParam(value = "producerId", required = false) String producerId,
                                       @PageableDefault Pageable pageable,
                                       Authentication authentication)  {

        User user = (User)authentication.getPrincipal();

        return storageSpaceRepository.findAll(pageable);
    }

    @RequestMapping(value = "/changeDefaultStorage", method = RequestMethod.POST)
    @ResponseBody

    public Map changeDefaultStorageUrl(@RequestParam(value = "uuid", required = false) String uuid, Authentication authentication) {

        logger.debug("----- param is  id : {}",uuid);

        User user = (User)authentication.getPrincipal();

        Map ret = new HashMap<String, String>();
        ret.put("status", false);

        StorageSpace storage = storageSpaceRepository.findByUuid(uuid);
        OperationResult location = preferenceService.changeDefaultStorage(storage,user);
       if(location != null){
           String distributorUrl =   linkTo(methodOn(SynthesizedController.class).index(null,null)).toUri().toString();
            ret.put("link", distributorUrl);

            ret.put("status", true);
        }else{

           ret.put("status", false);
       }



        return ret;

    }


}
