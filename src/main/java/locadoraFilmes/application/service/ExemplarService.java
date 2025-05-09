
package locadoraFilmes.application.service;

import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExemplarService {

    private final ExemplarRepository repository;
    private final FilmeRepository filmeRepository;

    // Listar Exemplares por título
    public List<Exemplar> listarExemplaresPorTituloFilme(String titulo) {
        return repository.findByFilmeTitulo(titulo);
    }

    // Listar Exemplares Titulo (apenas ativos)
    public List<Exemplar> listarExemplaresPorTituloFilmeTrue(String titulo) {
        return repository.findByFilmeTituloAndAtivoTrue(titulo);
    }

    public List<Exemplar> buscarPorIds(List<Integer> ids) {
        return repository.findAllById(ids);
    }

    // Cadastro de Exemplar - Adicionar validator posteriormente
    public void cadastrarExemplar(String tituloFilme, boolean ativo) {
        Filme filme = filmeRepository.findByTitulo(tituloFilme)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + tituloFilme));

        Exemplar exemplar = new Exemplar();
        exemplar.setFilme(filme);
        exemplar.setAtivo(ativo);
        repository.save(exemplar);
    }

    // Exclusão de Exemplares
    public void excluirExemplar(int id) {
        Exemplar exemplar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));

        repository.delete(exemplar);
    }

    // Alterar status (ativo/inativo) do exemplar
    @Transactional
    public void alterarStatus(int id) {
        Exemplar exemplar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));

        exemplar.setAtivo(!exemplar.isAtivo());

        repository.save(exemplar);
    }
}