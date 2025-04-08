function validateForm(form) {
		if (form.wtitle.value == "") {
		    alert("제목을 입력하세요.");
		    form.wtitle.focus();
		    return false;
		}
		if (form.wtext.value == "") {
		    alert("내용을 입력하세요.");
		    form.wtext.focus();
		    return false;
		}
		}
