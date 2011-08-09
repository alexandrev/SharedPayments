function login(){
	var identifier = document.getElementById("identifier");
	identifier = identifier.value;

	var password = document.getElementById("password");
	password = password.value;
	
	var html = $.ajax({
		url:'http://localhost:8080/sharedpayments/user.login?userName='+identifier+'&password='+password,
		success: 
			function(data) {
				alert(data);
			},
			error:
				function(data){
				alert(data);
			},
		async:true,
		type:'get',
		dataType:'json'
			});
}