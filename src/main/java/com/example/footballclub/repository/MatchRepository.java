package com.example.footballclub.repository;

import com.example.footballclub.model.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match,String> {

    public Match findByActivityId(int activityId);
}
