package locadoraFilmes.application.repository;

import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Integer> {

    @Query("SELECT e FROM Exemplar e JOIN e.filme f WHERE f.titulo LIKE %:titulo%")
    List<Exemplar> findByFilmeTitulo(@Param("titulo") String titulo);

}
