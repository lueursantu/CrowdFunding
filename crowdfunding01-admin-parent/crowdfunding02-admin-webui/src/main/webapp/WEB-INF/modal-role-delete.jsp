<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="deleteModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">删除角色</h4>
            </div>
            <div class="modal-body">
                <h3>确定删除以下角色吗？</h3>
                <div id="roleNameList" style="text-align: center"></div>
            </div>
            <div class="modal-footer">
                <button id="deleteRoleBtn" type="button" class="btn btn-primary">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->