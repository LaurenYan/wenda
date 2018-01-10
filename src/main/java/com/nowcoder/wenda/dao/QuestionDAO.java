package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Question;
import com.nowcoder.wenda.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ${ywj} on 2017/12/21.
 */
@Mapper//与数据库做的一个映射
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /* #之后的就是User里面的属性 */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id =#{id}"})
    Question selectById(int id);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Question getById(int id);
}