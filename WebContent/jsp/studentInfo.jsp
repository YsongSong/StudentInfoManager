<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息管理</title>
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="../jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	
	<!--查询-->
	function serachStudent(){
		
		$('#dg').datagrid('load',{
			stuNo:$('#s_stuNo').val(),
			stuName:$('#s_stuName').val(),
			sex:$('#s_sex').combobox("getValue"),
			bbirthday:$('#s_bbirthday').datebox("getValue"),
			ebirthday:$('#s_ebirthday').datebox("getValue"),
			gradeId:$('#s_gradeId').combobox("getValue")
		});
	}
	
	<!--删除-->
	function deleteStudent() {
		var selectedRows = $('#dg').datagrid('getSelections');//获取所有选中项的集合
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var strIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].stuNo);
		}
		var ids = strIds.join(",");
		alert(ids);
		$.messager.confirm("系统提示", "您确定要删除这<font color=red>"
				+ selectedRows.length + "</font>条数据吗?", function(r) {
			if (r) {
				$.post("../studentDelete", {
					delIds : ids
				}, function(result) {
					if (result.success) {
						$.messager.alert("系统提示", "您已成功删除<font color=red>"
								+ result.delNums + "</font>条数据！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.error);
					}
				}, "json");
			}
		});
	}
	
	<!--添加-->
	function addStudent() {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '学生信息添加');
		$('#fm').form('clear');
		url='../studentSave';
	}
	
	<!--班级信息修改操作-->
    function modifyStudent(){
    	var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一条要修改的数据！");
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "编辑班级信息");
		$("#fm").form("load", row);
		url = "../studentSave?stuNo=" + row.stuNo;
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
    

</script>
</head>
<body style="margin: 5px">
	<table id="dg" title="学生信息" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" fit="true" url="../studentList" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="stuNo" width="50px" align="center">学号</th>
				<th field="stuName" width="100px" align="center">姓名</th>
				<th field="sex" width="50px" align="center">性别</th>
				<th field="birthday" width="100px" align="center">出生日期</th>
				<th field="gradeId" width="100px" align="center" hidden="true">班级ID</th>
				<th field="gradeName" width="100px" align="center">班级名称</th>
				<th field="email" width="150px" align="center">email</th>
				<th field="stuDesc" width="250px" align="center">学生信息备注</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:addStudent()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:modifyStudent()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteStudent()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
		<div>
			&nbsp;学号：&nbsp;<input type="text" name="s_stuNo" id="s_stuNo" size="10"/>
			&nbsp;姓名：&nbsp;<input type="text" name="s_stuName" id="s_stuName" size="10"/>
			&nbsp;性别：&nbsp;<select class="easyui-combobox" id="s_sex" name="s_sex" editable="false" panelHeight="auto">
				<option value="">请选择...</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
			&nbsp;出生日期：&nbsp;<input class="easyui-datebox" id="s_bbirthday" name="s_bbirthday" editable="false" size="10"/>->
			<input class="easyui-datebox" id="s_ebirthday" name="s_ebirthday" editable="false" size="10"/>
			&nbsp;班级：&nbsp;<input class="easyui-combobox" id="s_gradeId" name="s_gradeId" editable="false" size="15" data-options="valueField:'gradeId',textField:'gradeName',url:'../gradeComboList'"/>
			<a href="javascript:serachStudent()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>
	
	<!-- 添加  修改 内容框 -->
	<div id="dlg" class="easyui-dialog" style="width: 400px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post" style="margin: 0; padding: 20px 50px">
			<div style="margin-bottom: 20px; font-size: 14px; border-bottom: 1px solid #ccc">学生信息</div>
			<div style="margin-bottom: 10px">
				姓&nbsp;&nbsp;名：&nbsp;<input name="stuName" id="stuName" class="easyui-validatebox" required="true" />
			</div>
			<div style="margin-bottom: 10px">
				性&nbsp;&nbsp;别：&nbsp;<select class="easyui-combobox" id="sex" name="sex" editable="false" panelHeight="auto">
				<option value="">请选择...</option>
				<option value="男">男</option>
				<option value="女">女</option>
			</select>
			</div>
			<div style="margin-bottom: 10px">
				出生日期：&nbsp;<input class="easyui-datebox" id="birthday" name="birthday" editable="true" size="10"/>
			</div>
			<div style="margin-bottom: 10px">
				班&nbsp;&nbsp;级：&nbsp;<input class="easyui-combobox" id="gradeId" name="gradeId" editable="false" size="15" data-options="valueField:'gradeId',textField:'gradeName',url:'../gradeComboList'"/>
			</div>
			<div style="margin-bottom: 10px">
				email：&nbsp;<input name="email" id="email" class="easyui-validatebox" required="true" />
			</div>
			<div style="margin-bottom: 10px">
				备&nbsp;&nbsp;注：&nbsp;<input name="stuDesc" id="stuDesc" class="easyui-validatebox" required="true" />
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width: 90px">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width: 90px">取消</a>
	</div>
</body>
</html>