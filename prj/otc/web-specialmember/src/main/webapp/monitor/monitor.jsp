<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>实时监控</title>
		<link type="text/css" href="assets/css/smoothness/jquery-ui-1.8.14.custom.css" rel="stylesheet" />	
		<script type="text/javascript" src="assets/js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript" src="assets/js/jquery-ui-1.8.14.custom.min.js"></script>
		<script type="text/javascript">
			var isShift = false;
			$(function(){
				$('a').each(function(i) {
				    // This function only happens if user hover element while holding shift-button down
				    jQuery(this).bind('click', function(e) {
				        if (e.shiftKey) {
				            isShift = true;
				        }
				    });
				    jQuery(this).bind('mouseout', function(e) {
				    	isShift = false;
				    });
				});
				// Tabs
				$('#tabs').tabs({
					cache:true,
					spinner: '加载中...',
					ajaxOptions: { dataType: "html" },
					select: function(event, ui) { 
						$.each(document.getElementsByTagName('object'),function(index,value){
							var str = 'value.'+value.id+'pause()';//alert(str);
							try{eval(str);}catch(e){}
						});
						if(ui.panel){
							var obj = ui.panel.getElementsByTagName('object');
							if(obj && obj.length>0){
								var str1='obj[0].'+obj[0].id+'resume()';//alert(str1);
								try{eval(str1);}catch(e){}
							}
						}
						if(isShift && ui.tab){//tab.innerText
							window.open(ui.tab.href,"交易监控","location=0,status=0,scrollbars=0,toolbar=0,menubar=0,resizable=1");
						}
					}
				});
				//hover states on the static widgets
				$('#dialog_link, ul#icons li').hover(
					function() { $(this).addClass('ui-state-hover'); }, 
					function() { $(this).removeClass('ui-state-hover'); }
				);
				
				//window resize
				$(window).resize(function(){
					var h = $(window).height();
			    	var w = $(window).width();
			    	if(h>490){
			    		$("#tabs").css('height', h-10 );
			    		$(".ui-tabs-panel").css('height', h-40 );
			    	}else{
			    		$("#tabs").css('height', 480 );
			    		$(".ui-tabs-panel").css('height', 450 );
			    	}
				});
				$(window).resize();
			});
			
		</script>
		<style type="text/css" media="screen"> 
            html, body  { height:100%; font-size:12px;}
            body { margin:0; padding:0; overflow:auto; text-align:center; 
                   background-color: #ffffff; }
            #tabs { width:98%; min-width:1100px;}
        </style>
	</head>
	<body>
		<div id="tabs">
			<ul>
				<li><a href="useronline.action?AUsessionId=${LOGINIDS}">在线监控</a></li>
				<li><a href="memberhold.action?AUsessionId=${LOGINIDS}">会员持仓单监控</a></li>
				<li><a href="memberclose.action?AUsessionId=${LOGINIDS}">会员平仓单监控</a></li>
				<li><a href="smembersum.action?AUsessionId=${LOGINIDS}">特别会员头寸监控</a></li>
				<li><a href="smemberfund.action?AUsessionId=${LOGINIDS}">特别会员资金监控</a></li>
			</ul>
		</div>	
	</body>
</html>


