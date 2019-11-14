layui.use(['form','layer','laydate','table','upload'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        table = layui.table;
    $.ajaxSetup({cache:false});

    //test列表
    var tableIns = table.render({
        elem: '#list',
        url : path + '/test/testData.do',
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
					{field : 'id',title : 'id',align : 'center',sort : true,width : '200'},
					{field : 'value',title : '数据值',align : 'center',sort : true,width : '200'},
					{field : 'label',title : '标签名',align : 'center',sort : true,width : '200'},
					{field : 'type',title : '类型',align : 'center',sort : true,width : '200'},
					{field : 'description',title : '描述',align : 'center',sort : true,width : '200'},
					{field : 'sort',title : '排序（升序）',align : 'center',sort : true,width : '200'},
					{field : 'status',title : '可用状态：0可用 1不可用',align : 'center',sort : true,width : '200'},
				{
					title : '操作',
					width : 200,
					fixed : "right",
					align : "center",
					templet : '#listBar'
				}
        ]]
    });

    //搜索
    $(".search-btn").on("click",function(){
            table.reload("tables",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    type: $(".type").val()  //查询内容，代码生成后手动修改
                }
            })
    });

    //添加弹出框 test
    function popupAdd(){
        var index = layer.open({
            title : "添加test",
            type : 2,
	        area: ['540px', '550px'],
            content : path + "/test/form.do"
        })
    }
  //编辑弹出框 test
    function popupEdit(edit){
        var index = layer.open({
            title : "修改test",
            type : 2,
	        area: ['540px', '550px'],
            content : path + "/test/edit.do?id="+edit.id
        })
    }

    //绑定编辑test事件
    $(".add-btn").click(function(){
        popupAdd();
    })

    //批量删除
    $(".delmatch-btn").click(function(){
        var checkStatus = table.checkStatus('tables'),
            data = checkStatus.data,
            ids = [];
        if(data.length > 0) {
            for (var i in data) {
                ids.push(data[i].id);
            }
            layer.confirm('确定删除选中的test？', {icon: 3, title: '提示信息'}, function (index) {
            	var ajaxReturnData;
                $.ajax({
		            url: path + '/test/deleteBatch.do',
		            type: 'post',
		            async: false,
		            data: {'ids':ids.toString()},
		            success: function (data) {
		                ajaxReturnData = data;
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
            layer.msg("请选择需要删除的test");
        }
    })

    //列表操作
    table.on('tool(tables)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            popupEdit(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此test？',{icon:3, title:'提示信息'},function(index){
                var ajaxReturnData;
		        $.ajax({
		            url: path + '/test/delete.do',
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
    
    form.on("submit(send)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var res;
        $.ajax({
            url: path + '/test/save.do',
            type: 'post',
            async: false,
            data: data.field,
            success: function (data) {
                res = data;
            }
        });
        //结果回应
        if (res == '0') {
        	top.layer.close(index);
            top.layer.msg('保存成功', {icon: 1,time:1000},function(){
                layer.closeAll();
                $(".layui-tab-item.layui-show",parent.document).find("iframe")[0].contentWindow.location.reload();
            })
        } else {
        	top.layer.msg('保存失败', {icon: 5});
        }
        return false;
    })
})
