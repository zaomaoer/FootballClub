package com.example.footballclub.controller;

import com.example.footballclub.constant.ErrorCode;
import com.example.footballclub.model.Activity;
import com.example.footballclub.model.ActivitySignIn;
import com.example.footballclub.model.ActivitySignUp;
import com.example.footballclub.model.WxResponseBody;
import com.example.footballclub.security.ControllerException;
import com.example.footballclub.service.ActivityService;
import com.example.footballclub.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private static Logger logger = LogManager.getLogger(ActivityController.class);

    @Resource
    private ActivityService activityService;
    @Resource
    private RedisService redisService;

    /**
     * 活动列表查询
     * */
    @RequestMapping(value="/queryAll",method = RequestMethod.POST)
    @ResponseBody
    public Object queryAll(@RequestBody Map<String,Object> map, HttpSession session){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String thirdSessionId = null;
        try{
            if(null == map.get("thirdSessionId")){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }else{
                thirdSessionId = map.get("thirdSessionId").toString();
            }
            response.setResult(activityService.findActivitiesByCreateTimeAfter());
        }catch(ControllerException e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(e.getErrorCode());
            response.setErrorMsg(e.getErrorMsg());
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            response.setErrorMsg(e.getMessage());
        }finally {
            return response;
        }
    }

    /**
     * 活动发布
     * */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestBody Map<String,Object> map){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String thirdSessionId = null;
        String title = null;
        String content = null;
        BigDecimal positionLatitude;
        BigDecimal positionLongitude;
        long endingTime = 0;
        try{
            if(null == map.get("thirdSessionId") || null == map.get("title") || null == map.get("content") || null == map.get("endingTime")){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }else{
                thirdSessionId = map.get("thirdSessionId").toString();
                String userId = redisService.getUserIdByThirdSessionId(thirdSessionId);

                title = map.get("title").toString();
                content = map.get("content").toString();
                endingTime = Long.parseLong(map.get("endingTime").toString());

                Activity activity = new Activity();
                activity.setTitle(title);
                activity.setContent(content);
                activity.setCreateUser(userId);
                activity.setEndingTime(new Date(endingTime));
                activity.setCreateTime(new Date());
                if(null != map.get("positionLatitude") && null != map.get("positionLongitude")){
                    positionLatitude = new BigDecimal(map.get("positionLatitude").toString());
                    positionLongitude = new BigDecimal(map.get("positionLongitude").toString());
                    activity.setPositionLatitude(positionLatitude);
                    activity.setPositionLongitude(positionLongitude);
                }
                response.setResult(activityService.saveActivity(activity));
            }
        }catch(ControllerException e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(e.getErrorCode());
            response.setErrorMsg(e.getErrorMsg());
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            response.setErrorMsg(e.getMessage());
        }finally {
            return response;
        }
    }

    /**
     * 活动报名
     * */
    @RequestMapping(value="/signUp",method = RequestMethod.POST)
    @ResponseBody
    public Object signUp(@RequestBody Map<String,Object> map){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String thirdSessionId = null;
        int activityId = 0;
        try{
            //上传参数检查
            if(null == map.get("thirdSessionId") || null == map.get("activityId")){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }else{
                thirdSessionId = map.get("thirdSessionId").toString();
                activityId = Integer.parseInt(map.get("activityId").toString());
            }
            //thirdSessionId换取userId
            String userId = redisService.getUserIdByThirdSessionId(thirdSessionId);
            ActivitySignUp activitySignUp = new ActivitySignUp();
            activitySignUp.setUserId(userId);
            activitySignUp.setActivityId(activityId);
            response.setResult(activityService.saveActivitySignUp(activitySignUp));
        }catch(ControllerException e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(e.getErrorCode());
            response.setErrorMsg(e.getErrorMsg());
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            response.setErrorMsg(e.getMessage());
        }finally {
            return response;
        }
    }

    /**
     * 活动签到
     * */
    @RequestMapping(value="/signIn",method = RequestMethod.POST)
    @ResponseBody
    public Object signIn(@RequestBody Map<String,Object> map){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String thirdSessionId = null;
        int activityId = 0;
        BigDecimal positionLatitude;
        BigDecimal positionLongitude;
        try{
            //上传参数检查
            if(null == map.get("thirdSessionId") || null == map.get("activityId")){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }else{
                thirdSessionId = map.get("thirdSessionId").toString();
                activityId = Integer.parseInt(map.get("activityId").toString());

            }
            //判断签到地点逻辑
            if(null != map.get("positionLatitude") && null != map.get("positionLongitude") ){
                positionLatitude = new BigDecimal(map.get("positionLatitude").toString());
                positionLongitude = new BigDecimal(map.get("positionLongitude").toString());
            }

            //thirdSessionId换取userId
            String userId = redisService.getUserIdByThirdSessionId(thirdSessionId);

            ActivitySignIn activitySignIn = new ActivitySignIn();
            activitySignIn.setUserId(userId);
            activitySignIn.setActivityId(activityId);
            activitySignIn.setSignInTime(new Date());
            activityService.saveActivitySignIn(activitySignIn);

            response.setResult("Y");
        }catch(ControllerException e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(e.getErrorCode());
            response.setErrorMsg(e.getErrorMsg());
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            response.setErrorMsg(e.getMessage());
        }finally {
            return response;
        }
    }
}
