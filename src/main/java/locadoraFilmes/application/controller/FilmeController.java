package locadoraFilmes.application.controller;


import locadoraFilmes.application.dto.FilmeDTO;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.service.FilmeService;
import locadoraFilmes.application.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/filme")
public class FilmeController {

    private final TmdbService tmdbService;
    private final FilmeService filmeService;

    // Cadastro de Filme -- Tela
    @GetMapping("/cadastroFilme")
    public String cadastroFilme(Model model) {
        FilmeDTO filmeDTO = tmdbService.getPopularMovie();
        model.addAttribute("filmeDTO", filmeDTO != null ? filmeDTO : new FilmeDTO(0, true, 0, null, null, null, null));
        return "cadastros/cadastroFilme"; // http://localhost:8080/filme/cadastroFilme
    }

    // Cadastro de Filme -- Salvar filme
    @PostMapping("/salvarFilme")
    public String salvarFilme(@ModelAttribute FilmeDTO filmeDTO, RedirectAttributes redirectAttributes) {
        Filme filme = filmeDTO.mapearFilme();
        filmeService.salvarFilme(filme);
        redirectAttributes.addFlashAttribute("Sucesso", "Filme cadastrado com sucesso!");
        return "redirect:/filme/cadastroFilme";
    }

    // Listagem de Filmes
    @GetMapping("/listarFilmes")
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", filmeService.listarFilmes());
        return "listas/listarFilmes"; // http://localhost:8080/filme/listarFilmes
    }

    // Listagem de Filmes -- Edição
    @GetMapping("/listarFilmesEdit")
    public String listarFilmesEdit(Model model) {
        model.addAttribute("filmes", filmeService.listarFilmesEdit());
        return "edits/listarFilmesEdit"; // http://localhost:8080/filme/listarFilmesEdit
    }

    // Excluir Filmes -- Botão
    @PostMapping("/excluirFilmes/{id}")
    public String excluirFilmes(@PathVariable int id, RedirectAttributes redirectAttributes) {
        filmeService.excluirFilmes(id);
        redirectAttributes.addFlashAttribute("Sucesso", "Filme excluído com sucesso!");
        return "redirect:/filme/listarFilmesEdit";
    }

    // Alterar Status de Filmes
    @PostMapping("/alterarStatusFilmes/{id}")
    public String alterarStatusFilmes(@PathVariable int id, RedirectAttributes redirectAttributes) {
        filmeService.alterarStatusFilmes(id);
        redirectAttributes.addFlashAttribute("Sucesso", "Status do filme alterado com sucesso!");
        return "redirect:/filme/listarFilmesEdit";
    }


}





