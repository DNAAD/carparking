package com.coalvalue.util;

import com.coalvalue.domain.entity.Company;
import com.coalvalue.domain.entity.NoGenerator;

import com.coalvalue.repository.CompanyRepository;
import com.coalvalue.repository.NoGeneratorRepository;
import com.coalvalue.service.NoGeneratorService;
import com.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Peter Xu on 05/30/2015.
 */
@Component
public class SequenceGenerator {

    @Autowired
    private MySQLMaxValueIncrementer orderNoGenerator;

    @Autowired
    private MySQLMaxValueIncrementer contractNoGenerator;

    @Autowired
    private MySQLMaxValueIncrementer shippingNoGenerator;


    @Autowired
    private MySQLMaxValueIncrementer dealNoGenerator;

    @Autowired
    private MySQLMaxValueIncrementer userNoGenerator;


    @Autowired
    private MySQLMaxValueIncrementer companyNoGenerator;

    @Autowired
    private MySQLMaxValueIncrementer supplyNoGenerator;

    @Autowired
    private MySQLMaxValueIncrementer demandNoGenerator;


    @Autowired
    private MySQLMaxValueIncrementer capacityNoGenerator;


    @Autowired
    private MySQLMaxValueIncrementer uuidNoGenerator;

    @Autowired
    private MySQLMaxValueIncrementer transportNoGenerator;


    @Autowired
    private MySQLMaxValueIncrementer teamDealNoGenerator;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NoGeneratorRepository noGeneratorRepository;

    @Autowired
    private NoGeneratorService noGeneratorService;

    @Autowired
    private MySQLMaxValueIncrementer scanIdGenerator;
    private int scanId;


    //   @Transactional
    public String nextOrderNO(User currentUser) {


        if(currentUser != null){
            //NoGenerator noGenerator = noGeneratorRepository.findByCompanyId(currentUser.getCompany().getId());//currentUser.getCompany().getNoGenerator();
            NoGenerator noGenerator = noGeneratorService.getOrderNo(1);//currentUser.getCompany().getNoGenerator();


            SimpleDateFormat format= new SimpleDateFormat("yyyyMMdd");
            String date=format.format(new Date());
            if(noGenerator != null) {

                Integer no = noGenerator.getOrderNo();
                noGenerator.setValue(no+1);

                noGeneratorRepository.save(noGenerator);
                return "ORNO";// + currentUser.getCompany().getCompanyNo() +"-"+ no+"-"+date+"-00";

                //  return "COMPANYORNO" + noGenerator.getOrderNo();
            }else {
                noGenerator = new NoGenerator();
                noGenerator.setValue(1);

                noGenerator.setCompanyId(currentUser.getCompanyId());

                noGeneratorRepository.save(noGenerator);
                return "ORNO" ;//+ currentUser.getCompany().getCompanyNo() +"-"+ 1+"-"+date+"-00";

            }



        }
           return "ORNO";// + orderNoGenerator.nextStringValue()+ActiveCodeGenerater.createSMSActiveCode(true,5) ;

    }

    public String nextContractNO() {
        return "CTNO" + contractNoGenerator.nextStringValue();
    }
    public String nextUuidNO() {
        return uuidNoGenerator.nextStringValue();
    }
    public String nextTransportNO() {
        return transportNoGenerator.nextStringValue();
    }


    public String nextTeamDealNO() {
        return teamDealNoGenerator.nextStringValue();
    }

    @Transactional
    public String nextDealNO(User currentUser) {

        SimpleDateFormat format= new SimpleDateFormat("yyyy");
        String date=format.format(new Date());

        Company company =null;// companyRepository.findByUserId(currentUser.getId());



        NoGenerator noGenerator =null;// noGeneratorService.getDealNo(currentUser);
        if(noGenerator != null){

            Integer no = noGenerator.getDealNo();
            if(no == null) {
                no = 0;
            }
            noGenerator.setDealNo(no+1);
            noGeneratorRepository.save(noGenerator);
            return "DNO" + "-"+date+"-"+no;
        }else {
            noGenerator = new NoGenerator();
            noGenerator.setCompanyId(company.getId());
            noGeneratorRepository.save(noGenerator);
            return "DNO" + "-"+date+"-"+1;
        }

      //  return "DNO" + "-"+date+"-"+dealNoGenerator.nextStringValue();
    }

    public String nextShippingNO() {
        return "SHNO" + shippingNoGenerator.nextStringValue();
    }

    public String nextUserNO() {
        return "U" + userNoGenerator.nextStringValue();
    }

    public String nextCompanyNO() {
        return "" + getUniqueCompanyNo();
    }

    public String nextSupplyNO(String companyNo) {
        return "SUP" + companyNo +  supplyNoGenerator.nextStringValue();
    }

    public String nextDemandNO(String companyNo) {
        return "DEM" + companyNo + demandNoGenerator.nextStringValue();
    }

    public String nextCapacityNo(String companyNo) {

        return "CA" + companyNo + capacityNoGenerator.nextStringValue();
    }

    public String getUniqueCompanyNo() {

        String no = null;
        String result = null;

        no = "" + GetStableHash(capacityNoGenerator.nextStringValue());
        result  = companyRepository.findCompanyNoByCompanyNo(no);

        while(result != null){
            no = "" + GetStableHash(capacityNoGenerator.nextStringValue());
            result = companyRepository.findCompanyNoByCompanyNo(no);

        }
        return no;
    }

    public int compactDigest(MessageDigest digest) {
        byte [] byteArr = digest.digest();
        // +3 since conversion to int array with divide length by four.
        // and we don't want to lose any bytes.
        ByteBuffer bytes = ByteBuffer.allocate(byteArr.length + 3);
        bytes.put(byteArr);
        bytes.rewind();

        IntBuffer ints = bytes.asIntBuffer();
        int compactDigest = 0;
        for (int i = 0; i < ints.limit(); ++i) {
            compactDigest ^= ints.get(i);
        }

        return compactDigest;
    }
    //static int  MUST_BE_LESS_THAN = 100000000; // 8 decimal digits
    static int  MUST_BE_LESS_THAN = 100000; // 8 decimal digits

    public static Integer GetStableHash(String s)
    {

        int shorthash = s.hashCode()% MUST_BE_LESS_THAN; // 8 zeros
        if (shorthash < 0) shorthash *= -1;
        return shorthash;
    }

    public static void main(String[] args) {

        System.out.print("hashcode : "+ GetStableHash("000001ffffffffffffffffffffffff"));
    }

    public String nextCanvassingNo(User user) {
        SimpleDateFormat format= new SimpleDateFormat("yyyyMMdd");
        String date=format.format(new Date());



        NoGenerator noGenerator =  null;//noGeneratorService.getCanvassingNo(user);

        Integer no = noGenerator.getValue();
        String noString = String.format("%05d",no);
        if(no == null) {
            no = 0;
        }
        noGenerator.setDealNo(no+1);
        noGeneratorRepository.save(noGenerator);
        return "CA" + "-"+date+"-"+noString;

    }

    public int getScanId() {
        return scanIdGenerator.nextIntValue();
    }
}
