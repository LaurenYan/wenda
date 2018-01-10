package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.async.EventModel;
import com.nowcoder.wenda.async.EventProducer;
import com.nowcoder.wenda.async.EventType;
import com.nowcoder.wenda.model.Comment;
import com.nowcoder.wenda.model.EntityType;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.service.CommentService;
import com.nowcoder.wenda.service.LikeService;
import com.nowcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ${ywj} on 2017/12/29.
 */

@Controller
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }


        Comment comment = commentService.getCommentById(commentId);
        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser()
                .getId()).setEntityType(commentId).setEntityType(EntityType.ENTITY_COMMENT).setEntityOwnerId(comment.getUserId())
                .setExt("questionId", String.valueOf(comment.getEntityId())));

        long likecount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likecount));
    }
    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }

        long dislikecount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(dislikecount));
    }



}

