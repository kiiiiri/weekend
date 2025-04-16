let postModalMode = "";
let currentPostCno = 0;

function openPostModal(mode, wno) {
  postModalMode = mode;
  currentPostCno = cno;
  document.getElementById("postPwModal").style.display = "block";
  document.getElementById("postModalOverlay").style.display = "block";
  document.getElementById("postModalTitle").innerText = (mode === "edit" ? "게시글 수정" : "게시글 삭제") + " - 비밀번호 확인";
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
    cpw: pw,
  };

  fetch(contextPath + "/community/checkpass.ajax", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  })
    .then((res) => res.json())
    .then((result) => {
      if (result.success) {
        // 인증 성공 시 페이지 이동
        if (postModalMode === "edit") {
          location.href = contextPath + "/community/edit.do?cno=" + currentPostCno;
        } else if (postModalMode === "delete") {
          // 비밀번호 맞으면 삭제 처리
          fetch(contextPath + "/community/delete.ajax", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ cno: currentPostCno }),
          })
            .then((res) => res.json())
            .then((deleteResult) => {
              if (deleteResult.success) {
                alert("게시물이 삭제되었습니다.");
                location.href = contextPath + "/community/list.do"; // 게시물 목록 페이지로 리다이렉트
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