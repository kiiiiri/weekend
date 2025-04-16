<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위캔드 | 게시글 작성</title>
<link rel="icon" sizes="256x256" href="<%=contextPath%>/img/Logo2_50.png" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=contextPath%>/css/Style.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/Communitywrite.css" />
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="<%=contextPath%>/js/Nav.js"></script>

</head>

<body class="bg-gray-50" >

<!-- 사이트 제목 -->
    <div class="site_title">
      <div class="title_logo2"><a href="#"></a></div>
      <div class="title_wrap2">WEEKEND</div>
    </div>
    
    <!-- 네비게이션 바 (비로그인/로그인) -->
    <c:choose>
  <c:when test="${not empty sessionScope.loginUser}">
    <jsp:include page="Header4.jsp" />
  </c:when>
  <c:otherwise>
    <jsp:include page="Header3.jsp" />
  </c:otherwise>
</c:choose>

<!-- 글쓰기 폼 -->
    <form name="writeForm" method="post" enctype="multipart/form-data"
        action="<%= request.getContextPath() %>/community/cwrite.do"
        onsubmit="return validateForm(this);">

    <div class="max-w-3xl mx-auto mt-10 p-8 bg-white shadow-lg rounded-2xl">
      <h2 class="text-3xl font-bold mb-6 text-gray-800">게시글 작성</h2>

      <!-- 카테고리 선택 -->
<div class="flex gap-4 mb-2">
  <select name="ctype" class="border rounded-md p-2 w-1/4">
    <option value="" disabled selected>카테고리 선택</option>
    <option value="1">잡담</option>
    <option value="2">질문</option>
    <option value="3">후기</option>
    <option value="4">여행지 추천</option>
    <option value="6">유용한 정보</option>
    <option value="7">가입인사</option>
    	<c:if test="${not empty sessionScope.loginUser && (sessionScope.loginUser.usertype == 3)}">
  			<option value="5">공지사항</option>
	  	</c:if>
  </select>
</div>

<!-- 작성자 + 비밀번호 입력 (카테고리와 제목 사이) -->
<div class="flex gap-4 mb-4">
  <input type="text" name="cwuser"
         placeholder="작성자"
         class="w-1/2 border rounded-md p-3" required />

  <input type="password" name="cpw"
         placeholder="비밀번호"
         class="w-1/2 border rounded-md p-3" required />
</div>

<!-- 제목 입력 -->
<input type="text" name="ctitle"
       placeholder="제목을 입력해주세요"
       class="w-full border rounded-md p-3 mb-4"
       required />

      <!-- WYSIWYG 에디터 -->
      <div class="border rounded-md p-3 mb-4 bg-gray-100">
        <div id="editor"></div>
      </div>

      <!-- 숨겨진 textarea: 에디터 내용이 저장될 곳 -->
      <textarea name="ctext" id="hiddenContent" style="display:none;"></textarea>
		
		<!-- 이미지 파일명 저장용 hidden input 추가 -->
	<input type="hidden" name="imageFiles" id="imageFiles" />

     <!-- 버튼 영역 -->
		<div class="flex justify-center items-center space-x-2 mt-6">
  			<button type="submit"
          			class="bg-green-500 text-white px-6 py-2 rounded-md hover:bg-green-600">
    			등록
  			</button>
  			<button type="button"
         		 onclick="location.href='<%= request.getContextPath() %>/community/list.do';"
         		 class="border px-4 py-2 rounded-md hover:bg-gray-100">
    			뒤로가기
  			</button>
		</div>
      </div>
  </form>

<script>
  // 업로드된 이미지 파일명을 저장할 배열
  const uploadedImageFiles = [];

  // Toast UI Editor 인스턴스 생성
  const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    height: '600px',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    hooks: {
      addImageBlobHook: async (blob, callback) => {
        const formData = new FormData();
        formData.append('Wofile', blob); // input name과 일치해야 함

        const response = await fetch('<%=contextPath%>/community/imageupload.do', {
          method: 'POST',
          body: formData
        });
        const result = await response.json();

        if (result.success === 1) {
          const imageUrl = result.url;
          const fileName = imageUrl.split('/').pop(); // 저장된 파일명 추출

          uploadedImageFiles.push(fileName); // 배열에 저장
          callback(imageUrl, 'image alt text');

          // hidden input에 이미지 파일명들 추가 (콤마로 구분된 문자열)
          const hiddenInput = document.getElementById('imageFiles');
          hiddenInput.value = uploadedImageFiles.join(',');
        }
      }
    }
  });

  // 폼 전송 전에 에디터 내용 textarea와 유효성 검사 처리
  function validateForm(form) {
    const content = editor.getHTML(); // 또는 getMarkdown()
    document.getElementById("hiddenContent").value = content;

    // 제목이 비어있는지 확인
    if (form.ctitle.value.trim() === "") {
      alert("제목을 입력해주세요.");
      form.wtitle.focus();
      return false;
    }

    // 내용이 비어있는지 확인
    if (ctext.trim() === "" || content === "<p><br></p>") {
      alert("내용을 입력해주세요.");
      return false;
    }

    // 작성자 유효성 검사
    if (form.cwuser.value.trim() === "") {
      alert("작성자를 입력해주세요.");
      form.cwuser.focus();
      return false;
    }

    // 비밀번호 유효성 검사
    if (form.cpw.value.trim() === "") {
      alert("비밀번호를 입력해주세요.");
      form.cpw.focus();
      return false;
    }
    
    return true; // 유효성 통과 시 submit 진행
  }
</script>
</body>
</html>