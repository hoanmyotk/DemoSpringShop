document.addEventListener('DOMContentLoaded', () => {
    const snowWrapper = document.getElementById('snow_wrapper');
    for (let i = 0; i < 60; i++) { 
        const snow = document.createElement('div');
        let sizeSnow = Math.floor(Math.random() * 50);
        let blurSnow = Math.floor(Math.random() * 5);
        snow.className = 'snow';
        snow.style.left = Math.floor(Math.random() * 100) + 'vw'; 
        snow.style.top = Math.floor(Math.random() * 100) + 'vh'; 
        snow.style.animationDuration = Math.random() * 5 + 5 + 's'; 
        snow.style.width = sizeSnow + 'px';
        snow.style.height = sizeSnow + 'px';
        snow.style.filter = "blur("+blurSnow+"px)";
        snowWrapper.appendChild(snow);
    }  
    let navBar = document.querySelector('nav');
    navScroll(false)

    document.addEventListener('scroll', () => {
        navScroll(false)
    })

    function navScroll(result) {
        if (window.scrollY > 0 && !(result)) {
            navBar.style.boxShadow = '0 5px 20px rgba(245, 235, 235, 0.15)';
            navBar.style.backgroundColor = '#121212';
            navBar.style.opacity = '0.7';
        }
        else {
            navBar.style.boxShadow = 'none'
            navBar.style.backgroundColor = 'transparent'
            navBar.style.opacity = '1';
        }
    }


    let menuBar = document.querySelector('#menu-bar');
    let menuPage = document.querySelector('#menu-page');
    let html = document.querySelector('html');

    let menuBarStyle = window.getComputedStyle(menuBar);
    let screenType = '';

    if (menuBarStyle.display === "flex") {
        screenType = "mobile"
    }
    else if (menuBarStyle.display === "none") {
        screenType = "desktop"
    }
    else {
        console.log("Error: Failed to identify screen type", screenType)
    }



    menuBar.addEventListener('click', () => {
        menuPage.classList.toggle('active');

        html.style.overflow = (menuPage.classList.contains('active')) ? "hidden" : "scroll"
        navScroll(menuPage.classList.contains('active'))
    })


    let productContainerWidth = document.querySelector('.product-cards-container').offsetWidth;

    let rootStyles = getComputedStyle(html);
    let productCardWidth;
    let productCards;

    if (screenType === "desktop") {
        productCardWidth = parseInt(rootStyles.getPropertyValue('--product-card-width').replace('px', ''));
        productCardsPerRow = Math.floor(productContainerWidth / (productCardWidth + 5));
    }
    else if (screenType === "mobile") {
        productCardsPerRow = (html.offsetWidth > 430) ? 3 : 2
        productCardWidth = Math.floor((productContainerWidth / productCardsPerRow) - 10)
    }
    else {
        console.log("Error: Failed to set productCardsPerRow & productCardWidth")
    }

    let marginSpacing = (productContainerWidth - (productCardsPerRow * productCardWidth)) / (productCardsPerRow - 1);
    let lastElement = productCardsPerRow - 1;
    
    let sectionLastElement = []
    let productSections = document.querySelectorAll('.product-section');
    productSections.forEach((section) => {
        productCards = section.querySelectorAll('.product-card');
        
        for (let i = 0; i < productCardsPerRow; i++) {
            productCards[i].classList.add('active');

            if (i === lastElement) {
                productCards[i].style.marginRight = '0px';
            }
            else {
                productCards[i].style.marginRight = `${marginSpacing}px`;
            }
        }

        sectionLastElement[section.id] = lastElement;
    })
    

    let wishListCount;
    let heartButtons = document.querySelectorAll('.heart-button');

    heartButtons.forEach((button) => {
        button.addEventListener('click', () => {
            button.classList.toggle('active');
            
            wishListCount = accessCounter('.wishlist-link span');
            wishListCount.innerHTML = document.querySelectorAll('.heart-button.active').length;
        })
    })


    function accessCounter(spanElement) {
        if (screenType === 'desktop') {
            return document.querySelector('#navbar-tools').querySelector(spanElement)
        }
        else if (screenType === 'mobile') {
            return document.querySelector('#menu-tools').querySelector(spanElement)
        }
        else {
            console.log("Error: accessCounter Function failed.")
        }
    }


    let slideshowButtons = document.querySelectorAll('.slideshow-button');
    slideshowButtons.forEach((button) => {
        button.addEventListener('click', () => {
            let slideshowSection = button.parentElement.dataset.slideshow;
            let slideshowContainer = document.querySelector(`#product-section-${slideshowSection}`);
            let productCards = slideshowContainer.querySelectorAll('.product-card');

            let currentSection = `product-section-${slideshowSection}`;
            if (button.classList.contains('prev-button')) {
                if (sectionLastElement[currentSection] <= (productCardsPerRow - 1)) {
                    sectionLastElement[currentSection] = productCards.length - 1
                }
                else {
                    sectionLastElement[currentSection]--
                }
            }
            else if (button.classList.contains('next-button')) {
                if (sectionLastElement[currentSection] === (productCards.length - 1)) {
                    sectionLastElement[currentSection] = productCardsPerRow - 1;
                }
                else {
                    sectionLastElement[currentSection]++
                }
            }
            else {
                console.log("Slideshow: Error occurred");
            }

            for (let i = 0; i < productCards.length; i++) {
                if ((i <= sectionLastElement[currentSection]) && (i >= (sectionLastElement[currentSection] - (productCardsPerRow - 1)))) {
                    productCards[i].classList.add('active');

                    if (i === sectionLastElement[currentSection]) {
                        productCards[i].style.marginRight = '0px'
                    }
                    else {
                        productCards[i].style.marginRight = `${marginSpacing}px`
                    }
                }
                else {
                    productCards[i].classList.remove('active');
                    productCards[i].style.marginRight = '0px'
                }
            }
        })
    })
    let slideIndex = 0;

    function showSlides() {
        const slides = document.querySelectorAll(".promotion-section");

        slides.forEach((slide, index) => {
            slide.classList.remove("active");
        });
        
        slideIndex++;
        if (slideIndex > slides.length) slideIndex = 1;

        slides[slideIndex - 1].classList.add("active");
        setTimeout(showSlides, 2000); 
    }

    showSlides();
})
