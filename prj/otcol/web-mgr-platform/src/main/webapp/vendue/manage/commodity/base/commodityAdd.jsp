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
 	qv.pageSize = 100;  //每页显示10条
 	qv.filter += " order by id ";
 	CommodityTypeVO[] ctvs = to.getCommodityType(qv);
 	if(ctvs.length==0) {
%>
	<SCRIPT LANGUAGE="JavaScript">
		alert("请添加商品种类");	
		window.location = "../../system/syscontrol/commodityType.jsp";
	</SCRIPT>
<%
	}
	//读取配置
	Configuration cfn = new Configuration();
	//保证金是否可以自己设定收取方式 0 不可以 1 可以  可以时保证金收取方式配置项将失效
	int securitybyself = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("SecurityBySelf")); 
	//手续费是否可以自己设定收取方式 0 不可以 1 可以  可以时手续费收取方式配置项将失效
	int feebyself = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("FeeBySelf"));
	int securityType = 1;
	if(securitybyself==0) {
		securityType = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("SecurityType")); //保证金收取方式  0 为绝对值 1 为百分比
	} else {
		if(request.getParameter("str7")!=null) {
			securityType = Integer.valueOf(request.getParameter("str7"));
		}
	}
	int feeType = 1;
	if(feebyself==0) {
		feeType = Integer.parseInt(cfn.getSection("MEBS.TradeParams").getProperty("FeeType")); //手续费收取方式  0 为绝对值 1 为百分比
	} else {
		if(request.getParameter("str8")!=null) {
			feeType =  Integer.valueOf(request.getParameter("str8"));
		}
	}
%>
<!--取得参数-->
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
		<db:select var="row" table="dual" columns="SP_V_COMMODITY_CODE.nextval as seqID_code">  <!-- xieying 自动产生标的号 -->
			<c:set var="com_code" value="${row.seqID_code}"/>
		</db:select> 
<%
  		String firstTime=request.getParameter("firstTime");
  		java.sql.Date a=null;
  		//第一次挂牌时间参数转换成日期函数
  		java.sql.Date convertDate=a.valueOf(firstTime);
  		int tradeMode=new Integer(request.getParameter("tradeMode")).intValue();//交易模式
  		double beginPrice=new Double(request.getParameter("beginPrice")).doubleValue();//起报价
  		double alertPrice=new Double(request.getParameter("alertPrice")).doubleValue();//报警价
%>
		<c:set var="convertDate" value="<%=convertDate%>"/>
		<c:set var="sqlDate" value="<%=sqlDate%>"/>
<%
		//---------------------   商品属性   <<<<<<<<<<<<<<<<<<
		String projName = request.getParameter("projName");
		String[] pn = projName.split(";");
		int len = request.getParameterValues(pn[0]).length;
		String[][] str3_tem = new String[pn.length][len];
		for(int i=0;i<pn.length;i++) {
			str3_tem[i] = request.getParameterValues(pn[i]);
	   	}
	   	String str5 = request.getParameter("id_now");
	   	String str6 = request.getParameter("str6"); //数量单位
	   	String stepPrice = Tool.fmtDouble2(toDouble(request.getParameter("stepPrice")));
	   	
	   	DBBean bean1=null;   //xieying
	   	ResultSet rs1=null;  //xieying
	   	String qyName = "";
	   	// ----------------------------  商品属性   >>>>>>>>>>>>>>>>>>>
	   	String userId=request.getParameter("userId");//所属交易商
	   	StringBuffer sql=new StringBuffer();   //xieying
		//sql.append("select "+COLS_USER_O+" from v_tradeuserext t1 where t1.usercode='"+userId+"'");  //xieying
		sql.append("select name from m_firm  where firmId='"+userId+"'");
		//System.out.println(sql.toString());
		bean1=new DBBean(JNDI);  //xieying
		rs1=bean1.executeQuery(sql.toString());  //xieying
		if(rs1.next()){
			qyName = delNull(rs1.getString("name"));  //xieying  交易所属商企业名称
		}
	   	boolean sellerFlag=false;//判断所属交易商是否存在
	   	//判断收购库点,品种,数量,是否已经存在,如果存在提示给用户已有相同的信息
       	String repeCommFlag="";
       	String amount=request.getParameter("amount");
       	String str1=request.getParameter("str1");
       	String str2=qyName;//request.getParameter("str2");
  	   	String str3="";//存储多对关系字段
  	   	String str4="";//商品种类名称
	   	//--------------  商品属性 <<<<<<<<<<<<<<<<<<<<<<<<
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
  		//判断标的号与第一次挂牌时间是否重复
  		boolean addFlag=true;
  		bean=new DBBean(JNDI);  //xieying 自动产生标的号 ，取消判断输入的标的号
  		rs=bean.executeQuery("select t1.id from v_commodity t1,v_commext t2 where t1.id=t2.commid and t1.code='"+"${com_code}"+"' and t1.firsttime=to_date('"+firstTime+"','yyyy-mm-dd')");
  		if(rs.next()){
    		addFlag=false;
  		}
  		rs.close();
  		if(addFlag==true){
  			//判断输入的所属交易商是否存在
  			rs=bean.executeQuery("select usercode from v_tradeuser where usercode='"+userId+"'");
  			if(rs.next()){
      			sellerFlag=true;
  			}
  			rs.close();
  			bean.closeStmt();
  			//输入的所属交易商存在情况
			if(sellerFlag==true){
  				boolean priceFlag=true;//判断在不同交易模式下起报价与报警价情况
  				String priceMess=null;
  				if(tradeMode==0&&alertPrice<beginPrice){
	  				priceFlag=false;
      				priceMess="交易模式为竞买时,报警价必须大于起报价";
  				}
  				if(tradeMode==1&&alertPrice>beginPrice){
	  				priceFlag=false;
      				priceMess="交易模式为竞卖时,报警价必须小于起报价";
  				}
  				if(tradeMode==2&&alertPrice>beginPrice){
	  				priceFlag=false;
      				priceMess="交易模式为招标时,报警价必须小于起报价";
  				}
  				if(priceFlag==true){
  					int i=0;
  					rs=bean.executeQuery("select t1.id,t1.code,t1.createtime from v_commodity t1,v_commext t2 where t1.id=t2.commid and t1.amount="+amount+" and t2.str2='"+str2+"' and t2.str1='"+str1+"'");
 					 while(rs.next()){
						if(i==0){
      						repeCommFlag="只是存在相同收购库点、品种、数量,可能重复录入商品,请仔细查阅。可能相同的商品如下:\\r\\n";
	  						repeCommFlag+="标的号:"+rs.getString("code")+",创建时间:"+rs.getString("createtime").substring(0,10)+"\\r\\n";
						}else{
	  						repeCommFlag+="标的号:"+rs.getString("code")+",创建时间:"+rs.getString("createtime").substring(0,10)+"\\r\\n";
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
	alert("商品添加成功！"+"<%=repeCommFlag%>");
	gotoUrl("commodityList.jsp?funcflg=true");
</SCRIPT>
<%
    			} else {
	     			alert("添加商品失败,"+priceMess,out);
    			}
    		} else {
       			alert("输入的所属交易商不存在,添加商品失败!",out);
    		}
		} else {
%>
<SCRIPT LANGUAGE="JavaScript">
	alert("存在相同的标的号与第一次挂牌时间,数据没有保存,请重新录入!");
	gotoUrl("commodityAdd.jsp");
</SCRIPT>
<%
		}
  		//关闭数据源
  		bean.close();
 	} catch(Exception e) {
  		e.printStackTrace();
    	errOpt();
%>
<SCRIPT LANGUAGE="JavaScript">
	alert("操作异常,操作失败!");
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
	//异常处理
   	String exceError=pageContext.getAttribute("exceError").toString();
	log(request,exceError);
	hintError(out);
%>
	</c:if>
</c:if>


<head>
	<title>添加商品</title>
</head>
<body>
<form name=frm id=frm method="post">
	<fieldset width="80%">
		<legend>添加商品</legend>
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
	        			<td align="left"> 所属交易商：</td>
	        			<td align="left" colspan="6">
	        				<input name="userId" type="text" maxlength="10" class="text" style="width: 220px;">&nbsp;<font color="red">*</font>
	        			</td>
	        		</tr>
					<tr height="35">
						<td align="left">交易模式：</td>
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
						<td align="left">保证金收取方式：</td>
	        			<td align="left">
	        				<select name="str7" onChange="changeSecurityType(this.value)">
								<option value="1">百分比</option>
								<option value="0">绝对值</option>
	        				</select>&nbsp;<font color="red">*</font>
							<input type="hidden" name="str7_tem" value="<%=securityType%>">
	        			</td>
<%
						}
						if(feebyself==1) {
%>
						<td align="left">手续费收取方式：</td>
	        			<td align="left">
	        				<select name="str8" onChange="changeFeeType(this.value)">
								<option value="1">百分比</option>
								<option value="0">绝对值</option>
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
                				<td align="left">第一次挂标日期：</td>
                				<td align="left" colspan="2"> 
                					<MEBS:calendar eltID="firstTime" eltName="firstTime" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue="<%=sqlDate%>"/>&nbsp;<font color="#ff0000">*</font>
                				</td>
                				<td align="left">商品分类：</td>
                				<td align="left" colspan="2">  <!--  商品属性   <<<<< -->
                					<select name="category" onChange="changeType(this.value)">
<%
									for(int i=0;i<ctvs.length;i++) {
%>
                						<option value="<%=ctvs[i].id%>"><%=ctvs[i].name%></option>
<%
									}
%>
                					</select>&nbsp;<font color="red">*</font>   <!-- 商品属性  >>>>>>>>>>> -->
                				</td>
              				</tr>
			  				<tr height="35" style="display:none;">
                				<td align="left">合同版本：</td>
                				<td align="left">
                					<select name="templet">
										<db:select var="rowColumn" columns="id,name" table="v_contracttemplet" where="">
											<option value="${rowColumn.id}">${rowColumn.name}</option>
										</db:select>
									</select>&nbsp;<font color="#ff0000">*</font>
                				</td>
              				</tr>
              				<tr height="35">
                				<td align="left">起拍价：</td>
                				<td align="left" colspan="2">
                					<input name="beginPrice" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'起拍价')">&nbsp;<font color="#ff0000">*</font>
                				</td>
                				<td align="left">加价幅度：</td>
                				<td align="left" colspan="2">
                					<input name="stepPrice" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'加价幅度')">&nbsp;<font color="#ff0000">*</font>
                				</td>
              				</tr>
               				<tr height="35">
                				<td align="left">交易数量单位：</td>
                				<td align="left" colspan="2">
                					<input name="str6" type="text" class="text" style="width: 180px;" readonly=true value="<%=ctvs[0].str1%>">
                					<input name="tradeUnit" type="hidden" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'交易数量单位')" value="1"><font color="#ff0000">*</font>
                				</td>
                				<td align="left">最迟交货时间：</td>
                				<td align="left" colspan="2">
                  					<MEBS:calendar eltID="str19" eltName="str19" eltCSS="date" eltStyle="width:80px" eltImgPath="${CONTEXT}/common/skinstyle/default/common/commoncss/images/" eltValue=""/>
				  					<input type="hidden" name="str19" class="text" style="width: 180px;" >
                				</td>
              				</tr>
              				<tr height="35">
                				<td align="left">报警价：</td>
                				<td align="left" colspan="2">
                					<input name="alertPrice" maxlength="10" type="text" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'报警价')">&nbsp;<font color="#ff0000">*</font>
                				</td>
								<td align="left">保证金(买方)：</td>
                				<td align="left">
                					<div id="b_security">
<%
									if(securityType==1) {
%>
                  						<input name="b_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'保证金')">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
<%
									} else {
%>
                  						<input name="b_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkBail(this,false,false,'保证金')">&nbsp;<font color="#ff0000">*</font>
<%
									}
%>
				  					</div>
                				</td>
								<td><font color="#ff0000"><div id="b_security_u"><%if(securityType==0){%>元/<%=ctvs[0].str1%><%}%></div></font></td>
							</tr>
			   				<tr height="35">
                				<td align="left">保证金(卖方)：</td>
                				<td align="left">
                					<div id="s_security">
<%
									if(securityType==1) {
%>
										<input name="s_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'保证金')">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
<%
									} else {
%>
                						<input name="s_security" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkBail(this,false,false,'保证金')">&nbsp;<font color="#ff0000">*</font>
<%
									}
%>
									</div>
                				</td>
								<td><font color="#ff0000"><div id="s_security_u"><%if(securityType==0){%>元/<%=ctvs[0].str1%><%}%></div></font></td>
								<td align="left">手续费(买方)：</td>
                				<td align="left">
                					<div id="b_fee">
<%
									if(feeType==1) {
%>
										<input name="b_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'手续费系数')" value="0.08">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
<%
									} else {
%>
                						<input name="b_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'手续费系数')" value="0.0008">&nbsp;<font color="#ff0000">*</font>
<%
									}
%>
									</div>
                				</td>
								<td><font color="#ff0000"><div id="b_fee_u"><%if(feeType==0){%>元/<%=ctvs[0].str1%><%}%></div></font></td>
              				</tr>
			   				<tr height="35">
                				<td align="left">手续费(卖方)：</td>
                				<td align="left">
                					<div id="s_fee">
<%
									if(feeType==1) {
%>
										<input name="s_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber_xie(this,false,false,true,'手续费系数')" value="0.08">&nbsp<font color="#ff0000">%</font>&nbsp;<font color="#ff0000">*</font>
<%
									} else {
%>
                						<input name="s_fee" type="text" maxlength="10" class="text" style="width: 180px;" onchange="checkNumber(this,false,false,'手续费系数')" value="0.0008">&nbsp;<font color="#ff0000">*</font>
<%
									}
%>
									</div>
                				</td>
								<td><font color="#ff0000"><div id="s_fee_u"><%if(feeType==0){%>元/<%=ctvs[0].str1%><%}%></div></font></td>
								<td align="left">产地：</td>
                				<td align="left" colspan="2">
                					<input name="str13" type="text" class="text" style="width: 180px;">
                				</td> 
              				</tr>
              				<tr>
                				<td align="left">生产年份：</td>
                				<td align="left" colspan="2">
                  					<input name="str12" type="text" class="text" style="width: 180px;">
                				</td>
                				<td align="left"> 质量：</td>
                				<td align="left" colspan="2">
                					<input name="str14" type="text" class="text" style="width: 180px;">
                				</td>
              					</tr>
              					<tr height="35">
                					<td align="left">交货地点：</td>
                					<td align="left" colspan="2">
                  						<input name="str15" type="text" class="text" style="width: 180px;">
                					</td>
                					<td align="left">火车站距离：</td>
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
              					<input type="hidden" name="amount">  <!-- 存放商品数量 -->
			  					<input type="hidden" name="isnull">  <!-- 存放不可为空的属性名   商品属性   -->
			  					<input type="hidden" name="projName"> <!-- 存放商品所有属性名     商品属性 -->
              				</table>
              			</td>
				</tr>
				<tr>
					<td colspan="6">
						<table id="table1"  border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
							<tr>
								<td align="center" width="15%">实际存储库点名称</td>
<%
				    			QueryValue qv2 = new QueryValue();
								qv2.pageIndex = 1;
								qv2.pageSize = 100;  //每页显示100条
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
				   				<td align="center" width="10%">数量</td>
				   				<td align="center" width="6%">删除</td>
				   			</tr>
				   			<tr><td colspan="<%=cpvs.length+3%>">&nbsp;</td></tr>
				   		</table>
                   		<table border="0" cellspacing="0" cellpadding="0" width="100%">
          					<tr height="25">
        						<td width="40%">
        							<div align="center" class="Noprint">
										<input type="button" onclick="addProj()" class="btn" value="添加储库">
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
				  	<input type="button" onclick="return frmChk()" class="btn" value="保存">&nbsp;&nbsp;
				  	<input name="back" type="button" onclick="goBack();" class="btn" value="返回">&nbsp;&nbsp;
	            </div>
	     	</td>
		</tr>
	</table>
<!-- add by yangpei  用来设置返回按钮事件 -->
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
		alert("所属交易商不能为空！");
		frm.userId.focus();
		return false;
	} else if(Trim(frm.tradeMode.value) == "") {
		alert("请选择交易模式！");
		frm.tradeMode.focus();
		return false;
	} else if(Trim(frm.str7.value) == "") {
		alert("保证金收取方式不能为空！");
		frm.str7.focus();
		return false;
	} else if(Trim(frm.str8.value) == "") {
		alert("手续费收取方式不能为空！");
		frm.str8.focus();
		return false;
	} else if(Trim(frm.firstTime.value) == "") {
		alert("第一次挂标日期不能为空！");
		frm.firstTime.focus();
		return false;
	} else if(Trim(frm.category.value) == "") {
		alert("商品分类不能为空！");
		frm.category.focus();
		return false;
	} else if(Trim(frm.beginPrice.value) == "") {
		alert("起拍价不能为空！");
		frm.beginPrice.focus();
		return false;
	} else if(isNaN(Trim(frm.beginPrice.value))) {
		alert("起拍价只能为数字！");
		frm.beginPrice.focus();
		return false;
	} else if(parseFloat(Trim(frm.beginPrice.value))<0) {
		alert("起拍价必须大于0！");
		frm.beginPrice.focus();
		return false;
	} else if(Trim(frm.stepPrice.value) == "") {
		alert("加价幅度不能为空！");
		frm.stepPrice.focus();
		return false;
	} else if(isNaN(Trim(frm.stepPrice.value))) {
		alert("加价幅度必须为数字！");
		frm.stepPrice.focus();
		return false;
	} else if(parseFloat(Trim(frm.stepPrice.value))<=0) {
		alert("加价幅度必须大于0！");
		frm.stepPrice.focus();
		return false;
	} else if(Trim(frm.str6.value) == "") {
		alert("交易数量单位不能为空！");
		frm.str6.focus();
		return false;
	} else if(Trim(frm.alertPrice.value) == "") {
		alert("报警价不能为空！");
		frm.alertPrice.focus();
		return false;
	} else if(isNaN(Trim(frm.alertPrice.value))) {
		alert("报警价只能为数字！");
		frm.alertPrice.focus();
		return false;
	} else if(parseFloat(Trim(frm.alertPrice.value))<0) {
		alert("报警价必须大于0！");
		frm.alertPrice.focus();
		return false;
	} else if(Trim(frm.b_security.value) == "") {
		alert("保证金(买)系数不能为空！");
		frm.b_security.focus();
		return false;
	} else if(isNaN(Trim(frm.b_security.value))) {
		alert("保证金(买)系数必须为数字！");
		frm.b_security.focus();
		return false;
	} else if(parseFloat(Trim(frm.b_security.value))<0) {
		alert("保证金(买)系数必须大于0！");
		frm.b_security.focus();
		return false;
	} else if(Trim(frm.s_security.value) == "") {
		alert("保证金(卖)系数不能为空！");
		frm.s_security.focus();
		return false;
	} else if(isNaN(Trim(frm.s_security.value))) {
		alert("保证金(卖)系数必须为数字！");
		frm.s_security.focus();
		return false;
	} else if(parseFloat(Trim(frm.s_security.value))<0) {
		alert("保证金(卖)系数必须大于0！");
		frm.s_security.focus();
		return false;
	} else if(Trim(frm.b_fee.value) == "") {
		alert("手续费(买)系数不能为空！");
		frm.b_fee.focus();
		return false;
	} else if(isNaN(Trim(frm.b_fee.value))) {
		alert("手续费(买)系数必须为数字！");
		frm.b_fee.focus();
		return false;
	} else if(parseFloat(Trim(frm.b_fee.value))<0) {
		alert("手续费(买)系数必须大于0！");
		frm.b_fee.focus();
		return false;
	} else if(Trim(frm.s_fee.value) == "") {
		alert("手续费(卖)系数不能为空！");
		frm.s_fee.focus();
		return false;
	} else if(isNaN(Trim(frm.s_fee.value))) {
		alert("手续费(卖)系数必须为数字！");
		frm.s_fee.focus();
		return false;
	} else if(parseFloat(Trim(frm.s_fee.value))<0) {
		alert("手续费(卖)系数必须大于0！");
		frm.s_fee.focus();
		return false;
	} else if(Trim(frm.tradeMode.value)==0&&parseFloat(Trim(frm.alertPrice.value))<parseFloat(Trim(frm.beginPrice.value))) {
		alert("交易模式为竞买时,报警价必须大于起报价!");
		frm.alertPrice.focus();
		return false;
	} else if(Trim(frm.tradeMode.value)==1&&parseFloat(Trim(frm.alertPrice.value))>parseFloat(Trim(frm.beginPrice.value))) {
		alert("交易模式为竞卖时,报警价必须小于起报价!");
		frm.alertPrice.focus();
		return false;
	} else if(Trim(frm.tradeMode.value)==2&&parseFloat(Trim(frm.alertPrice.value))>parseFloat(Trim(frm.beginPrice.value))) {
		alert("交易模式为招标时,报警价必须小于起报价!");
		frm.alertPrice.focus();
		return false;
	}
	//--------------------------   商品属性 ------------------------------<<<<<<<<<<<<<<
	frm.amount.value=0;  //置数量为0
	var isnull_Name_all = frm.isnull.value.split(";");
	for(var i=0;i<isnull_Name_all.length-1;i++) {
		var isnull_Name = isnull_Name_all[i].split(",");
		if(!document.getElementById(isnull_Name[0])) {
			alert(isnull_Name[1]+"不能为空");
			return false
		} else {
			var tem = document.getElementsByName(isnull_Name[0]);
			if(tem.length>0) {
				for(var j=0;j<tem.length;j++) {
					if(Trim(tem[j].value)=="") {
						alert(isnull_Name[1]+"不能为空");
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
	//-------------------------------------- 商品属性  ----------------------------->>>>>>>>>>>>>>>>>>
}
//控制创建交易用户复选框
function checkTradeUser(){
	if(document.frm.tradeUserCheck.checked){
		document.frm.tradeUser.value="true";	
	}else{
        //document.frm.tradeUser.value="0";	
	}
} 
//------------------------------------------------------------商品属性                  >>>>>>>>>>>>>>>>>>>>>>>
function changeType(typeid) {
	frm.id_now.value=typeid;
	A2(typeid);
}
var typie = frm.id_now.value;
A2(frm.id_now.value);

function A2(typeid) { //添加分录名 选择种类
	frm.isnull.value="";  //清空不可为空的属性id和属性名
	frm.projName.value="";  //清空商品属性名
	frm.isnull.value=frm.isnull.value+"place,实际存储库点名称;quanlity,数量;"
	frm.projName.value=frm.projName.value+"place;"
	var url = "commodityProperty_xml.jsp";
	if (window.XMLHttpRequest&&!(window.ActiveXObject)) { 	
		req = new XMLHttpRequest(); 
	} else if (window.ActiveXObject) {	
		req = new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	if(req) { 
		req.open("GET",url+"?cpid="+typeid, true); 
		req.onreadystatechange = complete2; //指定当readyState属性改变时的事件处理句柄
		req.send(null); 
	} 
}

function addProj() {
	A1(frm.id_now.value);
}
//Ajax 取数据
function A1(typeid) { //添加分录
	var url = "commodityProperty_xml.jsp";
	if (window.XMLHttpRequest&&!(window.ActiveXObject)) { 	
		req = new XMLHttpRequest(); 
	} else if (window.ActiveXObject) {	
		req = new ActiveXObject("Microsoft.XMLHTTP"); 
	} 
	if(req) { 
		req.open("GET",url+"?cpid="+typeid, true); 
		req.onreadystatechange = complete; //指定当readyState属性改变时的事件处理句柄
		req.send(null);
	} 
}
var selectID="";
/*分析返回的XML文档*/
function complete() { //添加分录
	var tableObj=table1;
	var num = "数量";
	var num2 = "实际存储库点名称";
	if (req.readyState==4 && req.status == 200) { 
		//setPage(req);
		var type = req.responseXML.getElementsByTagName("parameter");
		//alert(type.length);		
		var len = tableObj.rows.length;
		var Rows=tableObj.rows;//类似数组的Rows
		var newRow = tableObj.insertRow(tableObj.rows.length-1)
		var Cells=newRow.cells;//类似数组的Cells
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
		newCell.align="center"; 
		newCell.innerHTML='<td align="center">实际存储库点名称<font color="#ff0000">*</font><input name="place" type="text" value=""  onchange="checkNumber_any(this.value,\''+num2+'\')" id="place" class="text" style="width: 80px;"></td>';
		//添加数据
		for(var j=0;j<type.length;j++) {
			var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
			newCell.align="center";
			if(type[j].hasChildNodes()) {
				//取子节点nodlist
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
		newCell.innerHTML='<td align="center">数量<font color="#ff0000">*</font><input name="quanlity" type="text" id="quanlity" value="" onchange="checkNumber_cao(this,false,false,false,\''+num+'\')" class="text" style="width: 80px;"></td>';
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center"><span onclick="this.parentNode.parentNode.removeNode(true);" style="cursor:hand;text-decoration:underline;">删除</span></td>';
	}
}

function complete2() { //添加分录名 选择种类
	var tableObj=table1;
	if (req.readyState==4 && req.status == 200) { 
		var type_dw = req.responseXML.getElementsByTagName("dw");
		frm.str6.value=type_dw[0].firstChild.data
		if(<%=securitybyself%>==1) {
			if(frm.str7_tem.value==0) {
				document.getElementById("b_security_u").innerHTML='元/'+type_dw[0].firstChild.data;
				document.getElementById("s_security_u").innerHTML='元/'+type_dw[0].firstChild.data;
			}
		}
		if(<%=feebyself%>==1) {
			if(frm.str8_tem.value==0) {
				document.getElementById("b_fee_u").innerHTML='元/'+type_dw[0].firstChild.data;
				document.getElementById("s_fee_u").innerHTML='元/'+type_dw[0].firstChild.data;
			}
		}
		var type = req.responseXML.getElementsByTagName("parameter");
		var len = tableObj.rows.length;			
		//清除数据
		while(tableObj.rows.length>1) {
			//清除数据						
			tableObj.deleteRow(0);
		}
		var Rows=tableObj.rows;//类似数组的Rows
		var newRow = tableObj.insertRow(0)
		var Cells=newRow.cells;//类似数组的Cells
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); 
		newCell.align="center"; 
		newCell.innerHTML='<td align="center">实际存储库点名称<font color="#ff0000">*</font><input name="place" type="text" value=""  onchange="checkNumber_any(this.value,\''+'实际存储库点名称'+'\')" id="place" class="text" style="width: 80px;"></td>';
		//添加数据
		for(var j=0;j<type.length;j++) {				
			var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
			newCell.align="center";
			if(type[j].hasChildNodes()){
				//取子节点nodlist
				var nodlist=type[j].childNodes;	
				var str=new Array(nodlist.length);  
				for(var i=0;i<nodlist.length;i++) {
					if(nodlist[i].hasChildNodes())
						str[i]=nodlist[i].firstChild.data;
					else str[i]='--';
				}					
				addnew(str,newCell);
				if(str[3]==1) {
					frm.isnull.value=frm.isnull.value+str[0]+","+str[1]+";";  //存放不可为空的属性id和属性名
				}
				frm.projName.value=frm.projName.value+str[0]+";";
			}	
		}
		frm.projName.value=frm.projName.value+"quanlity";
		var newCell=Rows(newRow.rowIndex).insertCell(Cells.length);
		newCell.align="center";
		newCell.innerHTML='<td align="center">数量<font color="#ff0000">*</font><input name="quanlity" type="text" id="quanlity" value="" onchange="checkNumber_cao(this,false,false,false,\''+'数量'+'\')" class="text" style="width: 80px;"></td>';
	}	
}

//添加
function addnew(str,newCell) {	
	var id=str[0];	 //商品属性id
	var name=str[1];  //商品属性名称
	var type=str[2];   //商品属性种类
	var isnull=str[3];  //商品属性是否可以为空
	var charvartext=str[4];  //商品属性下拉框选项
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
	var id=str[0];	 //商品属性id
	var name=str[1];  //商品属性名称
	var type=str[2];   //商品属性种类
	var isnull=str[3];  //商品属性是否可以为空
	var charvartext=str[4];  //商品属性下拉框选项
	if(isnull==1) {
		newCell.innerHTML='<td align="center">'+name+'<input name="'+id+'" id="'+id+'" onchange="checkNumber_any(this.value,\''+name+'\')" value="" type="text" class="text" style="width: 80px;"></td>';
	} else {
		newCell.innerHTML='<td align="center">'+name+'<input name="'+id+'" id="'+id+'" onchange="checkNumber_any(this.value,\''+name+'\')" value="" type="text" class="text" style="width: 80px;"></td>';
	}
}

function checkNumber_any(value,name) {
	var inx = value.indexOf(",");
	if(inx>-1) {
		alert(name+"中不能输入 , ");
		return false;
	}
	inx = value.indexOf(";");
	if(inx>-1) {
		alert(name+"中不能输入 ; ");
		return false;
	}
	return true;
}
//---------------------------------------------------------商品属性           <<<<<<<<<<<<<<<<<<<<<<<<<<<,,,,,,
function changeSecurityType(typeid) {
	var b_securityDiv = document.getElementById("b_security");
	var s_securityDiv = document.getElementById("s_security");
	var text = "保证金";
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
		document.getElementById("b_security_u").innerHTML='元/'+frm.str6.value;
		document.getElementById("s_security_u").innerHTML='元/'+frm.str6.value;
	}
}

function changeFeeType(typeid) {
	var b_feeDiv = document.getElementById("b_fee");
	var s_feeDiv = document.getElementById("s_fee");
	var text = "手续费系数";
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
		document.getElementById("b_fee_u").innerHTML='元/'+frm.str6.value;
		document.getElementById("s_fee_u").innerHTML='元/'+frm.str6.value;
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
		alert("请先填写起拍价和报警价");
		obj.value="";
		return false;
	} else if(checkNumber(obj, isNotNull, isNotZero, msg)) {
		if(parseFloat(obj.value)>parseFloat(minMoney)) {
			alert("保证金不能大于商品的最低单价");
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