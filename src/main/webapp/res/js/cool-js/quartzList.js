layui.use(['form','layer','laydate','table','upload'],function(){
    var form = layui.form,
    layer = layui.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        table = layui.table;

    //定时任务列表
    var tableIns = table.render({
        elem: '#list',
        url : path + '/quartz/quartzData.do',
        page : true,
        cellMinWidth : 95,
        height : "full-104",
        limit : 20,
        limits : [10,15,20,25],
        id : "tables",
        cols : [[
				 {
					type : "checkbox",
					fixed : "left",
					width : 50
				},
					/*{field : 'id',title : '',align : 'center',sort : true,width : '200'},*/
					{field : 'jobName',title : '定时器名称',align : 'center',sort : true,width : '200'},
					{field : 'jobGroup',title : '所属组',align : 'center',sort : true,width : '200'},
					{field : 'classPath',title : '类路径',align : 'center',sort : true,width : '200'},
					{field : 'cronStr',title : 'cron表达式',align : 'center',sort : true,width : '200'},
					{field : 'state',title : '状态',align : 'center',sort : true,width : '200',templet: '#state'},
					{field : 'mark',title : '备注',align : 'center',sort : true,width : '330'},
				{
					title : '操作',
					width : 300,
					fixed : "right",
					align : "center",
					templet : '#flinkbar'
				}
        ]]
    });

    //搜索
    $(".search_btn").on("click",function(){
            table.reload("tables",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                	jobName: $(".type").val()  //查询内容，代码生成后手动修改
                }
            })
    });

    //添加定时任务
    function addLink(edit){
        var index = layer.open({
            title : "添加定时任务",
            type : 2,
			area: ['540px', '550px'],
            content : path + "/quartz/form.do"
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        });
    }
  //添加定时任务
    function editLink(edit){
        var index = layer.open({
            title : "修改定时任务",
            type : 2,
			area: ['540px', '550px'],
            content : path + "/quartz/edit.do?id="+edit.id
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        });
    }

    //绑定编辑定时任务事件
    $(".addLink_btn").click(function(){
        addLink();
    })

    //批量删除
    $(".delmatch-btn").click(function(){
        var checkStatus = table.checkStatus('tables'),
            data = checkStatus.data,
            linkId = [];
        if(data.length > 0) {
            for (var i in data) {
                linkId.push(data[i].id);
            }
            layer.confirm('确定删除选中的定时任务？', {icon: 3, title: '提示信息'}, function (index) {
            	var ajaxReturnData;
                $.ajax({
		            url: path + '/quartz/deleteBatch.do',
		            type: 'post',
		            async: false,
		            data: {ids:linkId.toString()},
		            success: function (data) {
		                ajaxReturnData = data;
		              //删除结果
				        if (ajaxReturnData == '0') {
				            table.reload('tables');
				            layer.msg('删除成功', {icon: 1});
				        } else {
				        	layer.msg('删除失败', {icon: 5});
				        }
		            }
		        });
            })
        }else{
            layer.msg("请选择需要删除的定时任务");
        }
    })

    //列表操作
    table.on('tool(tables)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            editLink(data);
        } else if (obj.event === 'disable') {
			layer.confirm('真的暂停该定时任务么', function(index) {
				var ajaxReturnData;
		        $.ajax({
		            url: path + '/quartz/setUse.do',
		            type: 'post',
		            async: false,
		            data: {id:data.id},
		            success: function (data) {
		                ajaxReturnData = data;
		            }
		        });
		        //删除结果
		        if (ajaxReturnData == '0') {
		            table.reload('tables');
		            layer.msg('操作成功', {icon: 1});
		        } else {
		        	layer.msg('操作失败', {icon: 5});
		        }
				
				layer.close(index);
				
			});
		}else if (obj.event === 'able') {
			layer.confirm('真的将该重新启动该定时任务么', function(index) {
				var ajaxReturnData;
		        $.ajax({
		            url: path + '/quartz/setUse.do',
		            type: 'post',
		            async: false,
		            data: {id:data.id},
		            success: function (data) {
		                ajaxReturnData = data;
		            }
		        });
		        //删除结果
		        if (ajaxReturnData == '0') {
		            table.reload('tables');
		            layer.msg('操作成功', {icon: 1});
		        } else {
		        	layer.msg('操作失败', {icon: 5});
		        }
				
				layer.close(index);
				
			});
		}else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此定时任务？',{icon:3, title:'提示信息'},function(index){
                var ajaxReturnData;
		        $.ajax({
		            url: path + '/quartz/delete.do',
		            type: 'post',
		            async: false,
		            data: {id:data.id},
		            success: function (data) {
		                ajaxReturnData = data;
		            }
		        });
		        //删除结果
		        if (ajaxReturnData == '0') {
		            table.reload('tables');
		            layer.msg('删除成功', {icon: 1});
		        } else {
		        	layer.msg('删除失败', {icon: 5});
		        }
				
				layer.close(index);
            });
		}
    });
    
    form.on("submit(addLink)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var ajaxReturnData;
        //登陆验证
        $.ajax({
            url: path + '/quartz/save.do',
            type: 'post',
            async: false,
            data: data.field,
            success: function (data) {
                ajaxReturnData = data;
            }
        });
        //结果回应
        if (ajaxReturnData.code == '0') {
        	top.layer.close(index);
        	top.layer.msg(ajaxReturnData.msg, {icon: 1});
        	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭                        //刷新父页面
            parent.location.reload();
        }else {
        	top.layer.msg(ajaxReturnData.msg, {icon: 5});
        }
        return false;
    })

})