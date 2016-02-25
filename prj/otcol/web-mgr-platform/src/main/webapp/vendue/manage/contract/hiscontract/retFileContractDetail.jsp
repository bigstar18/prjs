<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<html>
<head>
  <title class="Noprint">合同明细</title>
</head>
<c:if test='${not empty param.add}'>
<c:if test="${param.add=='controlStatus'}">
  <!--是否可以设置状态标志-->
  <c:set var="modFlag" value="true"/>
  <!--判断出库记录是否都为完毕状态-->
  <db:select var="row" table="v_outlog" columns="contractid" where="contractid=${param.contractId} and finished<>1">
     <c:set var="modFlag" value="false"/>
  </db:select>
  <c:choose>
    <c:when test="${modFlag=='true'}">
	  <db:update table="v_hisbargain" status="${param.finished}" where="commodityid=${param.commodityID}"/>
      <SCRIPT LANGUAGE="JavaScript">
         <!--
           alert("设置合同状态成功！");
         //-->
      </SCRIPT>
	</c:when>
	 <c:otherwise>
       <SCRIPT LANGUAGE="JavaScript">
         <!--
           alert("合同对应的出库记录是未完毕状态,设置状态失败！");
         //-->
       </SCRIPT>
	 </c:otherwise>
    </c:choose>
</c:if>
</c:if>
<body>
<form name=frm id=frm action="">
		<fieldset width="100%">
		<legend>合同明细</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
		<c:set var="payment" value=""/>
		<!--单价-->
		<c:set var="singlePrice" value=""/>
        <!--成交量-->
		<c:set var="amount" value=""/>
		<!--剩余货量-->
		<c:set var="lastAmount" value=""/>
		<!--保证金-->
		<c:set var="security" value=""/>
		<c:set var="contractNO" value=""/>
		<!--合同状态-->
		<c:set var="status" value=""/>
		<!--系统日期-->
        <c:set var="sysDate" value="<%=sqlDate%>"/>
		<db:select_HisBarDetail var="row" where="u1.commodityid=${param.commodityID} and u1.userid=u2.usercode and u1.commodityid=u3.id and u3.id=u4.commid">
		  <table border="0" cellspacing="0" cellpadding="0" width="100%">
		  	<c:set var="contractID" value="${row.contractid}"/>
			<c:set var="lastAmount" value="${row.lastAmount}"/>
		  	<c:set var="security" value="${row.bail}"/>
			<c:set var="amount" value="${row.amount}"/>
			<c:set var="status" value="${row.status}"/>
			<input type="hidden" name="commodityID" value="${param.commodityID}">
			<input type="hidden" name="contractId" value="${row.contractid}">
				<tr height="25">
                <td align="right"> 交易模式 ：</td>
                <td align="left">
                	<c:if test="${row.trademode==1}">竞买</c:if>
                	<c:if test="${row.trademode==2}">竞卖</c:if>
					<c:if test="${row.trademode==3}">招标</c:if>
                </td>
				<td align="right"> 合同状态 ：</td>
                <td align="left">
                	<c:if test="${row.status==0}">有效
					<db:select table="v_commext" var="colRow" columns="commid" where="commid=${param.commodityID} and to_date(str19,'yyyy-mm-dd')<to_date('${sysDate}','yyyy-mm-dd')">
                    (已到期)
					</db:select>
					</c:if>
                	<c:if test="${row.status==1}">履约完成</c:if>
					<c:if test="${row.status==2}">废除</c:if>
					<c:if test="${row.status==3}">买方履约</c:if>
					<c:if test="${row.status==4}">买方履约</c:if>
                </td>
				<td colspan="2">&nbsp;</td>
                </tr>
        <tr height="25">
                <td align="right"> 标的号 ：</td>
                <td align="left">
                	${row.code}
                </td>
                <td align="right"> 合同号 ：</td>
                <td align="left">
                	${row.contractid}
					<c:set var="contractNO" value="${row.contractid}"/>
                </td>
                <td align="right"> 品名 ：</td>
                <td align="left">${row.str1}</td>
        </tr>
			  <tr height="25">
              <td align="right"> 交易会员号 ：</td>
              <td align="left">${row.usercode}</td>
              <td align="right"> 买方全称 ：</td>
              <td align="left">${row.name}</td>
        	  <td align="right"> 卖方全称 ：</td>
              <td align="left">${row.str2}</td>
        </tr>
        <tr height="25">
              <td align="right"> 单价 ：</td>
              <td align="left">
			  <c:set var="singlePrice" value="${row.price}"/>
			  <fmt:formatNumber value="${row.price}" pattern="<%=FUNDPATTERN%>"/></td>
              <td align="right"> 数量 ：</td>
              <td align="left">${row.amount}</td>
              <td align="right"> 成交金额 ：</td>
              <td align="left"><fmt:formatNumber value="${row.amount*row.price*row.tradeunit}" pattern="<%=FUNDPATTERN%>"/></td>
         </tr>
		 <tr height="25">
           <td align="right"> 初始保证金 ：</td>
              <td align="left"><fmt:formatNumber value="${row.bail}" pattern="<%=FUNDPATTERN%>"/></td>
		   <td  colspan="4">&nbsp;</td>
		 </tr>
         <tr height="25">   
              <td align="right"> 剩余保证金 ：</td>
              <td align="left"><fmt:formatNumber value="${row.lastBail}" pattern="<%=FUNDPATTERN%>"/></td>
              <td align="right"> 已交手续费 ：</td>
              <td align="left"><fmt:formatNumber value="${row.poundage}" pattern="<%=FUNDPATTERN%>"/></td>
              <td align="right"> 已交货款 ：</td>
              <td align="left">
              <db:selectUnion_daily var="rowColumn" contractid="${row.contractid}" operation="${OPERATION}">
              	 <c:choose>
              	 	 <c:when test="${not empty rowColumn.m}">
              	     <fmt:formatNumber value="${rowColumn.m}" pattern="<%=FUNDPATTERN%>"/>
              	     <c:set var="payment" value="${rowColumn.m}"/>
              	   </c:when>
              	   <c:otherwise>
              	   	  0.00
              	   	  <c:set var="payment" value="0"/>
              	   </c:otherwise>
              	  </c:choose> 
              </db:selectUnion_daily>
              </td>
         </tr>
         <tr height="25">
		      <td align="right"> 应交总额 ：</td>
              <td align="left">
              <fmt:formatNumber value="${row.amount*row.price+row.poundage}" pattern="<%=FUNDPATTERN%>"/></td>
              <td align="right"> 已交总额 ：</td>
              <td align="left"><fmt:formatNumber value="${row.poundage+row.lastBail+payment}" pattern="<%=FUNDPATTERN%>"/></td>    
              <td align="right"> 还应交款 ：</td>
              <td align="left"><fmt:formatNumber value="${(row.amount*row.price+row.poundage)-(row.poundage+row.lastBail+payment)}" pattern="<%=FUNDPATTERN%>"/></td>
         </tr>
        </table>	
    </db:select_HisBarDetail>

			<BR>
        </span>
		</fieldset>
		<fieldset width="100%">
		<legend>收款记录</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
          <tr height="25">
          <td align="center" width="35%">日期</td>
          <td align="center" width="20%">金额</td>
		  <td align="center" width="20%">备注</td>
          <td align="center" width="45%">对应提货数量</td>
          </tr>
		  <%/*
          <db:selectUnion_MoneyRecord var="row" contractid="${contractNO}" operation="${OPERATION}">
          */%>
	  <%
	   try{
		  String jspContractNO=pageContext.getAttribute("contractNO").toString();//合同号
		  String jspOPERATION=pageContext.getAttribute("OPERATION").toString();//业务代码
		  long tempAmount=0l;//成交数量
		  long tempLastAmount=0l;//剩余货量
		  double tempPrice=0d;//成交价格
		  double tempSecurity=0d;//保证金系数
		  double curAmount=0d;//本次提货有效货款数
          double listGoodsTotal=0d;//本次允许最大提货数量
		  StringBuffer sql=new StringBuffer();
		  DBBean bean=new DBBean(JNDI);
		  ResultSet rs=null;
		  if(pageContext.getAttribute("amount")!=null&&!"".equals(pageContext.getAttribute("amount"))){
            tempAmount=Long.parseLong(pageContext.getAttribute("amount").toString());
          }
          if(pageContext.getAttribute("lastAmount")!=null&&!"".equals(pageContext.getAttribute("lastAmount"))){
            tempLastAmount=Long.parseLong(pageContext.getAttribute("lastAmount").toString());
          }
          if(pageContext.getAttribute("singlePrice")!=null&&!"".equals(pageContext.getAttribute("singlePrice"))){
            tempPrice=Double.parseDouble(pageContext.getAttribute("singlePrice").toString());
          }
          if(pageContext.getAttribute("security")!=null&&!"".equals(pageContext.getAttribute("security"))){
            tempSecurity=Double.parseDouble(pageContext.getAttribute("security").toString());
          }
		  sql.append("select * from (select ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,");
		  sql.append("FrozenCaptial,market1,market2,type from v_dailymoney where contractno="+jspContractNO+" and operation="+jspOPERATION+"");
		  sql.append(" UNION select ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,FrozenCaptial,");
		  sql.append("market1,market2,type from v_hismoney  where contractno="+jspContractNO+" and operation="+jspOPERATION+")");
		  rs=bean.executeQuery(sql.toString());
		  while(rs.next()){
			double tempPayment=rs.getDouble("money");
		%>
		  
		  <c:set var="infodate" value="<%=rs.getDate("infodate")%>"/>
		  <c:set var="moneyId" value="<%=rs.getString("id")%>"/>
			 <tr height="25">
          	 	<td align="center"><fmt:formatDate value="${infodate}" pattern="<%=DATEPATTERN%>"/></td>
          	    <td align="right"><fmt:formatNumber value="<%=String.valueOf(tempPayment)%>" pattern="<%=FUNDPATTERN%>"/></td>
				<td align="center">
                  <c:set var="note" value=""/>
				  <db:select table="v_hismoney" var="colRow" columns="note" where="id=${moneyId}">
                     <c:set var="note" value="${colRow.note}"/>
				  </db:select>
				  <c:choose>
				    <c:when test="${not empty note}">
                      ${note}
					</c:when>
					<c:otherwise>
                      &nbsp;
					</c:otherwise>
				  </c:choose>
				</td>
          	    <td align="center">
				<%
				  //curAmount=tempPayment-(tempAmount-tempLastAmount)*tempPrice;
				  listGoodsTotal=(tempPayment/(tempPrice-tempSecurity/tempAmount));
				%>
				<fmt:formatNumber value="<%=String.valueOf(listGoodsTotal)%>" pattern="<%=FUNDPATTERN%>"/>
				</td>
          	 </tr>
		  <%}
		    rs.close();
			bean.close();
		  %>
	  <%
	     }
         catch(Exception e)
         {
  	       e.printStackTrace();
           errOpt();
         }		
	  %>
     </table>
     <BR>
        </span>
     </fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%" class="Noprint">
          <tr height="25">
          	<td width="40%"><div align="center">
        <input type="hidden" name="add">
        <!--
			  <input type="submit" onclick="return frmChk()" class="btn" value="确定">&nbsp;&nbsp;-->
			  <input type="button" onclick="window.print();" class="btn" value="打印">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="取消">
            </div></td>
          </tr>
     </table>
	 <!--
	 <fieldset>
		<legend>合同状态操作</legend>
		<BR>
		<span>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     	    <input type="hidden" name="id" value="${param.id}">
     	    <tr height="25">
     		     <td>合同状态:
     		     <input type="radio" name="finished" <c:if test="${status==1}">checked</c:if> value="1">履约完成&nbsp;&nbsp;
     		     <input type="radio" name="finished" <c:if test="${status==0}">checked</c:if> value="0">有效
     		     </td>
     	    </tr>
          <tr height="25">
          	<td width="40%"><div align="center" class="Noprint">
			      <input type="button" name="conStatusBtn" onclick="return controlStatus();" class="btn" value="设置合同状态">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
    </span>
  </fieldset>
  -->
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{ if(Trim(frm.tradePartition.value) == "")
	{
		alert("交易模板不能为空！");
		frm.tradePartition.focus();
		return false;
	}
	else 
	{ frm.add.value="true";
		return true;
	}
}
//-->
<!--
function refreshParent() {
	window.opener.location.href = window.opener.location.href;
	if (window.opener.progressWindow) window.opener.progressWindow.close();
	window.close();
	}
//-->

<!--
//设置合同状态
function controlStatus(){
  if(userConfirm()){
    frm.add.value="controlStatus";	
    frm.submit();
    //return true;
  }else{
    return false;
  }	
}
//-->
</SCRIPT>
<%@ include file="/vendue/manage/public/footInc.jsp" %>