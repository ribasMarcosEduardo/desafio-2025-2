package locadoraFilmes.application.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(nullable = false)
    private LocalDate dataCadastro;

    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    @JsonBackReference // Fiquei 3 hrs tentando arrumar um bug q tava sendo causado pela falta de 1 linha KKKK Ficando loko
    private Filme filme;

    @PrePersist
    private void prePersist() {

        this.dataCadastro = LocalDate.now();
    }
}
