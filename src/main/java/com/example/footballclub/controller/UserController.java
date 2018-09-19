package com.example.footballclub.controller;

import com.example.footballclub.constant.ErrorCode;
import com.example.footballclub.model.StatisticsData;
import com.example.footballclub.model.User;
import com.example.footballclub.model.WxResponseBody;
import com.example.footballclub.security.ControllerException;
import com.example.footballclub.service.RedisService;
import com.example.footballclub.service.StatisticsDataService;
import com.example.footballclub.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LogManager.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private StatisticsDataService statisticsDataService;
    @Resource
    private RedisService redisService;

    /**
     * 个人信息编辑
     * */
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestBody Map<String,Object> map, HttpSession session){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        //参数检查
        String name = null;
        int sex = 0;
        int age = 0;
        int footballPosition = 0;
        try{
            if(null == map.get("thirdSessionId")){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }
            String thirdSessionId = map.get("thirdSessionId").toString();
            String userId = redisService.getUserIdByThirdSessionId(thirdSessionId);

            if(null != map.get("name")){
                name = map.get("name").toString();
                userService.updateName(userId,name);
            }
            if(null != map.get("sex")){
                sex = Integer.parseInt(map.get("sex").toString());
                userService.updateSex(userId,sex);
            }
            if (null != map.get("age")){
                age = Integer.parseInt(map.get("age").toString());
                userService.updateAge(userId,age);
            }
            if (null !=map.get("footballPosition")){
                footballPosition = Integer.parseInt(map.get("footballPosition").toString());
                userService.updateFootballPosition(userId,footballPosition);
            }
            User user = userService.findUserByUserId(userId);
            response.setResult(user);
        }catch(ControllerException e){
            e.printStackTrace();
            response.setErrorCode(e.getErrorCode());
            response.setErrorMsg(e.getErrorMsg());
        }catch(Exception e){
            e.printStackTrace();
            response.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            response.setErrorMsg(e.getMessage());
        }finally{
            return response;
        }
    }

    /**
     * 加入球队
     * */
    @RequestMapping(value="/save",method=RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestBody Map<String,Object> map, HttpSession session){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String thirdSessionId = null;
        String name = null;
        int sex = 0;
        int age = 0;
        int footballPosition = 0;
        try{
            if((null==map.get("thirdSessionId")) || (null==map.get("name")) || (null==map.get("sex")) || (null==map.get("age")) || (null==map.get("footballPosition"))){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }
            name = map.get("name").toString();
            sex = Integer.parseInt(map.get("sex").toString());
            age = Integer.parseInt(map.get("age").toString());
            footballPosition = Integer.parseInt(map.get("footballPosition").toString());
            thirdSessionId = map.get("thirdSessionId").toString();

            String openid = redisService.getOpenidByThirdSessionId(thirdSessionId);

            User oldUser = userService.findUserByOpenid(openid);
            if(oldUser != null){
                throw new ControllerException(ErrorCode.LOGIC_ERROR,ErrorCode.LOGIC_ERROR_MSG+"该成员已加入球队！");
            }
            User newUser = new User();
            newUser.setOpenid(openid);
            newUser.setName(name);
            newUser.setSex(sex);
            newUser.setAge(age);
            newUser.setFootballPosition(footballPosition);
            newUser.setRole(2);//P-待审批会员
            newUser.setCreateTime(new Date());
            User result = userService.save(newUser);
            response.setResult(result);
        }catch(ControllerException e){
            logger.error(e.getStackTrace());
            response.setErrorCode(e.getErrorCode());
            response.setErrorMsg(e.getErrorMsg());
        }catch (Exception e){
            logger.error(e.getStackTrace());
            System.out.println(e);
            response.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            response.setErrorMsg(e.getMessage());
        }finally{
            return response;
        }
    }

    /**
     * 个人数据查询
     * */
    @RequestMapping(value="/queryMatchData",method = RequestMethod.POST)
    @ResponseBody
    //@RequiredPermission(PermissionConstants.MEMBER)
    public Object queryMatchData(@RequestBody Map<String,Object> map, HttpSession session){
        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String thirdSessionId = null;
        try{
            //上传参数检查
            if(null == map.get("thirdSessionId")){
                throw new ControllerException(ErrorCode.REQUEST_ERROR,ErrorCode.REQUEST_ERROR_MSG);
            }else{
                thirdSessionId = map.get("thirdSessionId").toString();
            }
            //thirdSessionId换取userId
            String userId = redisService.getUserIdByThirdSessionId(thirdSessionId);
            StatisticsData statisticsData = statisticsDataService.findByUserIdAndYearStr(userId,"2018");
            response.setResult(statisticsData);
        }catch(ControllerException e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(e.getErrorCode());
            response.setErrorMsg(e.getErrorMsg());
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace());
            response.setErrorCode(ErrorCode.UNKNOWN_ERROR);
            response.setErrorMsg(e.getMessage());
        }finally{
            return response;
        }
    }
}
