package model;

import java.time.LocalDate;

public interface Pagavel {
    void pagar();
    boolean isPago();
    LocalDate getDataVencimento();
    double getValor();
}
