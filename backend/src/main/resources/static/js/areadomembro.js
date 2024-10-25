const formPublication = document.getElementById("formPublication")
const formProduct = document.getElementById("formProduct")

const selectPublication = document.getElementById("selectPublication")
const selectProduct = document.getElementById("selectProduct")

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

fetch("/publications/all")
.then( response => response.json())
.then(data => {    
    const items = data._embedded.publicationList    
    items.forEach(element => {
        let newoption = document.createElement("option")
        newoption.value = element.id;
        newoption.text = `${element.id} => ${element.title}`
        selectPublication.appendChild(newoption)
    });
})

fetch("/products/all")
.then( response => response.json())
.then(data => {
    
    const items = data._embedded.productList
    
    items.forEach(element => {
        let newoption = document.createElement("option")
        newoption.value = element.id;
        newoption.text = `${element.id} => ${element.name}`;
        selectProduct.appendChild(newoption)
    });
})

const delPublication = document.getElementById("delPublication")

delPublication.addEventListener("click", () => {
    
    fetch(`/publications/delete/${selectPublication.value}`,{
        method:"DELETE"
    })
    .then ( () => {
        alert("Publicação deletada com sucesso")
        location.reload();
    })
    .catch(error => {
        alert("Erro ao deletar, verificar o Log de eventos")
        console.log("Erro ao deletar", error)
    })
})

const delProduct = document.getElementById("delProduct")

delProduct.addEventListener("click", () => {
    
    fetch(`/products/delete/${selectProduct.value}`,{
        method:"DELETE"
    })
    .then ( () => {
        alert("Produto deletado com sucesso")
        location.reload();
    })
    .catch(error => {
        alert("Erro ao deletar, verificar o Log de eventos")
        console.log("Erro ao deletar", error)
    })
})