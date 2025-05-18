package locadoraFilmes.application.service;

import locadoraFilmes.application.dto.LocacaoDTO;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Locacao;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.LocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;
    private final ExemplarRepository exemplarRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${apgy.qr.api.url}")
    private String qrApiBaseUrl;

    // Salva a locação e faz o Qr
    @Transactional
    public void salvarLocacao(LocacaoDTO locacaoDTO) {
        Locacao locacao = locacaoDTO.mapearLocacao();
        String cpf = locacaoDTO.mapearLocacao().getCpf().replaceAll("[^\\d]", "");
        locacao.setCpf(cpf);

        List<Exemplar> exemplares = exemplarRepository.findAllById(locacaoDTO.exemplaresSelecionados());
        locacao.setExemplares(exemplares);

        Locacao locacaoSalva = locacaoRepository.save(locacao);

        try {
            DateTimeFormatter formatarData = DateTimeFormatter.ISO_LOCAL_DATE;

            String jsonGambiarra = String.format(
                    "{\n" + "  \"id\":%d,\n" +
                            "  \"nome\":\"%s\",\n" +
                            "  \"cpf\":\"%s\",\n" +
                            "  \"telefone\":\"%s\",\n" +
                            "  \"dataLocacao\":\"%s\",\n" +
                            "  \"dataDevolucao\":\"%s\",\n"+"}",
                    locacaoSalva.getId(),
                    locacaoSalva.getNome(),
                    locacaoSalva.getCpf(),
                    locacaoSalva.getTelefone(),
                    locacaoSalva.getDataLocacao() != null ? locacaoSalva.getDataLocacao().format(formatarData) : null,
                    locacaoSalva.getDataDevolucao() != null ? locacaoSalva.getDataDevolucao().format(formatarData) : null
            );

            String data = jsonGambiarra;
            int size = 250;

            URI qrApiUri = UriComponentsBuilder.fromUriString(qrApiBaseUrl)
                    .queryParam("data", data)
                    .queryParam("size", size)
                    .build()
                    .toUri();

            WebClient webClient = webClientBuilder.baseUrl(qrApiBaseUrl).build();

            byte[] imageBytes = webClient.get()
                    .uri(qrApiUri)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();

            String imagem = Base64.getEncoder().encodeToString(imageBytes);
            locacaoSalva.setQrCode(imagem);
            locacaoRepository.save(locacaoSalva);

        } catch (Exception e) {
            // Preciso arrumar as mensagens de erro -- Fazer isso durante a fase de organizar o front OUU se der erro
        }
    }

    // Buscar por Termos -- Creio q eu possa usar isso na visualização do usuário
    public List<Locacao> buscarTodasLocacoes(String termo) {
        return locacaoRepository.findByLocacao(termo);
    }

    public List<Locacao> buscarLocacoesPendentes(String termo) {
        return locacaoRepository.findByLocacaoPendentes(termo);
    }

    public List<Locacao> buscarLocacoesDevolvidas(String termo) {
        return locacaoRepository.findByLocacaoDevolvidas(termo);
    }

    // Buscar por cpf
    public List<Locacao> buscarLocacoesPorCpf(String termo) {
        if (termo == null || termo.isEmpty()) {
            return List.of();
        }
        String cpfSemMascara = termo.replaceAll("[.-]", "");
        return locacaoRepository.findByCpf(cpfSemMascara);
    }

    // Devolução
    @Transactional
    public void devolucao(int id) {
        Optional<Locacao> locacaoExistente = locacaoRepository.findById(id);

        if (locacaoExistente.isPresent()) {
            Locacao locacao = locacaoExistente.get();
            LocalDate dataSistema = LocalDate.now();
            locacao.setDataDevolvido(dataSistema);
            locacaoRepository.save(locacao);
        } else {
            System.err.println("Erro: Locação com ID " + id + " não encontrada para devolução.");
        }


    }


}
