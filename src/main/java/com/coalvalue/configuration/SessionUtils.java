package com.coalvalue.configuration;

import com.coalvalue.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SessionUtils {
   @Autowired
    private SessionRegistry sessionRegistry;

/*    @Resource
    private SessionRegistry sessionRegistry;*/
    public void expireUserSessions(String username) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        information.expireNow();
                    }
                }
            }
        }
    }



    public void getAllPrincipals_() {

        /*        return sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(e->e)
                .collect(Collectors.toList());*/

        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                if(!sessionRegistry.getAllSessions(userDetails, false).isEmpty()){
                    System.out.println(userDetails.toString());
                }

/*                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        information.expireNow();
                    }
                }*/
            }
        }
    }

    public Set<String> getAllPrincipals() {

        /*        return sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(e->e)
                .collect(Collectors.toList());*/
        List<Object> users = sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(e->e)
                .collect(Collectors.toList());

        Set<String> userName = new HashSet<>();
        for(Object user: users){
            userName.add(((User)user).getUsername());
        }
        userName.stream().forEach(e-> System.out.println(e));
        return userName;

    }

}