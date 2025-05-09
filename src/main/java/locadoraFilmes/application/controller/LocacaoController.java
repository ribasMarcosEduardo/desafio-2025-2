package locadoraFilmes.application.controller;

import locadoraFilmes.application.dto.LocacaoDTO;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.service.ExemplarService;
import locadoraFilmes.application.service.FilmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/locacao")
public class LocacaoController {

    private final FilmeService filmeService;
    private final ExemplarService exemplarService;

    // Tela Inicial Nova Locação -- Modal Filmes
    @GetMapping("/novaLocacao")
    public String novaLocacao(Model model) {
        model.addAttribute("locacaoDTO", new LocacaoDTO(0, null, null, null, null, null, null, null));
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
}