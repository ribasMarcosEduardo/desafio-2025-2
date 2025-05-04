package locadoraFilmes.application.controller;

import locadoraFilmes.application.dto.ExemplarDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/exemplar")
public class ExemplarController {

    @GetMapping("/cadastroExemplar")
    public String cadastroExemplar(Model model) {
        ExemplarDTO exemplarDTO = new ExemplarDTO(0, null, LocalDate.now(), true);
        model.addAttribute("exemplarDTO", exemplarDTO);
        return "cadastros/cadastroExemplar"; // http://localhost:8080/exemplar/cadastroExemplar
    }





}
