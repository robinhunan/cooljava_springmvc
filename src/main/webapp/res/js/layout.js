layui.config({
	base: '../res/js/'
}).use(['element', 'layer', 'navbar', 'tab', 'common'], function() {
	var element = layui.element
	$ = layui.jquery,
		layer = layui.layer,
		common = layui.common,
		navbar = layui.navbar(),
		tab = layui.tab({
			elem: '.layout-nav-card', //设置选项卡容器
			contextMenu:true
		});

	//iframe自适应
	$(window).on('resize', function() {
		var $content = $('.layout-nav-card .layui-tab-content');
		$content.height($(this).height() - 121);
		$content.find('iframe').each(function() {
			$(this).height($content.height());
		});
	}).resize();
		
	//初始化顶级菜单
	$.getJSON(path + '/menu/getMenuTop.do?t=' + Math.random(), function(menuData) {
		$('#menu').html(getTopHtml(menuData));
		element.init();
		
		var $menu = $('#menu');
		$menu.find('li.layui-nav-item').each(function() {
			var $this = $(this);
			//绑定一级导航的点击事件
			$this.on('click', function() {
				//获取设置的模块ID
				var id = $this.find('a').data('module-id');
				//这里的数据源只是演示时用的，实际需求可能通过远程读取（根据模块ID来获取对应模块的信息）
				var url;
				//设置数据源有两个方式。
				//第一：在此页面通过ajax读取设置  举个栗子： 两个栗子都先留这边，可以根据自己情况修改
				//---------这是第一个栗子----------
				$.getJSON(path + '/menu/getMenuByPidRec.do',{id:id},function(data){
					navbar.set({
						elem: '#side',
						data: data
					});
					navbar.render();
					navbar.on('click(side)', function(data) {
						tab.tabAdd(data.field);
					});
				});
				
			});
	
		});
	
		//模拟点击内容管理
		$.getJSON(path + '/menu/getTopMenuFirst.do?t=' + Math.random(), function(menuData) {
			$('.beg-layout-menu').find('a[data-module-id='+menuData+']').click();
		});
		
	});
	
	element.on('nav(user)', function(data) {
		var $a = data.children('a');
		if($a.data('tab') !== undefined && $a.data('tab')) {
			tab.tabAdd({
				title: $a.children('cite').text(),
				//icon: 'fa-user',
				href: $a.data('url')
			});
		}
	});

	$('.beg-layout-side-toggle').on('click', function() {
		var sideWidth = $('.beg-layout-side').width();
		if(sideWidth === 200) {
			$('.beg-layout-body').animate({
				left: '0'
			});
			$('.beg-layout-footer').animate({
				left: '0'
			});
			$('.beg-layout-side').animate({
				width: '0'
			});
		} else {
			$('.beg-layout-body').animate({
				left: '200px'
			});
			$('.beg-layout-footer').animate({
				left: '200px'
			});
			$('.beg-layout-side').animate({
				width: '200px'
			});
		}
	});

						
	//顶级菜单获取html字符串
	function  getTopHtml(data) {
		var ulHtml = '';
			ulHtml += '<ul class="layui-nav beg-layout-nav" lay-filter="">';
		for (var i = 0; i < data.length; i++) {
		 	if (i == 0) {
	          ulHtml += '<li class="layui-nav-item layui-this">';
	        } else {
	          ulHtml += '<li class="layui-nav-item">';
	        }
          ulHtml += '<a href="javascript:;" data-module-id="'+data[i].id+'">';
          ulHtml += '<i class="f18 fa '+ data[i].icon+'" aria-hidden="true"></i>&nbsp;';
          ulHtml += '<cite>' + data[i].title + '</cite>';
          ulHtml += '</a>';
          ulHtml += '</i>';
		}
		ulHtml += '</ul>';
		return ulHtml;
	}
	
	//全屏
	$('.admin-side-full').on('click', function () {
        var docElm = document.documentElement;
        //W3C  
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        //FireFox  
        else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        }
        //Chrome等  
        else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
        //IE11
        else if (elem.msRequestFullscreen) {
            elem.msRequestFullscreen();
        }
        layer.msg('按Esc即可退出全屏');
     });
	
	//清除缓存
	$(".clearCache").click(function(){
		window.sessionStorage.clear();
        window.localStorage.clear();
        var index = layer.msg('清除缓存中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            layer.msg("缓存清除成功！");
        },1000);
    })
	
	//清除缓存
	$(".showNotice").click(function(){
		layer.open({
	        type: 1,
	        title: "系统公告", //不显示标题栏
	        closeBtn: false,
	        area: '310px',
	        shade: 0.8,
	        id: 'LAY_layuipro', //设定一个id，防止重复弹出
	        btn: ['火速围观'],
	        moveType: 1, //拖拽模式，0或者1
	        content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;"><p>这是一个公告，可根据自己的要求修改，将会在后续版本增加公告管理。</p></div>',
	        success: function(layero){
				var btn = layero.find('.layui-layer-btn');
				btn.css('text-align', 'center');
				btn.on("click",function(){
					window.sessionStorage.setItem("showNotice","true");
				})
				if($(window).width() > 432){  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
					btn.on("click",function(){
						layer.tips('系统公告躲在了这里', '#showNotice', {
							tips: 3
						});
					})
				}
	        }
	    });
        
    })
	
    //退出
    $('#logout').on('click', function () {
        window.location.href=path + '/login/logout.do';
    })
});