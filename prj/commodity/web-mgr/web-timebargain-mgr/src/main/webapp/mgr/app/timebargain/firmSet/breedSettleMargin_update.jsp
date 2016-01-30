<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>修改特殊品种交收保证金</title>
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
			});

			
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
	  
       function settleMarginAlgr_B_change(){
    		if ($("#settleMarginAlgr_B").val() == "1") {
    			$("#settleMarginRate_BPercent").show();
    		}else {
    			$("#settleMarginRate_BPercent").hide();
    		}
    	}

    	function settleMarginAlgr_S_change(){
    		if ($("#settleMarginAlgr_S").val() == "1") {
    			$("#settleMarginRate_SPercent").show();
    		}else {
    			$("#settleMarginRate_SPercent").hide();
    		}
    	}

    	function payoutAlgr_change(){
    		if ($("#payoutAlgr").val() == "1") {
    			$("#payoutRatePercent").show();
    		}else {
    			$("#payoutRatePercent").hide();
    		}
    	}
    
    	function window_load(){
   	   
   	    	settleMarginAlgr_B_change();
   	    	settleMarginAlgr_S_change();
   	    	payoutAlgr_change();
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
			               温馨提示 :修改特殊交收保证金
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
			      
					  <table border="0" cellspacing="0" cellpadding="0"  class="table2_style" align="center">
					    <tr>
			              <td>
			              
                            <table cellSpacing="0" cellPadding="5" border="0" align="center" class="common"> 
								<tr>
        	                      <td>交收保证金：</td>
                                </tr>
                                <tr>
                                  <td align="right">买方方式：</td>
                                  <td>
                                    <select id="settleMarginAlgr_B" name="entity.settleMarginAlgr_B" style="width:80" onchange="settleMarginAlgr_B_change()" class="validate[required]">
					                  <option value="">请选择</option>
					                  <c:forEach items="${algrMap}" var="map" >
									    <option value="${map.key}">${map.value}</option>
									  </c:forEach>
			                        </select> 
                                    <span class="required">*</span>
                                  </td>
                                  <td align="right" >买方标准：</td>
                                  <td>
			                        <input  id="settleMarginRate_B" name="entity.settleMarginRate_B" value="${entity.settleMarginRate_B }"
			  	                            onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[10],custom[onlyDoubleSp]] input_text" />         
                                    <span id="settleMarginRate_BPercent">%</span><span class="required">*</span>
                                  </td>
                                  <script type="text/javascript">
                                     document.getElementById("settleMarginAlgr_B").value = "${entity.settleMarginAlgr_B}";
                                  </script>
                                </tr>
                                <tr >
                                  <td align="right" >卖方方式：</td>
                                  <td>
                                    <select id="settleMarginAlgr_S" name="entity.settleMarginAlgr_S" style="width:80" class="validate[required]" onchange="settleMarginAlgr_S_change()">
					                  <option value="">请选择</option>
					                  <c:forEach items="${algrMap}" var="map" >
									    <option value="${map.key}">${map.value}</option>
									  </c:forEach>
			                        </select>
                                    <span class="required">*</span>
                                  </td>
                                  <td align="right" >卖方标准：</td>
                                  <td>
			                        <input  id="settleMarginRate_S" name="entity.settleMarginRate_S" value="${entity.settleMarginRate_S }"
			  	                            onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[10],custom[onlyDoubleSp]] input_text" />         
                                    <span id="settleMarginRate_SPercent">%</span><span class="required">*</span>
                                  </td>
                                  <script type="text/javascript">
                                     document.getElementById("settleMarginAlgr_S").value = "${entity.settleMarginAlgr_S}";
                                  </script>
                             </tr>
                             <tr>
        	                   <td align="right">交收货款算法：</td>
                               <td>
                                   <select id="payoutAlgr" name="entity.payoutAlgr" style="width:80" class="validate[required]" onchange="payoutAlgr_change()">
					                  <option value="">请选择</option>
					                  <c:forEach items="${algrMap}" var="map" >
									    <option value="${map.key}">${map.value}</option>
									  </c:forEach>
			                       </select>
                                 <span class="required">*</span>
                               </td>
                               <td align="right">交收货款标准：</td>
                               <td>
			                     <input  id="payoutRate" name="entity.payoutRate" value="${entity.payoutRate }"
			  	                         onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[10],custom[onlyDoubleSp]] input_text" />         
                                 <span id="payoutRatePercent">%</span><span class="required">*</span>
                               </td>
                               <script type="text/javascript">
                                     document.getElementById("payoutAlgr").value = "${entity.payoutAlgr}";
                               </script>
                             </tr>
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
			  <rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/timebargain/firmSet/breedSpecial/updateSettleMargin.action" id="update"></rightButton:rightButton>
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