<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<title>分品种手续费汇总查询</title>
<script type="text/javascript">
	
	//执行查询列表
	function dolistquery() {
	//验证信息
	var beginBreed = document.forms[0].beginBreed.value;
	var endBreed = document.forms[0].endBreed.value;
	var beginDate = document.forms[0].beginDate.value;
	var endDate = document.forms[0].endDate.value;
	if(beginBreed == "" && endBreed != ""){
		alert("起始品种不能为空！！");
		return false;
	}
	if(beginBreed != "" && endBreed == ""){
		alert("结束品种不能为空！！");
		return false;
	}
	if(beginDate == "" && endDate != ""){
		alert("起始日期不能为空！！");
		return false;
	}
	if(beginDate != "" && endDate == ""){
		alert("结束日期不能为空！！");
		return false;
	}
	if(beginDate > endDate){
		alert("起始日期不能大于结束日期！！！");
		return false;
	}
		frm.submit();
	}

</script>
</head>
<body>
	<div id="main_body">
	  <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  <div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/brokerReward/listBreedTradeFee.action">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
											<tr>
								            
											<td class="table3_td_1" align="right">
												起始品种：
											   <SELECT  name="${GNNT_}primary.beginBreed[=][Long]" id="beginBreed" class="input_text">
			            						<OPTION value="">全部</OPTION>
									            <c:forEach items="${breedAscList}" var="result">
										        <option value="${result.BREEDID}">${result.BREEDID}</option>
											    </c:forEach>
		          							  </SELECT>
												 <script >frm.beginBreed.value = "<c:out value='${oldParams["primary.beginBreed[=][Long]"] }'/>";</script>
											</td>
											
											<td  class="table3_td_1" align="right">
												 结束品种：
											  <SELECT name="${GNNT_}primary.endBreed[=][Long]" id="endBreed" class="input_text">
			            						<OPTION value="">全部</OPTION>
									            <c:forEach items="${breedDescList}" var="result">
										        <option value="${result.BREEDID}">${result.BREEDID}</option>
											    </c:forEach>
		          							  </SELECT>
											 <script >frm.endBreed.value = "<c:out value='${oldParams["primary.endBreed[=][Long]"] }'/>";</script>
											</td>														
								    </tr>
								  <tr>																				            
									<td class="table3_td_1" align="right">
								            &nbsp;&nbsp;开始日期:
								        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
									       name="${GNNT_}primary.occurDate[>=][date]"			
									       value="${oldParams['primary.occurDate[>=][date]']}"					
									       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
				                     </td>

						              <td class="table3_td_1" align="right">
								           &nbsp;&nbsp;结束日期:
								         <input type="text" class="wdate" id="endDate"  style="width: 106px" 
									      name="${GNNT_}primary.occurDate[<=][date]"			
									       value="${oldParams['primary.occurDate[<=][date]']}"					
									      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
				                     </td>		
									<td class="table3_td_anniu" align="center">
									        &nbsp;&nbsp;
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
						<ec:table items="resultList" var="result" 
			action="${basePath}/timebargain/brokerReward/listBreedTradeFee.action"	
			xlsFileName="tradeFeeBreedList.xls" 
			csvFileName="tradeFeeBreedList.csv"
			showPrint="true" 			
			rowsDisplayed="20"
			listWidth="100%"	
			minHeight="300"  
			filterable="false"
            
	>
		<ec:row>	
	
			<ec:column property="BREEDNAME" title="品种名称" width="8%" calcTitle= "合计" calc="total" style="text-align:center;">
			</ec:column>		
			<ec:column property="BREEDID" title="品种代码"  width="8%"  style="text-align:center;">		
			</ec:column>		
			<ec:column property="SUMTRADEFEE" title="手续费" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMSELFGAIN" title="加盟商手续费" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMMARHETGAIN" title="交易所手续费" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMBROKEREACHDIVIDE" title="加盟商手续费分成" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMFINALMARHETGAIN" title="交易所实得手续费" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMREWARD" title="加盟商实得手续费" cell="currency" calc="total" format="###,##0.00" width="8%" style="text-align:right;"/>
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
</body>
<script type="text/javascript">

</script>
</html>
