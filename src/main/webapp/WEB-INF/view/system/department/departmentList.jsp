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
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/static/lib/treeTable/themes/vsStyle/treeTable.min.css" type="text/css">
<title>产品分类</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 产品管理 <span class="c-gray en">&gt;</span> 产品分类 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div>
	<div  id="treeDemo" style="float:left;width:20%" class="ztree"></div>
	<div  style="float:left;width:78%" class="table">
		<table id="table" class="table table-border table-bordered table-hover table-bg">
			<thead>
				<tr>
					<th scope="col" colspan="6">部门管理</th>
				</tr>
				<tr class="text-c">
					<th width = "150px">部门名称</th>
					<th>部门编号</th>
					<th>部门级别</th>
					<th>是否可用</th>
				</tr>
			</thead>
			<tbody id = "tbody">
			</tbody>
		</table>
	</div>
</div>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/treeTable/jquery.treeTable.min.js"></script> 

<script type="text/javascript">
var setting = {
	view: {
		dblClickExpand: false,
		showLine: false,
		selectedMulti: false
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: ""
		}
	},
	callback: {
		beforeClick: function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			if (treeNode.isParent) {
				zTree.expandNode(treeNode);
				return false;
			} else {
				demoIframe.attr("src",treeNode.file + ".html");
				return true;
			}
		}
	}
};

		
var code;
		
function showCode(str) {
	if (!code) code = $("#code");
	code.empty();
	code.append("<li>"+str+"</li>");
}


/**
 * 获取树的数据
 */
function getTreeData(){
	$.ajax({
		type: 'GET',
		url: '<%=request.getContextPath()%>' + '/department/getTreeData.do',
		dataType: 'json',
		success: function(data){
			if (data.OK == 1) {
				zNodes = data.treeData;
				var t = $("#treeDemo");
				t = $.fn.zTree.init(t, setting, zNodes);
				demoIframe = $("#testIframe");
				var zTree = $.fn.zTree.getZTreeObj("tree");
			}
		},
		error:function(data) {
			console.log(data.msg);
		},
	});	
}

/**
 * 获取树状表的数据
 */
function getTreeTableData() {
	$.ajax({
		type: 'GET',
		url: '<%=request.getContextPath()%>' + '/department/getTreeTableData.do',
		dataType: 'json',
		success: function(data){
			if (data.OK == 1) {
				var departmentList = data.departmentList;
				if (departmentList.length > 0) {
					var html = "";
					for (var i = 0; i < departmentList.length; i++) {
						html += "<tr class='text-c' id = '"+ departmentList[i].id +"' pId = '"+ departmentList[i].parentId +"'>";
						html += "<td>" + departmentList[i].name + "</td>";
						html += "<td>" + departmentList[i].departmentCode + "</td>";
						html += "<td>" + departmentList[i].grade + "</td>";
						html += "<td>" + departmentList[i].useable + "</td>";
						html += "</tr>";
					}
					$("#tbody").html(html);
					$("#table").treeTable({expandLevel : 5});
				}
			}
		},
		error:function(data) {
			console.log(data.msg);
		},
	});	
}
		
$(document).ready(function(){
	getTreeData();
	getTreeTableData();

	 
});
</script>
</body>
</html>