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
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;
    private final ExemplarRepository exemplarRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${apgy.qr.api.url}")
    private String qrApiBaseUrl;

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
                    locacaoSalva.getCpf(),
                    locacaoSalva.getNome(),
                    locacaoSalva.getTelefone(),
                    locacaoSalva.getDataLocacao() != null ? locacaoSalva.getDataLocacao().format(formatarData) : null,
                    locacaoSalva.getDataDevolucao() != null ? locacaoSalva.getDataDevolucao().format(formatarData) : null
            );

            String data = jsonGambiarra; // Conteúdo do QR

            int size = 250; // Tamano do QR

            // Construir a URI antes do WebClient -- Deu problema isso antes (Eu n sabia)
            URI qrApiUri = UriComponentsBuilder.fromUriString(qrApiBaseUrl)
                    .queryParam("data", data)
                    .queryParam("size", size)
                    .build()
                    .toUri();
            // ------------------------------------------------------------------

            WebClient webClient = webClientBuilder.baseUrl(qrApiBaseUrl).build();

            byte[] imageBytes = webClient.get()
                    .uri(qrApiUri) // Passa o objeto URI já construído
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
}
