<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%> 
<%
String orderField = request.getParameter("orderField");
String orderType= request.getParameter("orderDesc");
if (orderField == null || orderField.equals("")) {
	orderField = "code";
}
if (orderType == null || orderType.equals("")) {
	orderType = "false";
}
String order = "";
if (orderType.equals("false")) {
	order = "asc";
}
if (orderType.equals("true")){
	order = "desc";
}
String a = orderField+" "+order;
request.setAttribute("a", a);
%>
<!--����-->
<%
 if(request.getParameter("opt")!=null){
				
 if("delete".equals(request.getParameter("opt"))){ 
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs=null;
    ResultSet rsExt=null;
	long commodityId=-1;//����������
	int result=-1;//���ù黹�������ʽ𷵻ؽ��
    try{
	    Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource)envContext.lookup(JNDI);
        conn = ds.getConnection();
        conn.setAutoCommit(false);
	    boolean globalFlag=false;//�жϱ����Ƿ�����ɾ��һ����Ʒ,�źü���־
        int operCnt=0;//�жϲ����ɽ���Ʒ������
        String operCode="";//ֻ����������Ʒ��¼�ı�ĺ�
		String[] ids=request.getParameterValues("ck");//��ĺ�
		boolean tradingFlag=false;//�ж���Ʒ���������Ƿ������ڽ��׵Ľ��׽�
		String returnInfo="";//��¼û��ɾ������Ʒ���
		String partitionID=request.getParameter("partitionID");
		String kernelClassName = null;
		StringBuffer sql=null;
		DeliveryAction delivery=(DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();
		//-------------------------------------------------  xieying
					sql=new StringBuffer();
					sql.append("select partitionid, engineclass, quotationclass, submitactionclass, validflag, description from v_syspartition where partitionid="+partitionID);
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					if(rs.next()){
					    kernelClassName = rs.getString("engineclass");
					}
					rs.close();
					//-------------------------------------------------
		if(ids!=null){
		    for(int i=0;i<ids.length;i++){
			    tradingFlag=false;
                sql=new StringBuffer();
				commodityId=-1;
				result=-1;
				sql.append("select bargainflag,section,commodityid from v_curcommodity where ");
				sql.append("code='"+ids[i]+"'");
                ps=conn.prepareStatement(sql.toString());
				rs=ps.executeQuery();
				if(rs.next()){
					commodityId=rs.getLong("commodityid");
				    sql=new StringBuffer();
					sql.append("select * from v_syscurstatus where status=2 and tradepartition=");
					sql.append(""+partitionID+" and section="+rs.getString("section")+"");
					ps=conn.prepareStatement(sql.toString());
					rsExt=ps.executeQuery();
					if(rsExt.next()){
					    tradingFlag=true;
					}
					rsExt.close();
					ps.close();
					
					if(rs.getInt("bargainflag")==0&&tradingFlag==false)
					{
						KernelEngine dao = (KernelEngine)Class.forName(kernelClassName).newInstance();
						TradeDAO dao2 = TradeDAOFactory.getDAO();
						dao.setTradeDAO(dao2);
						dao.setPartition(Integer.parseInt(partitionID));
						//result=delivery.delCommodityCharge(commodityId,Integer.parseInt(partitionID),conn);
						result = dao.delCommodityCharge(commodityId,conn);
                        if(result==0){
						sql=new StringBuffer();
						sql.append("delete from v_curcommodity where code='"+ids[i]+"'");
						ps=conn.prepareStatement(sql.toString());
						ps.executeUpdate();
						ps.close();
						operCnt+=1;
                        operCode=ids[i];
                        globalFlag=true;
						}else{
						    returnInfo+=ids[i]+",";
						}
					}else{
					    returnInfo+=ids[i]+",";
					}
				}
			}
		}
		if(globalFlag==true){
            
		}
        
				//ˢ���ڴ�
				System.setProperty("java.rmi.server.hostname", host);
        KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":" +port+ "/"+REMOTECLASS+""+partitionID);
        dao.loadCommodity();
		//�ر�����Դ����
	    conn.commit();
		if(!"".equals(returnInfo)){//������һ����Ʒû��ɾ��
		    alert(returnInfo+"��Ʒ���ǳɽ�״̬�����������л��߷��ؽ������ʽ�ʧ��,����ɾ��!",out);
		}else{
		    alert("��Ʒɾ���ɹ�!",out);
		}
   }
   catch(Exception e)
   {
	   conn.rollback();
	   e.printStackTrace();
       errOpt();
	   alert("���������������Ա��ϵ!",out);
   }finally{
       conn.setAutoCommit(true);
	   if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
	   if(rsExt!=null){try{rsExt.close();}catch(Exception ex){}rsExt=null;}
       if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
       try{
          conn.close();
       }catch (Exception e){}
       conn = null;
   }
}
    //ˢ���ڴ�
    if("refreshEngine".equals(request.getParameter("opt"))){
		try{
	        String partitionID=request.getParameter("partitionID");
	        KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":" +port+ "/"+REMOTECLASS+""+partitionID);
            dao.loadCommodity();
	       
		   alert("���µ�ǰ���׽ڳɹ�!",out);
        }
		catch(Exception e)
        {
	        e.printStackTrace();
            errOpt();
	        alert("���������������Ա��ϵ!",out);
        }
    }
}
%>
<!--��������-->
<c:set var="sqlFilter" value=""/>
<!--��ҳ��������-->
<c:choose> 
 <c:when test="${empty param.pageIndex}"> 
   <c:set var="pageIndex" value="1"/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="pageIndex" value="${param.pageIndex}"/> 
 </c:otherwise> 
</c:choose>
<c:choose>
  <c:when test="${empty param.pageSize}">
  	 <c:set var="pageSize" value="${PAGESIZE}"/>
  </c:when>
  <c:otherwise>
  	 <c:set var="pageSize" value="${param.pageSize}"/>
  </c:otherwise>
</c:choose>
  <head>
	<title>��ǰ������Ʒ��ѯ</title>
</head>
<body onload="init();">
<form name=frm action="" method="post">
<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
	<!--Ĭ��ѡ�а��-->
	<c:set var="defaultSec" value=""/>
    <c:set var="tempfilter" value=" and validflag=1 and partitionid in (${USERPAR}'') and rownum=1"/>
	<c:choose>
	<c:when test="${empty param.partitionID}">
    <c:set var="defaultSec" value=""/>
    <db:select var="row" table="v_syspartition" columns="partitionid" where="1=1" orderBy="partitionid desc">
      <c:set var="defaultSec" value="${row.partitionid}"/>
    </db:select>
    <jsp:include page="../../public/menu1.jsp">
	  	<jsp:param name="partitionID" value="${defaultSec}"/>
	  	<jsp:param name="idx" value="${defaultSec}"/>
    </jsp:include>
	  </c:when>
	  <c:otherwise>
	  	<jsp:include page="../../public/menu1.jsp"/>
	  </c:otherwise>
	</c:choose>
<!--��ѯ����-->
<c:choose> 
 <c:when test="${empty param.code}"> 
   <c:set var="code" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="code" value="${param.code}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.code like '${code}' "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.bargainFlag}"> 
   <c:set var="bargainFlag" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="bargainFlag" value="${param.bargainFlag}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.bargainflag =${bargainFlag} "/>
 </c:otherwise> 
</c:choose>
<!--����ѯ����-->
<c:choose> 
 <c:when test="${empty param.partitionID}"> 
   <c:set var="partitionID" value="${defaultSec}"/>
   <c:set var="sqlFilter" value="${sqlFilter} and u1.tradepartition = '${partitionID}' "/>
 </c:when> 
 <c:otherwise> 
   <c:set var="partitionID" value="${param.partitionID}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.tradepartition = '${partitionID}' "/>
 </c:otherwise> 
</c:choose>
<!--���׽ڷ�Χ-->
<c:choose> 
 <c:when test="${empty param.startSection}"> 
   <c:set var="startSection" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="startSection" value="${param.startSection}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.section >= '${startSection}' "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.endSection}"> 
   <c:set var="endSection" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="endSection" value="${param.endSection}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.section <= '${endSection}' "/>
 </c:otherwise> 
</c:choose>
<!--ʡ��-->
<c:choose> 
 <c:when test="${empty param.province}"> 
   <c:set var="province" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="province" value="${param.province}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u3.str6 ='${province}' "/>
 </c:otherwise> 
</c:choose>
	  <br>
		<fieldset width="100%">
		<legend>��ǰ������Ʒ��ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="70%">
			  <tr height="35">
            	<td align="right"> ��ĺţ�</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 150px;" value="${param.code}"onkeypress="notSpace()">
                </td>
                <td align="right">���׽ڷ�Χ��</td>
                <td align="left">
                	<input name="startSection" onblur="reg();" type="text" class="text" style="width: 100px;" value="${param.startSection}"onkeypress="notSpace()" >��
                	<input name="endSection"  onblur="reg();" type="text" class="text" style="width: 100px;" value="${param.endSection}"onkeypress="notSpace()">
                </td>
                <td align="right"> ״̬��</td>
                <td align="left">
                	<select name="bargainFlag">
                		 <option value="" <c:if test="${empty param.bargainFlag}"><c:out value="selected"/></c:if>>ȫ��</option>
                		 <option value="1" <c:if test="${param.bargainFlag==1}"><c:out value="selected"/></c:if>>�ɽ�</option>
                		 	<option value="0" <c:if test="${param.bargainFlag=='0'}"><c:out value="selected"/></c:if>>δ�ɽ�</option>
                  </select>	
                </td>
                 <td align="right" colspan="4">
                <input type="button" onclick="initIdx();" class="btn" value="��ѯ">
                <!-- add by yangpei 2011-11-22 �������ù��� -->
            	<input type="button" onclick="resetForm();" class="btn" value="����">&nbsp;
				</tr>
				<tr height="35">
            	<script>
            	//���׽�������֤
            		function reg(){
            			if(!isNumber(frm.startSection.value)){
            				alert("���׽ڷ�Χ���������ֲ�ѯ");
            				frm.startSection.value="";
            			}
            			if(!isNumber(frm.endSection.value)){
            				alert("���׽ڷ�Χ���������ֲ�ѯ");
            				frm.endSection.value="";
            			}
            		}
            		function resetForm(){
            			frm.code.value="";
            			frm.startSection.value="";
            			frm.endSection.value="";
            			frm.bargainFlag.value="";
            		}
            		//���׽�������֤
            		function initIdxxx(){
            			var reg = false;//��ʶ���׽������ǹ���ȷ
            		//�������Ľ��׽���Ϣ��������
            			if(!isNumber(frm.startSection.value)){
            				frm.startSection.value="";
            				reg = true;
            			}
            			if(!isNumber(frm.endSection.value)){
            				frm.endSection.value="";
            				reg = true;
            			}
            			if(reg){
            				alert("���׽ڷ�Χ���������ֲ�ѯ");
            				return false;
            			}
            			initIdx();//�����ύ����
            		}
            	</script>
                </td>
              </tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  			<td class="panel_tHead_LB">&nbsp;</td>
	  			<td class="panel_tHead_MB">&nbsp;</td>
				<td class="panel_tHead_MB" align=left><input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB" abbr="code">��ĺ� </td>
			    <td class="panel_tHead_MB" abbr="section">�������׽�</td>
				<td class="panel_tHead_MB" abbr="amount">��������</td>
			    <td class="panel_tHead_MB"><!--������Ʒ--></td>
			    <td class="panel_tHead_MB" abbr="bargainflag">�ɽ�״�� </td>
			    <td class="panel_tHead_MB" abbr="modifytime">����޸�ʱ��</td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <c:catch var="exceError">
			    <db:selectPG_CurComm var="row" orderBy="${a}" pageIndex="${pageIndex}" pageSize="${pageSize}" where="u1.commodityid=u2.id and u2.id=u3.commid${sqlFilter}">
				  <tr onclick="selectTr();" align=center height="25">
		  			<td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine">
		  			<c:choose>
		  				<c:when test="${row.section==-1}">
		  			    <font color="#ff0000">��</font>
		  			  </c:when>
		  			  <c:otherwise>
		  			  	   <c:choose>
		  			  	   	 <c:when test="${row.bargainflag==1}">
		  			  	   	 	  <font color="#00ff00">��</font>
		  			  		   </c:when>
                     <c:otherwise>
		  			  		      <font color="#FFBB0D">��</font>
		  			  		   </c:otherwise>
		  			  		</c:choose>
		  			  </c:otherwise>
		  			</c:choose>
		  			</td>
		  			<td class="underLine" align=left><input name="ck" type="checkbox" value='${row.code}'></td>
		  			<td class="underLine">${row.code}</td>
		  			<td class="underLine">
		  			<c:choose>
		  			<c:when test="${not empty row.section}">
		  				<c:choose>
		  					<c:when test="${row.section!=-1}">
		  			      ${row.section}
		  			    </c:when>
		  			    <c:otherwise>
		  			    	&nbsp;
		  			    </c:otherwise>
		  			  </c:choose>
		  			</c:when>
		  			<c:otherwise>
		  				&nbsp;
		  		  </c:otherwise>
		  		  </c:choose>
		  			</td>
                    <td class="underLine">
                      <fmt:formatNumber value="${row.amount}" pattern="<%=AMOUNTPATTERN%>"/>
					</td>
		  			<td class="underLine">
					  <c:if test="${row.lpflag==1}"><!--��--></c:if>
					  <c:if test="${row.lpflag==0}"><!--��--></c:if>
					  </td>
					  <td class="underLine">
					  <c:if test="${row.bargainflag==1}">�ɽ�</c:if>
					  <c:if test="${row.bargainflag==0}">δ�ɽ�</c:if>
					  </td>
					  <td class="underLine"><fmt:formatDate value="${row.modifytime}" pattern="<%=DATEPATTERN%>"/></td>
		  			<td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG_CurComm>
		  	</tBody>
			<!--������ҳ��-->
			<db:cnt_CurComm var="c" where="u1.commodityid=u2.id and u2.id=u3.commid${sqlFilter}">
			 <c:set var="totalCnt" value="${c.n}"/>
			</db:cnt_CurComm>
			<!--������Ʒ��������ҳ������ʾ-->
			<c:set var="amountTotal" value="0"/>
			<db:sum_CurCommAmout var="s" where="u1.commodityid=u2.id and u2.id=u3.commid${sqlFilter}">
               <c:set var="amountTotal" value="${s.n}"/>
			</db:sum_CurCommAmout>
			<c:choose> 
			 <c:when test="${totalCnt%pageSize==0}"> 
			   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
			 </c:when> 
			 <c:otherwise>
			   <c:choose> 
				 <c:when test="${(totalCnt%pageSize)*10>=5*pageSize}"> 
				   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
				 </c:when> 
				 <c:otherwise>
				   <c:set var="pageCnt" value="${totalCnt/pageSize+1}"/>
				 </c:otherwise> 
				</c:choose>
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
			<jsp:include page="/vendue/manage/public/pageTurnComm.jsp">
				<jsp:param name="colspan" value="8"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
				<jsp:param name="amountTotal" value="${amountTotal}"/>
			</jsp:include>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
          	<!--
  		      <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="ɾ��">&nbsp;&nbsp;
            -->
            <input type="button" onclick="addSection(frm,tb,'ck');" class="mdlbtn" value="ָ�����׽�">&nbsp;&nbsp;
            <input type="button" onclick="delSection(frm,tb,'ck');" class="mdlbtn" value="ɾ�����׽�">&nbsp;&nbsp;
            <input type="button" onclick="autoProSection();" class="lgrbtn" value="�Զ����佻�׽�">&nbsp;&nbsp;
			<input type="button" onclick="insertSection(frm,tb,'ck');" class="mdlbtn" value="���뽻�׽�">&nbsp;&nbsp;
            <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="ɾ�����">&nbsp;&nbsp;
            <input type="button" onclick="refreshEngine();" class="lgrbtn" value="���µ�ǰ���׽�">&nbsp;&nbsp;
			<input type="button" onclick="exportToExcel(tb);" class="btn" value="��������">
            </div>
        <input type="hidden" name="opt">
        <input type="hidden" name="lpFlag">
            </td>
          </tr>
     </table>
</form>
</body>
</html>
<%@ include file="../../public/pageTurn2.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function winOpen(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;"); 
}
function winOpen1(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;"); 
}
//-->
//�޸Ļ�Ա�û���Ϣ
function editUserInfo(id,bargainflag){
	if(parseInt(bargainflag)==1){
	  alert("��Ʒ�ѳɽ�,�����޸�!");
	}else{
		var a=window.open("curCommodityMod.jsp?flag=query&code="+id,"_blank","width=400,height=300,scrollbars=yes;");
  }
}

//��תҳ��
function pageTo(){
  if(event.keyCode==13){
    agr=document.frm.pageJumpIdx.value;
    document.frm.pageIndex.value=agr;
    alert(document.frm.pageIndex.value);
    document.frm.submit();	
  }
}

//ˢ���ڴ�
function refreshEngine(){
  frm.opt.value="refreshEngine";
  frm.submit();
}
//����˵��еİ��������ѯʱ
function clickMenu(v){
	frm.partitionID.value=v;
	frm.idx.value=v;
	frm.action="curCommodityList.jsp";
    frm.submit();	
}

//ָ�����׽�
function addSection(frm_delete,tableList,checkName){
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	else if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}else{
	    var pid=frm.partitionID.value;
		var ids=getValues(tableList,checkName);
		var a=openDialog("addSection.jsp?ids="+ids+"&partitionID="+pid+"","_blank","400","250");
    if(1==a)
      frm.submit();
	}
}

//ɾ�����׽�
function delSection(frm_delete,tableList,checkName){
    var pid=frm.partitionID.value;
	var a=openDialog("delSection.jsp?partitionID="+pid+"","_blank","300","200");
    if(1==a)
	  frm.submit();
}

//���뽻�׽�
function insertSection(frm_delete,tableList,checkName){
	var pid=frm.partitionID.value;
	var a=openDialog("insertSection.jsp?partitionID="+pid+"","_blank","300","200");
    if(1==a)
	  frm.submit();
	//}
	

}

//�Զ��������׽�
function autoProSection(){
	    var pid=frm.partitionID.value;
		var a=openDialog("autoProSection.jsp?partitionID="+pid+"","_blank","350","300");
    if(1==a)
	  frm.submit();
	//}
	}

</SCRIPT>