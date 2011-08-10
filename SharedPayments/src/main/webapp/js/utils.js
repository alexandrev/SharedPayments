var user = "";

function showAddUser(){
	$( "#addUser" ).dialog({
		height: 240,
		modal: true
	});
}

function addUser(){
	var identifier = document.getElementById("addidentifier");
	identifier = identifier.value;
	
	var firstName = document.getElementById("addfirstname");
	firstname = firstName.value;
	
	var lastName = document.getElementById("addlastname");
	lastname = lastName.value;

	var mailAddress = document.getElementById("addmailaddress");
	mailaddress = mailAddress.value;
	
	var password = document.getElementById("addpassword");
	password = password.value;
	
	var html = $.ajax({
		url:'http://localhost:8080/sharedpayments/servlet/user.add?userName='+identifier+'&firstName='+firstName+'&lastName='+lastName+'&mailAddress='+mailaddress+'&password='+password,
		success: 
			function(data) {
				if(data.login == "ok"){
					user = identifier;
					window.location = "groups.html";
				}
			},
			error:
				function(data){
				var error = document.getElementById("error");
				error.value = data.error;
				error.css("display","block");
			},
		async:true,
		type:'get',
		dataType:'json'
			});
}

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
					user = identifier;
					window.location = "groups.html";
				}
			},
			error:
				function(data){
				var error = document.getElementById("error");
				error.value = data.error;
				error.css("display","block");
			},
		async:true,
		type:'get',
		dataType:'json'
			});
}