<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>系统用户浏览</title>
		<%@ include file="/public/ecsideLoad.jsp"%>


	</head>
	<body><br/>
	<form name="myForm" action="${basePath}/teacher/list.action" method="post">
		<fieldset width="50%" height="60%">
	<legend>
			查询条件
	</legend>
	<table >
		<tr>
			<td>  id: <input type="text" name="_id[=][long]" value="${oldParams['id[=][long]'] }"/> &nbsp;&nbsp;&nbsp; </td>
			<td>  名称: <input type="text" name="_name[like]" value="${oldParams['name[like]'] }"/> &nbsp;&nbsp;</td>
			<td>  <input type="submit" value="查询"/></td>
		</tr>
	</table>
		</fieldset>
	</form>
	<table id="editer" border="0" cellspacing="0" cellpadding="0"
			width="100%" align="right">
			<tr>
				<td align="right">
					<button id='addTeacher' style="color: red" onclick="add()">
						添加
					</button>
					&nbsp;&nbsp;
					<button id='deleteTeacher' style="color: red" onclick="deleteByCheckBox()">
						删除
					</button>
					&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	<br/><br/>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr><td>

	    <ec:table items="resultList" 
	    	      sortable="true" 
		  	      autoIncludeParameters="${empty param.autoInc}"
		  	      var="test" 
		  	      action="${basePath}/teacher/list.action"	
		  	      classic="true"
		  	      title=""
		  	      useAjax="true"
		  	      doPreload="false"
		  	      editable="true"
		  	      
			      minHeight="300"
			      listWidth="100%"
			      height="500px" 
			      resizeColWidth="true"

			      retrieveRowsCallback="limit"    
	              sortRowsCallback="limit"    
	              filterRowsCallback="limit"
	               
				  csvFileName="导出列表.csv"
	              
	              filterable="true"
	                
	              toolbarContent="navigation|pagejump |pagesize|refresh save add del|export|extend|status" 
		  >
		  
		  
		<ec:row recordKey="${test.id}" ondblclick="return update(${test.id});">
			<ec:column cell="checkbox"  headerCell="checkbox" alias="ids" value="${test.id}" 
				width="22" viewsAllowed="html" />
			<ec:column property="id[=][long]" title="id" width="20%" style="text-align:center; " value="${test.id}"/>
			<ec:column property="name[Like]" title="名称" width="40%" style="text-align:center;" value="${test.name}"/>
			<ec:column property="age[=][int]" title="年龄" width="20%" style="text-align:center;" value="${test.age}"/>
            <ec:column  property="address[like]" title="地址" width="20%" style="text-align:center;" value="${test.address}"/>
		</ec:row>
		
		</ec:table>
	 </td></tr>
	 </table>
	 
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
		</textarea>

		<SCRIPT type="text/javascript">
		function add(){
			var url="${basePath}/teacher/forwardAdd.action";
			ecsideDialog(url);
		}
	
	
		function update(id){
			var url="${basePath}/teacher/forwardUpdate.action?obj.id="+id;
			ecsideDialog(url);
		}
			
		function deleteByCheckBox(){
	 		var url="${basePath}/teacher/delete.action"
			deleteEcside(ec.ids,url);
		}
		</SCRIPT>
	</body>
</html>