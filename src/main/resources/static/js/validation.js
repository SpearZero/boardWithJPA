let dupId = document.getElementById("dupId");
dupId.style.display = "none";

let ruleId = document.getElementById("ruleId");
ruleId.style.display = "none";

let rulePwd = document.getElementById("rulePwd");
rulePwd.style.display = "none";

let eqPwd = document.getElementById("eqPwd");
eqPwd.style.display = "none";

let doSignUp = () => {
	let inputId = document.getElementById("username");
	fetch('http://localhost:8080/member/dupCheckResult?username='+inputId.value,{
		method : "GET"
	}).then((response) => {
		return response.json();
	}).then((data) => {
		console.log(data);
	})
}

let signUp = document.getElementById("signUp");
signUp.addEventListener("click", doSignUp);

