Aqui está uma versão aprimorada do seu README.md, com algumas melhorias no formato, clareza e organização. Ajustei a estrutura para torná-la mais legível e coesa.
Sistema Grifo - Gerenciamento de Artigos para Grêmio Estudantil

Este sistema foi desenvolvido como parte da cadeira de Laboratório de Programação e visa facilitar o gerenciamento de artigos disponibilizados pelo grêmio estudantil para os alunos.
Features

    Estoque da Lojinha de Artigos: Gerenciamento de itens disponíveis para os alunos.
    Sistema de Solicitação de Encomenda por E-mail: Permite que os alunos solicitem artigos via e-mail.
    Quadro de Avisos: Área para anunciar eventos e informativos.

Configurações para Testes

Este guia explica como configurar o projeto para testes locais, incluindo instruções para o backend, frontend e banco de dados.
Backend
1. Clonar o repositório

Clone o repositório para sua máquina local:

git clone https://github.com/lanerson/sistema-grifo.git

2. Instalar o Banco de Dados

    Para Linux:

    Atualize os pacotes e instale o MySQL Server:

    sudo apt update
    sudo apt install mysql-server

    Para Windows:

    Use o XAMPP ou outro ambiente local para configurar o banco de dados MySQL.

3. Configurar o Arquivo application.properties

Crie o arquivo application.properties dentro do diretório src/main/resources e adicione o seguinte conteúdo:

spring.application.name=backend

# Diretório para upload de imagens
file.upload-dir=.../backend/src/main/resources/static/images

# Configurações de limite de tamanho de arquivo
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=15MB

# Configurações de conexão com o banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/TESTE_DB
spring.datasource.username=<nome_de_usuario>
spring.datasource.password=<senha>

# Dialeto do Hibernate para MySQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# DDL auto: update, validate, create, create-drop
spring.jpa.hibernate.ddl-auto=update

spring.main.allow-bean-definition-overriding=true

    Importante: Lembre-se de substituir <nome_de_usuario> e <senha> pelas credenciais corretas do seu banco de dados.

4. Build e Execução do Backend

    Para Linux:

    Execute os comandos abaixo para compilar e rodar o backend:

./mvnw clean install
./mvnw spring-boot:run

Para Windows:

Execute os seguintes comandos no terminal:

    .\mvnw.cmd clean install
    .\mvnw.cmd spring-boot:run

Frontend (Android)
1. Configurar o Android Studio

Certifique-se de estar utilizando a versão mais recente do Android Studio.
2. Configurar o Backend no Android

No arquivo Constantes.java, localizado em /mobile/app/src/main/java/com/example/navigationapplication/Constantes.java, altere o valor da variável BASE_URL para o IP ou endereço onde o backend está sendo executado. Isso permitirá que o aplicativo Android se conecte corretamente ao servidor.

public static final String BASE_URL = "http://<IP_do_backend>:8080";
