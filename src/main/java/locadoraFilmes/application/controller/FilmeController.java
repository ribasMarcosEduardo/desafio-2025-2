package locadoraFilmes.application.controller;


import locadoraFilmes.application.dto.FilmeDTO;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.service.FilmeService;
import locadoraFilmes.application.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/filme")
public class FilmeController {

     private final TmdbService tmdbService;
     private final FilmeService filmeService;

    // Cadastro de Filme -- Tela
    @GetMapping("/cadastroFilme")
    public String cadastroFilme(Model model){
        FilmeDTO filmeDTO = tmdbService.getPopularMovie();
        model.addAttribute("filmeDTO", filmeDTO != null ? filmeDTO : new FilmeDTO(0, true, 0, null, null, null, null));
        return "cadastros/cadastroFilme"; // http://localhost:8080/filme/cadastroFilme
    }

    // Cadastro de Filme -- Salvar filme
    @PostMapping("/salvarFilme")
    public String salvarFilme(@ModelAttribute FilmeDTO filmeDTO, RedirectAttributes redirectAttributes){
        Filme filme = filmeDTO.mapearFilme();
        filmeService.salvarFilme(filme);
        redirectAttributes.addFlashAttribute("Sucesso", "Filme cadastrado com sucesso!");
        return "redirect:/filme/cadastroFilme";
    }

    // Listagem de Filmes
    @GetMapping("/listarFilmes")
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", filmeService.listarFilmes());
        return "cadastros/listarFilmes"; // http://localhost:8080/filme/listarFilmes
    }


}
