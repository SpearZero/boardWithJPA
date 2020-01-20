let doUpdate = async () => {
	let passwordValue = document.getElementById("password").value;
	let passwordChk = document.getElementById("passwordChk").value;
	
	if(passwordValue !== passwordChk) {
		alert("패스워드와 패스워드 확인이 다릅니다.")
		return false;
	}
	
	if(passwordValue.trim() === "") {
		alert("빈 패스워드 입력 불가");
		return false;
	}
	
	if(passwordValue.length < 8) {
		alert("패스워드의 길이는 최소 8글자여야 합니다.");
		return false;
	}
	
	let postBody = {
		username : document.getElementById("username").value,
		password : passwordValue
	}
	
	const postResponse = await fetch('/member/myInfo', {
		method : 'POST',
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		body : JSON.stringify(postBody)
	});
	
	const post = await postResponse.text();
	
	if(post === 'error') {
		alert("서버 에러가 발생했습니다. 다시 시도해주세요");
		return false;
	}
	
	if(post === 'fail') {
		alert("정보변경에 실패했습니다.");
		return false;
	}
	
	alert("정보 변경 완료.");
	location.href = "/";
}

let update = document.getElementById("update");
update.addEventListener("click", doUpdate);