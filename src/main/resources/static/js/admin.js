document.addEventListener('DOMContentLoaded', () => {
	let list = document.querySelectorAll(".navigation li");

	function activeLink() {
	  list.forEach((item) => {
	    item.classList.remove("hovered");
	  });
	  this.classList.add("hovered");
	}
	
	list.forEach((item) => item.addEventListener("mouseover", activeLink));
	
	// Menu Toggle
	let toggle = document.querySelector(".toggle");
	let navigation = document.querySelector(".navigation");
	let main = document.querySelector(".main");
	
	toggle.onclick = function () {
	  navigation.classList.toggle("active");
	  main.classList.toggle("active");
	};
    // Mở Modal Đổi Mật Khẩu
    window.showChangePasswordModal = function() {
        const changePasswordModal = document.getElementById('changePasswordModal');
        changePasswordModal.style.display = 'block'; // Hiển thị modal
    }

    // Đóng Modal Đổi Mật Khẩu
    window.closeChangePasswordModal = function() {
        const changePasswordModal = document.getElementById('changePasswordModal');
        changePasswordModal.style.display = 'none'; // Ẩn modal
    }

    // Mở Modal Đăng Nhập
    window.showLoginModal = function() {
        const loginModal = document.getElementById('loginModal');
        loginModal.style.display = 'block'; // Hiển thị modal
    }
	
    // Đóng Modal Đăng Nhập
    window.closeLoginModal = function() {
        const loginModal = document.getElementById('loginModal');
        loginModal.style.display = 'none'; // Ẩn modal
    }

    // Đóng modal khi bấm vào ngoài modal
    window.onclick = function(event) {
        if (event.target === document.getElementById('changePasswordModal')) {
            closeChangePasswordModal();
        }
        if (event.target === document.getElementById('loginModal')) {
            closeLoginModal();
        }
    }
    var errorMessage = document.getElementById('error-message');
    if (errorMessage && errorMessage.innerText !== "") {
        // Mở modal nếu có lỗi
        showLoginModal();
    }
    var errorUser = document.getElementById('error-user');
    if (errorUser && errorUser.innerText !== "") {
        // Mở modal nếu có lỗi
        showLoginModal();
    }
    var errorMessageCaptcha = document.getElementById('captcha-error');
    if (errorMessageCaptcha && errorMessageCaptcha.innerText !== "") {
        // Mở modal nếu có lỗi
        showLoginModal();
    }
    var errorChangePass = document.getElementById('error-changePass');
    if (errorChangePass && errorChangePass.innerText !== "") {
        // Mở modal nếu có lỗi
        showChangePasswordModal();
    }
    
    var successChangePass = document.getElementById('successChangePass');
    if (successChangePass && successChangePass.innerText !== "") {
        alert(successChangePass.innerText);
    }
    var successUpdateOrCreate = document.getElementById('successUpdateOrCreate');
    if (successUpdateOrCreate && successUpdateOrCreate.innerText !== "") {
        alert(successUpdateOrCreate.innerText);
    }
    var successChangePass = document.getElementById('successChangePass');
    if (successChangePass && successChangePass.innerText !== "") {
        alert(successChangePass.innerText);
    }

});


