package locadoraFilmes.application.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Filme {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column
   private int id;

   @Column(nullable = false)
   private boolean ativo;

    @Column(nullable = false)
    private long exemplares_disponiveis;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String resumo;

    @Column(nullable = false, length = 255)
    private String pontuacao;

    @Column(nullable = false)
    private LocalDate lancamento;

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Exemplar> exemplares = new ArrayList<>();

}
