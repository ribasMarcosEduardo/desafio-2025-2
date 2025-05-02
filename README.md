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
* **Estrutura do projeto;**
* **Histórico do GIT;**
* Build e execução da aplicação;
* Completude das funcionalidades;
* Qualidade de código (design pattern, manutenibilidade, clareza);
* Boas práticas de UI (Interface com o Usuário);
* **Sentido e coerência nas respostas aos questionamentos na entrevista de apresentação do desafio realizada pelo candidato.**
 
**OBS: Plágios tendem a ser desclassificados. Atenção com o uso excessivo de IA.**

**IMPORTANTE: Estamos buscando desenvolvedores que topam desafios, então mesmo não cumprindo todo os requisitos abaixo, seu esforço será avaliado.**

## DESAFIO 
Você terá que desenvolver a clássica aplicação para uma locadora de filmes em mídia física (oldschool não?). Trata-se de um backoffice para adição e manutenção de Filmes, Exemplares e Locações, os quais utilizam de APIs públicas para completude de suas informações.

Para o cadastro de Filmes você deverá utilizar algum endpoint (a sua escolha) disponível na [API da plataforma The Movie Database](https://developer.themoviedb.org/reference/intro/getting-started), para trazer dados de um filme aleatório. **Esta API exige chave de autenticação**, a documentação para criar/utilizar a mesma, bem como exemplos de aplicação encontram-se [AQUI](https://developer.themoviedb.org/docs/getting-started).

Para o cadastro de Locação você precisará utilizar a API [QRcode Generator](https://api.apgy.in/qr/documentation.html). Com ela você passará parâmetros e receberá uma imagem do QRcode, que deverá ser convertida em Base64 e armazenada em conjunto com o registro de Locação que estiver criando.

Também para Locação, você poderá selecionar até 3 filmes do seu catálogo, verificar se todos estes filmes selecionados possuem exemplares disponíveis e informar os dados do locador (não precisa necessariamente utilizar uma entidade "Pessoa", podem ser apenas informações literais).

Gestão de usuários e permissões não se faz necessário, mas proteger as páginas de sua aplicação com um usuário e senha sim. Dito isso, deve haver uma url pública de consulta na sua aplicação, onde ao passar um número de CPF, retorna se há alguma locação ativa com os dados (incluindo o QRcode).

A validação de dados é muito importante para a consistência das comunicações.

Relatórios não são obrigatórios, mas a presença dos mesmos tende a melhorar sua avaliação.

Se aplicar melhorias não previstas no desafio, favor destacá-las.

Em caso de dúvidas quanto a interpretação, você pode entrar em contato pelo email ti.dev@unoesc.edu.br.

## ESTRUTURA DO DESAFIO
Aqui uma sugestão de estrutura para você seguir, melhorias ou correções são bem-vindas.

**_Classe FILME_**
* "id" - int (auto-gerado)
* "ativo" - boolean
* "exemplares_disponiveis" - long

Deverá possuir as seguintes informações [VINDAS DA API](https://developer.themoviedb.org/reference/discover-movie):
* "titulo" - String (vindo da API)
* "resumo" - String (vindo da API)
* "pontuacao" - String (vindo da API)
* "lancamento" - Date (vindo da API)

**OBS:** Lembrar de passar o token de autenticação gerado como um Header. Há exemplos em diversas linguagens na documentação.

**OBS2:** Como a escolha do endpoint de filme ficou aberto, atenção com os parâmetros a serem enviados.

**_Classe EXEMPLAR_**
* "id" - int (auto-gerado)
* "filme" - Relacionamento em lista com a classe Filme
* "dataCadastro" - Date (data criação)
* "ativo" - boolean

**_Classe LOCACAO_**
* "id" - int (auto-gerado)
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

**Regras de Negócio**
1. A aplicação deve ser protegida, com tela de login (não é necessário uma entidade de usuário, pode ser fixo).
2. Ao adicionar um novo **FILME** as informações vindas da API devem ser preenchidas automaticamente e não poderão ser alteradas pelo usuário, porém o mesmo deverá informar os parâmetros faltantes manualmente.
3. No caso de edição de um **FILME**, o usuário não poderá alterar as informações vindas da API.
4. Deve possibilitar o relacionamento entre UM **FILME** e VÁRIOS **EXEMPLAR**
5. Ao adicionar um novo **EXEMPLAR** o campo **FILME.exemplares_disponiveis** deve ser atualizado com o total de **EXEMPLAR** associados.
6. Apenas deve permitir realizar a associação anterior, se o **FILME.ativo** for igual a TRUE.
7. Ao alterar um registro de **EXEMPLAR.ativo** para FALSE, o campo **FILME.exemplares_disponiveis** deve ser atualizado, subtraindo o total.
8. Apenas deve ser possível inativar um registro de **EXEMPLAR** se não existir algum registro de **LOCACAO** pendente (dataDevolvido nulo).
9. Deve possibilitar o relacionamento de até três **EXEMPLAR** diferentes com UMA MESMA **LOCACAO** por vez.
10. Apenas deve realizar a associação anterior, se o **EXEMPLAR.ativo** for igual a TRUE.
11. Ao adicionar uma nova **LOCACAO**, o usuário deverá preencher as informações manuais e ao salvar a API de QRcode deverá ser consultada gerar a imagem com os parâmetros/valores passados em formato JSON (cpf, telefone, dataLocacao, dataDevolucao).
12. A imagem do QRcode gerada deverá ser persistida em formato Base64 junto com o registro de **LOCACAO** (campo qrCode).
13. Ao persistir uma **LOCACAO** o campo **FILME.exemplares_disponiveis** deve ser atualizado, subtraindo o total.
14. Ao devolver uma **LOCACAO** o campo **FILME.exemplares_disponiveis** deve ser atualizado, somando o total.
15. Não deve ser possível excluir qualquer registro que possua qualquer associação.
16. Não deve ser possível inserir qualquer registro idêntico a outro já existente (desconsiderando seu id).
17. O sistema deverá permitir buscar todos os registro de **LOCACAO** armazenados em sua base e possibilitar filtrar por **LOCACAO.cpf, LOCACAO.email, LOCACAO.nome, FILME**, agrupando por DEVOLVIDAS e PENDENTES (com ou sem dataDevolvido).
18. Deve existir um link NÃO PROTEGIDO para consultar se há **LOCACAO** PENDENTE para um **LOCACAO.cpf**.
19. Todos os campos que necessitem de máscara, devem a ter implementada.

**IMPORTANTE: Lembrando que a não completude de todos os pontos, não necessariamente é fator reprovatório, seu esforço será avaliado.**

