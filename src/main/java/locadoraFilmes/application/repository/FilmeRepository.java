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

    @Query("SELECT f.id,f.titulo, COUNT(e.id) " +
            "FROM Filme f JOIN f.exemplares e " +
            "GROUP BY f.id, f.titulo")
    List<Object[]> findFilmesComExemplares();
}
