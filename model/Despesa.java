package model;

import java.time.LocalDate;

public abstract class Despesa implements Pagavel {
    protected String descricao;
    protected double valor;
    protected LocalDate dataVencimento;
    protected boolean pago;
    protected TipoDespesa tipo;
    
    public Despesa(String descricao, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.pago = false;
    }
    
    // Getters e Setters
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    @Override
    public LocalDate getDataVencimento() {
        return dataVencimento;
    }
    
    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    
    @Override
    public boolean isPago() {
        return pago;
    }
    
    public TipoDespesa getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoDespesa tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public void pagar() {
        this.pago = true;
    }
    
    public boolean isVencida() {
        return !pago && LocalDate.now().isAfter(dataVencimento);
    }
    
    @Override
    public String toString() {
        String status = pago ? "PAGO" : (isVencida() ? "VENCIDO" : "PENDENTE");
        return String.format("%s - R$ %.2f - %s - %s - %s", 
                           descricao, valor, dataVencimento, tipo, status);
    }
}
