<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<%
Date sysdate = new Date();
String nowDate = Tools.fmtDate(sysdate);
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

		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach', {promptPosition : "bottomRight"});
		});
		
		function dolistquery() {
			if(jQuery("#frm").validationEngine('validateform')){
				frm.submit();
			}
		}
		</script>
</head>
<body leftmargin="2" topmargin="0">
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/firm/tariff/tradeFeeFirmCommodityList.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px">
											<tr>
												<td align="right">
														��ʼƷ�֣�
												</td>
												<td>
													<input type="text" id="firmID" name="${GNNT_}tb.breedID[>=][int]" style="width:111;ime-mode:disabled" maxlength="16" title=""
														class="validate[onlyLetterNumber] input_text" value="${oldParams['tb.breedID[>=][int]']}"/>
												</td>
						                        <td align="right">
														����Ʒ�֣�
												</td>
												<td>
													<input type="text" id="commodityID" name="${GNNT_}tb.breedID[<=][int]"  style="width:111;ime-mode:disabled" maxlength="24"
														class="validate[onlyLetterNumber] input_text" value="${oldParams['tb.breedID[<=][int]']}"/>
												</td>		
						                        <td align="right"></td>
			                                    <td>
			                                       	                            
			                                    </td>																			
											</tr>
											
											<tr>
												<td align="right">
													��ʼ���ڣ�
												</td>
												<td><!--  ondblclick="if(!this.readOnly){setRq(this);}" -->
												<input type="text" style="width: 80px" id="beginDate"
													class="validate[required] wdate" maxlength="10"
													name="beginDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${beginDate}" />"
													onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="right">
													�������ڣ�
												</td>
												<td>
												<input type="text" style="width: 80px" id="endDate"
													class="validate[required] wdate" maxlength="10"
													name="endDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />"
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
								<ec:table items="pageInfo.result" var="trade"
									action="${basePath}/firm/tariff/tradeFeeFirmCommodityList.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
									    <ec:column property="BREEDNAME" title="Ʒ������" width="120" 
											style="text-align:center;" />
			
										<ec:column property="BREEDID" title="Ʒ�ִ���" width="120" 
											style="text-align:center;" />
										
										<ec:column property="SUMTRADEFEE" title="������" cell="currency"
											 width="120" style="text-align:center;" />
										<ec:column property="SUMSELFGAIN" title="������������"
										     width="150"
											style="text-align:center;" />
										<ec:column property="SUMMARHETGAIN" title="������������"
											cell="currency"  width="150"
											style="text-align:center;" />
										<ec:column property="SUMBROKEREACHDIVIDE" title="�����������ѷֳ�"
											cell="currency"  width="150"
											style="text-align:center;" />
										<ec:column property="SUMFINALMARHETGAIN" title="������ʵ��������"
											cell="currency"  width="150"
											style="text-align:center;" />
										<ec:column property="SUMREWARD" title="������ʵ��������"
											cell="currency"  width="150"
											style="text-align:center;" />
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
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_DelayOrderType" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="DelayOrderType" >
			<ec:options items="DELAYORDERTYPE" />
		</select>
	</textarea>	
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_status2" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TRADETYPE" />
		</select>
	</textarea>	
<span style="color: red;">ע��ֻ��ʾ����ģ��ļ����̷�Ӷ����</span>
</body>
</html>
