<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>首页--CoolJava后台管理模板</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
	<link rel="stylesheet" href="${path}/res/css/cool-css/font_eolqem241z66flxr.css" media="all" />
	<link rel="stylesheet" href="${path}/res/css/cool-css/main.css" media="all" />
</head>
<body class="p10">
	<div class="panel_box row">
		<div class="panel col">
			<a href="javascript:;" data-url="page/message/message.html">
				<div class="panel_icon">
					<i class="layui-icon" data-icon="&#xe63a;">&#xe63a;</i>
				</div>
				<div class="panel_word newMessage">
					<span>11</span>
					<cite>新消息</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/user/allUsers.html">
				<div class="panel_icon" style="background-color:#FF5722;">
					<i class="iconfont icon-dongtaifensishu" data-icon="icon-dongtaifensishu"></i>
				</div>
				<div class="panel_word userAll">
					<span>22</span>
					<cite>新增人数</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/user/allUsers.html">
				<div class="panel_icon" style="background-color:#009688;">
					<i class="layui-icon" data-icon="&#xe613;">&#xe613;</i>
				</div>
				<div class="panel_word userAll">
					<span>33</span>
					<cite>用户总数</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/img/images.html">
				<div class="panel_icon" style="background-color:#5FB878;">
					<i class="layui-icon" data-icon="&#xe64a;">&#xe64a;</i>
				</div>
				<div class="panel_word imgAll">
					<span>44</span>
					<cite>图片总数</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/news/newsList.html">
				<div class="panel_icon" style="background-color:#F7B824;">
					<i class="iconfont icon-wenben" data-icon="icon-wenben"></i>
				</div>
				<div class="panel_word waitNews">
					<span>55</span>
					<cite>待审核文章</cite>
				</div>
			</a>
		</div>
		<div class="panel col max_panel">
			<a href="javascript:;" data-url="page/news/newsList.html">
				<div class="panel_icon" style="background-color:#2F4056;">
					<i class="iconfont icon-text" data-icon="icon-text"></i>
				</div>
				<div class="panel_word allNews">
					<span>66</span>
					<em>文章总数</em>
					<cite>文章列表</cite>
				</div>
			</a>
		</div>
	</div>
	<div class="row">
		<div class="sysNotice col">
			<blockquote class="layui-elem-quote title">CoolJava更新日志</blockquote>
			<div class="layui-elem-quote layui-quote-nm">
				<h3>V2.1 - 2019-10-27</h3>
				<p>* 添加了shiro进行系统的权限管理，将权限控制到按钮</p>
				<p>* 数据库支持了SQLserver,oracle,只需修改配置文件即可实现数据库之间的转换。</p>
				<p>* 系统工具中加入了定时任务管理，轻松实现系统调度问题。</p>
				<p>* 修复若干bug。</p>
			</div>
		</div>
		<div class="sysNotice col">
			<blockquote class="layui-elem-quote title">系统概览</blockquote>
			<table class="layui-table">
				<colgroup>
					<col width="150">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<td>系统名称</td>
						<td class="version">CoolJava</td>
					</tr>
					<tr>
						<td>版本信息</td>
						<td class="homePage">CoolJava v2.0.0</td>
					</tr>
					
					<tr>
						<td>服务器环境</td>
						<td class="server">Jdk 1.8 + Tomcat 8.0 + MySQL5.7</td>
					</tr>
					<tr>
						<td>官网地址</td>
						<td class="dataBase"><a href="https://www.github.com/robinhunan/cooljava" target="_blank">https://www.github.com</a></td>
					</tr>
					
				</tbody>
			</table>
		</div>
		<div class="sysNotice col">
			<blockquote class="layui-elem-quote title">图表实例</blockquote>
			<div class="layui-elem-quote layui-quote-nm">
				<div id="main" style="width: 500px;height:250px;"></div>
			</div>
		</div>
	</div>
	<script src="${path}/res/layui/layui.js"></script>
	<script src="${path}/res/js/other-js/echarts.js"></script><!-- 图表js -->
	 <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
			title : {
				text: '某地区蒸发量和降水量',
				subtext: '纯属虚构'
			},
			tooltip : {
				trigger: 'axis'
			},
			legend: {
				data:['蒸发量','降水量']
			},
			toolbox: {
				show : true,
				feature : {
					mark : {show: true},
					dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [
				{
					type : 'category',
					data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				}
			],
			yAxis : [
				{
					type : 'value'
				}
			],
			series : [
				{
					name:'蒸发量',
					type:'bar',
					data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
					markPoint : {
						data : [
							{type : 'max', name: '最大值'},
							{type : 'min', name: '最小值'}
						]
					},
					markLine : {
						data : [
							{type : 'average', name: '平均值'}
						]
					}
				},
				{
					name:'降水量',
					type:'bar',
					data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
					markPoint : {
						data : [
							{name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
							{name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
						]
					},
					markLine : {
						data : [
							{type : 'average', name : '平均值'}
						]
					}
				}
			]
		};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
</body>
</html>