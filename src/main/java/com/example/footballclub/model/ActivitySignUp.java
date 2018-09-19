package com.example.footballclub.model;

import javax.persistence.*;

@Entity
@Table(name="activity_sign_up")
public class ActivitySignUp {

    private int seqno;
    private String userId;
    private int activityId;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
}