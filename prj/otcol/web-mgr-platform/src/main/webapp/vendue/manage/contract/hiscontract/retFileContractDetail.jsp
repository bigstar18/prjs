<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<html>
<head>
  <title class="Noprint">��ͬ��ϸ</title>
</head>
<c:if test='${not empty param.add}'>
<c:if test="${param.add=='controlStatus'}">
  <!--�Ƿ��������״̬��־-->
  <c:set var="modFlag" value="true"/>
  <!--�жϳ����¼�Ƿ�Ϊ���״̬-->
  <db:select var="row" table="v_outlog" columns="contractid" where="contractid=${param.contractId} and finished<>1">
     <c:set var="modFlag" value="false"/>
  </db:select>
  <c:choose>
    <c:when test="${modFlag=='true'}">
	  <db:update table="v_hisbargain" status="${param.finished}" where="commodityid=${param.commodityID}"/>
      <SCRIPT LANGUAGE="JavaScript">
         <!--
           alert("���ú�ͬ״̬�ɹ���");
         //-->
      </SCRIPT>
	</c:when>
	 <c:otherwise>
       <SCRIPT LANGUAGE="JavaScript">
         <!--
           alert("��ͬ��Ӧ�ĳ����¼��δ���״̬,����״̬ʧ�ܣ�");
         //-->
       </SCRIPT>
	 </c:otherwise>
    </c:choose>
</c:if>
</c:if>
<body>
<form name=frm id=frm action="">
		<fieldset width="100%">
		<legend>��ͬ��ϸ</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
		<c:set var="payment" value=""/>
		<!--����-->
		<c:set var="singlePrice" value=""/>
        <!--�ɽ���-->
		<c:set var="amount" value=""/>
		<!--ʣ�����-->
		<c:set var="lastAmount" value=""/>
		<!--��֤��-->
		<c:set var="security" value=""/>
		<c:set var="contractNO" value=""/>
		<!--��ͬ״̬-->
		<c:set var="status" value=""/>
		<!--ϵͳ����-->
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
                <td align="right"> ����ģʽ ��</td>
                <td align="left">
                	<c:if test="${row.trademode==1}">����</c:if>
                	<c:if test="${row.trademode==2}">����</c:if>
					<c:if test="${row.trademode==3}">�б�</c:if>
                </td>
				<td align="right"> ��ͬ״̬ ��</td>
                <td align="left">
                	<c:if test="${row.status==0}">��Ч
					<db:select table="v_commext" var="colRow" columns="commid" where="commid=${param.commodityID} and to_date(str19,'yyyy-mm-dd')<to_date('${sysDate}','yyyy-mm-dd')">
                    (�ѵ���)
					</db:select>
					</c:if>
                	<c:if test="${row.status==1}">��Լ���</c:if>
					<c:if test="${row.status==2}">�ϳ�</c:if>
					<c:if test="${row.status==3}">����Լ</c:if>
					<c:if test="${row.status==4}">����Լ</c:if>
                </td>
				<td colspan="2">&nbsp;</td>
                </tr>
        <tr height="25">
                <td align="right"> ��ĺ� ��</td>
                <td align="left">
                	${row.code}
                </td>
                <td align="right"> ��ͬ�� ��</td>
                <td align="left">
                	${row.contractid}
					<c:set var="contractNO" value="${row.contractid}"/>
                </td>
                <td align="right"> Ʒ�� ��</td>
                <td align="left">${row.str1}</td>
        </tr>
			  <tr height="25">
              <td align="right"> ���׻�Ա�� ��</td>
              <td align="left">${row.usercode}</td>
              <td align="right"> ��ȫ�� ��</td>
              <td align="left">${row.name}</td>
        	  <td align="right"> ����ȫ�� ��</td>
              <td align="left">${row.str2}</td>
        </tr>
        <tr height="25">
              <td align="right"> ���� ��</td>
              <td align="left">
			  <c:set var="singlePrice" value="${row.price}"/>
			  <fmt:formatNumber value="${row.price}" pattern="<%=FUNDPATTERN%>"/></td>
              <td align="right"> ���� ��</td>
              <td align="left">${row.amount}</td>
              <td align="right"> �ɽ���� ��</td>
              <td align="left"><fmt:formatNumber value="${row.amount*row.price*row.tradeunit}" pattern="<%=FUNDPATTERN%>"/></td>
         </tr>
		 <tr height="25">
           <td align="right"> ��ʼ��֤�� ��</td>
              <td align="left"><fmt:formatNumber value="${row.bail}" pattern="<%=FUNDPATTERN%>"/></td>
		   <td  colspan="4">&nbsp;</td>
		 </tr>
         <tr height="25">   
              <td align="right"> ʣ�ౣ֤�� ��</td>
              <td align="left"><fmt:formatNumber value="${row.lastBail}" pattern="<%=FUNDPATTERN%>"/></td>
              <td align="right"> �ѽ������� ��</td>
              <td align="left"><fmt:formatNumber value="${row.poundage}" pattern="<%=FUNDPATTERN%>"/></td>
              <td align="right"> �ѽ����� ��</td>
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
		      <td align="right"> Ӧ���ܶ� ��</td>
              <td align="left">
              <fmt:formatNumber value="${row.amount*row.price+row.poundage}" pattern="<%=FUNDPATTERN%>"/></td>
              <td align="right"> �ѽ��ܶ� ��</td>
              <td align="left"><fmt:formatNumber value="${row.poundage+row.lastBail+payment}" pattern="<%=FUNDPATTERN%>"/></td>    
              <td align="right"> ��Ӧ���� ��</td>
              <td align="left"><fmt:formatNumber value="${(row.amount*row.price+row.poundage)-(row.poundage+row.lastBail+payment)}" pattern="<%=FUNDPATTERN%>"/></td>
         </tr>
        </table>	
    </db:select_HisBarDetail>

			<BR>
        </span>
		</fieldset>
		<fieldset width="100%">
		<legend>�տ��¼</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
          <tr height="25">
          <td align="center" width="35%">����</td>
          <td align="center" width="20%">���</td>
		  <td align="center" width="20%">��ע</td>
          <td align="center" width="45%">��Ӧ�������</td>
          </tr>
		  <%/*
          <db:selectUnion_MoneyRecord var="row" contractid="${contractNO}" operation="${OPERATION}">
          */%>
	  <%
	   try{
		  String jspContractNO=pageContext.getAttribute("contractNO").toString();//��ͬ��
		  String jspOPERATION=pageContext.getAttribute("OPERATION").toString();//ҵ�����
		  long tempAmount=0l;//�ɽ�����
		  long tempLastAmount=0l;//ʣ�����
		  double tempPrice=0d;//�ɽ��۸�
		  double tempSecurity=0d;//��֤��ϵ��
		  double curAmount=0d;//���������Ч������
          double listGoodsTotal=0d;//������������������
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
			  <input type="submit" onclick="return frmChk()" class="btn" value="ȷ��">&nbsp;&nbsp;-->
			  <input type="button" onclick="window.print();" class="btn" value="��ӡ">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">
            </div></td>
          </tr>
     </table>
	 <!--
	 <fieldset>
		<legend>��ͬ״̬����</legend>
		<BR>
		<span>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     	    <input type="hidden" name="id" value="${param.id}">
     	    <tr height="25">
     		     <td>��ͬ״̬:
     		     <input type="radio" name="finished" <c:if test="${status==1}">checked</c:if> value="1">��Լ���&nbsp;&nbsp;
     		     <input type="radio" name="finished" <c:if test="${status==0}">checked</c:if> value="0">��Ч
     		     </td>
     	    </tr>
          <tr height="25">
          	<td width="40%"><div align="center" class="Noprint">
			      <input type="button" name="conStatusBtn" onclick="return controlStatus();" class="btn" value="���ú�ͬ״̬">&nbsp;&nbsp;
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
		alert("����ģ�岻��Ϊ�գ�");
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
//���ú�ͬ״̬
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