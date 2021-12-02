<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/menu-page.js"></script>
<script type="text/javascript">
    $(function (){
        generateTree();

        //添加节点
        $("#treeDemo").on("click", ".addBtn", function(){
            window.pid = this.id;
            $("#menuAddModal").modal("show");
        });
        $("#menuSaveBtn").click(function (){
            var pid = window.pid;
            var name = $("#menuAddModal [name=name]").val();
            var url = $("#menuAddModal [name=url]").val();
            var icon = $("#menuAddModal [name=icon]:checked").val();
            $.ajax({
                "url": "menu/save.json",
                "type": "post",
                "data": {
                    "pid": pid,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response){
                    if(response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        // 重新生成树形结构
                        generateTree();
                    }
                    if (response.result == "FAILED"){
                        layer.msg("操作失败！"+response.result.message);
                    }
                },
                "error":function (response) {
                    layer.msg(response.status + " " + response.statusText);
                    console.log(response);
                }
            });
            $("#menuResetBtn").click();
            $("#menuAddModal").modal("hide");
            generateTree();
        });

        //修改节点
        $("#treeDemo").on("click", ".editBtn", function(){
            window.id = this.id;
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var currentNode = zTreeObj.getNodeByParam("id", window.id);
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            $("#menuEditModal").modal("show");
        });
        $("#menuEditBtn").click(function (){
            var id = window.id;
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();
            $.ajax({
                "url": "menu/update.json",
                "type": "post",
                "data": {
                    "id": id,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    if (response.result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新生成树形结构
                        generateTree();
                    }
                    if (response.result == "FAILED") {
                        layer.msg("操作失败！" + response.result.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                    console.log(response);
                }
            });
            $("#menuEditModal").modal("hide");
            generateTree();
        });

        //删除节点
        $("#treeDemo").on("click", ".removeBtn", function(){
            window.id = this.id;
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var currentNode = zTreeObj.getNodeByParam("id", window.id);
            $("#removeNodeSpan").html("<i class='"+ currentNode.icon +"'></i>" + currentNode.name);
            $("#menuConfirmModal").modal("show");
        });
        $("#confirmBtn").click(function (){
            $.ajax({
                "url": "menu/remove.json",
                "type": "post",
                "data": {
                    "id": window.id
                },
                "dataType": "json",
                "success": function (response) {
                    if (response.result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新生成树形结构
                        generateTree();
                    }
                    if (response.result == "FAILED") {
                        layer.msg("操作失败！" + response.result.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                    console.log(response);
                }
            });
            $("#menuConfirmModal").modal("hide");
            generateTree();
        });
    });
</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree">

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-menu-add.jsp"%>
<%@include file="modal-menu-edit.jsp"%>
<%@include file="modal-menu-confirm.jsp"%>
</body>
</html>