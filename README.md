<h1 align="center" id="title">Hello People</h1>

<h3 align="center">Um "Hello" em Diversos Idiomas para o Usuario</h3>

<p align="center" id="icons">
  <a href="#icons">
    <img alt="Linguagens de Programação Utilizadas" src="https://img.shields.io/github/languages/count/guilhermePalma/Hello_People?color=2304D361">
  </a>

  <a href="https://github.com/GuilhermeCallegari/Maquiagem">
    <img alt="Tamanho do Repositorio" src="https://img.shields.io/github/repo-size/guilhermePalma/Hello_People">
  </a>

  <a href="https://github.com/GuilhermeCallegari/Maquiagem/commits/main">
    <img alt="Utlimo Commit" src="https://img.shields.io/github/last-commit/guilhermePalma/Hello_People">
  </a>

  <a href="LICENSE">
   <img alt="Licença do Projeto" src="https://img.shields.io/github/license/guilhermePalma/Hello_People">
  </a>
</p>

<h4 align="center">🚀 Finalizado 🚀</h4>

<!-- TODO: Video do APP Funcionando -->

Tabela de Conteúdos
=================
- [Sobre o projeto](#-sobre-o-projeto)
  - [Funcionalidades](#funcionalidades)
  - [Como executar o projeto](#-como-executar-o-projeto)
    - [Pré-requisitos](#pré-requisitos)
    - [Baixando o Projeto](#-baixando-o-projeto)
    - [Instalando o APP](#instalando-o-app)
  - [Tecnologias e Informações](#-tecnologias-e-informações)
    - [Estrutura](#estrutura)
    - [Explicação do APP](#explicação-do-app)
    - [Pontos Desenvolvidos](#pontos-desenvolvidos)
  - [Contribuidores](#-contribuidores)
  - [Referencias](#referencias)
  - [Como contribuir no projeto](#-como-contribuir-no-projeto)

# 💻 Sobre o projeto

**Hello People** - Projeto Desenvolvido para Obter um "Olá" no Idioma Local do Usuario (Pelo seu IP) ou em um dos diversos Idiomas Disponiveis. Tambem será exibido **Informações Publicas** sobre o IP do Usuario.

:books: Inicialmente, esse APP realiza uma busca do IP do Usuario, obtendo a Cidade, Estado, País e outros **Dados Publicos** desse IP. Após essa busca, por padrão, é obtido um "Ola" no Idioma Local do Usuario.

Esse Projeto utilizou 3 APIs no seu Desenvolvimento, uma Biblioteca (Material Design) de Widgets e uma Organização dos Diretorios, de forma que os Itens semelhantes estivessem no mesmo Escopo. Para mais Informações, acesse o Item [Tecnologias](#-tecnologias), e veja os **Pontos de Aprendizado**

> Obs: Esse APP **NÃO ARMAZENA** nenhum Dado de Uso (Nome, Senha, IP, Dados do Dispositivo, etc). **Nenhum** dado inserido ou obtido no APP é **salvo** de forma externa


## Funcionalidades
- [X] **Login**
  - [X] Inputs de Nome e Senha
  - [X] Ocultação da Senha
  - [X] Validação no Nome e Senha (Mensagem e Erro e Mudança de Cor)
  - [X] Botão Login e Logout
  - [X] Botão Logout Limpando Reiniciando os Inputs
- [X] **"Hello"**
  - [X] Ao Completar um novo Login: `<hello-in-native-language> <user-name> you have successfully logged in!`
  - [X] Ao Realizar um Login Novamente: `Have a great day <user-name>!`
- [X] **Extras**
  - [X] Usuario Escolher o Idioma
  - [X] Informações Adicionais (Endereço de IP, Cidade, Região, Nome do País e Fuso Horário)


## 🚀 Como executar o projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
- [Git](https://git-scm.com) → Atualizações e Versionamento no Codigo
- [Android Studio](https://developer.android.com/studio/) → Editor da Google voltado ao Desenvolvimento Android

### 📥 Baixando o Projeto

Execute os Comandos abaixo no **[Git Bash](https://git-scm.com)**, dentro da sua pasta escolhida. Dessa Forma, é possivel acessar o Projeto e seus Diretorios com mais Facilidade :smile:

```bash

# Clone este repositório
https://github.com/GuilhermePalma/Hello_People.git

# Acesse a pasta do projeto no terminal/cmd
cd Hello_People

# Abra o Android Studio
```

### Instalando o APP

Caso deseje apenas Instalar o APP para poder interagir com ele, acesse a aba [Releases](https://github.com/GuilhermePalma/Hello_People/releases).

Após acessar, Selecione a Versão mais Recente, deslize a tela até a parte inferior e baixe o arquivo anexado como `app-release.apk`.

Pegue seu dispositivo mobile, abra o aplicativo `Configurações`, selecione o Item `Segurnaça` e ative o Item `Permitir Fontes Desconhecidas`

Com um Cabo USB, conecte seu dispositivo mobile e mova o arquivo **`app-release.apk`** para alguma pasta do Dispositivo. Em seu aparelho, utilize algum aplicativo para acessar as pastas (como por exemplo, o app `Meus Arquivos`), navegue até o local onde o arquivo **`app-release.apk`** se encontrea e clique nele para começar a **Instalação** no Aparelho.

Durante a Instalação, seu dispositivo pode exibir alguns avisos informando que o APP vem de um Fonte Desconhecida e fora da PlayStore. Entretanto, o APP não desempenha nenhum risco ao seu dispositivo, por isso clique em "Instalar" ou "Continuar mesmo assim" ou "Confiar nesse Aplicativo"

> Após a Instalação do APP, é recomendado que desabilite a opção `"Confiar em Fontes Desconecidas"` (3° Paragrafo)

## 🛠 Tecnologias e Informações

Para a Construção desse APP, foi utilizada a Linguagem **[Java](https://developer.android.com/docs)**, em conjunto com a IDE da JetBrains, Android Studio.

Para o Versionamento do APP, foi utilizado o [Git](https://git-scm.com) junto com o [GitHub](https://github.com). Para facilitar a manipulação do Git, foi utilizada a Interface Grafica integrada no Android Studio.

Para o Desenvolvimento do APP, foram utilizados os seguintes recursos:
- API: **[Check IP - Obtem o IP do Usuario](http://checkip.amazonaws.com)**
- API: **[IP API - Obtem os Detalhes do IP](http://ip-api.com/json)**
- API: **[Fourton Fish (Project: Hello, Salut!) - Obtem o "Hello" em Diversos Idiomas](https://fourtonfish.com/project/hellosalut-api/)**
- Widgets: **[Material Design - Documentação e Widgets](https://material.io/components/)**

### Estrutura

Este projeto é divido nas seguintes partes:

1. [Layout das Telas](app/src/main/res/layout/)
2. [Configurações das Telas](app/src/main/java/com/example/hellopeople/activities)
4. [Classes das Entidades](app/src/main/java/com/example/hellopeople/entity)
5. [Classes de Funcionalidades](app/src/main/java/com/example/hellopeople/utils)

> É possivel encontrar outros arquivos "Fora" dessa organização. As partes descritas acima são apenas generealização da organização do Projeto

### Explicação do APP

O APP **Hello** é um dos Projetos da Coleção [APP Ideas](https://github.com/florinpop17/app-ideas), que se trata de um repositorio OpenSource (License MIT) com sugestões de Projetos. Essas Sugestões listam os Requisitos (User Stories) que o projeto deve abranger e as possiveis implementações extras. [Clique aqui](https://github.com/florinpop17/app-ideas/blob/master/Projects/1-Beginner/Hello-App.md) para ver os dados desse projeto.

Nesse APP foi desenvolvido um **Formulario** que o Usuario insere o seu **Nome** e uma **Senha** e, caso deseje, pode escolher o **Idioma** da mensagem do "Hello".

Durante a Execução do APP, é utilizado **APIs** em requisições HTTP. Essas APIs tem como o Objetivo obter o "Hello" nos diversos Idioams e os Detalhes do IP. Essas APIs são:
- **[Check IP - Obtem o IP do Usuario](http://checkip.amazonaws.com)**
- **[IP API - Obtem os Detalhes do IP](http://ip-api.com/json)**
- **[Fourton Fish (Project: Hello, Salut!) - Obtem o "Hello" em Diversos Idiomas](https://fourtonfish.com/project/hellosalut-api/)**

Após clicar no botão **Login**, é feita uma Verificação nos Dados Inseridos. Caso haja algum erro, informa ao usuario. Se não, os Dados são salvos em **SharedPreferences** e inicia-se uma nova Tela exibindo o **"Hello"**. Por padrão, o "Hello" é exibido em um idioma detectado de forma automatica pelo IP do Usuario, entretanto, caso o usuario tenha selecionado algum Idioma especifico, o "Hello" será obtido nesse Idioma.

Por se tratar de um projeto mais simples, os dados são salvos de Forma Local em uma SharedPreferences - tendo inclusive, uma classe no APP somente para centraliza-las e controla-las. Dessa forma, uma vez que seja limpo os Dados do APP ou ocorra a Desinstalação do APP, os dados serão perdidos.

### Pontos Desenvolvidos

- [X] Divisão da Responsabilidade dos Itens do Projeto, seguindo o conceito da POO (Programação Orientada a Objetos)
- [X] Utilização da SharedPreferences
  - [X] Utilização de uma Classe que centralizava as Operações da SharedPreferences. Mantendo uma maior organização e facilidade para a Manutenabilidade do Sistema
- [X] Chamadas Assincronas
  - [X] Utilização do `ExecutorService`, `Callable` e `Future` para a criação e execução de uma nova `Thread` em `Background`
  - [X] Exibição dos Resultados no Layout por meio do `runUiThread`
  - [X] Manipulação de Variaveis
- [X] Requisições HTTP
  - [X] Utilização do Metodo GET
  - [X] Verificação do `Status Code` da Requisição
  - [X] Formação da URL por meio da Classe `Uri`
- [X] Serialização de JSON
  - [X] Verificação de Itens Nulos
  - [X] Normalização de Caracteres HTML


## 👨‍💻 Contribuidores

💜 Desenvolvedores que contruiram o Aplicativo :)

<table>
  <tr>
    <td align="center"><a href="https://github.com/guilhermepalma"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/54846154?v=4" width="100px;" alt=""/><br /><sub><b>Guilherme Palma</b></sub></a><br /><a href="https://github.com/guilhermepalma" title="Github">🚀</a></td>
  </tr>
</table>


## Referencias
[Florinpop17: APP-Ideas](https://github.com/florinpop17/app-ideas) → [Projeto "Hello"](https://github.com/florinpop17/app-ideas/blob/master/Projects/1-Beginner/Hello-App.md)


## 💪 Como contribuir no projeto

1. Faça um **fork** do projeto.
2. Crie uma **nova branch** com as suas alterações: `git checkout -b my-feature`
3. Realize e Salve suas alterações, colocando uma Mensagem Contando o que Você Fez: `git commit -m "Feature: Update XXX"`
4. Envie as suas Alterações: `git push origin my-feature`
5. Entre no **seu Branch** no GitHub e clique em `Contribute` e em Seguida em `Open Pull Request`
6. Informe em `base` **o Branch `main` desse Repositorio** e em `compare` o **seu Branch de Alterações**
7. Insira um **Titulo** e escreva um **Mensagem** indicando o que foi feito nessa sua **Branch**


---
