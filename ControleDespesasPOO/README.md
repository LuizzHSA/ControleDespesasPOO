# B4_A_TRABALHO - Sistema de Controle de Despesas

Projeto final da disciplina de Programação Orientada a Objetos para a criação de um sistema de controle de despesas pessoais. O sistema permite o gerenciamento de despesas, pagamentos, tipos de despesas e usuários, com persistência de dados em arquivos de texto.

## Estratégia de Construção do Sistema

O sistema será desenvolvido em Java, seguindo os princípios da Programação Orientada a Objetos (POO) para garantir um código modular, reutilizável e de fácil manutenção. A estratégia de construção será dividida nas seguintes etapas e conceitos:

1.  **Modelagem das Entidades**: Serão criadas classes para representar as principais entidades do sistema: `Usuario`, `Despesa`, `TipoDespesa` e `Pagamento`. A classe `Despesa` será abstrata, servindo como base para categorias específicas (ex: `Transporte`, `Alimentacao`) através de **Herança**.

2.  **Separação de Responsabilidades**: A lógica de negócio será separada da lógica de persistência de dados. Para isso, utilizaremos o padrão de projeto *Repository* (ou DAO - Data Access Object). Teremos classes como `UsuarioRepository` e `DespesaRepository`, responsáveis exclusivamente por ler e escrever os dados nos arquivos de texto.

3.  **Uso de Interfaces e Polimorfismo**: Uma interface como `Pagavel` será criada para definir um contrato para entidades que podem receber um pagamento. A classe `Despesa` implementará essa interface, e o **Polimorfismo** permitirá tratar diferentes tipos de despesas de forma uniforme.

4.  **Encapsulamento**: Todos os atributos das classes serão privados, e o acesso a eles será feito através de métodos `getters` e `setters`, garantindo o encapsulamento e a integridade dos dados.

5.  **Segurança**: As senhas dos usuários serão armazenadas de forma segura. Uma classe utilitária `CriptografiaUtil` será responsável por aplicar um algoritmo de hash (como SHA-256) antes de salvar a senha no arquivo, garantindo que elas não fiquem em texto plano.

6.  **Interface com o Usuário**: A interação com o usuário será feita através de um menu no console (terminal), controlado pela classe principal `Main` ou `Sistema`.

## Documentação das Classes e Atributos

Abaixo está a estrutura planejada para as principais classes do sistema.

---

### **1. Classes de Modelo (Entidades)**

Representam os dados do sistema.

#### **`Usuario.java`**
Representa um usuário do sistema.
-   **Atributos:**
    -   `id: int` (Identificador único)
    -   `login: String`
    -   `senhaCriptografada: String`
-   **Métodos:**
    -   `Usuario(int id, String login, String senha)` (Construtor que já criptografa a senha)
    -   `getters/setters` para os atributos.
    -   `verificarSenha(String senha): boolean` (Método para comparar uma senha com a versão criptografada)

#### **`TipoDespesa.java`**
Representa uma categoria de despesa (ex: Alimentação).
-   **Atributos:**
    -   `id: int`
    -   `nome: String`
-   **Métodos:**
    -   `TipoDespesa(int id, String nome)` (Construtor)
    -   `getters/setters` para os atributos.

#### **`Pagavel.java` (Interface)**
Define o contrato para objetos que podem ser pagos.
-   **Métodos:**
    -   `anotarPagamento(double valor, LocalDate data)`

#### **`Despesa.java` (Classe Abstrata)**
Classe base para todas as despesas. Implementa a interface `Pagavel`.
-   **Atributos:**
    -   `id: int`
    -   `descricao: String`
    -   `valor: double`
    -   `dataVencimento: LocalDate`
    -   `tipoDespesa: TipoDespesa`
    -   `paga: boolean`
    -   `dataPagamento: LocalDate`
    -   `valorPago: double`
    -   `contadorDespesas: static int` (Atributo estático para contar o total de despesas criadas)
-   **Métodos:**
    -   `Despesa(int id, String descricao, double valor, ...)` (Construtor sobrecarregado)
    -   `getters/setters` para os atributos.
    -   `anotarPagamento(double valor, LocalDate data)` (**Sobrescrita** do método da interface `Pagavel`)
    -   `getContadorDespesas(): static int` (Método estático para acessar o contador)

#### **Classes Concretas de Despesa (Herança)**
Exemplos de classes que herdam de `Despesa`.
-   **`Transporte.java`**: `public class Transporte extends Despesa { ... }`
-   **`Alimentacao.java`**: `public class Alimentacao extends Despesa { ... }`
-   **`Eventual.java`**: `public class Eventual extends Despesa { ... }`
    -   Essas classes herdarão todos os atributos e métodos de `Despesa`. Elas usarão a **sobrescrita de construtor** para chamar o construtor da superclasse (`super(...)`).

---

### **2. Classes de Repositório (Persistência)**

Responsáveis pela leitura e escrita nos arquivos de texto.

#### **`UsuarioRepository.java`**
Gerencia o arquivo `usuarios.txt`.
-   **Atributos:**
    -   `caminhoArquivo: final String = "usuarios.txt"`
-   **Métodos:**
    -   `salvar(Usuario usuario): void`
    -   `buscarPorLogin(String login): Usuario`
    -   `listarTodos(): List<Usuario>`
    -   `excluir(int id): void`

#### **`DespesaRepository.java`**
Gerencia o arquivo `despesas.txt`.
-   **Métodos:**
    -   `salvar(Despesa despesa): void`
    -   `buscarPorId(int id): Despesa`
    -   `listarTodas(): List<Despesa>`
    -   `listarPagasNoPeriodo(LocalDate inicio, LocalDate fim): List<Despesa>`
    -   `listarAbertasNoPeriodo(LocalDate inicio, LocalDate fim): List<Despesa>`
    -   `excluir(int id): void`

*(Estrutura similar para `TipoDespesaRepository.java`)*

---

### **3. Classes Utilitárias e de Controle**

#### **`CriptografiaUtil.java`**
Classe com métodos estáticos para segurança.
-   **Métodos:**
    -   `criptografarSenha(String senha): static String` (Usa um algoritmo de hash)

#### **`Sistema.java` ou `Main.java`**
Classe principal que controla o fluxo do programa, exibe o menu e interage com o usuário.
-   **Métodos:**
    -   `main(String[] args): static void`
    -   `exibirMenuPrincipal(): void`
    -   `processarOpcao(int opcao): void`
    -   `cadastrarDespesa(): void`
    -   `realizarLogin(): void`
    -   (etc, para cada funcionalidade do menu)
