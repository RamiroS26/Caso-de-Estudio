package com.casoestudio.sistemas.repositorio;

import com.casoestudio.sistemas.modelo.Oficina;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OficinaRepositorio extends JpaRepository<Oficina, Integer> {

    @Override
    @EntityGraph(attributePaths = "sistemas")
    List<Oficina> findAll();

    @Override
    @EntityGraph(attributePaths = "sistemas")
    Optional<Oficina> findById(Integer id);
}
