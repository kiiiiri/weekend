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
<title>위캔드 | 기사 수정</title>
<link rel="icon" sizes="256x256" href="<%=contextPath%>/img/Logo_50.png" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet" href="<%=contextPath%>/css/Style.css" />
<link rel="stylesheet" href="<%=contextPath%>/css/Webzinewrite.css" />
<script src="<%=contextPath%>/js/Nav.js"></script>

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

<!-- 수정 폼 -->
    <form name="writeForm" method="post" enctype="multipart/form-data"
        action="<%= request.getContextPath() %>/webzine/update.do"
        onsubmit="return validateForm(this);">

    <div class="max-w-3xl mx-auto mt-10 p-8 bg-white shadow-lg rounded-2xl">
      <h2 class="text-3xl font-bold mb-6 text-gray-800">웹진 수정</h2>

<input type="hidden" name="prevOfile" value="${dto.wofile}" />
<input type="hidden" name="prevSfile" value="${dto.wsfile}" />
<input type="hidden" name="wno" value="${dto.wno}" />

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



<!-- 기존에 적혀 있던 제목 -->
<input type="text" name="wtitle"
       value="${dto.wtitle}"
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

     <!-- 버튼 영역 -->
		<div class="flex justify-center items-center space-x-2 mt-6">
 		 <button type="submit"
          class="bg-orange-500 text-white px-6 py-2 rounded-md hover:bg-orange-500">
    		수정하기
  		</button>
  		<button type="button"
          	onclick="location.href='<%= request.getContextPath() %>/webzine/wview.do?wno=${dto.wno}';"
          	class="border px-6 py-2 rounded-md hover:bg-gray-100">
    		이전으로
  		</button>
		</div>
      </div>
  </form>

<script>
  // 업로드된 이미지 파일명을 저장할 배열
  const uploadedImageFiles = [];

  // Toast UI Editor 인스턴스 생성
  const initialContent = `${dto.wtext}`
  const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    height: '600px',
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    initialValue: initialContent,
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
    if (wtext.trim() === "" || content === "<p><br></p>") {
      alert("내용을 입력해주세요.");
      return false;
    }
    return true; // 유효성 통과 시 submit 진행
  }
  
  
//폼 전송 전에 이미지 파일명들을 hidden input에 넣는 함수
  function prepareImageFileList() {
    const editorHTML = editor.getHTML();
    const imageRegex = /<img[^>]+src="([^">]+)"/g;
    let match;

    uploadedImageFiles.length = 0; // 기존 배열 비우기

    while ((match = imageRegex.exec(editorHTML)) !== null) {
      const src = match[1];
      const fileName = src.split('/').pop(); // 파일명만 추출
      uploadedImageFiles.push(fileName);
    }

    document.getElementById('imageFiles').value = uploadedImageFiles.join(',');
    
    // 에디터 내용을 textarea에도 반영
    document.getElementById('hiddenContent').value = editorHTML;
  }
  
  const form = document.querySelector('form');
  form.addEventListener('submit', function (e) {
    prepareImageFileList(); // 이미지 파일 리스트 준비
  });
  
  
  
</script>
</body>
</html>