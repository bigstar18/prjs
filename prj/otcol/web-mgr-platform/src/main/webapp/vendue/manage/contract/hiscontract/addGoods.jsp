<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<!--取得参数-->
  <head>
	<title>添加货物记录</title>
</head>
<%
	String pd = TradeDAOFactory.getDAO().getPD();
	request.setAttribute("pd",pd);
	request.setAttribute("DW",DW);
	request.setAttribute("DWS",DWS);
	int this_tradeunit = Integer.valueOf(request.getParameter("this_tradeunit"));
	String loginID = AclCtrl.getLogonID(request);
%>
<c:if test="${not empty param.add}">
<%
DBBean bean=null;
ResultSet rs=null;
try{
  java.util.Date curdate = new java.util.Date();
  java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
  String commodityID=request.getParameter("commodityID");
  String contractID=request.getParameter("contractID");
  
  BigDecimal realLiftAmount= null;
  if(pd.equals("true"))
  {
	realLiftAmount=new BigDecimal(String.valueOf(Double.valueOf(request.getParameter("amount"))*this_tradeunit));//填入的提货数量
	//alert("amount is "+Double.valueOf(request.getParameter("amount"))*this_tradeunit,out);
  }
  else
  {
	  realLiftAmount=new BigDecimal(request.getParameter("amount"));//填入的提货数量
  }
  //付货款业务代码
  String operation=pageContext.getAttribute("OPERATION").toString();
  
  //算出最大可提货数量
  bean=new DBBean(JNDI);
  BigDecimal price=null;//价格
  BigDecimal amount=null;//总成交量
  BigDecimal b_security=null;//初始化保证金  xieying怀疑是买方的保证金
  BigDecimal liftedAmount=null;//数量
  BigDecimal payment=null;//已交总货款
  BigDecimal coeff=null;
  BigDecimal liftGoodsAmount=null;//最大可提货数量
  BigDecimal curMoney=null;//当前货款数
  String firmid=null;//交易代码
  
  StringBuffer sql=new StringBuffer();
  sql.append("select price,amount,b_bail,(select tradeunit from v_commodity where id=commodityid)");
  sql.append(" tradeunit from v_hisbargain where commodityid="+commodityID+" and contractID="+contractID+"");
  rs=bean.executeQuery(sql.toString());
  if(rs.next()){
      price=ManaUtil.disBD(rs.getBigDecimal("price"));
	  	b_security=ManaUtil.disBD(rs.getBigDecimal("b_bail"));
	  	amount=ManaUtil.disBD(rs.getBigDecimal("amount")).multiply(ManaUtil.disBD(rs.
		  getBigDecimal("tradeunit")));
  }
  rs.close();
  bean.closeStmt();
  coeff=price.subtract(b_security.divide(amount,8,BigDecimal.ROUND_HALF_UP));
  firmid=request.getParameter("firmid");
  
  System.out.println("firmid==========:"+firmid);
  
  sql=new StringBuffer();
  int partitionid=0;
  sql.append("select partitionid from v_syspartition where rownum=1 and validflag=1");
  rs=bean.executeQuery(sql.toString());
  if(rs.next()){
	  partitionid=rs.getInt("partitionid");
  }
  rs.close();
  bean.closeStmt();
  //KernelEngineDAO dao = GlobalContainer.getEngineDAO(partitionid);
  //dao.getMarketPaymentForGoods(marketId,firmid)
  sql=new StringBuffer();
  sql.append("select paymentforgoods from v_tradeuser where usercode='"+firmid+"'");
  rs=bean.executeQuery(sql.toString());
  if(rs.next()){
      payment=rs.getBigDecimal("paymentforgoods");
  }
  //alert("1111    "+payment.intValue(),out);
  if(payment.compareTo(new BigDecimal(0))==0) //保证金大于等于货款 xieying
	liftGoodsAmount=amount;
  else
	liftGoodsAmount=payment.divide(coeff,4,BigDecimal.ROUND_HALF_UP);

  if(liftGoodsAmount.compareTo(amount)>0){
    liftGoodsAmount=amount;
  }
  sql=new StringBuffer();
  sql.append("select sum(amount) as s from v_outlog where contractid="+contractID+" and  finished=2");
  rs=bean.executeQuery(sql.toString());
  if(rs.next()){
          liftedAmount=ManaUtil.disBD(rs.getBigDecimal("s"));
  }
  rs.close();
  bean.close();
  if(liftedAmount.compareTo(new BigDecimal(0))>0){
      amount=amount.subtract(liftedAmount);
	  if(liftGoodsAmount.compareTo(amount)>0){
	      liftGoodsAmount=amount;
	  }
  }else{
      if(liftGoodsAmount.compareTo(amount)>0){
	      liftGoodsAmount=amount;
	  }
  }
  curMoney=payment.subtract(liftedAmount.multiply(price));
  BigDecimal realLiftMoney=realLiftAmount.multiply(coeff);
  BigDecimal liftMoney=liftGoodsAmount.multiply(coeff);
  if(realLiftMoney.compareTo(liftMoney.subtract(new BigDecimal(1)))<=0){
      realLiftMoney=realLiftMoney.add(new BigDecimal(1));
  }
  realLiftMoney=realLiftMoney.divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
  liftMoney=liftMoney.divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
  if(realLiftMoney.compareTo(liftMoney)<=0&&realLiftAmount.compareTo(liftGoodsAmount)<=0){
%>
<%
  String outdate=request.getParameter("outdate");
  java.sql.Date a=null;
  //新建时间
  java.sql.Date convertDate=a.valueOf(outdate);
%>
  <c:set var="mess" value=""/>
  <c:set var="modFlag" value="true"/>
    <db:select var="row" table="v_hisbargain" columns="status" where="contractid=${param.contractID} and status>0">
	   <c:set var="modFlag" value="false"/>
	   <c:if test="${row.status==1}">
         <c:set var="mess" value="合同已是提货完毕状态,不能添加出库记录,添加出库记录失败!"/>
       </c:if>
	   <c:if test="${row.status==2}">
         <c:set var="mess" value="合同已是付款完毕状态,不能添加出库记录,添加出库记录失败!"/>
       </c:if>
	   <c:if test="${row.status==3}">
         <c:set var="mess" value="合同已是归档状态,不能添加出库记录,添加出库记录失败!"/>
       </c:if>
	</db:select>
	<!--判断提货总数量是否大于成交量-->
	<c:if test="${modFlag==true}">
	    <!--已经提货量-->
	    <c:set var="liftedAmount" value="0"/>
		<!--成交量-->
		<c:set var="tradeAmount" value="0"/>
	    <db:select var="row" table="v_outlog" columns="sum(amount) s" where="contractid=${param.contractID}">
            <c:set var="liftedAmount" value="${row.s}"/>
		</db:select>
		<db:select var="row" table="v_hisbargain" columns="amount tradeamount" where="contractid=${param.contractID}">
            <c:set var="tradeAmount" value="${row.tradeamount}"/>
		</db:select>
	    <c:if test="${liftedAmount+param.amount>tradeAmount}">
		    <c:set var="modFlag" value="false"/>
			<c:set var="mess" value="提货总数量大于成交量,添加出库记录失败!"/>
		</c:if>
	</c:if>
	<c:choose>
	  <c:when test="${modFlag=='true'}">
         <db:select var="row" table="dual" columns="SP_V_OUTLOG.nextval as seqID">
            <c:set var="id" value="${row.seqID}"/>
         </db:select>
         <db:insert table="v_outlog"
         id="${id}"
         contractid="${param.contractID}"
         capital="${param.capital}"
         amount="${param.amount}"
         operator="${param.operator}"
         outdate="<%=convertDate%>"
         createtime="<%=sqlDate%>"
         finished="0"
         />
    <SCRIPT LANGUAGE="JavaScript">
       alert("添加提货记录成功！");
    </SCRIPT>
	</c:when>
	<c:otherwise>
       <SCRIPT LANGUAGE="JavaScript">
           alert("${param.contractID}${mess}");
       </SCRIPT>
    </c:otherwise>
  </c:choose>
<%}else{%>
    <SCRIPT LANGUAGE="JavaScript">
       alert("输入的提货数量大于最大可提货数量!");
    </SCRIPT>
<%}%> 
<%
}catch(Exception e){
	e.printStackTrace();
    errOpt();
%>
  <SCRIPT LANGUAGE="JavaScript">
      alert("操作出错,请与管理员联系!");
  </SCRIPT>
<%
}
finally{
    try{if(rs!=null)rs.close();}catch(Exception ex){}
    if(bean!=null)bean.close();
} 
%>
</c:if>
<body>
<form name=frm id=frm action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>添加提货记录</legend>
		<BR>
		<span>
        <input type="hidden" name="price" value="${param.price}">
		<input type="hidden" name="firmid" value="${param.firmid}">
		<input type="hidden" name="marketId" value="${param.marketId}">
		<input type="hidden" name="provinceId" value="${param.provinceId}">
		<input type="hidden" name="this_tradeunit" value=<%=this_tradeunit%>>
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  	<c:set var="contractID" value="${row.contractid}"/>
		  	<input type="hidden" name="listGoodsTotal" value="${param.listGoodsTotal}">
			<input type="hidden" name="commodityID" value="${param.commodityID}">
			<%
			  double b_security=0D;
			  double amount=0d;
			  double price=0D;
			  double coeff=0D;
			  if(ManaUtil.checkStr(request.getParameter("b_security"))){
			    b_security=Double.parseDouble(request.getParameter("b_security"));
			  }
			  if(pd.equals("true"))
			  {
				  if(ManaUtil.checkStr(request.getParameter("amount"))){
					amount=Double.parseDouble(request.getParameter("amount"))*this_tradeunit;
				  }
			  }
			  else
			  {
				  if(ManaUtil.checkStr(request.getParameter("amount"))){
					amount=Double.parseDouble(request.getParameter("amount"));
				  }
			  }
              if(ManaUtil.checkStr(request.getParameter("price"))){
			    price=Double.parseDouble(request.getParameter("price"));
			  }
              coeff=price-(b_security/amount);
			%>
			<input type="hidden" name="coeff" value="<%=coeff%>">
            <tr height="35">
                <td align="right"> 合同号 ：</td>
                <td align="left">
                	<input name="contractID" type="text" class="text" style="width: 220px;" value="${param.contractID}" readonly>
                </td>
            </tr>
            <tr height="35">
				<td align="right"> 出库商品数量 ：</td>
                <td align="left">
                	<input name="amount" type="text" class="text" style="width: 220px;" onchange="caculCapital(<%=this_tradeunit%>,<%=pd%>);">&nbsp;最大提货数量(<font color="#ff0000"><fmt:formatNumber value="${param.listGoodsTotal}" pattern="<%=FUNDPATTERNEXT%>"/></font>)(${param.str6})
                </td>
			<!--<%if(pd.equals("true")){%>
                <td align="right"> 出库商品数量(<%=DW%>) ：</td>
                <td align="left">
                	<input name="amount" type="text" class="text" style="width: 220px;" onchange="caculCapital(<%=this_tradeunit%>,<%=pd%>);">&nbsp;最大提货数量(<font color="#ff0000"><fmt:formatNumber value="${param.listGoodsTotal}" pattern="<%=FUNDPATTERNEXT%>"/></font>)(<%=DW%>)
                </td>
			<%}else{%>
				 <td align="right"> 出库商品数量(<%=DWS%>) ：</td>
                <td align="left">
                	<input name="amount" type="text" class="text" style="width: 220px;" onchange="caculCapital(<%=this_tradeunit%>,<%=pd%>);">&nbsp;最大提货数量(<font color="#ff0000"><fmt:formatNumber value="${param.listGoodsTotal}" pattern="<%=FUNDPATTERNEXT%>"/></font>)(<%=DWS%>)
                </td>
			<%}%>-->
            </tr>
            <tr height="35">
                <td align="right"> 出库对应的资金 ：</td>
                <td align="left">
                	<input name="capital" type="text" class="text" style="width: 220px;" readonly>
                </td>
            </tr>
        <tr height="35">
                <td align="right"> 操作员 ：</td>
                <td align="left">
                	<input name="operator" type="text" class="text" style="width: 220px;" value="<%=loginID%>" readonly>
                </td>
        </tr>
        <tr height="35">
                <td align="right"> 日期 ：</td>
                <td align="left">
                  <MEBS:calendar eltID="outdate" eltName="outdate" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/"/>
                </td>
        </tr>
        </table>
			<BR>
        </span>
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
              <input type="hidden" name="add">
			  <input type="button" onclick="return frmChk()" class="btn" value="保存">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--

function frmChk()
{
	
	var realLiftMoney=parseFloat(frm.amount.value)*parseFloat(frm.coeff.value);
	var liftMoney=parseFloat(frm.coeff.value)*parseFloat(frm.listGoodsTotal.value);
	if(Trim(frm.amount.value) != ""&&!isNaN(frm.amount.value)){
	    if(realLiftMoney<liftMoney-1){
	        realLiftMoney=realLiftMoney+1;
	    }
	}
	if(Trim(frm.contractID.value) == "")
	{
		alert("合同号不能为空！");
		frm.contractID.focus();
		return false;
	}else if(Trim(frm.amount.value) == "")
	{
		alert("出库商品数量不能为空！");
		frm.amount.focus();
		return false;
	}else if(isNaN(frm.amount.value))
	{
		alert("出库商品数量只能输入数字！");
		frm.amount.focus();
		return false;
	}
	else if(parseFloat(isFormat(realLiftMoney,2))>parseFloat(isFormat(liftMoney,2)))
	{
		alert("输入的出库商品数量大于最大提货数量!");
		frm.amount.focus();
		return false;
	}else if(parseFloat(isFormat(frm.amount.value,4))>parseFloat(isFormat(frm.listGoodsTotal.value,4))){
	    alert("输入的出库商品数量大于最大提货数量!");
		frm.amount.focus();
		return false;
	}
	else if(Trim(frm.capital.value) == "")
	{
		alert("出库对应资金不能为空！");
		frm.capital.focus();
		return false;
	}else if(Trim(frm.outdate.value) == "")
	{
		alert("日期不能为空！");
		frm.outdate.focus();
		return false;
	}
	else 
	{
	  if(userConfirm()){
	    frm.add.value="true";
		frm.submit();
		//return true;
	  }else{
	    return false;
	  }
	}
}

//算出对应资金流水
function caculCapital(this_tradeunit,pd){
  if(frm.amount.value<0)  //xieying 出库单为0
  {
	  alert("商品数量必需大于等于零");
		frm.amount.focus();
		return false;
  }
  if(pd)
	amount=frm.amount.value*this_tradeunit;
  else
	amount=frm.amount.value;
  coeff=frm.coeff.value;
  if(!isNaN(amount)){
    frm.capital.value=isFormat(parseFloat(amount)*parseFloat(coeff),2);//加上精度控制
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
</SCRIPT>
<%@ include file="/vendue/manage/public/footInc.jsp" %>