const formPublication = document.getElementById("formPublication")
const formProduct = document.getElementById("formProduct")

formPublication.addEventListener("submit", (event) => {
    event.preventDefault()
    console.log("tá enviando publi")

    const data = new FormData(formPublication)

    fetch("/publications/create", {
        method:"POST",
        body: data
    })
    .then(response => response.json())
    .then(data => {
        alert("Publicação criada com sucesso", data)
    })
    .catch((error) => alert("Erro", error))
    
});


formProduct.addEventListener("submit", (event) => {
    event.preventDefault()
    console.log("tá enviando product")
    const data = new FormData(formProduct)

    fetch("/products/create", {
        method:"POST",
        body: data
    })
    .then(response => response.json())
    .then(data => {
        alert("Produto criado com sucesso", data)
    })
    .catch((error) => alert("Erro", error))
});