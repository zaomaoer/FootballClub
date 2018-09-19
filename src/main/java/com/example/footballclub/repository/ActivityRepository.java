package com.example.footballclub.repository;

import com.example.footballclub.model.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity,String> {

    @Query(value = "select * from activity where create_time >= ?",nativeQuery=true)
    public List<Activity> findActivitiesByCreateTimeAfter(String after);

    @Query(value = "select * from activity where create_time <= ?",nativeQuery=true)
    public List<Activity> findActivitiesByCreateTimeBefore(String before);
}
