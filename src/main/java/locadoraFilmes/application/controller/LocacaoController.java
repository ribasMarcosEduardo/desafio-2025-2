package locadoraFilmes.application.controller;

import locadoraFilmes.application.dto.LocacaoDTO;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Locacao;
import locadoraFilmes.application.repository.ExemplarRepository;
import locadoraFilmes.application.repository.LocacaoRepository;
import locadoraFilmes.application.service.ExemplarService;
import locadoraFilmes.application.service.FilmeService;
import locadoraFilmes.application.service.LocacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
@RequestMapping("/locacao")
public class LocacaoController {

    private final FilmeService filmeService;
    private final ExemplarService exemplarService;
    private final LocacaoService locacaoService;

    // Tela Inicial Nova Locação -- Modal Filmes
    @GetMapping("/novaLocacao")
    public String novaLocacao(Model model) {
        model.addAttribute("locacaoDTO", new LocacaoDTO(0, null, null, null, null, null, null, null, null));
        model.addAttribute("filmes", filmeService.listarFilmesComExemplaresTrue());
        return "cadastros/novaLocacao";
    }

    // Modal Exemplares
    @GetMapping("/exemplaresPorTitulo")
    @ResponseBody
    public List<Exemplar> listarExemplaresPorTitulo(@RequestParam("titulo") String titulo) {
        List<Exemplar> exemplares = exemplarService.listarExemplaresPorTituloFilmeTrue(titulo);
        return exemplares;
    }

    // Cadastrar uma nova Locação
    @PostMapping("/salvarLocacao")
    public String salvarLocacao(@ModelAttribute LocacaoDTO locacaoDTO, RedirectAttributes redirectAttributes) {
        locacaoService.salvarLocacao(locacaoDTO);
        redirectAttributes.addFlashAttribute("Sucesso", "Locação salva com sucesso!");
        return "redirect:/locacao/novaLocacao";
    }

    // Busca
    @GetMapping("/buscar")
    public String buscar(
            @RequestParam(value = "termo", required = false, defaultValue = "") String termo,
            @RequestParam(value = "filtro", required = false, defaultValue = "todos") String filtro,
            Model model) {
        List<Locacao> locacoes;
        if (termo != null) {
            switch (filtro.toLowerCase()) {
                case "pendentes":
                    locacoes = locacaoService.buscarLocacoesPendentes(termo);
                    break;
                case "devolvidos":
                    locacoes = locacaoService.buscarLocacoesDevolvidas(termo);
                    break;
                case "todos":
                default:
                    locacoes = locacaoService.buscarTodasLocacoes(termo);
                    break;
            }
        } else {
            locacoes = new ArrayList<>();
        }
        model.addAttribute("locacoes", locacoes);
        model.addAttribute("param.termo", termo);
        model.addAttribute("filtroSelecionado", filtro);
        return "edits/finalizarLocacao"; // http://localhost:8080/locacao/buscar
    }

    // Realizar Devolução
    @PostMapping("/realizarDevolucao/{id}")
    public String realizarDevolucao(
            @PathVariable int id,
            @RequestParam(value = "termo", required = false, defaultValue = "") String termo,
            RedirectAttributes redirectAttributes) {
        locacaoService.devolucao(id);
        redirectAttributes.addFlashAttribute("Sucesso", "Devolução Concluída!");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/locacao/buscar");

        uriBuilder.queryParam("termo", termo);

        return "redirect:" + uriBuilder.build().encode().toUriString();
    }

    // tela de busca cliente
    @GetMapping("/buscarTelaCliente")
    public String buscarTelaCliente(
            @RequestParam(value = "termo", required = false, defaultValue = "") String termo,
            Model model) {
        List<Locacao> locacoes = locacaoService.buscarLocacoesPorCpf(termo);
        model.addAttribute("locacoes", locacoes);
        return "listas/buscarTelaCliente";
    }

}