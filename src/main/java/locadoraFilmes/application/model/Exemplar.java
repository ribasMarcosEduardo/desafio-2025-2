package locadoraFilmes.application.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
    @JsonBackReference
    private Filme filme;

    @ManyToMany(mappedBy = "exemplares")
    @JsonManagedReference
    @JsonIgnore
    private List<Locacao> locacoes;

    @PrePersist
    private void prePersist() {

        this.dataCadastro = LocalDate.now();
    }

    @PreRemove
    private void preRemove() {
        if (filme != null && filme.getExemplares_disponiveis() > 0) {
            filme.setExemplares_disponiveis(filme.getExemplares_disponiveis() - 1);
        }
    }
}

