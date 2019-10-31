<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>个人资料--CoolJava后台管理模板</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
	<link rel="stylesheet" href="${path}/res/font-awesome-4.7.0/css/font-awesome.css" />
	<link rel="stylesheet" href="${path}/res/css/cool-css/user.css" media="all" />
</head>
<body class="p10">
	<fieldset class="layui-elem-field">
	  <legend>个人信息</legend>
	  <div class="layui-field-box">
	    <form class="layui-form">
			<input type="hidden" name="id" value="${user.id }">
			<div class="user_left">
				<div class="layui-form-item">
				    <label class="layui-form-label">用户名</label>
				    <div class="layui-input-block">
				    	<input type="text" name="loginName" value="${user.loginName }"  disabled class="layui-input layui-disabled">
				    </div>
				</div>
				<!-- <div class="layui-form-item">
				    <label class="layui-form-label">用户组</label>
				    <div class="layui-input-block">
				    	<input type="text" value="超级管理员" disabled class="layui-input layui-disabled">
				    </div>
				</div> -->
				<div class="layui-form-item">
				    <label class="layui-form-label">真实姓名</label>
				    <div class="layui-input-block">
				    	<input type="text" name="name" value="${user.name }" placeholder="请输入真实姓名" lay-verify="required" class="layui-input">
				    </div>
				</div>
				<!-- <div class="layui-form-item" pane="">
				    <label class="layui-form-label">性别</label>
				    <div class="layui-input-block">
				    	<input type="radio" name="sex" value="男" title="男" checked="">
		     			<input type="radio" name="sex" value="女" title="女">
		     			<input type="radio" name="sex" value="保密" title="保密">
				    </div>
				</div> -->
				<div class="layui-form-item">
				    <label class="layui-form-label">手机号码</label>
				    <div class="layui-input-block">
				    	<input type="tel" name="mobile"  value="${user.mobile }" placeholder="请输入手机号码" lay-verify="required|phone" class="layui-input">
				    </div>
				</div>
				<!-- <div class="layui-form-item">
				    <label class="layui-form-label">出生年月</label>
				    <div class="layui-input-block">
				    	<input type="text" value="" placeholder="请输入出生年月" lay-verify="required|date" onclick="layui.laydate({elem: this,max: laydate.now()})" class="layui-input">
				    </div>
				</div>
				<div class="layui-form-item">
				    <label class="layui-form-label">家庭住址</label>
				    <div class="layui-input-inline">
		                <select name="province" lay-filter="province">
		                    <option value="">请选择省</option>
		                </select>
		            </div>
		            <div class="layui-input-inline">
		                <select name="city" lay-filter="city" disabled>
		                    <option value="">请选择市</option>
		                </select>
		            </div>
		            <div class="layui-input-inline">
		                <select name="area" lay-filter="area" disabled>
		                    <option value="">请选择县/区</option>
		                </select>
		            </div>
				</div>
				<div class="layui-form-item">
				    <label class="layui-form-label">兴趣爱好</label>
				    <div class="layui-input-block">
				    	<input type="checkbox" name="like1[javascript]" title="Javascript">
					    <input type="checkbox" name="like1[html]" title="HTML(5)">
					    <input type="checkbox" name="like1[css]" title="CSS(3)">
					    <input type="checkbox" name="like1[php]" title="PHP">
					    <input type="checkbox" name="like1[.net]" title=".net">
					    <input type="checkbox" name="like1[ASP]" title="ASP">
					    <input type="checkbox" name="like1[C#]" title="C#">
					    <input type="checkbox" name="like1[Angular]" title="Angular">
					    <input type="checkbox" name="like1[VUE]" title="VUE">
					    <input type="checkbox" name="like1[XML]" title="XML">
				    </div>
				</div> -->
				<div class="layui-form-item">
				    <label class="layui-form-label">邮箱</label>
				    <div class="layui-input-block">
				    	<input type="text" name="email"  value="${user.email }" placeholder="请输入邮箱" lay-verify="required|email" class="layui-input">
				    </div>
				</div>
				<!-- <div class="layui-form-item">
				    <label class="layui-form-label">自我评价</label>
				    <div class="layui-input-block">
				    	<textarea placeholder="请输入内容" class="layui-textarea"></textarea>
				    </div>
				</div> -->
			</div>
			<div class="user_right">
				<button type="button" class="layui-btn" id="test1">
				  <i class="layui-icon">&#xe67c;</i>上传图片
				</button> 
				<input type="file" name="file（可随便定义）" class="layui-upload-file">
				<p>请选择一张图片上传作为头像</p>
				<img src="${path}${user.img}" class="layui-circle" id="imgg" width="200px" height="200px">
				<input type="hidden" id="img" name="img" value="${user.img }">
			</div>
			<div class="layui-form-item" style="margin-left: 5%;">
			    <div class="layui-input-block">
			    	<button class="layui-btn" lay-submit="" lay-filter="changeUser"><i class="fa fa-save"></i>&nbsp;保存信息</button>
					<!-- <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
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