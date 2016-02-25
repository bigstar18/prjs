<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="org.ecside.table.limit.*" %>
<%@ page import="org.ecside.util.RequestUtils" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelayPattern" %>
<html>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<script type="text/javascript">
	function doQuery()
	{
		var CommodityID = document.getElementById("commodityID").value;
		var firmID = document.getElementById("firmID").value;
		//var filter = " and 1=1 ";
		//if(CommodityID!=""){
		//	filter = filter + " and CommodityID='"+CommodityID+"'";
		//}
		//if(firmID!=""){
		//	filter = filter + " and (FirmID_B='"+firmID+"' or FirmID_S='"+firmID+"')";
		//}
		//document.getElementById("condition").value = filter;
		//window.location.href="settle.jsp?condition="+filter;
		window.location.href="settle.jsp?CommodityID="+CommodityID+"&firmID="+firmID;
	}
	
	function add_onclick()
	{
		var result = window.showModalDialog("createSettleRecord.jsp?d="+Date(),"", "dialogWidth=500px; dialogHeight=330px; status=no;scroll=yes;help=no;"); 
		if(result)
		{
			//window.location.href="settle.jsp";		
			window.location.reload();	
		}
		
	}

	function edit_onclick(id)
	{
		if(confirm("��ȷ��Ҫ���˲�����")){
			var result = window.showModalDialog("verifySettle.jsp?matchID="+id+"&d="+Date(),"", "dialogWidth=500px; dialogHeight=330px; status=no;scroll=yes;help=no;"); 
			if(result)
			{
				window.location.reload();	
			}
		}
		
	}
	function down_onclick(id)
	{
		if (confirm("��ȷ��Ҫ���˲�����")) {
			var result = window.showModalDialog("verifySettleDown.jsp?matchID="+id+"&d="+Date(),"", "dialogWidth=500px; dialogHeight=330px; status=no;scroll=yes;help=no;"); 
			if(result)
			{
				window.location.reload();	
			}
		}
		
	}
	function trunTo	(v)
	{
		window.location.href="settle.jsp";
	}
	//��������
	function resetData()
	{
		document.getElementsByName("firmID")[0].value="";
		document.getElementsByName("commodityID")[0].value="";
		
	}
</script>
<%
	//�״η��ʻ��߰�������ѯ
	String settlePageInfo = request.getParameter("settlePageInfo");
	String settlePageSize = request.getParameter("settlePageSize");
	String settleTotalPage = request.getParameter("settleTotalPage");	
	String CommodityID = request.getParameter("CommodityID");
	String firmID = request.getParameter("firmID");

	String condition = "";
	if(CommodityID != null && !"".equals(CommodityID.trim())){
		condition = condition + " and t.CommodityID like '%"+CommodityID+"%'";
	}else{
		CommodityID="";
	}
	if(firmID != null && !"".equals(firmID.trim())){
		condition = condition + " and (FirmID_B like '%"+firmID+"%' or FirmID_S like '%"+firmID+"%')";
	}else{
		firmID="";
	}
	List settleList = SettleManageDelayPattern.getSettlesNew(condition);
	request.setAttribute("settleList",settleList);
	Map statusMap=new HashMap();
	statusMap.put("0","δ����");
	statusMap.put("1","�����");
	statusMap.put("-1","��ͨ��");
	statusMap.put("-2","����");
	request.setAttribute("statusMap",statusMap);
	Map resultMap=new HashMap();
	resultMap.put("1","��Լ");
	resultMap.put("2","��ΥԼ");
	resultMap.put("3","����ΥԼ");
	resultMap.put("4","˫��ΥԼ");
	request.setAttribute("resultMap",resultMap);
%>
<body>
	<fieldset>
		<legend class="common"><b>��ѯ����</b></legend>
		<span>
		<table class="common" align="left" valign="center">
			<tr class="common">
			<td align="left">&nbsp;&nbsp;��Ʒ���룺<input type="text" name="commodityID" value="<%=CommodityID%>" onkeypress="notSpace()" style="ime-mode:disabled"  maxlength="16"></td>
			<td align="left">&nbsp;&nbsp;�����̴��룺<input type="text" name="firmID" value="<%=firmID%>" onkeypress="notSpace()" style="ime-mode:disabled"  maxlength="16"></td>
			<td align="left">&nbsp;&nbsp;<input type="button" class="button" value="��ѯ" onclick="return doQuery();">
			<input type="button" class="button" value="����" onclick="resetData();">
			</td>
			</tr>
		</table>
		</span>
	</fieldset>
	
	<!-- ecsideչʾ���� -->
	<table class="common" align="center" width="100%">
	<tr class="common"><td>
		  <ec:table items="settleList" 
		  	         autoIncludeParameters="${empty param.autoInc}"
		  	         var="settle" 
		  	         action=""	
		  	         height="500px" 
		  	         title=""
		  	         rowsDisplayed="20"
			           minHeight="300"
			           listWidth="100%"
		  >
			
			<ec:row>
				<ec:column property="MatchID" title="��ͬ��" width="10%" style="text-align:center;">	
					
				</ec:column>
				<ec:column property="CommodityID" title="��Ʒ����" width="10%" style="text-align:center;" />
				<ec:column property="Quantity" title="��������" width="10%" style="text-align:center;" />
				<ec:column property="FirmID_B" title="�򷽽����̴���" width="10%" style="text-align:center;" />
				<ec:column property="FirmID_S" title="���������̴���" width="10%" style="text-align:center;" />
				<ec:column property="Status" title="״̬" editTemplate="ecs_t_status" mappingItem="statusMap" width="10%" style="text-align:center;" />
				<ec:column property="RegStockID" title="ע��ֵ�"  width="10%" style="text-align:center;" />
				<ec:column property="Result" title="ִ�н��" editTemplate="ecs_t_status1" mappingItem="resultMap" width="10%" style="text-align:center;" />			
				<ec:column property="null" title="���" width="5%" style="text-align:center;">
					<c:choose>
						<c:when test="${!not empty settle.RegStockID &&  fn:contains(settle.MatchID,'ATS')}">
							<font color="red">�貹¼�ֵ�</font>
						</c:when>
						<c:otherwise><a href="javascript:edit_onclick('${settle.MatchID}')">���</a></c:otherwise>
					</c:choose>
				</ec:column>
				<ec:column property="null" title="����" width="5%" style="text-align:center;">
					<c:choose>
						<c:when test="${!not empty settle.RegStockID &&  fn:contains(settle.MatchID,'ATS')}">
							<font color="red">�貹¼�ֵ�</font>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${!fn:contains(settle.MatchID,'ATS')}">
									<a href="javascript:down_onclick('${settle.MatchID}')">����</a>
								</c:when>
								<c:otherwise>
								    
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</ec:column>
			</ec:row>
			<ec:extend><a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></ec:extend>		
		</ec:table>
	</td></tr>
	</table>
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="statusMap" />
		</select>
	</textarea>
	<textarea id="ecs_t_status1" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="resultMap" />
		</select>
	</textarea>
</body>
</html>
<script type="text/javascript">
	var settlePageInfo=<%=settlePageInfo%>;
	var settlePageSize=<%=settlePageSize%>;
	var settleTotalPage=<%=settleTotalPage%>
	function turnPages()
	{
	
	if(settleTotalPage)
	{
		//ECSideUtil.gotoPageByInput(ec.ec_pg,'ec');
		try{
		ECSideUtil.gotoPage(settlePageInfo,'ec');
	  }
	  catch(err)
	  {
	  	  setTimeout("turnPages()",10);
	  }	  
	}
}

 function isCan()
 {
 	if(settleTotalPage)
 	{
 	  //alert(ec.waitingBar);
 	  if(ec.ec_waitingBar)
 	  {
 	  	turnPages();
 	  }
 	  else
 	  {
 	  	setTimeout("isCan()",10);
 	  }
 	}
 }

if(settleTotalPage)
{
	  ec.ec_pg.value=settlePageInfo;
	  ec.ec_rd.value=settlePageSize;
    ec.ec_totalpages.value=settleTotalPage;
    ec.ec_crd.value=settlePageSize;
	  for (var i=0;i<ec_table.rows.length;i++)                
  {
  	ec_table.rows(i).style.display = "none";		
  }
}


	setTimeout("turnPages()",10);
</script>