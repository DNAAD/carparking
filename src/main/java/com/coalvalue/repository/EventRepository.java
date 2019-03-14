package com.coalvalue.repository;


import com.coalvalue.domain.entity.Camera;
import com.coalvalue.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhao yuan on 01/10/2015.
 */
public interface EventRepository extends JpaRepository<Event, Integer> {




}
