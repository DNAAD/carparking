package com.coalvalue.configuration.security;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//@EnableWebMvc
/*@ControllerAdvice*/
public class RestResponseEntityExceptionHandler 
  extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
      Exception ex, WebRequest request) {

        System.out.println("dddRestResponseEntityExceptionHandlerddddddddddddddddddddddddddd");
        return new ResponseEntity<Object>(
          "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


/*    @ExceptionHandler({ Exception.class })
    public void handleException__(
            Exception ex, WebRequest request) {
        System.out.println("----------handleException__-----------");
    }*/


    @ExceptionHandler({ApplicationException.class})
    public void notFount(){
        System.out.println("----------CaughtApplicationException-----------");
    }

    @ExceptionHandler({Exception.class})
    public void notFountGlobal(){
        System.out.println("----------CaughtApplicationException-----------");
    }
}