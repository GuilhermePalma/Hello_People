<h1 align="center" id="title">Hello People</h1>

<h3 align="center">Um "Hello" na Linguagem Local de Acordo com o IP do Usuario</h3>

<p align="center" id="icons">
  <a href="#icons">
    <img alt="GitHub language count" src="https://img.shields.io/github/languages/count/guilhermePalma/Hello_People?color=2304D361">
  </a>
	
  <a href="https://github.com/GuilhermeCallegari/Maquiagem">
    <img alt="Repository size" src="https://img.shields.io/github/repo-size/guilhermePalma/Hello_People">
  </a>
	
  <a href="https://github.com/GuilhermeCallegari/Maquiagem/commits/main">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/guilhermePalma/Hello_People">
  </a>
	
  <a href="LICENSE">
   <img alt="License" src="https://img.shields.io/github/license/guilhermePalma/Hello_People">
  </a>
</p>

<h4 align="center">🚀 Finalizado 🚀</h4>


Tabela de conteúdos
=================
<!--ts-->
 * [Sobre o projeto](#-sobre-o-projeto)
   * [Funcionalidades](#funcionalidades)
   * [Layout](#-layout)
     * [Mobile](#mobile)
   * [Como executar o projeto](#-como-executar-o-projeto)
     * [Pré-requisitos](#pré-requisitos)
   * [Tecnologias](#-tecnologias)
   * [Contribuidores](#-contribuidores)
   * [Creditos](#creditos)
   * [Como contribuir no projeto](#-como-contribuir-no-projeto)
<!--te-->


# 💻 Sobre o projeto

**Hello People** - Projeto desenvolvido para utilizar diferentes APIs para obter informações do IP e a partir dele, obter o texto "Hello" na Linguagem Local 

:books: Esse aplicativo busca dados em uma API de IP, recuperando o IP, Cidade, Região, Nome do País e Fuso Horário

O Projeto utilizou 3 APIs. Para ver sua documentação, consulte o Item [Tecnologias](#-tecnologias)


## Funcionalidades
- [X] **Login**
  - [X] Inputs de Nome e Senha
  - [X] Ocultação da Senha
  - [X] Validação no Nome e Senha (Mensagemd e Erro e Mudança de Cor)
  - [X] Botão Login e Logout
  - [X] Botão Logout Limpando Reiniciando os Inputs
- [X] **"Hello"**
  - [X] Ao Completar um novo Login: `<hello-in-native-language> <user-name> you have successfully logged in!`
  - [X] Ao Realizar um Login Novamente: `Have a great day <user-name>!`
- [X] **Extras**
  - [X] Usuario Escolher o Idioma 
  - [X] Informações Adicionais (Endereço de IP, Cidade, Região, Nome do País e Fuso Horário)


## 🎨 Layout

### Mobile

**Imagens** das Telas do APP - [Tema Normal](printscreen/LightMode)


## 🚀 Como executar o projeto

Este projeto é divido em seis principais partes:
1. [Layout das Telas](app/src/main/res/layout/)
2. [Configurações das Telas](app/src/main/java/com/example/hellopeople/activities)
4. [Funções e Classes do Sistema](app/src/main/java/com/example/hellopeople/model)

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
- [Git](https://git-scm.com) → Atualizações e Versionamento no Codigo 
- [Android Studio](https://developer.android.com/studio/) → Editor da Google voltado ao Desenvolvimento Android

#### 📥 Baixando o Projeto

```bash

# Clone este repositório
$ https://github.com/GuilhermePalma/Hello_People.git

# Acesse a pasta do projeto no terminal/cmd
$ cd Hello_People

```


## 🛠 Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:
-   **[Java](https://developer.android.com/docs)**

#### **Utilitários**

-   API:  **[API - Recuperar o IP](http://checkip.amazonaws.com)**
-   API:  **[API - Detalhes do IP](http://ip-api.com/json)** → EndPoint: fields
-   API:  **[API - Hello, Salut!](https://fourtonfish.com/project/hellosalut-api/)** → EndPoint: lang e ip
-   Material Design: **[Text Fields](https://material.io/components/text-fields)**, **[Switches](https://material.io/components/switches)**
-   Documentação Android: → Extenção:  **[ExecutorService - Tarefa Assincrona](https://developer.android.com/reference/java/util/concurrent/ExecutorService)**
-   Documentação Android: → Extenção:  **[SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences?hl=pt-br)**
-   Documentação Android: → Extenção:  **[AlertDialog](https://developer.android.com/guide/topics/ui/dialogs?hl=pt-br)**


## 👨‍💻 Contribuidores

💜 Desenvolvedores que contruiram o Aplicativo :)

<table>
  <tr>
    <td align="center"><a href="https://github.com/guilhermepalma"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/54846154?v=4" width="100px;" alt=""/><br /><sub><b>Guilherme Palma</b></sub></a><br /><a href="https://github.com/guilhermepalma" title="Github">🚀</a></td>
  </tr>
</table>


## Creditos
[Florinpop17: app-ideas](https://github.com/florinpop17/app-ideas) → [Projeto "Hello"](https://github.com/florinpop17/app-ideas/blob/master/Projects/1-Beginner/Hello-App.md)


## 💪 Como contribuir no projeto

1. Faça um **fork** do projeto.
2. Crie uma nova branch com as suas alterações: `git checkout -b my-feature`
3. Salve as alterações e crie uma mensagem de commit contando o que você fez: `git commit -m "feature: My new feature"`
4. Envie as suas alterações: `git push origin my-feature`


---
