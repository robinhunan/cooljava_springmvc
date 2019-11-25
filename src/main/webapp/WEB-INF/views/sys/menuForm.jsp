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
	<meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
	<link rel="stylesheet" href="${path}/res/font-awesome-4.7.0/css/font-awesome.css" />
</head>
<style type="text/css" media="screen">
	.layui-form .layui-form-label{
		padding-left: 15px;
		color: #666666;
	}
	.layui-form .layui-input-block{
		width: 300px;
	}
	.layui-form .layui-input-block input,.layui-form .layui-input-block textarea{
		color: #aaaaaa;
	}
</style>
<body>
<div class="layui-fluid">

    <fieldset class="layui-elem-field layui-field-title site-title">
      <legend><a>菜单编辑</a></legend>
    </fieldset>
  <form class="layui-form">
  		<input type="hidden" name="id" value="${menu.id }">
  		<input type="hidden" name="pid" value="${menu.pid }">
  		<div class="layui-form-item">
			<label class="layui-form-label">上级菜单</label>
			<div class="layui-input-block" style="padding-top:8px;">
				${menuP.title }
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">菜单名称</label>
			<div class="layui-input-block">
				<input type="text" name="title" value="${menu.title }" class="layui-input linksName" lay-verify="required" placeholder="请输入菜单名称">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">方法地址</label>
			<div class="layui-input-block">
				<input type="tel" name="href" value="${menu.href }" class="layui-input linksUrl" lay-verify="" placeholder="请输入方法地址">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">图标</label>
			<div class="" style="padding-top:8px;">
				<input type="hidden" id="icon" name="icon" value="${menu.icon}" class="layui-input " lay-verify="" placeholder="请输入图标代号">
				<i id="icno_i" class="fa ${menu.icon }"></i>
				<a id="Button" class="layui-btn layui-btn-xs">选择图标</a>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">排序</label>
			<div class="layui-input-block">
				<input type="text" name="sort" value="${menu.sort }" class="layui-input masterEmail" lay-verify="required" placeholder="请输入排序">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限标识</label>
			<div class="layui-input-block">
				<input type="text" name="permission" value="${menu.permission }" class="layui-input" lay-verify="" placeholder="请输入权限标识">
			</div>
		</div>
		<div class="layui-form-item">
		    <label class="layui-form-label">菜单类型</label>
		    <div class="layui-input-block">
		      <select name="type" lay-verify="required" id="sel">
	        	<option value="0" checked>目录菜单</option>
	        	<option value="1">权限菜单</option>
		      </select>
		    </div>
		 </div>
		<div class="layui-form-item">
				<button class="layui-btn" lay-submit lay-filter="add">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</form>


</div>

<!-- 加载js文件 -->
<script type="text/javascript" src="${path}/res/layui/layui.js"></script> 
<script type="text/javascript">
	var path = "${path}";
	var type = "${menu.type}";
	
	layui.use(['form','layer','jquery'],function(){
		   var $ = layui.$,
		   form = layui.form,
		   layer = parent.layer === undefined ? layui.layer : top.layer;
		   
		   //select赋值
		   $("#sel").find("option[value='" + type + "']").attr("selected",true);
		   form.render('select');
		   
			$("#Button").click(function(){
				layer.open({
					type: 2, 
					title:"选择图标",
					area: ['700px',  $(top.document).height()-280+"px"],
				    content: path +'/menu/iconselect.do',
				    btn: ['确定', '关闭'],
				    yes: function(index, layero){ //或者使用btn1
		                var icon = layero.find("iframe")[0].contentWindow.$("#icon").val();
				    	$("#icno_i").attr("class", "fa "+icon);
				    	$("#icon").val(icon);
		                layer.close(index);
				    },cancel: function(index){ //或者使用btn2
				    	
				    },success: function(layero, index){
				    }
				});
				
			});
		   
		   form.on('submit(add)',function(data){
		     var ajaxReturnData;
		        //登陆验证
		        $.ajax({
		            url: path + '/menu/save.do',
		            type: 'post',
		            async: false,
		            data: data.field,
		            success: function (data) {
		                ajaxReturnData = data;
		            }
		        });
		        //结果回应
		        if (ajaxReturnData == '0') {
		        	top.layer.msg('保存成功', {icon: 1});
		            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		            parent.layer.close(index);
		            parent.location.reload();
		        } else {
		        	top.layer.msg('保存失败', {icon: 5});
		        }
		        return false;
		   });
		});
</script>
</body>
</html>
