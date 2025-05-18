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

    public Exemplar mapearExemplar() {
        Exemplar exemplar = new Exemplar();
        exemplar.setId(this.id);
        exemplar.setFilme(this.filme_id);
        exemplar.setDataCadastro(this.dataCadastro);
        exemplar.setAtivo(this.ativo);
        return exemplar;
    }


}
