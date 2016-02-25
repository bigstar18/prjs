<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<%  //xieying
try
{

%>

<html>
<head>
  <title class="Noprint">提货记录</title>
	<base target="_self"/>
</head>
<c:if test="${not empty param.opt}">
  <!--删除货物记录-->
  <c:if test="${param.opt=='delGoods'}">
    <c:catch var="exceError">
	<!--删除不成功提示信息-->
	<c:set var="returnInfo" value=""/>
	<c:forEach var="v_row" items="${paramValues.ck}">
	  <!--判断货物记录是否已有了出库单-->
      <c:set var="delFlag" value="true"/>
      <db:select var="row" table="v_outlog" columns="id" where="id=${v_row} and finished=2">
          <c:set var="delFlag" value="false"/>
	  </db:select>
	  <c:choose>
	  <c:when test="${delFlag=='true'}"><!--判断是否可以删除-->
        <db:tranDelRecord outlogid="${v_row}"/>
		<c:set var="outId" value=""/>
	  </c:when>
	  <c:otherwise><!--不能删除时记录哪对应的创建时间与数量-->
        <db:select var="colRow" table="v_outlog" columns="id,createtime,amount" where="id=${v_row}">
          <c:choose>
		    <c:when test="${not empty returnInfo}">
		      <c:set var="returnInfo" value="${returnInfo};\\r\\n创建时间:${fn:substring(colRow.createtime,0,10)},对应提货数量:${colRow.amount}"/>
			</c:when>
			<c:otherwise>
              <c:set var="returnInfo" value="创建时间:${fn:substring(colRow.createtime,0,10)},对应提货数量:${colRow.amount}"/>
			</c:otherwise>
		   </c:choose>
		</db:select>
	  </c:otherwise>
	  </c:choose>
	</c:forEach>
  <c:choose>
     <c:when test="${empty returnInfo}">
       <SCRIPT LANGUAGE="JavaScript">
        <!--
          alert("货物记录删除成功！");
        //-->
       </SCRIPT>
	  </c:when>
	  <c:otherwise>
         <SCRIPT LANGUAGE="JavaScript">
           <!--
             alert("${returnInfo}"+"出库记录已审核不能删除！");
           //-->
        </SCRIPT>
      </c:otherwise>
	</c:choose>
	</c:catch>
    <c:if test="${not empty exceError}">
        <%
	         //异常处理
	         String exceError=pageContext.getAttribute("exceError").toString();
		     log(request,exceError);
		     hintError(out);
	    %>
    </c:if>
   </c:if>
</c:if>
<body>
<form name=frm id=frm action="">
        <!--市场号-->
		<input type="hidden" name="marketId" value="${param.marketId}">
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
		<!--保证金(买方)-->
		<c:set var="b_security" value=""/>
		<!--保证金(卖方)-->
		<c:set var="s_security" value=""/>
		<!--买方编号-->
		<c:set var="firmid" value=""/>
		<!--商品所属省份-->
		<c:set var="provinceId" value=""/>
		<db:select_HisBarDetail var="row" where="u1.commodityid=${param.commodityID} and  u1.commodityid=u3.id and u3.id=u4.commid and u2.firmid=u1.userid and u1.contractid=${param.contractID} ">
		<input type="hidden" name="this_tradeunit" value="${row.tradeunit}">
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  	<c:set var="contractID" value="${row.contractid}"/>
			<c:set var="this_tradeunit" value="${row.tradeunit}"/>
			<c:set var="amount" value="${row.amount}"/>
		  	<c:set var="price" value="${row.price}"/>
		  	<c:set var="commodityID" value="${row.id}"/>
		  	<c:set var="lastAmount" value="${row.lastAmount}"/>
		  	<c:set var="b_security" value="${row.b_bail}"/>  <!-- 估计应该是买方保证金 xieying -->
            <c:choose>
			<c:when test="${row.trademode==0}">
			<c:set var="firmid" value="${row.userid}"/>
            </c:when>
			<c:when test="${row.trademode==1}">
            <c:set var="firmid" value="${row.bluserid}"/>
            </c:when>
			<c:when test="${row.trademode==2}">
            <c:set var="firmid" value="${row.bluserid}"/>
            </c:when>
			<c:otherwise>
			</c:otherwise>
			</c:choose>
			<c:set var="provinceId" value="${row.str6}"/>
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
				<td align="right" width="20%"> 数量(${row.str6}) ：</td>
                <td align="left" width="20%">
                	${row.amount}
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
      double tempAmount=0d;//成交数量
	  double tempLiftedAmount=0d;//已提货数量
      double tempPrice=0d;//成交价格
      double tempSecurity=0d;//保证金
	  double curAmount=0d;//本次提货有效货款数
      double listGoodsTotal=0d;//本次允许最大提货数量
	  double lastLiftAmount=0d;//剩余最大提货数量
      String contractID=null;//合同号
	  String firmid=null;//交易代码
	  int marketId=0;//所属市场
	  ResultSet rs=null;
	  DBBean bean=null;
	  try{
	  if(pageContext.getAttribute("firmid")!=null&&!"".equals(pageContext.getAttribute("firmid"))){
          firmid=pageContext.getAttribute("firmid").toString();
      }
	  marketId=0;
	  bean=new DBBean(JNDI);
	  StringBuffer sql=new StringBuffer();
	  int partitionid=0;

	  sql.append("select partitionid from v_syspartition where rownum=1 and validflag=1");
	  rs=bean.executeQuery(sql.toString());
	  if(rs.next()){
	      partitionid=rs.getInt("partitionid");
	  }
	  rs.close();
	  bean.closeStmt();
	  /** +++++++++++++++++++++++++++++++++++++++++++ 原资金处理方式 +++++++++++++++++++++++++++**/
	  /*
	  sql=new StringBuffer();
	  sql.append("select balance from v_tradeuser where firmid='"+firmid+"'");
    rs=bean.executeQuery(sql.toString());
	  if(rs.next()){
	      tempPayment=rs.getDouble("balance");
	  }
	  rs.close();
	  bean.closeStmt();
	  */
	  /*****^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^新资金处理^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*******/
	 Connection conn = null;
	 CallableStatement proc = null;
try{
		conn = TradeDAOFactory.getDAO().getConnection();
	  proc = conn.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
	  proc.setString(2, firmid);
	  proc.setInt(3, 0);
	  proc.registerOutParameter(1, Types.DOUBLE);
	  proc.executeQuery();
	  tempPayment=proc.getDouble(1);	  	
	}catch(Exception e){
	}finally{
		if(proc!=null)
	  	proc.close();
	  if(conn!=null)
	  	conn.close();
	}
	  /*****vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv新资金处理vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*******/
      if(pageContext.getAttribute("amount")!=null&&!"".equals(pageContext.getAttribute("amount"))){
        tempAmount=Double.parseDouble(pageContext.getAttribute("amount").toString());
      }
      if(pageContext.getAttribute("price")!=null&&!"".equals(pageContext.getAttribute("price"))){
        tempPrice=Double.parseDouble(pageContext.getAttribute("price").toString());
      }
	  // 怀疑是买方保证金  xieying
      if(pageContext.getAttribute("b_security")!=null&&!"".equals(pageContext.getAttribute("b_security"))){
        tempSecurity=Double.parseDouble(pageContext.getAttribute("b_security").toString());
      }
	  if(pageContext.getAttribute("contractID")!=null&&!"".equals(pageContext.getAttribute("contractID"))){
        contractID=pageContext.getAttribute("contractID").toString();
      }
	  rs=bean.executeQuery("select sum(amount) as s from v_outlog where contractid="+contractID+" and  finished=2");
	  if(rs.next()){
	      tempLiftedAmount=rs.getDouble("s");
	  }
	  rs.close();
	  bean.close();
      curAmount=tempPayment;
	  double price = tempPrice-tempSecurity/tempAmount;
	  if(price<=0) //保证金大于或者等于货款
        listGoodsTotal=tempSecurity/tempPrice;
	  else
		listGoodsTotal=curAmount/(tempPrice-tempSecurity/tempAmount);
	  //System.out.println("listGoodsTotal is "+listGoodsTotal); //xieying
      listGoodsTotal=ManaUtil.round(listGoodsTotal,4);
      if(listGoodsTotal<0){
        listGoodsTotal=0;
      }
	  if(tempLiftedAmount>0){
	      lastLiftAmount=tempAmount-tempLiftedAmount;
	      if(listGoodsTotal>lastLiftAmount){
	          listGoodsTotal=lastLiftAmount;
	      }
	  }else{
	      if(listGoodsTotal>tempAmount){
	          listGoodsTotal=tempAmount;
	      }
	  }
   }catch(Exception e){
	   e.printStackTrace();
       errOpt();
	   alert("查询出错!",out);
   }
   finally{
       try{if(rs!=null)rs.close();}catch(Exception ex){}
       if(bean!=null)bean.close();
   }
%>
	    <BR>
        </span>
		</fieldset>
		<fieldset width="100%">
		<legend>提货记录</legend>
		<input type="hidden" name="str6" value="${param.str6}">
		<input type="hidden" name="commodityID" value="${param.commodityID}">
		<input type="hidden" name="contractID" value="${param.contractID}">
		<input type="hidden" name="marketId" value="${param.marketId}">
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
          <db:select var="row" table="v_outlog" columns="id,outid,outdate,capital,amount,remark,finished,id" orderBy="outdate desc" where="contractid=${contractID}">
          	 <tr height="25">
			    <td>&nbsp;</td>
			    <td align="center"><input name="ck" type="checkbox" value='${row.id}'></td>
          	 	<td align="center"><fmt:formatDate value="${row.outdate}" pattern="<%=DATEPATTERN%>"/></td>

          	    <td align="center" style="text-align:right;"><fmt:formatNumber value="${row.amount}" pattern="<%=FUNDPATTERNEXT%>"/>
				<input type="hidden" name="tempLiftAmount" value="${row.amount}">

				</td>
          	    <td align="right"><fmt:formatNumber value="${row.capital}" pattern="<%=FUNDPATTERN%>"/></td>
          	    <td align="center"><a href="javascript:viewRecord('${commodityID}','${row.outid}','${row.capital}','${row.amount}','${row.finished}','${row.id}','${param.str6}','${param.contractID}')" class="normal">查看</a></td>
          	    <td align="center"><c:if test="${row.finished==2}">已审核</c:if>
				<c:if test="${row.finished==1}">已生成出库单</c:if>
          	    <c:if test="${row.finished==0}">未生成出库单</c:if>
          	    </td>
          	 </tr>	
          </db:select>
		  <tr height="25">
          <td align="right" colspan="3">&nbsp</td>
		  <td align="right">
		  <c:set var="hjLift" value=""/>
		  <db:select table="v_outlog" columns="sum(amount) as su" var="row" where="contractid=${contractID}">
             <c:choose>
                <c:when test="${not empty row.su}">
				 合计 <c:set var="hjLift" value="${row.su}"/>
				</c:when>
				<c:otherwise>
                 <c:set var="hjLift" value="0"/>
				</c:otherwise>
			 </c:choose>
          </db:select>
          <fmt:formatNumber value="${hjLift}" pattern="<%=FUNDPATTERNEXT%>"/>
		  </td>
		  <td colspan="4">&nbsp;</td>
		  </tr>
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
			 <input type="button" onclick="addGoodsView('${contractID}','${price}','<%=listGoodsTotal%>','<%=tempSecurity%>','<%=tempAmount%>','${commodityID}','${firmid}','${param.marketId}','${provinceId}','${param.str6}');" class="bigbtn" value="添加出库记录">&nbsp;&nbsp;
			 <input type="button" onclick="return deleteRecExt(frm,tb,'ck',8);" class="btn" value="删除">&nbsp;&nbsp;
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
function addGoodsView(contractID,price,listGoodsTotal,tempSecurity,tempAmount,commodityID,firmid,marketId,provinceId,str6){
  var tempLiftAmount=frm.tempLiftAmount;
  var liftedAmount=0;
  var mostLiftAmount=0;
  var this_tradeunit=frm.this_tradeunit.value;

  if(parseFloat(listGoodsTotal)<=0){
    alert("本次允许最大提货值为0,不能添加货物记录!");
  	return false;
  }else{
  
	PopWindow("addGoods.jsp?contractID="+contractID+"&price="+price+"&listGoodsTotal="+listGoodsTotal+"&b_security="+tempSecurity+"&amount="+tempAmount+"&commodityID="+commodityID+"&firmid="+firmid+"&marketId="+marketId+"&provinceId="+provinceId+"&this_tradeunit="+this_tradeunit+"&str6="+str6,600,450);  
	frm.submit();
  }
}


//查看出库单信息
function viewRecord(commodityID,outID,capital,amountTotal,finished,outlogId,str6,contractID){
var this_tradeunit=frm.this_tradeunit.value; result=PopWindow("addRecord.jsp?commodityID="+commodityID+"&outID="+outID+"&capital="+capital+"&amountTotal="+amountTotal+"&finished="+finished+"&outlogId="+outlogId+"&str6="+str6+"&contractID="+contractID,750,550);
frm.submit();
//window.open("addRecord.jsp?commodityID="+commodityID+"&outID="+outID+"&capital="+capital+"&amountTotal="+amountTotal+"&finished="+finished+"&outlogId="+outlogId+"&str6="+str6+"&contractID="+contractID,750,550);
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