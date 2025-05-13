package locadoraFilmes.application.validator;

import locadoraFilmes.application.exeption.MensagemPadrao;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FilmeLocadoraValidator {

    private final FilmeRepository filmeRepository;
    private final ExemplarRepository exemplarRepository;

    // Verificar se tem algum filme com o mesmo nome
    public void filmeDuplicado(Filme filme) {
        Optional<Filme> filmeExistente = filmeRepository.findByTitulo(filme.getTitulo());
        if (filmeExistente.isPresent() && filmeExistente.get().getId() != filme.getId()) {
            throw new MensagemPadrao("Já existe um filme com o mesmo nome");
        }
    }

    // Verificar se exemplar faz parte de alguma locação
    public void exemplarAssociado(int exemplarId) {
        Optional<Integer> exemplarExistente = exemplarRepository.findExemplarIdIfInActiveLocacao(exemplarId);

        if (exemplarExistente.isPresent()) {
            throw new MensagemPadrao(
                    "Não é possível excluir/inativar o exemplar com ID " + exemplarId +
                            " pois ele está associado a uma locação pendente."
            );
        }
    }

    // Validar Filmes ativos durante a exclusão
    public void validarExclusao(int filmeId) {
        int totalExemplaresAtivos = exemplarRepository.countActiveExemplaresByFilmeId(filmeId);

        if (totalExemplaresAtivos > 0) {
            throw new MensagemPadrao(
                    "Não é possível excluir o filme. Existem " + totalExemplaresAtivos +
                            " exemplar(es) ativo(s) associado(s) a este filme. " +
                            "Por favor, inative todos os exemplares ativos antes de prosseguir com a exclusão do filme."
            );
        }

    }
}
