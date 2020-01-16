let doLogin = async() => {
	let inputId = document.getElementById("username");
	let inputPassword = document.getElementById("password");
	
	let postBody = {
		username : inputId.value,
		password : inputPassword.value
	}
	
	const postResponse = await fetch('/member/login', {
		method : "POST",
		headers : {
			'Aceept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		body : JSON.stringify(postBody)
	});
	
	const post = await postResponse.json();
	const loginResult = post.toString();
	
	if(loginResult === 'error') {
		alert("서버 에러가 발생했습니다. 다시 시도해주세요");
	}
	
	if(loginResult === 'false') {
		alert("아이디, 비밀번호가 일치하지 않습니다.");
		inputId.value = "";
		inputPassword.value = "";
		
		inputId.focus();
	}
	
	if(loginResult === 'true') {
		location.href = "/";
	}
}

let login = document.getElementById("login");
login.addEventListener("click", doLogin);
