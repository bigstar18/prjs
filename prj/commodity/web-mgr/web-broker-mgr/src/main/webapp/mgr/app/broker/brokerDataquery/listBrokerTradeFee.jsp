<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<title>�����������ѻ��ܲ�ѯ</title>
<script type="text/javascript">
	
	//ִ�в�ѯ�б�
	function dolistquery() {
	//��֤��Ϣ
	var beginBroker = document.forms[0].beginBroker.value;
	var endBroker = document.forms[0].endBroker.value;
	var beginDate = document.forms[0].beginDate.value;
	var endDate = document.forms[0].endDate.value;
	//alert(beginBroker+","+endBroker+","+beginDate+","+endDate);
	if(beginBroker == "" && endBroker != ""){
		alert("��ʼ�����̲���Ϊ�գ���");
		return false;
	}
	if(beginBroker != "" && endBroker == ""){
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
	      <div class="div_cx">
					<form action="${basePath}/broker/brokerDataquery/listBrokerTradeFee.action"
						method="POST" styleClass="form" name="statQueryForm" id="frm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��ѯ����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>		
						            <td>&nbsp;</td>
									<td align="right">
										&nbsp;&nbsp;��ʼ�����̣�<SELECT  name="${GNNT_}primary.beginBroker[=][Long]" id="beginBroker" >
	            						<OPTION value="">ȫ��</OPTION>
							            <c:forEach items="${brokerAscList}" var="result">
								        <option value="${result.BROKERID}">${result.BROKERID}</option>
									    </c:forEach>
          							</SELECT>
										 <script >frm.beginBroker.value = "<c:out value='${oldParams["primary.beginBroker[=][Long]"] }'/>";</script>
									</td>
									
									<td align="left">
										&nbsp;&nbsp;���������̣�
									<SELECT name="${GNNT_}primary.endBroker[=][Long]" id="endBroker">
	            						<OPTION value="">ȫ��</OPTION>
							            <c:forEach items="${brokerDescList}" var="result">
								        <option value="${result.BROKERID}">${result.BROKERID}</option>
									    </c:forEach>
          							</SELECT>
									 <script >frm.endBroker.value = "<c:out value='${oldParams["primary.endBroker[=][Long]"] }'/>";</script>
									</td>
						
									<td align="right">
										���ܷ�ʽ��
										<select name="${GNNT_}primary.sumType[=][Long]" id="sumType">
										<option value="">����</option>
										<option value="breed" selected="selected">��Ʒ�ֻ���</option>
										<option value="sumByBreed" >Ʒ�ֻ���</option>
										<option value="day" >�ֽ����ջ���</option>
										<option value="sumByDay" >�����ջ���</option>
										</select>
										<script >frm.sumType.value = "<c:out value='${oldParams["primary.sumType[=][Long]"] }'/>";</script>
									</td>
								</tr>
								<tr>
									<td align="right">
									</td>
									<td>
									</td>
									
						            
									<td align="right">
											            &nbsp;&nbsp;��ʼ����:
											        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
												       name="${GNNT_}primary.occurDate[>=][date]"			
												       value="${oldParams['primary.occurDate[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>

									              <td align="right">
											           &nbsp;&nbsp;��������:
											         <input type="text" class="wdate" id="endDate"  style="width: 106px" 
												      name="${GNNT_}primary.occurDate[<=][date]"			
												       value="${oldParams['primary.occurDate[<=][date]']}"					
												      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>		
									<td align="right">
										&nbsp;&nbsp;<input type="button"
											onclick="javascript:return dolistquery();" class="button" value="ִ�в�ѯ"/>
									</td>
																										
								</tr>
							</table>
						</fieldset>
					</form>
			</div>
	    
		<div class="div_list">
		
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="resultList" var="result" 
			action="${basePath}/broker/brokerDataquery/listBrokerTradeFee.action"	
			xlsFileName="tradeFeeBrokerList.xls" 
			csvFileName="tradeFeeBrokerList.csv"
			showPrint="true" 			
			rowsDisplayed="20"
			listWidth="100%"	
			minHeight="300"  
			filterable="false"
            
	>
		<ec:row>	
			
			<ec:column property="NAME" title="����������" width="10%" calcTitle= "�ϼ�" calc="total" style="text-align:center;"/>
			<ec:column property="BROKERID" title="�����̱��"  width="10%"  style="text-align:center;"/>
			<ec:column property="BREEDID" title="Ʒ�ֱ��"  width="8%"  style="text-align:center;">
			<c:if test="${result.BREEDID==null}">
			������
			</c:if>
			<c:if test="${result.BREEDID!=null}">
			${result.BREEDID}
			</c:if>
			</ec:column>
			<ec:column property="_1" title="Ӷ������" width="5%" style="text-align:center;" >
				<c:if test="${result.REWARDTYPE==0}">
				         ����
				</c:if>				
				<c:if test="${result.REWARDTYPE==1}">
				    ����
				</c:if>								
			</ec:column>
			<ec:column property="SUMTRADEFEE" title="������" cell="currency" format="###,##0.00" calc="total" width="10%" style="text-align:right;"/>
			<ec:column property="SUMSELFGAIN" title="������������" cell="currency" format="###,##0.00" calc="total" width="10%" style="text-align:right;"/>
			<ec:column property="SUMMARHETGAIN" title="������������" cell="currency" format="###,##0.00" calc="total" width="10%" style="text-align:right;"/>
			<ec:column property="SUMBROKEREACHDIVIDE" title="�����������ѷֳ�" cell="currency" format="###,##0.00" calc="total" width="10%" style="text-align:right;"/>
			<ec:column property="SUMFINALMARHETGAIN" title="������ʵ��������" cell="currency" format="###,##0.00" calc="total" width="10%" style="text-align:right;"/>
			<ec:column property="SUMREWARD" title="������ʵ��������" cell="currency" format="###,##0.00" calc="total" width="10%" style="text-align:right;"/>
			<ec:column property="CLEARDATE" title="��������" width="10%" style="text-align:right;">
			<c:if test="${result.CLEARDATE==null}">
			������
			</c:if>
			<c:if test="${result.CLEARDATE!=null}">
			  <fmt:formatDate value="${result.CLEARDATE }" pattern="yyyy-MM-dd" />			
			</c:if>
			</ec:column>
		</ec:row>
	</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">

</script>
</html>
