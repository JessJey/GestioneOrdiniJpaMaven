package it.prova.gestioneordinijpamaven.service;

import java.util.List;

import it.prova.gestioneordinijpamaven.dao.categoria.CategoriaDAO;
import it.prova.gestioneordinijpamaven.model.Categoria;
import it.prova.gestioneordinijpamaven.model.Ordine;

public interface CategoriaService {
	
	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Categoria categoriaInstance) throws Exception;
	
	public List<Categoria> cercaCategorieDiUnOrdine(Ordine ordineInstance) throws Exception;
		
	// per injection
		public void setCategoriaDAO(CategoriaDAO categoriaDAO);
}

