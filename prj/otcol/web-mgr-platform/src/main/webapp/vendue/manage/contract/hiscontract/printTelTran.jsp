<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title class="Noprint">打印电汇凭证</title>
</head>

<body>
	<form method="post" name="frm" action="printTelTranCon.jsp">
	<input type="hidden" name="commodityID" value="${param.commodityID}">
	<input type="hidden" name="marketId" value="${param.marketId}">
	<db:select var="v_result" table="v_hisbargain" columns="<%=COLS_HISBARGAIN%>" where="commodityid=${param.commodityID}">
    <input type="hidden" name="contractID" value="${v_result.contractid}">
	</db:select>
	<fieldset width="100%">
	<legend><font size="3">中国农业发展银行 电汇凭证</font></legend>
    <table align="center">
	<thead>
		<tr><th colspan="2"></th></tr>
	</thead>
	<tbody>
	<%
	    Calendar c=Calendar.getInstance();
		int y=c.get(Calendar.YEAR);
		int m=c.get(Calendar.MONTH)+1;
		int d=c.get(Calendar.DAY_OF_MONTH);
	%>
            <tr>
			<td colspan="2">
			<table align="center"><tr>
				<td>委托日期： </td>
				<td><input name="year" type="text" class="text" style="width:50;" value="<%=y%>" /></td><td>年</td>
				<td><input name="month" type="text" class="text" style="width:50;" value="<%=m%>" /></td><td>月</td>
				<td><input name="day" type="text" class="text" style="width:50;" value="<%=d%>" /></td><td>日</td>
			</tr></table>
			</td>
		</tr>
		<tr><td>
		<table width="100%" border="0" class="printTemp" cellpadding="0" cellspacing="0">
		<tr>
		<td>
		<table cellpadding="0" cellspacing="0" width="100%">
		<tr><td rowspan="4" colspan="2" class="td_PaymentMd" style="width:0.5cm;">汇款人</td></tr>
		<db:select var="v_result" table="marketallcall" columns="<%=COLS_MARKETALLCALL%>" where="marketid=${param.marketId}">
		<tr>
			<td class="td_PaymentMd" style="width:2cm;">全称：</td>
			<td class="td_PaymentMd"><input name="fromName" type="text" class="text" style="width:215;" value="${v_result.name}"/></td>
		</tr>
		<tr>
			<td class="td_PaymentMd">账号：</td>
			<td class="td_PaymentMd"><input name="fromAccount" type="text" class="text" style="width:215;" value="${v_result.account}"></td>
		</tr>
		<tr>
			<td class="td_PaymentMd">汇出地点：</td>
			<td class="td_PaymentMd" style="width:6cm;"><input name="fromProvince" type="text" class="text" style="width:80;" value="${v_result.province}"/>省
			<input name="fromCity" type="text" class="text" style="width:80;" value="${v_result.city}"/>市/县
			</td>
		</tr>
		<tr>
			<td class="td_PaymentMd" colspan="3">汇出行名称：</td>
			<td class="td_PaymentMd"><input name="fromBank" type="text" class="text" style="width:215;" value="${v_result.bank}"></td>
		</tr>
		</db:select>
		</table>
		</td>
		<td valign="top">
		<table cellpadding="0" cellspacing="0" width="100%">
		<%
			   BigDecimal price=null;
			   BigDecimal amount=null;
			   BigDecimal paidMoney=null;
			   String contractID=null;
			   String acceptName=null;
			   String acceptAccount=null;
			   String acceptPro=null;
			   String acceptCity=null;
			   String acceptBank=null;
			   String code=null;
			   String remark=null;
			   DBBean bean=null;
			   ResultSet rs=null;
			   try{
				   String commodityID=request.getParameter("commodityID");
			       bean=new DBBean(JNDI);
				   StringBuffer sql=new StringBuffer();
				   sql.append("select contractid,price,amount,code from v_hisbargain where commodityid="+commodityID+"");
				   rs=bean.executeQuery(sql.toString());
				   if(rs.next()){
				       price=rs.getBigDecimal("price");
					   amount=rs.getBigDecimal("amount");
					   contractID=rs.getString("contractid");
					   code=rs.getString("code");
				   }
				   rs.close();
				   bean.closeStmt();
				   price=ManaUtil.disBD(price);
				   amount=ManaUtil.disBD(amount);
				   sql=new StringBuffer();
	               int partitionid=0;
	               sql.append("select partitionid from v_syspartition where rownum=1 and validflag=1");
	               rs=bean.executeQuery(sql.toString());
				   if(rs.next()){
	                   partitionid=rs.getInt("partitionid");
	               }
                   rs.close();
				   bean.closeStmt();
				   KernelEngineDAO dao = GlobalContainer.getEngineDAO(partitionid);
				   paidMoney=new BigDecimal(dao.getPaymentToSeller(Long.parseLong(contractID)));
				   sql=new StringBuffer();
				   sql.append("select * from acceptpayment where contractid="+contractID+" and status=1");
				   rs=bean.executeQuery(sql.toString());
				   if(rs.next()){
				       acceptName=rs.getString("name");
					   acceptAccount=rs.getString("account");
					   acceptPro=rs.getString("province");
					   acceptCity=rs.getString("city");
					   acceptBank=rs.getString("bank");
					   remark=rs.getString("remark");
				   }
				   rs.close();
				   bean.close();
			   }catch(SQLException se){
			       se.printStackTrace();
			   }
			   catch(Exception e){
			       e.printStackTrace();
			   }
			   finally{
                   try{if(rs!=null)rs.close();}catch(Exception ex){}
                   if(bean!=null)bean.close();
               }
		%>
		<tr><td rowspan="4" class="td_PaymentMd" style="width:0.5cm;">收款人</td></tr>		
		<tr>
			<td class="td_PaymentMd" style="width:2cm;">全称：</td>
			<td class="td_PaymentRd"><input name="toName" type="text" class="text" style="width:215;" value="<%=delNull(acceptName)%>"/></td>
		</tr>
		<tr>
			<td class="td_PaymentMd">账号：</td>
			<td class="td_printRd"><input name="toAccount" type="text" class="text" style="width:215;" value="<%=delNull(acceptAccount)%>"></td>
		</tr>
		<tr>
			<td class="td_PaymentMd">汇入地点：</td>
			<td style="width:6cm;" class="td_PaymentRd"><input name="toProvince" type="text" class="text" style="width:80;" value="<%=delNull(acceptPro)%>"/>省
			<input name="toCity" type="text" class="text" style="width:80;" value="<%=delNull(acceptCity)%>"/>市/县
			</td>
		</tr>
		<tr>
			<td colspan="2" class="td_PaymentMd">汇入行名称：</td>
			<td class="td_PaymentRd"><input name="toBank" type="text" class="text" style="width:215;" value="<%=delNull(acceptBank)%>"></td>
		</tr>
		</table>
		</td>
		</tr>
		<tr><td colspan="2">
		<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
		<td colspan="2">
					<tr>
					<td></td>
					<td class="td_PaymentMd" style="width:2.5cm;">金额：</td>
					<td align="left" class="td_PaymentRd" style="text-align:left;">&nbsp;<input name="amount" type="text" class="text" style="width:215;" value="<fmt:formatNumber value="<%=paidMoney.toString()%>" pattern="<%=FUNDPATTERN%>"/>" readonly/>
		</td>
		</tr>
		</table>
        </td>
		</tr>
		<tr>
		  
		  <td valign="top">
		  <table cellpadding="0" cellspacing="0" width="100%">
		  <tr>
		  <td class="td_PaymentBd" style="width:2.55cm;">附加信息：</td>
		  <td align="center" style="text-align:left;">&nbsp;<input name="comments" type="text" class="text" style="width:215;" value="<%if(remark==null){%>标的号<%=code%>粮款<%}else{%><%=remark%><%}%>"/></td>
		  </tr>
		  </table>
		  </td>
		</tr>
		</table>
		<tr>
			<td colspan="2" align="center">
			<table>
				<tr><td align="right"><input type="button" onclick="return print();" class="btn" value="打印"/></td><td width="10"></td><td align="left"><input type="reset" class="btn" value="重填"/></td></tr>
			</table>
			</td>
		</tr>
		<!--
        <tr>
			<td colspan="2">
			<table>
				<tr>
					<td>原点坐标移动</td><td colspan="2"></td>
				</tr>
				<tr>
					<td>水平移动：</td><td><input name="delta_x" type="text" class="text" style="width:50;" value="0.00"/></td><td>毫米</td>
                                </tr><tr>
					<td>竖直移动：</td><td><input name="delta_y" type="text" class="text" style="width:50;" value="0.00"/></td><td>毫米</td>
				</tr>
			</table>
			</td>		
		</tr>-->
		<input name="delta_x" type="hidden" value="<%=tranPayX%>"/>
		<input name="delta_y" type="hidden" value="<%=tranPayY%>"/>
	</tbody>
    </table>
	</fieldset>
	<input type="hidden" name="opt">
	</form>
</body>
</html>
<script language="javascript">
    function print(){
		if(Trim(frm.fromName.value) == "")
	    {
		    alert("汇款人全称不能为空");
		    frm.fromName.focus();
		    return false;
	    }else if(Trim(frm.fromAccount.value) == "")
	    {
		    alert("汇款人帐号不能为空");
		    frm.fromAccount.focus();
		    return false;
	    }else if(Trim(frm.fromProvince.value) == "")
	    {
		    alert("汇出地点(省份)不能为空！");
		    frm.fromProvince.focus();
		    return false;
	    }else if(Trim(frm.fromCity.value) == "")
	    {
		    alert("汇出地点(市/县)不能为空！");
		    frm.fromCity.focus();
		    return false;
	    }
		else if(Trim(frm.fromBank.value) == "")
	    {
		    alert("汇出银行不能为空！");
		    frm.fromBank.focus();
		    return false;
	    }
	    else if(Trim(frm.toName.value) == "")
	    {
		    alert("收款人全称不能为空");
		    frm.toName.focus();
		    return false;
	    }else if(Trim(frm.toAccount.value) == "")
	    {
		    alert("收款人帐号不能为空");
		    frm.toAccount.focus();
		    return false;
	    }else if(Trim(frm.toProvince.value) == "")
	    {
		    alert("汇入地点(省份)不能为空！");
		    frm.toProvince.focus();
		    return false;
	    }else if(Trim(frm.toCity.value) == "")
	    {
		    alert("汇入地点(市/县)不能为空！");
		    frm.toCity.focus();
		    return false;
	    }
		else if(Trim(frm.toBank.value) == "")
	    {
		    alert("汇入银行不能为空！");
		    frm.toBank.focus();
		    return false;
	    }
		else if(Trim(frm.amount.value) == "")
	    {
		    alert("金额不能为空！");
		    frm.amount.focus();
		    return false;
	    }
		else{
            if(userConfirm()){
				window.open("","win","left=2000,top=2000,fullscreen=3")
			    frm.opt.value="print";
				frm.target="win";
			    frm.submit();
	        }else{
			    return false;
	        }
		}
  }
</script>
