<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<script src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="css/pagination.css"/>
<script>
    $(function (){
        initPagination();
    })
    function initPagination(){
        //获取分页数据中的总记录数
        var totalRecord = ${requestScope.pageInfo.total};

        //声明Pagination设置属性的JSON对象
        var properties = {
            num_edge_entries: 3,                                //边缘页数
            num_display_entries: 5,                             //主体页数
            callback: pageSelectCallback,                       //点击各种翻页反扭时触发的回调函数（执行翻页操作）
            current_page: ${requestScope.pageInfo.pageNum-1},   //当前页码
            prev_text: "上一页",                                 //在对应上一页操作的按钮上的文本
            next_text: "下一页",                                 //在对应下一页操作的按钮上的文本
            items_per_page: ${requestScope.pageInfo.pageSize}   //每页显示的数量
        };

        $("#Pagination").pagination(totalRecord,properties);
    }
    function pageSelectCallback(pageIndex, jQuery){
        // pageIndex是当前页码的索引，因此比pageNum小1
        var pageNum = pageIndex+1;

        // 执行页面跳转
        window.location.href = "admin/get/page.html?pageNum="+pageNum+"&keyword=${param.keyword}";

        // 取消当前超链接的默认行为
        return false;
    }
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
                    <form class="form-inline" role="form" style="float:left;" action="admin/get/page.html" method="post">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件" name="keyword">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <a type="button" class="btn btn-primary" style="float:right;" href="admin/to/add/page.html"><i class="glyphicon glyphicon-plus"></i>新增</a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉！没有查询到您要的数据。</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="mystatus">
                                    <tr>
                                        <td>${mystatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a class="btn btn-success btn-xs"
                                               href="assign/to/assign/role/page.html?adminId=${admin.id}&pageNum=${pageInfo.pageNum}&keyword=${param.keyword}">
                                                <i class=" glyphicon glyphicon-check"></i></a>
                                            <a class="btn btn-primary btn-xs"
                                               href="admin/to/update/page.html?adminId=${admin.id}">
                                                <i class=" glyphicon glyphicon-pencil"></i></a>
                                            <a class="btn btn-danger btn-xs" href="admin/remove/${admin.id}/${pageInfo.pageNum}/${param.keyword}.html">
                                                <i class=" glyphicon glyphicon-remove"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
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
</body>
</html>