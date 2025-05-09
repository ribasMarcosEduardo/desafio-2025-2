package locadoraFilmes.application.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relacionamento Many-to-Many com Exemplar com cascade para garantir a persistência
    @ManyToMany(cascade = CascadeType.PERSIST)  // Adicionando Cascade para persistência automática
    @JoinTable(
            name = "locacao_exemplar",
            joinColumns = @JoinColumn(name = "locacao_id"),
            inverseJoinColumns = @JoinColumn(name = "exemplar_id")
    )
    private List<Exemplar> exemplares = new ArrayList<>();

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String telefone;

    @Column(nullable = false)
    private LocalDate dataLocacao;

    @Column(nullable = false)
    private LocalDate dataDevolucao;

    @Column
    private LocalDate dataDevolvido;

    @Column(length = 255)
    private String qrCode;
}


