package com.casoestudio.sistemas.controlador;

import com.casoestudio.sistemas.modelo.Oficina;
import com.casoestudio.sistemas.servicio.OficinaServicio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/oficinas")
public class OficinaControlador {

    private final OficinaServicio oficinaServicio;

    public OficinaControlador(OficinaServicio oficinaServicio) {
        this.oficinaServicio = oficinaServicio;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("oficinas", oficinaServicio.listarTodas());
        return "oficinas/listar";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("oficina", new Oficina());
        return "oficinas/crear";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("oficina") Oficina oficina,
                          RedirectAttributes redirectAttributes) {
        oficinaServicio.guardar(oficina);
        redirectAttributes.addFlashAttribute("mensaje", "Oficina guardada correctamente.");
        return "redirect:/oficinas/listar";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("oficina", oficinaServicio.obtenerPorId(id));
            return "oficinas/detalle";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/oficinas/listar";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("oficina", oficinaServicio.obtenerPorId(id));
            return "oficinas/editar";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/oficinas/listar";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute("oficina") Oficina oficina,
                             RedirectAttributes redirectAttributes) {
        try {
            oficinaServicio.actualizar(id, oficina);
            redirectAttributes.addFlashAttribute("mensaje", "Oficina actualizada correctamente.");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/oficinas/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String confirmarEliminacion(@PathVariable Integer id,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("oficina", oficinaServicio.obtenerPorId(id));
            return "oficinas/eliminar";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/oficinas/listar";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            boolean eliminada = oficinaServicio.eliminar(id);
            if (eliminada) {
                redirectAttributes.addFlashAttribute("mensaje", "Oficina eliminada correctamente.");
            } else {
                redirectAttributes.addFlashAttribute("error", "No se puede eliminar una oficina con sistemas asociados.");
            }
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/oficinas/listar";
    }
}
