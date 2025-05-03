package locadoraFilmes.application.controller;


import locadoraFilmes.application.dto.FilmeDTO;
import locadoraFilmes.application.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/filme")
public class FilmeController {

    @Autowired
     private TmdbService tmdbService;

    // Cadastro de Filme
    @GetMapping("/cadastroFilme")
    public String cadastroFilme(Model model){
        FilmeDTO filmeDTO = tmdbService.getPopularMovie();
        model.addAttribute("filmeDTO", filmeDTO != null ? filmeDTO : new FilmeDTO(0, true, 0, null, null, null, null));
        return "cadastros/cadastroFilme"; // http://localhost:8080/filme/cadastroFilme
    }


}
