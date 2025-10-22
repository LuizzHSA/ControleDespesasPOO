# Controle de Despesas POO

Sistema de gerenciamento de despesas pessoais desenvolvido em Java com Programação Orientada a Objetos.

## Funcionalidades
- Cadastro e login de usuários com senha criptografada (`SHA-256`).
- Adição de despesas por tipo (`TipoDespesa`), incluindo alimentação com taxa de delivery.
- Listagem de despesas: todas, pendentes e vencidas.
- Pagamento e remoção de despesas.
- Relatórios financeiros: total, pendente e pago.
- Filtragem por tipo de despesa.

## Requisitos
- Java 8+ instalado.
- Git (opcional, para versionamento e publicação no GitHub).

## Estrutura do Projeto
- `Main.java`: aplicação console com menu interativo.
- `model/`: classes de domínio (`Usuario`, `Despesa`, `Alimentação`, `TipoDespesa`, `Pagavel`).
- `util/CriptografiaUtil.java`: utilitário para hash e validação de senhas.

## Como executar
1. Compile o projeto:
   ```bash
   javac -cp . Main.java model/*.java util/*.java
   ```
2. Execute:
   ```bash
   java Main
   ```

## Exemplos de uso
- Cadastre um usuário com senha forte (mín. 6 caracteres, com maiúscula, minúscula e número).
- Faça login e adicione despesas. Para `Alimentação`, informe local e se é delivery.
- Use os menus para listar, filtrar, pagar e remover despesas.

## Publicando no GitHub (resumo)
1. Inicialize o repositório local e faça o commit inicial:
   ```bash
   git init
   git add .
   git commit -m "Inicial commit: projeto de controle de despesas"
   ```
2. Crie um repositório no GitHub (via web) e copie a URL (ex.: `https://github.com/SEU_USUARIO/ControleDespesasPOO.git`).
3. Adicione o remoto e envie:
   ```bash
   git remote add origin https://github.com/SEU_USUARIO/ControleDespesasPOO.git
   git branch -M main
   git push -u origin main
   ```

## Próximos passos
- Persistência de dados (arquivo/BD).
- Testes unitários.
- Outras categorias de despesas.
