import model.*;
import util.CriptografiaUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Usuario> usuarios = new ArrayList<>();
    private static Usuario usuarioLogado = null;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CONTROLE DE DESPESAS ===");
        System.out.println("Bem-vindo ao sistema de gerenciamento de despesas pessoais!");
        
        while (true) {
            if (usuarioLogado == null) {
                menuPrincipal();
            } else {
                menuUsuario();
            }
        }
    }
    
    private static void menuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Fazer Login");
        System.out.println("2. Cadastrar Usuário");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerInteiro();
        
        switch (opcao) {
            case 1:
                fazerLogin();
                break;
            case 2:
                cadastrarUsuario();
                break;
            case 3:
                System.out.println("Obrigado por usar o sistema!");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
    
    private static void menuUsuario() {
        System.out.println("\n=== MENU DO USUÁRIO ===");
        System.out.println("Usuário logado: " + usuarioLogado.getNome());
        System.out.println("1. Adicionar Despesa");
        System.out.println("2. Listar Todas as Despesas");
        System.out.println("3. Listar Despesas Pendentes");
        System.out.println("4. Listar Despesas Vencidas");
        System.out.println("5. Pagar Despesa");
        System.out.println("6. Relatório Financeiro");
        System.out.println("7. Filtrar por Tipo");
        System.out.println("8. Remover Despesa");
        System.out.println("9. Logout");
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerInteiro();
        
        switch (opcao) {
            case 1:
                adicionarDespesa();
                break;
            case 2:
                listarTodasDespesas();
                break;
            case 3:
                listarDespesasPendentes();
                break;
            case 4:
                listarDespesasVencidas();
                break;
            case 5:
                pagarDespesa();
                break;
            case 6:
                exibirRelatorioFinanceiro();
                break;
            case 7:
                filtrarPorTipo();
                break;
            case 8:
                removerDespesa();
                break;
            case 9:
                usuarioLogado = null;
                System.out.println("Logout realizado com sucesso!");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
    
    private static void fazerLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && 
                CriptografiaUtil.verificarSenha(senha, usuario.getSenha())) {
                usuarioLogado = usuario;
                System.out.println("Login realizado com sucesso!");
                return;
            }
        }
        
        System.out.println("Email ou senha incorretos!");
    }
    
    private static void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        // Verificar se email já existe
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                System.out.println("Email já cadastrado!");
                return;
            }
        }
        
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        if (!CriptografiaUtil.validarSenha(senha)) {
            System.out.println("Senha deve ter pelo menos 6 caracteres, incluindo maiúscula, minúscula e número!");
            return;
        }
        
        String senhaHash = CriptografiaUtil.criptografarSenha(senha);
        Usuario novoUsuario = new Usuario(nome, email, senhaHash);
        usuarios.add(novoUsuario);
        
        System.out.println("Usuário cadastrado com sucesso!");
    }
    
    private static void adicionarDespesa() {
        System.out.println("\n=== ADICIONAR DESPESA ===");
        System.out.println("Tipos de despesa disponíveis:");
        TipoDespesa[] tipos = TipoDespesa.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i].getDescricao());
        }
        
        System.out.print("Escolha o tipo (1-" + tipos.length + "): ");
        int tipoIndex = lerInteiro() - 1;
        
        if (tipoIndex < 0 || tipoIndex >= tipos.length) {
            System.out.println("Tipo inválido!");
            return;
        }
        
        TipoDespesa tipo = tipos[tipoIndex];
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        
        System.out.print("Valor: R$ ");
        double valor = lerDouble();
        
        System.out.print("Data de vencimento (dd/MM/yyyy): ");
        LocalDate dataVencimento = lerData();
        
        if (dataVencimento == null) {
            System.out.println("Data inválida!");
            return;
        }
        
        Despesa despesa;
        
        if (tipo == TipoDespesa.ALIMENTACAO) {
            System.out.print("Local: ");
            String local = scanner.nextLine();
            System.out.print("É delivery? (s/n): ");
            boolean delivery = scanner.nextLine().toLowerCase().startsWith("s");
            
            despesa = new Alimentação(descricao, valor, dataVencimento, local, delivery);
        } else {
            // Para outros tipos, criar uma classe genérica que herda de Despesa
            despesa = new DespesaGenerica(descricao, valor, dataVencimento, tipo);
        }
        
        usuarioLogado.adicionarDespesa(despesa);
        System.out.println("Despesa adicionada com sucesso!");
    }
    
    private static void listarTodasDespesas() {
        List<Despesa> despesas = usuarioLogado.getDespesas();
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa cadastrada.");
            return;
        }
        
        System.out.println("\n=== TODAS AS DESPESAS ===");
        for (int i = 0; i < despesas.size(); i++) {
            System.out.println((i + 1) + ". " + despesas.get(i));
        }
    }
    
    private static void listarDespesasPendentes() {
        List<Despesa> pendentes = usuarioLogado.getDespesasPendentes();
        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma despesa pendente.");
            return;
        }
        
        System.out.println("\n=== DESPESAS PENDENTES ===");
        for (int i = 0; i < pendentes.size(); i++) {
            System.out.println((i + 1) + ". " + pendentes.get(i));
        }
    }
    
    private static void listarDespesasVencidas() {
        List<Despesa> vencidas = usuarioLogado.getDespesasVencidas();
        if (vencidas.isEmpty()) {
            System.out.println("Nenhuma despesa vencida.");
            return;
        }
        
        System.out.println("\n=== DESPESAS VENCIDAS ===");
        for (int i = 0; i < vencidas.size(); i++) {
            System.out.println((i + 1) + ". " + vencidas.get(i));
        }
    }
    
    private static void pagarDespesa() {
        List<Despesa> pendentes = usuarioLogado.getDespesasPendentes();
        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma despesa pendente para pagar.");
            return;
        }
        
        System.out.println("\n=== PAGAR DESPESA ===");
        for (int i = 0; i < pendentes.size(); i++) {
            System.out.println((i + 1) + ". " + pendentes.get(i));
        }
        
        System.out.print("Escolha a despesa para pagar (1-" + pendentes.size() + "): ");
        int index = lerInteiro() - 1;
        
        if (index >= 0 && index < pendentes.size()) {
            pendentes.get(index).pagar();
            System.out.println("Despesa paga com sucesso!");
        } else {
            System.out.println("Índice inválido!");
        }
    }
    
    private static void exibirRelatorioFinanceiro() {
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        System.out.printf("Total de despesas: R$ %.2f%n", usuarioLogado.getTotalDespesas());
        System.out.printf("Total pendente: R$ %.2f%n", usuarioLogado.getTotalDespesasPendentes());
        System.out.printf("Total pago: R$ %.2f%n", usuarioLogado.getTotalDespesasPagas());
        System.out.println("Despesas vencidas: " + usuarioLogado.getDespesasVencidas().size());
    }
    
    private static void filtrarPorTipo() {
        System.out.println("\n=== FILTRAR POR TIPO ===");
        TipoDespesa[] tipos = TipoDespesa.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i].getDescricao());
        }
        
        System.out.print("Escolha o tipo (1-" + tipos.length + "): ");
        int tipoIndex = lerInteiro() - 1;
        
        if (tipoIndex >= 0 && tipoIndex < tipos.length) {
            TipoDespesa tipo = tipos[tipoIndex];
            List<Despesa> despesasTipo = usuarioLogado.getDespesasPorTipo(tipo);
            
            if (despesasTipo.isEmpty()) {
                System.out.println("Nenhuma despesa encontrada para este tipo.");
            } else {
                System.out.println("\n=== DESPESAS DO TIPO: " + tipo.getDescricao() + " ===");
                for (int i = 0; i < despesasTipo.size(); i++) {
                    System.out.println((i + 1) + ". " + despesasTipo.get(i));
                }
            }
        } else {
            System.out.println("Tipo inválido!");
        }
    }
    
    private static void removerDespesa() {
        List<Despesa> despesas = usuarioLogado.getDespesas();
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa para remover.");
            return;
        }
        
        System.out.println("\n=== REMOVER DESPESA ===");
        for (int i = 0; i < despesas.size(); i++) {
            System.out.println((i + 1) + ". " + despesas.get(i));
        }
        
        System.out.print("Escolha a despesa para remover (1-" + despesas.size() + "): ");
        int index = lerInteiro() - 1;
        
        if (index >= 0 && index < despesas.size()) {
            Despesa despesa = despesas.get(index);
            usuarioLogado.removerDespesa(despesa);
            System.out.println("Despesa removida com sucesso!");
        } else {
            System.out.println("Índice inválido!");
        }
    }
    
    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static LocalDate lerData() {
        try {
            String dataStr = scanner.nextLine();
            return LocalDate.parse(dataStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    // Classe interna para despesas genéricas
    private static class DespesaGenerica extends Despesa {
        public DespesaGenerica(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
            super(descricao, valor, dataVencimento, tipo);
        }
    }
}
