package it.prova.gestioneordinijpamaven.service;

import java.util.List;
import java.util.Set;

import it.prova.gestioneordinijpamaven.dao.ordine.OrdineDAO;
import it.prova.gestioneordinijpamaven.model.Articolo;
import it.prova.gestioneordinijpamaven.model.Categoria;
import it.prova.gestioneordinijpamaven.model.Ordine;

public interface OrdineService {

	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void rimuovi(Ordine ordineInstance) throws Exception;

	public void inserisciCompleto(Ordine ordineInstance, Set<Articolo> articoliDaCollegareAllOrdine) throws Exception;

	public List<Ordine> trovaOrdiniDiUnaCategoria(Categoria categoriaInstance) throws Exception;
	
	public void aggiungiArticoloAllOrdine(Ordine ordineInstance, Articolo articoloInstance) throws Exception;
	
	// per injection
	public void setOrdineDAO(OrdineDAO ordineDAO);


}
