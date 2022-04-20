package com.videoSite.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videoSite.entity.Notification;
import com.videoSite.entity.Subscribe;
import com.videoSite.entity.Video;
import com.videoSite.mapper.CollectionMapper;
import com.videoSite.utils.GetCurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController//定义一个controller类
@RequestMapping("/collection")//设定公共的请求路径前缀
public class CollectionController {
    @Autowired
    private CollectionMapper collectionMapper;

    @GetMapping("/addCollection/{videoId}")
    public void addCollection(@PathVariable("videoId") Integer videoId) {//@PathVariable从路径中获取变量
        String username = GetCurrentUserUtil.getCurrentUserName();
        if (collectionMapper.isExisted(username, videoId) == null) {
            collectionMapper.addCollectionByUsername(username, videoId);
        }
    }

    @GetMapping("/cancelCollection/{videoId}")
    public void cancelCollection(@PathVariable("videoId") Integer videoId) {
        String username = GetCurrentUserUtil.getCurrentUserName();
        collectionMapper.cancelCollectionByUsername(username, videoId);
    }

    @GetMapping("/getCollection/{pageNum}/{username}")//使用路径变量来传参
    public IPage<Video> getCollection(@PathVariable(value = "pageNum", required = false) Integer pageNum,
                                      @PathVariable("username") String username) {
        Page<Video> videoPage = new Page<>(pageNum, 8);
        IPage<Video> page = collectionMapper.getCollectionByUsername(videoPage, username);
        return page;
    }

    @GetMapping("/getCollectionStatus/{videoId}")
    public Integer getCollectionStatus(@PathVariable("videoId") Integer videoId) {
        String username = GetCurrentUserUtil.getCurrentUserName();
        if (collectionMapper.isExisted(username, videoId) == null) {
            return 1;
        }else {
            return 0;
        }
    }

}
