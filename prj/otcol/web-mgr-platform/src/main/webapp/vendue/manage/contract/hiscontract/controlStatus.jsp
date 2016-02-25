<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<%  //xieying
try
{
	String pd = TradeDAOFactory.getDAO().getPD();
	float tradeunit=Float.valueOf(request.getParameter("tradeunit"));//批吨系数
	request.setAttribute("pd",pd);
	request.setAttribute("DW",DW);
	request.setAttribute("DWS",DWS);
	request.setAttribute("tradeunit",tradeunit);
%>

<!--取得参数-->
<html>
  <head>
	<title class="Noprint">合同状态设置</title>
</head>
<c:if test="${not empty param.opt}">
  <%
    Timestamp timestamp=new Timestamp(System.currentTimeMillis());
  %>
  <!--合同确定-->
  <c:if test="${param.opt=='contractassurance'}">
     <c:catch var="exceError">
	 <!--是否可以设置状态标志-->
     <c:set var="modFlag" value="true"/>
	 <!--判断是否有出库记录-->
     <c:set var="cnt" value="0"/>
     <!--判断是否有出库记录-->
     <db:select var="row" table="v_outlog" columns="count(contractid) as c" where="contractid=${param.contractId}">
       <c:set var="cnt" value="${row.c}"/>
     </db:select>
     <c:choose>
       <c:when test="${cnt<=0}">
         <c:set var="modFlag" value="false"/>
	   </c:when>
	   <c:otherwise>
	     <c:set var="modFlag" value="true"/>
       </c:otherwise>
     </c:choose>
     <db:select var="row" table="v_outlog" columns="contractid" where="contractid=${param.contractId} and finished<>2">
     <c:set var="modFlag" value="false"/>
     </db:select>
  <!--判断执行什么状态操作   test="${modFlag=='true'}" 必需生成出库单  -->

  <c:choose>
    <c:when test="${modFlag=='true'}">  <!-- 不用生成出库单 -->
	  <c:choose>
	    <c:when test="${param.finished>1}">
	      <db:update table="v_hisbargain" status="${param.finished}" note="${param.remark}" where="commodityid=${param.commodityID}  and contractid=${param.contractID}"/>
          <SCRIPT LANGUAGE="JavaScript">
              alert("设置合同状态成功！");
          </SCRIPT>
		</c:when>
		<c:otherwise>
	    <!--选择执行确定结果-->
	    <c:choose>
	    <c:when test="${param.assurContract=='1'}">
		<!--判断是否已经是提货完毕状态-->
		<c:set var="complet" value="false"/>
		<db:select table="v_hisbargain" var="v_result" columns="status" where="commodityid=${param.commodityID}  and contractid=${param.contractID}">
           <c:if test="${v_result.status==1}">
              <c:set var="complet" value="true"/>
		   </c:if>
		</db:select>
		<c:choose>
		  <c:when test="${complet=='false'}">
		<%
		 Connection conn = null;
         PreparedStatement ps = null;
         ResultSet rs = null;

	     try{
			 Context initContext = new InitialContext();
             Context envContext  = (Context)initContext.lookup("java:/comp/env");
             DataSource ds = (DataSource)envContext.lookup(JNDI);
             conn = ds.getConnection();
             conn.setAutoCommit(false);
	         long contractID=0l;
	         double amount=0d;
			 int result=0;
			 double breakAmount=0d;
	         contractID=Long.parseLong(request.getParameter("contractId"));//合同号
			 if(pd.equals("true"))
			 {
				 amount=Double.parseDouble(request.getParameter("amount"))*tradeunit;//实际提货数量
				 breakAmount=Double.parseDouble(request.getParameter("breakAmount"))*tradeunit;//违约数量
			 }
			 else
			 {
				 amount=Double.parseDouble(request.getParameter("amount"));//实际提货数量
				 breakAmount=Double.parseDouble(request.getParameter("breakAmount"));//违约数量
			 }
			 result=Integer.parseInt(request.getParameter("result"));//执行结果
			 String remark=request.getParameter("remark");
			 String commodityID=request.getParameter("commodityID");//商品id
	         StringBuffer sql=new StringBuffer();
			 //调用提货完毕接口
	          //先取一个板块号
	         /*int partitionid=0;
	         sql.append("select partitionid from syspartition where rownum=1 and validflag=1");
             ps=conn.prepareStatement(sql.toString());
	         rs=ps.executeQuery();
	         if(rs.next()){
	             partitionid=rs.getInt("partitionid");
	         }
	         rs.close();
	         ps.close();*/
	         //KernelEngineDAO dao = GlobalContainer.getEngineDAO(partitionid);
	         //dao.finishContract(contractID,result,amount,breakAmount);
			 DeliveryAction delivery=(DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();
			 delivery.finishContract(contractID,result,amount,breakAmount);
			 //修改合同状态操作临时表
             sql=new StringBuffer();
			 sql.append("update v_contractoperstatus set status=2,note='"+remark+"',opertime=to_date('"+disDate(timestamp)+"',");
			 sql.append("'yyyy-mm-dd hh24:mi:ss') where contractid="+contractID+"");
			 ps=conn.prepareStatement(sql.toString());
			 ps.executeUpdate();
			 ps.close();
             //保存备注
             sql=new StringBuffer();
			 sql.append("update v_hisbargain set note='"+remark+"' where commodityid="+commodityID+"  and contractid="+contractID+"");
             ps=conn.prepareStatement(sql.toString());
			 ps.executeUpdate();
			 ps.close();
			 conn.commit();
             conn.setAutoCommit(true);
		%>
	     <SCRIPT LANGUAGE="JavaScript">
              alert("设置合同状态成功！");
         </SCRIPT>
		<%
	     }catch(SQLException e){
			 conn.rollback();
	         e.printStackTrace();
             errOpt();
		 %>
          <SCRIPT LANGUAGE="JavaScript">
              alert("实际提货数量大于最大可提货数量,设置合同状态失败！");
           </SCRIPT>
		 <%
	     }catch(Exception e){
			 conn.rollback();
		     e.printStackTrace();
             errOpt();
		 %>
		 <SCRIPT LANGUAGE="JavaScript">
             alert("实际提货数量大于最大可提货数量,设置合同状态失败！");
         </SCRIPT>
	   <%
		 }finally{
			 if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
             if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
	         try{
                 conn.close();
             }catch (Exception e){}
             conn = null;
	     }
	   %>
	   </c:when>
	   <c:otherwise>
         <SCRIPT LANGUAGE="JavaScript">
             alert("合同已经是提货完毕状态,不能再设置！");
         </SCRIPT>
	   </c:otherwise>
	   </c:choose>
	   </c:when>
	   <c:when test="${param.assurContract=='2'}">
	       <db:update table="v_contractoperstatus" status="0" where="contractid=${param.contractId}"/>

           <SCRIPT LANGUAGE="JavaScript">
              alert("返回审核成功！");
           </SCRIPT>
	   </c:when>
	   <c:when test="${param.assurContract=='3'}">
	       <db:update table="v_contractoperstatus" status="-1" where="contractid=${param.contractId}"/>

           <SCRIPT LANGUAGE="JavaScript">
              alert("返回录入成功！");
           </SCRIPT>
	   </c:when>
	   <c:otherwise>
	       <SCRIPT LANGUAGE="JavaScript">
              alert("合同已经是提货完毕状态,不能再设置！");
           </SCRIPT>
	   </c:otherwise>
	   </c:choose>
		</c:otherwise>
	  </c:choose>
	</c:when>
	 <c:otherwise>
       <SCRIPT LANGUAGE="JavaScript">
          alert("合同对应的出库记录是未完毕状态或者是没有出库记录,设置状态失败！");
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
     <!--合同录入-->
	 <%
	 if("contractwrite".equals(request.getParameter("opt"))){
	 DBBean bean=null;
	 ResultSet rs=null;
	 try{
         int exeResult=Integer.parseInt(request.getParameter("result"));
         String contractId=request.getParameter("contractId");
		 double amount=0d;
		 double breakAmount=0d;  //违约数量
		 double exauAmount=0d;  //损耗数量
		 if(pd.equals("true"))
		 {
			 amount=Double.parseDouble(request.getParameter("amount"))*tradeunit;
			 breakAmount=Double.parseDouble(request.getParameter("breakAmount"))*tradeunit;
			 exauAmount=Double.parseDouble(request.getParameter("exauAmount"))*tradeunit;
		 }
		 else
		 {
			 amount=Double.parseDouble(request.getParameter("amount"));
			 breakAmount=Double.parseDouble(request.getParameter("breakAmount"));
			 exauAmount=Double.parseDouble(request.getParameter("exauAmount"));
		 }
		 String remark=request.getParameter("remark");
         String operUser=AclCtrl.getLogonID(request);
		 boolean exist=false;
		 boolean outlogFlag=true;//判断合同出库单是否全部审核
		 int cntOutlog=0;//判断是否有出库记录
		 bean=new DBBean(JNDI);
		 StringBuffer sql=new StringBuffer();
		 sql.append("select count(contractid) as c from v_outlog where contractid="+contractId+"");
         rs=bean.executeQuery(sql.toString());
		 if(rs.next()){
		     cntOutlog=rs.getInt("c");
		 }
		 rs.close();
		 bean.closeStmt();
		 if(cntOutlog>0){
		     sql=new StringBuffer();
		     sql.append("select contractid from v_outlog where contractid="+contractId+" and finished<>2");
		     rs=bean.executeQuery(sql.toString());
		     if(rs.next()){
                 outlogFlag=false;
             }
		 }else{
		     outlogFlag=false;
		 }
		 rs.close();
		 bean.closeStmt();
		 if(outlogFlag){//不用生成出库单   //if(outlogFlag){  必需生成出库单
		 sql=new StringBuffer();
		 sql.append("select contractid from v_contractoperstatus where contractid="+contractId+"");
		 rs=bean.executeQuery(sql.toString());
		 if(rs.next()){
		     exist=true;
		 }
		 rs.close();
		 bean.closeStmt();
		 sql=new StringBuffer();
		 if(exist==false){
		     sql.append("insert into v_contractoperstatus(id,contractid,actualamount,fellbackamount");
			 sql.append(",exauamount,note,status,opertime,operuser,exeresult) values(SP_V_contractoperstatus.nextval");
			 sql.append(","+contractId+","+amount+","+breakAmount+","+exauAmount+",'"+remark+"',0,");
			 sql.append("to_date('"+disDate(timestamp)+"','yyyy-mm-dd hh24:mi:ss'),'"+operUser+"',"+exeResult+")");
			 bean.executeUpdate(sql.toString());
			 bean.close();
		 }else{
		     sql.append("update v_contractoperstatus set actualamount="+amount+",fellbackamount=");
			 sql.append(""+breakAmount+",exauamount="+exauAmount+",note='"+remark+"',status=0,opertime=");
			 sql.append("to_date('"+disDate(timestamp)+"','yyyy-mm-dd hh24:mi:ss'),operuser='"+operUser+"'");
		     sql.append(",exeresult="+exeResult+" where contractid="+contractId+"");
			 bean.executeUpdate(sql.toString());
			 bean.close();
		 }
     %>
	 <SCRIPT LANGUAGE="JavaScript">
          alert("合同录入成功！");
     </SCRIPT>
<%
	     }else{
%>
     <SCRIPT LANGUAGE="JavaScript">
          alert("出库单没有审核或者没有出库记录,合同不能录入！");
     </SCRIPT>
<%
	     }
	 }catch(Exception e){
	     e.printStackTrace();
%>
     <SCRIPT LANGUAGE="JavaScript">
          alert("合同录入失败！");
     </SCRIPT>
<%
	 }
	 finally{
    	  try{if(rs!=null)rs.close();}catch(Exception ex){}
    	  if(bean!=null)bean.close();
     }
	 }
%>
	 <%
	 if("contractexam".equals(request.getParameter("opt"))){
		 ResultSet rs=null;
		 DBBean bean=null;
         try{
             int appear=Integer.parseInt(request.getParameter("appear"));
			 int examContract=Integer.parseInt(request.getParameter("examContract"));
			 String contractId=request.getParameter("contractId");
			 String remark=request.getParameter("remark");
			 boolean outlogFlag=true;//判断合同出库单是否全部审核
			 int cntOutlog=0;//判断是否有出库记录
			 String operUser=AclCtrl.getLogonID(request);
			 int exeOper=1;
			 int result=1;//执行修改数据库操作返回值
			 bean=new DBBean(JNDI);
			 StringBuffer sql=new StringBuffer();
			 sql.append("select count(contractid) as c from v_outlog where contractid="+contractId+"");
             rs=bean.executeQuery(sql.toString());
		     if(rs.next()){
		         cntOutlog=rs.getInt("c");
		     }
		     rs.close();
		     bean.closeStmt();
             if(cntOutlog>0){
				 sql=new StringBuffer();
			     sql.append("select contractid from v_outlog where contractid="+contractId+" and finished<>2");
		         rs=bean.executeQuery(sql.toString());
		         if(rs.next()){
                     outlogFlag=false;
                 }
		         rs.close();
		         bean.closeStmt();
			 }else{
			     outlogFlag=false;
			 }
		     if(outlogFlag){//不用生成出库单   //if(outlogFlag){  必需生成出库单
		         sql=new StringBuffer();
                 if(appear==0){
				     if(examContract==1){
					     exeOper=1;
			             sql.append("update v_contractoperstatus set status="+exeOper+",note='"+remark+"',");
				         sql.append("opertime=to_date('"+disDate(timestamp)+"','yyyy-mm-dd hh24:mi:ss')");
						 sql.append(",operuser='"+operUser+"' where contractid="+contractId+"");
				     }else{
				         exeOper=-1;
						 sql.append("update v_contractoperstatus set status="+exeOper+" where contractid=");
						 sql.append(""+contractId+"");
				     }
				 result=bean.executeUpdate(sql.toString());
				 bean.close();
	             if(result!=-1){
					 
					 if(examContract==1)
					 {
	  %>
	                 <SCRIPT LANGUAGE="JavaScript">
                         alert("合同审核成功！");
                     </SCRIPT>
	  <%
					 }else{
	 %>
					 <SCRIPT LANGUAGE="JavaScript">
                         alert("返回录入成功！");
                     </SCRIPT>
	 <%
					 }
	             }else{					 
	  %>
	                 <SCRIPT LANGUAGE="JavaScript">
                         alert("合同审核失败！");
                     </SCRIPT>
      <%
				 }
	             }else{  
	  %>
                 <SCRIPT LANGUAGE="JavaScript">
                     alert("合同不是录入状态,不能审核！");
                 </SCRIPT>
	  <%
	             }
		     }else{
	  %>
	         <SCRIPT LANGUAGE="JavaScript">
                 alert("出库单没有审核或者没有出库记录,合同不能审核！");
             </SCRIPT>
	  <%
		     }
		 }catch(Exception e){
			 e.printStackTrace();
	 %>
             <SCRIPT LANGUAGE="JavaScript">
                 alert("合同审核失败！");
             </SCRIPT>
	 <%
		 }
		 finally{
    	     try{if(rs!=null)rs.close();}catch(Exception ex){}
    	     if(bean!=null)bean.close();
         }
	 }
	 %>
	 <SCRIPT LANGUAGE="JavaScript">
	<!--
	window.close();
	//-->
	</SCRIPT>
</c:if>
<body>
<form name=frm id=frm action="" targetType="hidden"  method="post">
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
		<!--合同状态-->
		<c:set var="status" value=""/>
		<!--执行结果-->
		<c:set var="result" value=""/>
		<!--实际提货数量-->
		<c:set var="actualAmount" value=""/>
		<!--违约数量-->
		<c:set var="fellBackAmount" value=""/>
		<!--备注-->
		<c:set var="remark" value=""/>
		<!--交易商代码-->
		<c:set var="firmid" value=""/>
		<db:select_HisBarDetail var="row" where="u1.commodityid=${param.commodityID} and u1.commodityid=u3.id and u3.id=u4.commid and u2.firmid=u1.userid and contractid=${param.contractID}">
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  	<c:set var="contractID" value="${row.contractid}"/>
		  	<c:set var="amount" value="${row.amount}"/>
		  	<c:set var="price" value="${row.price}"/>
		  	<c:set var="commodityID" value="${row.id}"/>
		  	<c:set var="lastAmount" value="${row.lastAmount}"/>
		  	<c:set var="b_security" value="${row.b_bail}"/>
			<c:set var="s_security" value="${row.s_bail}"/>
		    <c:set var="status" value="${row.status}"/>
			<c:set var="result" value="${row.result}"/>
			<c:set var="firmid" value="${row.firmid}"/>
			<c:set var="actualAmount" value="${row.actualamount}"/>
			<c:set var="fellBackAmount" value="${row.fellbackamount}"/>
			<c:set var="remark" value="${row.note}"/>
			<input type="hidden" name="commodityID" value="${row.id}">
			<input type="hidden" name="contractId" value="${row.contractid}">
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
        <td align="right">买方编号 ：</td>
        <td align="left">
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
		<td align="right">卖方编号 ：</td>
        <td align="left">
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
		<td align="right"> 买方全称 ：</td>
        <td align="left">
        <c:choose>
		<c:when test="${row.trademode==0}">
		<db:select var="rowColumn" table="m_firm" columns="name" where="firmid='${row.userid}'">
		${rowColumn.name}
		</db:select>
		</c:when>
		<c:when test="${row.trademode==1}">
		<db:select var="rowColumn" table="m_firm" columns="name" where="firmid='${row.bluserid}'">
		${rowColumn.name}
		</db:select>
		</c:when>
		<c:when test="${row.trademode==2}">
		<db:select var="rowColumn" table="m_firm" columns="name" where="firmid='${row.bluserid}'">
		${rowColumn.name}
		</db:select>
		</c:when>
		<c:otherwise>
		</c:otherwise>
		</c:choose>
        </td>
        <td align="right"> 卖方全称 ：</td>
        <td align="left">
        <c:choose>
		<c:when test="${row.trademode==0}">
		<db:select var="rowColumn" table="m_firm" columns="name" where="firmid='${row.bluserid}'">
		${rowColumn.name}
		</db:select>
		</c:when>
		<c:when test="${row.trademode==1}">
		<db:select var="rowColumn" table="m_firm" columns="name" where="firmid='${row.userid}'">
		${rowColumn.name}
		</db:select>
		</c:when>
		<c:when test="${row.trademode==2}">
		<db:select var="rowColumn" table="m_firm" columns="name" where="firmid='${row.userid}'">
		${rowColumn.name}
		</db:select>
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
        <td align="right" width="20%">&nbsp;</td>
        <td align="left" width="20%">
        &nbsp;
        </td>
        </tr>
        </table>
    </db:select_HisBarDetail>
    <%
      double tempPayment=0d;//已交货款
      long tempAmount=0l;//成交数量
      long tempLastAmount=0l;//剩余货量
      double tempPrice=0d;//成交价格
      double tempSecurity=0d;//保证金系数
	  double curAmount=0d;//本次提货有效货款数
      double listGoodsTotal=0d;//本次允许最大提货数量
	  String firmid=null;//交易代码
	  int marketId=0;//所属市场
	  //求最大提货数量与违约数量
	  String contractId=null;//合同号
	  int status=-1;//合同状态
	  int exeResult=-1;//执行结果
	  BigDecimal actualAmount=new BigDecimal(0);//实际提货数量
	  BigDecimal fellBackAmount=new BigDecimal(0);//违约数量
	  BigDecimal tradeunit_o = new BigDecimal(tradeunit);
	  int appear=-1;//合同操作状态
	  String remark="";//备注
	  String operUser="";//流程状态操作用户
	  String operTime="";//流程操作时间
	  ResultSet rs=null;
	  DBBean bean=null;
	  StringBuffer sql=null;
	  try{
	  bean=new DBBean(JNDI);
	  if(pageContext.getAttribute("status")!=null&&!"".equals(pageContext.getAttribute("status"))){
         status=Integer.parseInt(pageContext.getAttribute("status").toString());
      }
     
	  //合同号
	  if(pageContext.getAttribute("contractID")!=null&&!"".equals(pageContext.getAttribute("contractID"))){
         contractId=pageContext.getAttribute("contractID").toString();
      }
       if(status==0){//当合同状态为付款提货中时才算最大提货数量
	  //查合同货款
	  sql=new StringBuffer();
	  sql.append("select sum(money) as m from (select ID,InfoDate,FirmID,Operation,ContractNo,Money");
	  sql.append(",Balance,Overdraft,FrozenCapital from v_dailymoney where contractno="+contractId+" ");
	  sql.append("and operation=406 UNION select ID,InfoDate,FirmID,Operation,ContractNo,Money");
	  sql.append(",Balance,Overdraft,FrozenCapital from v_hismoney  where contractno="+contractId+" ");
	  sql.append("and operation=406)");
	  rs=bean.executeQuery(sql.toString());
	  if(rs.next()){
	      tempPayment=rs.getDouble("m");
		  System.out.println("tempPayment is "+tempPayment);
	  }
	  rs.close();
      if(pageContext.getAttribute("amount")!=null&&!"".equals(pageContext.getAttribute("amount"))){
          tempAmount=Long.parseLong(pageContext.getAttribute("amount").toString());  //批单位
		  System.out.println("tempAmount is "+tempAmount);
      }
      if(pageContext.getAttribute("lastAmount")!=null&&!"".equals(pageContext.getAttribute("lastAmount"))){
          tempLastAmount=Long.parseLong(pageContext.getAttribute("lastAmount").toString());  //批单位
      }
      if(pageContext.getAttribute("price")!=null&&!"".equals(pageContext.getAttribute("price"))){
          tempPrice=Double.parseDouble(pageContext.getAttribute("price").toString());
      }
      if(pageContext.getAttribute("b_security")!=null&&!"".equals(pageContext.getAttribute("b_security"))){
          tempSecurity=Double.parseDouble(pageContext.getAttribute("b_security").toString());
      }

	 //求总的提货货款,所以本次有效提货货款等总货款
	 curAmount=tempPayment;
	 System.out.println("curAmount is "+curAmount+" tempPrice is "+tempPrice+" tempSecurity is "+tempSecurity+" tempAmount is "+tempAmount);
	 double price = 0d;
	 if(pd.equals("true"))
	 {
		price = tempPrice-tempSecurity/(tempAmount*tradeunit);
		if(price<=0)
			listGoodsTotal=tempSecurity/tempPrice;
		else
			listGoodsTotal=curAmount/(tempPrice-tempSecurity/(tempAmount*tradeunit));
		if(listGoodsTotal!=0)
			listGoodsTotal=listGoodsTotal/tradeunit;
	 }
	 else
	{
		 price = tempPrice-tempSecurity/tempAmount;
		 if(price<=0)
			listGoodsTotal=tempSecurity/tempPrice;
		 else
			listGoodsTotal=curAmount/(tempPrice-tempSecurity/tempAmount);
	}

	 listGoodsTotal=ManaUtil.round(listGoodsTotal,4);
	 
     if(listGoodsTotal<0){
         listGoodsTotal=0;
     }
	 if(listGoodsTotal>tempAmount){
	     listGoodsTotal=tempAmount;
	 } 
	 }
	 if(pageContext.getAttribute("result")!=null&&!"".equals(pageContext.getAttribute("result"))){
         exeResult=Integer.parseInt(pageContext.getAttribute("result").toString());
     }
     if(pageContext.getAttribute("actualAmount")!=null&&!"".equals(pageContext.getAttribute("actualAmount"))){
		 if(pd.equals("true"))
		 {
			actualAmount=new BigDecimal(pageContext.getAttribute("actualAmount").toString());  //实际提货数量
			if(actualAmount.doubleValue()!=0)
			{
				actualAmount=actualAmount.divide(tradeunit_o);
			}
		 }
		 else
		 {
			actualAmount=new BigDecimal(pageContext.getAttribute("actualAmount").toString());  //实际提货数量
		 }
     }
	 if(pageContext.getAttribute("fellBackAmount")!=null&&!"".equals(pageContext.getAttribute("fellBackAmount"))){
		 if(pd.equals("true"))
		 {
			fellBackAmount=new BigDecimal(pageContext.getAttribute("fellBackAmount").toString());  //违约数量
			if(fellBackAmount.doubleValue()!=0)
			{
				fellBackAmount=fellBackAmount.divide(tradeunit_o);
			}
		 }
		 else
		 {
			fellBackAmount=new BigDecimal(pageContext.getAttribute("fellBackAmount").toString());  //违约数量
		 }
     }
	 if(pageContext.getAttribute("remark")!=null&&!"".equals(pageContext.getAttribute("remark"))){
         remark=pageContext.getAttribute("remark").toString();
     }
	 if(status==0){
         sql=new StringBuffer();
	     sql.append("select actualAmount,fellBackAmount,status,note,exeResult,operuser,opertime");
	     sql.append(" from v_contractoperstatus where contractid="+contractId+"");
	     rs=bean.executeQuery(sql.toString());
         if(rs.next()){
			 if(pd.equals("true"))
			 {
				 actualAmount=ManaUtil.disBD(rs.getBigDecimal("actualAmount"));
				 if(actualAmount.doubleValue()!=0)
				 {
					 actualAmount=actualAmount.divide(tradeunit_o);
				 }
				 fellBackAmount=ManaUtil.disBD(rs.getBigDecimal("fellbackamount"));
				 if(fellBackAmount.doubleValue()!=0)
				 {
					fellBackAmount=fellBackAmount.divide(tradeunit_o);
				 }
			 }
			 else
			 {
				 actualAmount=ManaUtil.disBD(rs.getBigDecimal("actualAmount"));
				 fellBackAmount=ManaUtil.disBD(rs.getBigDecimal("fellbackamount"));
			 }
		     appear=rs.getInt("status");
		     remark=rs.getString("note");
		     exeResult=rs.getInt("exeResult");
             operUser=rs.getString("operuser");
			 operTime=rs.getString("opertime").substring(0,10);
	     }
	     rs.close();
	     bean.close();
	 }

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
	<BR>
    </span>
	</fieldset>
	<br>
	<fieldset>
		<legend>合同状态操作</legend>
		<BR>
		<span>
	<input type="hidden" name="listGoodsTotal" value="<%=listGoodsTotal%>">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
     	    <input type="hidden" name="id" value="${param.id}">
			<!--市场号-->
			<input type="hidden" name="marketId" value="${param.marketId}">
			 <%if(appear==0){%>
             <tr height="25">
     		 <td align="right">是否审核：</td>
			 <td>
			   <input type="radio" name="examContract" value="1">是
			   <input type="radio" name="examContract" value="2">否
			 </td>
			 </tr>
			 <%}%>
			 <%if(appear==1){%>
             <tr height="25">
     		 <td align="right">执行确定结果：</td>
			 <td>
			   <input type="radio" name="assurContract" value="1">确定
			   <input type="radio" name="assurContract" value="2">返回审核
			   <input type="radio" name="assurContract" value="3">返回录入
			 </td>
			 </tr>
			 <%}%>
     	    <tr height="25">
     		     <td align="right">合同状态：</td>
				 <td align="left">
				 <input type="radio" name="finished" <c:if test="${status==0}">checked</c:if> checked id="f_0" value="0" onclick="changeStatusType(this.value);"><%=ManaUtil.retConStatus(0)%>&nbsp;&nbsp;
				 <input type="radio" name="finished" <c:if test="${status==1}">checked</c:if> id="f_1" value="1" onclick="changeStatusType(this.value);"><%=ManaUtil.retConStatus(1)%>&nbsp;&nbsp;
				 <input type="radio" name="finished"  <c:if test="${status==2}">checked</c:if> id="f_2" value="<%=2%>" onclick="changeStatusType(this.value);"><%=ManaUtil.retConStatus(2)%>
     		     </td>
				 <td>
     	    </tr>
			<tr height="25" id="resultSpan">
               <td align="right">执行结果：</td>
			   <td align="left">
				 <input type="radio" name="result" value="0" id="0" <%if(exeResult==0){%>checked<%}%>  onclick="changeResult(this.value);">正常履约&nbsp;&nbsp;
				 <input type="radio" name="result" value="1"  id="1" <%if(exeResult==1){%>checked<%}%>  onclick="changeResult(this.value);">买方违约&nbsp;&nbsp;
				 <input type="radio" name="result" value="2"  id="2" <%if(exeResult==2){%>checked<%}%>  onclick="changeResult(this.value);">卖方违约&nbsp;&nbsp;
			     <%
				 if(appear>0){
				 %>
                 <input type="hidden" name="result" value="<%=exeResult%>">
				 <%
				 }
				 %>
			   </td>
			</tr>
			<%if(status==0){%>
			<tr height="25">
     		     <td align="right">流程状态：</td>
				 <td align="left">
				 <font color="#ff0000">
				 <%if(appear==-1){%>
                   等待录入
				 <%}else if(appear==0){%>
                   等待审核
				 <%}else if(appear==1){%>
                   等待确定
				 <%}%>
				 </font>
				 </td>
			 </tr>
			 <%}%>
            <tr height="25" id="amountSpan">
               <td align="right">
			     实际提货数量(${param.str6}<!--<%if(pd.equals("true")){%><%=DW%><%}else{%><%=DWS%><%}%>-->)：
			   </td>
			   <td align="left">
                 <input type="text" class="text" name="amount" value="<fmt:formatNumber value='<%=actualAmount%>' pattern="<%=FUNDPATTERNEXT%>"/>" onchange="controlExauAmountExt();">
			<%
				 if(status==0){
			%>
				 &nbsp;<font color="#ff0000">
				 最大提货数量(${param.str6}<!--<%if(pd.equals("true")){%><%=DW%><%}else{%><%=DWS%><%}%>-->)：
				 (<fmt:formatNumber value="<%=String.valueOf(listGoodsTotal)%>" pattern="<%=FUNDPATTERNEXT%>"/>)
				 </font>
			<%
				}
			%>
			   </td>
			</tr>
			<tr height="25" id="breakSpan">
               <td align="right">
			     违约数量(${param.str6}<!--<%if(pd.equals("true")){%><%=DW%><%}else{%><%=DWS%><%}%>-->)：
			   </td>
			   <td align="left">
                 <input type="text" class="text" name="breakAmount" value="<fmt:formatNumber value="<%=fellBackAmount%>" pattern="<%=FUNDPATTERNEXT%>"/>" onchange="controlExauAmountExt();">
			   </td>
			</tr>
			<tr height="25" id="breakSpan">
               <td align="right">
			     损耗数量(${param.str6}<!--<%if(pd.equals("true")){%><%=DW%><%}else{%><%=DWS%><%}%>-->)：
			   </td>
			   <td align="left">
                 <input type="text" class="text" name="exauAmount" <c:if test="${status!=1}">value="0"</c:if> readonly>
			   </td>
			</tr>
			<tr height="25" id="amountSpan">
               <td align="right">
			     备注：
			   </td>
			   <td align="left">
                 <textarea name="remark" class="normal"><%=delNull(remark)%></textarea>
			   </td>
			</tr>
			<c:if test="${pd=='true'}">
				<input type="hidden" name="tradeAmount" value="${amount}">
			</c:if>
			<c:if test="${pd=='false'}">
				<input type="hidden" name="tradeAmount" value="${amount}">
			</c:if>
			<input type="hidden" name="appear" value="<%=appear%>">
			<tr>
          	<td width="100%" colspan="2"><br>
			<c:if test="${status<3}">
			<div align="center">
			   <%
		        
				if((appear==-1)&&status==0){
	           %>
			   <input type="button" name="conStatusBtn" onclick="return controlStatus(0);" class="btn" value="录入">&nbsp;
			   <%}%>
               <%if(appear==0&&status==0){%>
			   <input type="button" name="conStatusBtn" onclick="return controlStatus(1);" class="btn" value="审核">&nbsp;
			   <%}%>
			   <%if((appear==1)||(status>0)){%>
			   <input type="button" name="conStatusBtn" onclick="return controlStatus(2);" class="btn" value="确定">&nbsp;
			   <%}%>
			   <input name="back" type="button" onclick="window.close()" class="btn" value="取消">
            </div>
			</c:if>
			</td>
          </tr>
		  <%if(!"".equals(operUser)){%>
		  <tr>
		  <td colspan="2">
		  <br><br><br><br>
		  流程状态操作日志：流程状态：<%if(appear==-1){%>等待录入<%}else if(appear==0){%>录入
		  <%}else if(appear==1){%>审核<%}else if(appear==2){%>确定<%}%>&nbsp;操作人：
		  <%=operUser%>&nbsp;操作时间：<%=operTime%>
		  </td>
		  </tr>
		  <%}%>
     </table>
	 </span>
     </fieldset>
     <table border="0" cellspacing="0" cellpadding="0" width="100%" class="Noprint">
          <tr height="25">
          	<td width="40%"><div align="center">
             <input type="hidden" name="opt">
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
//添加货物
function addGoodsView(contractID,price,listGoodsTotal){
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
var a=openDialog("addGoods.jsp?contractID="+contractID+"&price="+price+"&listGoodsTotal="+mostLiftAmount+"","_blank","600","450");
  if(1==a)
    window.location.reload();
  }
}

//查看出库单信息
function viewRecord(commodityID,outID,capital,amountTotal,id,finished){ window.open("addRecord.jsp?commodityID="+commodityID+"&outID="+outID+"&capital="+capital+"&amountTotal="+amountTotal+"&id="+id+"&finished="+finished+"","_blank","width=700,height=550,scrollbars=yes");	
}


function refreshParent() {
	window.opener.location.href = window.opener.location.href;
	if (window.opener.progressWindow) window.opener.progressWindow.close();
	window.close();
}

//判断执行结果选择
function judgeResult(){
  if(document.getElementById("0").checked==false&&document.getElementById("1").checked==false
	 &&document.getElementById("2").checked==false){
      alert("执行结果不能为空!");
	  return false;
  }else if(document.getElementById("2").checked==true){//卖方违约
      if(Trim(frm.breakAmount.value)==""){
	      alert("违约数量不能为空!");
	      frm.breakAmount.focus();
	      return false;
	  }else if(Trim(frm.amount.value)==""){
	      alert("实际提货数量不能为空!");
	      frm.amount.focus();
	      return false;
	  }else if(parseFloat(frm.amount.value)<0&&parseFloat(frm.breakAmount.value)<0){  //xieying 完全违约
	      alert("实际提货数量不能小于0!");
	      frm.amount.focus();
	      return false; 
	  }else if(parseFloat(frm.breakAmount.value)<=0){
	      alert("违约数量必须大于0!");
	      frm.breakAmount.focus();
	      return false; 
	  }
	  else{
	      return true;
	  }
  }else if(document.getElementById("1").checked==true){//买方违约
      if(Trim(frm.breakAmount.value)==""){
	      alert("违约数量不能为空!");
	      frm.breakAmount.focus();
	      return false;
	  }else if(Trim(frm.amount.value)==""){
	      alert("实际提货数量不能为空!");
	      frm.amount.focus();
	      return false;
	  }else if(parseFloat(frm.breakAmount.value)<=0){
	      alert("违约数量必须大于0!");
	      frm.breakAmount.focus();
	      return false; 
	  }else{
	      return true;
	  }
  }else{//正常履约
      if(Trim(frm.amount.value)==""){
	      alert("实际提货数量不能为空!");
	      frm.amount.focus();
	      return false;
	  }else if(parseFloat(frm.amount.value)<=0){
	      alert("实际提货数量必须大于0!");
	      frm.amount.focus();
	      return false; 
	  }else{
	      return true;
	  }
  }
}
//设置合同状态
function controlStatus(v){
  status='${status}';
  if(judgeResult()==false){
      return false;
  }
  if(v==1&&judgeExam()==false){
      alert("请选择审核结果");
	  return false;
  }
  if(v==2&&status=='0'&&judgeAssur()==false){
      alert("请选择执行确定结果");
	  return false;
  }
  if(parseFloat(frm.exauAmount.value)<0){
      alert("实际提货数量加违约数量不能大于合同成交数量!");
	  return false;
  }
  if(Number(frm.amount.value)>Number(frm.listGoodsTotal.value)&&v==0)
  {
	  alert("实际提货数量不能大于最大提货数量")
	  return false;
  }
  var mes="";
  mes="实际提货数量:"+frm.amount.value;
  mes+="违约数量:"+frm.breakAmount.value;
  mes+="损耗数量:"+frm.exauAmount.value;
  if(!document.getElementById("f_1")){
    if(confirm(mes+"  确认要操作这些数据吗?")){
         if(v==0){
              frm.opt.value="contractwrite";
              frm.submit();
		  }else if(v==1){
		      frm.opt.value="contractexam";
              frm.submit();
		  }else if(v==2){
		      frm.opt.value="contractassurance";
              frm.submit();
		  }
    }else{
       return false;
    }
  }else{
    if(document.getElementById("f_1").checked==true){
	  if(judgeResult()){
	    if(confirm(mes+"  确认要操作这些数据吗?")){
            if(v==0){
                frm.opt.value="contractwrite";
                frm.submit();
		    }else if(v==1){
		        frm.opt.value="contractexam";
			    alert(judgeExam());
                frm.submit();
		    }else if(v==2){
		        frm.opt.value="contractassurance";
                frm.submit();
		    }
        }else{
          return false;
        }
	  }else{
	    return false;
	  }
    }else{
	  if(confirm(mes+"  确认要操作这些数据吗?")){
		  if(v==0){
              frm.opt.value="contractwrite";
              frm.submit();
		  }else if(v==1){
		      frm.opt.value="contractexam";
              frm.submit();
		  }else if(v==2){
		      frm.opt.value="contractassurance";
              frm.submit();
		  }
      }else{
        return false;
      }
	}
  }
}

//打印电汇凭证
function printTelTran(commodityID,marketId){
	url="printTelTran.jsp?commodityID="+commodityID+"&marketId="+marketId+"";
    PopWindow(url,800,600);
}

//控制只有卖方履约与买方履约才可以填写数量
function changeStatusType(v){
  //if(parseInt(v)==1){
    //document.getElementById("resultSpan").style.display="inline";
    //document.getElementById("breakSpan").style.display="inline";
	//document.getElementById("amountSpan").style.display="inline";
  //}else{
	//document.getElementById("resultSpan").style.display="none";
    //document.getElementById("breakSpan").style.display="none";
	//document.getElementById("amountSpan").style.display="none";
  //}
  if(parseInt(v)==0){
    document.getElementById("f_2").disabled=true;
	controlResult(3);
  }else if(parseInt(v)==1){
    //document.getElementById("f_0").disabled=true;
	//document.getElementById("f_3").disabled=true;
	//document.getElementById("f_2").disabled=false;
	//controlResult(1);
	//controlExauAmountExt();
  }else if(parseInt(v)==2){
    document.getElementById("f_0").disabled=true;
	//document.getElementById("f_1").disabled=true;
	//document.getElementById("f_3").disabled=false;
	//controlResult(0);
  }
}

//控制执行结果有效无效1:有效0:无效
function controlResult(v){
  appear=<%=appear%>;
  if(parseInt(v)==0){
    document.getElementById("1").disabled=true;
	document.getElementById("2").disabled=true;
	document.getElementById("0").disabled=true;
	frm.breakAmount.readOnly=true;
	frm.amount.readOnly=true;
	//frm.exauAmount.value="0";
	controlExauAmountExt();
  }else if(parseInt(v)==1){
    document.getElementById("1").disabled=false;
	document.getElementById("2").disabled=false;
	document.getElementById("0").disabled=false;
	if(document.getElementById("0").checked==true){
	  frm.amount.readOnly=false;
	  frm.breakAmount.value="0";
	  frm.breakAmount.readOnly=true;
	}else if(document.getElementById("1").checked==true){
	  frm.amount.readOnly=false;
	  frm.breakAmount.readOnly=false;
	}else if(document.getElementById("2").checked==true){
	  frm.amount.readOnly=false;
	  frm.breakAmount.readOnly=false;
	}
  }else if(parseInt(v)==3){
	  if(appear>-1){
          document.getElementById("1").disabled=true;
	      document.getElementById("2").disabled=true;
	      document.getElementById("0").disabled=true;
		  frm.breakAmount.readOnly=true;
	      frm.amount.readOnly=true;
		//frm.remark.readOnly=true;
	  }else{
	      frm.breakAmount.readOnly=false;
	      frm.amount.readOnly=false;
	  }
	  controlExauAmountExt();
	//frm.breakAmount.value="0";
	//frm.amount.value="0";
	//frm.exauAmount.value="0";
  }
}

//控制选择执行结果
function changeResult(v){
  if(parseInt(v)==0){
    frm.breakAmount.readOnly=true;
	frm.breakAmount.value="0";
	frm.amount.readOnly=false;
	//控制损耗数量
	controlExauAmountExt();
	//document.getElementById("2").disabled=true;
  }else if(parseInt(v)==1){
    frm.breakAmount.readOnly=false;
	frm.amount.readOnly=false;
	//frm.breakAmount.value="";
	//document.getElementById("0").disabled=true;
	//document.getElementById("2").disabled=false;
  }else if(parseInt(v)==2){
    frm.breakAmount.readOnly=false;
	frm.amount.readOnly=false;
	//frm.breakAmount.value="";
	//document.getElementById("0").disabled=true;
	//document.getElementById("1").disabled=true;
  }
}

//控制加减损耗数量(当页面刷新时用取数据库合同状态)
function controlExauAmount(){
  tradeAmount=0;
  amount=0;
  breakAmount=0;
  status='${status}';
  if(parseInt(status)==1){
      if(Trim(frm.tradeAmount.value)!=""){
          tradeAmount=frm.tradeAmount.value
      }
      if(Trim(frm.amount.value)!=""){
          amount=frm.amount.value
      }
      if(Trim(frm.breakAmount.value)!=""){
          breakAmount=frm.breakAmount.value
      }
      frm.exauAmount.value=isFormat(parseFloat(tradeAmount)-(parseFloat(breakAmount)+parseFloat(amount)),4);
  }
}

//控制加减损耗数量(当页面操作时用取页面合同状态)
function controlExauAmountExt(){
  tradeAmount=0;
  amount=0;
  breakAmount=0;
  //if(document.getElementById("f_1").checked==true){
      if(Trim(frm.tradeAmount.value)!=""){
          tradeAmount=frm.tradeAmount.value
      }
      if(Trim(frm.amount.value)!=""){
          amount=frm.amount.value
      }
      if(Trim(frm.breakAmount.value)!=""){
          breakAmount=frm.breakAmount.value
      }
      frm.exauAmount.value=isFormat(parseFloat(tradeAmount)-(parseFloat(breakAmount)+parseFloat(amount)),4);
  //}
      if(parseFloat(frm.exauAmount.value)<0){
          alert("实际提货数量加违约数量不能大于合同成交数量!");
          frm.breakAmount.value=isFormat(0,4);
          frm.amount.value=isFormat(0,4);
          frm.exauAmount.value=isFormat(0,4);
    	  return false;
      }
}

//审核时判断是否选择了审核结果
function judgeExam(){
    var exam=frm.examContract;
	result=false;
	for(i=0;i<exam.length;i++){
	    if(exam[i].checked==true){
		    result=true;
			break;
		}
	}
	return result;
}

//确定时判断是否选择了是否确定
function judgeAssur(){
    var assur=frm.assurContract;
	result=false;
	for(i=0;i<assur.length;i++){
	    if(assur[i].checked==true){
		    result=true;
			break;
		}
	}
	return result;
}
</SCRIPT>
<!--当刷新页面时,控制只有提货完毕才可以填写数量-->
<script language="javascript">
   var status='${status}';
   //if(parseInt(status)==1){
   // document.getElementById("resultSpan").style.display="inline";
   // document.getElementById("breakSpan").style.display="inline";
   // document.getElementById("amountSpan").style.display="inline";
   //}else{
   //  document.getElementById("resultSpan").style.display="none";
   //  document.getElementById("breakSpan").style.display="none";
   // document.getElementById("amountSpan").style.display="none";
   // }
 //frm.exauAmount.value=parseInt(frm.tradeAmount.value)-(parseInt(frm.breakAmount.value)+parseInt(frm.amount.value));
  //控制加减损耗数量
  //controlExauAmount();
  if(parseInt(status)==0){
	document.getElementById("f_1").disabled=true;
    document.getElementById("f_2").disabled=true;
	controlResult(3);
  }else if(parseInt(status)==1){
    document.getElementById("f_0").disabled=true;
	controlResult(0);
  }else if(parseInt(status)==2){
    document.getElementById("f_0").disabled=true;
	document.getElementById("f_1").disabled=true;
	controlResult(0);
  }
</script>
<!--当刷新页面时,执行结果只有不为正常履约时违约数量才有效-->
<script language="javascript">
   //var result='${result}'
   //if(parseInt(result)!=0){
   //  frm.breakAmount.readOnly=false;
   // frm.breakAmount.value="";
   //}else{
   //	 frm.breakAmount.value="0";
   //  frm.breakAmount.readOnly=true;
   //}
</script>
<%@ include file="/vendue/manage/public/footInc.jsp" %>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>