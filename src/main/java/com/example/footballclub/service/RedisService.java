package com.example.footballclub.service;

import com.example.footballclub.util.JedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private static Logger logger = LogManager.getLogger(RedisService.class);

    /**
     * redis 0 数据库存储thirdSessionId与userId的对应关系
     * */

    public void setThirdSessionToUserId(String thirdSessionId, String userId) throws Exception{
        logger.info("set thirdSessionId to userId.  key="+thirdSessionId + ", value="+userId);
        JedisUtil jedisUtil = JedisUtil.getInstance();
        jedisUtil.saveValueByKey(thirdSessionId,userId,1800,0);//设置过期时间
    }

    public String getUserIdByThirdSessionId(String thirdSessionId) throws Exception {
        logger.info("get userId by thirdSessionId.  key="+thirdSessionId);
        JedisUtil jedisUtil = JedisUtil.getInstance();
        jedisUtil.expire(thirdSessionId,1800,0);
        jedisUtil.expire(thirdSessionId,1800,1);
        return new String(jedisUtil.getValueByKey(0,thirdSessionId.getBytes()));
    }

    /**
     * redis 1 数据库存储thirdSessionId与openid的对应关系
     * */
    public void setThirdSessionToOpenid(String thirdSessionId, String openid) throws Exception {
        logger.info("set thirdSessionId to openid.  key="+thirdSessionId + ", value="+openid);
        JedisUtil jedisUtil = JedisUtil.getInstance();
        jedisUtil.saveValueByKey(thirdSessionId,openid,1800,1);//设置过期时间
    }

    public String getOpenidByThirdSessionId(String thirdSessionId) throws Exception {
        logger.info("get openid by thirdSessionId.  key="+thirdSessionId);
        JedisUtil jedisUtil = JedisUtil.getInstance();
        jedisUtil.expire(thirdSessionId,1800,0);
        jedisUtil.expire(thirdSessionId,1800,1);
        return new String(jedisUtil.getValueByKey(1,thirdSessionId.getBytes()));
    }
}
