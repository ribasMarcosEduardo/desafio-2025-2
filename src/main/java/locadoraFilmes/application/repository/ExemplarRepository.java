package locadoraFilmes.application.repository;

import locadoraFilmes.application.model.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Integer> {

    @Query("SELECT e FROM Exemplar e JOIN e.filme f WHERE f.titulo LIKE %:titulo%")
    List<Exemplar> findByFilmeTitulo(@Param("titulo") String titulo);

    @Query("SELECT e FROM Exemplar e " +
            "JOIN e.filme f " +
            "WHERE f.titulo LIKE %:titulo% " +
            "AND e.ativo = true " +
            "AND NOT EXISTS ( " +
            "   SELECT 1 FROM Locacao l " +
            "   JOIN l.exemplares le " +
            "   WHERE le = e AND l.dataDevolvido IS NULL " +
            ")")
    List<Exemplar> findByFilmeTituloAndAtivoTrue(@Param("titulo") String titulo);

    @Query("SELECT DISTINCT e FROM Exemplar e " +
            "JOIN e.filme f " +
            "JOIN e.locacoes l " +
            "WHERE f.titulo LIKE %:titulo% " +
            "AND l.dataDevolvido IS NULL")
    List<Exemplar> findByFilmeTituloAndLocacao(@Param("titulo") String titulo);

    @Query("SELECT e.id FROM Exemplar e JOIN e.locacoes l WHERE e.id = :exemplarId AND l.dataDevolvido IS NULL")
    Optional<Integer> findExemplarIdIfInActiveLocacao(@Param("exemplarId") int exemplarId);

    @Query("SELECT COUNT(e) FROM Exemplar e WHERE e.filme.id = :filmeId AND e.ativo = TRUE")
    int countActiveExemplaresByFilmeId(@Param("filmeId") int filmeId);

    @Query("SELECT COUNT(e) FROM Exemplar e JOIN e.filme f JOIN e.locacoes l WHERE l.dataDevolvido IS NOT NULL AND f.id = :filmeId")
    int countExemplaresDevolvidosByFilmeId(@Param("filmeId") int filmeId);

    @Query("SELECT e, COUNT(l) FROM Exemplar e LEFT JOIN e.locacoes l JOIN e.filme f WHERE f.titulo = :tituloFilme GROUP BY e")
    List<Object[]> findExemplarsWithRentalCountByFilmeTitulo(@Param("tituloFilme") String tituloFilme);


}
