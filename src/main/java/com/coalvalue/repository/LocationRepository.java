package com.coalvalue.repository;


import com.coalvalue.domain.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Peter Xu on 09/24/2017.
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
