<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<title>�����������ѻ��ܲ�ѯ</title>
<script type="text/javascript">
	
	//ִ�в�ѯ�б�
	function dolistquery() {
	//��֤��Ϣ
	var beginFirm = document.forms[0].beginFirm.value;
	var endFirm = document.forms[0].endFirm.value;
	var beginDate = document.forms[0].beginDate.value;
	var endDate = document.forms[0].endDate.value;
	//alert(beginBroker+","+endBroker+","+beginDate+","+endDate);
	if(beginFirm == "" && endFirm != ""){
		alert("��ʼ�����̲���Ϊ�գ���");
		return false;
	}
	if(beginFirm != "" && endFirm == ""){
		alert("���������̲���Ϊ�գ���");
		return false;
	}
	if(beginDate == "" && endDate != ""){
		alert("��ʼ���ڲ���Ϊ�գ���");
		return false;
	}
	if(beginDate != "" && endDate == ""){
		alert("�������ڲ���Ϊ�գ���");
		return false;
	}
	if(beginDate > endDate){
		alert("��ʼ���ڲ��ܴ��ڽ������ڣ�����");
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
							<form name="frm" action="${basePath}/timebargain/brokerReward/listFirmTradeFee.action">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
								  <tr>
									<td class="table5_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
										<tr>					            
									<td class="table3_td_1" align="right">
										��ʼ�����̣�
										<SELECT  name="${GNNT_}primary.beginFirm[=][Long]" id="beginFirm" class="input_text">
		            						<OPTION value="">ȫ��</OPTION>
								            <c:forEach items="${firmAscList}" var="result">
									        <option value="${result.FIRMID}">${result.FIRMID}</option>
										    </c:forEach>
          							   </SELECT>
										 <script >frm.beginFirm.value = "<c:out value='${oldParams["primary.beginFirm[=][Long]"] }'/>";</script>
									</td>
									
									<td class="table3_td_1" align="right">
										���������̣�
									<SELECT name="${GNNT_}primary.endFirm[=][Long]" id="endFirm" class="input_text">
	            						<OPTION value="">ȫ��</OPTION>
							            <c:forEach items="${firmDescList}" var="result">
								        <option value="${result.FIRMID}">${result.FIRMID}</option>
									    </c:forEach>
          							</SELECT>
									 <script >frm.endFirm.value = "<c:out value='${oldParams["primary.endFirm[=][Long]"] }'/>";</script>
									</td>
														
								</tr>
								  <tr>																				            
									<td class="table3_td_1" align="right">
								            &nbsp;&nbsp;��ʼ����:
								        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
									       name="${GNNT_}primary.occurDate[>=][date]"			
									       value="${oldParams['primary.occurDate[>=][date]']}"					
									       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
				                     </td>

						              <td class="table3_td_1" align="right">
								           &nbsp;&nbsp;��������:
								         <input type="text" class="wdate" id="endDate"  style="width: 106px" 
									      name="${GNNT_}primary.occurDate[<=][date]"			
									       value="${oldParams['primary.occurDate[<=][date]']}"					
									      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
				                     </td>		
									<td class="table3_td_anniu" align="center">
									        &nbsp;&nbsp;
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
						<ec:table items="resultList" var="result" 
			action="${basePath}/timebargain/brokerReward/listFirmTradeFee.action"	
			xlsFileName="tradeFeeFirmList.xls" 
			csvFileName="tradeFeeFirmList.csv"
			showPrint="true" 			
			rowsDisplayed="20"
			listWidth="110%"	
			minHeight="300"  
			filterable="false"
            
	>
		<ec:row>	
			
			<ec:column property="FIRMNAME" title="����������" width="8%" calcTitle= "�ϼ�" calc="total" style="text-align:center;"/>
			<ec:column property="FIRMID" title="�����̴���"  width="10%"  ellipsis="true" style="text-align:center;"/>
			<ec:column property="NAME" title="����������"  width="8%"  style="text-align:center;"/>
			<ec:column property="BROKERID" title="�����̱��"  width="10%" ellipsis="true"  style="text-align:center;"/>
			<ec:column property="BREEDID" title="Ʒ�ִ���"  width="8%"  style="text-align:center;">	
			</ec:column>
			<ec:column property="BREEDNAME" title="Ʒ������"  width="8%"  style="text-align:center;"/>
			<ec:column property="SUMTRADEFEE" title="������" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMSELFGAIN" title="������������" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMMARHETGAIN" title="������������" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMBROKEREACHDIVIDE" title="�����������ѷֳ�" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMFINALMARHETGAIN" title="������ʵ��������" cell="currency" format="###,##0.00" calc="total" width="8%" style="text-align:right;"/>
			<ec:column property="SUMREWARD" title="������ʵ��������" cell="currency" calc="total" format="###,##0.00" width="8%" style="text-align:right;"/>
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
