<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<%
try
{

%>

<html>
<head>
  <title class="Noprint">提货记录</title>
</head>
<c:if test="${not empty param.opt}">
  
</c:if>
<body>
<form name=frm id=frm action="">
		<fieldset width="100%">
		<legend>合同信息</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
	    <!--已交货款-->
		<c:set var="payment" value=""/>
		<!--成交数量-->
		<c:set var="amount" value=""/>
		<!--成交价格-->
		<c:set var="price" value=""/>
		<!--商品号-->
		<c:set var="commodityID" value=""/>
		<!--剩余货量-->
		<c:set var="lastAmount" value=""/>
		<!--保证金-->
		<c:set var="security" value=""/>
		<db:select_HisBarDetail var="row" where="u1.commodityid=${param.commodityID} and u1.commodityid=u3.id and u3.id=u4.commid and u2.firmid=u1.userid">
		<input type="hidden" name="this_tradeunit" value="${row.tradeunit}">
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  	<c:set var="contractID" value="${row.contractid}"/>
			<c:set var="this_tradeunit" value="${row.tradeunit}"/>
		  	<c:set var="amount" value="${row.amount}"/>
		  	<c:set var="price" value="${row.price}"/>
		  	<c:set var="commodityID" value="${row.id}"/>
		  	<c:set var="lastAmount" value="${row.lastAmount}"/>
		  	<c:set var="security" value="${row.bail}"/>
			<input type="hidden" name="commodityID" value="${row.id}">
        <tr height="25">
                <td align="right" width="20%"> 标的号 ：</td>
                <td align="left" width="20%">
                	${row.code}
                </td>
                <td align="right" width="20%"> 合同号 ：</td>
                <td align="left" width="20%">
                	${row.contractid}
                </td>
                <db:selectUnion_daily var="rowColumn" contractid="${row.contractid}" operation="${OPERATION}">
              	 <c:choose>
              	 	 <c:when test="${not empty rowColumn.m}">
              	     <c:set var="payment" value="${rowColumn.m}"/>
              	   </c:when>
              	   <c:otherwise>
              	   	  <c:set var="payment" value="0"/>
              	   </c:otherwise>
              	  </c:choose> 
              </db:selectUnion_daily>
        </tr>
		<tr height="25">
                <td align="right" width="20%"> 买方编号 ：</td>
                <td align="left" width="20%">
				<c:choose>
				<c:when test="${row.trademode==0}">
                ${row.userid}
				</c:when>
				<c:when test="${row.trademode==1}">
				${row.bluserid}  
				</c:when>
				<c:when test="${row.trademode==2}">
				${row.bluserid}  
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>
                </td>
                <td align="right" width="20%">卖方编号：</td>
				<td align="left" width="20%">
				<c:choose>
				<c:when test="${row.trademode==0}">
                ${row.bluserid}
				</c:when>
				<c:when test="${row.trademode==1}">
				${row.userid}  
				</c:when>
				<c:when test="${row.trademode==2}">
				${row.userid}  
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>
				</td>
        </tr>
		<tr height="25">
                <td align="right" width="20%"> 交易模式 ：</td>
                <td align="left" width="20%">
                <c:choose>
				<c:when test="${row.trademode==0}">
                竞买
				</c:when>
				<c:when test="${row.trademode==1}">
				竞卖 
				</c:when>
				<c:when test="${row.trademode==2}">
				招标
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>	
                </td>
                <td align="right" width="20%"> 成交价 ：</td>
                <td align="left" width="20%">
                ${row.price}
                </td>
		</tr>
		<tr height="25">
				<td align="right" width="20%"> 数量(${param.str6}) ：</td>
                <td align="left" width="20%">
                	${row.amount*row.tradeunit}
                </td>
                <td align="right" width="20%"> 成交金额 ：</td>
                <td align="left" width="20%">
                	<fmt:formatNumber value="${row.amount*row.price*row.tradeunit}" pattern="<%=FUNDPATTERN%>"/>
                </td>
		 </tr>
        </table>	
    </db:select_HisBarDetail>
    <%
      double tempPayment=0d;//已交货款
      long tempAmount=0l;//成交数量
      long tempLastAmount=0l;//剩余货量
      double tempPrice=0d;//成交价格
      double tempSecurity=0d;//保证金
	  double curAmount=0d;//本次提货有效货款数
      long listGoodsTotal=0;//本次允许最大提货数量
      if(pageContext.getAttribute("payment")!=null&&!"".equals(pageContext.getAttribute("payment"))){
        tempPayment=Double.parseDouble(pageContext.getAttribute("payment").toString());
      }
      if(pageContext.getAttribute("amount")!=null&&!"".equals(pageContext.getAttribute("amount"))){
        tempAmount=Long.parseLong(pageContext.getAttribute("amount").toString());
      }
      if(pageContext.getAttribute("lastAmount")!=null&&!"".equals(pageContext.getAttribute("lastAmount"))){
        tempLastAmount=Long.parseLong(pageContext.getAttribute("lastAmount").toString());
      }
      if(pageContext.getAttribute("price")!=null&&!"".equals(pageContext.getAttribute("price"))){
        tempPrice=Double.parseDouble(pageContext.getAttribute("price").toString());
      }
      if(pageContext.getAttribute("security")!=null&&!"".equals(pageContext.getAttribute("security"))){
        tempSecurity=Double.parseDouble(pageContext.getAttribute("security").toString());
      }
      curAmount=tempPayment-(tempAmount-tempLastAmount)*tempPrice;
      listGoodsTotal=(long)(curAmount/(tempPrice-tempSecurity/tempAmount));      
      if(listGoodsTotal<0){
        listGoodsTotal=0;
      }
    %>
	    <BR>
        </span>
		</fieldset>
		<fieldset width="100%">
		<legend>提货记录</legend>
		<BR>
		<span>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
          <tHead>
		  <tr height="25">
		  <td>&nbsp;</td>
		  <td align="center">&nbsp;</td>
          <td align="center">日期</td>
		  <td align="center" width="20%">对应提货数量(${param.str6})</td>
          <td align="center">金额</td>
          <td align="center">出库单</td>
          <td align="center">状态</td>
          </tr>
		  </tHead>
		  <tBody>
          <db:select var="row" table="v_outlog" columns="id,outid,outdate,capital,amount,remark,finished" orderBy="outdate desc" where="contractid=${contractID}">
          	 <tr height="25">
			    <td>&nbsp;</td>
			    <td align="center"><input name="ck" type="checkbox" value='${row.id}'></td>
          	 	<td align="center"><fmt:formatDate value="${row.outdate}" pattern="<%=DATEPATTERN%>"/></td>
          	    <td align="center" style="text-align:right;"><fmt:formatNumber value="${row.amount}" pattern="<%=FUNDPATTERNEXT%>"/>
				<input type="hidden" name="tempLiftAmount" value="${row.amount}">
				<td align="right"><fmt:formatNumber value="${row.capital}" pattern="<%=FUNDPATTERN%>"/></td>
          	    <td align="center"><a href="javascript:viewRecord('${commodityID}','${row.outid}','${row.capital}','${row.amount}','${row.finished}','${row.id}','${param.str6}')" class="normal">查看</a></td>
          	    <td align="center"><c:if test="${row.finished==2}">已完毕</c:if>
				<c:if test="${row.finished==1}">已生成出库单</c:if>
          	    <c:if test="${row.finished==0}">未生成出库单</c:if>
          	    </td>
          	 </tr>	
          </db:select>
		  </tBody>
     </table>
     <BR>
     </span>
     </fieldset>
	 <br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%" class="Noprint">
        <tr height="25">
          <td width="40%"><div align="center">
          <input type="hidden" name="opt">
		  <input type="button" onclick="window.print();" class="btn" value="打印">&nbsp;&nbsp;
		  <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
          </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
//添加货物
function addGoodsView(contractID,price,listGoodsTotal,tempSecurity,tempAmount,str6){
  var tempLiftAmount=frm.tempLiftAmount;
  var liftedAmount=0;
  var mostLiftAmount=0;
  if(!tempLiftAmount){
    liftedAmount=0;
  }else{
    if(tempLiftAmount.length>1){
      for(i=0;i<tempLiftAmount.length;i++){
	    liftedAmount=liftedAmount+parseInt(tempLiftAmount[i].value);
	  }
    }else{
      liftedAmount=parseInt(tempLiftAmount.value);
    }
   }
  mostLiftAmount=parseInt(listGoodsTotal)-liftedAmount;
  if(mostLiftAmount<=0){
    alert("本次允许最大提货值为0,不能添加货物记录!");
	return false;
  }else{
var a=openDialog("addGoods.jsp?contractID="+contractID+"&price="+price+"&listGoodsTotal="+mostLiftAmount+"&security="+tempSecurity+"&amount="+tempAmount+"&str6="+str6,"_blank","600","450");
  if(1==a)
    window.location.reload();
  }
}

//查看出库单信息
function viewRecord(commodityID,outID,capital,amountTotal,finished,outlogId,str6){
  //var a=openDialog("retFileAddRecord.jsp?commodityID="+commodityID+"&outID="+outID+"&capital="+capital+"&amountTotal="+amountTotal+"&id="+id+"&finished="+finished+"","_blank","600","550");
  //window.open("retFileAddRecord.jsp?commodityID="+commodityID+"&outID="+outID+"&capital="+capital+"&amountTotal="+amountTotal+"&id="+id+"&finished="+finished+"","_blank","width=700,height=550,scrollbars=yes");
 var this_tradeunit=frm.this_tradeunit.value; result=PopWindow("retFileAddRecord.jsp?commodityID="+commodityID+"&outID="+outID+"&capital="+capital+"&amountTotal="+amountTotal+"&finished="+finished+"&outlogId="+outlogId+"&this_tradeunit="+this_tradeunit+"&str6="+str6,700,550);
  //  if(1==a)
  //    window.location.reload();	
}

//-->
<!--
function refreshParent() {
	window.opener.location.href = window.opener.location.href;
	if (window.opener.progressWindow) window.opener.progressWindow.close();
	window.close();
	}
//-->
</SCRIPT>
<%@ include file="/vendue/manage/public/footInc.jsp" %>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>