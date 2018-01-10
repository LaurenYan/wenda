package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by ${ywj} on 2017/12/21.
 */
@Mapper//与数据库做的一个映射
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url";
/*
* head_url是数据库里的字段
* headUrl是User的属性
* {}放的是属性*/
    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

}

