<h1 align="center">Backend-challenge</h1>

<p align="center">
  <a href="#dart-sobre">Sobre</a> &#xa0; | &#xa0; 
  <a href="#sparkles-features">Features</a> &#xa0; | &#xa0;
  <a href="#rocket-tecnologias">Tecnologias</a> &#xa0; | &#xa0;
  <a href="#white_check_mark-requerimentos">Requerimentos</a> &#xa0; | &#xa0;
  <a href="#checkered_flag-começando">Começando</a> &#xa0; | &#xa0;
</p>
<br>

## :dart: Sobre ##

Este projeto consiste em uma aplicação que valida tokens JWT de acordo com critérios específicos, incluindo a verificação de reivindicações como Nome, Função e Semente. As principais funcionalidades incluem a validação de JWTs conforme regras predefinidas.

- Deve ser um JWT válido
- Deve conter apenas 3 reivindicações (Nome, Função e Semente)
- Um nome de reivindicação não pode ter caracteres de números
- A função de reivindicação deve conter apenas 1 dos três valores (Admin, Member e External)
- Uma reivindicação Seed deve ser um número primo.
- O tamanho máximo do nome da reivindicação é de 256 caracteres.


⭐: Tecnologias ##
- [java - 11](https://www.java.com/pt-BR/)
- [Spring-boot](https://spring.io/)
- [EC2/EKS/ELK](https://aws.amazon.com/)
- [Kibana](https://www.elastic.co/pt/kibana)
- [TerraForm](https://www.terraform.io/)
- [Docker](https://www.docker.com/)
- [Git/GitActions](https://git-scm.com)


## :white_check_mark: Requerimentos ##

Antes de começar :checkered_flag:, você precisar ter [Git](https://git-scm.com) e [Node](https://nodejs.org/en/) instalados.

## :checkered_flag: Execução local##

```bash
# Clonar o projeto
$ git clone https://github.com/mateusbrazza/backend-challenge.git

# Acessar projeto
$ cd backend-challenge

# Build da imagem docker
$ docker build -t backend-challenge:latest .

# Executar o Contêiner Docker
$ docker run -p 8080:8080 backend-challenge:latest

# O server irá iniciar em <http://localhost:8080>
```
