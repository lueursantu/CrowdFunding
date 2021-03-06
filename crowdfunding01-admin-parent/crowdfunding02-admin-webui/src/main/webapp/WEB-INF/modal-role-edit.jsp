<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="editModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改角色</h4>
            </div>
            <div class="modal-body">
                <form class="form-signin">
                    <h4 class="form-signin-heading">请输入角色名称</h4>
                    <p>${requestScope.exception.message}</p>
                    <div class="form-group has-success has-feedback">
                        <input type="text" class="form-control" name="roleName" id="roleName" placeholder="角色名称" autofocus>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="saveRoleBtn" type="button" class="btn btn-primary">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->