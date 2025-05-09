package locadoraFilmes.application.controller;

import locadoraFilmes.application.dto.LocacaoDTO;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Locacao;
import locadoraFilmes.application.service.ExemplarService;
import locadoraFilmes.application.service.FilmeService;
import locadoraFilmes.application.service.LocacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        model.addAttribute("locacaoDTO", new LocacaoDTO(0, null, null, null, null, null, null, null,null));
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

    @PostMapping("/salvarLocacao")
    public String salvarLocacao(@ModelAttribute LocacaoDTO locacaoDTO, RedirectAttributes redirectAttributes) {
        locacaoService.salvarLocacao(locacaoDTO);
        redirectAttributes.addFlashAttribute("Sucesso", "Locação salva com sucesso!");
        return "redirect:/locacao/novaLocacao";
    }
}