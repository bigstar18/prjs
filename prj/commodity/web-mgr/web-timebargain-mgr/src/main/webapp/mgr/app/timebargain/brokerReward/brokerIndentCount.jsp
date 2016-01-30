<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
  String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>�����̶����ϼ�</title>
		<SCRIPT type="text/javascript">

		function window_onload(){

			if(frm.clearDate.value=="")
			  {
				frm.clearDate.value = '<%=nowDate%>';
				   
			  }		  
			isQryHis_onclick();
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
			  setEnabled(frm.clearDate);	    
		  }
		  else
		  {
			  setDisabled(frm.clearDate);
		  }
		  
		}

			//ִ�в�ѯ�б�
			function dolistquery() {

		         if(frm.isQryHis.checked)
				  {
					  frm.isQryHisHidd.value = "yes";
					 
				  }else{
					  frm.isQryHisHidd.value = "no";
				  }
				  	       	
		         frm.submit();
			}
		</SCRIPT>
	</head>
	<body onload="window_onload()">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/dataquery/listBrokerIndentCount.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															�����̴��룺
															<label>
																<input type="text" class="input_text" id="brokerID"   name="brokerID"" />
															</label>
														</td>
													   <td class="table3_td_1" align="right">
															&nbsp;&nbsp;��Ʒ���룺
															<label>
																<input type="text" class="input_text" id="commodityId"   name="commodityId" title="������ģʽƥ�����ѯ" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															&nbsp;&nbsp;������
															<label>
																<select id="flag" name="flag"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${BS_flagMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
														</td>
												</tr>
												<tr>
												       <td class="table3_td_1" align="right">
															   <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" <c:if test="${isQryHisHidd=='yes' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">����ʷ</label>
															   <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
														</td>
											              <td class="table3_td_1" align="right">
																��ѯ����:
															   <input type="text" class="wdate" id="clearDate"  style="width: 106px"
																	name="clearDate"				
																	onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
													    </td>
												        <td class="table3_td_anniu" align="center">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
												        </td>	
												</tr>
												</table>
												<script type="text/javascript">
												    frm.brokerID.value = "<c:out value='${brokerID}'/>";
												    frm.commodityId.value = "<c:out value='${commodityId}'/>";
												    frm.flag.value = "<c:out value='${flag}'/>";
												    frm.clearDate.value = "<c:out value='${clearDate}'/>";
												</script>
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
										<ec:table items="pageInfo.result" var="broker"
											action="${basePath}/timebargain/dataquery/listBrokerIndentCount.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="BROKERID" title="�����̴���" width="170" ellipsis="true" style="text-align:center;" />
									            <ec:column property="NAME" title="����������" width="100" style="text-align:center;" ellipsis="true"/>			
									            <ec:column property="COMMODITYID" title="��Ʒ����" width="100" style="text-align:center;"/>
												<ec:column property="BS_FLAG" title="����" width="40" style="text-align:center;">
												   <c:if test="${broker.BS_FLAG == 1}">
												            ��
												   </c:if>
												   <c:if test="${broker.BS_FLAG == 2}">
												           ��
												   </c:if>
												</ec:column>
												<ec:column property="HOLDQTYGAGEQTY" title="��������" cell="number" calc="total" calcTitle= "�ϼ�" width="120" style="text-align:right;"/>			
												<ec:column property="GAGEQTY" title="�ֶ�����" cell="number" calc="total" calcTitle= "�ϼ�" width="120" style="text-align:right;"/>
												<ec:column property="LASTHOLEQTY" title="���ն�������" cell="number" calc="total" calcTitle= "�ϼ�" width="120" style="text-align:right;"/>
												<ec:column property="HOLDCE" title="�������" cell="number"  width="120" style="text-align:right;"/>
												<ec:column property="HOLDMARGIN" title="������֤��" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="FLOATINGLOSS" title="����ӯ��" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="HOLDASSURE" title="����������" cell="currency" width="120" style="text-align:right;"/>
												<ec:column property="HP" title="��������"  width="120" style="text-align:right;">
												  <fmt:formatNumber value="${broker.HP}" pattern="#,######0.000000"/>
												</ec:column>
												<c:if test="${isQryHisHidd == 'yes'}">
												   <ec:column property="CLEARDATE" title="��������" cell="date" format="date" width="85" style="text-align:center;">
												       <fmt:formatDate value="${broker.CLEARDATE }" pattern="yyyy-MM-dd" />
												   </ec:column>
												</c:if>												
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>