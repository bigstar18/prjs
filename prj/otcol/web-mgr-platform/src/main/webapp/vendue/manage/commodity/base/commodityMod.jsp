<%@ page contentType="text/html;charset=GBK" %>
<base target="_self">
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>
<%
DBBean bean=null;
ResultSet rs=null;
try {
	String pd = TradeDAOFactory.getDAO().getPD();
	TradeDAO to = TradeDAOFactory.getDAO();
	QueryValue qv = new QueryValue();
  	qv.filter = "";
  	qv.pageIndex = 1;
 	qv.pageSize = 100;  //ÿҳ��ʾ10��
 	qv.filter += " order by id ";
 	CommodityTypeVO[] ctvs = to.getCommodityType(qv);
 	//��ȡ����
	Configuration cfn = new Configuration();
	//��֤���Ƿ�����Լ��趨��ȡ��ʽ 0 ������ 1 ����  ����ʱ��֤����ȡ��ʽ�����ʧЧ
	int securitybyself = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("SecurityBySelf")); 
	//�������Ƿ�����Լ��趨��ȡ��ʽ 0 ������ 1 ����  ����ʱ��������ȡ��ʽ�����ʧЧ/
	int feebyself = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("FeeBySelf"));
	int securityType = 1;
	if(securitybyself==0) {
		securityType = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("SecurityType")); //��֤����ȡ��ʽ  0 Ϊ����ֵ 1 Ϊ�ٷֱ�
	} else {
		if(request.getParameter("str7")!=null) {
			securityType = Integer.valueOf(request.getParameter("str7"));
		}
	}
	int feeType = 1;
	if(feebyself==0) {
		feeType = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("FeeType")); //��������ȡ��ʽ  0 Ϊ����ֵ 1 Ϊ�ٷֱ�
	} else {
		if(request.getParameter("str8")!=null) {
			feeType =  Integer.valueOf(request.getParameter("str8"));
		}
	}		    
%>
<!--ȡ�ò���-->
<head>
  <title>�޸���Ʒ</title>
</head>
<c:if test="${not empty param.add}">
<c:if test="${param.add=='add'}">
<%
	try {
  		java.util.Date curdate = new java.util.Date();
  		java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
  		String firstTime=request.getParameter("firstTime");
  		String code=request.getParameter("code");
  		String id=request.getParameter("id");
  		int tradeMode=new Integer(request.getParameter("tradeMode")).intValue();//����ģʽ
  		double beginPrice=new Double(request.getParameter("beginPrice")).doubleValue();//�𱨼�
  		double alertPrice=new Double(request.getParameter("alertPrice")).doubleValue();//������
  		java.sql.Date a=null;
  		java.sql.Date convertDate=a.valueOf(firstTime);
  		//�жϱ�ĺ����һ�ι���ʱ���Ƿ��ظ�
 	 	boolean addFlag=true;
  		bean=new DBBean(JNDI);
  		rs=bean.executeQuery("select t1.id from v_commodity t1,v_commext t2 where t1.id=t2.commid and t1.code='"+code.trim()+"' and t1.firsttime=to_date('"+firstTime+"','yyyy-mm-dd') and t1.id<>"+id+"");
  		if(rs.next()) {
    		addFlag=false;
  		}
  		rs.close();
  		bean.closeStmt();
  		if(addFlag==true) {
  			//�ڷ������˿���״̬�Ƿ������޸�
  			String mess="";
  			boolean controlFlag=true;
  			rs=bean.executeQuery("select status from v_commodity t1,v_commext t2 where t1.id=t2.commid and t1.id="+id+" and status<>2");
  			if(rs.next()) {
    			controlFlag=false;
			if(rs.getInt("status")==1) {
		  		mess="��Ʒ���ǳɽ�״̬,�������޸�,�޸�ʧ��!";
			} else if(rs.getInt("status")==3) {
		  		mess="��Ʒ���Ǳ����״̬,�������޸�,�޸�ʧ��!";
			} else if(rs.getInt("status")==4) {
		  		mess="��Ʒ���Ǳ��ϱ�״̬,�������޸�,�޸�ʧ��!";
			} else if(rs.getInt("status")==5) {
		  		mess="��Ʒ���Ƿϳ�״̬,�������޸�,�޸�ʧ��!";
			}
	  	}
	  	rs.close();
	  	bean.closeStmt();
	  	if(controlFlag) {
%>
	<c:set var="updateFlag" value="0"/>
		<db:select var="c" table="v_curcommodity" columns="count(code) as v " where="commodityid=${param.id}">
    		<c:set var="updateFlag" value="${c.v}"/>
    	</db:select>
    	<c:choose>
    		<c:when test="${updateFlag==0}">
    			<c:set var="convertDate" value="<%=convertDate%>"/>
    			<c:set var="sqlDate" value="<%=sqlDate%>"/>
<%
			//---------------------   ��Ʒ����   <<<<<<<<<<<<<<<<<<
		   	String str5 = request.getParameter("id_now");
		   	String projName = request.getParameter("projName");
		   	if(projName.equals("")||projName==null) {
			   	QueryValue qv3 = new QueryValue();
			   	qv3.pageIndex = 1;
			   	qv3.pageSize = 100;  //ÿҳ��ʾ100��
			   	qv3.filter += " and cpid = "+str5+" order by id ";
			   	CommodityPropertyVO[] cpvs2 = to.getCommodityProperty(qv3);
			   	projName="place;";
			   	for(int i=0;i<cpvs2.length;i++) {
				   projName=projName+cpvs2[i].id+";";
			   	}
			   	projName=projName+"quanlity";
		   	}
		   	String[] pn = projName.split(";");
		   	int len = request.getParameterValues(pn[0]).length;
		   	String[][] str3_tem = new String[pn.length][len];
		   	for(int i=0;i<pn.length;i++) {
				str3_tem[i] = request.getParameterValues(pn[i]);
		   	}
		   	String stepPrice = Tool.fmtDouble2(toDouble(request.getParameter("stepPrice")));
		   	String userId=request.getParameter("userId");//����������
		   	boolean sellerFlag=false;//�ж������������Ƿ����
	  	   	String str3="";//�洢��Թ�ϵ�ֶ�
	  	   	String str4="";//��Ʒ��������
		   	//�ж��չ����,Ʒ��,����,�Ƿ��Ѿ�����,���������ʾ���û�������ͬ����Ϣ
	       	String repeCommFlag="";
	       	String amount=request.getParameter("amount");
	       	String str1=request.getParameter("str1");
	       	String str2=request.getParameter("str2");
		   	String str6=request.getParameter("str6");  //-----��Ʒ�����id
	  	   	String crade="dengji";
	  	   	if(crade!=null) {
			   	for(int i=0;i<str3_tem[0].length;i++) {
					for(int j=0;j<str3_tem.length;j++) {
						str3=str3+str3_tem[j][i]+",";
				   	}
				   	str3=str3.substring(0,str3.length()-1)+";";
			   	}
			   	for(int z=0;z<ctvs.length;z++) {
				   	if(ctvs[z].id==Integer.valueOf(str5)) {
					   	str4 = ctvs[z].name;
				   	}
			   	}
			   	str3=str3.substring(0,str3.length()-1);
	  	   	}
	  		//�ж�����������������Ƿ����
	  		rs=bean.executeQuery("select usercode from v_tradeuser where usercode='"+userId+"'");
	  		if(rs.next()) {
	      		sellerFlag=true;
	  		}
	  		rs.close();
	  		bean.closeStmt();
	  		if(sellerFlag==true) {
	  			boolean priceFlag=true;//�ж��ڲ�ͬ����ģʽ���𱨼��뱨�������
	  			String priceMess=null;
	  			if(tradeMode==0&&alertPrice<beginPrice) {
		  			priceFlag=false;
	      			priceMess="����ģʽΪ����ʱ,�����۱�������𱨼�";
	  			}
	  			if(tradeMode==1&&alertPrice>beginPrice) {
		  			priceFlag=false;
	      			priceMess="����ģʽΪ����ʱ,�����۱���С���𱨼�";
	  			}
	 	 		if(tradeMode==2&&alertPrice>beginPrice) {
		  			priceFlag=false;
	      			priceMess="����ģʽΪ�б�ʱ,�����۱���С���𱨼�";
	  			}
	  			if(priceFlag==true) {
	  				int i=0;
	  				rs=bean.executeQuery("select t1.id,t1.code,t1.createtime from v_commodity t1,v_commext t2 where t1.id=t2.commid and t1.amount="+amount+" and t2.str2='"+str2+"' and t2.str1='"+str1+"' and t1.id<>"+id+"");
	  				while(rs.next()) {
						if(i==0) {
	      					repeCommFlag="ֻ�Ǵ�����ͬ�չ���㡢Ʒ�֡�����,�����ظ�¼����Ʒ,����ϸ���ġ�������ͬ����Ʒ����:\\r\\n";
		  					repeCommFlag+="��ĺ�:"+rs.getString("code")+",����ʱ��:"+rs.getString("createtime").substring(0,10)+"\\r\\n";
						} else {
		  					repeCommFlag+="��ĺ�:"+rs.getString("code")+",����ʱ��:"+rs.getString("createtime").substring(0,10)+"\\r\\n";
						}
						i++;
	  				}
	  				rs.close();
	  				bean.close();
					if(securityType==1) {
						if(feeType==1) {
%>
	  				<db:tranModCommodity id="${param.id}" code="${param.code}" firsttime="${convertDate}" createtime="${param.createtime}" status="${param.status}"
	                                     splitid="${param.splitID}" category="${param.category}" beginprice="${param.beginPrice}" stepprice="<%=stepPrice%>"
	                                     amount="${param.amount}" tradeunit="${param.tradeUnit}" alertprice="${param.alertPrice}" b_security="${param.b_security/100}"
	                                     b_fee="${param.b_fee/100}" s_security="${param.s_security/100}" s_fee="${param.s_fee/100}" minamount="0" userid="${param.userId}"
	                                     trademode="${param.tradeMode}" commid="${param.id}" str1="${param.stepPrice}" str2="${param.str2}" str3="<%=str3%>"
	                                     str4="<%=str4%>" str5="<%=str5%>" str6="<%=str6%>" str7="<%=String.valueOf(securityType)%>" str8="<%=String.valueOf(feeType)%>"
	                                     str12="${param.str12}" str13="${param.str13}" str14="${param.str14}" str15="${param.str15}" str16="${param.str16}"
	                                     str17="${param.str17}" str19="${param.str19}" num1="${param.templet}" />
<% 
						} else {
%>
	  				<db:tranModCommodity id="${param.id}" code="${param.code}" firsttime="${convertDate}" createtime="${param.createtime}" status="${param.status}"
	                                     splitid="${param.splitID}" category="${param.category}" beginprice="${param.beginPrice}" stepprice="<%=stepPrice%>"
	                                     amount="${param.amount}" tradeunit="${param.tradeUnit}" alertprice="${param.alertPrice}" b_security="${param.b_security/100}"
	                                     b_fee="${param.b_fee}" s_security="${param.s_security/100}" s_fee="${param.s_fee}" minamount="0" userid="${param.userId}"
	                                     trademode="${param.tradeMode}" commid="${param.id}" str1="${param.stepPrice}" str2="${param.str2}" str3="<%=str3%>"
	                                     str4="<%=str4%>" str5="<%=str5%>" str6="<%=str6%>" str7="<%=String.valueOf(securityType)%>" str8="<%=String.valueOf(feeType)%>"
	                                     str12="${param.str12}" str13="${param.str13}" str14="${param.str14}" str15="${param.str15}" str16="${param.str16}"
	                                     str17="${param.str17}" str19="${param.str19}" num1="${param.templet}" />
<%
						}
					} else {
						if(feeType==1) {
%>
	   				<db:tranModCommodity id="${param.id}" code="${param.code}" firsttime="${convertDate}" createtime="${param.createtime}" status="${param.status}"
	                                     splitid="${param.splitID}" category="${param.category}" beginprice="${param.beginPrice}" stepprice="<%=stepPrice%>"
	                                     amount="${param.amount}" tradeunit="${param.tradeUnit}" alertprice="${param.alertPrice}" b_security="${param.b_security}"
	                                     b_fee="${param.b_fee/100}" s_security="${param.s_security}" s_fee="${param.s_fee/100}" minamount="0" userid="${param.userId}"
	                                     trademode="${param.tradeMode}" commid="${param.id}" str1="${param.stepPrice}" str2="${param.str2}" str3="<%=str3%>"
	                                     str4="<%=str4%>" str5="<%=str5%>" str6="<%=str6%>" str7="<%=String.valueOf(securityType)%>" str8="<%=String.valueOf(feeType)%>"
	                                     str12="${param.str12}" str13="${param.str13}" str14="${param.str14}" str15="${param.str15}" str16="${param.str16}"
	                                     str17="${param.str17}" str19="${param.str19}" num1="${param.templet}" />
<%
						} else {
%>
	   				<db:tranModCommodity id="${param.id}" code="${param.code}" firsttime="${convertDate}" createtime="${param.createtime}" status="${param.status}"
	                                     splitid="${param.splitID}" category="${param.category}" beginprice="${param.beginPrice}" stepprice="<%=stepPrice%>"
	                                     amount="${param.amount}" tradeunit="${param.tradeUnit}" alertprice="${param.alertPrice}" b_security="${param.b_security}"
	                                     b_fee="${param.b_fee}" s_security="${param.s_security}" s_fee="${param.s_fee}" minamount="0" userid="${param.userId}"
	                                     trademode="${param.tradeMode}" commid="${param.id}" str1="${param.stepPrice}" str2="${param.str2}" str3="<%=str3%>"
	                                     str4="<%=str4%>" str5="<%=str5%>" str6="<%=str6%>" str7="<%=String.valueOf(securityType)%>" str8="<%=String.valueOf(feeType)%>"
	                                     str12="${param.str12}" str13="${param.str13}" str14="${param.str14}" str15="${param.str15}" str16="${param.str16}"
	                                     str17="${param.str17}" str19="${param.str19}" num1="${param.templet}" />
<%
						}
					}
%>
				<SCRIPT LANGUAGE="JavaScript">
				   	alert("��Ʒ��Ϣ�޸ĳɹ���"+"<%=repeCommFlag%>");
				   	closeDialog(1);
				</SCRIPT>
<%
	    		} else {
		   			alert("�޸���Ʒʧ��,"+priceMess,out);
	    		}
	    	} else {
	         	alert("��������������̲�����,�����޸���Ʒ,�޸���Ʒʧ��!",out);
	    	}   	   
%>
	  		</c:when>
	  		<c:otherwise>
	  			<script language="javascript">
	    			alert("��Ʒ���ڽ���,�����޸�!");
	    			closeDialog(1);
	    		</script>
	  		</c:otherwise>
	  	</c:choose>
<%
		} else {
%>
	     	<SCRIPT LANGUAGE="JavaScript">
	     		alert("<%=mess%>");
		 		closeDialog(1);
	  		</SCRIPT>  
<%
		}
	} else {
%>
	  	<SCRIPT LANGUAGE="JavaScript">
	  		alert("������ͬ�ı�ĺ����һ�ι���ʱ��,����û�б���,������¼��!");
	  		closeDialog(1);
	  	</SCRIPT>
<%
	}
	//�ر�����Դ
	bean.close();
	} catch(Exception e) {
		e.printStackTrace();
		errOpt();
%>
	  	<SCRIPT LANGUAGE="JavaScript">
	   		alert("����ϵͳ,����ʧ��!");
	   		closeRefreshDialog();
	  	</SCRIPT>
<%
	} finally {
	       try{if(rs!=null)rs.close();}catch(Exception ex){}
	       if(bean!=null)bean.close();
	}
%>

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
<form name=frm id=frm action="" method="post">
<%
   	ResultSet rsAmount=null;
	int tradeMode=-1;//����ģʽ
	int templet=-1;//��ͬ�汾
	int divideAmount=0;//�Ѳ������
	int commAmount=0;//��Ʒ����
	String typeid = null;
	String commid_to = null;
	String str3 = null;
	String str6 = null;
	String str7 = null;
	String str8 = null;
	int category_now = 0;
	try {
		String id=request.getParameter("id");
		StringBuffer sql=new StringBuffer("select u1.id,u1.code,u1.firsttime,u1.createtime,u1.status,u1.splitid,u1.category,u1.beginprice,u1.stepprice,u1.amount,u1.tradeunit,u1.alertprice,u1.b_security,u1.b_fee,u1.s_security,u1.s_fee,u1.minamount,u1.trademode,u2.str1,");	sql.append("u2.str2,u2.str3,u2.str4,u2.str5,u2.str6,u2.str7,u2.str8,u2.str9,u2.str10,u2.str11,u2.str12,u2.str13,u2.str14,u2.str15,u2.str16,u2.str17,u2.str18,u2.str19,u2.num1,");
		sql.append("u1.userid,u1.category from v_commodity u1,v_commext u2 where u1.id=u2.commid and u1.id="+id+"");
		bean=new DBBean(JNDI);
		rs=bean.executeQuery(sql.toString());
%>
	<fieldset width="100%">
		<legend>�޸���Ʒ</legend>
		<BR>
		<span>
<%
		while(rs.next()){
			String commid=rs.getString("id");
		    String code=rs.getString("code");
		    int status=rs.getInt("status");
			String splitID=rs.getString("splitid");
			typeid = rs.getString("str5"); //��Ʒ����id 
			commid_to = rs.getString("id");  //��Ʒid
			str3 = rs.getString("str3");
		    tradeMode=rs.getInt("trademode");
		    templet=rs.getInt("num1");
			commAmount=rs.getInt("amount");
			pageContext.setAttribute("tradeMode",tradeMode);
			str6 = rs.getString("str6");
			str7 = rs.getString("str7");
			str8 = rs.getString("str8");
			securityType = Integer.parseInt(rs.getString("str7"));
			feeType = Integer.parseInt(rs.getString("str8"));
			category_now = rs.getInt("category");
%>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<input type="hidden" name="id" value="<%=commid%>">
         	<input type="hidden" name="status" value="<%=delNull(rs.getString("status"))%>">
           	<input type="hidden" name="splitID" value="<%=delNull(splitID)%>">
          	<input type="hidden" name="createtime" value="<%=rs.getString("createtime").substring(0,10)%>">
			<tr height="35">
          		<td align="left" colspan="6">״̬��
          			<font color="red">
          				<% if(status==1){ %>�ɽ� <%}else if(status==2){%>δ�ɽ� <%}else if(status==3){%>��� <%}else if(status==4){%>�ϱ�<%}else if(status==5){%>�ϳ� <%}%>
          			</font>
          		</td>
        	</tr>
        	<c:set var="status" value="<%=String.valueOf(status)%>"/>
        	<c:set var="commid" value="<%=commid%>"/>
        	<c:set var="splitid" value="<%=splitID%>"/>
<%
			String ids="";
		  	if(status==3) {	
%>
        	<c:set var="ids" value="<%=splitID%>"/>
			<c:set var="inCondition" value=""/>
			<c:choose>
           		<c:when test="${ids=='0'}">
              		<c:set var="inCondition" value="${ids}"/>
		   		</c:when>
		   		<c:otherwise>
              		<c:set var="inCondition" value="${ids}''"/>
		   		</c:otherwise>
			</c:choose>
        	<tr height="35">
        		<td align="left" colspan="2">�ӱ꣺
           			<db:select var="row" table="v_commodity" columns="<%=COLS_COMMODITY%>" where="id in (${inCondition})">
                		<a href="javascript:editUserInfo(${row.id},${status});" class="normal">${row.code}</a>&nbsp;&nbsp;&nbsp;&nbsp;
           			</db:select>
         		</td>
        	</tr>
<%
			} else if(status==4) {
%>
         	<tr height="35">
           		<td align="left" colspan="2">�ӱ꣺
          			<db:select var="row" table="v_commodity" columns="<%=COLS_COMMODITY%>" where="id=${splitid}">
            			<a href="javascript:editUserInfo(${row.id},${status});" class="normal">${row.code}</a>
          			</db:select>
         		</td>
         	</tr>
	 		<tr height="35">
           		<td align="left"></td>
               	<td align="left">
               		<input name="str2" type="hidden" class="text" style="width: 220px;" value="<%=delNull(rs.getString("str2"))%>">&nbsp;<font color="#ff0000"></font>
               	</td>
       		</tr>
<%
			}
%>
			<tr height="35">
            	<td align="left" width="23%"> ��ĺţ�</td>
                <td align="left" colspan="5">
                	<input name="code" type="text" class="text" style="width: 220px;" value="<%=delNull(code)%>" readonly>&nbsp;<font color="#ff0000">*</font>
                </td>
        	</tr>
			<tr height="35">
            	<td align="left"> ��Ʒ������</td>
                <td align="left" colspan="5">
					<input type="text" name="viewAmount" class="text" style="width: 220px;" value="<%=commAmount%>" readonly>
                <input name="tradeUnit" type="hidden"  value="<%=rs.getDouble("tradeUnit")%>">
                </td>
        	</tr>
        	<tr>
			<tr height="35">
        		<td align="left">���������̣�</td>
        		<td align="left" colspan="5">
        			<input name="userId" type="text" class="text" style="width: 220px;" readonly value="<%=delNull(rs.getString("userid"))%>">&nbsp;<font color="red">*</font>
        		</td>
        	</tr>
			<tr height="35">
        		<td align="left">����ģʽ��</td>
        		<td align="left">
        			<select name="tradeMode">
						<db:select var="row" table="v_syspartition" columns="partitionid,description,trademode" where="validflag=1">
							<option value="${row.trademode}" <c:if test="${tradeMode==row.trademode}">selected</c:if>>${row.description}</option>
						</db:select>
        			</select>&nbsp;<font color="red">*</font>
        		</td>
<%
			if(securitybyself==1) {
%>
				<td align="left">��֤����ȡ��ʽ��</td>
        		<td align="left">
        			<select name="str7" onChange="changeSecurityType(this.value)">
						<option value="1" <%if(str7.trim().equals("1")){%>selected<%}%>>�ٷֱ�</option>
						<option value="0" <%if(str7.trim().equals("0")){%>selected<%}%>>����ֵ</option>
        			</select>&nbsp;<font color="red">*</font>
					<input type="hidden" name="str7_tem" value="<%=str7%>">
        		</td>
<%
			}
			if(feebyself==1) {
%>
				<td align="left">��������ȡ��ʽ��</td>
        		<td align="left">
        			<select name="str8" onChange="changeFeeType(this.value)">
						<option value="1" <%if(str8.trim().equals("1")){%>selected<%}%>>�ٷֱ�</option>
						<option value="0" <%if(str8.trim().equals("0")){%>selected<%}%>>����ֵ</option>
        			</select>&nbsp;<font color="red">*</font>
					<input type="hidden" name="str8_tem" value="<%=str8%>">
        		</td>
<%
			}
%>
	    	</tr>
<%
		  	if(status==3) {	
%>
			<tr height="35">
        		<td align="left">�ɲ��������</td>
        		<td align="left">
<%
		    	sql=new StringBuffer();
		    	sql.append("select sum(amount) s_v from v_commodity where id in ("+splitID+"'')");
				rsAmount=bean.executeQuery(sql.toString());
				if(rsAmount.next()) {
			    	divideAmount=rsAmount.getInt("s_v");
				}
%>
        			<input type="text" name="" class="text" style="width: 220px;" value="<%=commAmount-divideAmount%>" readonly>
				</td>
	    	</tr>
<%
		  	}	
%>
			<tr>
        		<td colspan="6">
        			<table border="0" cellspacing="0" cellpadding="0" width="95%">
        				<tr height="35">
                			<td align="left">��һ�ιұ����ڣ�</td>
                			<td align="left" colspan="2"> 
                  				<MEBS:calendar eltID="firstTime" eltName="firstTime" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="<%=delNull(rs.getString("firsttime"))%>"/>&nbsp;<font color="#ff0000">*</font>
                			</td>
                			<td align="left">��Ʒ���ࣺ</td>
							<td align="left" colspan="2">
                				<select name="category" onChange="changeType(this.value)" >
                				<%for(int i=0;i<ctvs.length;i++) {%>
                					<option value="<%=ctvs[i].id%>" <%if(category_now==ctvs[i].id){out.println("selected");}%>><%=ctvs[i].name%></option>
                				<%}%>
			                	</select>&nbsp;<font color="red">*</font>
			                </td>
			        	</tr>
						<tr height="35" style="display:none;">
			        		<td align="left">��ͬ�汾��</td>
			        		<td align="left">
								<c:set var="tempTemplet" value="<%=new Integer(templet)%>"/>
			        				<select name="templet">
										<db:select var="rowColumn" columns="id,name" table="v_contracttemplet" where="">
											<option value="${rowColumn.id}" <c:if test="${rowColumn.id==tempTemplet}">selected</c:if>>${rowColumn.name}</option>
										</db:select>
									</select>&nbsp;<font color="#ff0000">*</font>
			        		</td>
			        	</tr>
			           	<tr height="35">
			          		<td align="left">���ļۣ�</td>
			                <td align="left" colspan="2">
			                	<input name="beginPrice" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'���ļ�')" value="<%=rs.getBigDecimal("beginprice")%>">&nbsp;<font color="#ff0000">*</font>
			                </td>
			                <td align="left">�Ӽ۷��ȣ�</td>
			                <td align="left" colspan="2">
			                	<input name="stepPrice" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'�Ӽ۷���')" value="<%=rs.getBigDecimal("stepprice")%>">&nbsp;<font color="#ff0000">*</font>
			                </td>
			        	</tr>
			           	<tr height="35">
			                <td align="left">����������λ��</td>
			                <td align="left" colspan="2">
			                	<input name="str6" type="text" class="text" style="width: 180px;" readonly=true value="<%=delNull(rs.getString("str6"))%>">
			                </td>
			               	<td align="left">��ٽ���ʱ�䣺</td>
			                <td align="left" colspan="2">
								<%String str19=rs.getString("str19");%>
						    	<MEBS:calendar eltID="str19" eltName="str19" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="<%=delNull(rs.getString("str19"))%>"/>
							</td>
			 			</tr>
						<tr height="35">
							<td align="left">�����ۣ�</td>
			                <td align="left" colspan="2">
			         			<input name="alertPrice" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'������')" value="<%=rs.getBigDecimal("alertprice")%>">&nbsp;<font color="#ff0000">*</font>
			                </td>
			                <td align="left">��֤��(��)��</td>
			                <td align="left">
			                	<div id="b_security">
							  		<%if(securityType==1){%>
			                  		<input name="b_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'��֤��')" value="<%=rs.getBigDecimal("b_security").multiply(new BigDecimal(100))%>">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
							  		<%}else{%>
			                  		<input name="b_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkBail(this,false,false,'��֤��')" value="<%=rs.getBigDecimal("b_security")%>">&nbsp;<font color="#ff0000">*</font>
							  		<%}%>
							  	</div>
			            	</td>
							<td>
								<font color="#ff0000"><div id="b_security_u"><%if(securityType==0){%>Ԫ/<%=delNull(rs.getString("str6"))%><%}%></div></font>
							</td>
						</tr>
						<tr height="35">
			       			<td align="left">��֤��(����)��</td>
			                <td align="left">
			                	<div id="s_security">
									<%if(securityType==1){%>
									<input name="s_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'��֤��')" value="<%=rs.getBigDecimal("s_security").multiply(new BigDecimal(100))%>">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
									<%}else{%>
			                		<input name="s_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkBail(this,false,false,'��֤��')" value="<%=rs.getBigDecimal("s_security")%>">&nbsp;<font color="#ff0000">*</font>
									<%}%>
								</div>
			                </td>
							<td>
								<font color="#ff0000"><div id="s_security_u"><%if(securityType==0){%>Ԫ/<%=delNull(rs.getString("str6"))%><%}%></div></font>
							</td>
							<td align="left">������(��)��</td>
			                <td align="left">
			                	<div id="b_fee">
									<%if(feeType==1){%>
									<input name="b_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'������ϵ��')" value="<%=rs.getBigDecimal("b_fee").multiply(new BigDecimal(100))%>">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
									<%}else{%>
			                		<input name="b_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'������ϵ��')" value="<%=rs.getBigDecimal("b_fee")%>">&nbsp;<font color="#ff0000">*</font>
									<%}%>
								</div>
			                </td>
							<td>
								<font color="#ff0000"><div id="b_fee_u"><%if(feeType==0){%>Ԫ/<%=delNull(rs.getString("str6"))%><%}%></div></font>
							</td>
			     		</tr>
						<tr height="35">
			      			<td align="left">������(����)��</td>
			                <td align="left">
			                	<div id="s_fee">
									<%if(feeType==1){%>
									<input name="s_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'������ϵ��')" value="<%=rs.getBigDecimal("s_fee").multiply(new BigDecimal(100))%>">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
									<%}else{%>
			                		<input name="s_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'������ϵ��')" value="<%=rs.getBigDecimal("s_fee")%>">&nbsp;<font color="#ff0000">*</font>
									<%}%>
								</div>
			                </td>
							<td>
								<font color="#ff0000"><div id="s_fee_u"><%if(feeType==0){%>Ԫ/<%=delNull(rs.getString("str6"))%><%}%></div></font>
							</td>
			                <td align="left">���أ�</td>
			                <td align="left" colspan="2">
			               		<input name="str13" type="text" class="text" style="width: 180px;" value="<%=delNull(rs.getString("str13"))%>">
			                </td>   
						</tr>
			      		<tr height="35">
			           		<td align="left">������ݣ�</td>
			                <td align="left" colspan="2">
			                  	<input name="str12" type="text" class="text" style="width: 180px;" value="<%=delNull(rs.getString("str12"))%>">
			                </td>
			                <td align="left">������</td>
			                <td align="left" colspan="2">
			                	<input name="str14" type="text" class="text" style="width: 180px;" value="<%=delNull(rs.getString("str14"))%>">
			                </td>
			      		</tr>
			      		<tr height="35">
			                <td align="left">�����ص㣺</td>
			                <td align="left" colspan="2">
			                  	<input name="str15" type="text" class="text" style="width: 180px;"  value="<%=delNull(rs.getString("str15"))%>">
			                </td>
			                <td align="left"> ��վ���룺</td>
			                <td align="left" colspan="2">
			                	<input name="str16" type="text" class="text" style="width: 180px;" value="<%=delNull(rs.getString("str16"))%>">
			                </td> 
			       		</tr>
			      		<tr height="35"> 
							<td align="left"></td> 
			             	<td align="left" colspan="2">	
			            		<input name="id_now" type="hidden" class="text" value="<%=typeid%>" readonly="true" style="width: 180px;">&nbsp;<font color="#ff0000"></font><input name="str1" type="hidden" class="text" style="width: 180px;" value="1">
			                </td>
			                <td >&nbsp;</td>
							<td colspan="2">&nbsp;</td>
			   			</tr>
						<input type="hidden" name="amount" value="0">  <!-- �����Ʒ���� -->
						<input type="hidden" name="isnull" value="place,ʵ�ʴ洢�������;quanlity,����;">  <!-- ��Ų���Ϊ�յ�������   ��Ʒ����   -->
						<input type="hidden" name="projName" value="place;"> <!-- �����Ʒ����������     ��Ʒ���� -->
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="6">
                  	<table id="table1" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
<%
			QueryValue qv2 = new QueryValue();
			qv2.pageIndex = 1;
			qv2.pageSize = 100;  //ÿҳ��ʾ100��
			qv2.filter += " and cpid = "+typeid+" order by id ";
			CommodityPropertyVO[] cpvs = to.getCommodityProperty(qv2);
%>
						<tr>
							<td align="center" width="15%">ʵ�ʴ洢�������<font color="#ff0000">*</font></td>
<%
			for(int i=0;i<cpvs.length;i++) {
%>
				<SCRIPT LANGUAGE="JavaScript">
					frm.projName.value=frm.projName.value+<%=cpvs[i].id%>+";";
				</SCRIPT>
<%
				if(cpvs[i].isnull==1) {
%>
							<td align="center" width="10%"><%=cpvs[i].name%><font color="#ff0000">*</font></td>
					<SCRIPT LANGUAGE="JavaScript">
						frm.isnull.value=frm.isnull.value+<%=cpvs[i].id%>+","+<%="'"+cpvs[i].name+"'"%>+";";
					</SCRIPT>
<%
				} else {
%>
							<td align="center" width="10%"><%=cpvs[i].name%></td>
<%
				}
			}
%>
			<SCRIPT LANGUAGE="JavaScript">
				frm.projName.value=frm.projName.value+"quanlity";
			</SCRIPT>
							<td align="center" width="10%">����<font color="#ff0000">*</font></td>
							<td align="center" width="6%"></td>
				  		</tr>
<%
			String[] proj_all = str3.split(";");
			for(int i=0;i<proj_all.length;i++) {
%>
						<tr>
<%
				String[] proj_one = proj_all[i].split(",");
				String[] tem = proj_all[i].split(",");
				for(int j=0;j<proj_one.length;j++) {
					if(j==0) {
%>
							<td align="center">
								<input name="place" type="text" maxlength="56" onchange="checkNumber_any(this.value,'ʵ�ʴ洢�������')" class="text" style="width: 80px;" value="<%=proj_one[j]%>">
							</td>
<%
					} else if(j==proj_one.length-1) {
%>
							<td align="center">
								<input name="quanlity" type="text" onchange="checkNumber_cao(this,false,false,false,'����')" class="text" style="width: 80px;" value="<%=proj_one[j]%>">
							</td>
<%
					} else { 
						if(cpvs[j-1].type==3) {
							String[] ops = cpvs[j-1].charvartext.split(",");
%>
							<td align="center">
								<SELECT NAME="<%=cpvs[j-1].id%>" class="text">
<%
								for(int z=0;z<ops.length;z++) {
%>
									<option value="<%=ops[z]%>"><%=ops[z]%></option>
<%
								}
%>
								</select>
							</td>
<%
						} else if(cpvs[j-1].type==2) {
%>
							<td align="center">
								<input name="<%=cpvs[j-1].id%>" onchange="checkNumber_xie(this,false,false,true,'<%=cpvs[j-1].name%>')" type="text" class="text" style="width: 80px;" value="<%=proj_one[j]%>">&nbsp;<font color="#ff0000">%</font>
							</td>
<%
						} else if(cpvs[j-1].type==1) {
%>
							<td align="center">
								<input name="<%=cpvs[j-1].id%>" onchange="checkNumber(this,false,false,'<%=cpvs[j-1].name%>')" type="text" class="text" style="width: 80px;" value="<%=proj_one[j]%>">
							</td>
<%
						} else {
%>
							<td align="center">
								<input name="<%=cpvs[j-1].id%>" onchange="checkNumber_any(this.value,'<%=cpvs[j-1].name%>')" type="text" class="text" style="width: 80px;" value="<%=proj_one[j]%>">
							</td>
<%
						}
					}
				}
%>
							<td align="center">
								<span onclick="this.parentNode.parentNode.removeNode(true);" style="cursor:hand;text-decoration:underline;">ɾ��</span>
							</td>
					  	</tr>
<%
			}
%>
                   	</table>
                </td>
			</tr>
		</table>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr height="25">
              	<td width="40%">
              		<%if(status==2){%>
              		<div align="center" class="Noprint"><br>
              			<input type="button" onclick="addProj();" class="btn" value="���ӷ�¼">
              		</div>
              		<%}%>
              	</td>
        	</tr>
 		</table>
		<br>
     	<table border="0" cellspacing="0" cellpadding="0" width="100%">
          	<tr height="35">
          		<td width="40%">
          			<div align="center">
              			<input type="hidden" name="add">
			  			<%if(status==2||status==3){%>
			  				<%if(status==2){%>
			    			<input type="button" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
			  				<%}%>
			  			<%}%>
			  			<input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">
            		</div>
            	</td>
          	</tr>
		</table>
<%
		}
       	//�ر�����Դ
       	rs.close();
       	bean.close();
	} catch(Exception e) {
  	  	e.printStackTrace();
      	errOpt();
	}		
%>
</form>
</body>

<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk() { 
	var status=frm.status.value;
	if(Trim(frm.code.value) == "") {
		alert("��ĺŲ���Ϊ�գ�");
		frm.code.focus();
		return false;
	} else if(Trim(frm.firstTime.value) == "") {
		alert("��һ�ιұ����ڲ���Ϊ�գ�");
		frm.firstTime.focus();
		return false;
	} else if(Trim(frm.category.value) == "") {
		alert("��Ʒ���಻��Ϊ�գ�");
		frm.category.focus();
		return false;
	} else if(Trim(frm.beginPrice.value) == "") {
		alert("���ļ۲���Ϊ�գ�");
		frm.beginPrice.focus();
		return false;
	} else if(isNaN(Trim(frm.beginPrice.value))) {
		alert("���ļ�ֻ��Ϊ���֣�");
		frm.beginPrice.focus();
		return false;
	} else if(parseFloat(Trim(frm.beginPrice.value))<0) {
		alert("���ļ۱������0��");
		frm.beginPrice.focus();
		return false;
	} else if(Trim(frm.stepPrice.value) == "") {
		alert("�Ӽ۷��Ȳ���Ϊ�գ�");
		frm.stepPrice.focus();
		return false;
	} else if(isNaN(Trim(frm.stepPrice.value))) {
		alert("�Ӽ۷��ȱ���Ϊ���֣�");
		frm.stepPrice.focus();
		return false;
	} else if(parseFloat(Trim(frm.stepPrice.value))<=0) {
		alert("�Ӽ۷��ȱ������0��");
		frm.stepPrice.focus();
		return false;
	} else if(status==1) {
	     alert("��Ʒ�ǳɽ�״̬,�����޸�!");
	     return false;
	} else if(status==5) {
	     alert("��Ʒ�Ƿϳ�״̬,�����޸�!");
	     return false;
	} else if(Trim(frm.tradeUnit.value) == "") {
		alert("����������λ����Ϊ�գ�");
		frm.tradeUnit.focus();
		return false;
	} else if(isNaN(Trim(frm.tradeUnit.value))) {
		alert("����������λֻ��Ϊ���֣�");
		frm.tradeUnit.focus();
		return false;
	} else if(parseFloat(Trim(frm.tradeUnit.value))<0) {
		alert("����������λ�������0��");
		frm.tradeUnit.focus();
		return false;
	} else if(Trim(frm.str1.value) == "") {
		alert("Ʒ�ֲ���Ϊ�գ�");
		frm.str1.focus();
		return false;
	} else if(Trim(frm.alertPrice.value) == "") {
		alert("�����۲���Ϊ�գ�");
		frm.alertPrice.focus();
		return false;
	} else if(isNaN(Trim(frm.alertPrice.value))) {
		alert("�����۱���Ϊ���֣�");
		frm.alertPrice.focus();
		return false;
	} else if(parseFloat(Trim(frm.alertPrice.value))<0) {
		alert("�����۱������0��");
		frm.alertPrice.focus();
		return false;
	} else if(Trim(frm.b_security.value) == "") {
		alert("��֤��(��)ϵ������Ϊ�գ�");
		frm.b_security.focus();
		return false;
	} else if(isNaN(Trim(frm.b_security.value))) {
		alert("��֤��(��)ϵ������Ϊ���֣�");
		frm.b_security.focus();
		return false;
	} else if(parseFloat(Trim(frm.b_security.value))<0) {
		alert("��֤��(��)ϵ���������0��");
		frm.b_security.focus();
		return false;
	} else if(Trim(frm.s_security.value) == "") {
		alert("��֤��(��)ϵ������Ϊ�գ�");
		frm.s_security.focus();
		return false;
	} else if(isNaN(Trim(frm.s_security.value))) {
		alert("��֤��(��)ϵ������Ϊ���֣�");
		frm.s_security.focus();
		return false;
	} else if(parseFloat(Trim(frm.s_security.value))<0) {
		alert("��֤��(��)ϵ���������0��");
		frm.s_security.focus();
		return false;
	} else if(Trim(frm.b_fee.value) == "") {
		alert("������(��)ϵ������Ϊ�գ�");
		frm.b_fee.focus();
		return false;
	} else if(isNaN(Trim(frm.b_fee.value))) {
		alert("������(��)ϵ������Ϊ���֣�");
		frm.b_fee.focus();
		return false;
	} else if(parseFloat(Trim(frm.b_fee.value))<0) {
		alert("������(��)ϵ���������0��");
		frm.b_fee.focus();
		return false;
	} else if(Trim(frm.s_fee.value) == "") {
		alert("������(��)ϵ������Ϊ�գ�");
		frm.s_fee.focus();
		return false;
	} else if(isNaN(Trim(frm.s_fee.value))) {
		alert("������(��)ϵ������Ϊ���֣�");
		frm.s_fee.focus();
		return false;
	} else if(parseFloat(Trim(frm.s_fee.value))<0) {
		alert("������(��)ϵ���������0��");
		frm.s_fee.focus();
		return false;
	} else if(Trim(frm.userId.value) == "") {
		alert("���������̲���Ϊ�գ�");
		frm.userId.focus();
		return false;
	} else if(Trim(frm.tradeMode.value)==0&&parseFloat(Trim(frm.alertPrice.value))<parseFloat(Trim(frm.beginPrice.value))) {
		alert("����ģʽΪ����ʱ,�����۱�������𱨼�!");
		frm.alertPrice.focus();
		return false;
	} else if(Trim(frm.tradeMode.value)==1&&parseFloat(Trim(frm.alertPrice.value))>parseFloat(Trim(frm.beginPrice.value))) {
		alert("����ģʽΪ����ʱ,�����۱���С���𱨼�!");
		frm.alertPrice.focus();
		return false;
	} else if(Trim(frm.tradeMode.value)==2&&parseFloat(Trim(frm.alertPrice.value))>parseFloat(Trim(frm.beginPrice.value))) {
		alert("����ģʽΪ�б�ʱ,�����۱���С���𱨼�!");
		frm.alertPrice.focus();
		return false;
	}
	//--------------------------   ��Ʒ���� ------------------------------<<<<<<<<<<<<<<
	frm.amount.value=0;  //������Ϊ0
	var isnull_Name_all = frm.isnull.value.split(";");
	for(var i=0;i<isnull_Name_all.length-1;i++) {
		var isnull_Name = isnull_Name_all[i].split(",");
		if(!document.getElementById(isnull_Name[0])) {
			alert(isnull_Name[1]+"����Ϊ��");
			return false
		} else {
			var tem = document.getElementsByName(isnull_Name[0]);
			if(tem.length>0) {
				for(var j=0;j<tem.length;j++) {
					if(Trim(tem[j].value)=="") {
						alert(isnull_Name[1]+"����Ϊ��");
						return false;
					} else if(isnull_Name[0]=="quanlity") {
						frm.amount.value=parseFloat(frm.amount.value)+parseFloat(tem[j].value);
					}
				}
			}
		}
	}
	if(userConfirm()) {
	  	frm.add.value="add";
	  	frm.submit();
	} else {
		return false;
	}
}

function dismantleMark(id,code) {
	var quanlity=frm.quanlity;
	var crade=frm.crade;
	var amount=0;
	var status=frm.status.value;
	if(status==1) {
		alert("��Ʒ�ǳɽ�״̬,���ܲ��!");
	  	return false;
	} else if(status==5) {
	  	alert("��Ʒ�Ƿϳ�״̬,���ܲ��!");
	   	return false;
	}
	if(!quanlity) {
		amount=0;
	} else if(quanlity.length>1) {
		for(i=0;i<quanlity.length;i++) {
			if(Trim(quanlity[i].value) == "") {
	       		amount=amount+0;
	       	} else {
	         	amount=amount+parseFloat(quanlity[i].value);
	       	}
	 	}
	} else {
		if(Trim(quanlity.value) == "") {
	       	amount=0;
	 	} else {
	       	amount=quanlity.value;
	  	}
	}
	result=PopWindow("dismantleMark.jsp?id="+id+"&code="+code+"&amount="+amount+"",850,650);
}

//�鿴��Ʒ��Ϣ
function editUserInfo(id,status){
	result=PopWindow("commodityMod.jsp?flag=query&id="+id,850,650);
}
//------------------------------------------------------------��Ʒ����                  >>>>>>>>>>>>>>>>>>>>>>>
function changeType(typeid) {
	frm.id_now.value=typeid;
	A2(typeid);
}

function addProj() {
	A1(frm.id_now.value);
}

//Ajax ȡ����
function A1(typeid) { //��ӷ�¼
	var url = "commodityProperty_xml.jsp";
	if (window.XMLHttpRequest&&!(window.ActiveXObject)) { 	
		req = new XMLHttpRequest(); 
	} else if (window.ActiveXObject) {	
		req = new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	if(req) { 
		req.open("GET",url+"?cpid="+typeid, true); 
		req.onreadystatechange = complete; //ָ����readyState���Ըı�ʱ���¼�������
		req.send(null); 
	} 
}
var selectID="";
/*�������ص�XML�ĵ�*/
function complete() { //��ӷ�¼
	var tableObj=table1;
	var num = "����";
	var num2 = "ʵ�ʴ洢�������";
	if (req.readyState==4 && req.status == 200) { 
		var type = req.responseXML.getElementsByTagName("parameter");
		var len = tableObj.rows.length;	
		var Rows=tableObj.rows;//���������Rows
		var newRow = tableObj.insertRow(tableObj.rows.length)
		var Cells=newRow.cells;//���������Cells
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
		newCell.align="center"; 
		newCell.innerHTML='<td align="center"><input name="place" type="text" onchange="checkNumber_any(this.value,\''+num2+'\')" value="" id="place" class="text" style="width: 80px;"></td>';
		//�������
		for(var j=0;j<type.length;j++) {				
			var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
			newCell.align="center";
			if(type[j].hasChildNodes()) {
				var nodlist=type[j].childNodes;	
				var str=new Array(nodlist.length);  
				for(var i=0;i<nodlist.length;i++) {
					if(nodlist[i].hasChildNodes())
						str[i]=nodlist[i].firstChild.data;
					else str[i]='--';
				}					
				addnew(str,newCell);
			}
		}
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center"><input name="quanlity" type="text" id="quanlity" value="" onchange="checkNumber_cao(this,false,false,false,\''+num+'\')" value="" class="text" style="width: 80px;"></td>';
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center"><span onclick="this.parentNode.parentNode.removeNode(true);" style="cursor:hand;text-decoration:underline;">ɾ��</span></td>';
	}
}
//���
function addnew(str,newCell) {	
	var id=str[0];	 //��Ʒ����id
	var name=str[1];  //��Ʒ��������
	var type=str[2];   //��Ʒ��������
	var isnull=str[3];  //��Ʒ�����Ƿ����Ϊ��
	var charvartext=str[4];  //��Ʒ����������ѡ��
	if(type==3) {
		var ops = charvartext.split(",");
		var tem = '<td align="center"><SELECT NAME="'+id+'" id="'+id+'" class="text">';
		for(var i=0;i<ops.length;i++) {
			tem = tem+'<option value="'+ops[i]+'">'+ops[i]+'</option>';
		}
		tem = tem+'</select></td>';
		newCell.innerHTML=tem;
	} else if(type==2) {
		newCell.innerHTML='<td align="center"><input name="'+id+'" id="'+id+'" onchange="checkNumber_xie(this,false,false,true,\''+name+'\')" value="" type="text" class="text" style="width:80px;">&nbsp;<font color="#ff0000">%</font></td>';
	} else if(type==1) {
		newCell.innerHTML='<td align="center"><input name="'+id+'" id="'+id+'" onchange="checkNumber(this,false,false,\''+name+'\')" value="" type="text" class="text" style="width: 80px;"></td>';
	} else {
		newCell.innerHTML='<td align="center"><input name="'+id+'" id="'+id+'" onchange="checkNumber_any(this.value,\''+name+'\')" value="" type="text" class="text" style="width: 80px;"></td>';
	}
}

function addnew2(str,newCell) {	
	var id=str[0];	 //��Ʒ����id
	var name=str[1];  //��Ʒ��������
	var type=str[2];   //��Ʒ��������
	var isnull=str[3];  //��Ʒ�����Ƿ����Ϊ��
	var charvartext=str[4];  //��Ʒ����������ѡ��
	if(isnull==1) {
		newCell.innerHTML='<td align="center" width="10%">'+name+'<font color="#ff0000">*</font></td>';
	} else {
		newCell.innerHTML='<td align="center" width="10%">'+name+'</td>';
	}
}
function A2(typeid) { //��ӷ�¼�� ѡ������
	frm.category.value=typeid;
	frm.isnull.value="";  //��ղ���Ϊ�յ�����id��������
	frm.projName.value="";  //�����Ʒ������
	frm.isnull.value=frm.isnull.value+"place,ʵ�ʴ洢�������;quanlity,����;"
	frm.projName.value=frm.projName.value+"place;"
	var url = "commodityProperty_xml.jsp";
	if (window.XMLHttpRequest&&!(window.ActiveXObject)) { 	
		req = new XMLHttpRequest(); 
	} else if (window.ActiveXObject) {	
		req = new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	if(req) { 
		req.open("GET",url+"?cpid="+typeid, true); 
		req.onreadystatechange = complete2; //ָ����readyState���Ըı�ʱ���¼�������
		req.send(null); 
	} 
}
function complete2() { //��ӷ�¼�� ѡ������
	var tableObj=table1;
	if (req.readyState==4 && req.status == 200) {
		var type_dw = req.responseXML.getElementsByTagName("dw");
		frm.str6.value=type_dw[0].firstChild.data
		if(<%=securitybyself%>==1) {
			if(frm.str7_tem.value==0) {
					document.getElementById("b_security_u").innerHTML='Ԫ/'+type_dw[0].firstChild.data;
					document.getElementById("s_security_u").innerHTML='Ԫ/'+type_dw[0].firstChild.data;
			}
		}
		if(<%=feebyself%>==1) {
			if(frm.str8_tem.value==0) {
				document.getElementById("b_fee_u").innerHTML='Ԫ/'+type_dw[0].firstChild.data;
				document.getElementById("s_fee_u").innerHTML='Ԫ/'+type_dw[0].firstChild.data;
			}
		}
		var type = req.responseXML.getElementsByTagName("parameter");
		var len = tableObj.rows.length;			
		//�������
		while(tableObj.rows.length>0) {
			//�������						
			tableObj.deleteRow(0);
		}		
		var Rows=tableObj.rows;//���������Rows
		var newRow = tableObj.insertRow(0)
		var Cells=newRow.cells;//���������Cells
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
		newCell.align="center"; 
		newCell.innerHTML='<td align="center" width="15%">ʵ�ʴ洢�������<font color="#ff0000">*</font></td>';
		//�������
		for(var j=0;j<type.length;j++) {				
			var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
			newCell.align="center";
			if(type[j].hasChildNodes()) {
				//ȡ�ӽڵ�nodlist
				var nodlist=type[j].childNodes;	
				var str=new Array(nodlist.length);  
				for(var i=0;i<nodlist.length;i++) {
					if(nodlist[i].hasChildNodes())
						str[i]=nodlist[i].firstChild.data;
					else str[i]='--';
				}					
				addnew2(str,newCell);
				if(str[3]==1) {
					frm.isnull.value=frm.isnull.value+str[0]+","+str[1]+";";  //��Ų���Ϊ�յ�����id��������
				}
					frm.projName.value=frm.projName.value+str[0]+";";
			}
		}
		frm.projName.value=frm.projName.value+"quanlity";
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center" width="10%">����<font color="#ff0000">*</font></td>';
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center" width="6%">ɾ��</td>';
						
	}
}
function checkNumber_any(value,name) {
	var vas = value.indexOf(",");
	if(vas>-1) {
		alert(name+"�в������� , ");
		return false;
	}
	vas = value.indexOf(";");
	if(vas>-1) {
		alert(name+"�в������� ; ");
		return false;
	}
	return true;

}
//---------------------------------------------------------��Ʒ����           <<<<<<<<<<<<<<<<<<<<<<<<<<<,,,,,,
function changeSecurityType(typeid) {
	var b_securityDiv = document.getElementById("b_security");
	var s_securityDiv = document.getElementById("s_security");
	var text = "��֤��";
	if(typeid==1) {
		b_securityDiv.innerHTML="";
		b_securityDiv.innerHTML = '<input name="b_security" type="text" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,\''+text+'\')">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>';
		s_securityDiv.innerHTML=""
		s_securityDiv.innerHTML = '<input name="s_security" type="text" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,\''+text+'\')">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>';
		frm.str7_tem.value=1;
		document.getElementById("b_security_u").innerHTML='';
		document.getElementById("s_security_u").innerHTML='';
	} else {
		b_securityDiv.innerHTML="";
		b_securityDiv.innerHTML = ' <input name="b_security" type="text" class="text" style="width: 180px;" onchange="checkBail(this,false,false,\''+text+'\')">&nbsp;<font color="#ff0000">*</font>';
		s_securityDiv.innerHTML
		s_securityDiv.innerHTML = '<input name="s_security" type="text" class="text" style="width: 180px;" onchange="checkBail(this,false,false,\''+text+'\')">&nbsp;<font color="#ff0000">*</font>';
		frm.str7_tem.value=0;
		document.getElementById("b_security_u").innerHTML='Ԫ/'+frm.str6.value;
		document.getElementById("s_security_u").innerHTML='Ԫ/'+frm.str6.value;
	}
}

function changeFeeType(typeid) {
	var b_feeDiv = document.getElementById("b_fee");
	var s_feeDiv = document.getElementById("s_fee");
	var text = "������ϵ��";
	if(typeid==1) {
		b_feeDiv.innerHTML="";
		b_feeDiv.innerHTML='<input name="b_fee" type="text" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,\''+text+'\')" >&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>';
		s_feeDiv.innerHTML="";
		s_feeDiv.innerHTML='<input name="s_fee" type="text" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,\''+text+'\')" >&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>';
		frm.str8_tem.value=1;
		document.getElementById("b_fee_u").innerHTML='';
		document.getElementById("s_fee_u").innerHTML='';
	} else {
		b_feeDiv.innerHTML="";
		b_feeDiv.innerHTML='<input name="b_fee" type="text" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,\''+text+'\')" >&nbsp;<font color="#ff0000">*</font>';
		s_feeDiv.innerHTML="";
		s_feeDiv.innerHTML='<input name="s_fee" type="text" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,\''+text+'\')" >&nbsp;<font color="#ff0000">*</font>';
		frm.str8_tem.value=0;
		document.getElementById("b_fee_u").innerHTML='Ԫ/'+frm.str6.value;
		document.getElementById("s_fee_u").innerHTML='Ԫ/'+frm.str6.value;
	}
}

function checkBail(obj, isNotNull, isNotZero, msg) {
	var tradeMode = frm.tradeMode.value;
	var minMoney = 0;
	if(tradeMode==0)
		minMoney = frm.beginPrice.value;
	else
		minMoney = frm.alertPrice.value;
	if(minMoney=="") {
		alert("������д���ļۺͱ�����");
		obj.value="";
		return false;
	} else if(checkNumber(obj, isNotNull, isNotZero, msg)) {
		if(parseFloat(obj.value)>parseFloat(minMoney)) {
			alert("��֤���ܴ�����Ʒ����͵���");
			obj.value="";
			return false;
		}
	}
}

</SCRIPT>
<%
} catch(Exception e) {
	System.out.println(e.toString());
}
%>
</html>
<%@ include file="/vendue/manage/public/footInc.jsp" %>