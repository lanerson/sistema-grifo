fetch('http://localhost:8080/products/all', { method: 'GET' })
  .then(response => {
    if (!response.ok) {
      throw new Error('Erro na requisição: ' + response.status);
    }
    return response.json(); // Converte a resposta em JSON
  })
  .then(data => {
    const products = data._embedded.productList; 
    const productList = document.getElementById('product-list');
    console.log(products)
    // Limpa a lista antes de adicionar novos produtos
    productList.innerHTML = '';

    products.forEach(product => {
      // Criação da estrutura do produto
      const productDiv = document.createElement('div');
      productDiv.className = 'product';

      // Adiciona a imagem
      const img = document.createElement('img');
      img.src = `/images/${product.image}`; // Assumindo que você tem um campo 'imageName'
      img.alt = product.name;

      // Adiciona o nome do produto
      const nameP = document.createElement('p');
      nameP.textContent = product.name;

      // Adiciona o preço do produto
      const priceP = document.createElement('p');
      priceP.textContent = `R$${product.price.toFixed(2)}`; // Ajusta o formato do preço

      // Adiciona o botão de encomendar
      const button = document.createElement('button');
      button.type = 'button';
      button.className = 'button-encomendar encomendarBtn';
      button.textContent = 'Encomendar';
      button.onclick = function() {
        // Ação ao clicar no botão
        alert(`Você encomendou: ${product.name}`);
      };

      // Adiciona todos os elementos à div do produto
      productDiv.appendChild(img);
      productDiv.appendChild(nameP);
      productDiv.appendChild(priceP);
      productDiv.appendChild(button);

      // Adiciona a div do produto à lista de produtos
      productList.appendChild(productDiv);
    });
  })
  .catch(error => {
    console.error('Erro:', error); // Trata erros
  });
