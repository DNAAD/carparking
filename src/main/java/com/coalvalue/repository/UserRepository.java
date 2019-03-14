package com.coalvalue.repository;


import com.coalvalue.domain.entity.TransportOperation;
import com.coalvalue.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created by Peter Xu on 01/10/2015.
 */
public interface UserRepository extends JpaRepository<User, Integer> {




    @Query("select u from User u where u.mobile= :mobile and u.password= :password")
    User userLoginByMobileNo(@Param("mobile") String userId, @Param("password") String pwd);





    User findByMobile(String s);

    Optional<User> findById(Integer id);

    User findByUserId(String s);

    User findByUserName(String s);


}
