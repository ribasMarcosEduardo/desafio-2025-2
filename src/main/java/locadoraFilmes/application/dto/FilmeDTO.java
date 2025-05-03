package locadoraFilmes.application.dto;

import locadoraFilmes.application.model.Filme;

import java.time.LocalDate;

public record FilmeDTO(
    int id,
    boolean ativo,
    long exemplares_disponiveis,
    String titulo,
    String resumo,
    String pontuacao,
    LocalDate lancamento
) {

   /* public Filme mapearFilme(){
        Filme filme = new Filme();
        filme.setId(this.id);
        filme.setAtivo(this.ativo);
        filme.setExemplares_disponiveis(this.exemplares_disponiveis);
        filme.setTitulo(this.titulo);
        filme.setResumo(this.resumo);
        filme.setPontuacao(this.pontuacao);
        filme.setLancamento(this.lancamento);
        return filme;
    }*/

}
