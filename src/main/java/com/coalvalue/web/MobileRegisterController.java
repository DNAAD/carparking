package com.coalvalue.web;


import com.coalvalue.domain.OperationResult;

import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.domain.entity.User;
import com.coalvalue.repository.UserRepository;
import com.coalvalue.service.ConfigurationService;
import com.coalvalue.service.ConfigurationServiceImpl;
import com.coalvalue.service.UserService;

import com.coalvalue.web.valid.UserCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import com.coalvalue.web.validation.UserCreateFormValidator;

/**
 * Created by yunpxu on 1/7/2015.
 * Step 1: Login flow
 * Step 2: Register flow
 * Step 3: Reset password flow
 */
@Controller
@RequestMapping("/mobile/register")
public class MobileRegisterController extends BaseController {



    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConfigurationService configurationService;




    @Autowired
    private UserRepository userRepository;






    //Step 1.1: render login UI
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(Authentication authentication,
                               @RequestParam(value = "X-Auth-Username", required = false) String userName,
                               @RequestParam(value = "X-Auth-Password", required = false) String password,
                               RedirectAttributes redirectAttributes,

                               Map<String, Object> model) {
        try {
            BigDecimal a = null;
        } catch (Exception ex) {

        }

        redirectAttributes.addFlashAttribute("email", userName);
        redirectAttributes.addFlashAttribute("password", password);
        redirectAttributes.addFlashAttribute("redirectUrl", "");


        return "redirectUrl:/user/userLogin";
    }






    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView checkRegisterPage(@ModelAttribute UserCreateForm userCreateForm, @RequestParam(value = "openId", required = false) String openId) {

        ModelAndView modelAndView = new ModelAndView("/register");


        String registerUserUrl =   linkTo(methodOn(MobileRegisterController.class).postUser(null, null)).withSelfRel().getHref();
        System.out.println("===================={}"+registerUserUrl);
        modelAndView.addObject("registerUserUrl", registerUserUrl);


        return modelAndView;
    }








    //Step 2.4: register user and set users' password with form data.
    @RequestMapping(value = "/user/registerUser", method = RequestMethod.POST)
    @ResponseBody
    public Object postUser( @ModelAttribute UserCreateForm userCreateForm, BindingResult bindingResult) {


        User user = userService.create(userCreateForm);


            HashMap hashMap = new HashMap();
            hashMap.put("status", true);
            hashMap.put("message", "注册成功");

            return hashMap;


    }








    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]{5}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }



    //Step 1.1: render login UI
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login( @RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("/login");


        if (error != null) {
            modelAndView.addObject("error", "Invalid username and password!");
        }
        Configuration configuration = configurationService.getConfiguration(ConfigurationServiceImpl.PRODUCER_CONFIGURATION_KEY_companyName);



        String registeUrl = linkTo(methodOn(MobileRegisterController.class).checkRegisterPage(null, null)).withSelfRel().getHref();
        modelAndView.addObject("registeUrl", registeUrl);

        if(configuration!= null){
            modelAndView.addObject("companyName", configuration.getStringValue());
        }

        return modelAndView;
    }



    //Step 1.2: Check login email and password
    @RequestMapping(value = "/user/userLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object userLogin(@RequestParam(value = "email",required = false) String email,
                            @RequestParam(value = "password",required = false) String pwd,
                            @RequestParam(value = "redirectUrl",required = false) String redirectUrl,
                            @RequestParam(value = "return_to", required = false) String returnTo,
                            @RequestParam(value = "resource_id", required = false) String resourceId,

                            @RequestParam(value = "captcha_login",required = false) String captcha_login,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes,
                            HttpServletResponse response,
                            @RequestParam(value = "scene", required = false)String scene,
                            @RequestParam(value = "variable", required = false)Integer variable) {

        logger.debug("----------we get redirectUrl  is:{}", redirectUrl);
        logger.debug("----------we get return to is:{}", returnTo);
        logger.debug("----------we get scene is {}", scene);

        logger.debug("----------we get variable is {}", variable);


        HttpSession session = request.getSession();

        OperationResult operationResult = null;
/*        try{
            operationResult = null;//mobileService.userLogin(email, pwd,request,response, false);

        } catch (InternalAuthenticationServiceException e){
            redirectAttributes.addFlashAttribute("msg", getMessage("LOGIN.ERROR"));

        }*/



        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();


        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
            logger.debug("--authectioanton 登录成功");
            String urlTo=null;// linkTo(methodOn(UserCenterController.class).dashboard("",null)).withSelfRel().getHref();

            return  "redirect:" + urlTo;


        }else{
            logger.debug("---------authectioanton 失败失败:{}");

        }

        String ret = "redirect:/homepage/user/login";


        return ret;
    }

}
