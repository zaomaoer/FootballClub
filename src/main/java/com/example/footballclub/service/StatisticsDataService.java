package com.example.footballclub.service;

import com.example.footballclub.model.StatisticsData;
import com.example.footballclub.repository.StatisticsDataRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class StatisticsDataService {

    @Resource
    private StatisticsDataRepository statisticsDataRepository;

    @Transactional
    public StatisticsData findByUserIdAndYearStr(String userId,String yearStr){
        return statisticsDataRepository.findByUserIdAndYearStr(userId,yearStr);
    }
}
