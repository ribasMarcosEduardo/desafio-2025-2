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

    @Query("""
    SELECT f.id, f.titulo,
           SUM(CASE WHEN e.ativo = true THEN 1 ELSE 0 END)
    FROM Filme f
    LEFT JOIN f.exemplares e
    WHERE f.ativo = true
    GROUP BY f.id, f.titulo
    ORDER BY f.titulo ASC
""")
    List<Object[]> findFilmesComExemplaresAtivos();

    @Query("SELECT f.id, f.titulo, COUNT(e.id) " +
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
        COUNT(CASE WHEN e.ativo = true THEN 1 ELSE NULL END) AS exemplares_disponiveis
    FROM 
        filme f
    LEFT JOIN 
        exemplar e ON e.filme_id = f.id
    GROUP BY 
        f.id, f.titulo, f.lancamento, f.pontuacao, f.ativo
    ORDER BY f.titulo ASC
    """, nativeQuery = true)
    List<Object[]> findFilmesComDetalhesEExemplaresAtivos();


}
