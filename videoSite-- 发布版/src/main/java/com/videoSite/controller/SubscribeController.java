package com.videoSite.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videoSite.entity.Notification;
import com.videoSite.entity.Subscribe;
import com.videoSite.service.SubscribeService;
import com.videoSite.utils.GetCurrentUserUtil;
import com.videoSite.utils.NotificationUtil;
import com.videoSite.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  前端控制器
 */
@RestController
@RequestMapping("/subscription")
public class SubscribeController {
    @Autowired
    NotificationUtil notificationUtil;
    @Autowired
    SubscribeService subscribeService;
    /*
    *   处理
    * */
    @GetMapping("/subscribe/{youtuber}")
    public void subscribe(@PathVariable("youtuber") String youtuber) throws InterruptedException, IOException {
        String currentSubscriber = GetCurrentUserUtil.getCurrentUserName();
        Map<String,Object> map = new HashMap<>();
        map.put("youtuber", youtuber);
        map.put("subscriber", currentSubscriber);
        //该订阅关系不存在才保存
        if(subscribeService.getOne(new QueryWrapper<Subscribe>().allEq(map)) == null){
        Subscribe subscribe=new Subscribe();
        subscribe.setSubscriber(currentSubscriber);
        subscribe.setYoutuber(youtuber);
        subscribe.setSubscribe_time(new Date());
        Process process = Runtime.getRuntime().exec("D:\\PyCharm 2021.2.3\\bin\\pycharm64.exe");
        process.waitFor();
        process.destroy();
        System.out.println("视频处理成功");
        //设置订阅该youtuber的 youtuber名:订阅者名 的键值对
         String url = "/user/toHome/"+currentSubscriber;
         Notification notification = new Notification(currentSubscriber,youtuber,new Date(),url,"关注了你");
         notificationUtil.Notify(youtuber, notification);
         subscribeService.save(subscribe);
        }
        else {
            System.out.println("已处理，不必重复处理");
        }

    }
    /*
    *   取消订阅
    * */
    @GetMapping("/undo_subscribe/{youtuber}")
    public void undo_subscribe(@PathVariable("youtuber" ) String youtuber){
        Map<String,Object> map=new HashMap<>();
        map.put("youtuber",youtuber);
        map.put("subscriber",GetCurrentUserUtil.getCurrentUserName());
        System.out.println("取消订阅成功");
        subscribeService.remove(new QueryWrapper<Subscribe>().allEq(map));

    }
    /*
    *  查询当前订阅状态
    * */
    @GetMapping("/status/{youtuber}/{subscriber}")
    public Integer status(@PathVariable("youtuber") String youtuber,
                          @PathVariable("subscriber")String subscriber ){
        Map<String,Object> map=new HashMap<>();
        map.put("youtuber",youtuber);
        map.put("subscriber",subscriber);
        Subscribe one = subscribeService.getOne(new QueryWrapper<Subscribe>().allEq(map));
        if (one != null){
            return 0;
        } else {
            return 1;
        }
    }
    /*
     *  查询已关注
     * */
    @GetMapping("/search_focus/{subscriber}")
    public List<Subscribe> getYoutubers(@PathVariable("subscriber") String subscriber){
         List<Subscribe> youtubers = subscribeService.list(new QueryWrapper<Subscribe>().eq("subscriber",subscriber));
         return youtubers;
    }
    /*
    *   查询粉丝
    * */
    @GetMapping("/search_fans/{youtuber}")
    public List<Subscribe> getSubscribers(@PathVariable ("youtuber")String youtuber){
        List<Subscribe> youtubers = subscribeService.list(new QueryWrapper<Subscribe>().eq("youtuber",youtuber));
        return youtubers;
    }
}
