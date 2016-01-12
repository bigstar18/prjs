<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>修改特殊品种交易保证金</title>
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	  <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	  <script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	  <script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
	  <script type="text/javascript">
	  jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			// 修改按钮注册点击事件
			$("#update").click(function(){
				// 验证信息
				if(jQuery("#frm").validationEngine('validate')){
					var isFlag = save_onclick();

				  if(isFlag){
					var vaild = affirm("您确定要操作吗？");
					if(vaild){
						// 修改信息URL
						var updateUrl = $(this).attr("action");
						// 全 URL 路径
						var url = "${basePath}"+updateUrl;
						$("#frm").attr("action",url);
						$("#frm").submit();
					}
				  }
				}
			});

			function save_onclick(){
				if ($("input[name='type1']:checked").val() == "1") {
					$("#marginItem1_S").val($("#marginItem1").val());
					$("#marginItemAssure1_S").val($("#marginItemAssure1").val());
				}
			    for (var i=2; i<6; i++) {
			    	if ($("input[name='type"+i+"']:checked").val() == "1") {
			    		$("#marginItem"+(i)+"_S").val($("#marginItem"+(i)).val());
			    		$("#marginItemAssure"+(i)+"_S").val($("#marginItemAssure"+(i)).val());
			    	}
			    }

			    for (var i = 1; i< 5; i++) {
			    	if ($("#marginItem"+(i+1)).val()=="" && $("#marginItem"+(i+1)+"_S").val()=="" && $("#marginItemAssure"+(i+1)).val()=="" && $("#marginItemAssure"+(i+1)+"_S").val()=="") 
				    {	
			    	}
			    	else if ($("#marginItem"+(i+1)).val()!="" && $("#marginItem"+(i+1)+"_S").val()!="" && $("#marginItemAssure"+(i+1)).val()!="" && $("#marginItemAssure"+(i+1)+"_S").val()!="") 
				    {
					}
					else {
						alert("保证金设置不正确！");
						return false;
					}
			    }
			    
			    return true;
			}
		});
	  </script>
		
    <script type="text/javascript">

      //仅输入数字和.
	  function onlyDoubleInput()
	  {
	    if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
	    {
	      event.returnValue=false;
	    }
	  } 
    
      function changeManner(id, td1, td2, td3, step){ 
    	  var td1 = $("#"+td1);
    	  var td2 = $("#"+td2);
    	  if(id==1)
    	  {
    	   td1.html('保证金'+step+'：');
    	   td2.html('担保金'+step+'：');
    	   $("#"+td3).hide();
    	  }
    	  else if(id==2)
    	  {
    		td1.html('买保证金'+step+'：');
    		td2.html('买担保金'+step+'：');
    		$("#"+td3).show();
    	  }
       } 
       function marginAlgr_change(){
    	 	if (document.getElementById("marginAlgr").value == "1") {
    	 		document.getElementById("marginItem1Percent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure1Percent").innerHTML = '%';
    	 		document.getElementById("marginItem1_SPercent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure1_SPercent").innerHTML = '%';
    	 		
    	 		document.getElementById("marginItem2Percent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure2Percent").innerHTML = '%';
    	 		document.getElementById("marginItem2_SPercent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure2_SPercent").innerHTML = '%';
    	 		
    	 		document.getElementById("marginItem3Percent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure3Percent").innerHTML = '%';
    	 		document.getElementById("marginItem3_SPercent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure3_SPercent").innerHTML = '%';
    	 		
    	 		document.getElementById("marginItem4Percent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure4Percent").innerHTML = '%';
    	 		document.getElementById("marginItem4_SPercent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure4_SPercent").innerHTML = '%';
    	 		
    	 		document.getElementById("marginItem5Percent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure5Percent").innerHTML = '%';
    	 		document.getElementById("marginItem5_SPercent").innerHTML = '%';
    	 		document.getElementById("marginItemAssure5_SPercent").innerHTML = '%';
    	 		
    	 	}else {
    	 		document.getElementById("marginItem1Percent").innerHTML = '';
    	 		document.getElementById("marginItemAssure1Percent").innerHTML = '';
    	 		document.getElementById("marginItem1_SPercent").innerHTML = '';
    	 		document.getElementById("marginItemAssure1_SPercent").innerHTML = '';
    	 		
    	 		document.getElementById("marginItem2Percent").innerHTML = '';
    	 		document.getElementById("marginItemAssure2Percent").innerHTML = '';
    	 		document.getElementById("marginItem2_SPercent").innerHTML = '';
    	 		document.getElementById("marginItemAssure2_SPercent").innerHTML = '';
    	 		
    	 		document.getElementById("marginItem3Percent").innerHTML = '';
    	 		document.getElementById("marginItemAssure3Percent").innerHTML = '';
    	 		document.getElementById("marginItem3_SPercent").innerHTML = '';
    	 		document.getElementById("marginItemAssure3_SPercent").innerHTML = '';
    	 		
    	 		document.getElementById("marginItem4Percent").innerHTML = '';
    	 		document.getElementById("marginItemAssure4Percent").innerHTML = '';
    	 		document.getElementById("marginItem4_SPercent").innerHTML = '';
    	 		document.getElementById("marginItemAssure4_SPercent").innerHTML = '';
    	 		
    	 		document.getElementById("marginItem5Percent").innerHTML = '';
    	 		document.getElementById("marginItemAssure5Percent").innerHTML = '';
    	 		document.getElementById("marginItem5_SPercent").innerHTML = '';
    	 		document.getElementById("marginItemAssure5_SPercent").innerHTML = '';
    	 	}
    	 }
       function setReadOnly(obj)
 	  {
 	    obj.readOnly = true;
 	    obj.style.backgroundColor = "#C0C0C0";
 	  }
 	  function setReadWrite(obj)
 	  {
 	    obj.readOnly = false;
 	    obj.style.backgroundColor = "white";
 	  }
 	 function setDisabled(obj)
	  {
		  obj.disabled = true;
		  obj.style.backgroundColor = "#C0C0C0";
	  }
	  function setEnabled(obj)
	  {
		  obj.disabled = false;
		  obj.style.backgroundColor = "white";
	  }
  
  	  function window_load(){
  	    	setReadOnly(document.getElementById("firmID"));
  	    	marginAlgr_change();
  	  }
    </script>
  </head>
  <body onload="window_load()">
	<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden" >

	  <div class="div_cx">
		<table border="0" width="100%" align="center">
		  <tr>
		    <td>
			  <div class="warning">
			    <div class="content">
			               温馨提示 :修改特殊交易保证金
				</div>
			  </div>
			</td>
		  </tr>
		  <tr>
		    <td>
			  <table border="0" width="100%" align="center">
			   
			    <tr>
				  <td>
				    <div class="div_cxtj">
					  <div class="div_cxtjL"></div>
					  <div class="div_cxtjC">
						基本信息
					  </div>
					  <div class="div_cxtjR"></div>
					</div>
					<div style="clear: both;"></div>
				    <div>
					  <table border="0" cellspacing="0" cellpadding="10" align="center" class="table2_style">
					    <tr>
		          		  <td align="right">交易商代码：</td>   
						  <td>
							${entity.firmID}
                		    <input type="hidden" id="firmID" name="entity.firmID" value="${entity.firmID}" />
						  </td>
            			  <td align="right">品种代码：</td>   
            							   
            			  <td>          		
                		    ${entity.breedID}
                		    <input type="hidden" id="breedID" name="entity.breedID" value="${entity.breedID}" />
						  </td>
						    
						</tr>
					  </table>
				   	</div>
				  </td>
			    </tr>
				
				<tr>  
				  <td>
				  
				    <div class="div_cxtj">
					  <div class="div_cxtjL"></div>
					  <div class="div_cxtjC">
						保证金
					  </div>
					  <div class="div_cxtjR"></div>
					</div>
					<div style="clear: both;"></div>
				    <div>
			      
					  <table border="0" cellspacing="0" cellpadding="10"  class="table2_style">
					    <tr>
			              <td>
                            <table cellSpacing="0" cellPadding="0" border="0" align="center" class="common"> 
           							<tr>
            							<td align="right" width="130">&nbsp;&nbsp;&nbsp;&nbsp;保证金算法：</td>
            							<td width="100">
            								<select id="marginAlgr" name="entity.marginAlgr" style="width:80" class="validate[required]" onchange="marginAlgr_change()">
											  <option value="">请选择</option>
											  <c:forEach items="${algrMap}" var="map" >
											    <option value="${map.key}">${map.value}</option>
											  </c:forEach>
			   								</select> 
			   								<span class="required">*</span>            
            							</td>
            		            
            			            <script type="text/javascript">
										document.getElementById("marginAlgr").value="${entity.marginAlgr}";
									</script>
            					    <script type="text/javascript">
						            	if ("${entity.marginAlgr}" == "1"){$("#td1s").show();$("#td2s").show();} else {$("#td1s").hide();$("#td2s").hide();}
						            </script>
              
              
            </tr>
            
           <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
                 
        <tr>
            
			
			<td align="right" >
           			 第一阶段买卖保证金：
            </td>
            <td colspan="3">
             <input type="radio" name="type1" value="1" onclick="changeManner(1,'td1','td2','aaa1',1);"  style="border:0px;" <c:if test="${entity.type1 == 1 or entity.type1 == null}">checked</c:if> />相同
             
             <input type="radio" name="type1" value="2" onclick="changeManner(2,'td1','td2','aaa1',1);" style="border:0px;" <c:if test="${entity.type1 == 2}">checked</c:if> />不同
            </td >
        </tr>
        <tr> 
             <td align="right" id="td1">
                <c:choose>
                	<c:when test="${entity.type1 == 2}">买保证金1：</c:when>
                	<c:otherwise>保证金1：</c:otherwise>
                </c:choose>
             </td>
            <td>
			  <input type="text"  id="marginItem1" name="entity.marginItem1"  value="${entity.marginItem1 }"
			  	  onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text" />
			  <span id="marginItem1Percent"></span><span class="required">*</span>           
            </td>
            <td align="right" width="112" id="td2">
            	<c:choose>
                	<c:when test="${entity.type1 == 2}">买担保金1：</c:when>
                	<c:otherwise>担保金1：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input type="text"  id="marginItemAssure1" name="entity.marginItemAssure1"  value="${entity.marginItemAssure1 }"
			  	  onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text" />
			  <span id="marginItemAssure1Percent"></span><span class="required">*</span>           
            </td>     
            </tr>
            
	         <tr id="aaa1">
	             <td align="right" id="abc2">卖保证金1：</td>
	             <td id="abc1">
				  <input  id="marginItem1_S" name="entity.marginItem1_S" value="${entity.marginItem1_S }"
				  	  onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text" />
				  <span id="marginItem1_SPercent"></span><span class="required">*</span>           
	            </td >
	            <td align="right"  id="abc3">
	          		  卖担保金1：</td>
	            <td id="abc4">
				  <input  id="marginItemAssure1_S" name="entity.marginItemAssure1_S"  value="${entity.marginItemAssure1_S }"
				  	  onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text" />
				  <span id="marginItemAssure1_SPercent"></span><span class="required">*</span>           
	            </td>   
	        </tr>
	        <script type="text/javascript">
            	if ("${entity.type1}" == "2"){$("#aaa1").show();} else {$("#aaa1").hide();}
            </script>    
        	
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
        <tr>
			<td align="right">
            	第二阶段买卖保证金：
            </td>
			<td colspan="3">
             <input type="radio" name="type2" value="1" onclick="changeManner(1,'td3','td4','aaa2',2);" style="border:0px;" <c:if test="${entity.type2 == 1 or entity.type2 == null}">checked</c:if> />相同            
             <input type="radio" name="type2" value="2" onclick="changeManner(2,'td3','td4','aaa2',2);"  style="border:0px;" <c:if test="${entity.type2 == 2}">checked</c:if> />不同
            
            </td >
			
			
            </tr>
            <tr>
        	<td align="right" id="td3">
        	    <c:choose>
                	<c:when test="${entity.type2 == 2}">买保证金2：</c:when>
                	<c:otherwise>保证金2：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input  id="marginItem2" name="entity.marginItem2"  value="${entity.marginItem2 }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />         
            <span id="marginItem2Percent"></span>
            </td> 
            <td align="right" id="td4">
            	<c:choose>
                	<c:when test="${entity.type2 == 2}">买担保金2：</c:when>
                	<c:otherwise>担保金2：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input  id="marginItemAssure2" name="entity.marginItemAssure2"  value="${entity.marginItemAssure2 }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />         
            <span id="marginItemAssure2Percent"></span>
            </td>  
        
       
        </tr>
        <tr id="aaa2">
            <td align="right"  id="abc21">&nbsp;&nbsp;卖保证金2：</td>
            <td id="abc11">
			  <input  id="marginItem2_S" name="entity.marginItem2_S"  value="${entity.marginItem2_S }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />         
            <span id="marginItem2_SPercent"></span>
            </td> 
            <td align="right" id="abc31">&nbsp;&nbsp;卖担保金2：</td>
            <td id="abc41">
			  <input  id="marginItemAssure2_S" name="entity.marginItemAssure2_S"  value="${entity.marginItemAssure2_S }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />         
            <span id="marginItemAssure2_SPercent"></span>
            </td>     
        </tr>
        <script type="text/javascript">
            	if ("${entity.type2}" == "2"){$("#aaa2").show();} else {$("#aaa2").hide();}
        </script>  
       
      <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr> 
         
        <tr>
			<td align="right">
           		 第三阶段买卖保证金：
            </td>
			<td colspan="3">
             <input type="radio" name="type3" value="1" onclick="changeManner(1,'td5','td6','aaa3',3);"  style="border:0px;" <c:if test="${entity.type3 == 1 or entity.type3 == null}">checked</c:if> />相同
             <input type="radio" name="type3" value="2" onclick="changeManner(2,'td5','td6','aaa3',3);"  style="border:0px;" <c:if test="${entity.type3 == 2}">checked</c:if> />不同
            
            </td >
		</tr>
		<tr >
            <td align="right" id="td5">
            	<c:choose>
                	<c:when test="${entity.type3 == 2}">买保证金3：</c:when>
                	<c:otherwise>保证金3：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input  id="marginItem3" name="entity.marginItem3"  value="${entity.marginItem3 }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />        
            <span id="marginItem3Percent"></span>
            </td>
            <td align="right" id="td6">
            	<c:choose>
                	<c:when test="${entity.type3 == 2}">买担保金3：</c:when>
                	<c:otherwise>担保金3：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input  id="marginItemAssure3" name="entity.marginItemAssure3"  value="${entity.marginItemAssure3 }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />        
            <span id="marginItemAssure3Percent"></span>
            </td>
			
            </tr>
            
            <tr id="aaa3"> 
			<td align="right" id="abc22">卖保证金3：</td>
            <td id="abc12">
			  <input  id="marginItem3_S" name="entity.marginItem3_S"  value="${entity.marginItem3_S }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />        
            <span id="marginItem3_SPercent"></span>
            </td>
            <td align="right"  id="abc32">卖担保金3：</td>
            <td id="abc42">
			  <input  id="marginItemAssure3_S" name="entity.marginItemAssure3_S"  value="${entity.marginItemAssure3_S }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />        
            <span id="marginItemAssure3_SPercent"></span>
            </td>
            </tr>
			<script type="text/javascript">
            	if ("${entity.type3}" == "2"){$("#aaa3").show();} else {$("#aaa3").hide();}
            </script>  
        
			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
			 
        <tr>
			<td align="right">
             	 第四阶段买卖保证金：
            </td>
			<td colspan="3">
            
             <input type="radio" name="type4" value="1" onclick="changeManner(1,'td7','td8','aaa4',4);"  style="border:0px;" <c:if test="${entity.type4 == 1 or entity.type4 == null}">checked</c:if> />相同
             <input type="radio" name="type4" value="2" onclick="changeManner(2,'td7','td8','aaa4',4);"  style="border:0px;" <c:if test="${entity.type4 == 2}">checked</c:if> />不同
            </td >
            </tr>
            <tr>
			<td align="right" id="td7">
				<c:choose>
                	<c:when test="${entity.type4 == 2}">买保证金4：</c:when>
                	<c:otherwise>保证金4：</c:otherwise>
                </c:choose>
			</td>
            <td>
			  <input  id="marginItem4" name="entity.marginItem4"  value="${entity.marginItem4 }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />           
            <span id="marginItem4Percent"></span>
            </td>
            <td align="right" id="td8">
            	<c:choose>
                	<c:when test="${entity.type4 == 2}">买担保金4：</c:when>
                	<c:otherwise>担保金4：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input  id="marginItemAssure4" name="entity.marginItemAssure4"  value="${entity.marginItemAssure4 }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />           
              <span id="marginItemAssure4Percent"></span>
            </td>
         </tr>
         
         <tr id="aaa4">
             <td align="right" id="abc13">卖保证金4：</td>
            <td id="abc23">
			  <input  id="marginItem4_S" name="entity.marginItem4_S"  value="${entity.marginItem4_S }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />           
            <span id="marginItem4_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金4：</td>
            <td id="abc43">
			  <input  id="marginItemAssure4_S" name="entity.marginItemAssure4_S"  value="${entity.marginItemAssure4_S }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />           
              <span id="marginItemAssure4_SPercent"></span>
            </td>
        </tr>
        <script type="text/javascript">
            	if ("${entity.type4}" == "2"){$("#aaa4").show();} else {$("#aaa4").hide();}
        </script>  
       <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
           
        
        
        <tr>
			<td align="right">
             	 第五阶段买卖保证金：
            </td>
			<td colspan="3">
            <input type="radio" name="type5" value="1" onclick="changeManner(1,'td50','td30','aaa5',5);" style="border:0px;" <c:if test="${entity.type5 == 1 or entity.type5 == null}">checked</c:if> />相同
             <input type="radio" name="type5" value="2" onclick="changeManner(2,'td50','td60','aaa5',5);" style="border:0px;" <c:if test="${entity.type5 == 2}">checked</c:if> />不同
             
            </td >
            </tr>
            <tr>
			 <td align="right" id="td50">
			 	<c:choose>
                	<c:when test="${entity.type5 == 2}">买保证金5：</c:when>
                	<c:otherwise>保证金5：</c:otherwise>
                </c:choose>
			 </td>
            <td>
			  <input  id="marginItem5" name="entity.marginItem5"  value="${entity.marginItem5 }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />           
            <span id="marginItem5Percent"></span>
            </td>
            <td align="right" id="td60">
            	<c:choose>
                	<c:when test="${entity.type5 == 2}">买担保金5：</c:when>
                	<c:otherwise>担保金5：</c:otherwise>
                </c:choose>
            </td>
            <td>
			  <input id="marginItemAssure5" name="entity.marginItemAssure5"  value="${entity.marginItemAssure5 }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text"/>           
            <span id="marginItemAssure5Percent"></span>
            </td>   
          </tr>
        
          <tr id="aaa5">
            <td align="right" id="abc13">卖保证金5：</td>
            <td id="abc24">
			  <input id="marginItem5_S" name="entity.marginItem5_S" value="${entity.marginItem5_S }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />           
            <span id="marginItem5_SPercent"></span>
            </td>
            <td align="right" id="abc33">卖担保金5：</td>
            <td id="abc44">
			  <input id="marginItemAssure5_S" name="entity.marginItemAssure5_S" value="${entity.marginItemAssure5_S }"
			  	 onkeypress="return onlyDoubleInput()" class="validate[maxSize[15],custom[onlyDoubleSp]] input_text" />           
            <span id="marginItemAssure5_SPercent"></span>
            </td>  
          </tr>
        <script type="text/javascript">
            	if ("${entity.type5}" == "2"){$("#aaa5").show();} else {$("#aaa5").hide();}
        </script>    
       <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>														
	 </table>
								
					  
					
				         </td>
				       </tr>
			         </table>
			       </div> 
			       
			     </td>
		       </tr>
		</table>
	  </div>
	  <div class="tab_pad">
	    <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  <tr>
		    <td align="center">
			  <rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/timebargain/firmSet/breedSpecial/updateTradeMargin.action" id="update"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <button class="btn_sec" onClick="window.close();">关闭</button>
		    </td>
		  </tr>
	    </table>
	  </div>
    </form>
  </body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>