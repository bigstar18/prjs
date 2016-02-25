<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>
<%@ taglib prefix="db" tagdir="/WEB-INF/tags/db" %>
<%
try {
	String pd = TradeDAOFactory.getDAO().getPD();
	TradeDAO to = TradeDAOFactory.getDAO();
  	QueryValue qv = new QueryValue();
  	qv.filter = "";
  	qv.pageIndex = 1;
 	qv.pageSize = 100;  //ÿҳ��ʾ10��
 	qv.filter += " order by id ";
 	CommodityTypeVO[] ctvs = to.getCommodityType(qv);
 	if(ctvs.length==0) {
%>
	<SCRIPT LANGUAGE="JavaScript">
		alert("�������Ʒ����");	
		window.location = "../../system/syscontrol/commodityType.jsp";
	</SCRIPT>
<%
	}
	//��ȡ����
	Configuration cfn = new Configuration();
	//��֤���Ƿ�����Լ��趨��ȡ��ʽ 0 ������ 1 ����  ����ʱ��֤����ȡ��ʽ�����ʧЧ
	int securitybyself = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("SecurityBySelf")); 
	//�������Ƿ�����Լ��趨��ȡ��ʽ 0 ������ 1 ����  ����ʱ��������ȡ��ʽ�����ʧЧ
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
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<c:if test="${not empty param.add}">
	<c:catch var="exceError">
<%
 	ResultSet rs=null;
  	DBBean bean=null;
  	try {
		java.util.Date curdate = new java.util.Date();
%>
		<db:select var="row" table="dual" columns="SP_V_COMMODITY.nextval as seqID">
			<c:set var="id" value="${row.seqID}"/>
		</db:select>
		<db:select var="row" table="dual" columns="SP_V_COMMODITY_CODE.nextval as seqID_code">  <!-- xieying �Զ�������ĺ� -->
			<c:set var="com_code" value="${row.seqID_code}"/>
		</db:select> 
<%
  		String firstTime=request.getParameter("firstTime");
  		java.sql.Date a=null;
  		//��һ�ι���ʱ�����ת�������ں���
  		java.sql.Date convertDate=a.valueOf(firstTime);
  		int tradeMode=new Integer(request.getParameter("tradeMode")).intValue();//����ģʽ
  		double beginPrice=new Double(request.getParameter("beginPrice")).doubleValue();//�𱨼�
  		double alertPrice=new Double(request.getParameter("alertPrice")).doubleValue();//������
%>
		<c:set var="convertDate" value="<%=convertDate%>"/>
		<c:set var="sqlDate" value="<%=sqlDate%>"/>
<%
		//---------------------   ��Ʒ����   <<<<<<<<<<<<<<<<<<
		String projName = request.getParameter("projName");
		String[] pn = projName.split(";");
		int len = request.getParameterValues(pn[0]).length;
		String[][] str3_tem = new String[pn.length][len];
		for(int i=0;i<pn.length;i++) {
			str3_tem[i] = request.getParameterValues(pn[i]);
	   	}
	   	String str5 = request.getParameter("id_now");
	   	String str6 = request.getParameter("str6"); //������λ
	   	String stepPrice = Tool.fmtDouble2(toDouble(request.getParameter("stepPrice")));
	   	
	   	DBBean bean1=null;   //xieying
	   	ResultSet rs1=null;  //xieying
	   	String qyName = "";
	   	// ----------------------------  ��Ʒ����   >>>>>>>>>>>>>>>>>>>
	   	String userId=request.getParameter("userId");//����������
	   	StringBuffer sql=new StringBuffer();   //xieying
		//sql.append("select "+COLS_USER_O+" from v_tradeuserext t1 where t1.usercode='"+userId+"'");  //xieying
		sql.append("select name from m_firm  where firmId='"+userId+"'");
		//System.out.println(sql.toString());
		bean1=new DBBean(JNDI);  //xieying
		rs1=bean1.executeQuery(sql.toString());  //xieying
		if(rs1.next()){
			qyName = delNull(rs1.getString("name"));  //xieying  ������������ҵ����
		}
	   	boolean sellerFlag=false;//�ж������������Ƿ����
	   	//�ж��չ����,Ʒ��,����,�Ƿ��Ѿ�����,���������ʾ���û�������ͬ����Ϣ
       	String repeCommFlag="";
       	String amount=request.getParameter("amount");
       	String str1=request.getParameter("str1");
       	String str2=qyName;//request.getParameter("str2");
  	   	String str3="";//�洢��Թ�ϵ�ֶ�
  	   	String str4="";//��Ʒ��������
	   	//--------------  ��Ʒ���� <<<<<<<<<<<<<<<<<<<<<<<<
	   	String crade="dengji";
  	   	if(crade!=null){
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
  		//�жϱ�ĺ����һ�ι���ʱ���Ƿ��ظ�
  		boolean addFlag=true;
  		bean=new DBBean(JNDI);  //xieying �Զ�������ĺ� ��ȡ���ж�����ı�ĺ�
  		rs=bean.executeQuery("select t1.id from v_commodity t1,v_commext t2 where t1.id=t2.commid and t1.code='"+"${com_code}"+"' and t1.firsttime=to_date('"+firstTime+"','yyyy-mm-dd')");
  		if(rs.next()){
    		addFlag=false;
  		}
  		rs.close();
  		if(addFlag==true){
  			//�ж�����������������Ƿ����
  			rs=bean.executeQuery("select usercode from v_tradeuser where usercode='"+userId+"'");
  			if(rs.next()){
      			sellerFlag=true;
  			}
  			rs.close();
  			bean.closeStmt();
  			//��������������̴������
			if(sellerFlag==true){
  				boolean priceFlag=true;//�ж��ڲ�ͬ����ģʽ���𱨼��뱨�������
  				String priceMess=null;
  				if(tradeMode==0&&alertPrice<beginPrice){
	  				priceFlag=false;
      				priceMess="����ģʽΪ����ʱ,�����۱�������𱨼�";
  				}
  				if(tradeMode==1&&alertPrice>beginPrice){
	  				priceFlag=false;
      				priceMess="����ģʽΪ����ʱ,�����۱���С���𱨼�";
  				}
  				if(tradeMode==2&&alertPrice>beginPrice){
	  				priceFlag=false;
      				priceMess="����ģʽΪ�б�ʱ,�����۱���С���𱨼�";
  				}
  				if(priceFlag==true){
  					int i=0;
  					rs=bean.executeQuery("select t1.id,t1.code,t1.createtime from v_commodity t1,v_commext t2 where t1.id=t2.commid and t1.amount="+amount+" and t2.str2='"+str2+"' and t2.str1='"+str1+"'");
 					 while(rs.next()){
						if(i==0){
      						repeCommFlag="ֻ�Ǵ�����ͬ�չ���㡢Ʒ�֡�����,�����ظ�¼����Ʒ,����ϸ���ġ�������ͬ����Ʒ����:\\r\\n";
	  						repeCommFlag+="��ĺ�:"+rs.getString("code")+",����ʱ��:"+rs.getString("createtime").substring(0,10)+"\\r\\n";
						}else{
	  						repeCommFlag+="��ĺ�:"+rs.getString("code")+",����ʱ��:"+rs.getString("createtime").substring(0,10)+"\\r\\n";
						}
						i++;
  					}
  					rs.close();
  					bean.close();
  					if(securityType==1){
						if(feeType==1){
%>
		<db:tranAddCommodity id="${id}" code="${com_code}" firsttime="${convertDate}" createtime="${sqlDate}" status="2" splitid="0" category="${param.category}"
  							  beginprice="${param.beginPrice}" stepprice="<%=stepPrice%>" amount="${param.amount}" tradeunit="${param.tradeUnit}" alertprice="${param.alertPrice}"
                              b_security="${param.b_security/100}" b_fee="${param.b_fee/100}" s_security="${param.s_security/100}" s_fee="${param.s_fee/100}"
                              minamount="0" userid="${param.userId}" trademode="${param.tradeMode}" commid="${id}" str1="${param.stepPrice}" str2="<%=str2%>"
                              str3="<%=str3%>" str4="<%=str4%>" str5="<%=str5%>" str6="<%=str6%>" str7="<%=String.valueOf(securityType)%>" str8="<%=String.valueOf(feeType)%>"
                              str12="${param.str12}" str13="${param.str13}" str14="${param.str14}" str15="${param.str15}" str16="${param.str16}" str17="${param.str17}"
                              str19="${param.str19}" num1="${param.templet}" />
<% 						
						} else {
%>
		<db:tranAddCommodity id="${id}" code="${com_code}" firsttime="${convertDate}" createtime="${sqlDate}" status="2" splitid="0" category="${param.category}"
                              beginprice="${param.beginPrice}" stepprice="<%=stepPrice%>" amount="${param.amount}" tradeunit="${param.tradeUnit}" alertprice="${param.alertPrice}"
                              b_security="${param.b_security/100}" b_fee="${param.b_fee}" s_security="${param.s_security/100}" s_fee="${param.s_fee}"
                              minamount="0" userid="${param.userId}" trademode="${param.tradeMode}" commid="${id}" str1="${param.stepPrice}" str2="<%=str2%>"
                              str3="<%=str3%>" str4="<%=str4%>" str5="<%=str5%>" str6="<%=str6%>" str7="<%=String.valueOf(securityType)%>" str8="<%=String.valueOf(feeType)%>"
                              str12="${param.str12}" str13="${param.str13}" str14="${param.str14}" str15="${param.str15}" str16="${param.str16}" str17="${param.str17}"
                              str19="${param.str19}" num1="${param.templet}" />
<%						
						}
					} else {
						if(feeType==1) {
%>
		<db:tranAddCommodity id="${id}" code="${com_code}" firsttime="${convertDate}" createtime="${sqlDate}" status="2" splitid="0" category="${param.category}"
                              beginprice="${param.beginPrice}" stepprice="<%=stepPrice%>" amount="${param.amount}" tradeunit="${param.tradeUnit}" alertprice="${param.alertPrice}"
                              b_security="${param.b_security}" b_fee="${param.b_fee/100}" s_security="${param.s_security}" s_fee="${param.s_fee/100}"
                              minamount="0" userid="${param.userId}" trademode="${param.tradeMode}" commid="${id}" str1="${param.stepPrice}" str2="<%=str2%>"
                              str3="<%=str3%>" str4="<%=str4%>" str5="<%=str5%>" str6="<%=str6%>" str7="<%=String.valueOf(securityType)%>" str8="<%=String.valueOf(feeType)%>"
                              str12="${param.str12}" str13="${param.str13}" str14="${param.str14}" str15="${param.str15}" str16="${param.str16}" str17="${param.str17}"
                              str19="${param.str19}" num1="${param.templet}" />
<%
						} else {
%>
		<db:tranAddCommodity id="${id}" code="${com_code}" firsttime="${convertDate}" createtime="${sqlDate}" status="2" splitid="0" category="${param.category}"
                              beginprice="${param.beginPrice}" stepprice="<%=stepPrice%>" amount="${param.amount}" tradeunit="${param.tradeUnit}" alertprice="${param.alertPrice}"
                              b_security="${param.b_security}" b_fee="${param.b_fee}" s_security="${param.s_security}" s_fee="${param.s_fee}" 
                              minamount="0" userid="${param.userId}" trademode="${param.tradeMode}" commid="${id}" str1="${param.stepPrice}" str2="<%=str2%>"
                              str3="<%=str3%>" str4="<%=str4%>" str5="<%=str5%>" str6="<%=str6%>" str7="<%=String.valueOf(securityType)%>" str8="<%=String.valueOf(feeType)%>"
                              str12="${param.str12}" str13="${param.str13}" str14="${param.str14}" str15="${param.str15}" str16="${param.str16}" str17="${param.str17}"
                              str19="${param.str19}" num1="${param.templet}" />
<%
						}
					}
%>
<SCRIPT LANGUAGE="JavaScript">
	alert("��Ʒ��ӳɹ���"+"<%=repeCommFlag%>");
	gotoUrl("commodityList.jsp?funcflg=true");
</SCRIPT>
<%
    			} else {
	     			alert("�����Ʒʧ��,"+priceMess,out);
    			}
    		} else {
       			alert("��������������̲�����,�����Ʒʧ��!",out);
    		}
		} else {
%>
<SCRIPT LANGUAGE="JavaScript">
	alert("������ͬ�ı�ĺ����һ�ι���ʱ��,����û�б���,������¼��!");
	gotoUrl("commodityAdd.jsp");
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
	alert("�����쳣,����ʧ��!");
</SCRIPT>
<%
 	} finally {
		try {
    	 	if(rs!=null)rs.close();
    	} catch(Exception ex){}
     	if(bean!=null)bean.close();
 	}
%>
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


<head>
	<title>�����Ʒ</title>
</head>
<body>
<form name=frm id=frm method="post">
	<fieldset width="80%">
		<legend>�����Ʒ</legend>
		<BR>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
				<tr height="35">
            		<td align="left"></td>
                	<td align="left" colspan="6">
                		<input name="str2" type="hidden" class="text" value="" style="width: 220px;">&nbsp;<font color="#ff0000"></font>
                	</td>
        		</tr>
				<tr>
					<tr height="35">
	        			<td align="left"> ���������̣�</td>
	        			<td align="left" colspan="6">
	        				<input name="userId" type="text" maxlength="10" class="text" style="width: 220px;">&nbsp;<font color="red">*</font>
	        			</td>
	        		</tr>
					<tr height="35">
						<td align="left">����ģʽ��</td>
	        			<td align="left">
	        				<select name="tradeMode">
								<db:select var="row" table="v_syspartition" columns="partitionid,description,trademode" where="validflag=1">
									<option value="${row.trademode}">${row.description}</option>
								</db:select>
	        				</select>&nbsp;<font color="red">*</font>
	        			</td>
<%
						if(securitybyself==1) {
%>
						<td align="left">��֤����ȡ��ʽ��</td>
	        			<td align="left">
	        				<select name="str7" onChange="changeSecurityType(this.value)">
								<option value="1">�ٷֱ�</option>
								<option value="0">����ֵ</option>
	        				</select>&nbsp;<font color="red">*</font>
							<input type="hidden" name="str7_tem" value="<%=securityType%>">
	        			</td>
<%
						}
						if(feebyself==1) {
%>
						<td align="left">��������ȡ��ʽ��</td>
	        			<td align="left">
	        				<select name="str8" onChange="changeFeeType(this.value)">
								<option value="1">�ٷֱ�</option>
								<option value="0">����ֵ</option>
	        				</select>&nbsp;<font color="red">*</font>
							<input type="hidden" name="str8_tem" value="<%=feeType%>">
	        			</td>
	<%
						}
	%>
					</tr>
        			<td colspan="6">
        				<table border="0" cellspacing="0" cellpadding="0" width="90%">
        					<tr height="35">
                				<td align="left">��һ�ιұ����ڣ�</td>
                				<td align="left" colspan="2"> 
                					<MEBS:calendar eltID="firstTime" eltName="firstTime" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="<%=sqlDate%>"/>&nbsp;<font color="#ff0000">*</font>
                				</td>
                				<td align="left">��Ʒ���ࣺ</td>
                				<td align="left" colspan="2">  <!--  ��Ʒ����   <<<<< -->
                					<select name="category" onChange="changeType(this.value)">
<%
									for(int i=0;i<ctvs.length;i++) {
%>
                						<option value="<%=ctvs[i].id%>"><%=ctvs[i].name%></option>
<%
									}
%>
                					</select>&nbsp;<font color="red">*</font>   <!-- ��Ʒ����  >>>>>>>>>>> -->
                				</td>
              				</tr>
			  				<tr height="35" style="display:none;">
                				<td align="left">��ͬ�汾��</td>
                				<td align="left">
                					<select name="templet">
										<db:select var="rowColumn" columns="id,name" table="v_contracttemplet" where="">
											<option value="${rowColumn.id}">${rowColumn.name}</option>
										</db:select>
									</select>&nbsp;<font color="#ff0000">*</font>
                				</td>
              				</tr>
              				<tr height="35">
                				<td align="left">���ļۣ�</td>
                				<td align="left" colspan="2">
                					<input name="beginPrice" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'���ļ�')">&nbsp;<font color="#ff0000">*</font>
                				</td>
                				<td align="left">�Ӽ۷��ȣ�</td>
                				<td align="left" colspan="2">
                					<input name="stepPrice" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'�Ӽ۷���')">&nbsp;<font color="#ff0000">*</font>
                				</td>
              				</tr>
               				<tr height="35">
                				<td align="left">����������λ��</td>
                				<td align="left" colspan="2">
                					<input name="str6" type="text" class="text" style="width: 180px;" readonly=true value="<%=ctvs[0].str1%>">
                					<input name="tradeUnit" type="hidden" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'����������λ')" value="1"><font color="#ff0000">*</font>
                				</td>
                				<td align="left">��ٽ���ʱ�䣺</td>
                				<td align="left" colspan="2">
                  					<MEBS:calendar eltID="str19" eltName="str19" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue=""/>
				  					<input type="hidden" name="str19" class="text" style="width: 180px;" >
                				</td>
              				</tr>
              				<tr height="35">
                				<td align="left">�����ۣ�</td>
                				<td align="left" colspan="2">
                					<input name="alertPrice" maxlength="10" type="text" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'������')">&nbsp;<font color="#ff0000">*</font>
                				</td>
								<td align="left">��֤��(��)��</td>
                				<td align="left">
                					<div id="b_security">
<%
									if(securityType==1) {
%>
                  						<input name="b_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'��֤��')">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
<%
									} else {
%>
                  						<input name="b_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkBail(this,false,false,'��֤��')">&nbsp;<font color="#ff0000">*</font>
<%
									}
%>
				  					</div>
                				</td>
								<td><font color="#ff0000"><div id="b_security_u"><%if(securityType==0){%>Ԫ/<%=ctvs[0].str1%><%}%></div></font></td>
							</tr>
			   				<tr height="35">
                				<td align="left">��֤��(����)��</td>
                				<td align="left">
                					<div id="s_security">
<%
									if(securityType==1) {
%>
										<input name="s_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'��֤��')">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
<%
									} else {
%>
                						<input name="s_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkBail(this,false,false,'��֤��')">&nbsp;<font color="#ff0000">*</font>
<%
									}
%>
									</div>
                				</td>
								<td><font color="#ff0000"><div id="s_security_u"><%if(securityType==0){%>Ԫ/<%=ctvs[0].str1%><%}%></div></font></td>
								<td align="left">������(��)��</td>
                				<td align="left">
                					<div id="b_fee">
<%
									if(feeType==1) {
%>
										<input name="b_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'������ϵ��')" value="0.08">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
<%
									} else {
%>
                						<input name="b_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'������ϵ��')" value="0.0008">&nbsp;<font color="#ff0000">*</font>
<%
									}
%>
									</div>
                				</td>
								<td><font color="#ff0000"><div id="b_fee_u"><%if(feeType==0){%>Ԫ/<%=ctvs[0].str1%><%}%></div></font></td>
              				</tr>
			   				<tr height="35">
                				<td align="left">������(����)��</td>
                				<td align="left">
                					<div id="s_fee">
<%
									if(feeType==1) {
%>
										<input name="s_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'������ϵ��')" value="0.08">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
<%
									} else {
%>
                						<input name="s_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'������ϵ��')" value="0.0008">&nbsp;<font color="#ff0000">*</font>
<%
									}
%>
									</div>
                				</td>
								<td><font color="#ff0000"><div id="s_fee_u"><%if(feeType==0){%>Ԫ/<%=ctvs[0].str1%><%}%></div></font></td>
								<td align="left">���أ�</td>
                				<td align="left" colspan="2">
                					<input name="str13" type="text" class="text" style="width: 180px;">
                				</td> 
              				</tr>
              				<tr>
                				<td align="left">������ݣ�</td>
                				<td align="left" colspan="2">
                  					<input name="str12" type="text" class="text" style="width: 180px;">
                				</td>
                				<td align="left"> ������</td>
                				<td align="left" colspan="2">
                					<input name="str14" type="text" class="text" style="width: 180px;">
                				</td>
              					</tr>
              					<tr height="35">
                					<td align="left">�����ص㣺</td>
                					<td align="left" colspan="2">
                  						<input name="str15" type="text" class="text" style="width: 180px;">
                					</td>
                					<td align="left">��վ���룺</td>
                					<td align="left" colspan="2">
                						<input name="str16" type="text" class="text" style="width: 180px;">
                					</td>
              					</tr>
              					<tr height="35">
			  						<td align="left"></td>
                					<td align="left">   
                						<input name="id_now" type="hidden" class="text" value="<%=ctvs[0].id%>" readonly="true" style="width: 180px;">&nbsp;<font color="#ff0000"></font>
                						<input name="str1" type="hidden" class="text" style="width: 180px;" value="1">
                					</td>
                					<td colspan="2">&nbsp;</td>
              					</tr> 
              					<input type="hidden" name="amount">  <!-- �����Ʒ���� -->
			  					<input type="hidden" name="isnull">  <!-- ��Ų���Ϊ�յ�������   ��Ʒ����   -->
			  					<input type="hidden" name="projName"> <!-- �����Ʒ����������     ��Ʒ���� -->
              				</table>
              			</td>
				</tr>
				<tr>
					<td colspan="6">
						<table id="table1"  border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
							<tr>
								<td align="center" width="15%">ʵ�ʴ洢�������</td>
<%
				    			QueryValue qv2 = new QueryValue();
								qv2.pageIndex = 1;
								qv2.pageSize = 100;  //ÿҳ��ʾ100��
								qv2.filter += " and cpid = "+ctvs[0].id+" order by id ";
								CommodityPropertyVO[] cpvs = to.getCommodityProperty(qv2);
								for(int j=0;j<cpvs.length;j++) {
									if(cpvs[j].isnull==1) {
%>
					    		<td align="center" width="10%"><%=cpvs[j].name%><font color="#ff0000">*</font></td>
<%
									} else {
%>
								<td align="center" width="10%"><%=cpvs[j].name%></td>
<%
									}
								}
%>
				   				<td align="center" width="10%">����</td>
				   				<td align="center" width="6%">ɾ��</td>
				   			</tr>
				   			<tr><td colspan="<%=cpvs.length+3%>">&nbsp;</td></tr>
				   		</table>
                   		<table border="0" cellspacing="0" cellpadding="0" width="100%">
          					<tr height="25">
        						<td width="40%">
        							<div align="center" class="Noprint">
										<input type="button" onclick="addProj()" class="btn" value="��Ӵ���">
									</div>
								</td>
        					</tr>
     					</table>
					</td>
				</tr>
			</table>
		<BR>
		</span>  
	</fieldset>
	<br>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
			<td width="40%">
				<div align="center">
	          	  	<input type="hidden" name="add">
				  	<input type="button" onclick="return frmChk()" class="btn" value="����">&nbsp;&nbsp;
				  	<input name="back" type="button" onclick="goBack();" class="btn" value="����">&nbsp;&nbsp;
	            </div>
	     	</td>
		</tr>
	</table>
<!-- add by yangpei  �������÷��ذ�ť�¼� -->
<script type="text/javascript">
		function goBack(){
     		//window.history.back(-1); 
     		window.location.href("commodityList.jsp?funcflg=true"); 
     	}
</script>
</form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk() {
	if(Trim(frm.userId.value) == "") {
		alert("���������̲���Ϊ�գ�");
		frm.userId.focus();
		return false;
	} else if(Trim(frm.tradeMode.value) == "") {
		alert("��ѡ����ģʽ��");
		frm.tradeMode.focus();
		return false;
	} else if(Trim(frm.str7.value) == "") {
		alert("��֤����ȡ��ʽ����Ϊ�գ�");
		frm.str7.focus();
		return false;
	} else if(Trim(frm.str8.value) == "") {
		alert("��������ȡ��ʽ����Ϊ�գ�");
		frm.str8.focus();
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
	} else if(Trim(frm.str6.value) == "") {
		alert("����������λ����Ϊ�գ�");
		frm.str6.focus();
		return false;
	} else if(Trim(frm.alertPrice.value) == "") {
		alert("�����۲���Ϊ�գ�");
		frm.alertPrice.focus();
		return false;
	} else if(isNaN(Trim(frm.alertPrice.value))) {
		alert("������ֻ��Ϊ���֣�");
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
	//-------------------------------------- ��Ʒ����  ----------------------------->>>>>>>>>>>>>>>>>>
}
//���ƴ��������û���ѡ��
function checkTradeUser(){
	if(document.frm.tradeUserCheck.checked){
		document.frm.tradeUser.value="true";	
	}else{
        //document.frm.tradeUser.value="0";	
	}
} 
//------------------------------------------------------------��Ʒ����                  >>>>>>>>>>>>>>>>>>>>>>>
function changeType(typeid) {
	frm.id_now.value=typeid;
	A2(typeid);
}
var typie = frm.id_now.value;
A2(frm.id_now.value);

function A2(typeid) { //��ӷ�¼�� ѡ������
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
		//setPage(req);
		var type = req.responseXML.getElementsByTagName("parameter");
		//alert(type.length);		
		var len = tableObj.rows.length;
		var Rows=tableObj.rows;//���������Rows
		var newRow = tableObj.insertRow(tableObj.rows.length-1)
		var Cells=newRow.cells;//���������Cells
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
		newCell.align="center"; 
		newCell.innerHTML='<td align="center">ʵ�ʴ洢�������<font color="#ff0000">*</font><input name="place" type="text" value=""  onchange="checkNumber_any(this.value,\''+num2+'\')" id="place" class="text" style="width: 80px;"></td>';
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
				addnew(str,newCell);
			}	
		}
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center">����<font color="#ff0000">*</font><input name="quanlity" type="text" id="quanlity" value="" onchange="checkNumber_cao(this,false,false,false,\''+num+'\')" class="text" style="width: 80px;"></td>';
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center"><span onclick="this.parentNode.parentNode.removeNode(true);" style="cursor:hand;text-decoration:underline;">ɾ��</span></td>';
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
		while(tableObj.rows.length>1) {
			//�������						
			tableObj.deleteRow(0);
		}
		var Rows=tableObj.rows;//���������Rows
		var newRow = tableObj.insertRow(0)
		var Cells=newRow.cells;//���������Cells
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
		newCell.align="center"; 
		newCell.innerHTML='<td align="center">ʵ�ʴ洢�������<font color="#ff0000">*</font><input name="place" type="text" value=""  onchange="checkNumber_any(this.value,\''+'ʵ�ʴ洢�������'+'\')" id="place" class="text" style="width: 80px;"></td>';
		//�������
		for(var j=0;j<type.length;j++) {				
			var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
			newCell.align="center";
			if(type[j].hasChildNodes()){
				//ȡ�ӽڵ�nodlist
				var nodlist=type[j].childNodes;	
				var str=new Array(nodlist.length);  
				for(var i=0;i<nodlist.length;i++) {
					if(nodlist[i].hasChildNodes())
						str[i]=nodlist[i].firstChild.data;
					else str[i]='--';
				}					
				addnew(str,newCell);
				if(str[3]==1) {
					frm.isnull.value=frm.isnull.value+str[0]+","+str[1]+";";  //��Ų���Ϊ�յ�����id��������
				}
				frm.projName.value=frm.projName.value+str[0]+";";
			}	
		}
		frm.projName.value=frm.projName.value+"quanlity";
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center">����<font color="#ff0000">*</font><input name="quanlity" type="text" id="quanlity" value="" onchange="checkNumber_cao(this,false,false,false,\''+'����'+'\')" class="text" style="width: 80px;"></td>';
	}	
}

//���
function addnew(str,newCell) {	
	var id=str[0];	 //��Ʒ����id
	var name=str[1];  //��Ʒ��������
	var type=str[2];   //��Ʒ��������
	var isnull=str[3];  //��Ʒ�����Ƿ����Ϊ��
	var charvartext=str[4];  //��Ʒ����������ѡ��
	//alert("name is "+name);
	if(type==3&&isnull==1) {
		var ops = charvartext.split(",");
		var tem = '<td align="center">'+name+'<font color="#ff0000">*</font><SELECT NAME="'+id+'" id="'+id+'" class="text">';
		for(var i=0;i<ops.length;i++) {
			tem = tem+'<option value="'+ops[i]+'">'+ops[i]+'</option>';
		}
		tem = tem+'</select></td>';
		newCell.innerHTML=tem;
	} else if(type==2&&isnull==1) {
		newCell.innerHTML='<td align="center">'+name+'<font color="#ff0000">*</font><input name="'+id+'" id="'+id+'" onchange="checkNumber_xie(this,false,false,true,\''+name+'\')" value="" type="text" class="text" style="width:80px;">&nbsp<font color="#ff0000">%</font></td>';
	} else if(type==1&&isnull==1) {
		newCell.innerHTML='<td align="center">'+name+'<font color="#ff0000">*</font><input name="'+id+'" id="'+id+'" onchange="checkNumber(this,false,false,\''+name+'\')"  value="" type="text" class="text" style="width: 80px;"></td>';
	} else if(type==0&&isnull==1) {
		newCell.innerHTML='<td align="center">'+name+'<font color="#ff0000">*</font><input name="'+id+'" id="'+id+'" onchange="checkNumber_any(this.value,\''+name+'\')"      value="" type="text" class="text" style="width: 80px;"></td>';
	}

	if(type==3&&isnull==0) {
		var ops = charvartext.split(",");
		var tem = '<td align="center">'+name+'<SELECT NAME="'+id+'" id="'+id+'" class="text">';
		for(var i=0;i<ops.length;i++) {
			tem = tem+'<option value="'+ops[i]+'">'+ops[i]+'</option>';
		}
		tem = tem+'</select></td>';
		newCell.innerHTML=tem;
	} else if(type==2&&isnull==0) {
		newCell.innerHTML='<td align="center">'+name+'<input name="'+id+'" id="'+id+'" onchange="checkNumber_xie(this,false,false,true,\''+name+'\')" value="" type="text" class="text" style="width:80px;">&nbsp<font color="#ff0000">%</font></td>';
	} else if(type==1&&isnull==0) {
		newCell.innerHTML='<td align="center">'+name+'<input name="'+id+'" id="'+id+'" onchange="checkNumber(this,false,false,\''+name+'\')"  value="" type="text" class="text" style="width: 80px;"></td>';
	} else if(type==0&&isnull==0) {
		newCell.innerHTML='<td align="center">'+name+'<input name="'+id+'" id="'+id+'" onchange="checkNumber_any(this.value,\''+name+'\')"      value="" type="text" class="text" style="width: 80px;"></td>';
	}
}

function addnew2(str,newCell) {	
	var id=str[0];	 //��Ʒ����id
	var name=str[1];  //��Ʒ��������
	var type=str[2];   //��Ʒ��������
	var isnull=str[3];  //��Ʒ�����Ƿ����Ϊ��
	var charvartext=str[4];  //��Ʒ����������ѡ��
	if(isnull==1) {
		newCell.innerHTML='<td align="center">'+name+'<input name="'+id+'" id="'+id+'" onchange="checkNumber_any(this.value,\''+name+'\')" value="" type="text" class="text" style="width: 80px;"></td>';
	} else {
		newCell.innerHTML='<td align="center">'+name+'<input name="'+id+'" id="'+id+'" onchange="checkNumber_any(this.value,\''+name+'\')" value="" type="text" class="text" style="width: 80px;"></td>';
	}
}

function checkNumber_any(value,name) {
	var inx = value.indexOf(",");
	if(inx>-1) {
		alert(name+"�в������� , ");
		return false;
	}
	inx = value.indexOf(";");
	if(inx>-1) {
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
		b_feeDiv.innerHTML='<input name="b_fee" type="text" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,\''+text+'\')" value="0.08">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>';
		s_feeDiv.innerHTML="";
		s_feeDiv.innerHTML='<input name="s_fee" type="text" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,\''+text+'\')" value="0.08">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>';
		frm.str8_tem.value=1;
		document.getElementById("b_fee_u").innerHTML='';
		document.getElementById("s_fee_u").innerHTML='';
	} else {
		b_feeDiv.innerHTML="";
		b_feeDiv.innerHTML='<input name="b_fee" type="text" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,\''+text+'\')" value="0.0008">&nbsp;<font color="#ff0000">*</font>';
		s_feeDiv.innerHTML="";
		s_feeDiv.innerHTML='<input name="s_fee" type="text" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,\''+text+'\')" value="0.0008">&nbsp;<font color="#ff0000">*</font>';
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
//-->
</SCRIPT>
<%
} catch(Exception e) {
	e.printStackTrace();
}
%>