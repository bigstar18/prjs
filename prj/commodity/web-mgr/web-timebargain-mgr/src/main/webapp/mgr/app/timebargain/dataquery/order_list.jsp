<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>ί�����</title>
		<SCRIPT type="text/javascript">

		function window_onload(){

			
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
			  if(frm.beginDate.value=="")
			  {
				frm.beginDate.value = '<%=nowDate%>';
				   
			  }
			  if(frm.endDate.value==""){
				  frm.endDate.value = '<%=nowDate%>';
				  
			  }
			  setEnabled(frm.beginDate);
			  setEnabled(frm.endDate);
		    
		  }
		  else
		  {
			  frm.beginDate.value = "";
			  frm.endDate.value = "";
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
				  
		         if(frm.isConsigner.checked)
				  {
					  frm.isConsignerHidd.value = "yes";
					 
				  }else{
					  frm.isConsignerHidd.value = "no";
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
							<form name="frm" action="${basePath}/timebargain/dataquery/listOrder.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															�����̴��룺
															<label>
																<input type="text" class="input_text" id="mFirmModel.firmId"   name="${GNNT_}primary.mFirmModel.firmId[=]" value="${oldParams['primary.mFirmModel.firmId[=]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															���������ƣ�
															<label>
																<input type="text" class="input_text" id="mFirmModel.name"   name="${GNNT_}primary.mFirmModel.name[=]" title="������ģʽƥ�����ѯ" value="${oldParams['primary.mFirmModel.name[=]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
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
														<td class="table3_td_1" align="left">
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
													     <td class="table3_td_1" align="left">
															&nbsp;&nbsp;ת�����ͣ�
															<label>
																<select id="closeFlag" name="${GNNT_}primary.closeFlag[=][Long]"  class="normal" style="width: 126px">
																	<option value="">ȫ������</option>
																	<c:forEach items="${closeFlagMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.closeFlag.value = "<c:out value='${oldParams["primary.closeFlag[=][Long]"] }'/>";
					  										</script>
														 </td>
													     <td class="table3_td_1" align="left">
															&nbsp;&nbsp;��Ʒ���룺
															<label>
																<input type="text" class="input_text" id="commodityId"   name="${GNNT_}primary.commodityId[=]" title="������ģʽƥ�����ѯ" value="${oldParams['primary.commodityId[=]']}" />
															</label>
														 </td>
													     <td class="table3_td_1" align="left">
															&nbsp;&nbsp;״̬��
															<label>
																<select id="status1" name="${GNNT_}primary.status1[=][Long]"  class="normal" style="width: 120px">
																	<option value="">ȫ��״̬</option>
																	<c:forEach items="${statusMap1}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.status1.value = "<c:out value='${oldParams["primary.status1[=][Long]"] }'/>";
					  										</script>
														 </td>
														 <td class="table3_td_1" align="left">
															&nbsp;&nbsp;�������ͣ�
															<label>
																<select id="withdrawType" name="${GNNT_}primary.withdrawType[=][Long]"  class="normal" style="width: 120px">
																	<option value="">ȫ������</option>
																	<c:forEach items="${withdrawTypeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.withdrawType.value = "<c:out value='${oldParams["primary.withdrawType[=][Long]"] }'/>";
					  										</script>
														 </td>
													</tr>
													<tr>
													      <td class="table3_td_1" align="left">
															�ֵ��������ͣ�
															<label>
																<select id="billTradeType" name="${GNNT_}primary.billTradeType[=][Long]"  class="normal" style="width: 90px">
																	<option value="">ȫ������</option>
																	<c:forEach items="${billTradeTypeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.billTradeType.value = "<c:out value='${oldParams["primary.billTradeType[=][Long]"] }'/>";
					  										</script>
														 </td>
													
														<td class="table3_td_1" align="right">
														<input type="checkbox" name="isConsigner" id="isConsigner" <c:if test="${isConsignerHidd=='yes' }">checked</c:if>  class="NormalInput"/><label for="isConsigner" class="hand">��Ϊί��</label>
														   <input type="hidden" id="isConsignerHidd" name="isConsignerHidd" >
														   <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" <c:if test="${isQryHisHidd=='yes' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">����ʷ</label>
														   <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
														</td>
									              <td class="table3_td_1" align="left">
											            &nbsp;&nbsp;��ʼ����:
											        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
												       name="${GNNT_}primary.clearDate[>=][date]"			
												       value="${oldParams['primary.clearDate[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>

									              <td class="table3_td_1" align="left">
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
											action="${basePath}/timebargain/dataquery/listOrder.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="mFirmModel.firmId" width="170" title="�����̴���" style="text-align:center;" ellipsis="true"/>
												<ec:column property="mFirmModel.name" title="����������" width="170" style="text-align:left;"  ellipsis="true"/>
												<ec:column property="customerId" title="��������" width="170" style="text-align:center; " ellipsis="true"/>
												<ec:column property="orderNo" title="ί�е���" width="170" style="text-align:center;" ellipsis="true"/>
												<ec:column property="commodityId" title="��Ʒ����" width="90" style="text-align:center;"/>
												<ec:column property="flag" title="����" width="50" style="text-align:center;">${BS_flagMap[hold.flag]}</ec:column>
												<ec:column property="orderType" title="ί������" width="80" style="text-align:center;">${orderTypeMap[hold.orderType]}</ec:column>
												<ec:column property="status1" title="״̬" width="90" style="text-align:center;">${statusMap1[hold.status1]}</ec:column>
												<ec:column property="quantity" title="ί������" calc="total" calcTitle= "�ϼ�" width="80" style="text-align:right;"/>
												<ec:column property="price" title="ί�м۸�" width="100" style="text-align:center;">
												   <fmt:formatNumber value="${hold.price }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradeQty" title="�ѳɽ�����" width="90" calc="total" style="text-align:right;"/>
												<ec:column property="orderTime" title="ί��ʱ��" width="200" style="text-align:center;">
												      <fmt:formatDate value="${hold.orderTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="withdrawTime" title="����ʱ��" width="200" style="text-align:center;">
												      <fmt:formatDate value="${hold.withdrawTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="traderId" title="����Ա����"  width="170" style="text-align:center; " ellipsis="true"/>
												<ec:column property="closeMode" title="ת�÷�ʽ" width="80" style="text-align:center;">${closeModeMap[hold.closeMode]}</ec:column>
												<ec:column property="billTradeType" title="�ֵ���������" width="80" style="text-align:center;">${billTradeTypeMap[hold.billTradeType]}</ec:column>
												<ec:column property="specPrice" title="ָ���۸�" width="80" style="text-align:center;"/>
												<ec:column property="timeFlag" title="ָ��ʱ���־" width="100" style="text-align:center;">${timeFlagMap[hold.timeFlag]}</ec:column>
												<ec:column property="closeFlag" title="ת�ñ�־" width="100" style="text-align:center;">${closeFlagMap[hold.closeFlag]}</ec:column>
												<ec:column property="consignerId" title="��Ϊί��Ա" width="100" style="text-align:center;"/>
												<ec:column property="withdrawerId" title="����Ա����" width="100" style="text-align:center;"/>
												<ec:column property="frozenFunds" title="�����ʽ�" width="100" style="text-align:center;">
												   <fmt:formatNumber value="${hold.frozenFunds }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="unfrozenFunds" title="�ⶳ�ʽ�" width="100" style="text-align:center;">
												    <fmt:formatNumber value="${hold.unfrozenFunds }" pattern="#,##0.00" />
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