package locadoraFilmes.application.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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

    @Column(nullable = false, length = 255)
    private String resumo;

    @Column(nullable = false, length = 255)
    private String pontuacao;

    @Column(nullable = false)
    private LocalDate lancamento;

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)  //lembrar de arrumar esse cascate
    private List<Exemplar> exemplares = new ArrayList<>();

}
