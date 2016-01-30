<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
   pageContext.setAttribute("nowDate", nowDate);
%>
<html>
	<head>
		<title>订货明细</title>
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

			//执行查询列表
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
							alert("开始日期不能为空！");
							frm.beginDate.focus();
							return false;
							
						}
						if(endDate == ""){
							alert("结束日期不能为空！");
							frm.endDate.focus();
							return false;
							
						}
					if ( startDate > endDate ) { 
				        alert("开始日期不能大于结束日期!"); 
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
															交易商代码：
															<label>
																<input type="text" class="input_text" id="mFirmModel.firmId"   name="${GNNT_}primary.mFirmModel.firmId[=]" value="${oldParams['primary.mFirmModel.firmId[=]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															交易商名称：
															<label>
																<input type="text" class="input_text" id="mFirmModel.name"   name="${GNNT_}primary.mFirmModel.name[=]" title="可输入模式匹配符查询" value="${oldParams['primary.mFirmModel.name[=]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;买卖：
															<label>
																<select id="flag" name="${GNNT_}primary.flag[=][Long]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
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
															&nbsp;&nbsp;商品代码：
															<label>
																<input type="text" class="input_text" id="commodityId"   name="${GNNT_}primary.commodityId[=]" title="可输入模式匹配符查询" value="${oldParams['primary.commodityId[=]']}" />
															</label>
														</td>
							                     <td class="table3_td_1" align="left">
											                         到期日期:
											         <input type="text" class="wdate" id="deadLine"  style="width: 106px" 
												      name="${GNNT_}primary.deadLine[<=][date]"			
												       value="${oldParams['primary.deadLine[<=][date]']}"					
												      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>
							                     <td  class="table3_td_1" align="left" style="width: 250px;">
													到期天数(小于等于)：
													<label>
														<input type="text" class="input_text" id="remainDay" onkeyup="value=value.replace(/[^\d]/g,'')" name="${GNNT_}primary.remainDay[=][Long]" value="${oldParams['primary.remainDay[=][Long]']}" />
													</label>
												  </td>
												</tr>
													<tr>
													      
														<td class="table3_td_1" align="right">
														   <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" <c:if test="${isQryHisHidd=='yes' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">查历史</label>
														   <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
														</td>
									              <td class="table3_td_1" align="left">
											            &nbsp;&nbsp;开始日期:
											        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
												       name="${GNNT_}primary.clearDate[>=][date]"			
												       value="${oldParams['primary.clearDate[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>

									              <td class="table3_td_1" align="left">
											           &nbsp;&nbsp;结束日期:
											         <input type="text" class="wdate" id="endDate"  style="width: 106px" 
												      name="${GNNT_}primary.clearDate[<=][date]"			
												       value="${oldParams['primary.clearDate[<=][date]']}"					
												      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>
							                     <td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
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
												<ec:column property="mFirmModel.firmId" width="13%" ellipsis="true" title="交易商代码" style="text-align:center;"/>
												<ec:column property="mFirmModel.name" title="交易商名称" width="13%" style="text-align:left;"  ellipsis="true"/>
												<ec:column property="customerId" title="二级代码" width="13%" ellipsis="true" style="text-align:center;"/>
												<ec:column property="commodityId" title="商品代码" width="8%" style="text-align:center;"/>
												<ec:column property="flag" title="买卖" width="4%" style="text-align:center;">${BS_flagMap[hold.flag]}</ec:column>
												<ec:column property="price" title="价格" width="8%" style="text-align:center;">
												   <fmt:formatNumber value="${hold.price }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdQty" title="订货数量" width="9%" style="text-align:center;">
												  ${hold.gageQty+hold.holdQty }
												</ec:column>
												<ec:column property="gageQty" title="抵顶数量" width="8%" style="text-align:center;"/>
												<ec:column property="openQty" title="订立数量" width="8%" style="text-align:center;"/>
												<ec:column property="holdTime" title="订立日期" width="8%" style="text-align:center;">
												  <fmt:formatDate value="${hold.holdTime }" pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="atClearDate" title="结算日期" width="8%" style="text-align:center;">
												     <c:if test="${isHis == 'yes'}">
												      <fmt:formatDate value="${hold.atClearDate }" pattern="yyyy-MM-dd" />
												     </c:if>
												     <c:if test="${isHiss == 'no'}">
												       ${nowDate }
												     </c:if>
												     
												 </ec:column>
												<ec:column property="deadLine" title="到期日期" width="8%" style="text-align:center;">
												    <fmt:formatDate value="${hold.deadLine }" pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="remainDay" title="到期天数" width="5%" style="text-align:center;"/>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>