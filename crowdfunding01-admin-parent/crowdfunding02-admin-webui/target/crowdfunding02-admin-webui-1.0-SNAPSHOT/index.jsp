<%--
  Created by IntelliJ IDEA.
  User: Santu
  Date: 2021/11/12
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        $(function (){
           $("#btn1").click(function (){
               $.ajax({
                   "url": "send/array.json",
                   "type": "post",
                   "data": {
                       "array": [5,8,12]
                   },
                   "dataType": "json",
                   "success": function (response){
                        alert(response)
                   },
                   "error": function (response){
                        alert(response)
                   }
               });
           });
        });
    </script>
</head>
<body>
    <a href="test/ssm.html">测试ssm整合环境</a>

    <br/>

    <button id="btn1">Send[5, 8, 12]</button>
    <form method="get" action="test/md5.html">
        测试MD5：<input name="md5_str" type="text"> <input type="submit">
    </form>
    <a href="admin/to/login/page.html">管理员登录页面</a>
</body>
</html>
