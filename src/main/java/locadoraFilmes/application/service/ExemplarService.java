
package locadoraFilmes.application.service;

import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.model.Locacao;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.FilmeRepository;
import locadoraFilmes.application.repository.LocacaoRepository;
import locadoraFilmes.application.validator.FilmeLocadoraValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExemplarService {

    private final ExemplarRepository repository;
    private final FilmeRepository filmeRepository;
    private final LocacaoRepository locacaoRepository;
    private final FilmeLocadoraValidator validator;

    // Listar Exemplares por título
    public List<Exemplar> listarExemplaresPorTituloFilme(String titulo) {
        return repository.findByFilmeTitulo(titulo);
    }

    // Listar Exemplares por título aalugados
    public List<Exemplar> listarExemplaresPortituloEmLocacao(String titulo) {
        return repository.findByFilmeTituloAndLocacao(titulo);
    }

    // Listar Exemplares Titulo (apenas ativos)
    public List<Exemplar> listarExemplaresPorTituloFilmeTrue(String titulo) {
        return repository.findByFilmeTituloAndAtivoTrue(titulo);
    }

    // Cadastro de Exemplar
    @Transactional
    public void cadastrarExemplar(String tituloFilme, boolean ativo) {
        Filme filme = filmeRepository.findByTitulo(tituloFilme)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + tituloFilme));

        Exemplar exemplar = new Exemplar();
        exemplar.setFilme(filme);
        exemplar.setAtivo(ativo);
        repository.save(exemplar);

        filme.setExemplares_disponiveis(filme.getExemplares_disponiveis() + 1);
        filmeRepository.save(filme);
    }

    // Exclusão de Exemplaress
    @Transactional
    public void excluirExemplar(int id) {
        Exemplar exemplar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado com ID: " + id));

        validator.exemplarAssociado(id);

        List<Locacao> locacoesAssociadas = exemplar.getLocacoes();
        List<Locacao> locacoesParaAtualizar = locacaoRepository.findByExemplaresContains(exemplar);


        for (Locacao locacao : locacoesParaAtualizar) {
            locacao.getExemplares().remove(exemplar);
            locacaoRepository.save(locacao);
        }

        repository.delete(exemplar);

        Filme filme = exemplar.getFilme();
        if (filme != null) {
            Filme filmeGerenciado = filmeRepository.findById(filme.getId())
                    .orElseThrow(() -> new RuntimeException("Filme associado não encontrado"));

            if (filmeGerenciado.getExemplares_disponiveis() > 0) {
                filmeGerenciado.setExemplares_disponiveis(filmeGerenciado.getExemplares_disponiveis() - 1);
                filmeRepository.save(filmeGerenciado);
            }
        }
    }

    // Alterar status (ativo/inativo) do exemplar
    @Transactional
    public void alterarStatus(int id) {
        Exemplar exemplar = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));

        if (exemplar.isAtivo()) {
            validator.exemplarAssociado(id);
        }
        exemplar.setAtivo(!exemplar.isAtivo());

        repository.save(exemplar);
    }
}