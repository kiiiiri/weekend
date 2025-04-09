let page = 1;
let loading = false;
let endOfData = false;

window.addEventListener("scroll", () => {
  if (loading || endOfData) return;

  const scrollTop = window.scrollY;
  const windowHeight = window.innerHeight;
  const documentHeight = document.body.scrollHeight;

  // 스크롤이 거의 바닥에 닿았을 때 (100px 여유)
  if (scrollTop + windowHeight >= documentHeight - 100) {
    loadMoreArticles();
  }
});

function loadMoreArticles() {
  loading = true;
  page++;

  document.querySelector("#loading").style.display = "block";
  
  fetch(`/webzine/list.do?pageNum=${page}`)
    .then((response) => response.text())
    .then((data) => {
      if (data.trim() === "") {
        endOfData = true; // 더 이상 데이터 없음
      } else {
        const container = document.querySelector("#recentwebzine_container");
        container.insertAdjacentHTML("beforeend", data);
      }
    })
    .catch((err) => console.error("로드 중 오류:", err))
    .finally(() => {
      loading = false;
    });
}
