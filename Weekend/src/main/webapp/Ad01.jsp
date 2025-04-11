<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<style>
  .ad_banner {
  width: 230px;
  height: 630px;
  background: url('<%= contextPath %>/img/ad_04.png') no-repeat center;
  background-size: cover;
  border: 1px solid black;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
  font-size: 14px;
  padding: 10px;
  box-sizing: border-box;
  position: absolute;
  top: 800px;
  right: 20px;
  display: none; /* 기본은 안 보이게 */
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.ad_banner.fixed {
  position: fixed;
  top: 200px;
  right: 20px;
  display: block;
  opacity: 0;
  transform: translateY(20px);
}

.ad_banner.fixed.active {
  opacity: 1;
  transform: translateY(0);
}

</style>

<div class="ad_banner" id="adBanner">
</div>

<script>
  const adBanner = document.getElementById("adBanner");
  const triggerOffset = 600;
  let isFixed = false;

  window.addEventListener("scroll", () => {
    const scrollY = window.scrollY;

    // 고정 시 광고 보이게
    if (scrollY >= triggerOffset && !isFixed) {
      adBanner.classList.add("fixed");

      requestAnimationFrame(() => {
        adBanner.classList.add("active");
      });

      isFixed = true;
    }

    // 원위치 복귀 시 광고 숨기기
    else if (scrollY < triggerOffset && isFixed) {
      adBanner.classList.remove("active");

      setTimeout(() => {
        adBanner.classList.remove("fixed");
        isFixed = false;
      }, 300); // transition 시간과 맞춤
    }
  });
</script>

