$(document).ready(function() {
		var amq = org.activemq.Amq;
	
	org.activemq.Gnnt = function() {

		var queueDestination = "mgr."+logonUser;

		var topicDestination = 'topic://GNNT.MGR.TOPIC';

		var i=1;
		var queueHandler = function(message) {
			i++;
			var newDiv=" <div id='message_"+i+"' class='userMessageDiv'><div id='backimage_"+i+"'> <img src='"+allJsSkinPath+"image/message_bg.jpg'  alt='标题'/></div><div class='messageToolDiv' id='messageTool_"+i+"'> <div class='msgtitleDiv' id='msgtitle_"+i+"'>用户消息</div><span id='message_close_"+i+"' class='messageCloseDiv'>x</span><div style='clear:both;'></div></div><div id='message_content_"+i+"' class='messageContentDiv'>"+message.nodeValue+" </div></div>";
			 $(document.body).append(newDiv);
			 $("#message_"+i).ShowDiv(256,185,i);
		};
		var topicHandler = function(message) {
			i++;
			var newDiv=" <div id='message_"+i+"' class='userMessageDiv'><div id='backimage_"+i+"'> <img src='"+allJsSkinPath+"image/message_bg.jpg'  alt='标题'/></div><div class='messageToolDiv' id='messageTool_"+i+"'> <div class='msgtitleDiv' id='msgtitle_"+i+"'>系统消息</div><span id='message_close_"+i+"' class='messageCloseDiv'>x</span><div style='clear:both;'></div></div><div id='message_content_"+i+"' class='messageContentDiv'>"+message.nodeValue+" </div></div>";
			 $(document.body).append(newDiv);
			 $("#message_"+i).ShowDiv(256,185,i);
		};
		

		return {
			init : function() {
				amq.addListener('topic', topicDestination, topicHandler);
				
				if (queueDestination.length>4) {
					//alert(queueDestination);
					amq.addListener('queue', queueDestination, queueHandler);
				}
			},
			close:function(){
			   amq.removeListener('topic', topicDestination);
		       if (queueDestination.length > 4) {
		         // alert(queueDestination.length);
			     amq.removeListener('queue', queueDestination);
		        }
			}
		}
	}();

	window.onload = function() {
		org.activemq.Amq.init( {
			uri : '../../amq',
			logging : true,
			timeout : 45,
			clientId : (new Date()).getTime().toString()
		});
		org.activemq.Gnnt.init();
	};

	$(window).unload(function UnLoad() {
		org.activemq.Gnnt.close();
	});});