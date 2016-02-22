<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlapay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
	String TLAPayBankID = "027";
	String firmID = request.getParameter("firmID");
	CorrespondValue corr = new CorrespondValue();
	TLAPayBankDAO dao = null;
	try{
		dao =BankDAOFactory.getTLAPayDAO();
		Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "'  ");
		if(vc != null && vc.size() > 0){
			corr = vc.get(0);
		}
	}catch(ClassNotFoundException e){
		e.printStackTrace();
		System.out.println(Tool.getExceptionTrace(e));
	}catch(IllegalAccessException e){
		e.printStackTrace();
		System.out.println(Tool.getExceptionTrace(e));
	}catch(InstantiationException e){
		e.printStackTrace();
		System.out.println(Tool.getExceptionTrace(e));
	}catch(Exception e){
		e.printStackTrace();
		System.out.println(Tool.getExceptionTrace(e));
	}
	
	
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>签约交易商银行帐号</title>
	<script language="javascript">
	var req;
	function Change_Select(){//当第一个下拉框的选项发生改变时调用该函数
      var OpenBankCodeText = document.getElementById('OpenBankCodeText').value;
      
	  var url = "./bankCodeAjax.jsp?OpenBankCodeText=" + OpenBankCodeText;
	  if(window.XMLHttpRequest){
	  	req = new XMLHttpRequest();
	  }else if(window.ActiveXObject){
	  	req = new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  if(req){
	  	req.open("GET",url,true);         
	  	req.onreadystatechange = callback;//指定回调函数为callback
	  	req.send(null);
	  }
    }
    //回调函数
    function callback(){
      if(req.readyState ==4){
        if(req.status ==200){
          parseMessage();//解析XML文档
        }else{
          alert("不能得到描述信息:" + req.statusText);
        }
      }
    }
    //解析返回xml的方法
    function parseMessage(){	  
		var res = req.responseText;
		if(!(res.indexOf('select')==-1)){//取得数据时
	     	document.getElementById("OpenBankCode").innerHTML = res;
	    }else{//没有取得数据时
	     	alert("取得对应开户行失败");
			return;
	    }
    }
	function init(){
		document.getElementById("OpenBankCode").innerHTML = "<select style='width: 140px'><option value='0' selected>--请选择--</option></select>";
	}
  </script>
	
  </head>
  
  <body onload="init()">
  	<form id="frm" action="openAccount2.jsp" method="post">
	<input type="hidden" name="firmID" value="<%=firmID%>">
		<fieldset width="95%">
			<legend>绑定交易商账户和通联账户支付系统</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易账号：&nbsp;</td>
					<td align="left">
						<input name="contact" value="<%=corr.contact%>"  readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">签约银行：&nbsp;</td>
					<td align="left">
						<input name="bankID" value="通联账户支付" readonly disabled='disabled' type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">银行帐号：&nbsp;</td>
					<td align="left">
						<input name="account"   type=text class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">账户名称：&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" id="bankNameFlag">
					<td align="right">银行名称：&nbsp;</td>
					<td align="left">
						<input type="text" name="bankName" id="bankName"  class="text"  maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" id="OpenBankCodeFlag">
					<td align="right">支付行号：&nbsp;</td>
					<td align="left">
						<input type="text" name="OpenBankCodeText" id="OpenBankCodeText" onBlur="Change_Select()"  class="text"  maxlength="30" style="width: 140px"><span class=star>*</span>
						<div id="OpenBankCode"></div>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">请选择</option>
							<option value="1" <%=(corr.cardType!=null && corr.cardType.equals("1")) ? "selected" : ""%>>身份证</option>
							<option value="8" <%=(corr.cardType!=null && corr.cardType.equals("8")) ? "selected" : ""%>>组织机构代码</option>
							<option value="9" <%=(corr.cardType!=null && corr.cardType.equals("9")) ? "selected" : ""%>>营业执照</option>
							<option value="a" <%=(corr.cardType!=null && corr.cardType.equals("a")) ? "selected" : ""%>>法人代码证</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input name="card" value="<%=Tool.delNull(corr.card)%>"  type=text  class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">签约</button>&nbsp;
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
	if(trim(frm.account.value) == "")
	{
		alert("请输入银行账户");
		frm.account.focus();
	}
	else  if(trim(frm.accountName.value) == "")
	{
		alert("请输入账户名称");
		frm.accountName.focus();
	}
	else if(trim(frm.bankName.value)=="")
	{
		alert("请输入开户行名称信息！");
		frm.bankName.focus();
	}
	else if(trim(frm.OpenBankCode.value)=="")
	{
		alert("请输入开户行行号！");
		frm.OpenBankCode.focus();
	}
	else if(trim(frm.cardType.value) == "")
	{
		alert("请选择证件类型");
		frm.cardType.focus();
	}
	else if(trim(frm.card.value) == "")
	{
		alert("请输入证件号码");
		frm.card.focus();
	}
	else
	{
		
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>