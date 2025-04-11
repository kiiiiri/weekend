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
<title>위캔드 | 웹진 작성</title>
<link rel="icon" sizes="256x256" href="<%=contextPath%>/img/Logo_50.png" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=contextPath%>/css/Style.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/Webzinewrite.css" />
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="<%=contextPath%>/js/Nav.js"></script>
<script src="<%=contextPath%>/js/WebzineWrite.js"></script>

</head>

<body class="bg-gray-50" >

<!-- 사이트 제목 -->
    <div class="site_title">
      <div class="title_logo"><a href="#"></a></div>
      <div class="title_wrap">WEEKEND</div>
    </div>
    
    <!-- 네비게이션 바 (비로그인/로그인) -->
    <c:choose>
  <c:when test="${not empty sessionScope.loginUser}">
    <jsp:include page="Header2.jsp" />
  </c:when>
  <c:otherwise>
    <jsp:include page="Header.jsp" />
  </c:otherwise>
</c:choose>

<!-- 글쓰기 폼 -->
    <form name="writeForm" method="post" enctype="multipart/form-data"
        action="<%= request.getContextPath() %>/webzine/wwrite.do"
        onsubmit="return validateForm(this);">

    <div class="max-w-3xl mx-auto mt-10 p-8 bg-white shadow-lg rounded-2xl">
      <h2 class="text-3xl font-bold mb-6 text-gray-800">웹진 작성</h2>

      <!-- 카테고리 선택 -->
      <div class="flex gap-4 mb-4">
        <select name="category" class="border rounded-md p-4 w-1/4">
          <option>카테고리 선택</option>
          <option>국내 여행지</option>
          <option>해외 여행지</option>
          <option>맛집 소개</option>
          <option>여행기</option>
          <option>기타 정보</option>
        </select>
      </div>

      <!-- 제목 입력 -->
      <input type="text" name="wtitle"
             placeholder="제목을 입력해주세요"
             class="w-full border rounded-md p-3 mb-4"
             required />

      <!-- WYSIWYG 에디터 -->
      <div class="border rounded-md p-3 mb-4 bg-gray-100">
        <div id="editor"></div>
      </div>

      <!-- 숨겨진 textarea: 에디터 내용이 저장될 곳 -->
      <textarea name="wtext" id="hiddenContent" style="display:none;"></textarea>
		
		<!-- 이미지 파일명 저장용 hidden input 추가 -->
	<input type="hidden" name="imageFiles" id="imageFiles" />

      <!-- 파일 첨부 -->
      <div class="mb-6">
        <label class="font-semibold block mb-2 text-gray-700">첨부 파일</label>
        <input type="file" name="Wofile" class="block" />
      </div>

     <!-- 버튼 영역 -->
		<div class="flex justify-center items-center space-x-2 mt-6">
  			<button type="submit"
          			class="bg-green-500 text-white px-6 py-2 rounded-md hover:bg-green-600">
    			등록
  			</button>
  			<button type="reset"
          			class="border px-4 py-2 rounded-md hover:bg-gray-100">
    			초기화
 			 </button>
  			<button type="button"
         		 onclick="location.href='<%= request.getContextPath() %>/webzine/list.do';"
         		 class="border px-4 py-2 rounded-md hover:bg-gray-100">
    			목록
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
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    hooks: {
      addImageBlobHook: async (blob, callback) => {
        const formData = new FormData();
        formData.append('Wofile', blob); // input name과 일치해야 함

        const response = await fetch('<%=contextPath%>/webzine/imageupload.do', {
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
    if (form.wtitle.value.trim() === "") {
      alert("제목을 입력해주세요.");
      form.wtitle.focus();
      return false;
    }

    // 내용이 비어있는지 확인
    if (content.trim() === "" || content === "<p><br></p>") {
      alert("내용을 입력해주세요.");
      return false;
    }

    return true; // 유효성 통과 시 submit 진행
  }
</script>
</body>
</html>