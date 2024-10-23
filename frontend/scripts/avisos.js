const prevButton = document.querySelector('.prev');
const nextButton = document.querySelector('.next');
const cardsContainer = document.querySelector('.cards-container');
const cards = document.querySelectorAll('.card');

let currentIndex = 1;

function updateCards() {
    cards.forEach((card, index) => {
        card.style.transform = 'scale(1)';
        card.style.zIndex = '1';
        if (index === currentIndex) {
            card.style.transform = 'scale(1.2)';
            card.style.zIndex = '2';
        }
    });
}

prevButton.addEventListener('click', () => {
    currentIndex = (currentIndex - 1 + cards.length) % cards.length;
    cardsContainer.scrollLeft -= cards[0].offsetWidth;
    updateCards();
});

nextButton.addEventListener('click', () => {
    currentIndex = (currentIndex + 1) % cards.length;
    cardsContainer.scrollLeft += cards[0].offsetWidth;
    updateCards();
});

updateCards();