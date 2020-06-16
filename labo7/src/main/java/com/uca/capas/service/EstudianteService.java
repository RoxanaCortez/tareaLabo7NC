package com.uca.capas.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.uca.capas.domain.Estudiante;
import com.uca.capas.dto.EstudianteDTO;

public interface EstudianteService {
	
	List<Estudiante> todos() throws DataAccessException;

	Estudiante findOne(Integer code) throws DataAccessException;

	void guardar(Estudiante estudiante) throws DataAccessException;

	List <Estudiante> filtrarPor(String cadena) throws DataAccessException;

	List<Estudiante> empiezaCon(String cadena) throws DataAccessException;

	List<EstudianteDTO> dtoPrueba() throws DataAccessException;

	void eliminar(Integer codigo) throws DataAccessException;

	List<Estudiante> listarAsc() throws DataAccessException;
}
