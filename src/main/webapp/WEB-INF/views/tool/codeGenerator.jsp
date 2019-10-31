<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>代码生成器--后台管理模板</title>
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
	  <legend>代码生成器表单配置</legend>
	  <div class="layui-field-box">
	    <form class="layui-form">
	    	<input type="hidden" name="pattern"  value="1"/>
	    	
	    	<div class="layui-form-item">
	    		<div class="layui-inline">
			    <label class="layui-form-label">数据库表</label>
			    <div class="layui-input-block">
	                <select name="tableName" lay-filter="tableName" lay-verify="required" id="tableName">
	                		<option value="">请选择要生成代码的表</option>
	                    <c:forEach items="${tableList }" var="table">
	                    	<option value="${table.tableName }">${table.tableName }</option>
	                    </c:forEach>
	                </select>
	            </div>
	            </div>
	            <div class="layui-inline">
		            <label class="layui-form-label">表主键</label>
				    <div class="layui-input-block">
		                <select name="pkColumn" lay-filter="pkColumn" lay-verify="required" id="pkColumn">
		                </select>
		            </div>
	            </div>
		     </div>
		     <div class="layui-form-item">
				<div style="display:none;">
				<select name="sortColumn"  lay-verify="required" id="sortColumn">
						</select>
				</div>
				<label class="layui-form-label"  style="color:#F00">Java类名称</label>
					<div class="layui-input-block">
						<input type="text" name="className" maxlength="100" lay-verify="required" placeholder="请输入Java类名称,首字母请大写" autocomplete="off" class="layui-input" style="width:50%"/>
					</div>
			 </div>
		 	<div class="layui-form-item">
				<label class="layui-form-label"  style="color:#F00">代码模块名</label>
			    <div class="layui-input-block">
					<input type="text" name="mouldName" maxlength="100" lay-verify="required" placeholder="请输入代码模块名，例如:sys、web、tool" autocomplete="off" class="layui-input" style="width:50%"/>
				 </div>
			 </div>
	    	 <div class="layui-form-item">
				<label class="layui-form-label"  style="color:#F00">功能说明</label>
			    <div class="layui-input-block">
					<input type="text" name="functionComment" maxlength="100" lay-verify="required" placeholder="请输入功能说明" autocomplete="off" class="layui-input" style="width:50%"/>
				 </div>
			 </div>
			  <div class="layui-form-item">
				<label class="layui-form-label"  style="color:#F00">保存路径</label>
			    <div class="layui-input-block">
					<input type="text" name="classPath" value="d:/code/" maxlength="200" lay-verify="required" placeholder="请输入保存路径" autocomplete="off" class="layui-input" style="width:50%"/>
				 </div>
			 </div>
			<div class="layui-form-item">
				<label class="layui-form-label"  style="color:#F00">作者</label>
			    <div class="layui-input-block">
					<input type="text" name="author" value="" maxlength="100" lay-verify="required" placeholder="请输入作者姓名" autocomplete="off" class="layui-input" style="width:50%"/>
				 </div>
			 </div>
	    	<!-- 按钮组 -->
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="btnSubmit">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	  </div>
	</fieldset>
	
	<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
	<script type="text/javascript">
	var path = "${path}";
	</script>
	<script>
	layui.use(['form', 'layedit', 'laydate','jquery'], function(){
	  var form = layui.form;
	  var $ = layui.jquery;

	  
	  //监听数据库表下拉框
	  form.on('select(tableName)', function (data) {
		  var tableName=data.value;
		  //字段集合
		  var url='codeGenerator/getFieldList.do?tableName='+tableName;
		  $.ajax({
				url : '<%=request.getContextPath()%>/'+url,
				type:'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
				dataType : 'json',
				async: true,
				success : function(data) {
					//layer.msg(JSON.stringify(data));
					
					//表主键
					$("#pkColumn").html("");
					$("#pkColumn").append('<option value="">请选择</option>');
					for(var i=0;i<data.length;i++){
						$("#pkColumn").append('<option value='+data[i].columnName+'>'+data[i].columnName+'</option>');
					}
					
					 //排序字段
				     $("#sortColumn").html("");
					 var sortUrl='codeGenerator/getFieldList.do?tableName='+tableName;
					 $.ajax({
							url : '<%=request.getContextPath()%>/'+sortUrl,
							type:'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
							dataType : 'json',
							async: true,
							success : function(data) {
								$("#sortColumn").html("");
								$("#sortColumn").append('<option selected="" value="">请选择</option>');
								for(var i=0;i<data.length;i++){
									$("#sortColumn").append('<option selected="" value='+data[i].columnName+'>'+data[i].columnName+'</option>');
								}
								form.render('select');//重新渲染
							}
					 });
					form.render('select');//重新渲染
				}
				
			});
		  
	  });
	  
	   //监听提交
	   form.on('submit(btnSubmit)', function(data){
	    // layer.msg(JSON.stringify(data.field));
	   var addmenu = $("#addmenu").val();
	   var pidSelect = $("#pidSelect").val();
	   if (addmenu == "1" && pidSelect == "") {
	    pubUtil.msg("请选择上一级菜单", layer, 2, function() {
			}, 5 * 1000);
	      return false;
		}
	   var index = layer.load(1);//开启进度条
	    $.ajax({
		url : '<%=request.getContextPath()%>/codeGenerator/createCode.do',
				data : data.field,
				type : 'POST',//默认以get提交，以get提交如果是中文后台会出现乱码
				dataType : 'json',
				success : function(obj) {
					layer.close(index);//关闭   
					if (obj.code == '0') {
						layer.msg('代码生成成功', {icon: 1});
						
					} else {
						layer.msg('代码生成失败', {icon: 5});
						
					}
				}
			});
			return false;
		});

	}); 
	</script>
</body>
</html>