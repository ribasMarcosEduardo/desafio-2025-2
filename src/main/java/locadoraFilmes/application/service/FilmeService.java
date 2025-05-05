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

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmeService {

    private final FilmeRepository repository;
    private final FilmeValidator validator;

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

    public List<Object[]> listarFilmes() {
        return repository.findFilmesComExemplares();
    }
}