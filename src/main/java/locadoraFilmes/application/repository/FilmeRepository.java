package locadoraFilmes.application.repository;

import locadoraFilmes.application.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {

    Optional<Filme> findByTitulo(String titulo);

    // Exibição filmes disponíveis
    @Query(
            "SELECT f.id, f.titulo, COUNT(e.id) " +
            "FROM Filme f " +
            "JOIN f.exemplares e " +
            "WHERE f.ativo = true " +
            "AND e.ativo = true " +
            "AND NOT EXISTS (" +
            "   SELECT 1 FROM Locacao l " +
            "   JOIN l.exemplares le " +
            "   WHERE le = e AND l.dataDevolvido IS NULL" +
            ") " +
            "GROUP BY f.id, f.titulo, f.lancamento " +
            "HAVING COUNT(e.id) > 0 " +
            "ORDER BY f.titulo ASC")
    List<Object[]> findFilmesAtivosComExemplaresDisponiveis();

    @Query(value = """
            SELECT
                f.id,
                f.titulo,
                f.lancamento,
                f.pontuacao,
                f.ativo,
                COUNT(e.id) AS exemplares_disponiveis
            FROM
                filme f
            LEFT JOIN
                exemplar e ON e.filme_id = f.id
            WHERE
                e.ativo = TRUE
                AND NOT EXISTS (
                    SELECT 1
                    FROM locacao l_sub
                    JOIN locacao_exemplar le_sub ON l_sub.id = le_sub.locacao_id
                    WHERE le_sub.exemplar_id = e.id
                    AND l_sub.data_devolvido IS NULL
                )
            GROUP BY
                f.id, f.titulo, f.lancamento, f.pontuacao, f.ativo
            ORDER BY
                f.titulo ASC
            """, nativeQuery = true)
    List<Object[]> findFilmesComDetalhesEExemplaresAtivos();

    // Lista de edição de filmes
    @Query(value = """
            SELECT
                f.id,
                f.titulo,
                f.lancamento,
                f.pontuacao,
                f.ativo,
                COUNT(CASE
                          WHEN e.ativo = TRUE AND NOT EXISTS (
                              SELECT 1
                              FROM locacao l_sub
                              JOIN locacao_exemplar le_sub ON l_sub.id = le_sub.locacao_id
                              WHERE le_sub.exemplar_id = e.id
                              AND l_sub.data_devolvido IS NULL
                          ) THEN e.id
                          ELSE NULL
                      END) AS exemplares_disponiveis
            FROM
                filme f
            LEFT JOIN
                exemplar e ON e.filme_id = f.id
            WHERE
                f.ativo = TRUE
            GROUP BY
                f.id, f.titulo, f.lancamento, f.pontuacao, f.ativo
            ORDER BY
                f.titulo ASC
            """, nativeQuery = true)
    List<Object[]> findFilmesComDetalhesEContagemDeExemplaresDisponiveis();

}
