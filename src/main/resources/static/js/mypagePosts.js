document.addEventListener('DOMContentLoaded', () => {
    const container = document.querySelector('.post-grid');
    if (container) {
        // 스크롤을 페이지 상단으로 이동시키는 함수
        function scrollToTop() {
            window.scrollTo({
                top: 0,
                behavior: 'smooth' // 부드러운 스크롤 효과
            });
        }

        // 페이지 상단 이동 버튼 클릭 시 이벤트 리스너 추가
        const scrollToTopButton = document.querySelector('.scroll-to-top');
        if (scrollToTopButton) {
            scrollToTopButton.addEventListener('click', scrollToTop);
        } else {
            console.error('Scroll to top button not found!');
        }
    } else {
        console.error('Post grid container not found!');
    }
});
