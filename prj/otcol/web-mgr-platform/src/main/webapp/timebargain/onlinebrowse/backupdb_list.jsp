<%@ include file="/timebargain/common/taglibs.jsp" %>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--
function chgGroup()
{
    var ecForm = document.forms.ec;
    //ecForm.target = "HiddFrame";
	if (confirm("��ȷ��Ҫ�ֹ�������"))
    {
        var form = document.forms.ec;
        form.action = '<c:url value="/timebargain/onlinebrowse/onlinebrowse.do?funcflg=backupdbcopy"/>&autoInc=false';
        form.submit();
    }
    //batch_do('�ֹ�����','<c:url value="/timebargain/onlinebrowse/onlinebrowse.do?funcflg=backupdbcopy&marketID="/>' + ecForm.marketID.value);
    //ecForm.target = "ListFrame";
}
// -->
</script>
</head>
<body>
<table width="100%">
  <tr><td>
	<ec:table items="backupdbList" var="backupdb" 
			action="${pageContext.request.contextPath}/timebargain/onlinebrowse/onlinebrowse.do?funcflg=backupdb_list"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="backupdbList.xls" 
			csvFileName="backupdbList.csv"
			showPrint="true"
			listWidth="100%"
			rowsDisplayed="1000"
			minHeight="300"
			title=""
	>
		<ec:row>
		    <ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${backupdb.name}" viewsAllowed="html" style="text-align:center;padding-left:6px;width:30;" width="30"/>
		    <ec:column property="name" title="�ļ�����" width="400" style="text-align:center;">
		    <c:out value="${backupdb.name}"/>
            </ec:column>
            <ec:column property="length" title="�ļ���С" width="250" style="text-align:center;"/>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="javascript:batch_do('ȷ��Ҫɾ��dmp','<c:url value="/timebargain/onlinebrowse/onlinebrowse.do?funcflg=backupdbDel"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
			&nbsp;
			<a href="#" onclick="chgGroup()"><img src="<c:url value="/timebargain/images/manual.gif"/>"></a>
		</ec:extend>
	</ec:table>
	</td></tr>
</table>
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	

<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
