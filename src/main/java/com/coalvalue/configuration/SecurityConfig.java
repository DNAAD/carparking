package com.coalvalue.configuration;

import com.coalvalue.configuration.security.CustomHttp403ForbiddenEntryPoint;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }
   @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String [] publicUrls = new String [] {
                "/weixin/**",
                "/login",
                "/logout",
                "/api/v1/**",
                "/login.do/**",
                "/upload*/**",

                "/h2-console/login.do/**",


        };
        http.authorizeRequests().antMatchers("/home/createTransfer**",

               "/home/correctEdit**",
                "/report/statistic")
                .authenticated()


/*            .anyRequest()
            .permitAll()*/
                .antMatchers(/*"/**",*/
                        "/execute",
                        "/upload*/**",


                        "/welcome",
                        "/images/**",
                        "/focus/**",
                        "/newQuery",
                        "/info/**",

                        "/service/**",

                        "/accessDeniedPage.htm",
                        "/logo_header.png",
                        "/plateServlet*/**",
                        "/plate/register",
                        "/info",
                        "/configurationInfo",
                        "/h2-console/**",
                        "/login.do/**",

                        "/webapp.zip",
                        "/j_spring_security_check",
                        "/report/**",
                        "/plateServlet/register",
                        "/home/**",
                        "/favicon.ico",
                        "/amchart/**",
                        "/error",
                        "/front/**",
                        "/ws/**",
                        "/bootstrap/**",
                        "/build/**",
                        "/css/**",
                        "/dist/**",
                        "/documentation/**",
                        "/fonts/**",
                        "/js/**",
                        "/echo/**",

                        "/leaflet/**",

                        "/pages/**",
                        "/plugins/**","/usercenter/dashboard","/components/**","/logo_header", "/qrcode (2).png"
                        ,"/mobile/register/**", "/login**","/report/inventories/**","/report/index/**","/mobile*/**","/companies/**","/usercenter/order/**"
                ).permitAll() //默认不拦截静态资源的url pattern （2）


                .anyRequest()
                .authenticated()
            .and()
             //   .exceptionHandling().accessDeniedPage("/accessDeniedPage.htm")

             //   .accessDeniedHandler(accessDeniedHandler)

                .formLogin().loginPage("/mobile/register/login").loginProcessingUrl("/login").defaultSuccessUrl("/home").permitAll()


                /*.failureUrl("/mobile/register/login?error")*/
             /*   .failureHandler(new AuthenticationFailureHandler() {
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        StringBuffer sb = new StringBuffer();
                        sb.append("{\"status\":\"error\",\"msg\":\"");
                        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                            sb.append("用户名或密码输入错误，登录失败!");
                        } else if (e instanceof DisabledException) {
                            sb.append("账户被禁用，登录失败，请联系管理员!");
                        } else {
                            sb.append("登录失败!");
                        }
                        sb.append("\"}");
                        out.write(sb.toString());
                        out.flush();
                        out.close();
                    }
                })*/


                .and().logout()  //默认注销行为为logout，可以通过下面的方式来修改
            /*    .logoutUrl("/custom-logout")*/
        /*.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).*/
                .logoutSuccessUrl("/").permitAll().and()
            /*    .and()*/


           .csrf() /*.ignoringAntMatchers(publicUrls)*/.ignoringAntMatchers("/h2-console/**","/ws/**","/upload*/**");
                //.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler);

        ;//don't apply CSRF protection to /h2-console;
        //http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
       // http.addFilterAfter(httpClientFilter(), AccessDeniedExceptionFilter.class);


       // http.exceptionHandling()/*.accessDeniedHandler(new CustomAccessDeniedHandler()).and()*/
       // http.exceptionHandling().authenticationEntryPoint(new CustomHttp403ForbiddenEntryPoint());
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(
                        new CustomHttp403ForbiddenEntryPoint(),
                        new AntPathRequestMatcher("/home/createTransfer**"))
                .defaultAuthenticationEntryPointFor(
                        loginUrlauthenticationEntryPoint(),
                        new AntPathRequestMatcher("/user/general/**"));


       http.headers().frameOptions().disable();


        http.sessionManagement()
                .maximumSessions(100)               //(1)
                .maxSessionsPreventsLogin(false).sessionRegistry(sessionRegistry()); //(4)
                //(2)
      /*          .expiredUrl("/auth/login").and()   //(3)
                .invalidSessionUrl("/invalidSession.html")*/

        ;

        ;//.antMatchers("/**").permitAll();

    }


    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }







    @Bean
    public AuthenticationEntryPoint loginUrlauthenticationEntryPoint(){
        return new LoginUrlAuthenticationEntryPoint("/userLogin");
    }

    @Bean
    public AuthenticationEntryPoint loginUrlauthenticationEntryPointWithWarning(){
        return new LoginUrlAuthenticationEntryPoint("/userLoginWithWarning");
    }

}