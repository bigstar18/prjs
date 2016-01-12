<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>商品行情</title>
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
							<form name="frm" action="${basePath}/timebargain/dataquery/listQuotation.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														
														<td class="table3_td_1" align="right">
															&nbsp;&nbsp;商品代码：
															<label>
																<input type="text" class="input_text" id="commodityId"   name="${GNNT_}primary.commodityId[=]" title="可输入模式匹配符查询" value="${oldParams['primary.commodityId[=]']}" />
															</label>
														</td>
														
													
														<td class="table3_td_1" align="center">
														   <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" <c:if test="${isQryHisHidd=='yes' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">查历史</label>
														   <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
														</td>
									              <td class="table3_td_1" align="center">
											            &nbsp;&nbsp;开始日期:
											        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
												       name="${GNNT_}primary.clearDate[>=][date]"			
												       value="${oldParams['primary.clearDate[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>

									              <td class="table3_td_1" align="center">
											           &nbsp;&nbsp;结束日期:
											         <input type="text" class="wdate" id="endDate"  style="width: 106px" 
												      name="${GNNT_}primary.clearDate[<=][date]"			
												       value="${oldParams['primary.clearDate[<=][date]']}"					
												      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>
												<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
															
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
											action="${basePath}/timebargain/dataquery/listQuotation.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												
												
												<ec:column property="commodityId" title="商品代码" width="10%" style="text-align:center;"/>
												<ec:column property="price" title="平均价" width="10%" style="text-align:center;">
												        <fmt:formatNumber value="${hold.price }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="yesterBalancePrice" title="昨日结算价" width="10%" style="text-align:center;">
												        <fmt:formatNumber value="${hold.yesterBalancePrice }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="openPrice" title="今开市价" width="10%" style="text-align:center;">
												        <fmt:formatNumber value="${hold.openPrice }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="highPrice" title="最高价" width="7%" style="text-align:center;">
												        <fmt:formatNumber value="${hold.highPrice }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="lowPrice" title="最低价" width="7%" style="text-align:center;">
												        <fmt:formatNumber value="${hold.lowPrice }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="curPrice" title="最新价" width="7%" style="text-align:center;">
												        <fmt:formatNumber value="${hold.curPrice }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="reserveCount" title="订货量" width="10%" style="text-align:center;">
												        <fmt:formatNumber value="${hold.reserveCount }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="totalAmount" title="总成交量" width="10%" style="text-align:center;">
												         <fmt:formatNumber value="${hold.totalAmount }" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="spread" title="涨跌" width="10%" style="text-align:center;">
												  <c:if test="${hold.curPrice == 0}">
												         <fmt:formatNumber value="0" pattern="#,##0.00" />
												  </c:if>
												  <c:if test="${hold.curPrice != 0}">
												   
												         <fmt:formatNumber value="${hold.spread }" pattern="#,##0.00" />
												  </c:if>
												        
												</ec:column>
												<ec:column property="createTime" title="创建时间" width="10%" style="text-align:center;">
												        <fmt:formatDate value="${hold.createTime }" pattern="yyyy-MM-dd" />
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>