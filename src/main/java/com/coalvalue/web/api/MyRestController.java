package com.coalvalue.web.api;


import com.coalvalue.web.MobileReportController;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class MyRestController {


/*
    @Autowired
    private ApplicantRepository applicantRepository;
*/

    @RequestMapping(value="/start-hire-process", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public void startHireProcess(@RequestBody Map<String, String> data) {
/*
        Applicant applicant = new Applicant(data.get("name"), data.get("email"), data.get("phoneNumber"));
        applicantRepository.save(applicant);

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("applicant", applicant);
        runtimeService.startProcessInstanceByKey("hireProcessWithJpa", variables);*/
    }



    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> info( @RequestParam(value = "id", required = false) Integer id,@RequestParam(value = "longitudeLatitude",required = false) String longitudeLatitude, Authentication authentication) {



        Map<String, Object> ret = new LinkedHashMap<String, Object>();


        String url = ControllerLinkBuilder.linkTo(methodOn(MobileReportController.class).index("STORAGE00000033",null)).withSelfRel().getHref();
        ret.put("url",url);


        ret.put("code", "0");
        return ret;
    }

}