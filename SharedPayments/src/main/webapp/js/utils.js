function login(){
	var identifier = document.getElementById("identifier");
	identifier = identifier.value;

	var password = document.getElementById("password");
	password = password.value;
	
	var html = $.ajax({
		url:'http://localhost:8080/sharedpayments/servlet/user.login?userName='+identifier+'&password='+password,
		success: 
			function(data) {
				if(data.login == "ok"){
					window.location = "groups.html"
				}
			},
			error:
				function(data){
				alert("error:"+data);
			},
		async:true,
		type:'get',
		dataType:'json'
			});
}