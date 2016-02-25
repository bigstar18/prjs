<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<title>
default
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{
    document.location.href = "<c:url value="/timebargain/baseinfo/consigner.do?crud=create&funcflg=editConsigner"/>";
}

function status2(){
	var status = document.getElementById("status").value;
	if (status == "1") {
		batch_do2('�޸Ĵ�Ϊί��Ա״̬','<c:url value="/timebargain/baseinfo/consigner.do?crud=correct&funcflg=updateStatusConsigner"/>');
	}
	if (status == "2") {
		batch_do2('�޸Ĵ�Ϊί��Ա״̬','<c:url value="/timebargain/baseinfo/consigner.do?crud=incorrect&funcflg=updateStatusConsigner"/>');
	}
	
}


// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>	
	<ec:table items="list" var="consigner" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/consigner.do?funcflg=searchConsigner"	
			height="390px" 
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="trader.xls" 
			csvFileName="trader.csv"
			showPrint="true" 
			filterable="true" 
			listWidth="100%"
			minHeight="300"
			title=""
	>		
		<ec:row>
		    <ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${consigner.consignerID}" style="text-align:center;padding-left:9px;width:30;" viewsAllowed="html" />				
            <ec:column property="consignerID" title="��Ϊί��Ա����" width="20%" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/consigner.do?crud=update&funcflg=editConsigner&consignerID=<c:out value="${consigner.consignerID}"/>"><c:out value="${consigner.consignerID}"/></a> 
            </ec:column>
			<ec:column property="name" title="��Ϊί��Ա����" width="20%" style="text-align:center;"/>
			<ec:column property="status" title="��Ϊί��Ա״̬" width="20%" style="text-align:center;" />			
			<ec:column property="type" title="����" width="20%" style="text-align:center;" editTemplate="ecs_t_type" mappingItem="CONSIGNERTYPE"/>	
			
			<ec:column property="_2" title="�ɲ���������" width="20%" tipTitle="����ɲ�����������Ϣ" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/consigner.do?funcflg=editConsignerCode&consignerID=<c:out value="${consigner.consignerID}"/>"/><img src="<c:url value="/timebargain/images/tool.gif"/>"></a> 
            </ec:column>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do2('ɾ����Ϊί��Ա','<c:url value="/timebargain/baseinfo/consigner.do?funcflg=deleteConsigner"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
		</ec:extend>		
	</ec:table>
	
</td></tr>

<tr></tr><tr></tr>
<tr>
	<td>
		<select id="status">
			<option value="1">����</option>
			<option value="2">��ֹ��¼</option>
		</select>
		<input type="button" class="button" value="�޸�״̬"  onclick="status2()">
	</td>
</tr>
</table>
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TRADER_STATUS" />
		</select>
	</textarea>	
	<textarea id="ecs_t_type" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="type" >
			<ec:options items="CONSIGNERTYPE" />
		</select>
	</textarea>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

</html>
