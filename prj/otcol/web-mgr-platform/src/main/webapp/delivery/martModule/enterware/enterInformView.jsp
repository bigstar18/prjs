<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title><%=TITLE%></title>
	
	<style type="text/css">
	<!--
	.cd_bt {
		font-size: 12px;
		line-height: 16px;
		font-weight: bold;
		text-decoration: none;
		text-align: right;
		padding-right: 5px;
	}
	-->
    </style>
</head>
<body>
  <form id="frm" method="POST" action="<%=basePath%>servlet/enterInformController.wha?funcflg=enterInformView">
  <input type="hidden" name="enterInformId" value="<c:out value='${operateObj.id }'/>">
       
	<fieldset width="100%" id="fieldset">
	<legend id="legend"><div id="title">���֪ͨ��<font style="color: red;">(${status.name })</font></div></legend>
		<br>
		<table width="96%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
			
			<tr height="18px">
				<td class="cd_bt" rowspan=2>${FIRMID}��</td>
				<td colspan="3" class="cd_list1"  rowspan=2>${dealer.firmId}
				</td>
				<td class="cd_bt">��ϵ�ˣ�</td>
				<td class="cd_list11">
				<c:choose>
					<c:when test="${dealer.linkman == null}">
					��
					</c:when>
					<c:otherwise>
					${dealer.linkman }
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr height="18px">
				<td class="cd_bt">��ϵ�绰��</td>
				<td class="cd_list11">
				<c:choose>
					<c:when test="${dealer.tel == null}">
						��
					</c:when>
					<c:otherwise>
					${dealer.tel }
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr height="36px">
				<td class="cd_bt">���ղֿ⣺</td>
				<td  class="cd_list1" colspan="3">${warehouse.name }</td>
				<td  class="cd_bt">Ʒ�����ƣ�</td>
				<td  class="cd_list1">${commodity.name }</td>
			</tr>
			<tr height="36px">
			<td class="cd_bt">�����ʱ�䣺</td>
				<td  class="cd_list1"  colspan="3"><fmt:formatDate value="${operateObj.enterDate}" pattern="yyyy-MM-dd"/></td>
				<td width="15%" class="cd_bt" id="c1">���ࣺ</td>
				<td width="18%" class="cd_list1" id="bre">
				   ${operateObj.sort }
				</td>
			</tr>
			<tr height="36px">
				<td width="15%" class="cd_bt">���������</td>
				<td width="35%"  colspan="3" class="cd_list1">
				  <fmt:formatNumber value="${operateObj.weight }" pattern="#,##0.0000"/> 
				  <!-- ��ӶԵ�λΪ�յ��жϣ������λΪ�ա�����ʾ  add by yangpei -->
				  <c:if test="${commodity.countType !=null}">(${commodity.countType })</c:if>
				</td>
				<td width="15%" class="cd_bt" id="c2">�ȼ���</td>
				<td width="35%" class="cd_list1">
				  ${operateObj.grade }
				</td>
			</tr>
			<tr height="36px">
				<td class="cd_bt">ÿ��������</td>
				<td class="cd_list1" colspan="3">
				  <fmt:formatNumber value="${operateObj.unitWeight }" pattern="#,##0.0000"/>
				  <!-- ��ӶԵ�λΪ�յ��жϣ������λΪ�ա�����ʾ  add by yangpei -->
				  <c:if test="${commodity.countType !=null}">(${commodity.countType })</c:if>
				</td>
				<td  class="cd_bt">�������ң�</td>
				<td  class="cd_list1" >${operateObj.origin }</td>
			</tr>
			<tr height="36px">
			  
			  <td class="cd_bt">������</td>
			  <td colspan="3" class="cd_list1">${operateObj.quantity }</td>
				<td class="cd_bt">�������£�</td>
				<td class="cd_list1">${operateObj.productionDate }</td>
			</tr>
			<tr height="36px">
				<td width="15%" class="cd_bt">��װ��ʽ��</td>
				<td width="35%"  colspan="3" class="cd_list1">${operateObj.packaging }</td>
				<td width="15%" class="cd_bt" id="c3">���ţ�</td>
				<td width="35%"  class="cd_list1">${operateObj.lot }</td>
			</tr>
		</table>
		<br>
		<br>
		<div align="center">			
			<button id="return" class="smlbtn" type="button" onClick="freturn();">����</button>
           </div><br><br>
	</fieldset>
   </form>
</body>
</html>
<script type="text/javascript">
	function freturn()
	{
		frm.action = "<%=basePath%>servlet/enterInformController.wha?funcflg=enterInformReturn";
		frm.submit();
	}
	function printButton()
	{
		document.getElementById("print").style.visibility = "hidden";
		document.getElementById("return").style.visibility = "hidden";
		document.getElementById("fieldset").style.borderColor="white";
		document.getElementById("title").style.fontColor="white";
		document.getElementById("title").style.display="none",
		window.print();
		frm.submit();
	}	
</SCRIPT>