<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ page import="java.util.List"%>

<html>
  <head>
    <title>����Ա���������ͻ�����</title> 
    <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
    <link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
    <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
    <script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
    <script type="text/javascript">
  //---------------------------start paraminfo-------------------------------
	  var tmp_paraminfo;
	  var tmp_paraminfo_up = true;
	  function paraminfo_onclick()
	  {
		
		var paraminfo = document.getElementById('paraminfo');
		var paraminfo_img = document.getElementById('paraminfo_img');
		
	    if (tmp_paraminfo_up)
	    {
	      tmp_paraminfo_up = false;
	      paraminfo_img.src = "<c:url value="${skinPath}/image/app/timebargain/ctl_detail_Down.gif"/>";
	      tmp_paraminfo = paraminfo.innerHTML;
	      paraminfo.innerHTML = "";
	    }
	    else
	    {
	      tmp_paraminfo_up = true;
	      paraminfo_img.src = "<c:url value="${skinPath}/image/app/timebargain/ctl_detail_Up.gif"/>";
	      paraminfo.innerHTML = tmp_paraminfo;
	    }
	  }
	  //-----------------------------end paraminfo-----------------------------
	  // �������״��봦��
	  function codeHandle(){
		  
		  var permission = "";
          var lbRight = document.getElementById('lbRight');
          
		  for(i=0; i < lbRight.options.length; i++)
		  {
		      permission += lbRight.options[i].value + ",";
		  }
		  if(permission != "")
		  {
		      permission =  permission.substr(0, permission.length - 1) ;
		  }
		    document.getElementById('code').value = permission;
	  }
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');	
				// ���ذ�ť����¼� 
				$("#back").click(function(){
					//��ȡ����Ȩ�޵� URL
			    	var backUrl=document.getElementById('back').action;
			    	//��ȡ������תURL
			    	var url = "${basePath}"+backUrl;
			    	//�� URL ��Ӳ���
					url += "?firmID=" + '${firmID}';

			    	document.location.href = url;
				});

				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateUrl;
							$("#frm").attr("action",url);
						
							var traderID = document.getElementById('traderID').value;
							if (traderID != ""){
							   if(!tmp_paraminfo_up)
							   {
							      paraminfo_onclick();
							   }
							   codeHandle();
							  $("#frm").submit();
							}else{
								alert("����Ա����Ϊ�գ�");
							}
							
						}
					}
				});
				
			});
	</script>
	<script type="text/javascript">
	  
function moveSelected(from,to) {

  newTo = new Array();
  newFrom = new Array();

  for(i=from.options.length - 1; i >= 0; i--) {
    if (from.options[i].selected) {
      newTo[newTo.length] = cloneOption(from.options[i]);
    }

    else
    {
      newFrom[newFrom.length] = cloneOption(from.options[i]);
    }
  }
  for(i=to.options.length - 1; i >= 0; i--) {
    newTo[newTo.length] = cloneOption(to.options[i]);

    newTo[newTo.length-1].selected = false;
  }

  to.options.length = 0;
  from.options.length = 0;

  for(i=newTo.length - 1; i >=0 ; i--) {
    to.options[to.options.length] = newTo[i];
  }

  for(i=newFrom.length - 1; i >=0 ; i--) {
    from.options[from.options.length] = newFrom[i];
  }

  selectionChanged(to,from);
}

function cloneOption(option) {
  var out = new Option(option.text,option.value);
  out.selected = option.selected;
  out.defaultSelected = option.defaultSelected;
  return out;
}

function selectionChanged(selectedElement,unselectedElement) {
  for(i=0; i<unselectedElement.options.length; i++) {
    unselectedElement.options[i].selected=false;
  }
}
function moveSelectedAll(from ,to){
  newTo = new Array();
  newFrom = new Array();

  for(i=from.options.length - 1; i >= 0; i--) {
      newTo[newTo.length] = cloneOption(from.options[i]);
  }
  for(i=to.options.length - 1; i >= 0; i--) {
    newTo[newTo.length] = cloneOption(to.options[i]);

    newTo[newTo.length-1].selected = false;
  }

  to.options.length = 0;
  from.options.length = 0;

  for(i=newTo.length - 1; i >=0 ; i--) {
    to.options[to.options.length] = newTo[i];
  }
}
	</script>
  </head>
  
  <body >
    <table border="0" height="400" width="800" align="center">
	  <tr>
	    <td>
	      <form id="frm" name="frm" action="" method="post">
	      
		    <fieldset class="pickList">
			  <legend class="common">
				<b>�ɲ������״�������</b>
			  </legend>
            
            <input type="hidden" id="firmID" name="firmID" value="${firmID }" />
            <input type="hidden" id="traderID" name="traderID" value="${traderID }" />
            <input type="hidden" id="code" name="code" />
            
            <!-- ������Ϣ -->
            <table width="95%" border="0" align="center"   cellpadding="0" cellspacing="2">
           
              <tr>
				<td colspan="4" height="3">&nbsp;</td>
			  </tr>		
              <tr class="common">
                <td colspan="4">
                  <fieldset class="pickList">
                    <legend>
                      <table cellspacing="0" cellpadding="0" border="0" width="100%" >
                        <col width="55"></col><col></col><col width="6"></col>
                        <tr>
                          <td><b>������Ϣ</b></td>
                          <td><hr width="99%" class="pickList"/></td>
                          <td ><img id="paraminfo_img" src="<c:url value="${skinPath}/image/app/timebargain/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="paraminfo_onclick()"/></td>
                        </tr>
                      </table>
                    </legend>
                    <span id="paraminfo">
                      <table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" valign="top" >        												
		                <tr>
		                  <td class="common"  width="30%" align="center">
		                    <table border="0" class="common"  width="200">
		                      <tr>
		                        <td align="center">δ�ô���</td>
		                      </tr>
		                      <tr>
		                        <td>
		                          <select  name="lbLeft" size="16" onDblClick="moveSelected(lbLeft,lbRight);" style="width : 100%" multiple="multiple">
		                            <c:forEach items="${codeNotChoose}" var="result" >
									  <option value="${result.CODE}">${result.CODE}</option>
									</c:forEach>
		                          </select>
		                        </td>
		                      </tr>
		                    </table>
		                  </td>
		                  <td align="center" valign="center"  width="15%">
		                    <table border="0"   height="140" width="100" >
		                      <tr>
		                        <td  >
		                          <input type="button" name="lbAdd"  value="  >   "  onclick="moveSelected(lbLeft,lbRight);" class="anniu_btn" >
		                        </td>
		                      </tr>
		                      <tr>
		                        <td >
		                          <input type="button" name="lbAddAll" value="  >>  "  onclick="return moveSelectedAll(lbLeft,lbRight);" class="anniu_btn" >
		                        </td>
		                      </tr>
		                      <tr>
		                        <td  >
		                          <input type="button" name="lbDel" value="  <   "  onclick="moveSelected(lbRight,lbLeft);" class="anniu_btn" >
		                        </td>
		                      </tr>
		                      <tr>
		                        <td >
		                          <input type="button" name="lbDelAll" value="  <<  "  onclick="moveSelectedAll(lbRight,lbLeft);" class="anniu_btn" >
		                        </td>
		                      </tr>
		                    </table>
		                  </td>
		                  <td class="common" width="30%">
		                    <table border="0" class="common" width="200">
		                      <tr>
		                        <td class="common" align="center" >���ô���</td></tr>
		                          <tr>
		                            <td>
		                              <select id="lbRight"  name="lbRight" size="16" onDblClick="moveSelected(lbRight,lbLeft);"   style="width : 100%" multiple="multiple"  >		                         
		                                ${operateCode }
		                              </select>
		                            </td>
		                          </tr>
		                        </table>
		                      </td> 
		                    </tr>																		
                      </table >
                    </span>
                  </fieldset>
                </td>
              </tr>
              
              <tr>
				<td colspan="4" height="3">&nbsp;</td>
			  </tr>																																										
			  <tr>
				<td colspan="4" align="center">
				  <rightButton:rightButton name="�ύ" onclick="" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/updateCode.action" id="update"></rightButton:rightButton>
				  &nbsp;&nbsp;
				  <rightButton:rightButton name="����" onclick="" className="anniu_btn" action="/timebargain/firmSet/tradePrivilege/listTrader.action" id="back"></rightButton:rightButton>
				</td>
			  </tr>
            </table>
            
            </fieldset>
          </form>		
        </td>
      </tr>
    </table>
  </body>
</html>
