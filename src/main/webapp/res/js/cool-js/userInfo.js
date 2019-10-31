var areaData = address;
var $form;
var form;
var $;
layui.config({
	base : "../../js/"
}).use(['form','layer','upload','laydate'],function(){
	form = layui.form;
	var layer = parent.layer === undefined ? layui.layer : parent.layer;
		$ = layui.jquery;
		$form = $('form');
		upload = layui.upload,
		laydate = layui.laydate;
        loadProvince();
        
        
       //普通图片上传
  	  var uploadInst = upload.render({
  		 elem: '#test1'
  	    ,url: path + '/upload/uploadimg.do'
  	    ,before: function(obj){
  	      //预读本地文件示例，不支持ie8
  	      obj.preview(function(index, file, result){
  	        $('#imgg').attr('src', result); //图片链接（base64）
  	      });
  	    }
  	    ,done: function(res){
  	      //如果上传失败
  	      if(res.code = 0){
  	        return layer.msg('上传失败');
  	      }else if(res.code = 1){//上传成功
  	    	  //$('#imgg').attr('src', res.data.src); //图片链接（base64）
  	    	  $("#img").val(res.data.src);
  	    	  return layer.msg('上传成功');
  	      }else if(res.code = 2){
  	    	  return layer.msg('上传的文件不是图片');
  	      }
  	    }
  	    ,error: function(){
  	      //演示失败状态，并实现重传
  	      var demoText = $('#demoText');
  	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
  	      demoText.find('.demo-reload').on('click', function(){
  	        uploadInst.upload();
  	      });
  	    }
  	  });
  	  
        //添加验证规则
        form.verify({
            oldPwd : function(value, item){
                if(value != "123456"){
                    return "密码错误，请重新输入！";
                }
            },
            newPwd : function(value, item){
                if(value.length < 5){
                    return "密码长度不能小于5位";
                }
            },
            confirmPwd : function(value, item){
                if(!new RegExp($("#oldPwd").val()).test(value)){
                    return "两次输入密码不一致，请重新输入！";
                }
            }
        })

        //判断是否修改过头像，如果修改过则显示修改后的头像，否则显示默认头像
        if(window.sessionStorage.getItem('userFace')){
        	$("#userFace").attr("src",window.sessionStorage.getItem('userFace'));
        }else{
        	$("#userFace").attr("src","../../res/images/avatar/3.jpg");
        }
       
        //保存个人信息
  	  form.on('submit(changeUser)',function(data){
	    	var ajaxReturnData;
	        //登陆验证
	        $.ajax({
	            url: path + '/user/saveInfo.do',
	            type: 'post',
	            async: false,
	            data: data.field,
	            success: function (data) {
	                ajaxReturnData = data;
	            }
	        });
	        if (ajaxReturnData == '0') {
	            layer.msg('保存成功', {icon: 1});
	            return false;
	        } else {
	        	layer.msg('保存失败', {icon: 5});
          	return false;
	        }
	        
	    });
  	  
  	  //保存密码
  	  form.on('submit(changePwd)',function(data){
	    	var ajaxReturnData;
	        //登陆验证
	        $.ajax({
	            url: path + '/user/savePwd.do',
	            type: 'post',
	            async: false,
	            data: data.field,
	            success: function (data) {
	                ajaxReturnData = data;
	            }
	        });
	        //修改成功
	        if (ajaxReturnData == '0') {
	            layer.msg('保存成功', {icon: 1});
	            return false;
	        } else {
	        	if(ajaxReturnData == '1'){
	        		layer.msg('新密码和确认密码不一致', {icon: 5});
	        	}else if(ajaxReturnData == '2'){
	        		layer.msg('旧密码不正确', {icon: 5});
	        	}else{
	        		layer.msg('保存失败', {icon: 5});
	        	}
          	return false;
	        }
	        
	    });
  	  
})


 //加载省数据
function loadProvince() {
    var proHtml = '';
    for (var i = 0; i < areaData.length; i++) {
        proHtml += '<option value="' + areaData[i].provinceCode + '_' + areaData[i].mallCityList.length + '_' + i + '">' + areaData[i].provinceName + '</option>';
    }
    //初始化省数据
    $form.find('select[name=province]').append(proHtml);
    form.render();
    form.on('select(province)', function(data) {
        $form.find('select[name=area]').html('<option value="">请选择县/区</option>');
        var value = data.value;
        var d = value.split('_');
        var code = d[0];
        var count = d[1];
        var index = d[2];
        if (count > 0) {
            loadCity(areaData[index].mallCityList);
        } else {
            $form.find('select[name=city]').attr("disabled","disabled");
        }
    });
}
 //加载市数据
function loadCity(citys) {
    var cityHtml = '<option value="">请选择市</option>';
    for (var i = 0; i < citys.length; i++) {
        cityHtml += '<option value="' + citys[i].cityCode + '_' + citys[i].mallAreaList.length + '_' + i + '">' + citys[i].cityName + '</option>';
    }
    $form.find('select[name=city]').html(cityHtml).removeAttr("disabled");
    form.render();
    form.on('select(city)', function(data) {
        var value = data.value;
        var d = value.split('_');
        var code = d[0];
        var count = d[1];
        var index = d[2];
        if (count > 0) {
            loadArea(citys[index].mallAreaList);
        } else {
            $form.find('select[name=area]').attr("disabled","disabled");
        }
    });
}
 //加载县/区数据
function loadArea(areas) {
    var areaHtml = '<option value="">请选择县/区</option>';
    for (var i = 0; i < areas.length; i++) {
        areaHtml += '<option value="' + areas[i].areaCode + '">' + areas[i].areaName + '</option>';
    }
    $form.find('select[name=area]').html(areaHtml).removeAttr("disabled");
    form.render();
    form.on('select(area)', function(data) {
    });
}