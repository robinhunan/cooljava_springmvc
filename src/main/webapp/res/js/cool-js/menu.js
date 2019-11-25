	//树加载
	function loadTree(pid){
	    $.ajax({
	        type: "post",
	        dataType: "json", 
	        traditional: true,
	        data: { pid: pid },
	        url: path + "/menu/dataJson.do",
	        async: true,//表示同步执行
	        success: function (data) {
	            if (data != null) {
	                $.fn.zTree.init($("#tree"), setting, data);
	                //默认首个节点选中
	                var zTree = $.fn.zTree.getZTreeObj("tree");//获取ztree对象  
	                var node = zTree.getNodeByParam('id', '1');//获取id为1的点
	                zTree.selectNode(node);//选择点
	            }

	        }
	    });
	} 

	var zTree;
	var demoIframe;

	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback: {
			onClick: onClick
		}
		
	};

	function onClick(e,treeId,treeNode) {
		onClick(treeNode.id);
        return false;
    }

layui.use(['layer', 'form', 'table'], function() {
		var $ = layui.$,
            layer = parent.layer === undefined ? layui.layer : top.layer,
			form = layui.form,
			table = layui.table;

		  window.onClick = function(id){
			   table.reload('flinkTables', {
	        	  page: false,
	        	  where: {
	        		  pid:id
	        	  }
		       });
		   }
		   
		var tableIns =table.render({
			elem: '#flinklist',
			id: 'flinkTables',
			cols: [
				[{
					checkbox: true,
					width: 60,
					fixed: true
				}, {
					field: 'id',
					width: 80,
					title: 'ID',
					sort: true
				}, {
					field: 'title',
					width: 200,
					title: '菜单名称',
					align:'center',
				}, {
					field: 'icon',
					width: 100,
					title: '图标',
					align:'center',
					templet: '#linkTpl'
				}, {
					field: 'href',
					title: '对应的方法',
					align:'center',
					width: 250
				},  
				{
					field: 'permission',
					title: '权限标识',
					align:'center',
					width: 180
				},{
					field: 'type',
					title: '菜单类型',
					align:'center',
					width: 100,
					templet: '#type'
				},{
					field: 'sort',
					title: '排序',
					align:'center',
					width: 100
				},
				{
					title: '操作',
					width: 300,
					align: 'center',
					toolbar: '#flinkbar',
					fixed: 'right',
				}]
			],
			url: path + '/menu/data.do',
			page: false,
			even: true,
		});
		
		//默认选中首个节点，后续优化
		onClick('1');
		
		//监听工具条
		table.on('tool(flinkTables)', function(obj) {
			var data = obj.data;
			if (obj.event === 'del') {
				layer.confirm('真的删除菜单及其所有子菜单么？', function(index) {
					var ajaxReturnData;
			        $.ajax({
			            url: path + '/menu/delete.do',
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
			} else if (obj.event === 'edit') {
				var index = layui.layer.open({
					title: "菜单修改",
					type: 2,
					skin:'',
					content: path + "/menu/form.do?id="+data.id,
				});
				 	layui.layer.full(index);
			        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
			        $(window).on("resize",function(){
			            layui.layer.full(index);
			        });
			} else if (obj.event === 'add') {//增加下一节点
				var index = layui.layer.open({
					title: "菜单添加",
					type: 2,
					skin:'',
					offset: ['85px', '430px'],
					area: ['560px', '550px'],
					content: path + "/menu/form.do?pid="+data.id,
				});
				layui.layer.full(index);
		        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
		        $(window).on("resize",function(){
		            layui.layer.full(index);
		        });
			}
			
		});


		$('.menu-btn a.layui-btn').on('click',function(){
	          var type = $(this).data('type');
	          active[type] ? active[type].call(this) : '';
		});
	    
	    var active = {
	         getSelect:function(){
	                var index = layer.msg('查询中，请稍候...',{icon: 16,time:false,shade:0});
	                table.reload('flinkTables', {
	                	  page: false,
	                	  where: {
	                		  title:encodeURI($('#search_input').val())
	                	  }, //设定异步数据接口的额外参数
	                	  done: function (res, curr, count) {
	                          layer.close(index);

	                      }
	                	});
	                
	         },
			addLink: function() {
				var index = layui.layer.open({
					title: "菜单添加",
					type: 2,
					skin:'',
					offset: ['85px', '430px'],
					area: ['560px', '550px'],
					content: path + "/menu/form.do?pid=1",
				});
				layui.layer.full(index);
		        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
		        $(window).on("resize",function(){
		            layui.layer.full(index);
		        });
			},
	         delLink:function(){
	        	 //获取选中的行
	        	 var checkStatus = table.checkStatus('flinkTables');
	             var data = checkStatus.data;
	             if(data.length == 0){
	            	 layer.msg('请选择要删除的菜单！', function(){
	            		//关闭后的操作
	            	 });
	             }else{
	            	 layer.confirm('真的删除选中的菜单么？', function(index) {
			             var ids = [];
			             for (var i in data){
			                   ids.push(data[i].id);
			              }
			             
			        	 var ajaxReturnData;
					        $.ajax({
					            url: path + '/menu/deleteBatch.do',
					            type: 'post',
					            async: false,
					            data: {ids:ids.toString()},
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
		        	 });
	             }
	        	 
	         }
	    };
	    
	});