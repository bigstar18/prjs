<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�������ʽ���ˮ</title>
<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
<script type="text/javascript">
	$ (function ()
    {
	    $ ("#view").click (function ()
	    {
		    frm.submit ();
	    });
    });
</script>
<script type="text/javascript">
	function setDisabled (obj)
    {
	    obj.disabled = true;
	    obj.style.backgroundColor = "#C0C0C0";
    }
    function setEnabled (obj)
    {
	    obj.disabled = false;
	    obj.style.backgroundColor = "white";
    }

    function change ()
    {
	    var value = frm.type.value;
	    if (value == 'D')
	    {
		    
		    setDisabled (frm.beginDate);
		    setDisabled (frm.endDate);
	    }
	    else if (value == 'H')
	    {
		    
		    setEnabled (frm.beginDate);
		    setEnabled (frm.endDate);
	    }
    }
</script>
<style type="text/css">
.input:disabled {
	border: 1px solid #DDD; background-color: #F5F5F5; color: #ACA899;
}
</style>
</head>
<body onload="change();">
	<!-------------------------Body Begin------------------------->
	<div class="main">
		<jsp:include page="/front/frame/current.jsp"></jsp:include>
		<div class="warning">
			<div class="title font_orange_14b">��ܰ��ʾ :</div>
			<div class="content">�ڴ�չʾ�������ʽ���ˮ��Ϣ��</div>
		</div>
		<div class="main_copy">
			<form id="frm" action="${basePath}/front/app/finance/fund/firmFundFlow.action?sortColumns=order+by+fundFlowId+desc" method="post">
				<div class="sort" style="height: 80px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr style="height: 35px;">
							<td align="left" width="20%">&nbsp;&nbsp;&nbsp;��ѯ��Χ:&nbsp; <label> <select name="type" size="1" id="type" style="width: 120" onchange="change()">
										<option value="D">��ǰ</option>
										<option value="H">��ʷ</option>
								</select>
							</label> <script>
								document.getElementById ("type").value = "${type}";
							</script>
						</td>
							<td align="left" width="20%">&nbsp;&nbsp;&nbsp;��ʼ����:&nbsp; <label> <input type="text" style="width: 120px" id="beginDate" title="�˴���ָ������Ϊ��������" class="Wdate" maxlength="10" name="${GNNT_}primary.b_Date[>=][date]" value='${oldParams["primary.b_Date[>=][date]"]}' readonly="readonly"
									onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" />
							</label>
						</td>
							<td align="left" width="20%">&nbsp;&nbsp;&nbsp;��������:&nbsp; <label> <input type="text" style="width: 120px" id="endDate" title="�˴���ָ������Ϊ��������" class="Wdate" maxlength="10" name="${GNNT_}primary.b_Date[<=][datetime]" value='${oldParams["primary.b_Date[<=][datetime]"]}' readonly="readonly"
									onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginDate\')}'})" />
							</label>
						</td>
							<td align="left" width="20%">&nbsp;ϵͳ����:&nbsp; <label> <select class="input_text" id="moduleId" name="${GNNT_}primary.summaryF.ledgerField.moduleId[=]">
										<option value="">ȫ��</option>
										<c:forEach var="list" items="${moduleList}">
											<option value="${list.moduleId}">${list.name }</option>
										</c:forEach>
								</select>
							</label> <script>
								document.getElementById ("moduleId").value = "${oldParams['primary.summaryF.ledgerField.moduleId[=]']}";
							</script>
						</td>
						</tr>
						<tr>
							<td align="left" width="20%">&nbsp;&nbsp;&nbsp;ҵ������:&nbsp; <label> <select class="input_text" id="summaryNo" name="${GNNT_}primary.summaryF.summaryNo[=][String]">
										<option value="">ȫ��</option>
										<c:forEach var="list" items="${summaryList}">
											<option value="${list.summaryNo }">${list.summary }</option>
										</c:forEach>
								</select>
							</label> <script>
								document.getElementById ("summaryNo").value = "${oldParams['primary.summaryF.summaryNo[=][String]']}";
							</script>
						</td>
							<td width="20%" align="left">&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��: <label> &nbsp;<input type="text" size="19" id="amount" name="amount" />
							</label> <script>
								document.getElementById ("amount").value = "${amount}";
							</script>
						</td>
							<td width="20%" align="left">&nbsp;&nbsp;&nbsp;�ʽ����:&nbsp; <label> <select id="balance" name="balance" class="normal" style="width: 120px">
										<option value="">ȫ��</option>
										<option value="B">������</option>
										<option value="Eq">������</option>
										<option value="S">С����</option>
								</select> <script>
									document.getElementById ("balance").value = "${balance}";
								</script>
							</label>
						</td>
							<td width="110" height="30"><a href="#" id="view"><img src="${skinPath }/image/memberadmin/searchbt1.gif" width="93" height="23" border="0" /> </a></td>
						</tr>
					</table>
				</div>
				<div class="content">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<div class="tb_bg">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content" summary="�����еĽ���">
										<tr class="font_b tr_bg">
											<td width="3%" align="center">���</td>
											<td width="4%" align="center">��ˮ���</td>
											<c:if test="${type=='H'}">
												<td width="5%" align="center">��������</td>
											</c:if>
											<td width="5%" align="center">�����̴���</td>
											<td width="5%" align="center">ҵ������</td>
											<td width="4%" align="center">��ͬ��</td>
											<td width="6%" align="center">��Ʒ����</td>
											<td width="6%" align="center">������(Ԫ)</td>
											<td width="7%" align="center">�ʽ����(Ԫ)</td>
											<%-- <td width="7%" align="center">�����ʽ��(Ԫ)</td>--%>
											<td width="4%" align="center">ƾ֤��</td>
											<td width="5%" align="center">��������</td>
										</tr>
										<c:forEach var="fundFlow" items="${pageInfo.result}" varStatus="status">
											<tr>
												<td class="maxsize" lang="25" align="center">${status.count}</td>
												<td class="maxsize" lang="40" align="center">${fundFlow.fundFlowId}</td>
												<c:if test="${type=='H'}">
													<td><fmt:formatDate value="${fundFlow.b_Date}" pattern="yyyy-MM-dd " /></td>
												</c:if>
												<td align="center">${fundFlow.firmId}</td>
												<td align="center">${fundFlow.summaryF.summary}</td>
												<td align="center">${fundFlow.contractNo}</td>
												<td align="center">${fundFlow.commodityId}</td>
												<td align="right"><fmt:formatNumber value="${fundFlow.amount}" pattern="#,##0.00" /></td>
												<td align="right"><fmt:formatNumber value="${fundFlow.balance}" pattern="#,##0.00" /></td>
												<%--<td align="right"><fmt:formatNumber value="${fundFlow.appendAmount}" pattern="#,##0.00" /></td>--%>
												<td align="center">${fundFlow.voucherNo}</td>
												<td align="center"><fmt:formatDate value="${fundFlow.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											</tr>
										</c:forEach>
									</table>
								</div>
						</td>
						</tr>
						<tr>
							<td><jsp:include page="/front/app/finance/fund/pageinfo.jsp"></jsp:include></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<!-------------------------Body End------------------------->
</body>
</html>