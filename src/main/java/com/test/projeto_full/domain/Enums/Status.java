package com.test.projeto_full.domain.Enums;

public enum Status {

    ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

    private Integer codigo;
    private String descricao;

    private Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    public Integer getCodigo() {
        return codigo;
    }
    public String getDescricao() {
        return descricao;
    }
    public static Status toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (Status perfil : Status.values()) {
            if (codigo.equals(perfil.getCodigo())) {
                return perfil;
            }
        }
        throw new IllegalArgumentException("STATUS inválido: " + codigo);
    }

}
