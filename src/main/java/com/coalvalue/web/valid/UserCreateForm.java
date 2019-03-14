package com.coalvalue.web.valid;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by silence on 2016/1/23.
 */

public class UserCreateForm {


 //   @NotEmpty
   // @NotBlank(message = "First name blank")

    private String userName;
    private String scene;
    private Integer itemId;
    private String fromUri;
    private Integer smsActionId;


    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

   // @NotEmpty
   // @NotBlank(message = "First name blank")

    private String givenName;

   // @NotEmpty(message = "{user.family.null}")
   // @NotBlank(message = "First name blank")

    private String familyName;

    @NotBlank(message = "First name blank")
    @NotNull
    private String phone;

    @NotEmpty(message = "{user.usertype.null}")
    @NotBlank(message = "First name blank")
    @NotNull
    private String userType;

    private String passwordRepeated;
    private String email;
    private String password;

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    //  @NotEmpty(message = "{user.companyname.null}")
   // @NotBlank(message = "First name blank")

    private String companyName;


    private String openId;










    private String plateNumber;
    private String activeCode;

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    private String vehicleType;
    private BigDecimal carryingCapacity;

    @NotNull
    private String termsAndConditionsAgreement;

    public String getTermsAndConditionsAgreement() {
        return termsAndConditionsAgreement;
    }

    public void setTermsAndConditionsAgreement(String termsAndConditionsAgreement) {
        this.termsAndConditionsAgreement = termsAndConditionsAgreement;
    }

    public BigDecimal getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(BigDecimal carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;


    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setFromUri(String fromUri) {
        this.fromUri = fromUri;
    }

    public String getFromUri() {
        return fromUri;
    }

    public void setSmsActionId(Integer smsActionId) {
        this.smsActionId = smsActionId;
    }

    public Integer getSmsActionId() {
        return smsActionId;
    }
}
