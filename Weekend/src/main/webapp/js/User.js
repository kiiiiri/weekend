const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

window.onload = function() {
    // 페이지 로딩 시 메시지가 있으면 alert()으로 표시
    if (window.loginErrorMessage) {
        alert(window.loginErrorMessage);  // 메시지를 alert로 표시
    }
}


function loginCheck() {
	if (document.frm.email.value.length == 0) {
		alert("이메일을 입력해주세요.");
		frm.email.focus();
		return false;
	}
	if (document.frm.pw.value == "") {
		alert("암호는 반드시 입력해야 합니다.");
		frm.pw.focus();
		return false;
	}
	return true;
}

let userType = 1;  // 기본 usertype 값 (1)

function toggleUserType() {
    const toggleBtn = document.getElementById('toggleBtn');
    const usertypeInput = document.getElementById('usertype');  // 숨겨진 input 필드

    // "on" 클래스 토글 (상태 변경)
    toggleBtn.classList.toggle('on');

    // 상태에 따라 userType 값 변경
    if (toggleBtn.classList.contains('on')) {
        userType = 2;  // "on" 상태일 때 userType을 2로 설정
    } else {
        userType = 1;  // "off" 상태일 때 userType을 1로 설정
    }

    // usertype 값을 폼에 반영
    usertypeInput.value = userType;
}

function emailCheck() {
    // 이메일 형식 검사 (정규 표현식)
    const email = document.frm.email.value;

    // 이메일이 비어 있거나 형식이 잘못되었으면 경고 메시지를 띄우고 종료
    if (email === "") {
        alert('이메일을 입력하여 주십시오.');
        document.frm.email.focus();
        return;
    }
    if (!emailPattern.test(email)) {
        alert('올바른 이메일 형식을 입력해 주세요.');
        document.frm.email.focus();
        return;
    }

    // 이메일이 올바른 형식일 경우, 중복 체크 창을 엽니다.
    var url = "emailcheck.do?email=" + email;
    window.open(url, "_blank_1", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=450, height=200");
}

function nicknameCheck() {
	if (document.frm.nickname.value == "") {
		alert('닉네임을 입력하여 주십시오.');
		document.formm.nickname.focus();
		return;
	}
	var url = "nicknamecheck.do?nickname=" + document.frm.nickname.value;
	window.open(url, "_blank_1",
					"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=450, height=200");
}

function emailok(email) {
	opener.frm.email.value = document.frm.email.value;
	opener.frm.reemail.value = document.frm.email.value;
	self.close();
}

function nicknameok(nickname) {
	opener.frm.nickname.value = document.frm.nickname.value;
	opener.frm.renickname.value = document.frm.nickname.value;
	self.close();
}

function joinCheck() {
	if (document.frm.name.value.length == 0) {
		alert("이름을 써주세요.");
		frm.name.focus();
		return false;
	}
	
	
	if (document.frm.email.value.length == 0) {
			alert("이메일을 입력해주세요.");
			frm.email.focus();
			return false;
		}
	
	if (!emailPattern.test(document.frm.email.value)) {
			 alert('올바른 이메일 형식을 입력해 주세요.');
			 frm.email.focus();
			 return false;
		}
		
	if (document.frm.reemail.value != document.frm.email.value) {
			alert("이메일 중복 체크를 하지 않았습니다.");
			frm.email.focus();
			return false;
		}
			
	if (document.frm.nickname.value.length == 0) {
		alert("닉네임을 입력해주세요.");
		frm.nickname.focus();
		return false;
	}
	
	if (document.frm.nickname.value.length > 10) {
		alert("닉네임은 10글자 이하 이어야 합니다.");
		frm.nickname.focus();
		return false;
	}
	if (document.frm.renickname.value != document.frm.nickname.value) {
		alert("닉네임 중복 체크를 하지 않았습니다.");
		frm.nickname.focus();
		return false;
	}
	
	
	if (document.frm.pw.value == "") {
		alert("암호는 반드시 입력해야 합니다.");
		frm.pw.focus();
		return false;
	}
	if (document.frm.pw.value != document.frm.pw_check.value) {
		alert("암호가 일치하지 않습니다.");
		frm.pw.focus();
		return false;
	}
	

	if(!document.getElementById('termsCheckbox').checked){
		alert('이용약관에 동의해야 회원가입이 가능합니다.');
		event.preventDefault();
		return false;
	}
	
	alert('회원가입이 완료되었습니다.');
	return true;
}