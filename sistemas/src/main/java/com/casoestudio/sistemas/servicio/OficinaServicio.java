package com.casoestudio.sistemas.servicio;

import com.casoestudio.sistemas.modelo.Oficina;
import com.casoestudio.sistemas.repositorio.OficinaRepositorio;
import com.casoestudio.sistemas.repositorio.SistemaRepositorio;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OficinaServicio {

    private final OficinaRepositorio oficinaRepositorio;
    private final SistemaRepositorio sistemaRepositorio;

    public OficinaServicio(OficinaRepositorio oficinaRepositorio, SistemaRepositorio sistemaRepositorio) {
        this.oficinaRepositorio = oficinaRepositorio;
        this.sistemaRepositorio = sistemaRepositorio;
    }

    @Transactional(readOnly = true)
    public List<Oficina> listarTodas() {
        return oficinaRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Oficina obtenerPorId(Integer id) {
        return oficinaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la oficina con id " + id));
    }

    public Oficina guardar(Oficina oficina) {
        return oficinaRepositorio.save(oficina);
    }

    public Oficina actualizar(Integer id, Oficina datos) {
        Oficina oficina = obtenerPorId(id);
        oficina.setNombre(datos.getNombre());
        return oficinaRepositorio.save(oficina);
    }

    public boolean eliminar(Integer id) {
        Oficina oficina = obtenerPorId(id);
        if (sistemaRepositorio.countByOficina_Id(id) > 0) {
            return false;
        }

        oficinaRepositorio.delete(oficina);
        return true;
    }
}
