<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
  <head>
    <title>分类账合计</title>
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
		  var accountCode = document.getElementById("accountCode").value;
		  var startDate = document.getElementById("beginDate").value;
		  var endDate =  document.getElementById("endDate").value;
			 
			 if(accountCode == ""){
				 alert("科目代码不能为空！");
				 frm.accountCode.focus();
				 return false;
			 }

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
			  <form name="frm" action="${basePath}/finance/financialQuery/ledgerSum.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table3_style" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							  <td class="table3_td_1" align="left">
							          科目代码:&nbsp;
								<label>
								  <input type="text" class="input_text" id="accountCode" name="accountCode" onkeypress="onlyNumberAndCharInput()" />
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
											开始日期:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="beginDate"			
		
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" align="right">
											结束日期:
											<input type="text" class="wdate" id="endDate"  style="width: 106px"
												name="endDate"			
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
						      </td>		
						      
						      <td class="table3_td_anniu" align="right">
						        <button class="btn_sec" id="view" onclick="dolistquery()">查询</button>
							    &nbsp;&nbsp;
							    <button class="btn_cz" onclick="myReset();">重置</button>
						      </td>									
					        </tr>
					    </table>
					    <script type="text/javascript">
					      frm.accountCode.value = "<c:out value='${accountCode}'/>";
					      frm.beginDate.value = "<c:out value='${beginDate}'/>";
					      frm.endDate.value = "<c:out value='${endDate}'/>";
					    </script>
				      </div>
				    </td>
			      </tr>
			    </table>
		      </form>
            </div>
            
            <div align="center">
              <b>科目代码：</b><c:out value="${balance.CODE}"/>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <b>科目名称：</b><c:out value="${balance.NAME}"/>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <b>期初余额：</b>
              <label>
                <c:choose>
	  				<c:when test='${balance != null}'>
	  				    <fmt:formatNumber value="${balance.LASTDAYBALANCE}" pattern="#,##0.00"/>	
		  			</c:when>
	  				<c:otherwise>0.00</c:otherwise>
	  			</c:choose>
              </label>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <b>期末余额：</b>
              <label>
                <c:choose>
	  				<c:when test='${balance != null}'>
	  					<fmt:formatNumber value="${balance.TODAYBALANCE}" pattern="#,##0.00"/>
		  			</c:when>
	  				<c:otherwise>0.00</c:otherwise>
	  			</c:choose>
              </label>
            </div>
            
            <c:choose>
	  				<c:when test='${dataSize != null}'>
	  					<c:set var="dataNum" value="${dataSize}"/>
		  			</c:when>
	  				<c:otherwise>
	  				  <c:set var="dataNum" value="0"/>
	  				</c:otherwise>
	  	    </c:choose>
            
            <div class="div_list">
			  <table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
			    <tr>
				  <td>
				  <c:set var="bal" value="${balance.LASTDAYBALANCE}"/>
				    <ec:table items="pageInfo.result" var="ledgerSum"
							  action="${basePath}/finance/financialQuery/ledgerSum.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="ledgerSum.xls" csvFileName="ledgerSum.csv"
							  showPrint="true" listWidth="100%"
							  minHeight="345"  style="table-layout:fixed;"
							  rowsDisplayed="${dataNum}"
							  toolbarContent="refresh|export|extend|status"
							  >
					  <ec:row>
					    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="SUMMARYNO" title="凭证摘要号" width="10%" style="text-align:center;" />
						<ec:column property="SUMMARY" title="凭证摘要" width="10%" style="text-align:center;" ellipsis="true"/>			
						<ec:column property="DAMOUNT" title="借方发生额" width="10%" style="text-align:right;"  format="#,##0.00"  cell="number"  calc="total" calcTitle= "合计" />							
						<ec:column property="CAMOUNT" title="贷方发生额" width="10%" style="text-align:right;"  format="#,##0.00"  cell="number"  calc="total" calcTitle= "合计"/>
						
						
						<c:if test="${balance.DCFLAG == 'D'}">
	  				       <c:set var="bal" value="${bal + ledgerSum.DAMOUNT - ledgerSum.CAMOUNT}"></c:set>
	  			        </c:if>
	  			        <c:if test="${balance.DCFLAG == 'C'}">
	  				       <c:set var="bal" value="${bal - ledgerSum.DAMOUNT + ledgerSum.CAMOUNT}"></c:set>
	  			        </c:if>
					    <ec:column property="bal" title="余额" width="10%" style="text-align:right;" >
						  <fmt:formatNumber value="${bal}" pattern="#,##0.00"/>	
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
