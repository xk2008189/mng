<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/layui/css/layui.css" />
<title>角色管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 角色管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<form action="<%=request.getContextPath()%>/role/roleList.do" id = "searchForm">
			<input type = "hidden" name = "currentPageNo" id = "currentPageNo"/>
			<input type = "hidden" name = "pageSize" id = "pageSize" />
			角色名称：<input type="text"  value = "${name }"  class="input-text" style="width:250px" placeholder="请输入角色名称" id="name" name="name">
			角色编号：<input type="text"  value = "${roleCode }"  class="input-text" style="width:250px" placeholder="请输入角色编号" id="roleCode" name="roleCode">
			<button type="submit" class="btn btn-success" id="submitBtn" ><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		</form>
	</div>
	<div class="cl pd-5 bg-1 bk-gray"> <span class="l"> <a href="javascript:;" onclick="deleteMore()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
	 <a class="btn btn-primary radius" href="javascript:;" onclick="admin_role_add('添加角色','<%=request.getContextPath()%>' + '/role/roleAdd.do','800')"><i class="Hui-iconfont">&#xe600;</i> 添加角色</a> </span> <span class="r">共有数据：<strong>${page.count}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="6">角色管理</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox" value="" name=""></th>
				<th>角色名</th>
				<th>角色编号</th>
				<th>角色类型</th>
				<th>是否可用</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="role">
				<tr class="text-c">
					<td><input type="checkbox" value="${role.id}" name="roleId"></td>
					<td>${role.name}</td>
					<td>${role.roleCode}</td>
					<td>${role.roleType}</td>
					<td>${role.useable}</td>
					<td class="f-14">
						<a title="编辑" href="javascript:;" onclick="admin_role_edit('角色编辑','<%=request.getContextPath()%>/role/roleUpdate.do?roleId=${role.id}','1')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="删除" href="javascript:;" onclick="admin_role_del(this,'${role.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="page" style="text-align:right" ></div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/layui/layui.js"></script>
<script type="text/javascript">
$(function(){
	layui.use(['laypage'], function(){
		var laypage = layui.laypage;
		laypage.render({
		    elem: 'page',
		    count: '${page.count}',
		    limit: '${page.pageSize}',
		    curr: '${page.currentPageNo}',
		    layout: ['limit', 'prev', 'page', 'next'],
		    jump: function(obj, first){
		        //obj包含了当前分页的所有参数，比如：
		        $("#currentPageNo").val(obj.curr);
		        $("#pageSize").val(obj.limit);
		        //首次不执行
		        if(!first){
		        	$("#searchForm").submit();
		        }
		    }
		});
	});
});
/*管理员-角色-添加*/
function admin_role_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-角色-编辑*/
function admin_role_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*管理员-角色-删除*/
function admin_role_del(obj,id){
	layer.confirm('角色删除须谨慎，确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>' + '/role/delete.do?roleId='+id,
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").remove();
				layer.msg('已删除!',{icon:1,time:1000});
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
}

function deleteMore() {
	var seleteIds = [];
	$('input[name="roleId"]:checked').each(function(i){
		seleteIds.push($(this).val());
	})
	if (seleteIds.length > 0) {
		layer.confirm('确认要删除吗？',function(index){
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/role/deleteSelect.do',
				data:{"seleteIds" : JSON.stringify(seleteIds)},
				dataType: 'json',
				success: function(data){
					location.replace(location.href);
					layer.msg('已删除!',{icon:1,time:1000});
				},
				error:function(data) {
					console.log(data.msg);
				},
			});		
		});
	}
}
</script>
</body>
</html>