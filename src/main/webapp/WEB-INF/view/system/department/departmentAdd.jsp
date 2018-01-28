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

<title>添加部门</title>
<meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
	<form action="<%=request.getContextPath()%>/department/add.do" method="post" class="form form-horizontal" id="form-admin-department-add">
		<input type = "hidden" value = "${parentId }" id = "parentId" name = "parentId"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>部门名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" placeholder="" id="name" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">部门编号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  placeholder="" id="departmentCode" name="departmentCode">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<button type="submit" class="btn btn-success radius" id="admin-department-save" name="admin-department-save"><i class="icon-ok"></i> 确定</button>
			</div>
		</div>
	</form>
</article>


<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript">
$(function(){
	$(".permission-list dt input:checkbox").click(function(){
		$(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
	});
	$(".permission-list2 dd input:checkbox").click(function(){
		var l =$(this).parent().parent().find("input:checked").length;
		var l2=$(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
		if($(this).prop("checked")){
			$(this).closest("dl").find("dt input:checkbox").prop("checked",true);
			$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
		}
		else{
			if(l==0){
				$(this).closest("dl").find("dt input:checkbox").prop("checked",false);
			}
			if(l2==0){
				$(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
			}
		}
	});
	
	$("#form-admin-department-add").validate({
		rules:{
			name:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit();
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	});
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>