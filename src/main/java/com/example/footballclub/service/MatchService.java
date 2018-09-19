package com.example.footballclub.service;

import com.example.footballclub.model.Match;
import com.example.footballclub.model.MatchData;
import com.example.footballclub.repository.MatchDataRepository;
import com.example.footballclub.repository.MatchRepository;
import com.example.footballclub.repository.StatisticsDataRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class MatchService {

    @Resource
    private MatchRepository matchRepository;
    @Resource
    private MatchDataRepository matchDataRepository;
    @Resource
    private StatisticsDataRepository statisticsDataRepository;

    @Transactional
    public Match queryByActivity(int activityId){
        return matchRepository.findByActivityId(activityId);
    }

    @Transactional
    public List<MatchData> queryMatchData(int matchId){
        return matchDataRepository.findMatchDataByMatchId(matchId);
    }
}
