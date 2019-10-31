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
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" href="${path}/res/css/public.css" media="all" />
</head>
<body class="p10">
<fieldset class="layui-elem-field layui-field-title site-title">
   <legend><a>编辑数据字典</a></legend>
 </fieldset>
<form class="layui-form">
	<input type="hidden" name="id" value="${dict.id }">
	<div class="layui-form-item">
		<label class="layui-form-label">键值</label>
		<div class="layui-input-block">
			<input type="text" name="value" value="${dict.value }" class="layui-input" lay-verify="required" placeholder="请输入键值" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">标签</label>
		<div class="layui-input-block">
			<input type="text" name="label" value="${dict.label }" class="layui-input" lay-verify="required" placeholder="请输入标签" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">类型</label>
		<div class="layui-input-block">
			<input type="text" name="type" value="${dict.type }" class="layui-input" lay-verify="required" placeholder="请输入类型" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">描述</label>
		<div class="layui-input-block">
			<input type="text" name="description" value="${dict.description }" class="layui-input" lay-verify="required" placeholder="请输入描述" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">排序</label>
		<div class="layui-input-block">
			<input type="text" name="sort" value="${dict.sort }" class="layui-input" lay-verify="required" placeholder="请输入排序" />
		</div>
	</div>
	<div class="layui-form-item">
		<button class="layui-btn layui-block" lay-filter="addLink" lay-submit>提交</button>
	</div>
</form>
<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
<script type="text/javascript" src="${path}/res/js/cool-js/dictList.js"></script>
<script type="text/javascript">
	var path = "${path}";
</script>
</body>
</html>