layui.use(['layer', 'form', 'table'], function() {
	var $ = layui.$,
		layer = layui.layer,
		form = layui.form,
		table = layui.table;
	
	var tableIns = table.render({
		elem: '#userTables',
		id: 'userTables',
        width: 1200,
        limit : 10,
        limits : [10,15,20,25],
		cols: [
			[{
				field: 'img',
				width: 100,
				height: 220,
				title: '头像',
				align: 'center',
				templet: '#img'
			},{
				field: 'loginName',
				width: 180,
				title: '用户名',
				align: 'center',
			},{
				field: 'name',
				width: 180,
				title: '真实姓名',
				align: 'center',
			}, {
				field: 'email',
				width: 225,
				title: '邮箱',
				align: 'center',
			}, {
				field: 'mobile',
				width: 180,
				title: '手机号',
				align: 'center',
			}, {
				field: 'orgName',
				width: 180,
				title: '机构',
				align: 'center',
			},{
				field: 'roleName',
				width: 180,
				title: '角色',
				align: 'center',
			},{
				field: 'status',
				width: 120,
				title: '状态',
				align: 'center',
				templet: '#status'
			},{
				title: '常用操作',
				width: 220,
				align: 'center',
				toolbar: '#userbar',
				fixed:"right"
			}]

		],
		url: path + '/user/data.do',
		page: true,
		even: true,

	});

	//监听工具条
	table.on('tool(userTables)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') {
			var index = layer.open({
				title: "用户信息修改",
				type: 2,
				skin:'',
				offset: ['85px', '530px'],
				area: ['540px', '520px'],
				content: path + "/user/form.do?id="+data.id,
			});
		} else if (obj.event === 'disable') {
			layer.confirm('真的禁用用户么', function(index) {
				var ajaxReturnData;
		        $.ajax({
		            url: path + '/user/setUse.do',
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
			layer.confirm('真的将该用户置为可用么', function(index) {
				var ajaxReturnData;
		        $.ajax({
		            url: path + '/user/setUse.do',
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
			layer.confirm('真的删除该用户么？', function(index) {
				var ajaxReturnData;
		        $.ajax({
		            url: path + '/user/delete.do',
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
				title: "用户编辑",
				type: 2,
				skin:'',
				offset: ['85px', '530px'],
				area: ['540px', '520px'],
				content: path + "/user/form.do",
			});
        }
	};
});
