package com.casoestudio.sistemas.controlador;

import com.casoestudio.sistemas.modelo.Sistema;
import com.casoestudio.sistemas.servicio.OficinaServicio;
import com.casoestudio.sistemas.servicio.SistemaServicio;
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
@RequestMapping("/sistemas")
public class SistemaControlador {

    private final SistemaServicio sistemaServicio;
    private final OficinaServicio oficinaServicio;

    public SistemaControlador(SistemaServicio sistemaServicio, OficinaServicio oficinaServicio) {
        this.sistemaServicio = sistemaServicio;
        this.oficinaServicio = oficinaServicio;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("sistemas", sistemaServicio.listarTodos());
        return "sistemas/listar";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("sistema", new Sistema());
        cargarOficinas(model);
        return "sistemas/crear";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("sistema") Sistema sistema,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            sistemaServicio.guardar(sistema);
            redirectAttributes.addFlashAttribute("mensaje", "Sistema guardado correctamente.");
            return "redirect:/sistemas/listar";    
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            cargarOficinas(model);
            return "sistemas/crear";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("sistema", sistemaServicio.obtenerPorId(id));
            return "sistemas/detalle";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/sistemas/listar";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("sistema", sistemaServicio.obtenerPorId(id));
            cargarOficinas(model);
            return "sistemas/editar";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/sistemas/listar";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute("sistema") Sistema sistema,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            sistemaServicio.actualizar(id, sistema);
            redirectAttributes.addFlashAttribute("mensaje", "Sistema actualizado correctamente.");
            return "redirect:/sistemas/listar";
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            sistema.setId(id);
            model.addAttribute("error", ex.getMessage());
            cargarOficinas(model);
            return "sistemas/editar";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String confirmarEliminacion(@PathVariable Integer id,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("sistema", sistemaServicio.obtenerPorId(id));
            return "sistemas/eliminar";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/sistemas/listar";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            sistemaServicio.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Sistema eliminado correctamente.");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/sistemas/listar";
    }

    private void cargarOficinas(Model model) {
        model.addAttribute("oficinas", oficinaServicio.listarTodas());
    }
}
