# Desafio TI DEV UNOESC 2025/2
CORRESPONDENTE AO EDITAL N. 21/UNOESC-R/2025

Desafio Programador Fullstack Unoesc

Este é o nosso desafio para a vaga de programador Fullstack na Unoesc. Serão testadas as habilidades e qualidade de código ao transformar requisitos limitados em uma aplicação web.

**FAÇA O FORK DESTE REPOSITÓRIO E IMPLEMENTE O DESAFIO. MANTENHA PÚBLICO, POIS QUEREMOS ACOMPANHAR SEUS COMMITS**

Ao concluir o desafio, lembre de enviar um email para recrutamentorh.jba@unoesc.edu.br, ti.coord@unoesc.edu.br e ti.dev@unoesc.edu.br, com seu repositório. Lembre de incluir a documentação para que possamos rodar sua aplicação.

## TECNOLOGIAS OBRIGATÓRIAS
* O escopo de linguagem é aberto, mas a utilização da Orientação a Objetos é um importante critério para a avaliação.
* MySQL 5.7.X+ OU Postgress;
* GIT;

## AVALIAÇÃO
O código será avaliado de acordo com os seguinte critérios:

* Documentação do processo necessário para rodar a aplicação;
* Estrutura do projeto;
* Histórico do GIT;
* Build e execução da aplicação;
* Completude das funcionalidades;
* Qualidade de código (design pattern, manutenibilidade, clareza);
* Boas práticas de UI (Interface com o Usuário);
* Sentido e coerência nas respostas aos questionamentos na entrevista de apresentação do desafio realizada pelo candidato. OBS: Plágios tendem a ser desclassificados. Atenção com o uso excessivo de IA.

**IMPORTANTE: Estamos buscando desenvolvedores que topam desafios, então mesmo não cumprindo todo os requisitos abaixo, seu esforço será avaliado.**

## DESAFIO 
Você terá que desenvolver a clássica aplicação para uma locadora de filmes em mídia física (oldschool não?). Trata-se de um backoffice para adição e manutenção de Filmes, Exemplares e Locações, os quais utilizam de APIs públicas para completude de suas informações.

Para o cadastro de Filmes você deverá utilizar o endpoint *Discover > Movie* disponível na [API da plataforma The Movie Database](https://developer.themoviedb.org/reference/discover-movie), para trazer dados de um filme aleatório. **Esta API exige chave de autenticação**, a documentação para criar/utilizar a mesma, bem como exemplos de aplicação encontram-se [AQUI](https://developer.themoviedb.org/docs/getting-started).

Para o cadastro de Locação você precisará utilizar a API *QRcode Generator*, disponível [AQUI](https://api.apgy.in/qr/documentation.html). Com ela você passará parâmetros e receberá uma imagem do QRcode, que deverá ser convertida em Base64 e armazenada em conjunto com o registro de Locação que estiver criando.
Também para Locação, você poderá selecionar até 3 filmes do seu catálogo, verificar se todos estes filmes selecionados possuem exemplares disponíveis e informar os dados do locador (não precisa necessariamente utilizar uma entidade "Pessoa", podem ser apenas informações literais).

Gestão de usuários e permissões não se faz necessário, mas proteger as páginas de sua aplicação com um usuário e senha sim. Dito isso, deve haver uma url pública de consulta na sua aplicação, onde ao passar um número de CPF, retorna se há alguma locação ativa.

A validação de dados é muito importante para a consistência das comunicações.

Relatórios não são obrigatórios, mas a presença dos mesmos tende a melhorar sua avaliação.

Se aplicar melhorias não previstas no desafio, favor destacá-las.

Em caso de dúvidas quanto a interpretação, você pode entrar em contato pelo email ti.dev@unoesc.edu.br.

## ESTRUTURA DO DESAFIO
Aqui uma sugestão de estrutura para você seguir, melhorias ou correções são bem-vindas.

**_Classe FILME_**
* "id" - int
* "ativo" - boolean
* "exemplares_disponiveis" - long

Deverá possuir as seguintes informações [VINDAS DA API](https://developer.themoviedb.org/reference/discover-movie):
* "titulo" - String (valor do campo "original_title" vindo da API)
* "resumo" - String (valor do campo "overview" vindo da API)
* "pontuacao" - String (valor do campo "vote_average" vindo da API)
* "adulto" - boolean (valor do campo "adult" vindo da API)
* "lancamento" - Date (valor do campo "release_date" vindo da API)

**OBS:** 

**_Classe EXEMPLAR_**
* "id" - int
* "filme" - Relacionamento em lista com a classe Filme
* "dataCadastro" - Date (data criação)
* "ativo" - boolean

**_Classe LOCACAO_**
* "id" - int
* "exemplares" - Relacionamento em lista com a classe Exemplar
* "nome" - String
* "cpf" - String
* "email" - String
* "telefone" - String
* "dataLocacao" - Date (data criação)
* "dataDevolucao" - Date (data prevista de devolução)
* "dataDevolvido" - Date (data efetiva de devolução)

Deverá possuir as seguintes informações [VINDAS DA API](https://api.apgy.in/qr/documentation.html):
* "qrCode" - String

**OBS:** Os parâmetros que devem ser passados para a APi, em *formato JSON* são *cpf, telefone, dataLocacao, dataDevolucao*

