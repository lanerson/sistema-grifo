const encomendarBtns = document.querySelectorAll(".encomendarBtn");
        const popupForm = document.getElementById("popupForm");
        const closeBtn = document.getElementById("closeBtn");

        // Adiciona um evento de clique a cada botão "ENCOMENDAR"
        encomendarBtns.forEach(button => {
            button.addEventListener("click", () => {
                popupForm.style.display = "flex";
            });
        });

        // Fechar o formulário ao clicar no botão "X"
        closeBtn.addEventListener("click", () => {
            popupForm.style.display = "none";
        });

        // Fechar o pop-up se clicar fora do formulário
        window.addEventListener("click", (event) => {
            if (event.target === popupForm) {
                popupForm.style.display = "none";
            }
        });