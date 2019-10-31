<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>机构管理</title>
    <link rel="stylesheet" type="text/css" href="${path}/res/css/public.css" />
    <link rel="stylesheet" type="text/css" href="${path}/res/layui/css/layui.css" />
    <style type="text/css">
	.treeTable-empty {
		width: 20px;
		display: inline-block;
	}
	
	.treeTable-icon {
		cursor: pointer;
	}
	
	.treeTable-icon .layui-icon-triangle-d:before {
		content: "\e623";
	}
	
	.treeTable-icon.open .layui-icon-triangle-d:before {
		content: "\e625";
		background-color: transparent;
	}
	
	.layui-icon {
		font-family: layui-icon !important;
		font-size: 16px;
		font-style: normal;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
	}

</style>
</head>
<body>
<div class="layui-fluid">
	 <div class="layui-row">
	 
    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<fieldset class="layui-elem-field layui-field-title site-title">
                <legend><a name="color-design">机构管理</a></legend>
            </fieldset>
            <div class="layui-btn-group" id="group-btn">
            	 <div class="layui-btn-group">
	            	<button class="layui-btn" id="btn-expand">全部展开</button>
			        <button class="layui-btn" id="btn-fold">全部折叠</button>
			        <button class="layui-btn" id="btn-refresh">刷新表格</button>
		        </div>
		          &nbsp;
            </div>
            <div class="layui-input-inline">
					<input  type="text" class="layui-input" id="keyword"  placeholder="请输入搜索的内容" />
				</div>
    			<button class="layui-btn" id="btn-search">&nbsp;&nbsp;搜索&nbsp;&nbsp;</button>
    	</div>
 				
    	<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
    		<div class="user-tables">
    			  <table id="list" class="layui-table" lay-filter="tables"></table>
    		</div>
    	</div>
    </div>

</div>
<!-- 操作列 -->
<script type="text/html" id="oper-col">
  <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon">&#xe640;</i>删除</a>
  <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="add"><i class="layui-icon">&#xe608;</i>添加下一节点</a>
</script>
<script type="text/html" id="type">
		 {{#  if(d.type === '0'){ }}
     		 公司机构
  		{{#  } else { }}
			部门机构
  		{{#  } }}
	</script>
<script type="text/javascript" src="${path}/res/layui/layui.js"></script>
<script type="text/javascript">
	var path = "${path}";
</script>
<script type="text/javascript" src="${path}/res/js/cool-js/orgList.js"></script>
</body>
</html>