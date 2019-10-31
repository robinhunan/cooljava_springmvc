<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>登录 - layuiAdmin</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<link rel="stylesheet" href="${path}/res/layui/css/layui.css" media="all">
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
	<link rel="stylesheet" href="${path}/res/css/cool-css/login.css" media="all" />
	<script language="JavaScript">
        if (window != top)
            top.location.href = location.href;
	</script>
</head>
<body>

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">

	<div class="layadmin-user-login-main">
		<div class="layadmin-user-login-box layadmin-user-login-header">
			<h2>CoolJava</h2>
			<p>基于SSM和Layui的快速、通用、高效、开发框架</p>
		</div>
		<form action="" class="layui-form" method="post">
		<div class="layadmin-user-login-box layadmin-user-login-body layui-form">
			<div class="layui-form-item">
				<label class="layadmin-user-login-icon layui-icon layui-icon-username"></label>
				<input type="text" name="name" lay-verify="required" placeholder="用户名" class="layui-input">
			</div>
			<div class="layui-form-item">
				<label class="layadmin-user-login-icon layui-icon layui-icon-password"></label>
				<input type="password" name="pwd" lay-verify="required|password" placeholder="密码" class="layui-input">
			</div>
			<!--
            <div class="layui-form-item">
              <div class="layui-row">
                <div class="layui-col-xs7">
                  <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label>
                  <input type="text" name="vercode" id="LAY-user-login-vercode" lay-verify="required" placeholder="图形验证码" class="layui-input">
                </div>
                <div class="layui-col-xs5">
                  <div style="margin-left: 10px;">
                    <img src="https://www.oschina.net/action/user/captcha" class="layadmin-user-login-codeimg" id="LAY-user-get-vercode">
                  </div>
                </div>
              </div>
            </div>

            <div class="layui-form-item" style="margin-bottom: 20px;">
              <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
              <a href="javascript:;" class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;">忘记密码？</a>
            </div>

            -->
			<div class="layui-form-item">
				<button class="layui-btn layui-btn-fluid" lay-submit lay-filter="submit">登 入</button>
			</div>
			<div class="layui-trans layui-form-item layadmin-user-login-other">
				<label>社交账号登录</label>
				<a href="javascript:;"><i class="layui-icon layui-icon-login-qq"></i></a>
				<a href="javascript:;"><i class="layui-icon layui-icon-login-wechat"></i></a>
				<a href="javascript:;"><i class="layui-icon layui-icon-login-weibo"></i></a>

				<a href="javascript:;" class="layadmin-user-jump-change layadmin-link">注册帐号</a>
			</div>
		</div>
		</form>
	</div>

	<div class="layui-trans layadmin-user-login-footer">
		<p>© 2019 <a href="http://www.github.com/robinhunan/cooljava" target="_blank">CoolJava</a></p>
	</div>
</div>
<script type="text/javascript">
    var path = "${path}";
</script>
<script src="${path}/res/layui/layui.js"></script>
<script src="${path}/res/js/cool-js/login.js"></script>
</body>
</html>