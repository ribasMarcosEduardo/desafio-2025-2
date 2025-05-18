package locadoraFilmes.application.repository;

import locadoraFilmes.application.model.Exemplar;
import locadoraFilmes.application.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {

    @Query("SELECT DISTINCT l FROM Locacao l " +
            "JOIN l.exemplares ex " +
            "JOIN ex.filme f " +
            "WHERE (:termo = '' OR " +
            "LOWER(l.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(l.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(l.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(f.titulo) LIKE LOWER(CONCAT('%', :termo, '%')))")
    List<Locacao> findByLocacao(@Param("termo") String termo);

    @Query("SELECT DISTINCT l FROM Locacao l " +
            "JOIN l.exemplares ex " +
            "JOIN ex.filme f " +
            "WHERE (:termo = '' OR " +
            "LOWER(l.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(l.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(l.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(f.titulo) LIKE LOWER(CONCAT('%', :termo, '%'))) " +
            "AND l.dataDevolvido IS NULL")
    List<Locacao> findByLocacaoPendentes(@Param("termo") String termo);

    @Query("SELECT DISTINCT l FROM Locacao l " +
            "JOIN l.exemplares ex " +
            "JOIN ex.filme f " +
            "WHERE (:termo = '' OR " +
            "LOWER(l.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(l.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(l.email) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(f.titulo) LIKE LOWER(CONCAT('%', :termo, '%'))) " +
            "AND l.dataDevolvido IS NOT NULL")
    List<Locacao> findByLocacaoDevolvidas(@Param("termo") String termo);

    @Query("SELECT DISTINCT l FROM Locacao l " +
            "JOIN l.exemplares ex " +
            "JOIN ex.filme f " +
            "WHERE (:termo = '' OR " +
            "LOWER(l.cpf) LIKE LOWER(CONCAT('%', :termo, '%')))")
    List<Locacao> findByCpf(@Param("termo") String termo);

    List<Locacao> findByExemplaresContains(Exemplar exemplar);
}


