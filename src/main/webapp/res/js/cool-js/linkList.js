layui.use(['form','layer','laydate','table','upload'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload,
        table = layui.table;

    //友链列表
    var tableIns = table.render({
        elem: '#flinklist',
        url : path + '/friendLink/fLinkInfo.do',
        page : true,
        cellMinWidth : 95,
        height : "full-104",
        limit : 20,
        limits : [10,15,20,25],
        id : "flinkTables",
        cols : [[
				 {
					type : "checkbox",
					fixed : "left",
					width : 50
				},
				{
					field: 'id',
					width: 80,
					title: 'ID',
					sort: true,
				}, 
				{
					field : 'webname',
					title : '网站名称',
					width : 300
				},
				{
					field : 'alink',
					title : '网站地址',
					width : 300,
					templet : function(d) {
						return '<a class="layui-blue" href="'
								+ d.alink
								+ '" target="_blank">'
								+ d.alink + '</a>';
					}
				},
				{
					field : 'email',
					title : '站长邮箱',
					width : 280,
					align : 'center'
				},
				{
					field : 'dispos',
					title : '展示位置',
					align : 'center',
					width : 200
				},
				{
					field : 'timeF',
					title : '添加时间',
					align : 'center',
					width : 280
				},
				{
					title : '操作',
					width : 184,
					fixed : "right",
					align : "center",
					templet : '#flinkbar'
				}
        ]]
    });

    //搜索
    $(".search_btn").on("click",function(){
        if($(".type").val() != ''){
            table.reload("flinkTables",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                	webname: encodeURI($(".type").val())  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加友链
    function addLink(edit){
        var index = layer.open({
            title : "添加友链",
            type : 2,
			area: ['540px', '450px'],
            content : path + "/friendLink/form.do"
        })
    }
  //添加友链
    function editLink(edit){
        var index = layer.open({
            title : "修改友链",
            type : 2,
			area: ['540px', '450px'],
            content : path + "/friendLink/edit.do?id="+edit.id
        })
    }
    //绑定添加友情链接事件
    $(".addLink_btn").click(function(){
        addLink();
    })

    //批量删除
    $(".delmatch-btn").click(function(){
        var checkStatus = table.checkStatus('flinkTables'),
            data = checkStatus.data,
            linkId = [];
        if(data.length > 0) {
            for (var i in data) {
                linkId.push(data[i].id);
            }
            layer.confirm('确定删除选中的友链？', {icon: 3, title: '提示信息'}, function (index) {
            	var ajaxReturnData;
                $.ajax({
		            url: path + '/friendLink/deleteBatch.do',
		            type: 'post',
		            async: false,
		            data: {ids:linkId.toString()},
		            success: function (data) {
		                ajaxReturnData = data;
		              //删除结果
				        if (ajaxReturnData == '0') {
				            table.reload('flinkTables');
				            layer.msg('删除成功', {icon: 1});
				        } else {
				        	layer.msg('删除失败', {icon: 5});
				        }
		            }
		        });
            })
        }else{
            layer.msg("请选择需要删除的友链");
        }
    })

    //列表操作
    table.on('tool(flinkTables)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            editLink(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此友链？',{icon:3, title:'提示信息'},function(index){
                var ajaxReturnData;
		        $.ajax({
		            url: path + '/friendLink/delete.do',
		            type: 'post',
		            async: false,
		            data: {id:data.id},
		            success: function (data) {
		                ajaxReturnData = data;
		            }
		        });
		        //删除结果
		        if (ajaxReturnData == '0') {
		            table.reload('flinkTables');
		            layer.msg('删除成功', {icon: 1});
		        } else {
		        	layer.msg('删除失败', {icon: 5});
		        }
				
				layer.close(index);
            });
        }
    });
    
    //上传logo
    upload.render({
        elem: '.linkLogo',
        url: '../../json/linkLogo.json',
        method : "get",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            var num = parseInt(4*Math.random());  //生成0-4的随机数，随机显示一个头像信息
            $('.linkLogoImg').attr('src',res.data[num].src);
            $('.linkLogo').css("background","#fff");
        }
    });

    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //添加时间
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());

    form.on("submit(addLink)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var ajaxReturnData;
        //登陆验证
        $.ajax({
            url: path + '/friendLink/save.do',
            type: 'post',
            async: false,
            data: data.field,
            success: function (data) {
                ajaxReturnData = data;
            }
        });
        //结果回应
        if (ajaxReturnData == '0') {
        	top.layer.close(index);
        	top.layer.msg('保存成功', {icon: 1});
        	 layer.closeAll("iframe");
             //刷新父页面
             $(".layui-tab-item.layui-show",parent.document).find("iframe")[0].contentWindow.location.reload();
        } else {
        	top.layer.msg('保存失败', {icon: 5});
        }
        return false;
    })

})