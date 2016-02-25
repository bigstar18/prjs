
    window.onload = function(){
    	document.getElementById("passwordPrompt").style.display = "none";
    	document.getElementById("msg").style.display = "none";
    };

//    function init(){
//    	document.getElementById("passwordPrompt").style.display = "none";
//    	document.getElementById("msg").style.display = "none";
//	}
	
	// 密码强度  
    function passwordStrength(password){
    	document.getElementById("passwordPrompt").style.display = "block";
	  
        var desc = new Array();
        desc[0] = "非常弱";
        desc[1] = "弱";
        desc[2] = "良好";
        desc[3] = "中";
        desc[4] = "强";
        desc[5] = "非常强";
        
        var score   = 0;
        
        // 长度不小于8位
        if (password.length >= 8) {
        	document.getElementById("msg").style.display = "none";
        	document.getElementById("passwordPrompt").style.display = "block";
			
          score++;
        }
        // 字母
        if ( ( password.match(/[a-z]/) ) || ( password.match(/[A-Z]/) ) ) score++;
        // 数字 
        if (password.match(/\d+/)) score++;
        // 特殊符号
        if ( password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/) ) score++;
        // 长度大于12位
        if (password.length > 12) score++;
        
        document.getElementById("passwordDescription").innerHTML = desc[score];
        document.getElementById("passwordStrength").className = "strength" + score;		
	  }
	  // 隐藏密码强度
	  function passwordYin(password){
		  if(password == "" || password.length == 0){
			  document.getElementById("passwordPrompt").style.display = "none";
		  }else if (password.length < 8){
			  document.getElementById("passwordPrompt").style.display = "none";
			  document.getElementById("msg").style.display = "block";
			  document.getElementById("msg").innerHTML = "密码长度不正确，请修改！";
			
		  }
	  }
//window.setTimeout("init()",1);