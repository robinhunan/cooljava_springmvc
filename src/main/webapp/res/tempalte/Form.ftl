<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>CoolJava后台管理模板</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" href="${r"${path}"}/res/css/public.css" />
</head>
<body class="p10">
<fieldset class="layui-elem-field layui-field-title site-title">
   <legend><a>编辑${functionComment}</a></legend>
</fieldset>
 
<form class="layui-form">
	<input type="hidden" name="id" value="${r"${"}${classNameToL}.id ${r"}"}">
	<#list cloums as c>
		<div class="layui-form-item">
			<label class="layui-form-label">${c.columnComment}</label>
			<div class="layui-input-block">
				<input type="text" name="${c.columnName}" value="${r"${"}${classNameToL}.${c.columnName} ${r"}"}" class="layui-input" lay-verify="required" placeholder="请输入${c.columnComment}" />
			</div>
		</div>
	</#list>
	
	<div class="layui-form-item tc">
		<button class="layui-btn" lay-filter="send" lay-submit>提交</button>
		<button type="reset" class="layui-btn layui-btn-primary">重置</button>
	</div>
</form>
<script type="text/javascript" src="${r"${path}"}/res/layui/layui.js"></script>
<script type="text/javascript" src="${r"${path}"}/res/js/cool-js/${classNameToL}List.js"></script>
<script type="text/javascript">
	var path = "${r"${path}"}";
</script>
</body>
</html>