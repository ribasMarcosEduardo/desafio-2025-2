package locadoraFilmes.application.dto;

import locadoraFilmes.application.model.Locacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record LocacaoDTO(
        int id,
        String nome,
        String cpf,
        String email,
        String telefone,
        LocalDate dataLocacao,
        LocalDate dataDevolucao,
        LocalDate dataDevolvido,
        List<Integer> exemplaresSelecionados
) {

    public LocacaoDTO {
        if (exemplaresSelecionados == null) {
            exemplaresSelecionados = new ArrayList<>();
        }
    }

public Locacao mapearLocacao() {
        Locacao locacao = new Locacao();
        locacao.setId(this.id);
        locacao.setNome(this.nome);
        locacao.setCpf(this.cpf);
        locacao.setEmail(this.email);
        locacao.setTelefone(this.telefone);
        locacao.setDataLocacao(this.dataLocacao);
        locacao.setDataDevolucao(this.dataDevolucao);
        locacao.setDataDevolvido(this.dataDevolvido);

        return locacao;
    }
}
