<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>�ɽ����</title>
		<SCRIPT type="text/javascript">

		function window_onload(){

			if(frm.beginDate.value=="")
			  {
				frm.beginDate.value = '<%=nowDate%>';
				   
			  }
			  if(frm.endDate.value==""){
				  frm.endDate.value = '<%=nowDate%>';
				  
			  }
			isQryHis_onclick();
		}
		
		function setDisabled(obj)
		{
		  obj.disabled = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setEnabled(obj)
		{
		  obj.disabled = false;
		  obj.style.backgroundColor = "white";
		}
		function isQryHis_onclick()
		{
		  if(frm.isQryHis.checked)
		  {
			  setEnabled(frm.beginDate);
			  setEnabled(frm.endDate);
		    
		  }
		  else
		  {
			  setDisabled(frm.beginDate);
			  setDisabled(frm.endDate);
		    
		  }
		  
		}

			//ִ�в�ѯ�б�
			function dolistquery() {

		         if(frm.isQryHis.checked)
				  {
					  frm.isQryHisHidd.value = "yes";
					 
				  }else{
					  frm.isQryHisHidd.value = "no";
				  }
				  
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
		</SCRIPT>
	</head>
	<body onload="window_onload()">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/dataquery/listTrade.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															�����̴��룺
															<label>
																<input type="text" class="input_text" id="firmId"   name="${GNNT_}primary.firmId[=]" value="${oldParams['primary.firmId[=]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															���������ƣ�
															<label>
																<input type="text" class="input_text" id="firmName"   name="${GNNT_}primary.firmName[=]" title="������ģʽƥ�����ѯ" value="${oldParams['primary.firmName[=]']}" />
															</label>
														</td>
														
														<td class="table3_td_1" align="right">
															&nbsp;&nbsp;������
															<label>
																<select id="flag" name="${GNNT_}primary.flag[=][Long]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${BS_flagMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.flag.value = "<c:out value='${oldParams["primary.flag[=][Long]"] }'/>";
					  										</script>
														</td>
												
													</tr>
													<tr>
													    <td class="table3_td_1" align="right">
															&nbsp;&nbsp;�ɽ����ͣ�
															<label>
																<select id="tradeType" name="${GNNT_}primary.tradeType[=][Long]"  class="normal" style="width: 120px">
																	<option value="">ȫ������</option>
																	<c:forEach items="${tradeTypeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.tradeType.value = "<c:out value='${oldParams["primary.tradeType[=][Long]"] }'/>";
					  										</script>
														</td>
													    <td class="table3_td_1" align="right">
															&nbsp;&nbsp;��Ʒ���룺
															<label>
																<input type="text" class="input_text" id="commodityId"   name="${GNNT_}primary.commodityId[=]" title="������ģʽƥ�����ѯ" value="${oldParams['primary.commodityId[=]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															&nbsp;&nbsp;ί�����ͣ�
															<label>
																<select id="orderType" name="${GNNT_}primary.orderType[=][Long]"  class="normal" style="width: 120px">
																	<option value="">ȫ������</option>
																	<c:forEach items="${orderTypeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.orderType.value = "<c:out value='${oldParams["primary.orderType[=][Long]"] }'/>";
					  										</script>
														</td>
													</tr>
													<tr>
													      
														<td class="table3_td_1" align="right">
														   <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" <c:if test="${isQryHisHidd=='yes' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">����ʷ</label>
														   <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
														</td>
									              <td class="table3_td_1" align="right">
											            &nbsp;&nbsp;��ʼ����:
											        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
												       name="${GNNT_}primary.clearDate[>=][date]"			
												       value="${oldParams['primary.clearDate[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>

									              <td class="table3_td_1" align="right">
											           &nbsp;&nbsp;��������:
											         <input type="text" class="wdate" id="endDate"  style="width: 106px" 
												      name="${GNNT_}primary.clearDate[<=][date]"			
												       value="${oldParams['primary.clearDate[<=][date]']}"					
												      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>
												<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
															&nbsp;&nbsp;
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
										<ec:table items="pageInfo.result" var="hold"
											action="${basePath}/timebargain/dataquery/listTrade.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												
												<ec:column property="firmId" width="150" ellipsis="true" title="�����̴���"  style="text-align:center;"/>
											    <ec:column property="firmName" title="����������" width="120" style="text-align:left;"  ellipsis="true"/>
											    <ec:column property="customerId" width="150" ellipsis="true" title="��������" style="text-align:center;"/>
												<ec:column property="oppCustomerId" width="150" ellipsis="true" title="�ɽ����ִ���" style="text-align:center;"/>
												<ec:column property="tradeNo" width="100" title="�ɽ���" ellipsis="true" style="text-align:center;"/>
												<ec:column property="orderNo" width="100" title="ί�е���" ellipsis="true" style="text-align:center;"/>
												<ec:column property="tradeTime" width="150" title="�ɽ�ʱ��" style="text-align:center;">
												    <fmt:formatDate value="${hold.tradeTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="commodityId" width="100" title="��Ʒ����" style="text-align:center;"/>
												<ec:column property="flag" width="60" title="����" style="text-align:center;">${BS_flagMap[hold.flag]}</ec:column>
												<ec:column property="orderType" width="100" title="ί������" style="text-align:center;">${orderTypeMap[hold.orderType]}</ec:column>
												<ec:column property="price" width="100" title="�ɽ���" style="text-align:center;" >
												     <fmt:formatNumber value="${hold.price }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="quantity" title="�ɽ�����" calc="total" calcTitle= "�ϼ�" width="100" style="text-align:right;" />
												<ec:column property="tradeType" width="100" title="�ɽ�����" style="text-align:center;">${tradeTypeMap[hold.tradeType]}</ec:column>
												<ec:column property="close" title="ת��ӯ��" calc="total"  width="100" style="text-align:right;" format="#,##0.00">
												       <fmt:formatNumber value="${hold.close }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradeFee" width="100" title="������" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.tradeFee }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closeAddedTax" width="100" title="ת����ֵ˰" calc="total" style="text-align:right;" format="#,##0.00">
												        <fmt:formatNumber value="${hold.closeAddedTax }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdPrice" width="100" title="�����۸�" style="text-align:center;">
												        <fmt:formatNumber value="${hold.holdPrice }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdTime" width="150" title="����ʱ��" style="text-align:center;">
												    <fmt:formatDate value="${hold.holdTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>