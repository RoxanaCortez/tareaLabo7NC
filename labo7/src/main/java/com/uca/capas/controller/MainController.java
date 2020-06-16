package com.uca.capas.controller;

import java.util.List;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.domain.Estudiante;
import com.uca.capas.dto.EstudianteDTO;
import com.uca.capas.service.EstudianteService;

@Controller
public class MainController {
	Logger log = Logger.getLogger(MainController.class.getName());
	
	@Autowired 
	private EstudianteService estudianteService;
	
	@RequestMapping("/inicio")	
	public ModelAndView initMain() {	
		Estudiante estudiante = new Estudiante();
		ModelAndView mav = new ModelAndView();
		mav.addObject("estudiante", estudiante);
		mav.setViewName("index");
		return mav;
	}
	

	@RequestMapping("/ingresarEstudiante")
	public ModelAndView ingresarEstudiante(@Valid  @ModelAttribute Estudiante estudiante, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if(result.hasErrors()) { 
			mav.setViewName("index");
		}else {	
		estudianteService.guardar(estudiante);
		mav.setViewName("redirect:/listado");
		}
		return mav;
	}

		
	@RequestMapping("/listado")
	public ModelAndView listado() {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;
		try {
			estudiantes = estudianteService.listarAsc();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("listado");
		return mav;
	}
	
	@PostMapping(value="/Estudiante", params="action=eliminar")
	public ModelAndView delete(@RequestParam(value="c") Integer c) {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;
		try {
			estudianteService.eliminar(c);
			log.info("Eliminado");
		}
		catch(Exception e) {
			log.info("Error: "+e.toString());
		}
		estudiantes = estudianteService.listarAsc();
		
		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("listado");
		return mav;
	}
	
	
	@PostMapping(value="/filtrar")
	public ModelAndView filtro(@RequestParam(value="nombre") String cadena) {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;
		try {
			estudiantes = estudianteService.filtrarPor(cadena);
			//estudiantes = estudianteService.empiezaCon(cadena);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("listado");
		
		return mav;
	}
	
	@PostMapping(value="/mostrarDTO")
	public ModelAndView mostrarDTO() {
		ModelAndView mav = new ModelAndView();
		List<EstudianteDTO> estudiantes = null;
		try {
			estudiantes = estudianteService.dtoPrueba();
			//estudiantes = estudianteService .empiezaCon(cadena);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("muestraDTO");
		
		return mav;
	}
	
	@PostMapping(value="/Estudiante", params="action=editar")
	public ModelAndView editar(@RequestParam(value="c") Integer c) {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;
		if (c == null) {
			log.info("Errorres encontrados");
			mav.setViewName("redirect:/listado");
		}else {
			Estudiante e = estudianteService.findOne(c);
			mav.addObject("estudiante", e);
			mav.addObject("estudiantes", estudiantes);
			mav.setViewName("editar");
		}
		return mav;
	}
	
}