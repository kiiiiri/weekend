$(function () {
  const swiper = new Swiper(".swiper", {
    loop: true,
    slidesPerView: 5,
    centeredSlides: true,
    spaceBetween: 20,
    autoplay: {
      delay: 3000,
      disableOnInteraction: false,
    },
    speed: 800,
    loopedSlides: 5,
  });
});