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
	  <title>�����ѻ��ܱ�</title>
		<%@ include file="/timebargain/common/ecside_head.jsp"%>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		 
		<import namespace="MEBS" implementation="<%=memberPath%>/public/calendar.htc">
		
		<script type="text/javascript">
			//���ð�ť������д
			function resetForm(){
				document.getElementById("sfirmid").value="";
				document.getElementById("brokerid").value="";
				document.getElementById("efirmid").value="";
				frm_query.moduleId.value="";
				document.getElementById("breedName").value="";
				document.getElementById("sOccurDate").value="";
				document.getElementById("eOccurDate").value="";
			}
			//�ύ�����в�ѯ
			function submitForm(){
				var startDate = document.getElementById("sOccurDate").value;
				var endDate =  document.getElementById("eOccurDate").value;
				

			  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
			  {
				if(startDate == ""){
					alert("��ʼ���ڲ���Ϊ�գ�");
					frm_query.sOccurDate.focus();
					return false;
					
				}
				if(endDate == ""){
					alert("�������ڲ���Ϊ�գ�");
					frm_query.eOccurDate.focus();
					return false;
					
				}
				if(!isDateFomat(startDate))
			    {
			        alert("��ʼ���ڸ�ʽ����ȷ��\n�磺" + '<%=nowDate%>');
			        frm_query.sOccurDate.value = "";
			        frm_query.sOccurDate.focus();
			        return false;
			    }
			    if(!isDateFomat(endDate))
			    {
			        alert("�������ڸ�ʽ����ȷ��\n�磺" + '<%=nowDate%>');
			        frm_query.eOccurDate.value = "";
			        frm_query.eOccurDate.focus();
			        return false;
			    }
			  
			    if ( startDate > '<%=nowDate%>' ) { 
			        alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
			        frm_query.sOccurDate.focus();
			        return false; 
			    } 
			    if ( startDate > endDate ) { 
			        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
			        return false; 
			    } 
			  }  
				frm_query.submit();
			}
			
			function initSummarizingWay(){
				//���ܷ�ʽvalue
				var summarizingWay = document.getElementById("summarizingWay");

				//������/Ʒ�� ������
				var summarizingWayInpt1 = document.getElementById("summarizingWayInpt1");
				//�ֽ�����/���� ������
				var summarizingWayInpt2 = document.getElementById("summarizingWayInpt2");
				
				//Ʒ��
				var breed = document.getElementById("breedName");
				
				if(${oldParams['summarizingWay1'] == 'summarizingFirm'}){
					//������
					summarizingWayInpt1.value = "summarizingFirm";
					if(${oldParams['summarizingWay2'] == 'Summarizing'}){
						//����
						var summarizingWayOpt = eachSummarizingWay(summarizingWay, 1);
						summarizingWayOpt.selected = "selected";

						summarizingWayInpt2.value = "Summarizing";

						breed.disabled = "disabled";
						breed.style.cssText = "background-color: gray;";
					}else if(${oldParams['summarizingWay2'] == 'SettleDay'}){
						//�ֽ�����
						var summarizingWayOpt = eachSummarizingWay(summarizingWay, 3);
						summarizingWayOpt.selected = "selected";

						summarizingWayInpt2.value = "SettleDay";

						breed.disabled = "disabled";
						breed.style.cssText = "background-color: gray;";
					}
				}else if(${oldParams['summarizingWay1'] == 'summarizingBreed'}){
					//Ʒ��
					summarizingWayInpt1.value = "summarizingBreed";
					if(${oldParams['summarizingWay2'] == 'Summarizing'}){
						//����
						var summarizingWayOpt = eachSummarizingWay(summarizingWay, 2);
						summarizingWayOpt.selected = "selected";

						summarizingWayInpt2.value = "Summarizing";
					}else if(${oldParams['summarizingWay2'] == 'SettleDay'}){
						//�ֽ�����
						var summarizingWayOpt = eachSummarizingWay(summarizingWay, 4);
						summarizingWayOpt.selected = "selected";

						summarizingWayInpt2.value = "SettleDay";
					}
				}
				
				/*�������ܷ�ʽ,����val����valָ����option����*/
				function eachSummarizingWay(way, val){
					wayChildren = way.childNodes;
					for(var i in wayChildren){
						if(wayChildren[i].value == val){
							return wayChildren[i];
						}
					}
				}

			}
		</script>
	</head>
	<body leftmargin="2" topmargin="0" onload="initSummarizingWay()">
	
		<table width="100%">
		    <tr>
				<td>
					<form name="frm_query" action="${pageContext.request.contextPath}/member/feeDetailController.mem?funcflg=brokerUserFeeList"
						method="POST" styleClass="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>��ѯ����</b>
							</legend>
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35" width="100%">
							<td align="right" style="width: 12%">
								�����̿�ʼ��
							</td>
							<td align="left" style="width: 12%">
								<input name="_fb.firmid[>=]" id="sfirmid" size=10 type=text value="<c:out value='${oldParams["fb.firmid[>=]"]}'/>" onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
							<td align="right" style="width: 12%">
								�����̽�����
							</td>
							<td align="left" style="width: 12%">
								<input name="_fb.firmid[<=]" id="efirmid" size=10 type=text value="<c:out value='${oldParams["fb.firmid[<=]"]}'/>" onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
							<td align="right" style="width: 8%">
								�������ڣ�
							</td>
							<td align="left" style="width: 27%">
							<MEBS:calendar eltName="_trunc(OccurDate)[>=][date]" eltCSS="date" eltID="sOccurDate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(OccurDate)[>=][date]"]}'/>"/>
							&nbsp;��&nbsp;
							<MEBS:calendar eltName="_trunc(OccurDate)[<=][date]" eltCSS="date" eltID="eOccurDate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(OccurDate)[<=][date]"]}'/>"/>
							</td>
							
						</tr>
						<tr height="35" width="100%">
							<td align="right">���ܷ�ʽ��</td>
							<td align="left">
								<input type = "hidden" id = "summarizingWayInpt1" name = "_summarizingWay1" value = ""/>
								<input type = "hidden" id = "summarizingWayInpt2"name = "_summarizingWay2" value = ""/>
								<select id = "summarizingWay" onchange="summarizingWayChange()">
									<option value="1">�ֽ����̻���</option>
									<option value="2">�ֽ�����Ʒ�ֻ���</option>
									<option value="3">�ֽ����̽����ջ���</option>
									<option value="4">�ֽ�����Ʒ�ֽ����ջ���</option>
								</select>
								<!-- 
								<select name="_summarizingWay1">
								<c:if test="${oldParams['summarizingWay1'] == 'summarizingFirm'}">
									<option selected="selected" value="summarizingFirm">������</option>
									<option value="summarizingBreed">Ʒ&nbsp; ��</option>
								</c:if>
								<c:if test="${oldParams['summarizingWay1'] == 'summarizingBreed'}">
									<option value="summarizingFirm">������</option>
									<option selected="selected" value="summarizingBreed">Ʒ&nbsp; ��</option>
								</c:if>
								</select>
								
								&nbsp;&nbsp;&nbsp;&nbsp;
								<select name="_summarizingWay2">
								<c:if test="${oldParams['summarizingWay2'] == 'Summarizing'}">
									<option selected="selected" value="Summarizing">��&nbsp;&nbsp;&nbsp; ��</option>
									<option value="SettleDay">�ֽ�����</option>
								</c:if>
								<c:if test="${oldParams['summarizingWay2'] == 'SettleDay'}">
									<option value="Summarizing">��&nbsp;&nbsp;&nbsp; ��</option>
									<option selected="selected" value="SettleDay">�ֽ�����</option>
								</c:if>
								</select> -->
							</td>
							
							<td align="right">
								�����̱�ţ�
							</td>
							<td align="left">
								<input name="_brokerid[like]" id="brokerid" size=10 type=text value="<c:out value='${oldParams["brokerid[like]"]}'/>" onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
							
							<td align="right">Ʒ�֣�</td>
							<td align="left">
								<input type="text" size="10" name="_breedName[like]" id="breedName" value="<c:out value='${oldParams["breedName[like]"]}'/>" 
onkeypress="onlyNumberAndCharInput()" maxlength="16">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ģ�飺
								<select id="moduleId" name="_moduleId[=]">
									<option selected="true" value="">ȫ��</option>
									<option value="2">����</option>
									<option value="3">����</option>
									<option value="4">����</option>
								</select>
							</td>
							<script>
								frm_query.moduleId.value = "<c:out value='${oldParams["moduleId[=]"]}'/>";
							</script>
							<td align="left">
								<input type="button" class="btn" onclick="submitForm();" value="��ѯ">&nbsp;
								<input type="button" class="btn" onclick="resetForm();"value="����">&nbsp;
							</td>
						</tr>
					</table>
						</fieldset>
					</form>
				</td>
			</tr>
			
			<tr>
				<td>
				    <c:set var="time" value="${nowTime}" ></c:set>
					<ec:table items="resultList" var="brokerFeeSum"
						action="${pageContext.request.contextPath}/member/feeDetailController.mem?funcflg=brokerUserFeeList"
						xlsFileName="�����ѻ���${nowTime}.xls"
					    showPrint="false"
						rowsDisplayed="20" listWidth="100%" 
						minHeight="300"
						filterable="false">
						<ec:row>
						  <ec:column property="brokerId" title="����������"  width="10%" style="text-align:center;" />
						  <ec:column property="moduleId" title="ģ��"  width="10%" style="text-align:center;" >
						     <c:if test="${brokerFeeSum.moduleId=='2'}">����</c:if>
	  						 <c:if test="${brokerFeeSum.moduleId=='3'}">����</c:if>
	  						 <c:if test="${brokerFeeSum.moduleId=='4'}">����</c:if>
						  </ec:column>
						  <ec:column property="firmId" title="�����̴���"  width="10%" style="text-align:center;" />
						  <ec:column property="firmName" title="����������"  width="10%" style="text-align:center;" />
						  <c:if test="${oldParams['summarizingWay1'] == 'summarizingBreed'}">
							<ec:column property="breedName" title="Ʒ������"  width="10%" style="text-align:center;" />
						  </c:if>
						  <c:if test="${oldParams['summarizingWay1'] == 'summarizingFirm'}">
							<ec:column property="_1" title="Ʒ������" value="ȫ��" width="10%" style="text-align:center;" />
						  </c:if>
						  <ec:column property="quantity" title="�ɽ���"  width="10%" style="text-align:right;" calcTitle="�ϼ�" format="#,###" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.quantity}" pattern="#,###"/>
						  </ec:column>
						  <ec:column property="tradeFee" title="����������(Ԫ)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.tradeFee}" pattern="#,##0.00"/>
						  </ec:column>
						  <ec:column property="firstPay" title="Ӷ���׿�(Ԫ)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.firstPay}" pattern="#,##0.00"/>
						  </ec:column>
						  <ec:column property="secondPay" title="Ӷ��β��(Ԫ)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${brokerFeeSum.secondPay}" pattern="#,##0.00"/>
						  </ec:column>
						  <c:if test="${oldParams['summarizingWay2'] == 'SettleDay'}">
							<ec:column property="occurdate" title="����ʱ��" cell="date" format="date" width="10%" style="text-align:center;"/>
						  </c:if>
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		<script type="text/javascript">

		/*�ı���ܷ�ʽ*/
		function summarizingWayChange(){
			var summarizingWayVal = document.getElementById("summarizingWay").value;
			//������/Ʒ�� ������
			var summarizingWayInpt1 = document.getElementById("summarizingWayInpt1");
			//�ֽ�����/���� ������
			var summarizingWayInpt2 = document.getElementById("summarizingWayInpt2");

			var breed = document.getElementById("breedName");
			if(summarizingWayVal == '1'){
				//�����̻���
				summarizingWayInpt1.value = "summarizingFirm";
				summarizingWayInpt2.value = "Summarizing";

				breed.disabled = "disabled";
				breed.style.cssText = "background-color: gray;";
			}else if(summarizingWayVal == '2'){
				//Ʒ�ֻ���
				summarizingWayInpt1.value = "summarizingBreed";
				summarizingWayInpt2.value = "Summarizing";

				breed.removeAttribute("disabled");
				breed.removeAttribute("style");
			}else if(summarizingWayVal == '3'){
				//�����̽�����
				summarizingWayInpt1.value = "summarizingFirm";
				summarizingWayInpt2.value = "SettleDay";

				breed.disabled = "disabled";
				breed.style.cssText = "background-color: gray;";
			}else if(summarizingWayVal == '4'){
				//Ʒ�ֽ�����
				summarizingWayInpt1.value = "summarizingBreed";
				summarizingWayInpt2.value = "SettleDay";

				breed.removeAttribute("disabled");
				breed.removeAttribute("style");
			}
		}
		</script>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
