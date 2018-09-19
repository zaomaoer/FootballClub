package com.example.footballclub.controller;

import com.example.footballclub.model.User;
import com.example.footballclub.model.WxResponseBody;
import com.example.footballclub.service.HttpsService;
import com.example.footballclub.service.RedisService;
import com.example.footballclub.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/wx")
public class WxController {

    private static Logger logger = LogManager.getLogger(WxController.class);
    @Resource
    private HttpsService httpsService;
    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;


    /**
     * 微信登录及个人信息查询
     * */
    @RequestMapping(value = "/login",method=RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody Map<String,Object> map, HttpSession session) throws JsonProcessingException{

        logger.info(map);
        WxResponseBody response = new WxResponseBody();

        String code = null;
        String appid = "wx3f663d5af3e32c16";
        String secret = "968d22d5935cbf27262a850fa71722b8";
        //获取openid和session_key
        try{
            code = map.get("code").toString();
        }catch(Exception e){
            e.printStackTrace();
            response.setErrorCode(1);
            response.setErrorMsg("请求报文错误");
            return response;
        }
        Map param = new HashMap<String,String>();
        param.put("appid",appid);
        param.put("secret",secret);
        param.put("js_code",code);
        param.put("grant_type","authorization_code");
        String s = httpsService.doGet("https://api.weixin.qq.com/sns/jscode2session",param,"UTF-8");
        logger.info("login response is " + s);

        //构造返回对象
        Map result = new HashMap();
        //解析为json对象
        ObjectMapper mapper = new ObjectMapper();
        String thirdSessionId = null;
        try {
            JsonNode rootNode = mapper.readTree(s);
            String openid = rootNode.path("openid").asText();
            String sessionKey = rootNode.path("session_key").asText();

            //自定义登录态采用UUID，并记录到redis中
            thirdSessionId = UUID.randomUUID().toString();
            redisService.setThirdSessionToOpenid(thirdSessionId,openid);
            result.put("thirdSessionId",thirdSessionId);
            //检查是否首次登陆
            User user = userService.findUserByOpenid(openid);
            if(null != user){//非首次登陆
                result.put("userId",user.getUserId());
                result.put("name",user.getName());
                result.put("sex",user.getSex());
                result.put("age",user.getAge());
                result.put("role",user.getRole());
                result.put("footballPosition",user.getFootballPosition());
                result.put("createTime",user.getCreateTime());
                redisService.setThirdSessionToUserId(thirdSessionId,user.getUserId());
            }else{//首次登陆
                result.put("firstLogin",0);
                User newUser = new User();
                newUser.setOpenid(openid);
                newUser.setRole(3);
                newUser.setCreateTime(new Date());
                userService.save(newUser);
                newUser = userService.findUserByOpenid(openid);
                result.put("userId",newUser.getUserId());
                result.put("role",newUser.getRole());
                result.put("createTime",newUser.getCreateTime());
            }
            logger.info("result : " + result);
            response.setResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return response;
        }
    }
}
