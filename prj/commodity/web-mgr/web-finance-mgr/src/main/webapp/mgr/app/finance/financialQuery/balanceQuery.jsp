<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
  <head>
    <title>�����ѯ</title>
    
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
				if ( startDate > '<%=nowDate%>' ) { 
				        alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
				        frm.beginDate.focus();
				        return false; 
				} 
				if ( startDate > endDate ) { 
			        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
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
							         ��Ŀ����:&nbsp;
								<label>
								  <input type="text" class="input_text" id="accountCode" name="${GNNT_}primary.code[like]" value="${oldParams['primary.code[like]']}" onkeypress="onlyNumberAndCharInput()" />
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							          ��Ŀ����:&nbsp;
								<label>
									<select id="level" name="${GNNT_}account.accountLevel[=]"  class="normal" style="width: 120px">
									  <option value="">ȫ��</option>
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
											��ʼ����:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="${GNNT_}primary.bdate[>=][date]"			
												value="${oldParams['primary.bdate[>=][date]']}"					
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" align="right">
											��������:
											<input type="text" class="wdate" id="endDate"  style="width: 106px"
												name="${GNNT_}primary.bdate[<=][date]"			
												value="${oldParams['primary.bdate[<=][date]']}"					
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
						      </td>	
						  
						    <td class="table3_td_anniu" align="right">
						      <button class="btn_sec" id="view" onclick="dolistquery()">��ѯ</button>
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
				    <ec:table items="pageInfo.result" var="dailyBalance"
							  action="${basePath}/finance/financialQuery/balanceQuery.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="dailyBalance.xls" csvFileName="dailyBalance.csv"
							  showPrint="true" listWidth="120%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    <ec:column property="bdate" title="��������" width="8%" style="text-align:center;">
					      <fmt:formatDate value="${dailyBalance.bdate }" pattern="yyyy-MM-dd" />
					    </ec:column>
						<ec:column property="code" title="��Ŀ����" width="10%" style="text-align:center;"  ellipsis="true"/>
					    <ec:column property="account.name" title="��Ŀ����" width="10%" style="text-align:center;" ellipsis="true"/>
						<ec:column property="account.accountLevel" title="��Ŀ����" width="5%" style="text-align:center;"/>
						
						<ec:column property="lastDayBalance" title="�ڳ����" width="15%" style="text-align:right;">
						  <fmt:formatNumber value="${dailyBalance.lastDayBalance }" pattern="#,##0.00" />
						</ec:column>
						<ec:column property="debitAmount" title="�跽������" width="15%" style="text-align:right;" >
						  <fmt:formatNumber value="${dailyBalance.debitAmount }" pattern="#,##0.00" />
						</ec:column>
						<ec:column property="creditAmount" title="����������" width="15%" style="text-align:right;" >		  
						  <fmt:formatNumber value="${dailyBalance.creditAmount }" pattern="#,##0.00" />
						</ec:column>
						<ec:column property="todayBalance" title="��ĩ���" width="15%" style="text-align:right;">
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
