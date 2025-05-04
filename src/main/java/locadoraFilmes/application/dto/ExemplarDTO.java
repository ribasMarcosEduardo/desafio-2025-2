package locadoraFilmes.application.dto;

import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;

import java.time.LocalDate;

public record ExemplarDTO(
        int id,
        Filme filme_id,
        LocalDate dataCadastro,
        boolean ativo
) {

}
