<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级信息管理</title>
<link rel="stylesheet" type="text/css"
	href="../jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript"
	src="../jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="../jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">

	var url;
	
	<!--搜索-->
	function searchGrade() {
		$('#dg').datagrid('load', {
			gradeName : $('#s_gradeName').val()
		});
	}

	<!--删除-->
	function deleteGrade() {
		var selectedRows = $('#dg').datagrid('getSelections');//获取所有选中项的集合
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var strIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].gradeId);
		}
		var ids = strIds.join(",");
		alert(ids);
		$.messager.confirm("系统提示", "您确定要删除这<font color=red>"
				+ selectedRows.length + "</font>条数据吗?", function(r) {
			if (r) {
				$.post("../gradeDelete", {
					delIds : ids
				}, function(result) {
					if (result.success) {
						$.messager.alert("系统提示", "您已成功删除<font color=red>"
								+ result.delNums + "</font>条数据！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示',selectedRows[result.errorIndex].gradeName+result.error);
					}
				}, "json");
			}
		});
	}

	<!--添加-->
	function addGrade() {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '班级信息添加');
		$('#fm').form('clear');
		url='../gradeSave';
	}
	
	<!--班级信息保存操作-->
    function saveUser(){
        $('#fm').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success : function(result) {
			/* 	if (result.error) {
					$.messager.alert("系统提示", result.error);
					return;
				} else {
					$.messager.alert("系统提示", "添加成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				} */
				
                var result = eval('('+result+')');
                if (result.error){
                    $.messager.show({
                        title: 'Error',
                        msg: result.error
                    });
                } else {
                	$.messager.alert("系统提示", "添加成功");
                    $('#dlg').dialog('close');        // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
				
			}
        });
    }
    
    <!--班级信息修改操作-->
    function modifyGrade(){
    	var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一条要修改的数据！");
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "编辑班级信息");
		$("#fm").form("load", row);
		url = "../gradeSave?gradeId=" + row.gradeId;
    }
    
    <!--重置操作-->
    function resetValue() {
		$("#gradeName").val("");
		$("#gradeDesc").val("");
	}
	
</script>

</head>
<body style="margin: 5px">
	<table id="dg" title="班级信息" class="easyui-datagrid" fitColumns="true"
		pagination="true" rownumbers="true" fit="true" url="../gradeList"
		toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="gradeId" width="50px" align="center">编号</th>
				<th field="gradeName" width="100px" align="center">班级名称</th>
				<th field="gradeDesc" width="250px" align="center">班级描述</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<div>
			<a href="javascript:addGrade()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="javascript:modifyGrade()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:deleteGrade()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			&nbsp;班级名称：<input type="text" name="s_gradeName" id="s_gradeName" />
			<a href="javascript:searchGrade()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 400px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post" style="margin: 0; padding: 20px 50px">
			<div style="margin-bottom: 20px; font-size: 14px; border-bottom: 1px solid #ccc">班级信息</div>
			<div style="margin-bottom: 10px">
				班级名称：&nbsp;<input name="gradeName" id="gradeName" class="easyui-validatebox" required="true" />
			</div>
			<div style="margin-bottom: 10px">
				班级描述：&nbsp;<input name="gradeDesc" id="gradeDesc" class="easyui-textbox" />
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width: 90px">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width: 90px">取消</a>
	</div>

</body>
</html>