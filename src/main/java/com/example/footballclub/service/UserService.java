package com.example.footballclub.service;

import com.example.footballclub.model.User;
import com.example.footballclub.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Transactional
    public void updateName(String userId, String name){
        userRepository.updateName(userId,name);
    }

    @Transactional
    public void updateSex(String userId, int sex){
        userRepository.updateSex(userId,sex);
    }

    @Transactional
    public void updateAge(String userId, int age){
        userRepository.updateAge(userId,age);
    }

    @Transactional
    public void updateFootballPosition(String userId, int footballPosition){
        userRepository.updateFootballPosition(userId,footballPosition);
    }

    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }

    @Transactional
    public List<User> findAll(){
        List<User> list = (List<User>)userRepository.findAll();
        return list;
    }

    @Transactional
    public User findUserByUserId(String userId){
        return userRepository.findUserByUserId(userId);
    }

    @Transactional
    public User findUserByOpenid(String openid){ return userRepository.findUserByOpenid(openid); }
}
