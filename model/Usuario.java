package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private List<Despesa> despesas;
    
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.despesas = new ArrayList<>();
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public List<Despesa> getDespesas() {
        return new ArrayList<>(despesas);
    }
    
    // Métodos de gerenciamento de despesas
    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }
    
    public boolean removerDespesa(Despesa despesa) {
        return despesas.remove(despesa);
    }
    
    public List<Despesa> getDespesasPendentes() {
        return despesas.stream()
                      .filter(d -> !d.isPago())
                      .collect(Collectors.toList());
    }
    
    public List<Despesa> getDespesasVencidas() {
        return despesas.stream()
                      .filter(Despesa::isVencida)
                      .collect(Collectors.toList());
    }
    
    public List<Despesa> getDespesasPorTipo(TipoDespesa tipo) {
        return despesas.stream()
                      .filter(d -> d.getTipo() == tipo)
                      .collect(Collectors.toList());
    }
    
    public double getTotalDespesas() {
        return despesas.stream()
                      .mapToDouble(Despesa::getValor)
                      .sum();
    }
    
    public double getTotalDespesasPendentes() {
        return getDespesasPendentes().stream()
                                   .mapToDouble(Despesa::getValor)
                                   .sum();
    }
    
    public double getTotalDespesasPagas() {
        return despesas.stream()
                      .filter(Despesa::isPago)
                      .mapToDouble(Despesa::getValor)
                      .sum();
    }
    
    @Override
    public String toString() {
        return String.format("Usuário: %s (%s) - Total de despesas: R$ %.2f", 
                           nome, email, getTotalDespesas());
    }
}
