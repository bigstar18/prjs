document.onreadystatechange = subSomething;// ��ҳ�����״̬�ı��ʱ��ִ���������.
function subSomething() {
	if (document.readyState == "complete") // ��ҳ�����״̬Ϊ��ȫ����ʱ����
	{
		var regexTests = document.getElementsByTagName("button");
		var len = regexTests.length;

		//		var key = rightMap.keySet();
		//        for (var i in key){
		//           alert(map.get(key[i]));
		//        }
		// ��������ʽ
		// var regex = /^add/;//��add��ͷ
		/**
		 * for (i = 0; i < len; i++) { var str =regexTests[i].id;
		 * if(regex.test(str)) { alert("������ʽ:"+regexTests[i].value); } }
		 */
		// �ַ�������
		for (i = 0; i < len; i++) {
			var str = regexTests[i].id + '';
			//			if (str.substr(0, "add".length) == "add")// ��add��ͷ
			//			{
			//				if (!sessionAdd) {
			//					regexTests[i].disabled = true;
			//				}
			//			}
			//			if (str.substr(0, "delete".length) == "delete")// ��delete��ͷ
			//			{
			//				if (!sessionDelete) {
			//					regexTests[i].disabled = true;
			//				}
			//			}
			//			if (str.substr(0, "update".length) == "update")// ��update��ͷ
			//			{
			//				if (!sessionUpdate) {
			//					regexTests[i].disabled = true;
			//				}
			//			}
			if (rightMap) {
				for ( var o in rightMap) {
					if (str.substr(0, o.length) == o) {
						if (!rightMap[o]) {
							regexTests[i].style.display = 'none';
						}
					}
				}
			}

		}

	}
}
