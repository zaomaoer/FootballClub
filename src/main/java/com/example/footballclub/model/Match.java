package com.example.footballclub.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_match")
public class Match {

    private int matchId;
    private Date matchTime;
    private int activityId;
    private String matchOpponent;
    private String matchResult;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getMatchOpponent() {
        return matchOpponent;
    }

    public void setMatchOpponent(String matchOpponent) {
        this.matchOpponent = matchOpponent;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }
}
