<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<%  //xieying
try
{

%>

<html>
<head>
  <title class="Noprint">�����¼</title>
	<base target="_self"/>
</head>
<c:if test="${not empty param.opt}">
  <!--ɾ�������¼-->
  <c:if test="${param.opt=='delGoods'}">
    <c:catch var="exceError">
	<!--ɾ�����ɹ���ʾ��Ϣ-->
	<c:set var="returnInfo" value=""/>
	<c:forEach var="v_row" items="${paramValues.ck}">
	  <!--�жϻ����¼�Ƿ������˳��ⵥ-->
      <c:set var="delFlag" value="true"/>
      <db:select var="row" table="v_outlog" columns="id" where="id=${v_row} and finished=2">
          <c:set var="delFlag" value="false"/>
	  </db:select>
	  <c:choose>
	  <c:when test="${delFlag=='true'}"><!--�ж��Ƿ����ɾ��-->
        <db:tranDelRecord outlogid="${v_row}"/>
		<c:set var="outId" value=""/>
	  </c:when>
	  <c:otherwise><!--����ɾ��ʱ��¼�Ķ�Ӧ�Ĵ���ʱ��������-->
        <db:select var="colRow" table="v_outlog" columns="id,createtime,amount" where="id=${v_row}">
          <c:choose>
		    <c:when test="${not empty returnInfo}">
		      <c:set var="returnInfo" value="${returnInfo};\\r\\n����ʱ��:${fn:substring(colRow.createtime,0,10)},��Ӧ�������:${colRow.amount}"/>
			</c:when>
			<c:otherwise>
              <c:set var="returnInfo" value="����ʱ��:${fn:substring(colRow.createtime,0,10)},��Ӧ�������:${colRow.amount}"/>
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
          alert("�����¼ɾ���ɹ���");
        //-->
       </SCRIPT>
	  </c:when>
	  <c:otherwise>
         <SCRIPT LANGUAGE="JavaScript">
           <!--
             alert("${returnInfo}"+"�����¼����˲���ɾ����");
           //-->
        </SCRIPT>
      </c:otherwise>
	</c:choose>
	</c:catch>
    <c:if test="${not empty exceError}">
        <%
	         //�쳣����
	         String exceError=pageContext.getAttribute("exceError").toString();
		     log(request,exceError);
		     hintError(out);
	    %>
    </c:if>
   </c:if>
</c:if>
<body>
<form name=frm id=frm action="">
        <!--�г���-->
		<input type="hidden" name="marketId" value="${param.marketId}">
		<fieldset width="100%">
		<legend>��ͬ��Ϣ</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
	    <!--�ѽ�����-->
		<c:set var="payment" value=""/>
		<!--�ɽ�����-->
		<c:set var="amount" value=""/>
		<!--�ɽ��۸�-->
		<c:set var="price" value=""/>
		<!--��Ʒ��-->
		<c:set var="commodityID" value=""/>
		<!--ʣ�����-->
		<c:set var="lastAmount" value=""/>
		<!--��֤��(��)-->
		<c:set var="b_security" value=""/>
		<!--��֤��(����)-->
		<c:set var="s_security" value=""/>
		<!--�򷽱��-->
		<c:set var="firmid" value=""/>
		<!--��Ʒ����ʡ��-->
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
		  	<c:set var="b_security" value="${row.b_bail}"/>  <!-- ����Ӧ�����򷽱�֤�� xieying -->
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
                <td align="right" width="20%"> ��ĺ� ��</td>
                <td align="left" width="20%">
                	${row.code}
                </td>
                <td align="right" width="20%"> ��ͬ�� ��</td>
                <td align="left" width="20%">
                	${row.contractid}
                </td>
        </tr>
		<tr height="25">
                <td align="right" width="20%"> �򷽱�� ��</td>
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
                <td align="right" width="20%">������ţ�</td>
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
                <td align="right" width="20%"> ����ģʽ ��</td>
                <td align="left" width="20%">
                <c:choose>
				<c:when test="${row.trademode==0}">
                ����
				</c:when>
				<c:when test="${row.trademode==1}">
				���� 
				</c:when>
				<c:when test="${row.trademode==2}">
				�б�
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>	
                </td>
                <td align="right" width="20%"> �ɽ��� ��</td>
                <td align="left" width="20%">
                ${row.price}
                </td>
		</tr>
		<tr height="25">
				<td align="right" width="20%"> ����(${row.str6}) ��</td>
                <td align="left" width="20%">
                	${row.amount}
                </td>

                <td align="right" width="20%"> �ɽ���� ��</td>
                <td align="left" width="20%">
                	<fmt:formatNumber value="${row.amount*row.price*row.tradeunit}" pattern="<%=FUNDPATTERN%>"/>
                </td>
		 </tr>
        </table>	
    </db:select_HisBarDetail>
    <%
      double tempPayment=0d;//�ѽ�����
      double tempAmount=0d;//�ɽ�����
	  double tempLiftedAmount=0d;//���������
      double tempPrice=0d;//�ɽ��۸�
      double tempSecurity=0d;//��֤��
	  double curAmount=0d;//���������Ч������
      double listGoodsTotal=0d;//������������������
	  double lastLiftAmount=0d;//ʣ������������
      String contractID=null;//��ͬ��
	  String firmid=null;//���״���
	  int marketId=0;//�����г�
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
	  /** +++++++++++++++++++++++++++++++++++++++++++ ԭ�ʽ���ʽ +++++++++++++++++++++++++++**/
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
	  /*****^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^���ʽ���^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*******/
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
	  /*****vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv���ʽ���vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*******/
      if(pageContext.getAttribute("amount")!=null&&!"".equals(pageContext.getAttribute("amount"))){
        tempAmount=Double.parseDouble(pageContext.getAttribute("amount").toString());
      }
      if(pageContext.getAttribute("price")!=null&&!"".equals(pageContext.getAttribute("price"))){
        tempPrice=Double.parseDouble(pageContext.getAttribute("price").toString());
      }
	  // �������򷽱�֤��  xieying
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
	  if(price<=0) //��֤����ڻ��ߵ��ڻ���
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
	   alert("��ѯ����!",out);
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
		<legend>�����¼</legend>
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
          <td align="center">����</td>
		  <td align="center" width="20%">��Ӧ�������(${param.str6})</td>

          <td align="center">���</td>
          <td align="center">���ⵥ</td>
          <td align="center">״̬</td>
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
          	    <td align="center"><a href="javascript:viewRecord('${commodityID}','${row.outid}','${row.capital}','${row.amount}','${row.finished}','${row.id}','${param.str6}','${param.contractID}')" class="normal">�鿴</a></td>
          	    <td align="center"><c:if test="${row.finished==2}">�����</c:if>
				<c:if test="${row.finished==1}">�����ɳ��ⵥ</c:if>
          	    <c:if test="${row.finished==0}">δ���ɳ��ⵥ</c:if>
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
				 �ϼ� <c:set var="hjLift" value="${row.su}"/>
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
			 <input type="button" onclick="addGoodsView('${contractID}','${price}','<%=listGoodsTotal%>','<%=tempSecurity%>','<%=tempAmount%>','${commodityID}','${firmid}','${param.marketId}','${provinceId}','${param.str6}');" class="bigbtn" value="��ӳ����¼">&nbsp;&nbsp;
			 <input type="button" onclick="return deleteRecExt(frm,tb,'ck',8);" class="btn" value="ɾ��">&nbsp;&nbsp;
		     <input type="button" onclick="window.print();" class="btn" value="��ӡ">&nbsp;&nbsp;
		     <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
          </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
//��ӻ���
function addGoodsView(contractID,price,listGoodsTotal,tempSecurity,tempAmount,commodityID,firmid,marketId,provinceId,str6){
  var tempLiftAmount=frm.tempLiftAmount;
  var liftedAmount=0;
  var mostLiftAmount=0;
  var this_tradeunit=frm.this_tradeunit.value;

  if(parseFloat(listGoodsTotal)<=0){
    alert("��������������ֵΪ0,������ӻ����¼!");
  	return false;
  }else{
  
	PopWindow("addGoods.jsp?contractID="+contractID+"&price="+price+"&listGoodsTotal="+listGoodsTotal+"&b_security="+tempSecurity+"&amount="+tempAmount+"&commodityID="+commodityID+"&firmid="+firmid+"&marketId="+marketId+"&provinceId="+provinceId+"&this_tradeunit="+this_tradeunit+"&str6="+str6,600,450);  
	frm.submit();
  }
}


//�鿴���ⵥ��Ϣ
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