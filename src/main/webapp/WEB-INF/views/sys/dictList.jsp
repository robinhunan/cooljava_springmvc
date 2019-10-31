<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>数据字典--CoolJava后台管理系统</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" href="${path}/res/css/public.css" media="all" />
</head>
<body>
	<blockquote class="layui-elem-quote">
		<form class="layui-form">
			<div class="layui-inline">
				类型：
				<div class="layui-input-inline">
					<input type="text" class="layui-input type" placeholder="请输入搜索的内容" />
				</div>
				描述：
				<div class="layui-input-inline">
					<input type="text" class="layui-input description" placeholder="请输入搜索的内容" />
				</div>
				<a class="layui-btn search_btn" data-type="reload"><i class="layui-icon">&#xe615;</i>搜索</a>
			</div>
			<div class="layui-inline">
				<a class="layui-btn layui-btn-normal addLink_btn"><i class="layui-icon">&#xe608;</i>添加数据字典</a>
			</div>
			<div class="layui-inline">
				<a class="layui-btn layui-btn-danger layui-btn-normal delmatch-btn"><i class="layui-icon">&#xe640;</i>批量删除</a>
			</div>
		</form>
	</blockquote>
	<table class="layui-table" id="list" lay-filter="tables"></table>

	<script type="text/html" id="flinkbar">
      <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
  		{{#  if(d.status === '0'){ }}
     		<a class="layui-btn layui-btn-warm  layui-btn-xs" lay-event="disable"><i class="fa fa-ban"></i>&nbsp;禁用</a>
  		{{#  } else { }}
			<a class="layui-btn layui-btn-warm  layui-btn-xs" lay-event="able"><i class="fa fa-circle-o"></i>&nbsp;置为可用</a>
  		{{#  } }}
      <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
 	  <a class="layui-btn layui-btn-xs" lay-event="addV"><i class="layui-icon">&#xe608;</i>添加键值</a>
	</script>

	<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
	<script type="text/javascript" src="${path}/res/js/cool-js/dictList.js"></script>
	<script type="text/javascript">
		var path = "${path}";
	</script>
</body>
</html>