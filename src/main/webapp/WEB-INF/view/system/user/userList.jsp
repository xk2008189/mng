<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/head.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/layui/css/layui.css" />
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 用户管理
 <a class="btn btn-success radius r" id="userRefresh"  style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c"> 
		<form action="<%=request.getContextPath()%>/user/userList.do" id = "searchForm">
			<input type = "hidden" name = "currentPageNo" id = "currentPageNo"/>
			<input type = "hidden" name = "pageSize" id = "pageSize" />
			日期范围：<input type="text" value = "${minCreateDate }" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" name="minCreateDate"  class="input-text Wdate" style="width:120px;">
			-
			<input type="text" value = "${maxCreateDate }"  onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" name="maxCreateDate" class="input-text Wdate" style="width:120px;">
			<input type="text"  value = "${name }"  class="input-text" style="width:250px" placeholder="请输入姓名" id="name" name="name">
			<button type="submit" class="btn btn-success" id="submitBtn" ><i class="Hui-iconfont">&#xe665;</i> 搜用户</button>
		</form>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="deleteMore()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> <a href="javascript:;" onclick="admin_add('添加管理员','<%=request.getContextPath()%>' + '/user/userAdd.do','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加管理员</a></span> <span class="r">共有数据：<strong>${page.count}</strong> 条</span> </div>
	<table class="table table-border table-bordered table-bg table-sort">
		<thead>
			<tr>
				<th scope="col" colspan="10">员工列表</th>
			</tr>
			<tr class="text-c">
				<th width="25"><input type="checkbox"></th>
				<th>员工编号</th>
				<th>登录名</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
				<th>邮箱</th>
				<th>创建时间</th>
				<th>是否登录</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="user">
				<tr class="text-c">
					<td><input type="checkbox" value="${user.id}" name="userId"></td>
					<td>${user.userCode}</td>
					<td>${user.userName}</td>
					<td>${user.name}</td>
					<c:if test="${user.gender eq 0}">
						<td>男</td>
					</c:if>
					<c:if test="${user.gender eq 1}">
						<td>女</td>
					</c:if>
					<td>${user.age}</td>
					<td>${user.email}</td>
					<td>
						<fmt:formatDate value="${user.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<c:if test="${user.loginFlag eq 0}">
						<td>否</td>
					</c:if>
					<c:if test="${user.loginFlag eq 1}">
						<td>是</td>
					</c:if>
					<td class="td-manage"><a style="text-decoration:none" onClick="admin_stop(this,'10001')" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a> 
					<a title="编辑" href="javascript:;" onclick="admin_edit('用户编辑','<%=request.getContextPath()%>' + '/user/userUpdate.do?userId=${user.id}','1','800','500')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> 
					<a title="删除" href="javascript:;" onclick="admin_del(this,'${user.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="page" style="text-align:right" ></div>
</div>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/layui/layui.js"></script>
<script type="text/javascript">
/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
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
		        console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
		        console.log(obj.limit); //得到每页显示的条数
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
/*管理员-增加*/
function admin_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-删除*/
function admin_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'GET',
			url: '<%=request.getContextPath()%>' + '/user/delete.do?userId='+id,
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

/*管理员-编辑*/
function admin_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}

function deleteMore() {
	var seleteIds = [];
	$('input[name="userId"]:checked').each(function(i){
		seleteIds.push($(this).val());
	})
	if (seleteIds.length > 0) {
		layer.confirm('确认要删除吗？',function(index){
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/user/deleteSelect.do',
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

/*管理员-停用*/
function admin_stop(obj,id){
	layer.confirm('确认要停用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
		$(obj).remove();
		layer.msg('已停用!',{icon: 5,time:1000});
	});
}

/*管理员-启用*/
function admin_start(obj,id){
	layer.confirm('确认要启用吗？',function(index){
		//此处请求后台程序，下方是成功后的前台处理……
		$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id)" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
		$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
		$(obj).remove();
		layer.msg('已启用!', {icon: 6,time:1000});
	});
}
</script>
</body>
</html>