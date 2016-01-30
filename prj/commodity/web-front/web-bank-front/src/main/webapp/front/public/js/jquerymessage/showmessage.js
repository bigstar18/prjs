$(document).ready(function() {
		var amq = org.activemq.Amq;
	
	org.activemq.Gnnt = function() {

		var queueDestination = "front."+ logonUser;

		var topicDestination = 'topic://GNNT.FRONT.TOPIC';

		var i=1;
		var queueHandler = function(message) {
			i++;
			var newDiv=" <div id='message_"+i+"' class='userMessageDiv'><div id='backimage_"+i+"'> <img src='"+jsSkinPath+"image/message_bg.jpg'  alt='用户消息'/></div><div class='messageToolDiv' id='messageTool_"+i+"'> <div class='msgtitleDiv' id='msgtitle_"+i+"'>用户消息</div><span id='message_close_"+i+"' class='messageCloseDiv'>×</span><div style='clear:both;'></div></div><div id='message_content_"+i+"' class='messageContentDiv' style='width:245px;height:125px;overflow-x:hidden;overflow-y:auto;white-space:pre-line;word-break: break-all;'>"+message.nodeValue+" </div></div>";
			 $(document.body).append(newDiv);
			 $("#message_"+i).ShowDiv(256,185,i);
			 
		};
		var topicHandler = function(message) {
			 i++;
			var newDiv=" <div id='message_"+i+"' class='userMessageDiv'><div id='backimage_"+i+"'> <img src='"+jsSkinPath+"image/message_bg.jpg'  alt='系统消息'/></div><div class='messageToolDiv' id='messageTool_"+i+"'> <div class='msgtitleDiv' id='msgtitle_"+i+"'>系统消息</div><span id='message_close_"+i+"' class='messageCloseDiv'>×</span><div style='clear:both;'></div></div><div id='message_content_"+i+"' class='messageContentDiv' style='width:245px;height:125px;overflow-x:hidden;overflow-y:auto;white-space:pre-line;word-break: break-all;'>"+message.nodeValue+" </div></div>";
			 $(document.body).append(newDiv);
			 $("#message_"+i).ShowDiv(256,185,i);
			
		};
		return {
			init : function() {
				queue = document.getElementById('queue');
				amq.addListener('topic', topicDestination, topicHandler);
				if (queueDestination.length>6) {
					//alert(queueDestination);
					amq.addListener('queue', queueDestination, queueHandler);
				}
			},
			close:function(){
			   amq.removeListener('topic', topicDestination);
		       if (queueDestination.length > 6) {
		         // alert(queueDestination.length);
			     amq.removeListener('queue', queueDestination);
		        }
			}
		}
	}();

	window.onload = function() {
		org.activemq.Amq.init( {
			uri : serverName+'/amq',
			logging : true,
			timeout : 45,
			clientId : (new Date()).getTime().toString()
		});
		org.activemq.Gnnt.init();
		
	};

	$(window).unload(function UnLoad() {
		org.activemq.Gnnt.close();
	});});