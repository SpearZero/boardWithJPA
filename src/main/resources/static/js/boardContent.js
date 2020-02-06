let listBtn = document.getElementById("listBtn");

listBtn.addEventListener("click", function(){
	let listBtnValue = listBtn.value;
	let listPage = '1';
	
	if(parseInt(listBtnValue)) {
		listPage = listBtnValue;
	}
	
    location.href = "/board?page=" + listPage;
})