package locadoraFilmes.application.service;

import locadoraFilmes.application.dto.ExemplarDTO;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.repository.FilmeRepository;
import locadoraFilmes.application.validator.FilmeLocadoraValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmeService {

    private final FilmeRepository repository;
    private final FilmeLocadoraValidator validator;

    // Cadastrar Filme
    public Filme salvarFilme(Filme filme) {
        validator.filmeDuplicado(filme);
        for (int i = 0; i < filme.getExemplares_disponiveis(); i++) {
            ExemplarDTO exemplarDTO = new ExemplarDTO(
                    0,
                    filme,
                    LocalDate.now(),
                    true
            );
            Exemplar exemplar = exemplarDTO.mapearExemplar();
            filme.getExemplares().add(exemplar);
        }

        try {
            return repository.save(filme);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar filme: " + e.getMessage(), e);
        }

    }

    // Listar Filmes
    public List<Object[]> listarFilmes() {
        return repository.findFilmesComDetalhesEContagemDeExemplaresDisponiveis();
    }

    // Listar Filmes Com Exemplares Ativos -- modal
    public List<Object[]> listarFilmesComExemplaresTrue() {
        return repository.findFilmesComDetalhesEExemplaresAtivos();
    }

    // Listar Filmes Edit -----------------
    public List<Object[]> listarFilmesEdit() {
        return repository.findFilmesTelaEdit();
    }

    // Exclusão de Filmes
    @Transactional
    public void excluirFilmes(int id){
        Filme filme = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
        validator.validarExclusao(id);
        repository.delete(filme);
    }

    // Alterar Status (ativo/inativo) de Filmes
    @Transactional
    public void alterarStatusFilmes(int id) {
        Filme filme = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
        validator.validarAlteraFilmeStatus(id);
        filme.setAtivo(!filme.isAtivo());

        repository.save(filme);
    }


}