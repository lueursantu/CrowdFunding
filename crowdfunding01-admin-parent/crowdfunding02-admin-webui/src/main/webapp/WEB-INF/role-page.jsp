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

        $("#showModelBtn").click(function (){
            $("#addModel").modal("show");
        });

        $("#saveRoleBtn").click(function (){
            var roleName = $("#addModel #roleName").val();
            $.ajax({
                "url": "save/role.json",
                "type": "post",
                "data": {
                    "roleName": roleName
                },
                "dateType": "json",
                "success": function (response){
                    if(response.result == "SUCCESS"){
                        layer.msg("操作成功！");
                        // 将页码调到最后一页
                        window.pageNum = Number.MAX_SAFE_INTEGER;
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
            $("#addModel roleName").val("");
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
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="showModelBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageTBody">
                            <tr>
                                <td>1</td>
                                <td><input type="checkbox"></td>
                                <td>PM - 项目经理</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            </tbody>
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
</body>
</html>