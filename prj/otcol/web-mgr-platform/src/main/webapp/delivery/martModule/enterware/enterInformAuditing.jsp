<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title><%=TITLE%></title>
</head>
<body>
        <form id="formNew" name="frm" method="POST" targetType="hidden">
		<fieldset width="100%">
		<legend>处理入库通知单</legend>
			<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
				<tr height="30px">
					<td colspan="5" class="cd_hr">******商品入库通知单<FONT COLOR="red">（*******）</FONT></td>
				</tr>
			</table>
			<table width="96%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
				<tr height="25px">
					<td width="6%" rowspan="2" class="cd_bt">
						<table border="0">
							<tr><td class="cd_bt">交</td></tr>
							<tr><td class="cd_bt">易</td></tr>
							<tr><td class="cd_bt">商</td></tr>
						</table>
					</td>
					<td width="18%" class="cd_bt">${FIRMNAME}</td>
					<td width="29%" class="cd_list1">*****</td>
					<td width="18%" class="cd_bt">${FIRMID}</td>
					<td width="29%" class="cd_list1">*****</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">联系人</td>
					<td class="cd_list1">********</td>
					<td class="cd_bt">联系电话</td>
					<td class="cd_list1">********</td>
				</tr>
				<tr height="25px">
					<td rowspan="2" class="cd_bt">
						<table border="0">
							<tr><td class="cd_bt">仓</td></tr>
							<tr><td class="cd_bt">库</td></tr>
						</table>
					</td>
					<td class="cd_bt">仓库名称</td>
					<td class="cd_list1">*******</td>
					<td class="cd_bt">详细地址</td>
					<td class="cd_list1">********</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">联系人</td>
					<td class="cd_list1">**********</td>
					<td class="cd_bt">联系电话</td>
					<td class="cd_list1">**********</td>
				</tr>
				<tr height="25px">
					<td rowspan="7" class="cd_bt">
						<table border="0">
							<tr><td class="cd_bt">商</td></tr>
							<tr><td class="cd_bt">品</td></tr>
							<tr><td class="cd_bt">信</td></tr>
							<tr><td class="cd_bt">息</td></tr>
						</table>
					</td>
					<td class="cd_bt">拟入库时间</td>
					<td  class="cd_list1">***********</td>
					<td class="cd_bt">品种名称</td>
					<td class="cd_list1">***********</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">
						入库重量
						<jsp:include page="/public/countUnit.jsp">
							<jsp:param name="commodityID" value="***********"/>
						</jsp:include>
					</td>
					<td class="cd_list1">***************</td>
					<td class="cd_bt">等级</td>
					<td class="cd_list1">************</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">
						每件重量
						<jsp:include page="/public/countUnit.jsp">
							<jsp:param name="commodityID" value="*********"/>
						</jsp:include>
					</td>
					<td class="cd_list1">**********</td>
					<td class="cd_bt">件数</td>
					<td class="cd_list1">*************</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">批号</td>
					<td class="cd_list1">**********</td>
					<td class="cd_bt">***********</td>
					<td class="cd_list1">***********</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">生产厂家</td>
					<td class="cd_list1">
						**********&nbsp;	
					</td>
					<td class="cd_bt">生产年月</td>
					<td class="cd_list1">********</td>
				</tr>
				<tr height="25px">
					<td class="cd_bt">备注</td>
					<td  class="cd_list1"><input name="remark" type="text" readOnly value="**************" class="form_k1"></td>
					<td class="cd_bt">是否质检</td>
					<td  class="cd_list1"><input name="remark" type="text" readOnly value="*********" class="form_k1"></td>
				</tr>
				<tr height="25px">
					<td width="18%" class="cd_bt">入库申请单编号</td>
					<td width="29%" class="cd_list1">***********</td>
					<td width="18%" class="cd_bt">入库通知单编号</td>
					<td width="29%" class="cd_list1">**********</td>
				</tr>
				<!--<tr height="25px">
					<td colspan="2" class="cd_bt"><FONT COLOR="red">保证金（*********）</FONT></td>
					<td colspan="3" class="cd_list1"><input name="bail" type="text" readOnly value="*********" class="form_k4"></td>
				</tr>-->
				<tr height="50px">
					<td class="cd_bt">
						<table border="0">
							<tr><td class="cd_bt">提</td></tr>
							<tr><td class="cd_bt">示</td></tr>
						</table>
					</td>
					<td class="cd_list1" colspan=4><font color="blue">
						1、卖方应至少提前2天与交收仓库确定到货日期。<br>
						<!--&nbsp;2、本市场允许实际入库量与申报量存在±10%的溢短差。<br>-->
						&nbsp;2、仓库地图请在本市场交易网站下载。<br></font>
					</td>
					
				</tr>
			</table>
			<br>
			<br>
			<div align="center">

			  <button class="mdlbtn" type="button" onClick="fReceive();">收取保证金</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			 <!-- <button class="mdlbtn" type="button" onClick="fBack();">退还保证金</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->

			 <!-- <button class="mdlbtn" type="button" onclick="fBreach();">会员违约</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->

			  <button class="mdlbtn" type="button" onClick="fEnterWare();">查看入库单</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			  <button class="smlbtn" type="button" onClick="fprint()">打印</button>
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			  <button class="smlbtn" type="button" onClick="freturn()">返回</button>
            </div>
		</fieldset>
		<input type="hidden" name="tag" value="************">
		<input type="hidden" name="pageIndex" value="***********">
		<input type="hidden" name="queryId" value="************">
		<input type="hidden" name="queryDealerId" value="************">
		<input type="hidden" name="queryWareId" value="*************">
		<input type="hidden" name="queryState" value="***********">
		<INPUT TYPE="hidden" NAME="requestId" value="***********">
		<INPUT TYPE="hidden" NAME="requestState" value="****************">
        </form>
</body>
</html>
<script>
	function fEnterWare(){
		openDialog("***********EnterWare/enterWareInfo.jsp?Id=*********","_bank",750,430);
	}
	function fReceive(){
		if(confirm("确定已收取了保证金吗？")){
			frm.tag.value="receive";
			frm.submit();
		}
	}
	function fBack(){
		openDialog("**************EnterWare/bailInfo.jsp?Id=********","_bank",600,200);
		if(confirm("确定保证金已返还会员了吗？")){
			frm.tag.value="back";
			frm.submit();
		}
	}
	function fBreach(){
		if(confirm("确定违约已处理完成吗？")){
			frm.tag.value="breach";
			frm.submit();
		}
	}
	function freturn(){
		frm.tag.value="query";
		frm.action = "***************EnterWare/enterInformList.jsp";
		frm.submit();
	}
	function fprint(){
		frm.action = "*********enterInformPrint.jsp";
		frm.submit();
	}
</script>
<SCRIPT LANGUAGE="JavaScript">
	var errorInfo = "************";
	if(errorInfo!=""){
		alert(errorInfo);
	}
</SCRIPT>