# Análise de Coesão e Acoplamento do Sistema AgroFinanças

## Coesão
- Cada classe tem responsabilidade clara:
  - `Usuario`: dados do usuário.
  - `Lavoura`: gerencia lavoura e despesas.
  - `Despesa`: representa um gasto.
  - `AgroFinancas`: gerencia usuários, lavouras e persistência.
  - `AgroController`: atua como fachada entre GUI e sistema.
- A coesão é boa, pois métodos dentro de cada classe estão relacionados a uma única responsabilidade.

## Acoplamento
- `AgroController` depende de `AgroFinancas` e levemente de `JOptionPane` (GUI).
- `AgroFinancas` depende de `GravadorDeDados` (persistência).
- Melhorias possíveis:
  - Separar GUI da lógica de negócio (evitar JOptionPane no controller).
  - Criar interface para persistência (`IDados`) para reduzir acoplamento.

## Pontos de melhoria / Bad Smells
- Mistura de responsabilidades: lógica e GUI juntas em alguns métodos.
- Métodos grandes podem ser quebrados.
- Serialização direta: poderia usar JSON ou banco de dados.
- Variáveis curtas (`u`, `l`) podem ser mais descritivas.