package com.example.footballclub.repository;

import com.example.footballclub.model.MatchData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchDataRepository extends CrudRepository<MatchData,String>{

    public List<MatchData> findMatchDataByMatchId(int matchId);
}
