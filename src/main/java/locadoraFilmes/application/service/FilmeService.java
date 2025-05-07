package locadoraFilmes.application.service;

import locadoraFilmes.application.dto.ExemplarDTO;
import locadoraFilmes.application.exeption.FilmeDuplicado;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.FilmeRepository;
import locadoraFilmes.application.validator.FilmeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmeService {

    private final FilmeRepository repository;
    private final FilmeValidator validator;

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
        return repository.findFilmesComExemplaresAtivos();
    }

    // Listar Filmes Edit
    public List<Object[]> listarFilmesEdit() {
        return repository.findFilmesComDetalhesEExemplaresAtivos();
    }

    // Exclusão de Filmes
    public void excluirFilmes(int id){
        Filme filme = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        repository.delete(filme);
    }

    // Alterar Status (ativo/inativo) de Filmes
    @Transactional
    public void alterarStatusFilmes(int id) {
        Filme filme = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        filme.setAtivo(!filme.isAtivo());

        repository.save(filme);
    }


}