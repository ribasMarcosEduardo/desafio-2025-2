package locadoraFilmes.application.service;

import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExemplarService {

    private final ExemplarRepository repository;
    private final FilmeRepository filmeRepository;

    public List<Exemplar> listarExemplaresPorTituloFilme(String titulo) {
        return repository.findByFilmeTitulo(titulo);
    }

    public void cadastrarExemplar(String tituloFilme, boolean ativo) {
        Filme filme = filmeRepository.findByTitulo(tituloFilme)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + tituloFilme));

        Exemplar exemplar = new Exemplar();
        exemplar.setFilme(filme);
        exemplar.setAtivo(ativo);
        repository.save(exemplar);
    }


}
