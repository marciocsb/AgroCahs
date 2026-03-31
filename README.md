# 🌾 AgroCahs  
### Sistema de Controle Financeiro para Agronegócio

O **AgroCahs** é um sistema desenvolvido em **Java** para auxiliar no controle financeiro de atividades agrícolas.  
Permite cadastrar usuários, gerenciar lavouras e registrar despesas, organizando dados de forma prática e eficiente.

---

## 🎯 Objetivo
Aplicar conceitos de **Programação Orientada a Objetos**, arquitetura em camadas (**MVC**) e **persistência de dados**, simulando um sistema de gerenciamento financeiro voltado ao agronegócio.

---

## 🏗️ Arquitetura
- **Model:** `AgroFinancas`, `Usuario`, `Lavoura`, `Despesa`  
- **Controller:** `AgroController`  
- **View:** `TelaPrincipal` (interface gráfica em Swing)  

---

## ⚙️ Funcionalidades
- Cadastro de usuários com validação de CPF  
- Associação de lavouras a usuários  
- Registro de despesas por lavoura  
- Listagem de dados cadastrados  
- Persistência em arquivos `.dat` via serialização  

---

## 🔄 Fluxo de Execução
1. A aplicação inicia pela classe `Main`  
2. O `AgroController` é instanciado  
3. A interface (`TelaPrincipal`) é exibida  
4. Usuário interage → Controller → `AgroFinancas`  
5. Dados são armazenados/recuperados via `GravadorDeDados`  

---

## 📊 Estruturas de Dados
- `Map<Usuario, List<Lavoura>>` para associar usuários às suas lavouras  

---

## 🚀 Possíveis Melhorias
- Integração com banco de dados (MySQL, PostgreSQL)  
- Interface gráfica mais moderna  
- Validações mais robustas  
- Autenticação de usuários  
- Relatórios financeiros avançados  

---

## 🛠️ Tecnologias
- Java  
- POO  
- Swing  
- Serialização  

