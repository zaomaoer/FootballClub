package com.example.footballclub.model;

import javax.persistence.*;

@Entity
@Table(name="statistics_data")
public class StatisticsData{


    private String userId;
    @Column
    private String yearStr;
    @Column
    private Integer attendance;
    @Column
    private Integer goal;
    @Column
    private Integer assist;
    @Column
    private Float attendanceRate;
    @Column
    private Integer late;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Integer getAssist() {
        return assist;
    }

    public void setAssist(Integer assist) {
        this.assist = assist;
    }

    public Float getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(Float attendanceRate) {
        this.attendanceRate = attendanceRate;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }
}
