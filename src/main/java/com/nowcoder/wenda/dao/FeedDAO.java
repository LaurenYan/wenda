package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ${ywj} on 2017/12/21.
 */
@Mapper//与数据库做的一个映射
public interface FeedDAO {
    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = " user_id, data, created_date, type ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /* #之后的就是User里面的属性 */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values(#{userId},#{data},#{createdDate},#{type})"})
    int addFeed(Feed feed);

    //拉模式
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Feed getFeedById(int id);

    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);
}