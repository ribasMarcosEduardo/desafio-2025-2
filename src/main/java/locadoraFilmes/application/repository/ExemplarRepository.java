package locadoraFilmes.application.repository;

import locadoraFilmes.application.model.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Integer> {

    List<Exemplar> findByFilmeId(int filmeId); // Vou deixar aq vai que eu use

    List<Exemplar> findByFilmeIdAndAtivoTrue(int filmeId);

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


}
