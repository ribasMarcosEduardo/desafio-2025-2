package locadoraFilmes.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import locadoraFilmes.application.dto.FilmeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

// Primeira vez que estou usando isso então deixarei comentado para eu entender melhor

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FilmeDTO getPopularMovie() {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=pt-BR&sort_by=popularity.desc&page=1";
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.path("results");

            if (results.size() > 0) {
                JsonNode movie = results.get(0); // Pega o primeiro filme (nesse caso o mais popular)
                String title = movie.path("title").asText(null);
                String overview = movie.path("overview").asText(null);
                String voteAverage = String.valueOf(movie.path("vote_average").asDouble(0.0));
                String releaseDateStr = movie.path("release_date").asText(null);
                LocalDate releaseDate = releaseDateStr != null && !releaseDateStr.isEmpty()
                        ? LocalDate.parse(releaseDateStr)
                        : null;

                return new FilmeDTO(
                        0, // id (definido pelo sistema)
                        true, // ativo (padrão)
                        0, // exemplares_disponiveis (editável)
                        title,
                        overview,
                        voteAverage,
                        releaseDate
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Retorna null se a chamada falhar
    }
}