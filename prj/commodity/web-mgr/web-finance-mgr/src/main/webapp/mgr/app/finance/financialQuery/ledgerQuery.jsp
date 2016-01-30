<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
  <head>
    <title>�˲���ѯ</title>
    
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
			  <form name="frm" action="${basePath}/finance/financialQuery/ledgerQuery.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table4_style" border="0" cellspacing="0" cellpadding="0">
					        <tr>
					          <td class="table3_td_1" align="right">
							         �跽��Ŀ:&nbsp;
								<label>
								  <input type="text" class="input_text" id="debitCode"  name="${GNNT_}primary.debitNo[like]" value="${oldParams['primary.debitNo[like]']}" onkeypress="onlyNumberAndCharInput()"/>
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							          ������Ŀ:&nbsp;
								<label>
								  <input type="text" class="input_text" id="creditCode" name="${GNNT_}primary.creditNo[like]" value="${oldParams['primary.creditNo[like]']}" onkeypress="onlyNumberAndCharInput()"/>
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							         ��ͬ��:&nbsp;
								<label>
								  <input type="text" class="input_text" id="contractNo"  name="${GNNT_}primary.contractNo[like]" value="${oldParams['primary.contractNo[like]']}" />
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							          ƾ֤��:&nbsp;
								<label>
								  <input type="text" class="input_text" id="voucherNo" name="${GNNT_}primary.voucherNo[=][int]" value="${oldParams['primary.voucherNo[=][int]']}" onkeypress="onlyNumberAndCharInput()"/>
							    </label>
							  </td>
					        </tr>
						    <tr>
							 <td class="table3_td_1" align="right">
							          ƾ֤ժҪ��:&nbsp;
								<label>
								  <input type="text" class="input_text" id="summaryNo" name="${GNNT_}primary.summaryNo[like]" value="${oldParams['primary.summaryNo[like]']}" onkeypress="onlyNumberAndCharInput()"/>
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
							          ƾ֤ժҪ:&nbsp;
								<label>
								  <input type="text" class="input_text" id="summary" name="${GNNT_}primary.summary[like]" value="${oldParams['primary.summary[like]']}" onkeypress="onlyNumberAndCharInput()"/>
							    </label>
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
				    <ec:table items="pageInfo.result" var="ledgerQuery"
							  action="${basePath}/finance/financialQuery/ledgerQuery.action"											
							  autoIncludeParameters="${empty param.autoInc}"
							  xlsFileName="ledgerQuery.xls" csvFileName="ledgerQuery.csv"
							  showPrint="true" listWidth="120%"
							  minHeight="345"  style="table-layout:fixed;">
					  <ec:row>
					    <ec:column width="50" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
					    <ec:column property="bdate" title="��������" width="8%" style="text-align:center;">
					         <fmt:formatDate value="${ledgerQuery.bdate}" pattern="yyyy-MM-dd" />
					    </ec:column>
						<ec:column property="voucherNo" title="ƾ֤��" width="8%" style="text-align:center;" />
					    <ec:column property="summaryNo" title="ƾ֤ժҪ��" width="10%" style="text-align:center;"/>
						<ec:column property="summary" title="ƾ֤ժҪ" width="10%" style="text-align:center;"  ellipsis="true"/>			
						<ec:column property="debitNo" title="�跽��Ŀ" width="10%" style="text-align:center;" ellipsis="true"/>						
						<ec:column property="debitAccount.name" title="�跽��Ŀ����" width="10%" style="text-align:center;" ellipsis="true"/>
						<ec:column property="creditNo" title="������Ŀ" width="10%" style="text-align:center;" ellipsis="true"/>
						<ec:column property="creditAccount.name" title="������Ŀ����" width="10%" style="text-align:center;" ellipsis="true"/>
						<ec:column property="amount" title="�������" width="15%" style="text-align:right;" format="#,##0.00"  cell="number"  calc="total" calcTitle= "�ϼ�">
						  <fmt:formatNumber value="${ledgerQuery.amount }" pattern="#,##0.00" />
						</ec:column>		
						<ec:column property="contractNo" title="��ͬ��" width="10%" style="text-align:center;" ellipsis="true"/>					
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
