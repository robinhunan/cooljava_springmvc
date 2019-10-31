<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/inc/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Echarts测试</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
    <link rel="stylesheet" href="${path}/res/css/public.css" media="all"/>
</head>
<body class="p10">
<div class="row">
    <div class="sysNotice col">
        <blockquote class="layui-elem-quote title">图表测试案例</blockquote>
        <div class="layui-elem-quote layui-quote-nm">
            <div id="main" style="width: 100%;height:500px;"></div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
<script type="text/javascript" src="${path}/res/js/other-js/echarts.js"></script><!-- 图表js -->
<script type="text/javascript">

    layui.use(['form', 'layer', 'laydate', 'table', 'upload'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : top.layer,
            $ = layui.jquery,
            laydate = layui.laydate,
            upload = layui.upload,
            table = layui.table;


        var myChart = echarts.init(document.getElementById('main'));
        // 显示标题，图例和空的坐标轴
        option = {
            title: {
                text: '2016年三位销售员业绩',
                subtext: '数据为虚构'
            },
            tooltip: {},
            legend: {
                data: ["销售1", "销售2", "销售3"]
            },
            toolbox:
                {
                    show: true,
                    feature:
                        {
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                },
            xAxis: {
                type: "category",
                data: []
            },
            yAxis: [{
                type: "value",
            }],
            series: [
                {
                    name: "销售1",
                    type: "bar",
                    data: []
                },
                {
                    name: "销售2",
                    type: "bar",
                    data: []
                },
                {
                    name: "销售3",
                    type: "bar",
                    data: []
                }
            ]
        };
        myChart.showLoading(); //loading动画

        var path = "${path}";

        $.ajax({
            type: "get",
            async: true,
            url: path + '/chart/bar.do',
            success: function (result) {

                if (result) {
                    var obj = eval('(' + result + ')');
                    myChart.hideLoading();
                    myChart.setOption({
                        xAxis: {
                            data: obj.xAxisData
                        },
                        series: obj.seriesList
                    });
                }

            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                alert("请求数据失败!");
                myChart.hideLoading();
            }
        });

        myChart.setOption(option);
    });


</script>
</body>
</html>