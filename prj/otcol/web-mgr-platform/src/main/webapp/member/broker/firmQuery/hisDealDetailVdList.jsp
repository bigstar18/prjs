<%@ page pageEncoding="GBK"%>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import='java.util.Date'%>
<%@ page import='gnnt.MEBS.base.query.*'%>
<%
   String memberPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/member";
   
   String nowDate = Utils.formatDate("yyyy-MM-dd",new Date());
   String nowTime = Utils.formatDate("yyyyMMdd HHmm",new Date());
   pageContext.setAttribute("nowDate",nowDate);
   pageContext.setAttribute("nowTime",nowTime);
%>

<html xmlns:MEBS>
	<head>
	  <title>��������ϸ(����)</title>
		<%@ include file="/timebargain/common/ecside_head.jsp"%>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		 
		<import namespace="MEBS" implementation="<%=memberPath%>/public/calendar.htc">
		
		<script type="text/javascript">
		//�ύ����
		function submitForm(){
			var startDate = document.getElementById("scleardate").value;
			var endDate = document.getElementById("ecleardate").value;
			

		  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		  {
			if(startDate == ""){
				alert("��ʼ���ڲ���Ϊ�գ�");
				frm_query.scleardate.focus();
				return false;
				
			}
			if(endDate == ""){
				alert("�������ڲ���Ϊ�գ�");
				frm_query.ecleardate.focus();
				return false;
				
			}
			if(!isDateFomat(startDate))
		    {
		        alert("��ʼ���ڸ�ʽ����ȷ��\n�磺" + '<%=nowDate%>');
		        frm_query.scleardate.value = "";
		        frm_query.scleardate.focus();
		        return false;
		    }
		    if(!isDateFomat(endDate))
		    {
		        alert("�������ڸ�ʽ����ȷ��\n�磺" + '<%=nowDate%>');
		        frm_query.ecleardate.value = "";
		        frm_query.ecleardate.focus();
		        return false;
		    }
		  
		    if ( startDate > '<%=nowDate%>' ) { 
		        alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
		        frm_query.scleardate.focus();
		        return false; 
		    } 
		    if ( startDate > endDate ) { 
		        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
		        return false; 
		    } 
		  }  
			  frm_query.submit();
			
		}
		//��������
		function resetForm(){
			document.getElementById("firmId").value="";
			document.getElementById("brokerId").value="";
			frm_query.bsFlag.value="";
			document.getElementById("scleardate").value="";
			document.getElementById("ecleardate").value="";
			document.getElementById("bsFlag").value = "";
			document.getElementById("scleardate").value = "";
			document.getElementById("ecleardate").value = "";	
       }
		</script>
	</head>
	<body leftmargin="2" topmargin="0">
	
		<table width="100%">
		    <tr>
				<td>
					<form name="frm_query" action="${pageContext.request.contextPath}/member/feeDetailController.mem?funcflg=hisDealDetailVdList"
						method="POST" styleClass="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>��ѯ����</b>
							</legend>
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35" width="100%">
							<td align="right">�����̱�ţ�</td>
							<td align="left">
								<input type="text" size="10" name="_brokerId[like]" id="brokerId" value="<c:out value='${oldParams["brokerId[like]"]}'/>" 
onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
						
							<td align="right">�����̴��룺</td>
							<td align="left">
								<input type="text" size="10" name="_firmId[like]" id="firmId" value="<c:out value='${oldParams["firmId[like]"]}'/>" 
onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
							<td align="right">�ɽ����ͣ�</td>
							<td align="left">
							<select id="bsFlag" name="_bsFlag[=]">
								<option value="">ȫ��</option>
								<option value="1">��ɽ�</option>
								<option value="2">���ɽ�</option>
							</select>
							<script>
								frm_query.bsFlag.value = "<c:out value='${oldParams["bsFlag[=]"]}'/>";
							</script>
							</td>
							<td align="right">�������ڣ�</td>
							<td align="left" colspan="6">
								<MEBS:calendar eltName="_trunc(cleardate)[>=][date]" eltCSS="date" eltID="scleardate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(cleardate)[>=][date]"]}'/>"/>
							&nbsp;��&nbsp;
							    <MEBS:calendar eltName="_trunc(cleardate)[<=][date]" eltCSS="date" eltID="ecleardate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(cleardate)[<=][date]"]}'/>"/>
							</td>
							<td align="right">
								<input type="button" class="btn" value="��ѯ" onclick="submitForm();">&nbsp;
								<input type="button" class="btn" value="����" onclick="resetForm();">
							</td>
						</tr>
					</table>
						</fieldset>
					</form>
				</td>
			</tr>
			
			<tr>
				<td>
					<ec:table items="resultList" var="result"
						action="${pageContext.request.contextPath}/member/feeDetailController.mem?funcflg=hisDealDetailVdList"
						xlsFileName="��������ϸ(����)${nowTime}.xls"
					    showPrint="false"
						rowsDisplayed="20" listWidth="100%" 
						minHeight="300"
						retrieveRowsCallback="limit"
						>
						<ec:row>
						  <ec:column property="brokerId" title="�����̱��"  width="10%" style="text-align:center;" />
						  <ec:column property="firmId" title="�����̴���"  width="10%" style="text-align:center;" />
						  <ec:column property="clearDate" title="��������"  width="10%" style="text-align:center;" >
						     <fmt:formatDate value="${result.clearDate}" pattern="yyyy-MM-dd"/>
						  </ec:column>
						  <ec:column property="tradeNo" title="�ɽ���"  width="10%" style="text-align:center;" />
						  <ec:column property="bsFlag" title="�ɽ�����"  width="10%" style="text-align:right;" >						   
						    <c:if test="${result.bsFlag==1}">
								<c:out value="��ɽ�"/>
							</c:if>
							<c:if test="${result.bsFlag==2}">
								<c:out value="���ɽ�"/>
							</c:if>
						  </ec:column>
						  <ec:column property="quantity" title="�ɽ���"  width="10%" style="text-align:right;" calcTitle="�ϼ�" format="#,###" calc="total">
						    <fmt:formatNumber value="${result.quantity}" pattern="#,###"/>
						  </ec:column>
						  <ec:column property="tradeFee" title="����������(Ԫ)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${result.tradeFee}" pattern="#,##0.00"/>
						  </ec:column>
						
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
