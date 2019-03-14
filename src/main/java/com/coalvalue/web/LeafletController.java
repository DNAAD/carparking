package com.coalvalue.web;

import com.coalvalue.service.BehaviouralService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

//import org.springframework.security.core.Authentication;
//import com.coalvalue.repositorySecondary.ProductRepository;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
/**
 * Created by silence yuan on 2015/7/12.
 */

@Controller
@RequestMapping(value= {"/leaflet"})
public class LeafletController {
    private static final Logger logger = LoggerFactory.getLogger(LeafletController.class);


    @Autowired
    private BehaviouralService behaviouralService;



    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "q", required = false) String searchTerm,Authentication authentication)  {
        //  mqttService.sendNetworkStatus("OK");


        ModelAndView modelAndView = new ModelAndView("/leaflet/leaflet");
        String auto_trnx = linkTo(methodOn(LeafletController.class).auto_trnx(null,null)).toUri().toString();

        modelAndView.addObject("auto_trnx",auto_trnx);

        String twilio_trnx = linkTo(methodOn(LeafletController.class).twilio_trnx(null,null)).toUri().toString();

        modelAndView.addObject("twilio_trnx",twilio_trnx);
        return modelAndView;
    }






    @RequestMapping(value = "/auto_trnx", method = RequestMethod.GET)
    @ResponseBody
    public Map auto_trnx(
            @RequestParam(value = "verificationCode",required = false) String verificationCode,

            Authentication authentication) {
        logger.debug("verificationCode :{}", verificationCode);
        //User user = (User) authentication.getPrincipal();

        List<Output_twilio> outputList = new ArrayList<>();
        Output_auto output = new Output_auto();
        output.setDate("ddd");
        output.setFrom("ddd");
        output.setName("ddd");
        Geocode geocode = new Geocode();
        geocode.setLat("40.0568");
        Double lng_d = 116.30815 + Math.random()*100;

        geocode.setLng(lng_d+"");

        output.setGeocode(geocode);



        Map map = new HashMap();
        map.put("output", output);

        return map;

    }

    @RequestMapping(value = "/twilio_trnx", method = RequestMethod.GET)
    @ResponseBody
    public Map twilio_trnx(
            @RequestParam(value = "verificationCode",required = false) String verificationCode,
            Authentication authentication) {
        logger.debug("verificationCode :{}", verificationCode);
        //User user = (User) authentication.getPrincipal();

        List<Output_twilio> outputList = new ArrayList<>();
        Output_twilio output = new Output_twilio();
        output.setDate("ddd");
        output.setFrom("ddd");
        Geocode geocode = new Geocode();

        Double lat_d = 40.0568 + Math.random()*10;

        geocode.setLat(lat_d+"");

        geocode.setLng("116.30815");
        Body body = new Body();
        body.setGeocode(geocode);
        output.setBody(body);



        Map map = new HashMap();
        map.put("output", output);
        return map;


    }










        class Geocode {
        String lat;
        String lng;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }

    class Body {
        Geocode geocode;

        public Geocode getGeocode() {
            return geocode;
        }

        public void setGeocode(Geocode geocode) {
            this.geocode = geocode;
        }
    }
    class Output_twilio {
        String from;
        String date;

        Body body;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }
    class Output_auto {
        String from;
        String date;
        String name;


        Geocode geocode;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Geocode getGeocode() {
            return geocode;
        }

        public void setGeocode(Geocode geocode) {
            this.geocode = geocode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
