package locadoraFilmes.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import locadoraFilmes.application.dto.FilmeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    public FilmeDTO getPopularMovie() {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=pt-BR&sort_by=popularity.desc&page=1";
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.path("results");

            if (results.size() > 0) {
                int randomFilme = random.nextInt(results.size());
                JsonNode movie = results.get(randomFilme);
                String title = movie.path("title").asText(null);
                String overview = movie.path("overview").asText(null);
                String voteAverage = String.valueOf(movie.path("vote_average").asDouble(0.0));
                String releaseDateStr = movie.path("release_date").asText(null);

                LocalDate releaseDate = null;
                if (releaseDateStr != null && !releaseDateStr.isEmpty()) {
                    try {
                        releaseDate = LocalDate.parse(releaseDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                    } catch (DateTimeParseException e) {
                        System.err.println("Erro ao parsear data: " + releaseDateStr + " - " + e.getMessage());
                    }
                }

                return new FilmeDTO(
                        0,
                        true,
                        0,
                        title,
                        overview,
                        voteAverage,
                        releaseDate
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}