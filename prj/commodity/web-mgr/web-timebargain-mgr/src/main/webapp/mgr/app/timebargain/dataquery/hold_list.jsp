<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
   pageContext.setAttribute("nowDate", nowDate);
%>
<html>
	<head>
		<title>������ϸ</title>
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
							<form name="frm" action="${basePath}/timebargain/dataquery/listHold.action" method="post">
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
														
														
													</tr>
													<tr>
													<td class="table3_td_1" align="left">
															&nbsp;&nbsp;��Ʒ���룺
															<label>
																<input type="text" class="input_text" id="commodityId"   name="${GNNT_}primary.commodityId[=]" title="������ģʽƥ�����ѯ" value="${oldParams['primary.commodityId[=]']}" />
															</label>
														</td>
							                     <td class="table3_td_1" align="left">
											                         ��������:
											         <input type="text" class="wdate" id="deadLine"  style="width: 106px" 
												      name="${GNNT_}primary.deadLine[<=][date]"			
												       value="${oldParams['primary.deadLine[<=][date]']}"					
												      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>
							                     <td  class="table3_td_1" align="left" style="width: 250px;">
													��������(С�ڵ���)��
													<label>
														<input type="text" class="input_text" id="remainDay" onkeyup="value=value.replace(/[^\d]/g,'')" name="${GNNT_}primary.remainDay[=][Long]" value="${oldParams['primary.remainDay[=][Long]']}" />
													</label>
												  </td>
												</tr>
													<tr>
													      
														<td class="table3_td_1" align="right">
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
											action="${basePath}/timebargain/dataquery/listHold.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="110%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="mFirmModel.firmId" width="13%" ellipsis="true" title="�����̴���" style="text-align:center;"/>
												<ec:column property="mFirmModel.name" title="����������" width="13%" style="text-align:left;"  ellipsis="true"/>
												<ec:column property="customerId" title="��������" width="13%" ellipsis="true" style="text-align:center;"/>
												<ec:column property="commodityId" title="��Ʒ����" width="8%" style="text-align:center;"/>
												<ec:column property="flag" title="����" width="4%" style="text-align:center;">${BS_flagMap[hold.flag]}</ec:column>
												<ec:column property="price" title="�۸�" width="8%" style="text-align:center;">
												   <fmt:formatNumber value="${hold.price }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdQty" title="��������" width="9%" style="text-align:center;">
												  ${hold.gageQty+hold.holdQty }
												</ec:column>
												<ec:column property="gageQty" title="�ֶ�����" width="8%" style="text-align:center;"/>
												<ec:column property="openQty" title="��������" width="8%" style="text-align:center;"/>
												<ec:column property="holdTime" title="��������" width="8%" style="text-align:center;">
												  <fmt:formatDate value="${hold.holdTime }" pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="atClearDate" title="��������" width="8%" style="text-align:center;">
												     <c:if test="${isHis == 'yes'}">
												      <fmt:formatDate value="${hold.atClearDate }" pattern="yyyy-MM-dd" />
												     </c:if>
												     <c:if test="${isHiss == 'no'}">
												       ${nowDate }
												     </c:if>
												     
												 </ec:column>
												<ec:column property="deadLine" title="��������" width="8%" style="text-align:center;">
												    <fmt:formatDate value="${hold.deadLine }" pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="remainDay" title="��������" width="5%" style="text-align:center;"/>
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