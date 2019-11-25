layui.use(['layer', 'form', 'table'], function() {
	var $ = layui.$,
        layer = parent.layer === undefined ? layui.layer : top.layer,
		form = layui.form,
		table = layui.table;
	
	var tableIns = table.render({
		elem: '#userTables',
		id: 'userTables',
		width: 776,
		cols: [
			[{
				checkbox: true,
				width: 60,
				fixed: true
			}, {
				field: 'id',
				width: 80,
				title: 'ID',
				sort: true,
				// fixed: true
			},{
				field: 'name',
				width: 150,
				title: '角色名称',
				align: 'center',
			}, {
				field: 'useable',
				width: 120,
				title: '状态',
				align: 'center',
				templet: '#status'
			},{
				title: '常用操作',
				width: 360,
				align: 'center',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: path + '/role/data.do',
		page: true,
		even: true,

	});

	//监听工具条
	table.on('tool(userTables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') {
			var index = layer.open({
				title: "角色信息修改",
				type: 2,
				skin:'',
				offset: ['85px', '530px'],
				area: ['540px', '350px'],
				content: path + "/role/form.do?id="+data.id,
			});
			
		}else if (obj.event === 'shouquan') {
			loadTree(data.id);
			roleId = data.id;
		}else if (obj.event === 'disable') {
			layer.confirm('真的禁用角色么', function(index) {
				var ajaxReturnData;
		        $.ajax({
		            url: path + '/role/setUse.do',
		            type: 'post',
		            async: false,
		            data: {id:data.id},
		            success: function (data) {
		                ajaxReturnData = data;
		            }
		        });
		        //删除结果
		        if (ajaxReturnData == '0') {
		            table.reload('userTables');
		            layer.msg('操作成功', {icon: 1});
		        } else {
		        	layer.msg('操作失败', {icon: 5});
		        }
				
				layer.close(index);
				
			});
		}else if (obj.event === 'able') {
			layer.confirm('真的将角色置为可用么', function(index) {
				var ajaxReturnData;
		        $.ajax({
		            url: path + '/role/setUse.do',
		            type: 'post',
		            async: false,
		            data: {id:data.id},
		            success: function (data) {
		                ajaxReturnData = data;
		            }
		        });
		        //删除结果
		        if (ajaxReturnData == '0') {
		            table.reload('userTables');
		            layer.msg('操作成功', {icon: 1});
		        } else {
		        	layer.msg('操作失败', {icon: 5});
		        }
				
				layer.close(index);
				
			});
		}else if (obj.event === 'del') {
			layer.confirm('真的删除角色么', function(index) {
				var ajaxReturnData;
		        $.ajax({
		            url: path + '/role/delete.do',
		            type: 'post',
		            async: false,
		            data: {id:data.id},
		            success: function (data) {
		                ajaxReturnData = data;
		            }
		        });
		        //删除结果
		        if (ajaxReturnData == '0') {
		            table.reload('userTables');
		            layer.msg('删除成功', {icon: 1});
		        } else {
		        	layer.msg('删除失败', {icon: 5});
		        }
				
				layer.close(index);
				
			});
		}
	});

	$('#group-btn .layui-btn').on('click',function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
	});
	
	var active = {
        add:function(){
        	var index = layer.open({
				title: "角色编辑",
				type: 2,
				skin:'',
				offset: ['85px', '530px'],
				area: ['540px', '350px'],
				content: path + "/role/form.do",
			});
        }
	};
	
});

//关于树的js方法 start
//树加载
function loadTree(roleId){
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
	//构造树的必须参数
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
	     var ids = "";
	     for(var i=0;i<nodes.length;i++){
             //获取选中节点的值
	    	 ids += nodes[i].id + ",";
	     }
	    ajaxSubmit(rid,ids);       
	}
	function ajaxSubmit(rid, ids){
		var index  = layer.msg('保存中', {
			  icon: 16
			  ,shade: 0.1
			});
		var ajaxReturnData;
        //保存
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
        	layer.close(index);
            top.layer.msg('保存成功', {icon: 1});
        } else {
        	top.layer.msg('保存失败', {icon: 5});
        }
	}