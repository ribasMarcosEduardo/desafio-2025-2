package locadoraFilmes.application.exeption;

public class FilmeDuplicado extends RuntimeException {
    public FilmeDuplicado(String message){
        super(message);
    }
}
