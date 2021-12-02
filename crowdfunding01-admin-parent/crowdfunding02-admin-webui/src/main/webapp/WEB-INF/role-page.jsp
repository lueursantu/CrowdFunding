<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="crowd/role-page.js"></script>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function (){
        //1、为分页准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        generatePage();

        $("#searchBtn").click(function (){
            window.keyword = $("#searchInput").val();
            generatePage();
        });

        $("#addRoleBtn").click(function (){
            $("#addModel").modal("show");
        });

        $("#addModel #saveRoleBtn").click(function (){
            var roleName = $("#addModel #roleName").val();
            $.ajax({
                "url": "save/role.json",
                "type": "post",
                "data": {
                    "roleName": roleName
                },
                "dataType": "json",
                "success": function (response){
                    if(response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        // 将页码调到最后一页
                        window.pageNum = 99999;
                        generatePage();
                    }
                    if(response.result == "FAILED"){
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response){
                    layer.msg("失败！响应状态码："+stateCode+" 说明信息："+ajaxResult.statusText);
                }
            });
            // 关闭模态框
            $("#addModel").modal("hide");
            // 清理模态框
            $("#addModel #roleName").val("");
        });

        $("#rolePageTBody").on("click", ".pencilBtn", function (){
            $("#editModel").modal("show");
            var roleName = $(this).parent().prev().text();
            console.log(roleName);
            window.roleId = this.id;
            $("#editModel #roleName").val(roleName);
        });

        $("#editModel #saveRoleBtn").click(function (){
            var roleName = $("#editModel #roleName").val();
            $.ajax({
                "url": "update/role.json",
                "type": "post",
                "data": {
                    "roleId": window.roleId,
                    "roleName": roleName
                },
                "dataType": "json",
                "success": function (response){
                    if(response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        generatePage();
                    }
                    if(response.result == "FAILED"){
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response){
                    layer.msg("失败！响应状态码："+stateCode+" 说明信息："+ajaxResult.statusText);
                }
            })
            // 关闭模态框
            $("#editModel").modal("hide");
        });

        //测试
        // var roleList = [{roleName: "qwe", roleId: 112}, {roleName: "qse", roleId: 122}];
        // showDeleteModel(roleList);

        $("#deleteModel #deleteRoleBtn").click(function (){
            roleIdList = [];
            $.each(window.roleList, function (){
                roleIdList.push(this.roleId);
            });
            var roleIdListJson = JSON.stringify(roleIdList);
            console.log(roleIdListJson);
            $.ajax({
                "url": "remove/role.json",
                "type": "post",
                "data": roleIdListJson,
                "dataType": "json",
                "contentType": "application/json;charset=UTF-8",
                "success": function (response){
                    if(response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        generatePage();
                    }
                    if(response.result == "FAILED"){
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response){
                    layer.msg("失败！响应状态码："+stateCode+" 说明信息："+ajaxResult.statusText);
                }
            });
            $("#deleteModel").modal("hide");
        });

        // 列表项上的单个删除按钮
        $("#rolePageTBody").on("click", ".removeBtn", function(){
            var roleName = $(this).parent().prev().text();
            var roleId = this.id;
            showDeleteModel([{"roleName": roleName, "roleId": roleId}]);
        });

        // 复选框全选全不选功能
        $("#headCheckBox").click(function (){
            // 获取当前状态（是否被选中）
            var currentStatus = this.checked;
            $(".itemBox").prop("checked",currentStatus);
        });

        // 复选框勾选的全部删除按钮
        $("#deleteRoleBtn").click(function (){
            var roleList = [];
            $(".itemBox:checked").each(function () {
                // 通过this引用当前遍历得到的多选框的id
                var roleId = this.id;

                // 通过DOM操作获取角色名称
                var roleName = $(this).parent().next().text();

                roleList.push({
                    "roleId":roleId,
                    "roleName":roleName
                });
            });

            // 判断roleArray的长度是否为0
            if (roleList.length == 0){
                layer.msg("请至少选择一个来删除!");
                return ;
            }

            // 显示确认框
            showDeleteModel(roleList);
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
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="searchInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="deleteRoleBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="addRoleBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="headCheckBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageTBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-role-add.jsp"%>
<%@include file="modal-role-edit.jsp"%>
<%@include file="modal-role-delete.jsp"%>
</body>
</html>