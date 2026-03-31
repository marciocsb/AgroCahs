AgroCahs - Sistema de Controle Financeiro para Agronegócio
 
O AgroCahs é um sistema em Java para auxiliar no controle financeiro de atividades agrícolas. Permite cadastrar usuários, gerenciar lavouras e registrar despesas, organizando dados de forma prática e eficiente.

Objetivo:  
Aplicar conceitos de programação orientada a objetos, arquitetura em camadas (MVC) e persistência de dados, simulando um sistema de gerenciamento financeiro voltado ao agronegócio.

Arquitetura:

Model: classes de negócio (AgroFinancas, Usuario, Lavoura, Despesa)

Controller: AgroController, responsável por intermediar interface e lógica

View: TelaPrincipal, interface gráfica em Swing

Funcionalidades:

Cadastro de usuários com validação de CPF

Associação de lavouras a usuários

Registro de despesas por lavoura

Listagem de dados cadastrados

Persistência em arquivos .dat via serialização

Fluxo de Execução:

A aplicação inicia pela classe Main

O AgroController é instanciado

A interface (TelaPrincipal) é exibida

Usuário interage → Controller → AgroFinancas

Dados são armazenados/recuperados via GravadorDeDados

Estruturas de Dados:

Map<Usuario, List<Lavoura>> para associar usuários às suas lavouras

Possíveis Melhorias:

Integração com banco de dados

Interface gráfica mais moderna

Validações mais robustas

Autenticação de usuários

Relatórios financeiros avançados

Tecnologias:

Java

POO

Swing

Serialização
