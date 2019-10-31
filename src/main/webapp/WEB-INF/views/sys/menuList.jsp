<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>菜单管理</title>
	<meta name="keywords" content="" />
    <meta name="description" content="CoolJava Version:2.0" />
	<meta name="Author" content="" />
	<meta name="renderer" content="webkit">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">	
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
  	<link rel="stylesheet" href="${path}/res/font-awesome-4.7.0/css/font-awesome.css" />
    <script src="${path}/res/zTree/js/jquery-1.4.4.min.js"></script>
    <script src="${path}/res/zTree/js/jquery.ztree.all.js"></script>
    <script src="${path}/res/zTree/js/jquery.ztree.core.js"></script>
	<link href="${path}/res/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
	
</head>
<body>
<div class="layui-fluid">
	<div class="">  
		  <div class="layui-row">
		    <div class="layui-col-md2">
				<!-- 左边 开始-->
				<div id="treeContainer" class="treeContainer">
				    <ul id="tree" class="ztree"></ul>
				</div>
				<!-- 左边 结束-->
		    </div>
		    <div class="layui-col-md10">
		    	<!-- 右边 开始 -->
			       <blockquote class="layui-elem-quote menu-btn">
					<div class="layui-inline">
					    <div class="layui-input-inline">
					    	<input type="text" name="search" value="" id="search_input" placeholder="请输入菜单名称" class="layui-input ">
					    </div>
					    <a class="layui-btn search_btn" data-type="getSelect"><i class="layui-icon">&#xe615;</i>查询</a>
					</div>
					<div class="layui-inline">
						<a class="layui-btn layui-bg-pale" data-type="addLink"><i class="layui-icon">&#xe608;</i>添加顶级菜单</a>
					</div>
					<div class="layui-inline">
						<a class="layui-btn layui-btn-danger" data-type="delLink"><i class="layui-icon">&#xe640;</i>批量删除</a>
					</div>
				</blockquote>
				<!-- 友链数据列表 -->
				<div class="flinkTable">
					<table class="layui-table" id="flinklist" lay-filter="flinkTables"></table>
				</div>
		    	<!-- 右边 结束-->
		    </div>
		  </div>
	 </div>
</div>
<script type="text/html" id="flinkbar">
  <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
  <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="add"><i class="layui-icon">&#xe608;</i>添加下一节点</a>
</script>
<script type="text/html" id="type">
		 {{#  if(d.type === '0'){ }}
     		 目录菜单
  		{{#  } else { }}
			权限菜单
  		{{#  } }}
</script>
<script type="text/html" id="linkTpl">
	<i class="fa {{d.icon}}"></i>
</script>
<!-- 加载js文件 -->
<script src="${path}/res/layui/layui.js"></script>
<script src="${path}/res/js/cool-js/menu.js"></script>
<script type="text/javascript">
	var path = "${path}";
	$(document).ready(function(){
		//加载树
		loadTree('1');
	});
</script>
</body>
</html>