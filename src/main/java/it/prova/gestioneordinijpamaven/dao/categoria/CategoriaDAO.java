package it.prova.gestioneordinijpamaven.dao.categoria;

import java.util.List;

import it.prova.gestioneordinijpamaven.dao.IBaseDAO;
import it.prova.gestioneordinijpamaven.model.Categoria;
import it.prova.gestioneordinijpamaven.model.Ordine;

public interface CategoriaDAO extends IBaseDAO<Categoria> {

	public List<Categoria> findAllCategorieDiUnOrdine(Ordine ordineInstance) throws Exception;
}
