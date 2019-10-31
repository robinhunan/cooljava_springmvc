package com.controller.test;

import com.model.user.User;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @功能说明：测试Echarts
 * @作者：YangPiao
 * @创建日期：2019-09-02
 */
@Controller
@RequestMapping("/chart")
public class EchartsController {

    /**
     * YpTest列表跳转页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, Model model) {
        return "views/sys/chart";
    }


    //柱状图
    @RequestMapping("/bar")
    @ResponseBody
    public String bar()
    {

        List<String> xAxisData = new ArrayList<String>();
        List<JSONObject> seriesList = new ArrayList< JSONObject>();
        for (int i = 1; i < 13; i++)
        {
            xAxisData.add(i+"月");
        }
        for (int i = 1; i < 4; i++)
        {
            List<Integer> list = new ArrayList<Integer>();
            for (int j = 1; j < 13; j++)
            {
                list.add((int)(Math.random()*100));
            }
            Series series = new Series("销售"+i, "line", list);
            JSONObject job = new JSONObject();
            job.put("name", series.toName());
            job.put("type", "bar");
            job.put("data",series.data);
            seriesList.add(job);
        }
        //xAxisData和seriesList转为json
        JSONObject jsob = new JSONObject();
        jsob.put("xAxisData", xAxisData);
        jsob.put("seriesList", seriesList);
        System.out.println("---------------------------------");
        System.out.println(jsob.toString());
        System.out.println("---------------------------------");
        return jsob.toString();
    }

    //饼状图
    @RequestMapping("/pie")
    @ResponseBody
    public String pie()
    {

        List<String> xAxisData = new ArrayList<String>();       //
        List<JSONObject> seriesList = new ArrayList< JSONObject>();
        for (int i = 1; i < 13; i++)
        {
            xAxisData.add(i+"月");
        }
        for (int i = 1; i < 4; i++)
        {
            List<Integer> list = new ArrayList<Integer>();
            for (int j = 1; j < 13; j++)
            {
                list.add((int)(Math.random()*100));
            }
            Series series = new Series("销售"+i, "line", list);
            JSONObject job = new JSONObject();
            job.put("name", series.toName());
            job.put("type", "bar");
            job.put("data",series.data);
            seriesList.add(job);
        }
        //xAxisData和seriesList转为json
        JSONObject jsob = new JSONObject();
        jsob.put("xAxisData", xAxisData);
        jsob.put("seriesList", seriesList);
        System.out.println("---------------------------------");
        System.out.println(jsob.toString());
        System.out.println("---------------------------------");
        return jsob.toString();
    }

}

class Series
{
    public String name;
    public String type;
    public List<Integer> data;
    public Series(String name, String type, List<Integer> data)
    {
        this.name = name;
        this.type = type;
        this.data = data;
    }
    public String toName()
    {
        return name;
    }

}
