package com.coalvalue.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;

//import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Peter Xu on 01/10/2015.
 */
@Entity
@Table(name = "user",catalog="storage")
public class User extends BaseDomain implements UserDetails {

    @Transient
    List<GrantedAuthority> authorities;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_TYPE")
    private String userType;


    @Column(name = "STATUS")
    private String status;

    @Column(name = "mobile")
    private String mobile;




    @Transient
    private Distributor distributor;


    private String userId;

    @Transient
    private String companyNo;


    @Transient
    private Employee employee;
    @Transient
    private Preference preference;


    public User(String username) {

        this.userName = username;
    }

    public User(String username, String password, List<GrantedAuthority> authorities) {

        this.userName = username;
        this.password = password;
        this.authorities = authorities;
    }




    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public User() {
    }






    public String getUserType() {
        return userType;
    }


    public String getStatus() {
        return status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }



    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(authorities != null){
            for(GrantedAuthority grantedAuthority : this.authorities) {
                System.out.println("-----------------------------------user get authorities:" + grantedAuthority.getAuthority().toString());
            }

            return authorities;
        }else {
            return null;
        }

    }

    @Override
    public String getPassword() {

        System.out.println("-----------------------------------user get authorities");

        return password;
    }

    @Override
    public String getUsername() {
        System.out.println("-----------------------------------user get getUsername" + userName);

        return userName;
    }








    //@NotNull
    private boolean accountExpired;

   // @NotNull
    private boolean accountLocked;

   // @NotNull
    private boolean credentialsExpired;

   // @NotNull
    private boolean accountEnabled;


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        System.out.println("-----------------------------------user get isAccountNonExpired");
        return !accountExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        System.out.println("-----------------------------------user get isAccountNonLocked");

        return !accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        System.out.println("-----------------------------------user get isCredentialsNonExpired");

        return !credentialsExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        System.out.println("-----------------------------------user get isEnabled");

        return !accountEnabled;
    }





    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public Distributor getDistributor() {
        return distributor;
    }


    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public Preference getPreference() {
        return preference;
    }
}
