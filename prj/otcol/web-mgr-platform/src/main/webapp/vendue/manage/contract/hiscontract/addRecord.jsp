<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="org.apache.log4j.*"%>
<%@ include file="/vendue/manage/globalDef.jsp"%>

<html>
  <head>
	<title>���ⵥ��Ϣ</title>
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
	<!--�жϺ�ͬ�Ƿ��������ɳ��ⵥ-->
    <db:select var="v_result" table="v_hisbargain" columns="status" where="commodityid=${param.commodityID} and contractid=${param.contractID}">
	    <c:if test="${v_result.status>0}">
            <c:set var="modFlag" value="false"/>
			<c:set var="errorMess" value="��״̬���������ɳ��ⵥ,�޸�ʧ��"/>
		</c:if>
	</db:select>
	<c:if test="${modFlag=='true'}">
	    <!--�жϳ��ⵥ״̬�Ƿ��������ɳ��ⵥ-->
	    <db:select var="v_result" table="v_outlog" columns="finished" where="id=${param.outlogId}">
            <c:if test="${v_result.finished>1&&param.addRecordFlag==1}">
               <c:set var="modFlag" value="false"/>
			   <c:set var="errorMess" value="���ⵥ�Ѿ������״̬,�����������ɳ��ⵥ,�޸�ʧ��"/>
		    </c:if>
	    </db:select>
	</c:if>
	<!--�ж��Ƿ����ظ����ɳ��ⵥ-->
	<c:if test="${modFlag=='true'}">
        <db:select var="v_result" table="v_outlog" columns="finished" where="id=${param.outlogId}">
	        <c:if test="${v_result.finished==1}">
		        <c:set var="modFlag" value="false"/>
		        <c:set var="errorMess" value="�����ظ����ɳ��ⵥ,���ɳ��ⵥʧ��!"/>
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
  //�����������ݶ���
  //д����ⵥ��
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  int cnt = 0;
  String mess=null;
  BigDecimal quanlity=new BigDecimal(0);
  String outLogId=request.getParameter("outlogId");//�����¼��id
  BigDecimal outLogAmount=new BigDecimal(0);//�����¼��Ӧ������
  try{
    Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    conn.setAutoCommit(false);
    StringBuffer sql=new StringBuffer();
	HashMap map=new HashMap();//��Ŵ洢�ص�������
	HashMap map1=new HashMap();//�����ɳ��ⵥ��
	boolean modFlag=true;//�ж��ܷ񱣴�ı�־
	String tempPlace=null;//��Ӧ�洢�ص�
	BigDecimal tempTotalAmount=null;//�洢�ص��Ӧ������
	BigDecimal tempLiftedAmount=null;//�洢�ص���������
	BigDecimal tempOldAmount=null;//�����ɳ��ⵥ��,ԭ���ڴ洢�������������
	BigDecimal tempAmount=null;//����������;
	String errorMess="";//����������Ϣ
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
	//�жϵ�����������Ƿ�������
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
				errorMess=tempPlace+":��������������ڵ������������,����û�б���!";
				break;
			}
	    }
	}
	//�жϵ�����������Ƿ�������
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
	  //�ó��ⵥ״̬
	  if(addRecordFlag==1){
          sql=new StringBuffer();
	      sql.append("update v_outlog set finished=1 where id="+outLogId+"");
          ps = conn.prepareStatement(sql.toString());
          ps.executeUpdate();
          ps.close();
	      mess="���ɳ��ⵥ�ɹ�!";
	  }else{
	      mess="�޸ĳ��ⵥ�ɹ�!";
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
              alert("�����������ȱ��γ���������,����û�б���!");
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
        mess="��ӳ��ⵥ����,���ⵥû�б���";
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
<!--���ⵥ���-->
<c:if test="${param.add=='controlStatus'}">
	<%
	      //��־����
		  Logger logger=Logger.getLogger("Managelog");
		  long contractIDLog=Long.parseLong(request.getParameter("contractID"));
		  boolean modFlag=true;
		  String errorMess="";
		  int result=0;
		  double amountTotal=0d;
		  long contractID=Long.parseLong(request.getParameter("contractID"));
		  String outLogId=request.getParameter("outlogId");//�����¼��id
		  int finished=Integer.parseInt(request.getParameter("finished"));//��״ֵ̬
		  int outLogStatus=-1;//���ⵥ��״̬
	      String belongMarket=request.getParameter("belongMarket");//�����г�
		  String codePro=null;//��Ʒ����ʡ��
		  String provinceId=null;//�����г�����ʡ��
		  String provinceAbb=null;//ʡ�ݼ��
	      String newOutId=null;//�����ĳ��ⵥ��
		  String orgOutId=null;//��¼ԭ���Ƿ���������ⵥ��
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
		  //�жϺ�ͬ�Ƿ������޸ĳ��ⵥ״̬
		  if(rs.next()){
		      if(rs.getInt("status")>0){
			      modFlag=false;
				  errorMess="��״̬����������״̬,�޸�ʧ��";
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
				  errorMess="�޸ĳ��ⵥ�������ɳ��ⵥ״̬,�������ɳ��ⵥ,�޸�ʧ��";
			  }else if(outLogStatus==2){
				  modFlag=false;
				  errorMess="���ⵥ�Ѿ������״̬,�޸�ʧ��";
			  }else if(outLogStatus==finished){
				  modFlag=false;
                  errorMess="�����ظ����ó��ⵥ״̬,�޸�ʧ��";
			  }
		  }
		  //��ָ����ͬ��Ӧ��������Ļ����208ת��202�ϣ��ýӿ�����˳��ⵥʱ����
          if(modFlag==true&&finished==2){
		      amountTotal=Double.parseDouble(request.getParameter("amountTotal"));
			  System.out.println("contractID"+contractID+"amountTotal"+amountTotal);
		      DeliveryAction delivery=(DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();
			  int tradeunit = Integer.valueOf(request.getParameter("tradeunit"));
			  if(pd.equals("true"))
				result=delivery.convertPrePaymentToContract(contractID,amountTotal*tradeunit,conn);
			  else
				result=delivery.convertPrePaymentToContract(contractID,amountTotal,conn);
		      logger.debug("��ͬ"+contractID+"���ⵥ���");
		      logger.debug("�ж��Ƿ����㹻�Ļ���");
		      if(result==0){//û���㹻�Ļ���
			      modFlag=false;
				  errorMess="û���㹻�Ļ���,�޸�״̬ʧ��";
		          logger.debug("��ͬ"+contractIDLog+"��ͬû���㹻�Ļ���������ʧ��");
		      }
              else if(result==1){//ת����ɹ�
                   modFlag=true;
		      }
		  }
		  if(modFlag==true&&finished==2){
			    logger.debug("��ͬ"+contractIDLog+"��ͬ���㹻�Ļ���,���г��ⵥ��һ������");
				sql=new StringBuffer();
				sql.append("select outid from v_outlog where id="+outLogId+"");
				ps=conn.prepareStatement(sql.toString());
				rs=ps.executeQuery();
				if(rs.next()){
				    orgOutId=rs.getString("outid");
				}
				rs.close();
				ps.close();
				logger.debug("�޸ĳ����¼��״̬Ϊ���");
				sql=new StringBuffer();
                sql.append("update v_outlog set finished="+finished+" where id="+outLogId+"");
				ps=conn.prepareStatement(sql.toString());
				ps.executeUpdate();
				ps.close();
				logger.debug("�޸ĳ����¼��Ϊ���״̬�ɹ�");
				logger.debug("�жϳ��ⵥ�Ƿ���ǰ���������ⵥ��");
				if(orgOutId==null||"".equals(orgOutId)){//����û�в��������ⵥ��
				    logger.debug("���ⵥ��ǰû�в��������ⵥ��");
					logger.debug("�������ⵥ�Ŵ���");
				    String seq_outrecord=null;
                    sql=new StringBuffer();
					logger.debug("����sequence��");
			        sql.append("select SP_V_OUTID.nextval as seqID from dual");
			        ps=conn.prepareStatement(sql.toString());
			        rs=ps.executeQuery();
			        if(rs.next()){
				       seq_outrecord=rs.getString("seqID");
			        }
				    rs.close();
			        ps.close();
					logger.debug("sequence��Ϊ"+seq_outrecord);
					logger.debug("ͨ��sequence��,�������ⵥ��");
	                newOutId=ManaUtil.produceOutRecord(OUTRECORDCOUNT,seq_outrecord,"");
				    newOutId=newOutId;
					logger.debug("�����ĳ��ⵥ��Ϊ"+newOutId);
				    sql=new StringBuffer();
					logger.debug("д����ⵥ�ŵ����ⵥ��");
				    sql.append("update v_outrecord set outid='"+newOutId+"' where outlogid="+outLogId+"");
				    ps=conn.prepareStatement(sql.toString());
				    ps.executeUpdate();
				    ps.close();
					logger.debug("д����ⵥ�ŵ����ⵥ��ɹ�");
					logger.debug("д����ⵥ�ŵ������");
                    sql=new StringBuffer();
				    sql.append("update v_outlog set outid='"+newOutId+"' where id="+outLogId+"");
				    ps=conn.prepareStatement(sql.toString());
				    ps.executeUpdate();
				    ps.close();
					logger.debug("д����ⵥ�ŵ������ɹ�");
				}else{
				    logger.debug("���ⵥ��ǰ���������ⵥ��,���ⵥ��Ϊ"+orgOutId);
				}
                conn.commit();
				logger.debug("��ͬ"+contractIDLog+"���ⵥ��˳ɹ�");
				alert("����״̬�ɹ���",out);
		   }else{
		        alert(errorMess,out);
		   }
       }catch(Exception e){
           conn.rollback();
           out.print(e.getMessage());
           e.printStackTrace();
		   alert("��˳��ⵥ����,�������Ա��ϵ!",out);
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
	<!--�жϺ�ͬ�Ƿ������޸ı�ע-->
    <db:select var="v_result" table="v_hisbargain" columns="status" where="commodityid=${param.commodityID}  and contractid=${param.contractID}">
	    <c:if test="${v_result.status>0}">
            <c:set var="modFlag" value="false"/>
            <c:set var="errorMess" value="��״̬�������汸ע,�޸�ʧ��"/>
		</c:if>
	</db:select>
    <c:if test="${modFlag=='true'}">
	    <!--�жϳ��ⵥ״̬�Ƿ������޸ı�ע-->
	    <db:select var="v_result" table="v_outlog" columns="finished" where="id=${param.outlogId}">
            <c:if test="${v_result.finished>1}">
               <c:set var="modFlag" value="false"/>
			   <c:set var="errorMess" value="���ⵥ�Ѿ������״̬,�������ٱ��汸ע,�޸�ʧ��"/>
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
                    alert("���汸ע�ɹ���");
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
		<legend>������Ϣ</legend>
		<BR>
		<span>
		<c:set var="contractID" value=""/>
		<c:set var="payment" value=""/>
		<c:set var="price" value=""/>
		<!--��Ʒ��-->
		<c:set var="commodityID" value=""/>
		<!--�洢��-->
		<c:set var="str3" value=""/>
		<!--��ͬ״̬-->
		<c:set var="status" value=""/>
		<!--���ⵥ��-->
        <c:set var="outId" value=""/>
		<!--���׵�λ����-->
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
                <td align="right"> ��� ��</td>
                <td align="left">
                	${outId}
                </td>
                <td align="right"> ��ĺ� ��</td>
                <td align="left">
                	${row.code}
                </td>
        </tr>
		<tr height="25">
                <td align="right"> �򷽱�� ��</td>
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
                <td align="right">������� ��</td>
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
                <td align="right"> ������ ��</td>
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
                <td align="right"> �������� ��</td>
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
                <td align="right"> ����ģʽ �� </td>
                <td align="left">
                <c:choose>
				<c:when test="${row.trademode==0}">
                ����
				</c:when>
				<c:when test="${row.trademode==1}">
				���� 
				</c:when>
				<c:when test="${row.trademode==2}">
				���� 
				</c:when>
				<c:otherwise>
				</c:otherwise>
				</c:choose>	
                </td>
                <td align="right"> �ɽ����� ��</td>
                <td align="left">
                	<fmt:formatDate value="${row.tradedate}" pattern="<%=DATEPATTERN%>"/>
                </td>
        </tr>
        <tr height="25">
				<td align="right"> ���γ���������(${param.str6}) ��</td>
                <td align="left">
                	${param.amountTotal*row.tradeunit}
                </td>
				<!--<c:if test="${pd=='true'}">
                <td align="right"> ���γ���������(${DW}) ��</td>
                <td align="left">
                	${param.amountTotal}
                </td>
				</c:if>
				<c:if test="${pd=='false'}">
				<td align="right"> ���γ���������(${DWS}) ��</td>
                <td align="left">
                	${param.amountTotal*row.tradeunit}
                </td>
				</c:if>-->
                <td align="right"> ��Ӧ�Ļ����� ��</td>
                <td align="left">
                	<fmt:formatNumber value="${param.capital}" pattern="<%=FUNDPATTERN%>"/>
                </td>
        </tr>
        </table>	
    </db:select_HisBarDetail>
	<!--����ⵥ״̬-->
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
		<legend>���ɳ��ⵥ</legend>
		<BR>
		<span>
		<table id="table1" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
          <tr height="25">
          <td align="center"><b>�����ŵ�</b></td>
          <td align="center"><b>Ʒ��</b></td>
		  <!--<td align="center"><b>�ȼ�</b></td>-->
		  <td align="center"><b>�������(${param.str6})</b></td>
          <td align="center"><b>������(${param.str6})</b></td>
		  <td align="center"><b>��������(${param.str6})</b></td>
          <!--<%if(pd.equals("true")){%>
		  <td align="center"><b>�������(<%=DW%>)</b></td>
          <td align="center"><b>������(<%=DW%>)</b></td>
		  <td align="center"><b>��������(<%=DW%>)</b></td>
		  <%}else{%>
		  <td align="center"><b>�������(<%=DWS%>)</b></td>
		  <td align="center"><b>������(<%=DWS%>)</b></td>
		  <td align="center"><b>��������(<%=DWS%>)</b></td>
		  <%}%>-->
          </tr>
          <c:set var="delRow" value="0"/>
		  <!--���ʱֱ�Ӵ���Ʒ��Ѷ�Ӧ�Ĵ洢���,Ʒ�ִ���Ʒ������,���������������ӻ����Ѿ���ӹ��ı�־-->
		  <c:set var="modFlag" value="false"/>
		  <%
		    BigDecimal tradeunit=null;//���׵�λ����
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
		     //������ַ�����ϴ�ŵ�hashmap��
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
				<!--��¼ԭ��������������Ա��ж���������������ܴ���(��������ȥ���������)-->
				<input type="hidden" name="oldAmount" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				<td align="center"><%=ManaUtil.accuracyNum(tempTotal.divide(tradeunit),".####")%>
				<input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempTotal.divide(tradeunit))%>"></td>
				<%}else{%>
          	    <td align="center"><input name="amount" type="text" class="text" style="width: 90px;" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				</td>
				<!--��¼ԭ��������������Ա��ж���������������ܴ���(��������ȥ���������)-->
				<input type="hidden" name="oldAmount" value="<%=ManaUtil.disBD(rs.getBigDecimal("amount"))%>">
				<td align="center"><%=ManaUtil.accuracyNum(tempTotal,".####")%>
				<input type="hidden" name="tempTotal" value="<%=ManaUtil.disBD(tempTotal)%>"></td>
				<%}%>
				<td align="center">
				<%
				  //��������ͬ��ͬһ���洢�ص������������
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
		   //�ر�����Դ
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
					//��������ͬ��ͬһ���洢�ص������������
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
				   <!--��¼ԭ��������������Ա��ж���������������ܴ���(��������ȥ���������)-->
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
		
		<input type="button" onclick="return addRecord(1);" class="bigbtn" value="���ɳ��ⵥ" <c:if test="${finished>1}">disabled</c:if>>&nbsp;&nbsp;

        </div></td>
        </tr>
     </table>
     <BR>
        </span>
     </fieldset>
		<br>
		<fieldset>
		<legend>���ⵥ����״̬</legend>
		<BR>
		<span>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     	    <tr height="25">
     		     <td>���ⵥ����״̬��
				 <input type="radio" name="finished" id="r_0" <c:if test="${finished==0}">checked</c:if> value="0" <c:if test="${finished>0}">disabled</c:if>>δ���ɳ��ⵥ&nbsp;&nbsp;
     		     <input type="radio" name="finished" id="r_1" <c:if test="${finished==1}">checked</c:if> value="1"  <c:if test="${finished>1}">disabled</c:if>>�����ɳ��ⵥ&nbsp;&nbsp;
				 <input type="radio" name="finished" id="r_2"  <c:if test="${finished==2}">checked</c:if> value="2">�����
     		     </td>
				 <input type="hidden" name="outlogStatus" value="${finished}">
     	    </tr>
          <tr height="25">
          	<td width="40%"><div align="center" class="Noprint">
			      <input type="button" name="conStatusBtn" onclick="return controlStatus();" <c:if test="${finished!=1}">disabled</c:if> class="bigbtn" value="���ⵥȷ��">&nbsp;&nbsp;
			      <input name="printBtn" type="button" onclick="printRecord('${param.commodityID}','${param.capital}','${outId}','${param.amountTotal}','${param.outlogId}','${param.str6}','${param.contractID}');" <c:if test="${finished<2||status>0}">disabled</c:if> class="bigbtn" value="��ӡ���ⵥ">&nbsp;&nbsp;
				  <input name="printBtn" type="button" onclick="printRecordExt('${param.commodityID}','${param.capital}','${outId}','${param.amountTotal}','${param.outlogId}','${param.str6}','${param.contractID}');" <c:if test="${finished<1}">disabled</c:if> class="bigbtn" value="���ⵥԤ��">&nbsp;&nbsp;

				  <%
				    //if(tempBeatBack){
				  %>
				  <!--
		          <input type="button" onclick="beatBack();" <c:if test="${status>0||finished<2}">disabled</c:if> class="btn" value="���ⵥ���">-->
				  <%
				    //}	  
				  %>
            </div></td>
          </tr>
     </table>
    </span>
  </fieldset>
  <fieldset>
	 <legend>���汸ע</legend>
	 <BR>
	 <span>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
     	<tr height="35">
           <td align="center" valign="middle"> ��ע ��
             <textarea name="remark" class="normal">${remark}</textarea>
           </td>
        </tr>
		<tr height="25">
          	<td width="100%"><div align="center" class="Noprint">
              <input type="button" name="remarkBtn" onclick="return saveRemark();" class="btn" <c:if test="${finished==2}">disabled</c:if> value="���汸ע">

			</td>
		</tr>
	 </table>
    </span>
  </fieldset>
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="25">
          	<td width="40%"><div align="center" class="Noprint">
			      <br><br><br>
			      <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">
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
  alert("���ֻ�����5��");
  return;	
}
var Rows=table1.rows;//���������Rows 
var newRow=table1.insertRow(table1.rows.length);//�����µ�һ�� 
var Cells=newRow.cells;//���������Cells
for (i=0;i<4;i++)//ÿ�е�3������ 
{ 
var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
newCell.align="center"; 
switch (i) 
{ 
case 0 : newCell.innerHTML='<td align="center"><input name="place" type="text" class="text" style="width: 150px;"></td>';break; 
case 1 : newCell.innerHTML='<td align="center"><input name="variety" type="text" class="text" style="width: 150px;"></td>'; break; 
case 2 : newCell.innerHTML='<td align="center"><input name="amount" type="text" class="text" style="width: 150px;"></td>'; break;
case 3 : newCell.innerHTML='<td align="center"><span onclick="this.parentNode.parentNode.removeNode(true);" style="cursor:hand">ɾ��</span></td>'; break;
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
		alert("����ģ�岻��Ϊ�գ�");
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
	  alert("û�з�¼,�������ɳ��ⵥ!");
	  return false;	
	}
	if(amount.length>1){
	   for(i=0;i<amount.length;i++){
		   if(Trim(frm.amount[i].value) == ""){
  	         alert("�����������Ϊ�գ�");
		     frm.amount[i].focus();
		     return false;	
  	       }else if(Trim(frm.place[i].value) == ""){
  	         alert("�����ص㲻��Ϊ�գ�");
		     frm.place[i].focus();
		     return false;	
  	       }else if(Trim(frm.variety[i].value) == ""){
  	         alert("Ʒ�ֲ���Ϊ�գ�");
		     frm.variety[i].focus();
		     return false;	
  	       }
		   else if(parseFloat(frm.amount[i].value)>parseFloat(frm.tempTotal[i].value)){
		      alert("���������������������!");
		      frm.amount[i].focus();
		      return false;
		   }
		   else if(parseFloat(frm.amount[i].value)>(parseFloat(isFormat((parseFloat(frm.tempTotal[i].value)-parseFloat(frm.templiftAmount[i].value)+parseFloat(frm.oldAmount[i].value)),4)))){
		     alert("���������������ʣ���������!");
		    frm.amount[i].focus();
		    return false;
		   }
		   else{
		     amountTotal=amountTotal+parseFloat(frm.amount[i].value);
		   }
	     }
		 if(amountTotal<0)  //xieying
		 {
			alert("�������������С��0��");
		 }
		 if(parseFloat(isFormat(amountTotal,4))!=parseFloat(frm.amountTotal.value)){
		   alert("�������������ڱ��γ���������!");
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
  	 //if(Trim(frm.amount.value) == ""||parseFloat(frm.amount.value)==0){  xieying ���ⵥΪ0
	 if(Trim(frm.amount.value) == ""){
  	     //alert("�����������Ϊ��,���Ҳ���Ϊ0��");
		 alert("�����������Ϊ��");
		 frm.amount.focus();
		 return false;	
  	 }else if(Trim(frm.place.value) == ""){
  	     alert("�����ص㲻��Ϊ�գ�");
		 frm.place.focus();
		 return false;	
  	 }else if(Trim(frm.variety.value) == ""){
  	     alert("Ʒ�ֲ���Ϊ�գ�");
		 frm.variety.focus();
		 return false;	
  	 }else if(parseFloat(frm.amount.value)>parseFloat(frm.tempTotal.value)){
	     alert("���������������������!");
	     frm.amount.focus();
	     return false;
	 }else if(parseFloat(frm.amount.value)>(parseFloat(isFormat((parseFloat(frm.tempTotal.value)-parseFloat(frm.templiftAmount.value)+parseFloat(frm.oldAmount.value)),4)))){
	  	     alert("���������������ʣ���������!");
	  		 frm.amount.focus();
	  	     return false;
	 }
	 else{
	   amountTotal=parseFloat(frm.amount.value);
	   if(amountTotal!=parseFloat(frm.amountTotal.value)){
	      alert("�����������ȱ��γ���������!");
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

//����״̬
function controlStatus(){
  var outlogStatus=frm.outlogStatus.value;
  if(parseInt(outlogStatus)==0&&(document.getElementById("r_1").checked==true||document.getElementById("r_2").checked==true||document.getElementById("r_0").checked==true)){
    alert("�������ɳ��ⵥ�������ɳ��ⵥ״̬!");
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

//���汸ע
function saveRemark(){
  if(Trim(frm.remark.value) == ""){
    alert("��ע����Ϊ��!");
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

//�鿴���ⵥ��Ϣ
function viewRecord(commodityID){
  var a=openDialog("addRecord.jsp?commodityID="+commodityID+"","_blank","600","550");
    if(1==a)
      window.location.reload();	
}

//��س��ⵥ
function beatBack(){
   if(userConfirm()){
      frm.add.value="beatBack";	
      frm.submit();
   }else{
      return false;
   }
}



//��ӡ���ⵥ
function printRecord(commodityID,capital,outID,amountTotal,outlogId,str6,contractID){ result=PopWindow("recordTemp.jsp?commodityID="+commodityID+"&capital="+capital+"&outID="+outID+"&amountTotal="+amountTotal+"&outlogId="+outlogId+"&str6="+str6+"&contractID="+contractID,670,600);
}

//Ԥ�����ⵥ
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