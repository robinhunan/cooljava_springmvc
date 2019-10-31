package com.controller.tool;

import com.model.base.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    /**
     * 发送消息 - 测试
     */
    @RequestMapping("/sendTest")
    @ResponseBody
    public AjaxResult sendTest(HttpServletRequest request) {

        kafkaTemplate.sendDefault("Im from producer ！");
        System.out.println("send success !");
        AjaxResult result=new AjaxResult();
        result.setMsg("发送成功");
        result.setCode("0");
        return result;

    }

}
