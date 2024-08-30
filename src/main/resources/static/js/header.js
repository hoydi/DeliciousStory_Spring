document.addEventListener("DOMContentLoaded", function() {
    const menuToggle = document.querySelector("#toggleButton");
    const mobileMenu = document.querySelector("#mobileMenu");

    menuToggle.addEventListener("click", function(event) {
        event.stopPropagation(); // 클릭 이벤트 전파 방지
        mobileMenu.classList.toggle("show");
    });

    document.addEventListener("click", function(event) {
        if (!menuToggle.contains(event.target) && !mobileMenu.contains(event.target)) {
            mobileMenu.classList.remove("show");
        }
    });
});
