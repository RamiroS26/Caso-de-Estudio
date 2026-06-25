package com.casoestudio.sistemas.config;

import com.casoestudio.sistemas.modelo.Oficina;
import com.casoestudio.sistemas.modelo.Sistema;
import com.casoestudio.sistemas.repositorio.OficinaRepositorio;
import com.casoestudio.sistemas.repositorio.SistemaRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// CLASE PARA CREAR DATOS DE PRUEBA CUANDO SE INICIA LA APLICACION POR PRIMERA VEZ

@Component
public class DatosIniciales implements CommandLineRunner {

    private final OficinaRepositorio oficinaRepositorio;
    private final SistemaRepositorio sistemaRepositorio;

    public DatosIniciales(OficinaRepositorio oficinaRepositorio, SistemaRepositorio sistemaRepositorio) {
        this.oficinaRepositorio = oficinaRepositorio;
        this.sistemaRepositorio = sistemaRepositorio;
    }

    @Override
    public void run(String... args) {
        if (oficinaRepositorio.count() > 0 || sistemaRepositorio.count() > 0) {         // VALIDAR QUE NO EXISTAN ANTES DE CREAR PARA EVITAR DUPLICACION
            return;
        }

        Oficina mesaEntrada = crearOficina("Mesa de Entrada");
        Oficina direccionSistemas = crearOficina("Direccion Sistemas");
        Oficina recursosHumanos = crearOficina("Recursos Humanos");

        sistemaRepositorio.save(crearSistema(
                "Sistema de Expedientes",
                "Equipo Desarrollo",
                "Gestion de expedientes internos",
                2024,
                mesaEntrada));

        sistemaRepositorio.save(crearSistema(
                "Inventario TI",
                "Direccion Sistemas",
                "Control de equipamiento informatico",
                2025,
                direccionSistemas));

        sistemaRepositorio.save(crearSistema(
                "Legajos Digitales",
                "Recursos Humanos",
                "Administracion de legajos del personal",
                2023,
                recursosHumanos));
    }

    private Oficina crearOficina(String nombre) {
        Oficina oficina = new Oficina();
        oficina.setNombre(nombre);
        return oficinaRepositorio.save(oficina);
    }

    private Sistema crearSistema(String nombre, String autor, String descripcion, Integer anio, Oficina oficina) {
        Sistema sistema = new Sistema();
        sistema.setNombre(nombre);
        sistema.setAutor(autor);
        sistema.setDescripcion(descripcion);
        sistema.setAnio(anio);
        sistema.setOficina(oficina);
        return sistema;
    }
}
