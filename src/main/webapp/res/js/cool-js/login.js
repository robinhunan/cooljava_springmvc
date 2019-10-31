layui.use(['layer', 'form'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;

    //登录
    form.on('submit(submit)',function(data){
        var res;
        $.ajax({
            url: path + '/login/login.do',
            type: 'post',
            async: false,
            data: data.field,
            success: function (data) {
                res = data;
            }
        });

        if (res.code == '0') { //成功
            layer.msg(res.msg, {icon: 1,time:1000},function(){
                location.href=path+"/index/index.do";
            });
            return false;
        } else { //失败
            layer.msg(res.msg, {icon: 5});
            return false;
        }

    });
});