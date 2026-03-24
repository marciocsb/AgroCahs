*AgroCahs - Sistema de Controle Financeiro para Agronegócio*

*Descrição:*
O AgroCahs é um sistema desenvolvido em Java com o objetivo de auxiliar no controle financeiro de atividades agrícolas. A aplicação permite o cadastro de usuários, gerenciamento de lavouras e registro de despesas associadas, proporcionando uma organização eficiente dos dados financeiros no contexto do agronegócio.

*Objetivo*
O projeto tem como finalidade aplicar conceitos de programação orientada a objetos, organização em camadas e persistência de dados, simulando um sistema de gerenciamento financeiro.
Arquitetura do Sistema
O sistema foi estruturado seguindo uma separação em camadas inspirada no padrão Model-View-Controller (MVC).

Model (Modelo)
Responsável pela lógica de negócio e estrutura dos dados.
AgroFinancas: classe principal que gerencia usuários e lavouras
Usuario: representa um usuário do sistema
Lavoura: representa uma plantação associada a um usuário
Despesa: representa gastos relacionados a uma lavoura

Controller (Controle)
Responsável por intermediar a comunicação entre a interface e a lógica de negócio.
AgroController: recebe as ações da interface e delega ao sistema
View (Interface)
Responsável pela interação com o usuário.
TelaPrincipal: interface gráfica do sistema

*Funcionalidades:*
Cadastro de usuários com validação de CPF
Associação de lavouras a usuários
Registro de despesas por lavoura
Listagem de dados cadastrados
Persistência de dados em arquivos

*Persistência de Dados:*
O sistema utiliza serialização em Java para armazenamento local de dados.
Os dados são salvos em arquivos com extensão .dat
Cada usuário possui seus dados persistidos
Os arquivos são organizados em diretórios específicos

*Fluxo de Execução:*
A aplicação inicia pela classe Main
O AgroController é instanciado
A interface (TelaPrincipal) é exibida
O usuário interage com o sistema
As ações são enviadas ao controller
O controller processa as informações por meio do AgroFinancas
Os dados são armazenados ou recuperados via GravadorDeDados


*Estruturas de Dados Utilizadas:*
Map<Usuario, List>
Essa estrutura permite associar cada usuário às suas respectivas lavouras de forma eficiente.

*Possíveis Melhorias:*

Integração com banco de dados (MySQL, PostgreSQL, etc.)
Melhorias na interface gráfica
Validações mais robustas de dados
Implementação de autenticação de usuários
Geração de relatórios financeiros
Aplicação mais completa do padrão MVC

*Tecnologias Utilizadas:*
Java
Programação Orientada a Objetos
Serialização de dados
Interface gráfica (Swing ou similar)

*Conceitos Aplicados:*

Encapsulamento
Separação de responsabilidades
Persistência de dados
Estruturas de dados (Map e List)
Arquitetura em camadas
