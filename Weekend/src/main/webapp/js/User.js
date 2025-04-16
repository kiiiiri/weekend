// 이메일 형식 검사 (정규 표현식)
const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
let userType = 1;  // 기본 usertype 값 (1)


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
    window.open(url, "_blank_1", "toolbar=no, menubar=no, resizable=no, width=450, height=200");
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


// 비밀번호 찾기
function findPassword(){
	
	if (document.frm.email.value == "") {
		alert('이메일을 입력하여 주십시오.');
		document.formm.email.focus();
		return;
	}
	
	
	// 폼을 서버로 제출하여 이메일을 검증
	var form = document.frm;
	form.submit();
}

// 비밀번호 수정
function validateAndShowMessage() {
    var pw = document.frm.pw.value;
    var confirmpw = document.frm.confirmpw.value;

    if (pw === "") {
        alert("새 비밀번호를 입력해 주세요.");
        document.frm.pw.focus();
        return false;
    }

    if (pw !== confirmpw) {
        alert("비밀번호가 일치하지 않습니다.");
        document.frm.confirmpw.focus();
        return false;
    }

    // 폼 제출
	alert("비밀번호가 수정되었습니다.");
	
    return true;
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
	
	return true;
}

function updateCheck() {
    let nickname = document.frm.nickname.value;
    let originalNickname = document.frm.originalNickname.value; // 원래 닉네임 값을 가져옵니다.

    // 닉네임을 변경했을 경우
    if (nickname !== originalNickname) {
        // 닉네임을 변경했을 때만 중복 체크
        // 닉네임이 비어있는지 확인
        if (nickname === "") {
            alert("닉네임을 입력해주세요.");
            return false; // 빈값이면 폼 제출을 막음
        }
		
		if (document.frm.renickname.value != document.frm.nickname.value) {
			alert("닉네임 중복 체크를 하지 않았습니다.");
			frm.nickname.focus();
			return false;
		}
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
		

    // 닉네임이 변경되지 않았다면 폼을 정상적으로 제출
	alert("변경 완료 되었습니다")
    return true;
}