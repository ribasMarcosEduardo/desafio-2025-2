package locadoraFilmes.application.controller;

import locadoraFilmes.application.service.LocacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/geral")
public class GeralController {

    private final LocacaoService locacaoService;

    @GetMapping("/menuPrincipal")
    public String listarFilmes() {
        return "principal/menu"; // http://localhost:8080/geral/menuPrincipal
    }

    @GetMapping("/login")
    public String login() {
        return "principal/login"; // http://localhost:8080/geral/login
    }

    // Rel Quantidade de locações
    @GetMapping("/buscarContagemAlugueis")
    public String buscarContagemAlugueisPorTitulo(
            @RequestParam(value = "tituloFilme", required = false, defaultValue = "") String tituloFilme,
            Model model) {

        List<Object[]> exemplaresComContagem = locacaoService.buscarContagemAlugueisPorTituloFilme(tituloFilme);

        model.addAttribute("exemplaresComContagem", exemplaresComContagem);

        model.addAttribute("tituloBuscado", tituloFilme);

        return "rel/relQuantAlugado"; // http://localhost:8080/geral/buscarContagemAlugueis
    }

}
