package com.example.footballclub.service;

import com.example.footballclub.model.Activity;
import com.example.footballclub.model.ActivitySignIn;
import com.example.footballclub.model.ActivitySignUp;
import com.example.footballclub.repository.ActivityRepository;
import com.example.footballclub.repository.ActivitySignInRepository;
import com.example.footballclub.repository.ActivitySignUpRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
public class ActivityService {

    @Resource
    private ActivityRepository activityRepository;
    @Resource
    private ActivitySignUpRepository activitySignUpRepository;
    @Resource
    private ActivitySignInRepository activitySignInRepository;

    @Transactional
    public List<Activity> findActivitiesByCreateTimeAfter(){
        Calendar calendar = Calendar.getInstance();
        return activityRepository.findActivitiesByCreateTimeAfter(calendar.get(Calendar.YEAR)+"0000");
    }

    @Transactional
    public Activity saveActivity(Activity activity){
        return activityRepository.save(activity);
    }

    @Transactional
    public ActivitySignUp saveActivitySignUp(ActivitySignUp activitySignUp){
        return activitySignUpRepository.save(activitySignUp);
    }

    @Transactional
    public ActivitySignIn saveActivitySignIn(ActivitySignIn activitySignIn){
        return activitySignInRepository.save(activitySignIn);
    }
}
