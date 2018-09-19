package com.example.footballclub.repository;

import com.example.footballclub.model.StatisticsData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsDataRepository extends CrudRepository<StatisticsData,String> {

    @Query(value = "select user_id,year_str,attendance,goal,assist,attendance_rate,late from statistics_data where user_id=? and year_str=?",nativeQuery=true)
    public StatisticsData findByUserIdAndYearStr(String userId, String yearStr);
}
