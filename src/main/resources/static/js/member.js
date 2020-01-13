
let doSignUp = async () => {
	
	if(confirm("회원가입을 하시겠습니까?")) {
		let inputId = document.getElementById("username");
		let inputIdValue = inputId.value;
		if(inputIdValue.trim() === "") {
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
		
		const getResponse = await fetch('http://localhost:8080/member/dupCheckResult?username='+inputIdValue,{
			method : "GET"
		});
		const get = await getResponse.json();
		const dupCheckResult = get.toString();
		
		if(dupCheckResult === 'error') {
			alert("서버 에러가 발생했습니다. 다시 시도해주세요");
			return false;
		}
		
		if(dupCheckResult === 'true') {
			alert("중복되는 아이디가 존재합니다. 다른 아이디를 입력해주세요");
			return false;
		}
		
		// dupCheckResult가 false이면 아이디가 중복되지 않은것
		if(dupCheckResult === 'false') {
			let postBody = {
					username : inputIdValue,
					password : passwordValue
			}
			
			const postResponse = await fetch('http://localhost:8080/member/signUp', {
				method : 'POST',
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				body : JSON.stringify(postBody)
			});
			
			const post = await postResponse.json();
			
			if(post === 'error') {
				alert("서버 에러가 발생했습니다. 다시 시도해주세요");
				return false;
			}
			
			if(post === 'false') {
				alert("회원가입에 실패했습니다. 다시 시도해주세요");
				return false;
			}
			
			alert("회원가입에 성공했습니다.");
			location.href = "/";
		}
	}
	
}

let signUp = document.getElementById("signUp");
signUp.addEventListener("click", doSignUp);

