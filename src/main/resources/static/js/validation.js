
let doSignUp = async () => {
	
	if(confirm("회원가입을 하시겠습니까?")) {
		let inputId = document.getElementById("username");
		
		if(inputId.value.trim() === "") {
			alert("가입할 아이디를 입력해주세요");
			return false;
		}
		
		let idRule = /^[A-Za-z0-9+]{6,12}$/;
		if(!(idRule.test(inputId.value))) {
			alert("아이디는 영문자와 숫자를 포함한 6자이상 12자 이하 문자를 입력해주세요.");
			return false;
		}
		
		let passwordValue = document.getElementById("password").value;
		let passwordLength = passwordValue.length;
		if(passwordLength < 8) {
			alert("패스워드의 길이는 최소 8글자여야 합니다.");
			return false;
		}
		
		let eqPasswordValue = document.getElementById("passwordCheck").value;
		if(!(passwordValue === eqPasswordValue)) {
			alert("비밀번호와 비밀번호 확인의 값이 일치해야 합니다");
			return false;
		}
		
		
		let getResponse = await fetch('http://localhost:8080/member/dupCheckResult?username='+inputId.value,{
			method : "GET"
		});
		let get = await getResponse.json();
		let dupCheckResult = get.toString();
		
		// dupCheckResult가 false이면 아이디가 중복되지 않은것
	}
	
}

let signUp = document.getElementById("signUp");
signUp.addEventListener("click", doSignUp);

