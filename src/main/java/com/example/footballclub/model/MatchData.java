package com.example.footballclub.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="match_data")
public class MatchData {

    private int seqno;
    @Column
    private String userId;
    @Column
    private int matchId;
    @Column
    private int goal;
    @Column
    private int assist;
    @Column
    private Date recordTime;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getSeqno(){
        return seqno;
    }

    public void setSeqno(int seqno){
        this.seqno = seqno;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
}
