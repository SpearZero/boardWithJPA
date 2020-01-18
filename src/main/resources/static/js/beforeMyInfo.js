let doCheckMyInfo = () => {
	let checkForm = document.getElementById("checkMyInfoForm");
	checkForm.submit();
};

let checkMyInfo = document.getElementById("checkMyInfo");
checkMyInfo.addEventListener("click", doCheckMyInfo);
