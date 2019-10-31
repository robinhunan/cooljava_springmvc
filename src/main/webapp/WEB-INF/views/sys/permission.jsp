<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>权限编辑</title>
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
    
    <!-- ztree -->
    <script src="${path}/res/zTree/js/jquery-1.4.4.min.js"></script>
    <script src="${path}/res/zTree/js/jquery.ztree.all.js"></script>
    <script src="${path}/res/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${path}/res/zTree/js/jquery.ztree.excheck.js"></script>
	<link href="${path}/res/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
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
      <legend><a>权限编辑</a></legend>
    </fieldset>
    <div style="float:right;"><button class="layui-btn" onclick="onCheck();">保存权限</button></div>
    <div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>

		
</div>

<!-- 加载js文件 -->
<script type="text/javascript" src="${path}/res/layui/layui.js"></script> 
<SCRIPT type="text/javascript">
	var path = "${path}";
	var roleId = "${role.id}";
	
	$(document).ready(function(){
		//加载树
		loadTree();
	});

	//树加载
	function loadTree(){
	    $.ajax({
	        type: "post",
	        dataType: "json",
	        traditional: true,
	        data: { roleId: roleId },
	        url: path + "/menu/dataCheckJson.do",
	        async: true,//表示同步执行
	        success: function (data) {
	            if (data != null) {
	            	$.fn.zTree.init($("#treeDemo"), setting, data);
	            }

	        }
	    });
	} 
	
	
		var setting = {
			check: {
		        chkboxType:{"Y":"ps","N":"ps"},//勾选checkbox对于父子节点的关联关系,取消勾选时不关联父  
		        chkStyle:"checkbox",
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var code;
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		//获取选中节点  
		function onCheck(){  
		     var rid = roleId;  
		     var treeObj=$.fn.zTree.getZTreeObj("treeDemo");  
		     var nodes=treeObj.getCheckedNodes(true);  
		     //var ids = new Array();
		     var ids = "";
		     for(var i=0;i<nodes.length;i++){ 
		    	// ids.push(nodes[i].id); 
		        //获取选中节点的值  
		    	 ids += nodes[i].id + ",";
		     }
		    ajaxSubmit(rid,ids);       
		}
		function ajaxSubmit(rid, ids){
			layer.msg('保存中', {
				  icon: 16
				  ,shade: 0.1
				});
			var ajaxReturnData;
	        //登陆验证
	        $.ajax({
	            url: path + '/role/savePermission.do',
	            type: 'post',
	            async: false,
	            data: {roleId:rid, ids:ids.toString()},
	            success: function (data) {
	                ajaxReturnData = data;
	            }
	        });
	        //结果回应
	        if (ajaxReturnData == '0') {
	        	top.layer.msg('保存成功', {icon: 1});
	            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	            parent.layer.close(index); //再执行关闭                        //刷新父页面
	            parent.location.reload();
	        } else {
	        	top.layer.msg('保存失败', {icon: 5});
	        }
		}
	</SCRIPT>
</body>
</html>