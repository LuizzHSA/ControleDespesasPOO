package model;

import java.time.LocalDate;

public class Alimentação extends Despesa {
    private String local;
    private boolean delivery;
    
    public Alimentação(String descricao, double valor, LocalDate dataVencimento, String local, boolean delivery) {
        super(descricao, valor, dataVencimento, TipoDespesa.ALIMENTACAO);
        this.local = local;
        this.delivery = delivery;
    }
    
    public String getLocal() {
        return local;
    }
    
    public void setLocal(String local) {
        this.local = local;
    }
    
    public boolean isDelivery() {
        return delivery;
    }
    
    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }
    
    public double calcularTaxaDelivery() {
        return delivery ? valor * 0.1 : 0.0; // 10% de taxa de delivery
    }
    
    @Override
    public double getValor() {
        return super.getValor() + calcularTaxaDelivery();
    }
    
    @Override
    public String toString() {
        String tipoServico = delivery ? "Delivery" : "Presencial";
        return String.format("%s - Local: %s (%s) - R$ %.2f", 
                           super.toString(), local, tipoServico, getValor());
    }
}
