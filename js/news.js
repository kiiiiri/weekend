// URL에서 id 값 가져오기
function getArticleIdFromURL() {
  const params = new URLSearchParams(window.location.search);
  return params.get("id"); // URL에서 id 값 가져옴
}

// JSON 데이터에서 해당 ID의 기사 찾기
async function fetchArticleData() {
  try {
      const response = await fetch("js/article.json");
      const data = await response.json();

      const articleId = getArticleIdFromURL();
      if (!articleId) {
          console.error("기사 ID가 없습니다.");
          return;
      }

      // ID에 해당하는 기사 찾기
      const article = data.articles.find(a => a.id == articleId);

      if (!article) {
          console.error("해당 ID의 기사를 찾을 수 없습니다.");
          return;
      }

      // 기사 데이터 출력
      document.querySelector('.article-title').textContent = article.title;
      document.querySelector('.article-description').textContent = article.description;

      const photoContainer = document.querySelector('.photo');
      const imgElement = document.createElement('img');
      imgElement.src = article.thumbnail_url;
      imgElement.alt = "기사 이미지";
      photoContainer.appendChild(imgElement);

  } catch (error) {
      console.error("기사 데이터를 불러오는 데 실패했습니다:", error);
  }
}

// 페이지 로드 시 실행
window.onload = fetchArticleData;