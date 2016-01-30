//作者:王珺
//时间2009年6月30日9:02:51（修改）
//版本:v 1.1.1.2(第二个版本)修正在滚动滚动的时候消息框不往下滚动的BUG

(function($) {
	$.fn.extend( {
		ShowDiv : function(widht, height, id) {
			var n = "_" + id;
			var TopY = 0;// 初始化元素距父元素的距离
			$(this).css("width", widht + "px").css("height", height + "px");// 设置消息框的大小
			$(this).slideDown(1000);// 弹出
			$("#messageTool" + n).css("margin-top", -height);// 为内容部分创建高度 溢出
			$("#message" + n).css("bottom",-$(window).scrollTop());//随着滚动条滚动 div弹出始终在右下角
			$("#message_close" + n).click(function() {// 当点击关闭按钮的时候
						if (TopY == 0) {
							$("#message" + n).slideUp(1000);// 这里之所以用slideUp是为了兼用Firefox浏览器
						} else {
							$("#message" + n).animate( {
								top : TopY + height
							}, "slow", function() {
								$("#message" + n).hide();
							});// 当TopY不等于0时 ie下和Firefox效果一样
						}
					});
			$(window).scroll(
					function() {
						$("#message" + n).css(
								"top",
								$(window).scrollTop() + $(window).height()
										- $("#message" + n).height());// 当滚动条滚动的时候始终在屏幕的右下角
						TopY = $("#message" + n).offset().top;// 当滚动条滚动的时候随时设置元素距父原素距离
					});
		}
	})

})(jQuery);