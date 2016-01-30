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
			  //�����ʼ���� 
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
			if ( startDate > endDate ) { 
		        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
		        return false; 
		    } 
		  }
		  
				frm.submit();
			
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
					<form id="frm" name="frm" action="${basePath}/timebargain/delay/delayQuotationList.action?sortColumns=order+by+commodityID+asc" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px">
											<tr>
												<td align="right">
												��Ʒ���� ��
												</td>
												<td>
													<input type="text" id="commodityID" name="${GNNT_}primary.commodityID[like]" title="������ģʽƥ�����ѯ" style="width:111;ime-mode:disabled" maxlength="24"
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.commodityID[like]']}"/>
												</td>
												<td align="center">
													    <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" <c:if test="${isQryHisHidd == 'yes' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">����ʷ</label>
													    <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
												</td>
												<td align="right">
													��ʼ���ڣ�
												</td>
												<td><!--  ondblclick="if(!this.readOnly){setRq(this);}" -->
												<input type="text" style="width: 80px" id="beginDate"
													class="validate[required] wdate" maxlength="10"
													name="${GNNT_}primary.clearDate[>=][date]" 
													value="${oldParams['primary.clearDate[>=][date]']}"
													onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="right">
													�������ڣ�
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
				<br />
			
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="quotation"
									action="${basePath}/timebargain/delay/delayQuotationList.action?sortColumns=order+by+commodityID+asc"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
									    <ec:column property="commodityID" title="��Ʒ����" width="10%" style="text-align:center;"/>
										<ec:column property="buySettleQty" title="���뽻���걨��" cell="currency" width="10%" style="text-align:right;"/>
										<ec:column property="sellSettleQty" title="���������걨��" cell="currency" width="10%" style="text-align:right;"/>
										<ec:column property="buyNeutralQty" title="�����������걨��" cell="number" calc="total" calcTitle= "�ϼ�" width="10%" style="text-align:right;"/>
							
										<ec:column property="sellNeutralQty" title="�����������걨��" cell="number" calc="total" calcTitle= "�ϼ�" width="10%" style="text-align:right;"/>
										<ec:column property="delayCleanHoldQty" title="���ھ�������" cell="number" calc="total" calcTitle= "�ϼ�" width="10%" style="text-align:right;"/>
										<ec:column property="createTime" title="����ʱ��" width="12%" style="text-align:center;">
										      <fmt:formatDate value="${quotation.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
										</ec:column>
										<ec:column property="clearDate" title="��������" width="10%" style="text-align:center;">
												     <c:if test="${isHis == 'yes'}">
												      <fmt:formatDate value="${quotation.clearDate }" pattern="yyyy-MM-dd" />
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
	
	<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	

</body>
</html>
