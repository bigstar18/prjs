<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="org.apache.log4j.*"%>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<html>
  <head>
	<title>出库单信息</title>
  </head>
 <%
	String pd = TradeDAOFactory.getDAO().getPD();
	request.setAttribute("pd",pd);
	request.setAttribute("DW",DW);
	request.setAttribute("DWS",DWS);
	//int this_tradeunit = Integer.valueOf(request.getParameter("this_tradeunit"));
%>
<c:if test="${not empty param.add}">
<%
java.util.Date curdate = new java.util.Date();
java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
%>
  	<c:if test="${param.add=='addRecord'}">
  	<c:set var="id" value=""/>
	<c:set var="modFlag" value="true"/>
	<c:set var="errorMess" value=""/>
	<!--判断合同是否允许生成出库单-->
    <db:select var="v_result" table="v_hisbargain" columns="status" where="commodityid=${param.commodityID} and contractid=${param.contractID}">
	    <c:if test="${v_result.status>0}">
            <c:set var="modFlag" value="false"/>
			<c:set var="errorMess" value="合状态不允许生成出库单,修改失败"/>
		</c:if>
	</db:select>
	<c:if test="${modFlag=='true'}">
	    <!--判断出库单状态是否允许生成出库单-->
	    <db:select var="v_result" table="v_outlog" columns="finished" where="id=${param.outlogId}">
            <c:if test="${v_result.finished>1&&param.addRecordFlag==1}">
               <c:set var="modFlag" value="false"/>
			   <c:set var="errorMess" value="出库单已经是审核状态,不允许再生成出库单,修改失败"/>
		    </c:if>
	    </db:select>
	</c:if>
	<!--判断是否是重复生成出库单-->
	<c:if test="${modFlag=='true'}">
        <db:select var="v_result" table="v_outlog" columns="finished" where="id=${param.outlogId}">
	        <c:if test="${v_result.finished==1}">
		        <c:set var="modFlag" value="false"/>
		        <c:set var="errorMess" value="不能重复出成出库单,生成出库单失败!"/>
		    </c:if>
		</db:select>
	</c:if>
    <c:choose>
	    <c:when test="${modFlag=='true'}">
  	<%
  	   String[] pla=request.getParameterValues("place");
  	   String[] amount=request.getParameterValues("amount");
  	   String[] variety=request.getParameterValues("variety");
  	   String outID=delNull(request.getParameter("outID"));
  	   String operator=delNull(request.getParameter("operator"));
  	   String sysuser=delNull(AclCtrl.getLogonID(request));
	   String str3=delNull(request.getParameter("str3"));
	   BigDecimal tradeunit=new BigDecimal(request.getParameter("tradeunit"));
	   String contractID=request.getParameter("contractID");
	   int addRecordFlag=Integer.parseInt(request.getParameter("addRecordFlag"));
  %>
  <%
  //创建连接数据对象
  //写入出库单表
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  int cnt = 0;
  String mess=null;
  BigDecimal quanlity=new BigDecimal(0);
  String outLogId=request.getParameter("outlogId");//出库记录表id
  BigDecimal outLogAmount=new BigDecimal(0);//出库记录对应的数量
  try{
    Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    conn.setAutoCommit(false);
    StringBuffer sql=new StringBuffer();
	HashMap map=new HashMap();//存放存储地点与数量
	HashMap map1=new HashMap();//已生成出库单后
	boolean modFlag=true;//判断能否保存的标志
	String tempPlace=null;//对应存储地点
	BigDecimal tempTotalAmount=null;//存储地点对应总数量
	BigDecimal tempLiftedAmount=null;//存储地点已提数量
	BigDecimal tempOldAmount=null;//已生成出库单后,原来在存储库点上所提数量
	BigDecimal tempAmount=null;//新输入数量;
	String errorMess="";//反馈出错信息
	String outId="";
	sql=new StringBuffer();
	sql.append("select amount,outid from v_outlog where id="+outLogId+"");
	ps=conn.prepareStatement(sql.toString());
	rs=ps.executeQuery();
	if(rs.next()){
	    outLogAmount=ManaUtil.disBD(rs.getBigDecimal("amount"));
		if(rs.getString("outid")!=null){
		    outId=rs.getString("outid");
		}
	}
	rs.close();
	ps.close();
	//判断单行提货数量是否总数量
	sql=new StringBuffer();
	sql.append("select place,amount from v_outrecord where outlogid='"+outLogId+"'");
	ps=conn.prepareStatement(sql.toString());
	rs=ps.executeQuery();
	while(rs.next()){
	    map1.put(rs.getString("place"),rs.getBigDecimal("amount"));
	}
	rs.close();
	ps.close();
	map=ManaUtil.addPlaceAmount(str3,tradeunit);
    if(amount!=null&&!"".equals(amount)){
	    for(int i=0;i<amount.length;i++){
	        tempAmount=new BigDecimal(amount[i]);
			tempPlace=pla[i];
			if(map.get(tempPlace)!=null){
			    tempTotalAmount=(BigDecimal)map.get(tempPlace);
			}else{
			    tempTotalAmount=new BigDecimal(0);
			}
			if(map1.get(tempPlace)!=null){
			    tempOldAmount=(BigDecimal)map1.get(tempPlace);
		    }else{
			    tempOldAmount=new BigDecimal(0);
			}
			sql=new StringBuffer();
			sql.append("select sum(t2.amount) as n from v_outlog t1,v_outrecord t2 where");
		    sql.append(" t1.contractid="+contractID+" and t1.id=t2.outlogid ");
		    sql.append("and t2.place='"+tempPlace+"'");
			ps=conn.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			if(rs.next()){
			    tempLiftedAmount=ManaUtil.disBD(rs.getBigDecimal("n"));
			}
			rs.close();
			ps.close();
			BigDecimal lineLift=new BigDecimal(0);	
			
			lineLift=tempTotalAmount.subtract(tempLiftedAmount).add(tempOldAmount);
			lineLift=ManaUtil.accuracyNum(lineLift,".####");
			if(tempAmount.compareTo(lineLift)>0){
			    modFlag=false;
				errorMess=tempPlace+":单行提货数量大于单行总提货数量,数据没有保存!";
				break;
			}
	    }
	}
	//判断单行提货数量是否总数量
	if(modFlag==true){
    if(amount!=null&&!"".equals(amount)){
	  for(int i=0;i<amount.length;i++){
	    quanlity=quanlity.add(new BigDecimal(amount[i]));
	  }
	  quanlity=ManaUtil.accuracyNum(quanlity,".####");
	  if(outLogAmount.compareTo(quanlity)==0){
		  sql=new StringBuffer();
          sql.append("delete from v_outrecord where outlogid='"+outLogId+"'");
          ps = conn.prepareStatement(sql.toString());
          ps.executeUpdate();
	      ps.close();
      for(int i=0;i<amount.length;i++){
      %>
      <db:select var="row" table="dual" columns="SP_V_OUTRECORD.nextval as seqID">
       <c:set var="id" value="${row.seqID}"/>
      </db:select>  
      <%
        String id=delNull(pageContext.getAttribute("id").toString());
        sql=new StringBuffer("insert into v_outrecord(id,outid,place,variety");
        sql.append(",amount,operator,createtime,outlogid) values("+id+",'"+outId+"'");
        sql.append(",'"+pla[i]+"','"+variety[i]+"',"+amount[i]+",'"+sysuser+"',");
		sql.append("to_date('"+sqlDate+"','yyyy-mm-dd'),"+outLogId+")");
        ps = conn.prepareStatement(sql.toString());
        ps.executeUpdate();
		ps.close();
      }
	  //置出库单状态
	  if(addRecordFlag==1){
          sql=new StringBuffer();
	      sql.append("update v_outlog set finished=1 where id="+outLogId+"");
          ps = conn.prepareStatement(sql.toString());
          ps.executeUpdate();
          ps.close();
	      mess="生成出库单成功!";
	  }else{
	      mess="修改出库单成功!";
	  }
      
      conn.commit();
      conn.setAutoCommit(true);
      ps.close();
	  %>
      <SCRIPT LANGUAGE="JavaScript">
          alert("<%=mess%>");		  
      </SCRIPT>
	  <%}else{%>
         <SCRIPT LANGUAGE="JavaScript">
              alert("提货数量必须等本次出库总数量,数据没有保存!");
         </SCRIPT>
	  <%
		}
	    }	 
	  %>
	  <%}else{%>
         <SCRIPT LANGUAGE="JavaScript">
              alert("<%=errorMess%>");
         </SCRIPT>
	  <%}%>
	  <%
      }catch(Exception e){
        conn.rollback();
        mess="添加出库单出错,出库单没有保存";
        out.print(e.getMessage());
        e.printStackTrace();
	  %>
        <SCRIPT LANGUAGE="JavaScript">
             alert("<%=mess%>");
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
	<SCRIPT LANGUAGE="JavaScript">
	<!--
	window.close();
	//-->
	</SCRIPT>
     </c:when>
	 <c:otherwise>
            <SCRIPT LANGUAGE="JavaScript">
                 alert("${errorMess}!");
            </SCRIPT>
		</c:otherwise>
	</c:choose>
</c:if>
<!--出库单审核-->
<c:if test="${param.add=='controlStatus'}">
	<%
	      //日志跟踪
		  Logger logger=Logger.getLogger("Managelog");
		  long contractIDLog=Long.parseLong(request.getParameter("contractID"));
		  boolean modFlag=true;
		  String errorMess="";
		  int result=0;
		  double amountTotal=0d;
		  long contractID=Long.parseLong(request.getParameter("contractID"));
		  String outLogId=request.getParameter("outlogId");//出库记录表id
		  int finished=Integer.parseInt(request.getParameter("finished"));//置状态值
		  int outLogStatus=-1;//出库单现状态
	      String belongMarket=request.getParameter("belongMarket");//所属市场
		  String codePro=null;//商品所属省份
		  String provinceId=null;//交易市场所属省份
		  String provinceAbb=null;//省份简称
	      String newOutId=null;//产生的出库单号
		  String orgOutId=null;//记录原来是否产生过出库单号
		  codePro=request.getParameter("codePro");
		  StringBuffer sql=null;
		  ResultSet rs=null;
		  Connection conn = null;
          PreparedStatement ps = null;
		  try{
          Context initContext = new InitialContext();
          Context envContext  = (Context)initContext.lookup("java:/comp/env");
          DataSource ds = (DataSource)envContext.lookup(JNDI);
		  conn = ds.getConnection();
          conn.setAutoCommit(false);
          sql=new StringBuffer();
		  sql.append("select status from v_hisbargain where contractid="+contractID+"");
		  ps=conn.prepareStatement(sql.toString());
		  rs=ps.executeQuery();
		  //判断合同是否允许修改出库单状态
		  if(rs.next()){
		      if(rs.getInt("status")>0){
			      modFlag=false;
				  errorMess="合状态不允许设置状态,修改失败";
			  }
		  }
		  rs.close();
		  ps.close();
		  if(modFlag==true){
		      sql=new StringBuffer();
			  sql.append("select finished from v_outlog where id="+outLogId+"");
			  ps=conn.prepareStatement(sql.toString());
		      rs=ps.executeQuery();
              if(rs.next()){
			      outLogStatus=rs.getInt("finished");
			  }
			  rs.close();
			  ps.close();
			  if(outLogStatus==0&&(finished==0||finished==1||finished==2)){
				  modFlag=false;
				  errorMess="修改出库单到已生成出库单状态,请点击生成出库单,修改失败";
			  }else if(outLogStatus==2){
				  modFlag=false;
				  errorMess="出库单已经是审核状态,修改失败";
			  }else if(outLogStatus==finished){
				  modFlag=false;
                  errorMess="不能重复设置出库单状态,修改失败";
			  }
		  }
		  //将指定合同相应提货数量的货款从208转到202上，该接口在审核出库单时调用
          if(modFlag==true&&finished==2){
		      amountTotal=Double.parseDouble(request.getParameter("amountTotal"));
			  System.out.println("contractID"+contractID+"amountTotal"+amountTotal);
		      DeliveryAction delivery=(DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();
			  int tradeunit = Integer.valueOf(request.getParameter("tradeunit"));
			  if(pd.equals("true"))
				result=delivery.convertPrePaymentToContract(contractID,amountTotal*tradeunit,conn);
			  else
				result=delivery.convertPrePaymentToContract(contractID,amountTotal,conn);
		      logger.debug("合同"+contractID+"出库单审核");
		      logger.debug("判断是否有足够的货款");
		      if(result==0){//没有足够的货款
			      modFlag=false;
				  errorMess="没有足够的货款,修改状态失败";
		          logger.debug("合同"+contractIDLog+"合同没有足够的货款，出库审核失败");
		      }
              else if(result==1){//转货款成功
                   modFlag=true;
		      }
		  }
		  if(modFlag==true&&finished==2){
			    logger.debug("合同"+contractIDLog+"合同有足够的货款,进行出库单下一步处理");
				sql=new StringBuffer();
				sql.append("select outid from v_outlog where id="+outLogId+"");
				ps=conn.prepareStatement(sql.toString());
				rs=ps.executeQuery();
				if(rs.next()){
				    orgOutId=rs.getString("outid");
				}
				rs.close();
				ps.close();
				logger.debug("修改出库记录表状态为审核");
				sql=new StringBuffer();
                sql.append("update v_outlog set finished="+finished+" where id="+outLogId+"");
				ps=conn.prepareStatement(sql.toString());
				ps.executeUpdate();
				ps.close();
				logger.debug("修改出库记录表为审核状态成功");
				logger.debug("判断出库单是否以前产生过出库单号");
				if(orgOutId==null||"".equals(orgOutId)){//从来没有产生过出库单号
				    logger.debug("出库单以前没有产生过出库单号");
					logger.debug("产生出库单号处理");
				    String seq_outrecord=null;
                    sql=new StringBuffer();
					logger.debug("查找sequence号");
			        sql.append("select SP_V_OUTID.nextval as seqID from dual");
			        ps=conn.prepareStatement(sql.toString());
			        rs=ps.executeQuery();
			        if(rs.next()){
				       seq_outrecord=rs.getString("seqID");
			        }
				    rs.close();
			        ps.close();
					logger.debug("sequence号为"+seq_outrecord);
					logger.debug("通过sequence号,产生出库单号");
	                newOutId=ManaUtil.produceOutRecord(OUTRECORDCOUNT,seq_outrecord,"");
				    newOutId=newOutId;
					logger.debug("产生的出库单号为"+newOutId);
				    sql=new StringBuffer();
					logger.debug("写入出库单号到出库单表");
				    sql.append("update v_outrecord set outid='"+newOutId+"' where outlogid="+outLogId+"");
				    ps=conn.prepareStatement(sql.toString());
				    ps.executeUpdate();
				    ps.close();
					logger.debug("写入出库单号到出库单表成功");
					logger.debug("写入出库单号到出库表");
                    sql=new StringBuffer();
				    sql.append("update v_outlog set outid='"+newOutId+"' where id="+outLogId+"");
				    ps=conn.prepareStatement(sql.toString());
				    ps.executeUpdate();
				    ps.close();
					logger.debug("写入出库单号到出库表成功");
				}else{
				    logger.debug("出库单以前产生过出库单号,出库单号为"+orgOutId);
				}
                conn.commit();
				logger.debug("合同"+contractIDLog+"出库单审核成功");
				alert("设置状态成功！",out);
		   }else{
		        alert(errorMess,out);
		   }
       }catch(Exception e){
           conn.rollback();
           out.print(e.getMessage());
           e.printStackTrace();
		   alert("审核出库单出错,请与管理员联系!",out);
       }finally{
           conn.setAutoCommit(true);
		   if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
           if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
	       try{
                conn.close();
           }catch (Exception e){}
           conn = null;
	   }

%>
<SCRIPT LANGUAGE="JavaScript">
	<!--
	window.close();
	//-->
	</SCRIPT>
</c:if>
<c:if test="${param.add=='remark'}">
    <c:set var="modFlag" value="true"/>
	<c:set var="errorMess" value=""/>
	<!--判断合同是否允许修改备注-->
    <db:select var="v_result" table="v_hisbargain" columns="status" where="commodityid=${param.commodityID}  and contractid=${param.contractID}">
	    <c:if test="${v_result.status>0}">
            <c:set var="modFlag" value="false"/>
            <c:set var="errorMess" value="合状态不允许保存备注,修改失败"/>
		</c:if>
	</db:select>
    <c:if test="${modFlag=='true'}">
	    <!--判断出库单状态是否允许修改备注-->
	    <db:select var="v_result" table="v_outlog" columns="finished" where="id=${param.outlogId}">
            <c:if test="${v_result.finished>1}">
               <c:set var="modFlag" value="false"/>
			   <c:set var="errorMess" value="出库单已经是审核状态,不允许再保存备注,修改失败"/>
		    </c:if>
	    </db:select>
	</c:if>
	<c:choose>
	    <c:when test="${modFlag=='true'}">
	        <db:update table="v_outlog"
	            remark="${param.remark}"
	            where="id=${param.outlogId}"
	        />
            <SCRIPT LANGUAGE="JavaScript">
                <!--
                    alert("保存备注成功！");
                //-->
            </SCRIPT>
	      </c:when>
		<c:otherwise>
            <SCRIPT LANGUAGE="JavaScript">
                <!--
                    alert("${errorMess}!");
                //-->
            </SCRIPT>
		</c:otherwise>
	</c:choose>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
	window.close();
	//-->
	</SCRIPT>
</c:if>
</c:if>

<body>
<!--
<form name=frm id=frm action="addRecord.jsp" method="post" targetType="hidden" callback='refreshDialog();'>
-->
<form name=frm id=frm action="addRecord.jsp" method="post" targetType="hidden">
		<fieldset width="100%">
		<legend>出库信息</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
		<c:set var="payment" value=""/>
		<c:set var="price" value=""/>
		<!--商品号-->
		<c:set var="commodityID" value=""/>
		<!--存储点-->
		<c:set var="str3" value=""/>
		<!--合同状态-->
		<c:set var="status" value=""/>
		<!--出库单号-->
        <c:set var="outId" value=""/>
		<!--交易单位数量-->
		<c:set var="tradeunit" value=""/>
		<db:select var="v_result" table="v_outlog" columns="outid" where="id=${param.outlogId}">
            <c:set var="outId" value="${v_result.outid}"/>
		</db:select>
		<db:select_HisBarDetail var="row" where="u1.commodityid=${param.commodityID} and  u1.commodityid=u3.id and u3.id=u4.commid and u2.firmid=u1.userid and u1.contractid=${param.contractID}">
		  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  	<c:set var="contractID" value="${row.contractid}"/>
		  	<c:set var="price" value="${row.price}"/>
		  	<c:set var="commodityID" value="${row.id}"/>
		  	<input type="hidden" name="outID" value="${outId}">
			<input type="hidden" name="outlogId" value="${param.outlogId}">
		  	<input type="hidden" name="LOGINID" value="${LOGINID}">
		  	<input type="hidden" name="commodityID" value="${param.commodityID}">
		  	<input type="hidden" name="capital" value="${param.capital}">
		  	<input type="hidden" name="amountTotal" value="${param.amountTotal}">
			<input type="hidden" name="str3" value="${row.str3}">
			<input type="hidden" name="contractID" value="${row.contractid}">
			<input type="hidden" name="codePro" value="${row.str6}">
			<input type="hidden" name="belongMarket" value="${row.str7}">
			<input type="hidden" name="tradeunit" value="${row.tradeunit}">
			<input type="hidden" name="str6" value="${param.str6}">
			<input type="hidden" name="contractID" value="${param.contractID}">
			<c:set var="str3" value="${row.str3}"/>
			<c:set var="str4" value="${row.str4}"/>
			<c:set var="status" value="${row.status}"/>
			<c:set var="tradeunit" value="${row.tradeunit}"/>
            <tr height="25">
                <td align="right"> 编号 ：</td>
                <td align="left">
                	${outId}
                </td>
                <td align="right"> 标的号 ：</td>
                <td align="left">
                	${row.code}
                </td>
        </tr>
		<tr height="25">
                <td align="right"> 买方编号 ：</td>
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
                <td align="right"> 买方名称 ：</td>
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
                <td align="right"> 卖方名称 ：</td>
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
                <td align="right"> 交易模式 ： </td>
                <td align="left">
                <c:choose>
				<c:when test="${row.trademode==0}">
                竞买
				</c:when>
				<c:when test="${row.trademode==1}">
				竞卖 
				</c:when>
				<c:when test="${row.trademode==2}">
				竞卖 
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>	
                </td>
                <td align="right"> 成交日期 ：</td>
                <td align="left">
                	<fmt:formatDate value="${row.tradedate}" pattern="<%=DATEPATTERN%>"/>
                </td>
        </tr>
        <tr height="25">
				<td align="right"> 本次出库总数量(${param.str6}) ：</td>
                <td align="left">
                	${param.amountTotal*row.tradeunit}
                </td>
				<!--<c:if test="${pd=='true'}">
                <td align="right"> 本次出库总数量(${DW}) ：</td>
                <td align="left">
                	${param.amountTotal}
                </td>
				</c:if>
				<c:if test="${pd=='false'}">
				<td align="right"> 本次出库总数量(${DWS}) ：</td>
                <td align="left">
                	${param.amountTotal*row.tradeunit}
                </td>
				</c:if>-->
                <td align="right"> 对应的货款数 ：</td>
                <td align="left">
                	<fmt:formatNumber value="${param.capital}" pattern="<%=FUNDPATTERN%>"/>
                </td>
        </tr>
        </table>	
    </db:select_HisBarDetail>
	<!--查出库单状态-->
	<c:set var="finished" value=""/>
	<c:set var="remark" value=""/>
	<db:select var="v_result" table="v_outlog" columns="finished,amount,remark" where="id=${param.outlogId}">
      <c:set var="finished" value="${v_result.finished}"/>
	  <c:set var="remark" value="${v_result.remark}"/>
	</db:select>
			<BR>
        </span>
		</fieldset>
		<fieldset width="100%">
		<legend>生成出库单</legend>
		<BR>
		<span>
		<table id="table1" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
          <tr height="25">
          <td align="center"><b>货物存放地</b></td>
          <td align="center"><b>品种</b></td>
		  <!--<td align="center"><b>等级</b></td>-->
		  <td align="center"><b>提货数量(${param.str6})</b></td>
          <td align="center"><b>总数量(${param.str6})</b></td>
		  <td align="center"><b>已提数量(${param.str6})</b></td>
          <!--<%if(pd.equals("true")){%>
		  <td align="center"><b>提货数量(<%=DW%>)</b></td>
          <td align="center"><b>总数量(<%=DW%>)</b></td>
		  <td align="center"><b>已提数量(<%=DW%>)</b></td>
		  <%}else{%>
		  <td align="center"><b>提货数量(<%=DWS%>)</b></td>
		  <td align="center"><b>总数量(<%=DWS%>)</b></td>
		  <td align="center"><b>已提数量(<%=DWS%>)</b></td>
		  <%}%>-->
          </tr>
          <c:set var="delRow" value="0"/>
		  <!--添加时直接从商品表把对应的存储库点,品种从商品表查出来,下面变量是做是添加还是已经添加过的标志-->
		  <c:set var="modFlag" value="false"/>
		  <%
		    BigDecimal tradeunit=null;//交易单位数量
		    String str3=pageContext.getAttribute("str3").toString();
			System.out.println("str3 is "+str3);
			String str4=pageContext.getAttribute("str4").toString();
			String contractID=pageContext.getAttribute("contractID").toString();
			String outLogId=delNull(request.getParameter("outlogId"));
			if(pageContext.getAttribute("tradeunit")!=null){
			    tradeunit=new BigDecimal(pageContext.getAttribute("tradeunit").toString());
			}
		  %>
	<%
		  DBBean bean=null;
		  ResultSet rsAmount=null;
		  ResultSet rs=null;
		  try{
			 bean=new DBBean(JNDI);
		     //将组合字符串组合存放到hashmap中
			 HashMap map=new HashMap();
			 //HashMap mapGrade=new HashMap();
			 map=ManaUtil.addPlaceAmount(str3,tradeunit);
			 //mapGrade=ManaUtil.addPlaceGrade(str3);
			 if(ManaUtil.checkStr(outLogId)){
			   String tempGrade=null;
			   rs=bean.executeQuery("select place,variety,amount from v_outrecord where outlogid="+outLogId+"");
			   while(rs.next()){
			     String tempPlace=rs.getString("place");
				 //tempGrade=(String)mapGrade.get(tempPlace);
				 BigDecimal tempTotal=new BigDecimal(0);
				 if(map.get(tempPlace)!=null){
			         tempTotal=new BigDecimal(map.get(tempPlace).toString());
				 }
			%>
          	 <c:set var="delRow" value="${delRow+1}"/>
			 <c:set var="modFlag" value="true"/>
          	 <tr height="25">
          	 	<td align="center"><input name="place" type="text" class="text" style="width: 150px;" value="<%=delNull(tempPlace)%>" readonly></td>
          	    <td align="center"><input name="variety" type="text" class="text" style="width: 90px;" value="<%=delNull(str4)%>" readonly></td>
				
				<%if(pd.equals("true")){%>
				<td align="center"><input name="amount" type="text" class="text" style="width: 90px;" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				</td>
				<!--记录原来的提货数量，以便判断所输入的数量不能大于(总数量减去已提货数量)-->
				<input type="hidden" name="oldAmount" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				<td align="center"><%=ManaUtil.accuracyNum(tempTotal.divide(tradeunit),".####")%>
				<input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempTotal.divide(tradeunit))%>"></td>
				<%}else{%>
          	    <td align="center"><input name="amount" type="text" class="text" style="width: 90px;" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				</td>
				<!--记录原来的提货数量，以便判断所输入的数量不能大于(总数量减去已提货数量)-->
				<input type="hidden" name="oldAmount" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				<td align="center"><%=ManaUtil.accuracyNum(tempTotal,".####")%>
				<input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempTotal)%>"></td>
				<%}%>
				<td align="center">
				<%
				  //求整个合同在同一个存储地点上已提货数量
				  BigDecimal tempAmount=null;
				  StringBuffer sqlAmount=new StringBuffer();
				  sqlAmount.append("select sum(t2.amount) as n from v_outlog t1,v_outrecord t2 where");
				  sqlAmount.append(" t1.contractid="+contractID+" and t1.id=t2.outlogid ");
				  sqlAmount.append("and t2.place='"+tempPlace+"'");
				  rsAmount=bean.executeQuery(sqlAmount.toString());
				  if(rsAmount.next()){
				    tempAmount=rsAmount.getBigDecimal("n");
				  }
				  rsAmount.close();
				  bean.closeStmt();
				%>

				<%if(pd.equals("true")){%>
				<%=ManaUtil.disBD(tempAmount)%>
				<%}else{%>
				<%=ManaUtil.disBD(tempAmount.multiply(tradeunit))%>
				<%}%>
				<input type="hidden" name="templiftAmount" value="<%=ManaUtil.disBD(tempAmount)%>"></td>
          	 </tr>
    <%
		   }
		   //关闭数据源
		   rs.close();
		}
	%>
		  <c:if test="${modFlag=='false'}">
			<%
				  Set entries=map.entrySet();
		          Iterator ite=entries.iterator();
				  String tempGrade=null;
				  while(ite.hasNext()){
					Map.Entry entry=(Map.Entry)ite.next();
				    String tempPlace=entry.getKey().toString();
					BigDecimal tempAmount=(BigDecimal)entry.getValue();
					//tempGrade=(String)mapGrade.get(tempPlace);
					//求整个合同在同一个存储地点上已提货数量
				    BigDecimal tempLiftAmount=null;
				    StringBuffer sqlAmount=new StringBuffer();
				    sqlAmount.append("select sum(t2.amount) as n from v_outlog t1,v_outrecord t2 where");
				    sqlAmount.append(" t1.contractid="+contractID+" and t1.id=t2.outlogid ");
				    sqlAmount.append("and t2.place='"+tempPlace+"'");
				    rsAmount=bean.executeQuery(sqlAmount.toString());
				    if(rsAmount.next()){
				      tempLiftAmount=rsAmount.getBigDecimal("n");
					  System.out.println(tempLiftAmount);
				    }
				    rsAmount.close();
				    bean.closeStmt();
			%>
                <tr height="25">
                   <td align="center"><input name="place" type="text" class="text" style="width: 150px;" value="<%=delNull(tempPlace)%>" readonly></td>
				   <td align="center"><input name="variety" type="text" class="text" style="width: 90px;" value="<%=delNull(str4)%>" readonly></td>
				   
				   <td align="center"><input name="amount" type="text" class="text" style="width: 90px;" value="0">
				   </td>
				   <!--记录原来的提货数量，以便判断所输入的数量不能大于(总数量减去已提货数量)-->
				   <input type="hidden" name="oldAmount" value="0">
				   <%if(pd.equals("true")){%>
					   <%if(ManaUtil.disBD(tempAmount).floatValue()>0){%>
						   <td align="center"><%=ManaUtil.disBD(tempAmount.divide(tradeunit)).floatValue()%>
						   <input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempAmount.divide(tradeunit)).floatValue()%>">
						   </td>
					   <%}else{%>
							<td align="center"><%=ManaUtil.disBD(tempAmount).floatValue()%>
						   <input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempAmount).floatValue()%>">
						   </td>
					   <%}%>
					  
						   <td align="center">
						   <%=ManaUtil.disBD(tempLiftAmount).floatValue()%>
						   <input type="hidden" name="templiftAmount" value="<%=ManaUtil.disBD(tempLiftAmount).floatValue()%>">   
						   </td>
						
				   <%}else{%>
						<td align="center"><%=ManaUtil.disBD(tempAmount)%>
					   <input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempAmount)%>">
					   </td>
					   <td align="center"><%=ManaUtil.disBD(tempLiftAmount)%>
					   <input type="hidden" name="templiftAmount" value="<%=ManaUtil.disBD(tempLiftAmount)%>">   
					   </td>
				   <%}%>
				   <!--<td align="center"><%=ManaUtil.disBD(tempAmount)%>
				   <input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempAmount)%>">
				   </td>
				   <td align="center">
				   <%=ManaUtil.disBD(tempLiftAmount)%>
				   <input type="hidden" name="templiftAmount" value="<%=ManaUtil.disBD(tempLiftAmount)%>">   
				   </td> -->
			    </tr>
			    <%
				   } 
				%>
		  </c:if>
	  <%
	       }
           catch(Exception e)
           {
  	           e.printStackTrace();
               errOpt();
           }finally{
               if(rsAmount!=null){try{rsAmount.close();}catch(Exception ex){}rsAmount=null;}
			   if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
	           if(bean!=null){bean.close();}
	       }
		   
	   %>
     </table>

     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="25">
        <td width="40%"><div align="center" class="Noprint">
        <input type="hidden" name="add">
        <input type="hidden" name="addRecordFlag">
		
		<input type="button" onclick="return addRecord(1);" class="bigbtn" value="生成出库单" <c:if test="${finished>1}">disabled</c:if>>&nbsp;&nbsp;

        </div></td>
        </tr>
     </table>
     <BR>
        </span>
     </fieldset>
		<br>
		<fieldset>
		<legend>出库单操作状态</legend>
		<BR>
		<span>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     	    <tr height="25">
     		     <td>出库单操作状态：
				 <input type="radio" name="finished" id="r_0" <c:if test="${finished==0}">checked</c:if> value="0" <c:if test="${finished>0}">disabled</c:if>>未生成出库单&nbsp;&nbsp;
     		     <input type="radio" name="finished" id="r_1" <c:if test="${finished==1}">checked</c:if> value="1"  <c:if test="${finished>1}">disabled</c:if>>已生成出库单&nbsp;&nbsp;
				 <input type="radio" name="finished" id="r_2"  <c:if test="${finished==2}">checked</c:if> value="2">已审核
     		     </td>
				 <input type="hidden" name="outlogStatus" value="${finished}">
     	    </tr>
          <tr height="25">
          	<td width="40%"><div align="center" class="Noprint">
			      <input type="button" name="conStatusBtn" onclick="return controlStatus();" <c:if test="${finished!=1}">disabled</c:if> class="bigbtn" value="出库单确认">&nbsp;&nbsp;
			      <input name="printBtn" type="button" onclick="printRecord('${param.commodityID}','${param.capital}','${outId}','${param.amountTotal}','${param.outlogId}','${param.str6}','${param.contractID}');" <c:if test="${finished<2||status>0}">disabled</c:if> class="bigbtn" value="打印出库单">&nbsp;&nbsp;
				  <input name="printBtn" type="button" onclick="printRecordExt('${param.commodityID}','${param.capital}','${outId}','${param.amountTotal}','${param.outlogId}','${param.str6}','${param.contractID}');" <c:if test="${finished<1}">disabled</c:if> class="bigbtn" value="出库单预览">&nbsp;&nbsp;

				  <%
				    //if(tempBeatBack){
				  %>
				  <!--
		          <input type="button" onclick="beatBack();" <c:if test="${status>0||finished<2}">disabled</c:if> class="btn" value="出库单打回">-->
				  <%
				    //}	  
				  %>
            </div></td>
          </tr>
     </table>
    </span>
  </fieldset>
  <fieldset>
	 <legend>保存备注</legend>
	 <BR>
	 <span>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     	<tr height="35">
           <td align="center" valign="middle"> 备注 ：
             <textarea name="remark" class="normal">${remark}</textarea>
           </td>
        </tr>
		<tr height="25">
          	<td width="100%"><div align="center" class="Noprint">
              <input type="button" name="remarkBtn" onclick="return saveRemark();" class="btn" <c:if test="${finished==2}">disabled</c:if> value="保存备注">

			</td>
		</tr>
	 </table>
    </span>
  </fieldset>
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="25">
          	<td width="40%"><div align="center" class="Noprint">
			      <br><br><br>
			      <input name="back" type="button" onclick="window.close()" class="btn" value="取消">
            </div>
            </td>
          </tr>
  </table>
</form>
</body>
</html>
<script language="javascript">
function insertRows(){ 
tempRow=table1.rows.length; 
if(table1.rows.length>5){
  alert("最多只能添加5行");
  return;	
}
var Rows=table1.rows;//类似数组的Rows 
var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
var Cells=newRow.cells;//类似数组的Cells
for (i=0;i<4;i++)//每行的3列数据 
{ 
var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
newCell.align="center"; 
switch (i) 
{ 
case 0 : newCell.innerHTML='<td align="center"><input name="place" type="text" class="text" style="width: 150px;"></td>';break; 
case 1 : newCell.innerHTML='<td align="center"><input name="variety" type="text" class="text" style="width: 150px;"></td>'; break; 
case 2 : newCell.innerHTML='<td align="center"><input name="amount" type="text" class="text" style="width: 150px;"></td>'; break;
case 3 : newCell.innerHTML='<td align="center"><span onclick="this.parentNode.parentNode.removeNode(true);" style="cursor:hand">删除</span></td>'; break;
} 
} 
} 
function delTableRow(rowNum){
if (table1.rows.length >rowNum){ 
table1.deleteRow(rowNum); 
} 
} 
</script>
<SCRIPT LANGUAGE="JavaScript">

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

function addRecord(v)
{   var amount=frm.place;
    var amountTotal=0;
	if(!amount){
	  alert("没有分录,不能生成出库单!");
	  return false;	
	}
	if(amount.length>1){
	   for(i=0;i<amount.length;i++){
		   if(Trim(frm.amount[i].value) == ""){
  	         alert("提货数量不能为空！");
		     frm.amount[i].focus();
		     return false;	
  	       }else if(Trim(frm.place[i].value) == ""){
  	         alert("存粮地点不能为空！");
		     frm.place[i].focus();
		     return false;	
  	       }else if(Trim(frm.variety[i].value) == ""){
  	         alert("品种不能为空！");
		     frm.variety[i].focus();
		     return false;	
  	       }
		   else if(parseFloat(frm.amount[i].value)>parseFloat(frm.tempTotal[i].value)){
		      alert("单行提货数量大于总数量!");
		      frm.amount[i].focus();
		      return false;
		   }
		   else if(parseFloat(frm.amount[i].value)>(parseFloat(isFormat((parseFloat(frm.tempTotal[i].value)-parseFloat(frm.templiftAmount[i].value)+parseFloat(frm.oldAmount[i].value)),4)))){
		     alert("所输入的数量大于剩余提货数量!");
		    frm.amount[i].focus();
		    return false;
		   }
		   else{
		     amountTotal=amountTotal+parseFloat(frm.amount[i].value);
		   }
	     }
		 if(amountTotal<0)  //xieying
		 {
			alert("总提货数量不能小于0！");
		 }
		 if(parseFloat(isFormat(amountTotal,4))!=parseFloat(frm.amountTotal.value)){
		   alert("提货数量必须等于本次出库总数量!");
		   return false;
		 }
	     if(userConfirm()){
	       frm.add.value="addRecord";
		   if(v==1){
		       frm.addRecordFlag.value="1";
		   }else if(v==2){
		       frm.addRecordFlag.value="2";
		   }
	       frm.submit();
	     }else{
	       return false;
	     }
  }else{
  	 //if(Trim(frm.amount.value) == ""||parseFloat(frm.amount.value)==0){  xieying 出库单为0
	 if(Trim(frm.amount.value) == ""){
  	     //alert("提货数量不能为空,并且不能为0！");
		 alert("提货数量不能为空");
		 frm.amount.focus();
		 return false;	
  	 }else if(Trim(frm.place.value) == ""){
  	     alert("存粮地点不能为空！");
		 frm.place.focus();
		 return false;	
  	 }else if(Trim(frm.variety.value) == ""){
  	     alert("品种不能为空！");
		 frm.variety.focus();
		 return false;	
  	 }else if(parseFloat(frm.amount.value)>parseFloat(frm.tempTotal.value)){
	     alert("单行提货数量大于总数量!");
	     frm.amount.focus();
	     return false;
	 }else if(parseFloat(frm.amount.value)>(parseFloat(isFormat((parseFloat(frm.tempTotal.value)-parseFloat(frm.templiftAmount.value)+parseFloat(frm.oldAmount.value)),4)))){
	  	     alert("所输入的数量大于剩余提货数量!");
	  		 frm.amount.focus();
	  	     return false;
	 }
	 else{
	   amountTotal=parseFloat(frm.amount.value);
	   if(amountTotal!=parseFloat(frm.amountTotal.value)){
	      alert("提货数量必须等本次出库总数量!");
	      return false;
	   }
  	   if(userConfirm()){
         frm.add.value="addRecord";
		 frm.addRecordFlag.value="1";
         frm.submit();
       }else{
         return false;
       }
     }
  }
}

//设置状态
function controlStatus(){
  var outlogStatus=frm.outlogStatus.value;
  if(parseInt(outlogStatus)==0&&(document.getElementById("r_1").checked==true||document.getElementById("r_2").checked==true||document.getElementById("r_0").checked==true)){
    alert("请点击生成出库单置已生成出库单状态!");
	return false;
  }else{
    if(userConfirm()){
      frm.add.value="controlStatus";	
      frm.submit();
     }else{
      return false;
     }	
 }
}

//保存备注
function saveRemark(){
  if(Trim(frm.remark.value) == ""){
    alert("备注不能为空!");
	return false;
  }else{
    if(userConfirm()){
      frm.add.value="remark";	
      frm.submit();
     }else{
      return false;
     }	
 }
}

function addGoodsView(contractID,price){
  var a=openDialog("addGoods.jsp?contractID="+contractID+"&price="+price+"","_blank","420","350");
  if(1==a)
    window.location.reload();
}

//查看出库单信息
function viewRecord(commodityID){
  var a=openDialog("addRecord.jsp?commodityID="+commodityID+"","_blank","600","550");
    if(1==a)
      window.location.reload();	
}

//打回出库单
function beatBack(){
   if(userConfirm()){
      frm.add.value="beatBack";	
      frm.submit();
   }else{
      return false;
   }
}



//打印出库单
function printRecord(commodityID,capital,outID,amountTotal,outlogId,str6,contractID){ result=PopWindow("recordTemp.jsp?commodityID="+commodityID+"&capital="+capital+"&outID="+outID+"&amountTotal="+amountTotal+"&outlogId="+outlogId+"&str6="+str6+"&contractID="+contractID,670,600);
}

//预览出库单
function printRecordExt(commodityID,capital,outID,amountTotal,outlogId,str6,contractID)
{
var tradeunit = frm.tradeunit.value;
result=PopWindow("previewRecordTemp.jsp?commodityID="+commodityID+"&capital="+capital+"&outID="+outID+"&amountTotal="+amountTotal+"&outlogId="+outlogId+"&tradeunit="+tradeunit+"&str6="+str6+"&contractID="+contractID,670,600);
}
</SCRIPT>
<script language="javascript">
	if(table1.rows.length==1){
	  frm.conStatusBtn.disabled=true;
	  frm.printBtn.disabled=true;	
	}
</script>
<%@ include file="/vendue/manage/public/footInc.jsp" %>