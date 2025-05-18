package locadoraFilmes.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/geral")
public class GeralController {


    @GetMapping("/menuPrincipal")
    public String listarFilmes() {
        return "principal/menu"; // http://localhost:8080/geral/menuPrincipal
    }

}
