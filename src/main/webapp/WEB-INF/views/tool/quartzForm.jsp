<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>CoolJava后台管理模板 </title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
	<link rel="stylesheet" href="${path}/res/css/public.css" media="all" />
</head>
<body>
<div class="layui-fluid">
<fieldset class="layui-elem-field layui-field-title site-title">
   <legend><a>编辑定时任务</a></legend>
 </fieldset>
 
<form class="layui-form">
	<input type="hidden" name="id" value="${quartz.id }">
		<div class="layui-form-item">
			<label class="layui-form-label">定时器名称</label>
			<div class="layui-input-block">
				<input type="text" name="jobName" value="${quartz.jobName }" class="layui-input" lay-verify="required" placeholder="请输入定时器名称" style="width:40%"/>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">所属组</label>
			<div class="layui-input-block">
				<input type="text" name="jobGroup" value="${quartz.jobGroup }" class="layui-input" lay-verify="required" placeholder="请输入所属组" style="width:40%"/>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">类路径</label>
			<div class="layui-input-block">
				<input type="text" name="classPath" value="${quartz.classPath }" class="layui-input" lay-verify="required" placeholder="请输入类路径" style="width:40%"/>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">cron表达式</label>
			<div class="layui-input-block">
				<input type="text" name="cronStr" value="${quartz.cronStr }" class="layui-input" lay-verify="required" placeholder="请输入cron表达式" style="width:40%"/>
				表达式编写可使用cron生成器
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<input type="text" name="mark" value="${quartz.mark }" class="layui-input" lay-verify="required" placeholder="请输入备注" style="width:40%"/>
			</div>
		</div>
	

	<div class="layui-form-item" style="padding-left:230px;">
			<button class="layui-btn" lay-submit lay-filter="addLink">立即提交</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
	</div>
</form>
</div>
<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
<script type="text/javascript" src="${path}/res/js/cool-js/quartzList.js"></script>
<script type="text/javascript">
	var path = "${path}";
</script>
</body>
</html>