let postModalMode = '';
let currentPostCno = 0;

function openPostModal(mode, cno) {
    postModalMode = mode;
    currentPostCno = cno;
    document.getElementById("postPwModal").style.display = "block";
    document.getElementById("postModalOverlay").style.display = "block";
    document.getElementById("postModalPw").value = '';
    document.getElementById("postModalTitle").innerText = (mode === 'edit' ? '게시글 수정' : '게시글 삭제') + ' - 비밀번호 확인';
}

function closePostModal() {
    document.getElementById("postPwModal").style.display = "none";
    document.getElementById("postModalOverlay").style.display = "none";
}

function submitPostModal() {
	const pw = document.getElementById("postModalPw").value;
	    if (!pw) {
	        alert("비밀번호를 입력하세요.");
	        return;
	    }

	    const data = {
	        cno: currentPostCno,
	        cpw: pw
	    };

	    fetch(contextPath + '/community/checkpass.ajax', {
	        method: 'POST',
	        headers: { 'Content-Type': 'application/json' },
	        body: JSON.stringify(data)
	    }).then(res => res.json())
	      .then(result => {
	        if (result.success) {
	            // 인증 성공 시 페이지 이동
	            if (postModalMode === 'edit') {
	                location.href = contextPath + '/community/edit.do?cno=' + currentPostCno;
	            } else if (postModalMode === 'delete') {
	                // 비밀번호 맞으면 삭제 처리
	                fetch(contextPath + '/community/delete.ajax', {
	                    method: 'POST',
	                    headers: { 'Content-Type': 'application/json' },
	                    body: JSON.stringify({ cno: currentPostCno })
	                }).then(res => res.json())
	                  .then(deleteResult => {
	                      if (deleteResult.success) {
	                          alert("게시물이 삭제되었습니다.");
	                          location.href = contextPath + '/community/list.do'; // 게시물 목록 페이지로 리다이렉트
	                      } else {
	                          alert("삭제에 실패했습니다.");
	                      }
	                  });
	            }
	        } else {
	            alert("비밀번호가 틀렸습니다.");
	        }
	        closePostModal();
	      });
}



let modalMode = '';
let currentRno = 0;
let currentCno = 0;

function openModal(mode, rno, cno) {
    modalMode = mode;
    currentRno = rno;
    currentCno = cno;
    document.getElementById("pwModal").style.display = "block";
    document.getElementById("modalOverlay").style.display = "block";
    document.getElementById("modalPw").value = '';
    document.getElementById("modalTitle").innerText = (mode === 'edit' ? '댓글 수정' : '댓글 삭제') + ' - 비밀번호 확인';
}

function closeModal() {
    document.getElementById("pwModal").style.display = "none";
    document.getElementById("modalOverlay").style.display = "none";
}

function submitModal() {
    const pw = document.getElementById("modalPw").value;
    if (!pw) {
        alert("비밀번호를 입력하세요.");
        return;
    }

    const data = {
        rno: currentRno,
        rpw: pw
    };

    let url = '';
    if (modalMode === 'edit') {
        url = contextPath + '/comments/checkedit.do';
    } else {
        url = contextPath + '/comments/delete.do';
    }

    fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(result => {
        if (result.success) {
            if (modalMode === 'edit') {
                window.location.href = contextPath + '/commentEditForm.do?rno=' + currentRno + '&cno=' + currentCno;
            } else {
                // 새로고침 없이 댓글 DOM 제거
                const targetRow = document.querySelector(`#reply-${currentRno}`);
                if (targetRow) {
                    targetRow.remove();
                }

                closeModal();
            }
        } else {
            alert("비밀번호가 틀렸습니다.");
        }
    });
}
