package com.coalvalue.repository;


import com.coalvalue.domain.entity.Behavioural;
import com.coalvalue.domain.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface FollowerRepository extends JpaRepository<Follower, Integer> {


    Follower findByOpenId(String openId);

}
