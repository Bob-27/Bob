package com.videoSite.controller;


import cn.bob27.service.IpCountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videoSite.entity.Comment;
import com.videoSite.entity.Notification;
import com.videoSite.service.CommentService;
import com.videoSite.utils.NotificationUtil;
import com.videoSite.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    IpCountService ipCountService;
    @Autowired
    NotificationUtil notificationUtil;
    @Autowired
    CommentService commentService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/postComment")
    public void postComment(@RequestBody Comment comment){//@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
        ipCountService.count();
        ipCountService.print();
        String beNotifier = comment.getVideo_username();
        String url = "/video/toVideo/" + comment.getVideo_id();
        String ip = httpServletRequest.getRemoteAddr();
        comment.setIp(ip);
        Notification notification = new Notification(comment.getCommentator(),beNotifier,comment.getCommentTime(),url,"评论了你的视频");
        notificationUtil.Notify(beNotifier,notification);
        commentService.save(comment);
    }

    @GetMapping("/getComment/{pageNum}/{video_id}")
    public IPage<Comment> getComment(@PathVariable("video_id") Integer video_id,
                                    @PathVariable(value = "pageNum",required = false) Integer pageNum){
        Page<Comment> commentPage = new Page<>(pageNum,8);//分页查询
        IPage<Comment> page = commentService.page(commentPage, new QueryWrapper<Comment>().eq("video_id",video_id).orderByDesc("id"));
        //条件查询，eq意思为等于，后面是查询条件，用LambdaQueryWrapper就可以不手工输入属性名
        return page;
    }
}
