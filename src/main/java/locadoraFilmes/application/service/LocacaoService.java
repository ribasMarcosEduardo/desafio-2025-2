package locadoraFilmes.application.service;

import locadoraFilmes.application.dto.LocacaoDTO;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Locacao;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.LocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;
    private final ExemplarRepository exemplarRepository;

    public void salvarLocacao(LocacaoDTO locacaoDTO) {
        Locacao locacao = locacaoDTO.mapearLocacao();
        String cpf = locacaoDTO.mapearLocacao().getCpf().replaceAll("[^\\d]", "");
        locacao.setCpf(cpf);

        List<Exemplar> exemplares = exemplarRepository.findAllById(locacaoDTO.exemplaresSelecionados());
        locacao.setExemplares(exemplares);

        locacaoRepository.save(locacao);
    }

}
