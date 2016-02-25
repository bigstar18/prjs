<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>


<%!


	
public String disDouble2(double f)
{
	String result = "0.00";
	try
	{		
		DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
		nf.applyPattern("###0.00");
		result = nf.format(f); 
	}
	catch(Exception e)
	{
	}	
	return result;

}

public String disDouble4(double f)
{
	String result = "0.0000";
	try
	{		
		DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
		nf.applyPattern("###0.0000");
		result = nf.format(f); 
	}
	catch(Exception e)
	{
	}	
	return result;

}

public String disDoubleSpe(double f)
{
	if(f < 0) f = 0;
	String result = "0.00";
	try
	{		
		DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
		nf.applyPattern("###0.00");
		result = nf.format(f); 
	}
	catch(Exception e)
	{
	}	
	return result;

}

public String disTime(Timestamp ts)
{
	try
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return sdf.format(ts);
	}
	catch(Exception e)
	{
	}	
	return "";

}

public String disDate1(Timestamp ts)
{
	try
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		return sdf.format(ts);
	}
	catch(Exception e)
	{
	}	
	return "";

}

/*
*根据合同的业务代码统计流水金额
*/
public double getMoney(int operation,long contractid,String userid)
{
	System.out.println("=========================================================================");
	System.out.println("交易商 "+userid +" 科目是 "+operation);
	double money = 0;
	try
	{
		DeliveryAction delivery = (DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();		
		MoneyVO[] mvos = delivery.getMoneyList("where operation="+operation+" and contractno="+contractid+" and FirmID='"+userid+"'");
		if(mvos != null)
		{
			  for(int i=0;i<mvos.length;i++)
			  {
				  money += mvos[i].money;
				  System.out.println("mvos["+i+"]  is "+mvos[i].iD+"   科目号为  "+mvos[i].operation+"   合同号为 "+mvos[i].contractNo+"   金额为  "+mvos[i].money);
			  }
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
	System.out.println();
	System.out.println();
	return money;
}
%>


<%
//获得合同号
long contractid = Long.parseLong(request.getParameter("contractID"));

//数量单位
String str6 = delNull(request.getParameter("str6"));

//获得交收管理对象
DeliveryAction delivery = (DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();

//取得历史合同对象
BargainVO bvo = delivery.getHisBargain(contractid);

System.out.println(">>>>>>>>>>>>>>>>>"+bvo.actualAmount);

//取得商品对象
CommodityVO cvo = delivery.getCommodity(bvo.commodityID);

//取得合同的已缴货款总额
double totalPayForGoods = delivery.getPaidMoneyForContract(contractid);
System.out.println("totalPayForGoods is "+totalPayForGoods);

//取得买方代码
String buyerid = delivery.getBuyerID(contractid);

//取得卖方代码
String sellerid = delivery.getSellerID(contractid);

//取得买方交易商对象
TradeUserVO tvo = delivery.getTradeUser(buyerid);
%>
<%
try
{
	String pd = TradeDAOFactory.getDAO().getPD();
%>

<html>
  <head>
	<title class="Noprint">合同明细</title>
</head>

<body>
<fieldset width="100%">
	<legend>合同明细</legend>
	<BR>
	<span>		
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			   <tr height="25">
					<td align="right" width=15%> 交易模式 ：</td>
					<td align="left" width=15%>&nbsp;<%
					if(cvo.tradeMode == 0)
					{
						out.println("竞买");
					}
					else if(cvo.tradeMode == 1)
					{
						out.println("竞卖");
					}
					else if(cvo.tradeMode == 2)
					{
						out.println("招标");
					}
					%>
					</td>
					<td align="right" width=15%> 合同状态 ：</td>
					<td align="left" width=15%>&nbsp;<%
					if(bvo.status == 0)
					{
						out.println("履约中");
					}
					else if(bvo.status == 1)
					{
						out.println("提货完毕");
					}
					else if(bvo.status == 2)
					{
						out.println("归档");
					}
					%>
					</td>
					<td align="right" width=15%> 执行结果 ：</td>
					<td align="left" width=15%>&nbsp;<%
					if(bvo.result == 0)
					{
						out.println("正常履约");
					}
					else if(bvo.result == 1)
					{
						out.println("买方违约");
					}
					else if(bvo.result == 2)
					{
						out.println("卖方违约");
					}
					%>
					</td>
			   </tr>  
			   <tr height="25">
					<td align="right"> 合同号 ：</td>
					<td align="left" >&nbsp;<%=contractid%></td>
					<td align="right"> 标的号 ：</td>
					<td align="left" >&nbsp;<%=bvo.code%></td>
					<td align="right">&nbsp;</td>
					<td align="left" >&nbsp;</td>
			   </tr>
			   <tr height="25">
					<td align="right"> 买方代码 ：</td>
					<td align="left" >&nbsp;<%=buyerid%></td>
					<td align="right"> 卖方代码 ：</td>
					<td align="left" >&nbsp;<%=sellerid%></td>
					<td align="right">&nbsp;</td>
					<td align="left" >&nbsp;</td>
			   </tr>
			   <tr height="25">
					<td align="right"> 成交价 ：</td>
					<td align="right" >&nbsp;<%=disDouble2(bvo.price)%></td>
					<td align="right"> 成交数量(<%=str6%>) ：</td>
					<td align="right" >&nbsp;<%=disDouble4(bvo.amount*cvo.tradeUnit)%></td>
					<!--<%if(pd.equals("true")){%>
					<td align="right"> 成交数量(<%=DW%>) ：</td>
					<td align="right" >&nbsp;<%=disDouble4(bvo.amount)%></td>
					<%}else{%>
					<td align="right"> 成交数量(<%=DWS%>) ：</td>
					<td align="right" >&nbsp;<%=disDouble4(bvo.amount*cvo.tradeUnit)%></td>
					<%}%>-->
					<td align="right"> 成交金额 ：</td>
					<td align="right" >&nbsp;<%=disDouble2(Arith.mul(bvo.price,cvo.tradeUnit)*bvo.amount)%></td>
			   </tr>
			   <tr height="25">
					<td align="right" colspan=6>&nbsp;</td>
			   </tr>
			   <tr height="25">
					<td align="right"> 占用保证金 ：</td>
					<td align="right" >&nbsp;<%=disDouble2(bvo.b_bail)%></td>
					<td align="right"> 已缴手续费 ：</td>
					<td align="right" >&nbsp;<%=disDouble2(bvo.b_poundage)%></td>
					<td align="right"> 剩余保证金 ：</td>
					<td align="right" >&nbsp;<%=disDouble2(bvo.b_lastBail)%></td>
			   </tr>			   
			   <tr height="25">
					<td align="right"> 应缴总额 ：</td>
					<td align="right" >&nbsp;
					<%
					double totalMoney = 0;
					if(bvo.status == 0)//履约中
					{
						totalMoney = Arith.format(Arith.mul(bvo.price,cvo.tradeUnit)*bvo.amount + bvo.b_poundage,2);
					}
					else//合同状态确认
					{
						if(bvo.result == 0)//正常履约
						{
							totalMoney = Arith.format(Arith.mul(bvo.actualAmount,bvo.price) + bvo.b_poundage,2);
						}
						else if(bvo.result == 1)//买方违约
						{
							totalMoney = Arith.format(Arith.mul(bvo.actualAmount,bvo.price) + bvo.b_poundage + Arith.div(Arith.mul(bvo.b_bail,bvo.fellBackAmount),bvo.amount*cvo.tradeUnit),2);
						}
						else if(bvo.result == 2)//卖方违约
						{
							totalMoney = Arith.format(Arith.mul(bvo.actualAmount,bvo.price) + bvo.b_poundage - Arith.div(Arith.mul(bvo.s_bail,bvo.fellBackAmount),bvo.amount*cvo.tradeUnit),2);
						}
					}
					out.println(disDouble2(totalMoney));
					%>
					</td>
					<td align="right"> 已缴货款 ：</td>
					<td align="right" >&nbsp;<%=disDouble2(totalPayForGoods)%></td>
					<td align="right"> 已缴总额 ：</td>
					<td align="right" >&nbsp;
					<%
					double totalPaidMoney = 0;
					if(bvo.status == 0)
					{
						totalPaidMoney = bvo.b_bail+bvo.b_poundage+totalPayForGoods;
					}
					else
					{
						totalPaidMoney = totalMoney;						
					}
					out.println(disDouble2(totalPaidMoney));
					%>
					</td>
			   </tr>
			   <tr height="25">
					<td align="right"> 还应缴款 ：</td>
					<td align="right" >&nbsp;<%=disDouble2(totalMoney-totalPaidMoney)%></td>
					<td align="right"> <!--买方预收货款余额 ：--></td>
					<td align="right" >&nbsp;<font color=blue><!--<%=disDouble2(tvo.paymentForGoods)%>--></font></td>
					<td colspan=2>&nbsp;</td>
			   </tr>
		</table>	
	<BR>
	</span>
</fieldset>
<fieldset width="100%">
	<legend>货款明细</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
	  <tr height="25">
	  <td align="left" colspan=4><font color=blue>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;货款记录</font></td>
	  </tr>
	  <tr height="25">
	  <td align="center" width="35%">日期</td>
	  <td align="right" width="20%">金额</td>
	  <td align="center" width="20%">备注</td>
	  <td align="right" width="45%">对应提货数量(<%=str6%>)</td>
	  <!--<%if(pd.equals("true")){%>
	  <td align="right" width="45%">对应提货数量(<%=DW%>)</td>
	  <%}else{%>
	  <td align="right" width="45%">对应提货数量(<%=DWS%>)</td>
	  <%}%>-->
	  </tr>
	  <%
	  //实收买家货款
	  double inPayMoney = 0;

	  //总提货数量
	  double totalAmount = 0;

	  double price_tem = 0d;

	  MoneyVO[] mvos = delivery.getMoneyList("where operation=406 and note is null and contractno="+contractid+" order by id");
	  //MoneyVO[] mvos_tem = delivery.getMoneyList("where operation=504 and note is null and contractno="+contractid+" order by id");
	  if(mvos != null)
	  {
		  for(int i=0;i<mvos.length;i++)
		  {
			  inPayMoney += mvos[i].money;	
			  if(pd.equals("true"))
			  {
				price_tem = bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit);
				if(price_tem<=0)   // 保证金大于等于货款  xieying
					totalAmount +=Arith.div(mvos[i].money,bvo.price);
				else
					totalAmount += Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit))/cvo.tradeUnit;
			  }
			  else
			  {
				price_tem = bvo.price-Arith.div(bvo.b_bail,bvo.amount);
				if(price_tem<=0)  // 保证金大于等于货款  xieying
					totalAmount +=Arith.div(mvos[i].money,bvo.price);
				else
					totalAmount += Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount));
			  }
			  %>
				  <tr height="25">
				<td align="center">&nbsp;<%=disDate1(mvos[i].infoDate)%></td>
				<td align="right">&nbsp;<%=disDouble2(mvos[i].money)%></td>
				<td align="center">&nbsp;<%=delNull(mvos[i].note)%></td>
				<%if(pd.equals("true")){%>
				<td align="right">&nbsp;
				<%
					price_tem = bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit);
				if(price_tem<=0)  // 保证金大于等于货款  xieying
				 {
				%>
				<%=disDouble4(Arith.div(mvos[i].money,bvo.price))%>
				<%}else{%>
			  <%=disDouble4(Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit))/cvo.tradeUnit)%></td>
			   <%}%>
			  <%}else{%>
			    <td align="right">&nbsp;
				<%
					price_tem = bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit);
				if(price_tem<=0)  // 保证金大于等于货款  xieying
				{
				%>
				<%=disDouble4(Arith.div(mvos[i].money,bvo.price))%>
				<%}else{%>
			  <%=disDouble4(Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount*cvo.tradeUnit)))%></td>
			    <%}}%>

			  </tr>
			  <%			  	
		  }
	  }

	  mvos = delivery.getMoneyList("where operation=406 and note='bail' and contractno="+contractid+" order by id");
	  if(mvos != null)
	  {
		  double fellAmount = 0;
		  if(bvo.result == 1) fellAmount = bvo.fellBackAmount;
		  for(int i=0;i<mvos.length;i++)
		  {
			  inPayMoney += mvos[i].money;			  			  
			  System.out.println("bvo.amount is "+bvo.amount+" totalAmount is "+totalAmount);
			  %>
				  <tr height="25">
				<td align="center">&nbsp;<%=disDate1(mvos[i].infoDate)%></td>
				<td align="right">&nbsp;<%=disDouble2(mvos[i].money)%></td>
				<td align="center">&nbsp;保证金转货款</td>
				<td align="right">&nbsp;
			<%if(price_tem<=0)  // 保证金大于等于货款  xieying
			{%>	
			 <%if(pd.equals("true")){%> 
              <%=disDouble4(Arith.div(Arith.mul(bvo.b_bail,cvo.tradeUnit*(bvo.amount-totalAmount)-fellAmount),Arith.mul(bvo.price,cvo.tradeUnit)*bvo.amount))%>
			  <%=disDouble4(Arith.div(mvos[i].money,bvo.price)/cvo.tradeUnit)%>
			 <%}else{%>
			  <%=disDouble4(Arith.div(Arith.mul(bvo.b_bail,cvo.tradeUnit*(bvo.amount-totalAmount)-fellAmount),Arith.mul(bvo.price,cvo.tradeUnit)*bvo.amount))%>
			 <%}%>
			 <%}else{}%>
			  </td>
			  </tr>
			  <%
				  if(pd.equals("true"))
				  {
					 //totalAmount+=Arith.div(Arith.mul(bvo.b_bail,(bvo.amount-totalAmount)-fellAmount),Arith.mul(bvo.price,bvo.amount));
				  }
				  else
			      {
					  //totalAmount+=Arith.div(Arith.mul(bvo.b_bail,bvo.amount-totalAmount-fellAmount),Arith.mul(bvo.price,bvo.amount));
				  }
		  }
	  }

	  %>				
	</table>
	<BR>
	</span>	
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
	  <tr height="25">
	  <td align="left" colspan=4><font color=blue>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退款记录</font></td>
	  </tr>
	  <tr height="25">
	  <td align="center" width="35%">日期</td>
	  <td align="right" width="20%">金额</td>
	  <td align="center" width="20%">备注</td>
	  <td align="right" width="45%">对应提货数量(<%=str6%>)</td>	
	 <!--<%if(pd.equals("true")){%>
	  <td align="right" width="45%">对应提货数量(<%=DW%>)</td>
	  <%}else{%>
	  <td align="right" width="45%">对应提货数量(<%=DWS%>)</td>
	  <%}%>-->
	  </tr>
	  <%
	  //应退买家货款
	  double returnPayMoney = 0;
	  mvos = delivery.getMoneyList("where operation=409 and contractno="+contractid+" order by id");
	  System.out.println("----------------------- totalAmount = "+totalAmount);
	  if(mvos != null)
	  {
		  for(int i=0;i<mvos.length;i++)
		  {
			  returnPayMoney += mvos[i].money;
			  //System.out.println("totalAmount is "+totalAmount);
			//totalAmount -= Arith.div(mvos[i].money,bvo.price);
			  /*if(pd.equals("true"))
				totalAmount -= Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount))/cvo.tradeUnit;
			  else
				totalAmount -= Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount));*/
			  %>
				  <tr height="25">
					<td align="center">&nbsp;<%=disDate1(mvos[i].infoDate)%></td>
					<td align="right">&nbsp;<%=disDouble2(mvos[i].money)%></td>
					<td align="center">&nbsp;<%=delNull(mvos[i].note)%></td>
					<!--<%if(pd.equals("true")){%>
					<td align="right">&nbsp;<%=disDouble4(Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount))/cvo.tradeUnit)%></td>
					<%}else{%>
					<td align="right">&nbsp;<%=disDouble4(Arith.div(mvos[i].money,bvo.price-Arith.div(bvo.b_bail,bvo.amount)))%></td>
					<%}%>-->
					<td align="right">&nbsp;<%=disDouble4(totalAmount-bvo.actualAmount)%></td>
				  </tr> 
			  <%
		  }
	  }
	  %>	
	</table>
	<BR>
	</span>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
	  <tr height="25">
	  <td align="left" colspan=4><font color=blue>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总计</font></td>
	  </tr>
	  <tr height="25">
	  <td align="center" width="">总货款 ：</td>
	  <td align="center" width="">&nbsp;<%=disDouble2(totalPayForGoods)%></td>
	  <td align="center" width="">总提货数 ：</td>
	  <!--<td align="center" width="">&nbsp;<%=disDouble4(totalAmount)%></td>-->
	  <td align="center" width="">&nbsp;<%=disDouble4(bvo.actualAmount)%></td>
	  </tr>
	</table>
	<BR>
	</span>
</fieldset>
<%
if(bvo.status > 0)
{
	%>
<fieldset width="100%">
	<legend>交收情况</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
	  <tr height="25">
	  <%if(pd.equals("true")){%>
	  <td align="center" width="">实际交货数量 ：</td>
	  <td align="left" width="">&nbsp;<%=disDouble4(bvo.actualAmount/cvo.tradeUnit)%></td>
	  <td align="center" width="">违约数量 ：</td>
	  <td align="left" width="">&nbsp;<%=disDouble4(bvo.fellBackAmount/cvo.tradeUnit)%></td>
	  <td align="center" width="">损耗数量 ：</td>
	  <td align="left" width="">&nbsp;
	  <%=disDouble4((bvo.amount*cvo.tradeUnit-bvo.actualAmount-bvo.fellBackAmount)/cvo.tradeUnit)%>
		  </td>
	  <%}else{%>
	  <td align="center" width="">实际交货数量 ：</td>
	  <td align="left" width="">&nbsp;<%=disDouble4(bvo.actualAmount)%></td>
	  <td align="center" width="">违约数量 ：</td>
	  <td align="left" width="">&nbsp;<%=disDouble4(bvo.fellBackAmount)%></td>
	  <td align="center" width="">损耗数量 ：</td>
	  <td align="left" width="">&nbsp;
	  <%=disDouble4(bvo.amount*cvo.tradeUnit-bvo.actualAmount-bvo.fellBackAmount)%>
		  </td>
	  <%}%>
	  </tr>
	</table>
	<BR>
	</span>	
</fieldset>

<%
		  int result = bvo.result; //合同结果  0：正常履约 1：买方违约 2：卖方违约
%>

<fieldset width="100%">
	<legend>买方明细</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="50%" align="left">
	  <tr height="25">
	  <td align="right" width="50%">实收买方货款 （+）：</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(inPayMoney)%><%//if(result==1){out.println(disDouble2(inPayMoney-getMoney(515,contractid,sellerid)));}else{out.println(disDouble2(inPayMoney));}%></td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">应退买方货款 （-）：</td>
	  <td align="right" width="">&nbsp;
	  <%=disDouble2(returnPayMoney)%>
	  </td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">应扣买方保证金 （+）：</td>
	  <td align="right" width="">&nbsp;
	  <%//=disDouble2(getMoney(511,contractid,buyerid))%>
	    <%if(result==1){out.println(disDouble2(getMoney(415,contractid,sellerid)));}else{out.println(0.00);}%>
	  </td>
	  </tr>  
	  <tr height="25">
	  <td align="right" width="">应赔买方保证金 （-）：</td>
	  <td align="right" width="">
		  &nbsp;<%=disDouble2(getMoney(415,contractid,buyerid))%>
		  </td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">应收买方手续费 （+）：</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(403,contractid,buyerid))%></td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">应收买方总金额 ：</td>	
	  <td align="right" width="">&nbsp;
	  <!--<%=disDouble2(inPayMoney-returnPayMoney+getMoney(515,contractid,sellerid)-getMoney(515,contractid,buyerid)+getMoney(503,contractid,buyerid))%>-->
	  <%=disDouble2(inPayMoney-returnPayMoney+getMoney(415,contractid,sellerid)-getMoney(415,contractid,buyerid)+getMoney(403,contractid,buyerid))%></td>
	  </tr>
	</table>
	<BR>
	</span>	
</fieldset>

<fieldset width="100%">
	<legend>卖方明细</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="50%" align="left">
	  <tr height="25">
	  <td align="right" width="50%">实收货款 （+）：</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(totalPayForGoods)%><%//if(result==2){out.println(disDouble2(totalPayForGoods-getMoney(515,contractid,buyerid)));}else{out.println(disDouble2(totalPayForGoods));}%></td>
	  </tr>
	<tr height="25">
	  <td align="right" width="50%">实收卖方保证金（-）：</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(404,contractid,sellerid))%></td>
	  </tr>
	<tr height="25">
	  <td align="right" width="">应退卖方保证金（+）：</td>
	  <td align="right" width="">&nbsp;
	  <%=disDouble2(getMoney(407,contractid,sellerid))%>
	  </td>
	  </tr>
	<tr height="25">
	  <td align="right" width="">应扣卖方保证金：</td>
	  <td align="right" width="">&nbsp;
	   <%//=disDouble2(getMoney(511,contractid,buyerid))%>
	  <%if(result==2){out.println(disDouble2(getMoney(415,contractid,buyerid)));}else{out.println(0.00);}%>
	  </td>
	  </tr>
		  <tr height="25">
	  <td align="right" width="">应赔卖方保证金 （+）：</td>
	  <td align="right" width="">&nbsp;
	  <%=disDouble2(getMoney(415,contractid,sellerid))%>
	  </td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">应收卖方手续费（-）：</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(403,contractid,sellerid))%></td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">应付卖方总金额 ：</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(totalPayForGoods-getMoney(404,contractid,sellerid)+getMoney(407,contractid,sellerid)+getMoney(415,contractid,sellerid)-getMoney(403,contractid,sellerid))%></td>
	  </tr>
	</table>
	<BR>
	</span>	
</fieldset>
<fieldset width="100%">
	<legend>市场明细</legend>
	<BR>
	<span>
	<table border="0" cellspacing="0" cellpadding="0" width="50%" align="left">
	  <tr height="25">
	  <td align="right" width="50%">市场收手续费 （+）：</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(403,contractid,buyerid)+getMoney(403,contractid,sellerid))%></td>
	  </tr>
	  <tr height="25">
	  <td align="right" width="">总计 ：</td>
	  <td align="right" width="">&nbsp;<%=disDouble2(getMoney(403,contractid,buyerid)+getMoney(403,contractid,sellerid))%></td>
	  </tr>
	</table>
	<BR>
	</span>	
</fieldset>	
	<%
}
%>

<br>
<table border="0" cellspacing="0" cellpadding="0" width="100%" class="Noprint">
  <tr height="25">
	<td width="40%"><div align="center">
	  <input type="button" onclick="window.print();" class="btn" value="打印">&nbsp;&nbsp;
	  <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
	</div></td>
  </tr>
</table>
</body>
</html>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>