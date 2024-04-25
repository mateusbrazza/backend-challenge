<h1 align="center">Backend-challenge</h1>

<br>

## :dart: Sobre ##

Este projeto consiste em uma aplicação que valida tokens JWT de acordo com critérios específicos, incluindo a verificação de reivindicações como Nome, Função e Semente. As principais funcionalidades incluem a validação de JWTs conforme regras predefinidas.

- Deve ser um JWT válido
- Deve conter apenas 3 reivindicações (Nome, Função e Semente)
- Um nome de reivindicação não pode ter caracteres de números
- A função de reivindicação deve conter apenas 1 dos três valores (Admin, Member e External)
- Uma reivindicação Seed deve ser um número primo.
- O tamanho máximo do nome da reivindicação é de 256 caracteres.


## ⭐: Tecnologias ##
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
#📑Documentação 

🟢 Swagger
Contrato da API.
http://ac0df1c7cb7f343ac8ccfe252428abb4-605364190.us-east-1.elb.amazonaws.com:8080/swagger-ui/index.html

Endpoint validateJwt
Este endpoint recebe um token JWT no cabeçalho de autorização e valida sua autenticidade.

Dica no https://www.javainuse.com/jwtgenerator você consegue criar novos token jwt passivel de analise
Utilize essa Key gd24Nrh3OcxZJjR5PVXZGHW46A4mR93s8MF7OEKdRPg=

### Caso 1:
Entrada:
```
eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiMyIsIk5hbWUiOiJSZWJlY2EgVmllaXJhIn0.SHIJRt3Jrqy3xyF_cinsFLzcrSyOWLrX0-RzpCM7qbA
```
Saida:
```
verdadeiro
```
Justificativa:
Abrindo o JWT, as informações contidas atendem a descrição:
```json
{
    "Role": "Admin",
    "Seed": "3",
    "Name": "Rebeca Vieira"
}
```
### Caso 2:
Entrada:
```
eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNCIsIk5hbWUiOiJSZWJlY2EgVmllaXJhIn0.4guGRF5wjoNBVeRzSqjARu_blQWwQQ8qdDLxDFSkZH8
```
Saida:
```
falso
```
Justificativa:
Abrindo o JWT, a Seed e um numero primo
```json
{
    "Role": "Admin",
    "Seed": "4",
    "Name": "Rebeca Vieira"
}
```

#🧠 Linha de pensamento:#

##Arquitetura escolhida MVC - MOdel View Control ##

🏁
Foi ultilizado o Terraform como base para provicionar todos os recursos dentro da AWS
Foi ultilizado um EC2,EKS e ELK todos os recursos são criados automaticamente via scirpt de CI/CD
Commit na master faz esse disparo atualizando os recursos caso modificado e fazendo o deploy.

Tambem foi usado o logstah e o Kibana para ajudar na monitoração posteriormente criando dash de monitoração
e consulta e armazenamento de logs.

Criaçoes foram feitas buscando uma modularidade atraves de modules.

Apear de ainda não esta em uso, templantes do HELM estão encaminhado para aplicação.

LINK APLICAÇÂO:http://ac0df1c7cb7f343ac8ccfe252428abb4-605364190.us-east-1.elb.amazonaws.com:8080/api/v1/validate

LINK KIBANA:http://a8d8cebf7dc5c4538865ab53c5e2ac35-943387975.us-east-1.elb.amazonaws.com:5601/app/logs/


## Descrição dos Diretórios ##

- **`backend-challenge/`**: É a pasta raiz do projeto.
- **`src/`**: Contém todo o código fonte.
- **`main/java/`**: Pasta padrão para código fonte Java em projetos Maven/Gradle.
- **`com/backend/jwt/`**: Pacote base onde estão organizados os subpacotes e classes Java.
  - **`controller/`**: Gerencia as requisições e respostas HTTP.
     - Ultilize  anotações para organizar e documentar nossa API, facilitando a manutenção e a geração automática de documentação via Swagger. Além disso,
       implementamos a validação de padrões com @Pattern para evitar o processamento de tokens inválidos, otimizando a performance e reforçando a segurança.

  - **`exception/`**: Contém as exceções personalizadas do projeto.
      - A ideia é personalizar as exceções para trazer maior rastreabilidade.

  - **`service/`**: Inclui a lógica de negócios, especificamente operações relacionadas ao JWT.
    A ideia foi deixar bem estruturado buscando uma separação de responsabilidade, validação e rastreabilidade de possiveis erros. Buscando facilitar no
    desenvolvimento de testes  acessando indiretamente os metodos que estão privados.

  - **`swagger/`**: Mantém as configurações para a documentação da API com Swagger.

  - **`util/`**: Fornece utilitários e ajudantes, como o `RoleValidator`.
     Realizei a validação dos roles através de uma linha de captura diretamente de um arquivo, configurado para facilitar futuras manutenções. Atualmente,
    os roles são obtidos de um arquivo .txt, mas a ideia  é evoluir para buscá-los de um S3 criptografado, visando garantir maior segurança



