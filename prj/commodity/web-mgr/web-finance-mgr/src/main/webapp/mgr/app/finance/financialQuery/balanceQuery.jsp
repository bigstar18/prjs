<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
  <head>
    <title>结算查询</title>
    
    <script type="text/javascript" src="${mgrPath}/app/finance/voucher/voucher.js"></script>
    <script type="text/javascript">
      function init(){
		if(frm.beginDate.value == null || frm.beginDate.value == ""){
			frm.beginDate.value = '<%=nowDate%>';
		}
		if(frm.endDate.value == null || frm.endDate.value == ""){
			frm.endDate.value = '<%=nowDate%>';
		}
		
	  } 
    
      //执行查询列表
	  function dolistquery() {
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
				if ( startDate > '<%=nowDate%>' ) { 
				        alert("开始日期不能大于当天日期!"); 
				        frm.beginDate.focus();
				        return false; 
				} 
				if ( startDate > endDate ) { 
			        alert("开始日期不能大于结束日期!"); 
			        return false; 
			    } 
			  }
		frm.submit();
	  }
    </script>
  </head>
  
  <body onload="init()">
    <div id="main_body">
      <table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td>
		  
            <div class="div_cx">
			  <form name="frm" action="${basePath}/finance/financialQuery/balanceQuery.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							 <td class="table3_td_1" align="right">
							         科目代码:&nbsp;
								<label>
								  <input type="text" class="input_text" id="accountCode" name="${GNNT_}primary.code[like]" value="${oldParams['primary.code[like]']}" onkeypress="onlyNumberAndCharInput()" />
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							          科目级别:&nbsp;
								<label>
									<select id="level" name="${GNNT_}account.accountLevel[=]"  class="normal" style="width: 120px">
									  <option value="">全部</option>
									  <option value="1">1</option>
									  <option value="2">2</option>
									  <option value="3">3</option>
									</select>
								</label>
							    <script type="text/javascript">
								  frm.level.value = "<c:out value='${oldParams["account.accountLevel[=]"] }'/>";
					  			</script>
							  </td>
							  <td class="table3_td_1" align="right">
											开始日期:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="${GNNT_}primary.bdate[>=][date]"			
												value="${oldParams['primary.bdate[>=][date]']}"					
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" align="right">
											结束日期:
											<input type="text" class="wdate" id="endDate"  style="width: 106px"
												name="${GNNT_}primary.bdate[<=][date]"			
												value="${oldParams['primary.bdate[<=][date]']}"					
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
						      </td>	
						  
						    <td class="table3_td_anniu" align="right">
						      <button class="btn_sec" id="view" onclick="dolistquery()">查询</button>
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
				    <ec:table items="pageInfo.result" var="dailyBalance"
							  action="${basePath}/finance/financialQuery/balanceQuery.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="dailyBalance.xls" csvFileName="dailyBalance.csv"
							  showPrint="true" listWidth="120%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    <ec:column property="bdate" title="结算日期" width="8%" style="text-align:center;">
					      <fmt:formatDate value="${dailyBalance.bdate }" pattern="yyyy-MM-dd" />
					    </ec:column>
						<ec:column property="code" title="科目代码" width="10%" style="text-align:center;"  ellipsis="true"/>
					    <ec:column property="account.name" title="科目名称" width="10%" style="text-align:center;" ellipsis="true"/>
						<ec:column property="account.accountLevel" title="科目级别" width="5%" style="text-align:center;"/>
						
						<ec:column property="lastDayBalance" title="期初余额" width="15%" style="text-align:right;">
						  <fmt:formatNumber value="${dailyBalance.lastDayBalance }" pattern="#,##0.00" />
						</ec:column>
						<ec:column property="debitAmount" title="借方发生额" width="15%" style="text-align:right;" >
						  <fmt:formatNumber value="${dailyBalance.debitAmount }" pattern="#,##0.00" />
						</ec:column>
						<ec:column property="creditAmount" title="贷方发生额" width="15%" style="text-align:right;" >		  
						  <fmt:formatNumber value="${dailyBalance.creditAmount }" pattern="#,##0.00" />
						</ec:column>
						<ec:column property="todayBalance" title="期末余额" width="15%" style="text-align:right;">
						  <fmt:formatNumber value="${dailyBalance.todayBalance }" pattern="#,##0.00" />
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
  </body>
</html>
