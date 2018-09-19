package com.example.footballclub.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="activity_sign_in")
public class ActivitySignIn {

    private int seqno;
    private String userId;
    private int activityId;
    private Date signInTime;

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

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }
}
