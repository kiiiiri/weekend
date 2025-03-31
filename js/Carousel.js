const carousel = document.querySelector(".carousel");
const newsList = document.querySelector(".news_list");
let slideInterval; // 자동 슬라이드를 위한 변수

// ⭐ 최신 기사 썸네일을 동적으로 추가하는 함수
async function fetchArticles() {
    try {
        // 로컬의 articles.json 파일을 가져오기
        const response = await fetch('js/articles.json');  // JSON 파일을 로드
        const data = await response.json();

        // 받은 데이터로 썸네일 이미지 동적으로 생성 (캐러셀)
        data.articles.slice(0, 5).forEach((article, index) => {
            const imgElement = document.createElement('img');
            imgElement.src = article.thumbnail_url; // 썸네일 URL
            imgElement.alt = `이미지 ${index + 1}`;
            carousel.appendChild(imgElement);
        });

        // 받은 데이터로 기사 리스트 동적으로 생성
        data.articles.slice(0, 6).forEach((article, index) => {
            const newsItem = document.createElement('div');
            newsItem.classList.add('news_item');

            const img = document.createElement('img');
            img.src = article.thumbnail_url;
            img.alt = `기사 이미지 ${index + 1}`;

            const title = document.createElement('h3');
            title.textContent = `기사 제목 ${index + 1}`;

            const description = document.createElement('p');
            description.textContent = `기사 ${index + 1}의 간단한 설명입니다.`;

            // 기사 항목에 이미지, 제목, 설명 추가
            newsItem.appendChild(img);
            newsItem.appendChild(title);
            newsItem.appendChild(description);

            // news_list에 기사 추가
            newsList.appendChild(newsItem);
        });

        // 자동 슬라이드 시작
        autoSlide();

    } catch (error) {
        console.error("기사 데이터를 불러오는 데 실패했습니다:", error);
    }
}

// ⭐ 캐러셀 이동 함수 (슬라이드 효과)
function moveCarousel(direction) {
    const images = carousel.children; // 모든 이미지 가져오기

    // 슬라이드 이동 방향 설정
    const width = carousel.offsetWidth / 3; // 각 이미지의 너비

    if (direction === 'next') {
        carousel.style.transition = "transform 0.5s ease-in-out";
        carousel.style.transform = `translateX(-${width}px)`; // 왼쪽으로 한 칸 이동
    } else if (direction === 'prev') {
        carousel.style.transition = "transform 0.5s ease-in-out";
        carousel.style.transform = `translateX(${width}px)`; // 오른쪽으로 한 칸 이동
    }

    // 이동이 끝난 후 실행
    setTimeout(() => {
        const first_image = images[0]; // 첫 번째 이미지
        const last_image = images[images.length - 1]; // 마지막 이미지

        if (direction === 'next') {
            carousel.appendChild(first_image); // 첫 번째 이미지를 마지막으로 이동
        } else if (direction === 'prev') {
            carousel.insertBefore(last_image, images[0]); // 마지막 이미지를 첫 번째로 이동
        }

        // 원래 위치로 돌아오는 설정
        carousel.style.transition = "none"; 
        carousel.style.transform = "translateX(0)"; // 원래 위치로 복귀
    }, 500); // 0.5초 후 첫 번째 이미지를 마지막으로 이동
}

// ⭐ 자동 슬라이드 시작
function autoSlide() {
    slideInterval = setInterval(() => moveCarousel('next'), 3000); // 3초마다 슬라이드 이동
}

// ⭐ 마우스를 캐러셀에 올리면 자동 슬라이드 멈추기
carousel.addEventListener('mouseenter', () => {
    clearInterval(slideInterval); // 자동 슬라이드 멈춤
});

// ⭐ 마우스를 캐러셀에서 떼면 자동 슬라이드 재시작
carousel.addEventListener('mouseleave', () => {
    autoSlide(); // 자동 슬라이드 다시 시작
});

// 좌측 화살표 클릭 이벤트
document.querySelector(".carousel-prev").addEventListener("click", () => {
    moveCarousel('prev');
});

// 우측 화살표 클릭 이벤트
document.querySelector(".carousel-next").addEventListener("click", () => {
    moveCarousel('next');
});

// 초기 실행
fetchArticles();  // 캐러셀에 이미지 추가 및 기사 리스트 추가