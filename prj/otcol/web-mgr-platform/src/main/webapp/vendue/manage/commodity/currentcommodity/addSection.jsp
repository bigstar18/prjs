<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<head>
<title>ָ�����׽�</title>
</head>
<%
   java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
   if(request.getParameter("add")!=null){
   Connection conn = null;
   PreparedStatement ps = null;
   ResultSet rs=null;
   ResultSet rsExt=null;
   StringBuffer sql=new StringBuffer();
   try{
	Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    conn.setAutoCommit(false);
    String idV=request.getParameter("ids");//��Ʒ��ĺż���
	boolean modFlag=true;//��־�Ƿ���Щ��Ʒ�����޸Ľ��׽�
    boolean globalFlag=false;//�жϱ����Ƿ�����ɾ��һ����Ʒ,�źü���־
    int operCnt=0;//�жϲ�����ǰ��Ʒ������
    String operCode=null;//ֻ����������Ʒ��¼�ı�ĺ�
    String returnInfo="";//���ز����޸Ľ��׽ڵı�ĺ�
    boolean curSectionFlag=false;//�ж�����Ľ��׽��Ƿ���ڵ�ǰ�����н��׵Ľ��׽�,�ǵĻ�����ָ��,��־����
    String sysdate="";//���ݿ������ʱ��
	String sids="";//��Ҫ�޸���Ʒ��ĺ�
	boolean tradingFlag=true;//�ж���Ʒ�Ƿ����ڽ���
    int section=Integer.parseInt(request.getParameter("section"));
    String partitionID=request.getParameter("partitionID");
	String[] ids=null;//�������ű�ĺ�
	//���Զ�˽ӿ�
	KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":"+port+ "/"+REMOTECLASS+""+partitionID);
    sql.append("select sysdate from dual");
    ps=conn.prepareStatement(sql.toString());
	rs=ps.executeQuery();
	if(rs.next()){
	    sysdate=rs.getString(1).substring(0,18);
	}
	rs.close();
	ps.close();
	if(section!=-1){
	    sql=new StringBuffer();
	    sql.append("select section from v_syscurstatus where tradepartition="+partitionID+" and section>=");
	    sql.append(""+section+"");
	    ps=conn.prepareStatement(sql.toString());
	    rs=ps.executeQuery();
		if(rs.next()){
		    curSectionFlag=true;
		}
    }
	if(curSectionFlag==false){
	    if(idV!=null){
			ids=idV.split(",");
		    for(int i=0;i<ids.length;i++){
			    sids+=ids[i]+",";
                sql=new StringBuffer();
				sql.append("select bargainflag,section from v_curcommodity where code='"+ids[i]+"'");
				ps=conn.prepareStatement(sql.toString());
	            rs=ps.executeQuery();
				if(rs.next()){
				    sql=new StringBuffer();
					sql.append("select section from v_syscurstatus where status=2 and section="+rs.getInt(2)+"");
					sql.append(" and tradepartition="+partitionID+"");
                    ps=conn.prepareStatement(sql.toString());
	                rsExt=ps.executeQuery();
					if(rsExt.next()){
					    tradingFlag=false;
					}
					rsExt.close();
					ps.close();
					if(rs.getInt("bargainflag")==0&&tradingFlag==true){
						sql=new StringBuffer();
						sql.append("update v_curcommodity set section="+section+",modifytime=");
						sql.append("to_date('"+sysdate+"','yyyy-mm-dd hh24:mi:ss') where code=");
						sql.append("'"+ids[i]+"'");
						ps=conn.prepareStatement(sql.toString());
						ps.executeUpdate(sql.toString());
						operCnt+=1;
						operCode=ids[i];
                        globalFlag=true;
					}else{
						modFlag=false;
						returnInfo+=ids[i]+",";
					}
				}
			}
		}
        
		//�ر�����Դ����
	    conn.commit();
        conn.setAutoCommit(true);
        conn.close();
	    //ˢ���ڴ�
        dao.loadCommodity();
    	if(modFlag==true){
	        alert("ָ�����׽ڳɹ�!",out);
	    }else{
	        alert(returnInfo+"�ǳɽ�״̬�������ڽ����в���ָ�����׽�!",out);
	    }
	}else{
	    alert("������Ľ��׽ڱ���Ѿ��������,������ָ����",out);
	}
}
catch(Exception e)
{
	conn.rollback();
	e.printStackTrace();
    errOpt();
	alert("���������������Ա��ϵ!",out);
}finally{
	if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
	if(rsExt!=null){try{rsExt.close();}catch(Exception ex){}rsExt=null;}
    if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
    try{
        conn.close();
    }catch (Exception e){}
    conn = null;
}
}
%>
<body>
	<form name=frm id=frm action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>���뽻�׽ڱ��</legend>
		<BR>
		<span>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
		 	  	<tr height="35">
		 	  	    <td align="right">���׽ڱ�ţ�</td>
		 	  		<td align="left">
		 	  		  <input type="text" class="text" name="section" maxlength="5" onchange="checkNumber(this,false,false,'���׽ڱ��')">
		 	  	    </td>
		 	  	</tr>
				<tr height="35">
		 	  	    <td align="right">��ǰ��Ч���׽���ʼ��ţ�</td>
		 	  		<td align="left">
					  <db:select var="v_result" columns="status,section" table="v_syscurstatus" where="tradepartition=${param.partitionID}">
					      <c:set var="curParSection" value="${v_result.section}"/>
						  <c:set var="status" value="${v_result.status}"/>
					  </db:select>
					  <c:choose>
						<c:when test="${curParSection==0}">    
							<input type="text" name="now" class="text" value="1" readonly>
						</c:when>
						<c:when test="${status==3}">
							<input type="text" name="now" class="text" value="${curParSection+1}" readonly>
						</c:when>
					    <c:otherwise>  
						 <input type="text" name="now" class="text" value="${curParSection}" readonly>
					    </c:otherwise>
					  
					 </c:choose>

		 	  		  
		 	  	    </td>
		 	  	</tr>
		 	  	<tr height="35">
		 	  	  <td align="right">�������κν��׽ڣ�</td>
		 	  	  <td align="left">
		 	  		  <input type="checkbox" name="noSection" onclick="clickNoSection();">
		 	  	  </td>
		 	  	</tr>
		 </table>
		 </span>
     </fieldset>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
            <td width="40%">
            <div align="center">
            <input type="hidden" name="add">
            <input type="hidden" name="partitionID" value="${param.partitionID}">
            <input type="hidden" name="ids" value="${param.ids}">
			      <input type="button" onclick="return frmChk();" class="btn" value="ȷ��">&nbsp;&nbsp;
			      <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
            </div>
            </td>
          </tr>
     </table>
	</form>
<script language="javascript">
var request = new ActiveXObject("Microsoft.XMLHTTP");
var add = true;
function oneAjax(){
	var tradepartition = frm.partitionID.value;
	var unitid = frm.section.value;
	request.onreadystatechange = stateChanged;
	request.open("post","haveSection.jsp?tradepartition="+tradepartition+"&unitid="+unitid,false);
	request.send();
	request.abort();
}
function stateChanged(){
	if (request.readyState == 4){
		var result = request.responseText;
		var bs = result.split("[]");
		var number = bs[0];
		var no = Number(number);
		if(no != 0){
			add = false;
		} else if (no == 0) {
			add = true;
		}
	}
}

function frmChk(){
	if(frm.noSection.checked==true){
	} else {
		oneAjax();
		if (add == true) {
			alert("�޴˽��׽ڣ�");
			return false;
		}
	}
	if(Trim(frm.section.value) == "") {
		alert("���׽ڱ�Ų���Ϊ�գ�");
		frm.section.focus();
		return false;
	} else if(parseInt(frm.section.value)<=0&&frm.noSection.checked==false) {
		  alert("���׽ڱ�ű������0��");
		  frm.section.focus();
		  return false;
	} else if(frm.section.value<frm.now.value&&frm.noSection.checked==false) {
		  alert("���׽� "+frm.section.value+" ������Ч�Ľ��׽ڣ�������ָ��");
		  frm.section.focus();
		  return false;
	} else {
	    if(userConfirm()){
  	      frm.add.value="����";
  	      frm.submit();
  	      //return true;
  	    }else{
  	      return false;
  	    }
    }
  }
  
  //���Ƶ�����������κν��׽�ʱ
  function clickNoSection(){
    if(frm.noSection.checked==true){
      frm.section.value="-1";
      frm.section.readOnly=true;
    }else{
      frm.section.value="";
      frm.section.readOnly=false;
    }
  }
</script>
</body>
</html>
<%@ include file="/vendue/manage/public/footInc.jsp" %>