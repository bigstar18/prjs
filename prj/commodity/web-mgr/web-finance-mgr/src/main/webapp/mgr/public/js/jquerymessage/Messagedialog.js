//����:���B
//ʱ��2009��6��30��9:02:51���޸ģ�
//�汾:v 1.1.1.2(�ڶ����汾)�����ڹ���������ʱ����Ϣ�����¹�����BUG

(function($) {
	$.fn.extend( {
		ShowDiv : function(widht, height, id) {
			var n = "_" + id;
			var TopY = 0;// ��ʼ��Ԫ�ؾุԪ�صľ���
			$(this).css("width", widht + "px").css("height", height + "px");// ������Ϣ��Ĵ�С
			$(this).slideDown(1000);// ����
			$("#messageTool" + n).css("margin-top", -height);// Ϊ���ݲ��ִ����߶� ���
			$("#message" + n).css("bottom",-$(window).scrollTop());//���Ź��������� div����ʼ�������½�
			$("#message_close" + n).click(function() {// ������رհ�ť��ʱ��
						if (TopY == 0) {
							$("#message" + n).slideUp(1000);// ����֮������slideUp��Ϊ�˼���Firefox�����
						} else {
							$("#message" + n).animate( {
								top : TopY + height
							}, "slow", function() {
								$("#message" + n).hide();
							});// ��TopY������0ʱ ie�º�FirefoxЧ��һ��
						}
					});
			$(window).scroll(
					function() {
						$("#message" + n).css(
								"top",
								$(window).scrollTop() + $(window).height()
										- $("#message" + n).height());// ��������������ʱ��ʼ������Ļ�����½�
						TopY = $("#message" + n).offset().top;// ��������������ʱ����ʱ����Ԫ�ؾุԭ�ؾ���
					});
		}
	})

})(jQuery);