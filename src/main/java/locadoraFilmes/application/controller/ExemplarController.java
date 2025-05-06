package locadoraFilmes.application.controller;

import locadoraFilmes.application.dto.ExemplarDTO;
import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;
import locadoraFilmes.application.repository.FilmeRepository;
import locadoraFilmes.application.service.ExemplarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/exemplar")
public class ExemplarController {

    private FilmeRepository filmeRepository;
    private final ExemplarService exemplarService;

    //  Visualização de Exemplares -- Lista
    @GetMapping("/listarExemplares")
    public String listarExemplares(@RequestParam(required = false) String tituloFilme,
                                   Model model) {
        List<Exemplar> exemplares;

        if (tituloFilme != null && !tituloFilme.isEmpty()) {
            exemplares = exemplarService.listarExemplaresPorTituloFilme(tituloFilme.trim());
        } else {
            exemplares = new ArrayList<>();
        }

        model.addAttribute("exemplares", exemplares);
        model.addAttribute("tituloFiltrado", tituloFilme);
        return "listas/listarExemplares";
    }

    // Cadastro de um novo exemplar -- Botão
    @PostMapping("/cadastrarExemplar")
    public String cadastrarExemplar(@RequestParam String tituloFilme, RedirectAttributes redirectAttributes) {
        exemplarService.cadastrarExemplar(tituloFilme, true);
        redirectAttributes.addFlashAttribute("mensagem", "Exemplar cadastrado com sucesso!");
        return "redirect:/exemplar/listarExemplares?tituloFilme=" + tituloFilme; // eu preciso arrumar esse suposto problema de cache relacionado a essa parte
    }

    // Excluir Exemplar -- Botão
    @PostMapping("/excluirExemplar/{id}")
    public String excluirExemplar(@PathVariable int id, @RequestParam(required = false) String tituloFilme, RedirectAttributes redirectAttributes) {
        exemplarService.excluirExemplar(id);
        redirectAttributes.addFlashAttribute("Sucesso", "Exemplar excluído com sucesso!"); // Tenho q ajustar essa mensagem no front
        return "redirect:/exemplar/listarExemplares?tituloFilme=" + tituloFilme;
    }

    // Alterar Status do Exemplar
    @PostMapping("/alterarStatus/{id}")
    public String alterarStatus(@PathVariable int id, @RequestParam(required = false) String tituloFilme, RedirectAttributes redirectAttributes) {
        exemplarService.alterarStatus(id);
        redirectAttributes.addFlashAttribute("Sucesso", "Exemplar excluído com sucesso!"); // Tenho q ajustar essa mensagem no front
        return "redirect:/exemplar/listarExemplares?tituloFilme=" + tituloFilme;
    }










}
