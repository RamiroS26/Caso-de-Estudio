package com.casoestudio.sistemas.repositorio;

import com.casoestudio.sistemas.modelo.Sistema;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaRepositorio extends JpaRepository<Sistema, Integer> {

    @Override
    @EntityGraph(attributePaths = "oficina")
    List<Sistema> findAll();

    @Override
    @EntityGraph(attributePaths = "oficina")
    Optional<Sistema> findById(Integer id);

    long countByOficina_Id(Integer oficinaId);
}
