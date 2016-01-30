<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
  SimpleDateFormat tempDate = new SimpleDateFormat("yyyy_MM_dd-HH_mm");
  String datetime = tempDate.format(new Date());
  request.setAttribute("datetime",datetime);
%>
<html>
<head>


<title>����Ӷ���б�</title>
<script type="text/javascript">

	
	//ִ�в�ѯ�б�
	function dolistquery() {
		
		 var startDate = document.getElementById("beginDate").value;
		 var endDate =  document.getElementById("endDate").value;	

		  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		  {
			  if(startDate == ""){
					alert("��ʼ���ڲ���Ϊ�գ�");
					frm.beginDate.focus();
					return false;
					
				}
				if(endDate == ""){
					alert("�������ڲ���Ϊ�գ�");
					frm.endDate.focus();
					return false;
					
				}
			if ( startDate > endDate ) { 
		        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
		        return false; 
		    } 
		  }
		  
		frm.submit();
	}

	//�޸���Ϣ��ת
	function detailForward(brokerId,occurDate,moduleId){
	
		//��ȡ����Ȩ�޵� URL
		var detailUrl = "${basePath}/broker/brokerDataquery/updateBrokerRewardforward.action";
		//��ȡ������תURL
		var url = detailUrl + "?entity.brokerId=" + brokerId  + "&entity.occurDate=" + occurDate + "&entity.moduleId=" + moduleId;
		//�����޸�ҳ��
		if(showDialog(url, "", 450, 300)){
			//����޸ĳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		};
	}

// -->
</script>
</head>
<body>
	<div id="main_body">
	      <div class="div_cx">
					<form name="frm" action="${basePath}/broker/brokerDataquery/listBrokerReward.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
											<tr>
											   <td class="table3_td_1" align="left">
													    �����̱�ţ�
													<label>
														<input type="text" class="input_text" id="brokerId"   name="${GNNT_}primary.brokerId[like]" value="${oldParams['primary.brokerId[like]']}" />
													</label>
												</td>
												<td class="table3_td_1" align="left">
															&nbsp;&nbsp;ģ�飺
															<label>
																<select id="moduleId" name="${GNNT_ }primary.moduleId[=][int]"  class="normal" style="width: 120px">
																<%--
																	<option value="">ȫ��</option>
																	<option value="15" <c:if test="${entity.moduleId == 15}">selected</c:if>>${request.shortName }</option>
																--%>		
																<option value="">ȫ��</option>
																 <c:forEach items="${brTradeModule }" var="d">
																		<option value="${d.moduleId }">${d.cnName }</option>
																</c:forEach>								
																</select>
															</label>
															 <script >
																frm.moduleId.value = "<c:out value='${oldParams["primary.moduleId[=][int]"] }'/>";
					  										</script>
												</td>
												<td class="table3_td_1" align="left">
											            &nbsp;&nbsp;��ʼ����:
											        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
												       name="${GNNT_}primary.occurDate[>=][date]"			
												       value="${oldParams['primary.occurDate[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>

									              <td class="table3_td_1" align="left">
											           &nbsp;&nbsp;��������:
											         <input type="text" class="wdate" id="endDate"  style="width: 106px" 
												      name="${GNNT_}primary.occurDate[<=][date]"			
												       value="${oldParams['primary.occurDate[<=][date]']}"					
												      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>		
														
												<td class="table3_td_anniu" align="left">
													<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
													<button class="btn_cz" onclick="myReset();">����</button>
												</td>
												
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</form>
			</div>
	    
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="broker"
							action="${basePath}/broker/brokerDataquery/listBrokerReward.action"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="����Ӷ��${datetime}.xls" csvFileName="export.csv"
							showPrint="true" listWidth="100%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
							
		
								<ec:column property="brokerId" width="5%" title="�����̱��" style="text-align:center;"/>
								<ec:column property="moduleId" title="ģ��" width="10%" style="text-align:center;">
								 	<c:forEach items="${brTradeModule }" var="tm">
											<c:if test="${tm.moduleId == broker.moduleId}">${tm.cnName }</c:if>
									</c:forEach>
								 
								</ec:column>
								<ec:column property="occurDate" title="������" width="10%" style="text-align:center;">
								     <fmt:formatDate value="${broker.occurDate }" pattern="yyyy-MM-dd" />
								</ec:column>
								<ec:column property="paidAmount" title="�Ѹ������" width="8%" style="text-align:right;" calc="total" calcTitle= "�ϼ�" format="#,##0.00">
								    <fmt:formatNumber value="${broker.paidAmount }" pattern="#,##0.00"/>
								</ec:column>
								<ec:column property="payDate" title="������" width="10%" style="text-align:center;">
								     <fmt:formatDate value="${broker.payDate }" pattern="yyyy-MM-dd" />
								</ec:column>
								<ec:column property="amount" title="���������" width="8%" style="text-align:right;" calc="total" format="#,##0.00">
								    <fmt:formatNumber value="${broker.amount }" pattern="#,##0.00" />
								</ec:column>
								<ec:column property="_1" title="֧�������" width="10%" sortable="false" filterable="false" style="text-align:center;" >
                                   <a href="#" onclick="detailForward('${broker.brokerId}','${broker.occurDate}','${broker.moduleId}')"><img src="<c:url value="${skinPath}/image/app/broker/edit.gif"/>"/></a> 
                                </ec:column>	
					
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
