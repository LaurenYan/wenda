package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ${ywj} on 2017/12/21.
 */
@Mapper//与数据库做的一个映射
public interface CommentDAO {
    /*String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    *//* #之后的就是User里面的属性 *//*
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values(#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    //选一个entity下所有的评论
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId,
                                         @Param("entityType") int entityType);

    //筛选某一个下面现在有多少条评论
    @Select({"select count(id) from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} "})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where id=#{id}"})
    Comment getCommentById(int id);

    @Select({"select count(id) from ", TABLE_NAME, " where user_id=#{userId}"})
    int getUserCommentCount(int userId);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);*/

    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Comment getCommentById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);

    @Select({"select count(id) from ", TABLE_NAME, " where user_id=#{userId}"})
    int getUserCommentCount(int userId);

}