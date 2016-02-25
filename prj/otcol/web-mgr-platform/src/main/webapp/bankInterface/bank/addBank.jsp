<%@ page contentType="text/html;charset=GB2312" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 

<%
BankDAO dao = BankDAOFactory.getDAO();
if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
{
	String bankID = request.getParameter("bankID");
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogdate(new Date());
	lv.setIp(computerIP);
	String bankName = request.getParameter("bankName");
	double maxPerSglTransMoney = Tool.strToDouble(request.getParameter("maxPerSglTransMoney"));
	double maxPerTransMoney = Tool.strToDouble(request.getParameter("maxPerTransMoney"));
	int maxPerTransCount = Tool.strToInt(request.getParameter("maxPerTransCount"));
	double maxAuditMoney = Tool.strToDouble(request.getParameter("maxAuditMoney"));
	String adapterIP = request.getParameter("adapterIP");
	String adapterPort = request.getParameter("adapterPort"); 
	String className = request.getParameter("adapterClassname");
	String beginTime = request.getParameter("beginTime");
	String endTime = request.getParameter("endTime");
	int control = Tool.strToInt(request.getParameter("control"));

	String adapterClassname="//"+adapterIP+":"+adapterPort+"/"+className;

	BankValue bank = new BankValue();
	bank.bankID = bankID;
	bank.bankName = bankName;
	bank.maxAuditMoney = maxAuditMoney;
	bank.maxPerSglTransMoney = maxPerSglTransMoney;
	bank.maxPerTransMoney = maxPerTransMoney;
	bank.maxPerTransCount = maxPerTransCount;
	bank.adapterClassname = adapterClassname;
	bank.validFlag = 0;
	bank.beginTime = beginTime;
	bank.endTime = endTime;
	bank.control = control;

	int result = 0;
	try
	{
		dao.addBank(bank);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		result = -1;
	}
	lv.setLogcontent(result==0?"添加银行成功，银行代码："+bankID+"时间："+Tool.fmtTime(new java.util.Date()):"添加银行失败，银行代码："+bankID+"时间："+Tool.fmtTime(new java.util.Date()));
	dao.log(lv);
	if(result == 0)
	{
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("银行添加成功！");
			//window.opener.location.reload();
			window.returnValue="1";
			window.close();
			//-->
			</SCRIPT>	
		<%
		return;
	}
	else
	{
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("银行添加失败！");
			//-->
			</SCRIPT>	
		<%
	}
}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>新增银行</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="45%">
			<legend>新增银行</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">银行代码：&nbsp;</td>
					<td>
						<input name="bankID" type="text" class="text" style="width: 140px;" reqfv = "required;银行代码"  onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">银行名称：&nbsp;</td>
					<td>
						<input name="bankName" type="text" class="text" style="width: 140px;" reqfv = "required;银行名称" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">单笔最大转账金额：&nbsp;</td>
					<td>
						<input name="maxPerSglTransMoney" type="text" class="text" maxlength="11" style="width: 140px;" reqfv = "REQ_MLT;1;1;0;0;单笔最大转账金额" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">每日最大转账次数：&nbsp;</td>
					<td>
						<input name="maxPerTransCount" type="text" class="text" maxlength="3" style="width: 140px;" reqfv = "REQ_MLT;1;1;0;0;每日最大转账次数" maxlength="10" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">每日最大转账金额：&nbsp;</td>
					<td>
						<input name="maxPerTransMoney" type="text" class="text" maxlength="11" style="width: 140px;" reqfv = "REQ_MLT;1;1;0;0;每日最大转账金额" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">出金审核额度：&nbsp;</td>
					<td>
						<input name="maxAuditMoney" type="text" class="text" maxlength="11" style="width: 140px;" reqfv = "REQ_MLT;1;1;0;0;出金审核额度" maxlength="10" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">适配器服务地址：&nbsp;</td>
					<td>
						<input name="adapterIP" type="text" class="text" style="width: 140px;" reqfv = "required;适配器服务地址" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">适配器服务端口：&nbsp;</td>
					<td>
						<input name="adapterPort" type="text" class="text" style="width: 140px;" reqfv = "required;适配器服务端口" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">适配器实现类名称：&nbsp;</td>
					<td>
						<input name="adapterClassname" type="text" class="text" style="width: 140px;" reqfv = "required;适配器实现类名称" onkeydown="if(event.keyCode==13)event.keyCode=9"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">转账限制&nbsp;</td>
					<td>
						<select name="control">
							<option value="0">日期时间双限制</option>
							<option value="2">只受日期限制</option>
							<option value="3">只受时间限制</option>
							<option value="1">不限制</option>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">转账开始时间&nbsp;</td>
					<td>
						<input name="bhour" type="text" class="text" maxlength="2" style="width: 30px;">时
						<input name="bminite" type="text" class="text" maxlength="2" style="width: 30px;">分
						<input name="bsecond" type="text" class="text" maxlength="2" style="width: 30px;">秒
						<input name="beginTime" type="hidden" value="">
					</td>
				</tr>
				<tr height="35">
					<td align="right">转账结束时间&nbsp;</td>
					<td>
						<input name="ehour" type="text" class="text" maxlength="2" style="width: 30px;">时
						<input name="eminite" type="text" class="text" maxlength="2" style="width: 30px;">分
						<input name="esecond" type="text" class="text" maxlength="2" style="width: 30px;">秒
						<input name="endTime" type="hidden" value="">
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">添加</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">取消</button>&nbsp;
						<input type=hidden name=submitFlag value="">
					</td>
				</tr>
			</table>
		</fieldset>	  
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
		function doAdd()
		{
			var bhour = frm.bhour.value;
			var bminite = frm.bminite.value;
			var bsecond = frm.bsecond.value;
			var ehour = frm.ehour.value;
			var eminite = frm.eminite.value;
			var esecond = frm.esecond.value;
			bhour = getNum(bhour,"00");
			bminite = getNum(bminite,"00");
			bsecond = getNum(bsecond,"00");
			ehour = getNum(ehour,"23");
			eminite = getNum(eminite,"59");
			esecond = getNum(esecond,"59");
			frm.beginTime.value=bhour+":"+bminite+":"+bsecond;
			frm.endTime.value=ehour+":"+eminite+":"+esecond;
			if(checkValue("frm"))
			{
				frm.submitFlag.value = "do";
				frm.submit();
			}
		}
		//判断是否为数字
		function getNum(text,defaul){
			var param=/^[0-9]{1,2}$/;
			var param2=/^[0-9]{1}$/;
			if(!param.exec(text)){
				text = defaul;
			}else if(param2.exec(text)){
				text = "0" + text;
			}
			return text;
		}
//-->
</SCRIPT>