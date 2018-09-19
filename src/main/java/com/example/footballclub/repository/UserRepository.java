package com.example.footballclub.repository;

import com.example.footballclub.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,String> {

    @Override
    Iterable<User> findAll();

    User findUserByUserId(String userId);

    @Query(value = "select * from user where openid = ?1 limit 1",nativeQuery=true)
    User findUserByOpenid(String openid);


    @Modifying
    @Query(value="update user a set a.name= ?2 where a.user_id=?1",nativeQuery=true)
    public int updateName(String userId, String name);

    @Modifying
    @Query(value="update user a set a.sex=?2 where a.user_id=?1",nativeQuery=true)

    public int updateSex(String userId,int sex);

    @Modifying
    @Query(value="update user a set a.age=?2 where a.user_id=?1",nativeQuery=true)
    public int updateAge(String userId,int age);

    @Modifying
    @Query(value="update user a set a.football_position=?2 where a.user_id=?1",nativeQuery=true)
    public int updateFootballPosition(String userId,int footballPosition);
}
