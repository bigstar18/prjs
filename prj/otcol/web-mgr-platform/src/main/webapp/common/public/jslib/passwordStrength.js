
    window.onload = function(){
    	document.getElementById("passwordPrompt").style.display = "none";
    	document.getElementById("msg").style.display = "none";
    };

//    function init(){
//    	document.getElementById("passwordPrompt").style.display = "none";
//    	document.getElementById("msg").style.display = "none";
//	}
	
	// ����ǿ��  
    function passwordStrength(password){
    	document.getElementById("passwordPrompt").style.display = "block";
	  
        var desc = new Array();
        desc[0] = "�ǳ���";
        desc[1] = "��";
        desc[2] = "����";
        desc[3] = "��";
        desc[4] = "ǿ";
        desc[5] = "�ǳ�ǿ";
        
        var score   = 0;
        
        // ���Ȳ�С��8λ
        if (password.length >= 8) {
        	document.getElementById("msg").style.display = "none";
        	document.getElementById("passwordPrompt").style.display = "block";
			
          score++;
        }
        // ��ĸ
        if ( ( password.match(/[a-z]/) ) || ( password.match(/[A-Z]/) ) ) score++;
        // ���� 
        if (password.match(/\d+/)) score++;
        // �������
        if ( password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/) ) score++;
        // ���ȴ���12λ
        if (password.length > 12) score++;
        
        document.getElementById("passwordDescription").innerHTML = desc[score];
        document.getElementById("passwordStrength").className = "strength" + score;		
	  }
	  // ��������ǿ��
	  function passwordYin(password){
		  if(password == "" || password.length == 0){
			  document.getElementById("passwordPrompt").style.display = "none";
		  }else if (password.length < 8){
			  document.getElementById("passwordPrompt").style.display = "none";
			  document.getElementById("msg").style.display = "block";
			  document.getElementById("msg").innerHTML = "���볤�Ȳ���ȷ�����޸ģ�";
			
		  }
	  }
//window.setTimeout("init()",1);