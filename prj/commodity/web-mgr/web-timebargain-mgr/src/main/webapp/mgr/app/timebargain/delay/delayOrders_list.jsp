<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
   pageContext.setAttribute("nowDate", nowDate);
%>
<html>
	<head>
	    <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<title>
default
</title>
		<script type="text/javascript">

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
		
		function dolistquery() {
			
			  if(frm.isQryHis.checked)
			  {
				  frm.isQryHisHidd.value = "yes";
				 
			  }else{
				  frm.isQryHisHidd.value = "no";
			  }
			//if(jQuery("#frm").validationEngine('validateform')){
		
		//检查起始日期 
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
			//}
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
		</script>
</head>
<body onload="window_onload()" leftmargin="2" topmargin="0">
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/timebargain/delay/delayOrdersList.action?sortColumns=order+by+firmID+asc" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px">
											<tr>
												<td align="right">
														交易商代码：
												</td>
												<td>
													<input type="text" id="firmID" name="${GNNT_}primary.firmID[=]" style="width:111;ime-mode:disabled" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" title=""
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.firmID[=]']}"/>
												</td>
						                        <td align="right">
														商品代码：
												</td>
												<td>
													<input type="text" id="commodityID" name="${GNNT_}primary.commodityID[=]"  style="width:111;ime-mode:disabled" maxlength="24"
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.commodityID[=]']}"/>
												</td>
						                        <td align="right"><label>买卖：</label></td>
						                        <td>
							                        <select id="flag" name="${GNNT_}primary.flag[=][int]" style="width:111">
							                          <option value="">全部</option>
							                          <option value="1" <c:if test="${oldParams['primary.flag[=][int]'] == '1'}">selected</c:if>>买</option>
								                      <option value="2" <c:if test="${oldParams['primary.flag[=][int]'] == '2'}">selected</c:if>>卖</option>
						                            </select>
						                        </td>		
						                        <td align="right">状态：</td>
			                                    <td>
			                                        <select id="status" name="${GNNT_}primary.status[=][int]" style="width:111">
							                            <option value="">全部状态</option>
								                        <option value="1" <c:if test="${oldParams['primary.status[=][int]'] == '1'}">selected</c:if>>已委托</option>
								                        <option value="2" <c:if test="${oldParams['primary.status[=][int]'] == '2'}">selected</c:if>>部分成交</option>
								                        <option value="3" <c:if test="${oldParams['primary.status[=][int]'] == '3'}">selected</c:if>>全部成交</option>
								                        <option value="5" <c:if test="${oldParams['primary.status[=][int]'] == '5'}">selected</c:if>>全部撤单</option>
								                        <option value="6" <c:if test="${oldParams['primary.status[=][int]'] == '6'}">selected</c:if>>部分成交后撤单</option>
						                            </select>			                            
			                                    </td>																			
											</tr>
											
											<tr>
												<td>&nbsp;</td>
												<td align="center">
													    <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" <c:if test="${isQryHisHidd == 'yes' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">查历史</label>
													    <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
												</td>
												<td align="right">
													开始日期：
												</td>
												<td><!--  ondblclick="if(!this.readOnly){setRq(this);}" -->
												<input type="text" style="width: 80px" id="beginDate"
													class="validate[required] wdate" maxlength="10"
													name="${GNNT_}primary.clearDate[>=][date]" 
													value="${oldParams['primary.clearDate[>=][date]']}"
													onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="right">
													结束日期：
												</td>
												<td>
												<input type="text" style="width: 80px" id="endDate"
													class="validate[required] wdate" maxlength="10"
													name="${GNNT_}primary.clearDate[<=][date]" 
													value="${oldParams['primary.clearDate[<=][date]']}"
													onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td></td>

												<td class="table3_td_1" align="left">
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
				<br />
			
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="order"
									action="${basePath}/timebargain/delay/delayOrdersList.action?sortColumns=order+by+firmID+asc"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
									    <ec:column property="firmID" title="交易商代码" width="170" ellipsis="true" style="text-align:center;"/>
							            <ec:column property="customerID" title="二级代码" width="170" ellipsis="true" style="text-align:center;"/>
							            <ec:column property="orderNo" title="委托单号" width="70" style="text-align:center;"/>
							       
										<ec:column property="commodityID" title="商品代码" width="95" style="text-align:center;"/>
										<ec:column property="flag" title="买卖" width="40" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>
										<ec:column property="status" title="状态" width="100" editTemplate="ecs_t_status2" mappingItem="ORDER_STATUS" style="text-align:center;"/>
										<ec:column property="delayOrderType" title="委托类型" width="100"  mappingItem="DELAYORDERTYPE_STATUS" style="text-align:center;"/>
										<ec:column property="withdrawType" title="撤单类型" width="120"  mappingItem="WITHDRAWTYPE" style="text-align:center;"/>
										<ec:column property="quantity" title="委托数量" cell="number" calc="total" calcTitle= "合计"  width="65" style="text-align:right;"/>
										<ec:column property="price" title="委托价格" cell="currency" width="120" style="text-align:right;"/>
										<ec:column property="tradeQty" title="已成交数量" cell="number" calc="total" width="90" style="text-align:right;"/>
										<ec:column property="orderTime" title="委托时间" width="150" style="text-align:center;">
										    <fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" />
										</ec:column>
										<ec:column property="withdrawTime" title="撤单时间" width="150" style="text-align:center;">
										  <fmt:formatDate value="${order.withdrawTime}" pattern="yyyy-MM-dd HH:mm:ss" />
										</ec:column>			
										<ec:column property="traderID" title="交易员代码" width="170" ellipsis="true" style="text-align:center;"/>
										<ec:column property="clearDate" title="结算日期" width="100" style="text-align:center;">
												     <c:if test="${isHis == 'yes'}">
												      <fmt:formatDate value="${order.clearDate }" pattern="yyyy-MM-dd" />
												     </c:if>
												     <c:if test="${isHiss == 'no'}">
												       ${nowDate }
												     </c:if>	     
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
</div>
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status1" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="ORDERTYPE" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="ORDER_STATUS" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status3" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CLOSEMODE" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status4" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TIMEFLAG" />
		</select>
	</textarea>	
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status5" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="CLOSEFlAG2" />
		</select>
	</textarea>	
</body>

</html>
