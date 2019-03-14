package com.coalvalue.util;

import com.coalvalue.domain.entity.Company;
import com.coalvalue.domain.entity.NoGenerator;

import com.coalvalue.domain.entity.User;
import com.coalvalue.repository.CompanyRepository;
import com.coalvalue.repository.NoGeneratorRepository;
import com.coalvalue.service.NoGeneratorService;

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
    private MySQLMaxValueIncrementer transportNoGenerator;




    @Autowired
    private MySQLMaxValueIncrementer inventoryTransferGenerator;


    public String nextTransportNO() {
        return transportNoGenerator.nextStringValue();
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


    public String nextInventoryTransferNo() {
        //SimpleDateFormat format= new SimpleDateFormat("yyyyMMdd");
        //String date=format.format(new Date());

        return  inventoryTransferGenerator.nextStringValue();

    }

}
