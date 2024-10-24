const popupForm = document.getElementById("popupForm");
const closeBtn = document.getElementById("closeBtn");
const encomendarForm = document.getElementById("orderForm");
let productSelected=""
closeBtn.addEventListener("click", () => {
  popupForm.style.display = "none";
});

// Fechar o pop-up se clicar fora do formulário
window.addEventListener("click", (event) => {
  if (event.target === popupForm) {
      popupForm.style.display = "none";
  }
});
orderForm.addEventListener("submit", function(event) {
  event.preventDefault(); // Impede o envio padrão do formulário  
  // Captura os dados do formulário
  const formData = new FormData(orderForm);
  sendWhatsAppMessage(formData);
  
});


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
        productSelected = product.name
        popupForm.style.display = "flex";
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

function sendWhatsAppMessage(formData) {  
  var message = `Olá, meu nome é ${formData.get('nome')}.\n` + 
                    `Telefone: ${formData.get('email')}.\n` + 
                    `Sou sócio: ${formData.get('socio') === 'sim' ? 'Sim' : 'Não'}.\n` + 
                    `Gostaria de fazer uma encomenda deste produto: \n` +
                    productSelected+`\n`+
                    `Tamanho: ${formData.get('tamanho')}`

  console.log(message)
  var whatsappLink = `https://api.whatsapp.com/send?phone=+558591893009&text=${encodeURIComponent(message)}`;

  window.open(whatsappLink, '_blank'); // Abre o WhatsApp em uma nova aba
}
