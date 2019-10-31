<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>角色管理</title>
	<meta name="keywords" content="" />
    <meta name="description" content="CoolJava Version:2.0" />
	<meta name="Author" content="" />
	<meta name="renderer" content="webkit">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">	
	<meta name="apple-mobile-web-app-capable" content="yes">
	<link rel="Shortcut Icon" href="/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
	<link rel="stylesheet" href="${path}/res/font-awesome-4.7.0/css/font-awesome.css" />
    <!-- ztree -->
    <script src="${path}/res/zTree/js/jquery-1.4.4.min.js"></script>
    <script src="${path}/res/zTree/js/jquery.ztree.all.js"></script>
    <script src="${path}/res/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${path}/res/zTree/js/jquery.ztree.excheck.js"></script>
	<link href="${path}/res/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="layui-row">
    <div class="layui-col-md9">
      		<div class="layui-fluid ">
			    <div class="layui-row">
			    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
			    		<fieldset class="layui-elem-field layui-field-title site-title">
			                <legend><a name="color-design">角色管理</a></legend>
			            </fieldset>
			            <div class="layui-btn-group" id="group-btn">
			                <button class="layui-btn"  data-type="add"><i class="layui-icon">&#xe61f;</i><cite>增加角色</cite></button>
			            </div>
			    	</div>
			
			    	<div class="layui-col-lg10 layui-col-md10 layui-col-sm12 layui-col-xs12">
			    		<div class="user-tables">
			    			<table id="userTables" lay-filter="userTables"></table>
			    		</div>
			    	</div>
			    </div>
			</div>
    </div>
    <div class="layui-col-md3">
     	<div class="layui-fluid">
		    <fieldset class="layui-elem-field layui-field-title site-title">
		      <legend><a>权限编辑</a></legend>
		    </fieldset>
		    <div style="float:right;"><button class="layui-btn" onclick="onCheck();">保存权限</button></div>
		    <div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
    </div>
  </div>

<script type="text/html" id="userbar">
  <a class="layui-btn layui-btn-xs" lay-event="edit"> <i class="layui-icon">&#xe642;</i>编辑</a>
  <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="shouquan"><i class="layui-icon">&#xe620;</i>权限设置</a>
  {{#  if(d.useable === '0'){ }}
     	<a class="layui-btn layui-btn-warm  layui-btn-xs" lay-event="disable"><i class="fa fa-ban"></i>&nbsp;禁用</a>
  {{#  } else { }}
		<a class="layui-btn layui-btn-warm  layui-btn-xs" lay-event="able"><i class="fa fa-circle-o"></i>&nbsp;置为可用</a>
  {{#  } }}
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
</script>
<script type="text/html" id="img">
  {{#  if(d.img === undefined){ }}
      未知
  {{#  } else { }}
		<img id="img" alt="" src="/basicManage{{d.img}}" width="50px" height="50px" />
  {{#  } }}
</script>
<script type="text/html" id="status">
  {{#  if(d.useable === undefined){ }}
      未知
  {{#  } else { }}
		 {{#  if(d.useable === '0'){ }}
     		 可用
  		{{#  } else { }}
			禁用
  		{{#  } }}
  {{#  } }}
</script>
<!-- 加载js文件 -->
<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
<script type="text/javascript" src="${path}/res/js/cool-js/role.js"></script> 
<script type="text/javascript">
	var path = "${path}";
	var roleId ="";
	layui.config({
		base: path + '/common/lib/'
	});
</script>
</body>
</html>