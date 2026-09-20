package com.tracebank.customer;

public class CpfAlreadyRegisteredException extends RuntimeException {
    public CpfAlreadyRegisteredException(String cpf) {
        super("CPF already registered: " + cpf);
    }
}