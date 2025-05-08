package locadoraFilmes.application.controller;

import locadoraFilmes.application.dto.LocacaoDTO;
import locadoraFilmes.application.model.Locacao;
import locadoraFilmes.application.service.FilmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/locacao")
public class LocacaoController {

    private final FilmeService filmeService;

    @GetMapping("/novaLocacao")
    public String novaLocacao(Model model) {
        model.addAttribute("locacaoDTO", new LocacaoDTO(0, null, null, null, null, null, null, null));
        model.addAttribute("filmes", filmeService.listarFilmes());
        return "cadastros/novaLocacao";
    }

}
