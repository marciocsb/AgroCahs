# Análise de Coesão e Acoplamento do Sistema AgroCahs

## Coesão
- Cada classe tem responsabilidade clara:
    - `Usuario`: dados de usuários.
    - `Lavoura`: informações sobre plantações.
    - `Despesa`: gastos relacionados às lavouras.
    - `AgroFinancas`: operações principais do sistema.
- A coesão pode ser melhorada separando a persistência em uma camada própria (DAO/Repository).

## Acoplamento
- O sistema apresenta acoplamento moderado:
    - `AgroFinancas` depende diretamente de `GravadorDeDados`.
    - Uso de `Map<Usuario, List<Lavoura>>` cria dependência entre entidades.
- Melhorias possíveis: aplicar injeção de dependência e interfaces para reduzir acoplamento.

## Bad Smells Identificados
- **God Class:** `AgroFinancas` concentra muitas responsabilidades.
- **Duplicação de Código:** lógica de busca por CPF repetida.
- **Baixa cobertura de testes:** antes dos novos testes, não havia validação completa.

## Melhorias Futuras
- Criar camada de persistência separada.
- Aplicar padrão DAO/Repository.
- Melhorar cobertura de testes unitários.
- Usar interfaces para reduzir dependência direta entre classes.
