<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<!-- IE5.5���� -->
<html>
  <head>
     <title class="Noprint">��ӡ����֪ͨ��</title>
     <meta http-equiv="Content-Type" content="text/html; charset=GBK">
     <style type="text/css">
     </style>
  </head>
<body>
<!--�����ʾ��ʽ-->
<c:set var="FUNDPATTERN" value="<%=FUNDPATTERN%>"/>
<!--���γ��ⵥ��������-->
<c:set var="curamount" value="0"/>
<!--������οⵥ�ж��ٻ�������-->
<db:select var="row" table="v_outrecord" columns="sum(amount) as s" where="outlogid='${param.outlogId}'">
  <c:choose>
    <c:when test="${not empty row.s}">
       <c:set var="curamount" value="${row.s}"/>
	</c:when>
	<c:otherwise>
       <c:set var="curamount" value="0"/>
	</c:otherwise>
   </c:choose>
</db:select>
<table width="100%"  border="0" align="center">
    <!--�����г�-->
    <c:set var="belongMarket" value=""/>
	<!--��ͬ״̬-->
    <c:set var="status" value=""/>
	<!--�ɽ��۸�-->
	<c:set var="price" value=""/>
	<!--�洢��λ-->
	<c:set var="str2" value=""/>
	<!--��ĺ�-->
	<c:set var="code" value=""/>
	<!---��¼�ȼ�����Ϣ->
	<c:set var="str3" value=""/>
    <!--�ɽ�����-->
	<c:set var="tradeDate" value=""/>
	<!--�ɽ����ⵥ��������ʾ�ɽ�ʱ��-->
	<c:set var="disTradeDate" value=""/>
	<c:set var="tempTradeDate" value=""/>
	<!--������-->
	<c:set var="buyerName" value=""/>
	<!--��������-->
    <c:set var="sellerName" value=""/>
	<db:select table="v_hisbargain" var="row" columns="tradedate" where="commodityid=${param.commodityID}">
	    <c:set var="tempTradeDate" value="${fn:substring(row.tradedate,0,10)}"/>
	</db:select>
    <%
	    //ʱ���ʽת��(XX��XX��XX��)
	    Object tradeDateObj=pageContext.getAttribute("tempTradeDate");
		String disTradeDate="";
		if(tradeDateObj!=null){
	        disTradeDate=ManaUtil.convertDateFormatExt(tradeDateObj.toString(),"-");
		}
	%>
	<c:set var="disTradeDate" value="<%=disTradeDate%>"/>
	<db:select_HisBarDetail var="row" where="u1.commodityid=${param.commodityID} and u1.commodityid=u3.id and u3.id=u4.commid and u2.firmid=u1.userid and u1.contractID=${param.contractID}">
	<c:set var="belongMarket" value="${row.str7}"/>
	<c:set var="status" value="${row.status}"/>
	<c:choose>
	<c:when test="${row.trademode==0}">
	<db:select var="rowColumn" table="v_tradeuserext" columns="name" where="usercode='${row.userid}'">
	<c:set var="buyerName" value="${rowColumn.name}"/>
	</db:select>
	</c:when>
	<c:when test="${row.trademode==1}">
	<db:select var="rowColumn" table="v_tradeuserext" columns="name" where="usercode='${row.bluserid}'">
	<c:set var="buyerName" value="${rowColumn.name}"/>
	</db:select>
	</c:when>
	<c:otherwise>
	</c:otherwise>
	</c:choose>
	<c:choose>
    <c:when test="${row.trademode==0}">
	<db:select var="rowColumn" table="v_tradeuserext" columns="name" where="usercode='${row.bluserid}'">
	<c:set var="sellerName" value="${rowColumn.name}"/>
	</db:select>
	</c:when>
	<c:when test="${row.trademode==1}">
	<db:select var="rowColumn" table="v_tradeuserext" columns="name" where="usercode='${row.userid}'">
	<c:set var="sellerName" value="${rowColumn.name}"/>
	</db:select>
	</c:when>
	<c:otherwise>
	</c:otherwise>
	</c:choose>
	<tr>
    <td align="right" colspan="2" style="font-size:19;">
    	<br>
    	NO.${param.outID}
    	<br>
		<font style="font-size:14px;">����ģʽ:
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
		</font>
		<br>
    	</td>
  </tr>
	<tr>
    <td align="center" style="font-size:22;font-family:Verdana;" colspan="2">���۽���<br><br>
      <font style="font-size:20;font-family:Verdana;">�� �� ͨ ֪ ��</font>	
    </td>
  </tr>
  <tr>
    <td class="fontSize" colspan="2">
<br><br>
������λ(${sellerName}):<br><br>

�����򷽵�λ${buyerName}��XXXXXXXXXXXXXXXǩ���ġ����۽��׺�ͬ��,��ͬ���${row.contractid}�����ѽ�${curamount}(${param.str6})
����������г�ָ�������ʻ�,���㵥λƾ��֪ͨ���򷽵���Ч֤�����±����л����������⡣��������,
�뼰ʱ�����г���ϵ��
<br><br><br>
    </td>
  </tr>
  <tr>
  <c:set var="price" value="${row.price}"/>
  <c:set var="str2" value="${row.str2}"/>
  <c:set var="code" value="${row.code}"/>
  <c:set var="str3" value="${row.str3}"/>
  <c:set var="tradeDate" value="${row.tradedate}"/>
  </db:select_HisBarDetail>
    <td></td>
  </tr>
  <%
      
	  BigDecimal totalAmount=new BigDecimal("0");
  %>
  <tr><td align="left" class="fontSize">����:</td><td align="right" class="fontSize">��λ:${param.str6}��Ԫ</td></tr>
  <tr>
    <td colspan="2">
    	<table width="100%" border="0" class="printTemp" cellpadding="0" cellspacing="0">
      <tr height="50">
        <td align="center" class="td_printMd" style="width:1.5cm">��ĺ�</td>
        <!--<td align="center" class="td_printMd" style="width:4cm">������λ</td>-->
        <td align="center" class="td_printMd" style="width:3.5cm">ʵ�ʴ洢�������</td>
        <td align="center" class="td_printMd" style="width:2cm">��Ʒ����</td>
		<td align="center" class="td_printMd" style="width:2.5cm">�۸�</td>
        <td align="center" class="td_printMd" style="width:2cm">�����������(${param.str6})</td>
        <td align="center" class="td_printRd" style="width:2.5cm">���</td>
      </tr>
      <c:set var="recordTotal" value="0"/>
      <db:select var="rowCacu" table="v_outrecord" columns="count(*) as n" where="outlogid='${param.outlogId}' and amount>0">
          <c:set var="recordTotal" value="${rowCacu.n}"/>
      </db:select>
	  <c:choose>
	  <c:when test="${recordTotal>0}"><!--�������¼ʱ��ʾ��ʽ-->
      <tr height="22">
      <c:set var="i" value="1"/>
      <!--�������������-->
        <c:set var="totalAmount" value="0"/>
        <td style="width:1.5cm" rowspan='<c:if test="${recordTotal>7}">${recordTotal}</c:if><c:if test="${recordTotal<=7}">7</c:if>' align="center" class="td_printMd">${code}</td>
        <!--<td style="width:4cm" rowspan='<c:if test="${recordTotal>7}">${recordTotal}</c:if><c:if test="${recordTotal<=7}">7</c:if>' align="center" class="td_printMd"><font style="font-size:16px;">
        <c:choose>
		  <c:when test="${not empty str2}">
             ${str2}
		  </c:when>
		  <c:otherwise>
             &nbsp;
		  </c:otherwise>
		</c:choose>
		</font></td>-->
      <%
	    DBBean bean=null;
		ResultSet rs=null;
	    try{
		    HashMap mapGrade=new HashMap();
		    String str3=pageContext.getAttribute("str3").toString();
			//mapGrade=ManaUtil.addPlaceGrade(str3);
		    StringBuffer sql =new StringBuffer();
			String outlogId=request.getParameter("outlogId");
			String tempPlace=null;//�����ص�
			BigDecimal curAmount=null;//�����������
			String tempGrade=null;//�ȼ�
			bean=new DBBean(JNDI);
			sql.append("select u1.place,u1.amount,u4.str4,u3.price from v_outrecord u1,v_outlog u2,v_hisbargain u3,v_commext u4 where outlogid='"+outlogId+"' and u1.outlogid=u2.id and u2.contractid=u3.contractid and u3.commodityid=u4.commid ");
			sql.append(" and u1.amount>0");
			rs=bean.executeQuery(sql.toString());
			while(rs.next()){
				tempPlace=rs.getString("place");
				//tempGrade=(String)mapGrade.get(tempPlace);
				curAmount=rs.getBigDecimal("amount");
				totalAmount=totalAmount.add(curAmount);
		%>
        <c:if test="${i>1}">
          <tr height="22">
        </c:if>
        <td style="width:3.5cm" align="center" class="td_printMd"><%=delNull(tempPlace)%></td>
        <td style="width:2cm" align="center" class="td_printMd"><%=delNull(rs.getString("str4"))%></td>
		<td style="width:2.5cm" align="center" class="td_printMd"><%=rs.getDouble("price")%></td>
        <td style="width:2cm" align="center" class="td_printMd" style="text-align:right;">
        <%
		    if(curAmount.intValue()-curAmount.doubleValue()>0){	
		%>
		<fmt:formatNumber value="<%=curAmount%>" pattern="<%=FUNDPATTERNEXT%>"/>
		<%}else{%>
            <%=curAmount%>
		<%}%>
		</td>
        <c:if test="${i>1}">
        </tr>
        </c:if>
        <c:if test="${i==1}">
          <td style="width:2.5cm" rowspan='<c:if test="${recordTotal>7}">${recordTotal}</c:if><c:if test="${recordTotal<=7}">7</c:if>' class="td_printRd" style="text-align:right;" align="center"><fmt:formatNumber value="${curamount*price}" pattern="${FUNDPATTERN}"/></td>
        </c:if>
        <c:set var="i" value="${i+1}"/>
	<%
		  }
		  rs.close();
		  bean.close();
		  }
          catch(Exception e)
          {
  	          e.printStackTrace();
              errOpt();
          }
	      finally{
    	      try{if(rs!=null)rs.close();}catch(Exception ex){}
    	      if(bean!=null)bean.close();
          }
	%>
        <c:if test="${recordTotal<=7}">
        <c:forEach var="each" begin="${recordTotal}" end="6" step="1">
        <tr height="22">
        <td class="td_printMd">&nbsp;</td>
        <td class="td_printMd">&nbsp;</td>
        <td class="td_printMd">&nbsp;</td>
	    <td class="td_printMd">&nbsp;</td>
        </tr>
        </c:forEach>
        </c:if>
      </tr>
	  </c:when>
	  <c:otherwise><!--û�������¼ʱ��ʾ��ʽ-->
	  <tr height="22">
        <td style="width:1.5cm" rowspan='<c:if test="${recordTotal>7}">${recordTotal}</c:if><c:if test="${recordTotal<=7}">7</c:if>' align="center" class="td_printMd">${code}</td>
        <!--<td style="width:4cm" rowspan='<c:if test="${recordTotal>7}">${recordTotal}</c:if><c:if test="${recordTotal<=7}">7</c:if>' align="center" class="td_printMd"><font style="font-size:16px;">
		<c:choose>
		  <c:when test="${not empty str2}">
             ${str2}
		  </c:when>
		  <c:otherwise>
             &nbsp;
		  </c:otherwise>
		</c:choose>
		</font></td>-->
        <td style="width:3.5cm" align="center" class="td_printMd">&nbsp;</td>
        <td style="width:2cm" align="center" class="td_printMd">&nbsp;</td>
        <td style="width:2.5cm" align="center" class="td_printMd">&nbsp;</td>
		<td style="width:2cm" align="center" class="td_printMd">&nbsp;</td>
        <td style="width:2.5cm" rowspan='<c:if test="${recordTotal>7}">${recordTotal}</c:if><c:if test="${recordTotal<=7}">7</c:if>' class="td_printRd" style="text-align:right;" align="center"><fmt:formatNumber value="${curamount*price}" pattern="${FUNDPATTERN}"/></td>
		<c:forEach var="each" begin="${recordTotal+1}" end="6" step="1">
          <tr height="22">
          	<td class="td_printMd">&nbsp;</td>
          	<td class="td_printMd">&nbsp;</td>
          	<td class="td_printMd">&nbsp;</td>
			<td class="td_printMd">&nbsp;</td>
          </tr>
          </c:forEach>
		</tr>
	  </c:otherwise>
	  </c:choose>
      <tr height="30">
        <td colspan="4" align="center" class="td_printBd">�ϼ�:</td>
        <td align="center" class="td_printBd" style="text-align:right;">
		<%
		    if(ManaUtil.disBD(totalAmount).intValue()-ManaUtil.disBD(totalAmount).doubleValue()>0){
		%>
		<fmt:formatNumber value="<%=ManaUtil.disBD(totalAmount)%>" pattern="<%=FUNDPATTERNEXT%>"/>
		<%}else{%>
		<%=ManaUtil.disBD(totalAmount)%>
		<%}%>
		</td>
        <td align="center" style="font-size:14px;font-family:Verdana;text-align:right;"><fmt:formatNumber value="${curamount*price}" pattern="${FUNDPATTERN}"/></td>
      </tr>
    </table></td>
  </tr>
  <tr>
  	<td align="right" colspan="2"  class="fontSize" valign="bottom">
  	<br><br><br>
	XXXXXXXXXXX�����г�&nbsp;&nbsp;&nbsp;
  	</td>
   </tr>
  <tr>
  	<td align="right" colspan="2"  class="fontSize" valign="bottom">
	<table border="0">
	<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
	<tr>
	<td style="width:13.5cm;" align="right" class="fontSize"><!--����ʱ��-->&nbsp;</td>
	<td style="width:5.5cm;text-align:left;" class="fontSize">&nbsp;&nbsp;
	<%=sqlDate.getYear()+1900%>��<%=sqlDate.getMonth()+1%>��<%=sqlDate.getDate()%>��</td>
	</tr>
	</table>
  	</td>
   </tr>
   <tr>
   <td align="right" colspan="2"  class="fontSize" valign="bottom">
   <table border="0">
   <%
	  String tradeDate =pageContext.getAttribute("tradeDate").toString().substring(0,10);
   %>
   </table>
   </td>
   </tr>
   <tr>
     <td colspan="2" class="fontRemark">
	 <!--
     ��ע:<br>
     1��������֪ͨ��һʽ����:�д��ֹ�˾,����˫����һ�ݡ�<br>
	 2����ϵ�绰��0551-2880789��2288850��2870671��2885802
     -->
	 </td>	
   </tr>
</table>
<br><br>
<div align="center" class="Noprint">
    <c:set var="finished" value=""/>
    <db:select table="v_outlog" columns="finished" var="v_result" where="id=${param.outlogId}">
	  <c:set var="finished" value="${v_result.finished}"/>
	</db:select>
	<c:if test="${status<1&&finished==2}">
	  <input name="printBtn" type="button" onclick="webPrint();" class="btn" value="��ӡ">&nbsp;&nbsp;
	</c:if>
    <input name="printBtn" type="button" onclick="window.close();" class="btn" value="ȡ��">
</div>
</body>
</html>
<script language="javascript">
	function webPrint(){
	    window.print();	
	}
</script>