console.log("teste")
fetch('http://localhost:8080/products/all')
.then(response => {
    if (!response.ok) {
      throw new Error('Erro na requisição: ' + response.status);
    }
    return response.json(); // Converte a resposta em JSON
  })
  .then(data => {
    console.log(data._embedded); // Manipula os dados recebidos
  })
  .catch(error => {
    console.error('Erro:', error); // Trata erros
  });