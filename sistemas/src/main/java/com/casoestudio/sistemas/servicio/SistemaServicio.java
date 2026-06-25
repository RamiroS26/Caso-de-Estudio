package com.casoestudio.sistemas.servicio;

import com.casoestudio.sistemas.modelo.Oficina;
import com.casoestudio.sistemas.modelo.Sistema;
import com.casoestudio.sistemas.repositorio.OficinaRepositorio;
import com.casoestudio.sistemas.repositorio.SistemaRepositorio;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SistemaServicio {

    private final SistemaRepositorio sistemaRepositorio;
    private final OficinaRepositorio oficinaRepositorio;

    public SistemaServicio(SistemaRepositorio sistemaRepositorio, OficinaRepositorio oficinaRepositorio) {
        this.sistemaRepositorio = sistemaRepositorio;
        this.oficinaRepositorio = oficinaRepositorio;
    }

    @Transactional(readOnly = true)
    public List<Sistema> listarTodos() {
        return sistemaRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Sistema obtenerPorId(Integer id) {
        return sistemaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el sistema con id " + id));
    }

    public Sistema guardar(Sistema sistema) {
        sistema.setOficina(obtenerOficina(sistema));
        return sistemaRepositorio.save(sistema);
    }

    public Sistema actualizar(Integer id, Sistema datos) {
        Sistema sistema = obtenerPorId(id);
        sistema.setNombre(datos.getNombre());
        sistema.setAutor(datos.getAutor());
        sistema.setDescripcion(datos.getDescripcion());
        sistema.setAnio(datos.getAnio());
        sistema.setOficina(obtenerOficina(datos));
        return sistemaRepositorio.save(sistema);
    }

    public void eliminar(Integer id) {
        Sistema sistema = obtenerPorId(id);
        sistemaRepositorio.delete(sistema);
    }

    private Oficina obtenerOficina(Sistema sistema) {
        Integer oficinaId = sistema.getOficina() != null ? sistema.getOficina().getId() : null;
        if (oficinaId == null) {
            throw new IllegalArgumentException("Debe seleccionar una oficina.");
        }

        return oficinaRepositorio.findById(oficinaId)
                .orElseThrow(() -> new EntityNotFoundException("No existe la oficina con id " + oficinaId));
    }
}
