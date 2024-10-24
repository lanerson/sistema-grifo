
document.addEventListener("DOMContentLoaded", function() {
  fetch('http://localhost:8080/publications/all')
  .then(response => {
      if (!response.ok) {
          throw new Error('Erro na requisição: ' + response.status);
      }
      return response.json();
  })
  .then(data => data._embedded.publicationList)
  .then(data => {    
      const carouselItemsContainer = document.getElementById('carousel-items');
      carouselItemsContainer.innerHTML = ''; // Limpa itens existentes
      const indicatorsContainer = document.querySelector('.carousel-indicators');
      indicatorsContainer.innerHTML = ''; // Limpa os indicadores existentes

      data.forEach((item, index) => {
        console.log(item)
          // Cria o item do carrossel
          const carouselItem = document.createElement('div');
          carouselItem.className = 'carousel-item' + (index === 0 ? ' active' : ''); // Define o primeiro item como ativo
          carouselItem.style.position = 'relative';

          // Adiciona a imagem
          const img = document.createElement('img');
          img.src = `/images/${item.image}`; // Caminho da imagem
          img.alt = item.title;          

          // Adiciona o gradiente
          const gradientDiv = document.createElement('div');
          gradientDiv.className = 'image-gradient';

          // Adiciona o container e a legenda
          const container = document.createElement('div');
          container.className = 'container';

          const caption = document.createElement('div');
          caption.className = 'carousel-caption text-start';
          const title = document.createElement('h1');
          title.textContent = item.title;
          const description = document.createElement('p');
          description.className = 'opacity-75';
          description.textContent = item.description;

          // Monta a estrutura
          caption.appendChild(title);
          caption.appendChild(description);
          container.appendChild(caption);
          carouselItem.appendChild(img);
          carouselItem.appendChild(gradientDiv);
          carouselItem.appendChild(container);
          carouselItemsContainer.appendChild(carouselItem);

          // Adiciona o indicador
          const indicator = document.createElement('button');
          indicator.type = 'button';
          indicator.setAttribute('data-bs-target', '#myCarousel');
          indicator.setAttribute('data-bs-slide-to', index);
          indicator.className = index === 0 ? 'active' : '';
          indicator.setAttribute('aria-label', `Slide ${index + 1}`);
          if (index === 0) {
              indicator.setAttribute('aria-current', 'true');
          }
          indicatorsContainer.appendChild(indicator);
      });
  })
  .catch(error => {
      console.error('Erro:', error);
  })
});
