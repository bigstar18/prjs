<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
  <head>
    <title>�����˺ϼ�</title>
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
		  var accountCode = document.getElementById("accountCode").value;
		  var startDate = document.getElementById("beginDate").value;
		  var endDate =  document.getElementById("endDate").value;
			 
			 if(accountCode == ""){
				 alert("��Ŀ���벻��Ϊ�գ�");
				 frm.accountCode.focus();
				 return false;
			 }

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
			  <form name="frm" action="${basePath}/finance/financialQuery/ledgerSum.action" method="post">
			    <table border="0" cellpadding="0" cellspacing="0" class="table2_style">
				  <tr>
				    <td class="table5_td_width">
					  <div class="div2_top">
					    <table class="table3_style" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							  <td class="table3_td_1" align="left">
							          ��Ŀ����:&nbsp;
								<label>
								  <input type="text" class="input_text" id="accountCode" name="accountCode" onkeypress="onlyNumberAndCharInput()" />
							    </label>
							  </td>
							  <td class="table3_td_1" align="right">
											��ʼ����:
											<input type="text" class="wdate" id="beginDate"  style="width: 106px"
												name="beginDate"			
		
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							  </td>
							  <td class="table3_td_1" align="right">
											��������:
											<input type="text" class="wdate" id="endDate"  style="width: 106px"
												name="endDate"			
												onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
						      </td>		
						      
						      <td class="table3_td_anniu" align="right">
						        <button class="btn_sec" id="view" onclick="dolistquery()">��ѯ</button>
							    &nbsp;&nbsp;
							    <button class="btn_cz" onclick="myReset();">����</button>
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
              <b>��Ŀ���룺</b><c:out value="${balance.CODE}"/>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <b>��Ŀ���ƣ�</b><c:out value="${balance.NAME}"/>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <b>�ڳ���</b>
              <label>
                <c:choose>
	  				<c:when test='${balance != null}'>
	  				    <fmt:formatNumber value="${balance.LASTDAYBALANCE}" pattern="#,##0.00"/>	
		  			</c:when>
	  				<c:otherwise>0.00</c:otherwise>
	  			</c:choose>
              </label>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <b>��ĩ��</b>
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
					    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
						<ec:column property="SUMMARYNO" title="ƾ֤ժҪ��" width="10%" style="text-align:center;" />
						<ec:column property="SUMMARY" title="ƾ֤ժҪ" width="10%" style="text-align:center;" ellipsis="true"/>			
						<ec:column property="DAMOUNT" title="�跽������" width="10%" style="text-align:right;"  format="#,##0.00"  cell="number"  calc="total" calcTitle= "�ϼ�" />							
						<ec:column property="CAMOUNT" title="����������" width="10%" style="text-align:right;"  format="#,##0.00"  cell="number"  calc="total" calcTitle= "�ϼ�"/>
						
						
						<c:if test="${balance.DCFLAG == 'D'}">
	  				       <c:set var="bal" value="${bal + ledgerSum.DAMOUNT - ledgerSum.CAMOUNT}"></c:set>
	  			        </c:if>
	  			        <c:if test="${balance.DCFLAG == 'C'}">
	  				       <c:set var="bal" value="${bal - ledgerSum.DAMOUNT + ledgerSum.CAMOUNT}"></c:set>
	  			        </c:if>
					    <ec:column property="bal" title="���" width="10%" style="text-align:right;" >
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
