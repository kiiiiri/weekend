$(document).on("click", ".pagination a", function (e) {
    // 현재 페이지 번호를 눌렀다면 이벤트 막고 함수 종료
    if ($(this).hasClass("current")) {
      e.preventDefault(); // 링크 이동 막기
      return;
    }

    // 현재 페이지가 아닌 경우에는 클릭 후 스크롤 애니메이션
    setTimeout(function () {
      $("html, body").animate({
        scrollTop: 0
      }, 400);
    }, 100);
  });

