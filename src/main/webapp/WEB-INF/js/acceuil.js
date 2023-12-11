
var act = "";
var menu = document.querySelector('.icon-menu');
menu.addEventListener('click',function(){
    document.querySelector('.menu').classList.toggle('liste');
});

document.getElementById("header-text").textContent = "Mikalo";
var xhr = new XMLHttpRequest();
xhr.open('GET', "navbar", true);
xhr.setRequestHeader('Content-Type', '*');
xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
        document.getElementById('menu').innerHTML = xhr.responseText;
        document.getElementById(act).className = "active";
    }
};
xhr.send();

function active(a){
    act = a.toString();
}
var divs = document.querySelectorAll('.grid-item');
var nombreDivs = divs.length;
if(nombreDivs == 2){
    document.querySelector('.grid-container-1').classList.toggle('diminue'); 
}else if(nombreDivs == 1){
    document.querySelector('.grid-container-1').classList.toggle('diminue1');
}
const carousel = document.querySelector('.carousel-container');
const slides = carousel.querySelectorAll('.slide');
const prevButton = carousel.querySelector('.prev');
const nextButton = carousel.querySelector('.next');
let currentIndex = 0;

function showSlide(index) {
    slides[currentIndex].classList.remove('active');
    slides[index].classList.add('active');
    currentIndex = index;
}

function nextSlide() {
    let nextIndex = currentIndex + 1;
    if (nextIndex === slides.length) {
        nextIndex = 0;
    }
    showSlide(nextIndex);
}

function prevSlide() {
    let prevIndex = currentIndex - 1;
    if (prevIndex < 0) {
        prevIndex = slides.length - 1;
    }
    showSlide(prevIndex);
}

nextButton.addEventListener('click', nextSlide);
prevButton.addEventListener('click', prevSlide);