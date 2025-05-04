package locadoraFilmes.application.service;

import locadoraFilmes.application.dto.ExemplarDTO;
import locadoraFilmes.application.dto.FilmeDTO;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class FilmeService {

    private final FilmeRepository repository;
    private final ExemplarRepository exemplarRepository;

    public Filme salvarFilme(Filme filme) {

        for (int i = 0; i < filme.getExemplares_disponiveis(); i++) {
            ExemplarDTO exemplarDTO = new ExemplarDTO(
                    0,
                    filme,
                    LocalDate.now(),
                    true);
        }

        try {
            return repository.save(filme);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar filme: " + e.getMessage(), e);
        }
    }
}