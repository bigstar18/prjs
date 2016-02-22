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
	<br/><br/>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr><td>

	    <ec:table items="resultList" 
	    	      sortable="true" 
		  	      autoIncludeParameters="${empty param.autoInc}"
		  	      var="obj" 
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
	              
	              filterable="true"
	                
	              toolbarContent="navigation|pagejump |pagesize|refresh|export|extend|status" 
		  >
		  
		  
		<ec:row recordKey="${obj.id}" ondblclick="return update(${obj.id});">
			<ec:column property="id[=][long]" title="id" width="20%" style="text-align:center; " value="${obj.id}"/>
			<ec:column property="proposer[like]" title="申请人" width="20%" style="text-align:center;" value="${obj.proposer}"/>
            <ec:column  property="applytime" title="申请时间" width="20%" style="text-align:center;" value="${obj.applytime}"/>
            <ec:column  property="statusDiscribe" title="申请状态" width="20%" style="text-align:center;" value="${obj.statusDiscribe}"/>
            <ec:column  property="detail" title="详情" width="20%" style="text-align:center;">
            	<a href="<c:out value='${detailPath}'/>" >详情展示</a> 
            </ec:column>
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