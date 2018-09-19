package com.example.footballclub.controller;

import com.example.footballclub.constant.ErrorCode;
import com.example.footballclub.model.WxResponseBody;
import com.example.footballclub.security.ControllerException;
import com.example.footballclub.service.MatchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class MatchController {
    private static Logger logger = LogManager.getLogger(MatchController.class);

    @Resource
    private MatchService matchService;

    /**
     * 根据活动ID查询比赛
     * */
    @RequestMapping(value="/queryByActivity",method = RequestMethod.POST)
    @ResponseBody
    public Object queryByActivity(@RequestBody Map<String,Object> map){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String thirdSessionId = null;
        int activityId = 0;
        try{
            if(null == map.get("thirdSessionId") || null == map.get("activityId")){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }else{
                thirdSessionId = map.get("thirdSessionId").toString();
                activityId = Integer.parseInt(map.get("activityId").toString());
            }
            response.setResult(matchService.queryByActivity(activityId));
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
     * 比赛数据查询
     * */
    @RequestMapping(value="/queryMatchData",method = RequestMethod.POST)
    public Object queryMatchData(@RequestBody Map<String,Object> map){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String thirdSessionId = null;
        int matchId = 0;
        try{
            if(null == map.get("thirdSessionId") || null == map.get("matchId")){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }else{
                thirdSessionId = map.get("thirdSessionId").toString();
                matchId = Integer.parseInt(map.get("matchId").toString());
            }
            response.setResult(matchService.queryMatchData(matchId));
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
