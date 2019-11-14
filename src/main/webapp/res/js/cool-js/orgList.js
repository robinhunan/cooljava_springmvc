layui.config({
    base:  path +'/res/js/'
}).extend({
    treetable: '/treetable'
}).use(['layer', 'table', 'treetable'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var layer = layui.layer;
    var treetable = layui.treetable;
    $.ajaxSetup({cache:false});
    // 渲染表格
    var renderTable = function () {
        layer.load(2);
        treetable.render({
            treeColIndex: 1,
            treeSpid: -1,
            treeIdName: 'id',
            treePidName: 'pid',
            treeDefaultClose: true,
            treeLinkage: false,
            elem: '#list',
            url: path + '/org/dataJson.do?id=-1',
            page: false,
            cols: [[
                {type: 'numbers'},
                {field: 'name', title: '名称'},
                {field: 'sort', title: '排序',width:28},
                {field: 'type', title: '机构类型',templet: '#type'},
                {templet: '#oper-col', title: '操作',width:260}
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });
    };

    renderTable();

    $('#btn-expand').click(function () {
        treetable.expandAll('#list');
    });

    $('#btn-fold').click(function () {
        treetable.foldAll('#list');
    });

    $('#btn-refresh').click(function () {
        renderTable();
    });

    //监听工具条
    table.on('tool(tables)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'del') {
            layer.confirm('真的删除机构及其所有子机构么？', function(index) {
                var res;
                $.ajax({
                    url: path + '/org/delete.do',
                    type: 'post',
                    async: false,
                    data: {id:data.id},
                    success: function (data) {
                        res = data;
                    }
                });
                //删除结果
                if (res == '0') {
                    renderTable();
                    layer.msg('删除成功', {icon: 1});
                } else {
                    layer.msg('删除失败', {icon: 5});
                }
                layer.close(index);
            });
        } else if (layEvent === 'edit') {
            var index = layer.open({
                title: "机构修改",
                type: 2,
                content: path + "/org/edit.do?id="+data.id,
            });
            layui.layer.full(index);//调整窗口大小
        }else if (obj.event === 'add') {//增加下一节点
            var index = layer.open({
                title: "机构添加",
                type: 2,
                offset: ['85px', '430px'],
                area: ['560px', '550px'],
                content: path + "/org/form.do?pid="+data.id,
            });
            layui.layer.full(index);
        }
    });
    $('#btn-search').click(function () {
        var keyword = $('#keyword').val();
        var searchCount = 0;
        $('#list').next('.treeTable').find('.layui-table-body tbody tr td').each(function () {
            $(this).css('background-color', 'transparent');
            var text = $(this).text();
            if (keyword != '' && text.indexOf(keyword) >= 0) {
                $(this).css('background-color', 'rgba(250,230,160,0.5)');
                if (searchCount == 0) {
                    treetable.expandAll('#list');
                    $('html,body').stop(true);
                    $('html,body').animate({scrollTop: $(this).offset().top - 150}, 500);
                }
                searchCount++;
            }
        });
        if (keyword == '') {
            layer.msg("请输入搜索内容", {icon: 5});
        } else if (searchCount == 0) {
            layer.msg("没有匹配结果", {icon: 5});
        }
    });
});