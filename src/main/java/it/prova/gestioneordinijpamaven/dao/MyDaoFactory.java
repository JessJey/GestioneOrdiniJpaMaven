package it.prova.gestioneordinijpamaven.dao;

import it.prova.gestioneordinijpamaven.dao.articolo.ArticoloDAO;
import it.prova.gestioneordinijpamaven.dao.articolo.ArticoloDAOImpl;
import it.prova.gestioneordinijpamaven.dao.categoria.CategoriaDAO;
import it.prova.gestioneordinijpamaven.dao.categoria.CategoriaDAOImpl;
import it.prova.gestioneordinijpamaven.dao.ordine.OrdineDAO;
import it.prova.gestioneordinijpamaven.dao.ordine.OrdineDAOImpl;

public class MyDaoFactory {

	private static ArticoloDAO articoloDaoInstance = null;
	private static CategoriaDAO categoriaDaoInstance = null;
	private static OrdineDAO ordineDaoInstance = null;

	public static ArticoloDAO getArticoloDAOInstance() {
		if (articoloDaoInstance == null)
			articoloDaoInstance = new ArticoloDAOImpl();

		return articoloDaoInstance;
	}

	public static CategoriaDAO getCategoriaDAOInstance() {
		if (categoriaDaoInstance == null)
			categoriaDaoInstance = new CategoriaDAOImpl();

		return categoriaDaoInstance;
	}

	public static OrdineDAO getOrdineDAOInstance() {
		if (ordineDaoInstance == null)
			ordineDaoInstance = new OrdineDAOImpl();

		return ordineDaoInstance;
	}
}
