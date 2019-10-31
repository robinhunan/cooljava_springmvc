<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>修改密码--CoolJava后台管理系统</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
	<link rel="stylesheet" href="${path}/res/css/cool-css/user.css" media="all" />
</head>
<body class="p10">
	<fieldset class="layui-elem-field">
	  <legend>修改密码</legend>
	  <div class="layui-field-box">
	    	<form class="layui-form changePwd" method="post" action="">
			<input type="hidden" name="id" value="${user.id }">
			<div style="margin:0 0 15px 110px;color:#f00;">新密码必须两次输入一致才能提交</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">用户名</label>
			    <div class="layui-input-block">
			    	<input type="text" value="${user.loginName }" disabled class="layui-input layui-disabled">
			    </div>
			</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">旧密码</label>
			    <div class="layui-input-block">
			    	<input type="password" name="pwd" value="" placeholder="请输入旧密码" lay-verify="required" class="layui-input pwd">
			    </div>
			</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">新密码</label>
			    <div class="layui-input-block">
			    	<input type="password" name="pwd1" value="" placeholder="请输入新密码" lay-verify="required|newPwd" id="oldPwd" class="layui-input pwd">
			    </div>
			</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">确认密码</label>
			    <div class="layui-input-block">
			    	<input type="password" name="pwd2" value="" placeholder="请确认密码" lay-verify="required|confirmPwd" class="layui-input pwd">
			    </div>
			</div>
			<div class="layui-form-item">
			    <div class="layui-input-block">
			    	<button class="layui-btn" lay-submit="" lay-filter="changePwd">立即修改</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
			</div>
		</form>
	  </div>
	</fieldset>

	<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
	<script type="text/javascript" src="${path}/res/js/cool-js/address.js"></script>
	<script type="text/javascript" src="${path}/res/js/cool-js/userInfo.js"></script>
	<script type="text/javascript">
		var path = "${path}";
	</script>
</body>
</html>