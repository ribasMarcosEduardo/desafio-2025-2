package locadoraFilmes.application.validator;

import locadoraFilmes.application.exeption.FilmeDuplicado;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FilmeValidator {

    private final FilmeRepository filmeRepository;

    public void filmeDuplicado(Filme filme) {
        Optional<Filme>filmeExistente = filmeRepository.findByTitulo(filme.getTitulo());
        if (filmeExistente.isPresent() && filmeExistente.get().getId() != filme.getId()) {
            throw new FilmeDuplicado("Já existe um filme com o mesmo nome");
        }
    }
}
