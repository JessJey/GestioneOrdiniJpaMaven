package it.prova.gestioneordinijpamaven.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.prova.gestioneordinijpamaven.dao.EntityManagerUtil;
import it.prova.gestioneordinijpamaven.model.Articolo;
import it.prova.gestioneordinijpamaven.model.Categoria;
import it.prova.gestioneordinijpamaven.model.Ordine;
import it.prova.gestioneordinijpamaven.service.ArticoloService;
import it.prova.gestioneordinijpamaven.service.CategoriaService;
import it.prova.gestioneordinijpamaven.service.MyServiceFacrtory;
import it.prova.gestioneordinijpamaven.service.OrdineService;


public class TestGestioneOrdini {

	public static void main(String[] args) {
		ArticoloService articoloServiceInstance = MyServiceFacrtory.getArticoloServiceInstance();
		OrdineService ordineServiceInstance = MyServiceFacrtory.getOrdineServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFacrtory.getCategoriaServiceInstance();

		try {

			System.out.println(".....INIZIO DEI TEST........");

			testInserisciOrdine(ordineServiceInstance);

			testInserimentoNuovoArticolo(ordineServiceInstance,articoloServiceInstance);
			
			testCollegaCategoriaAArticolo(ordineServiceInstance,articoloServiceInstance,categoriaServiceInstance);

			testInserimentoNuovaCategoria(categoriaServiceInstance);
			
			testAggiornaCategoria(categoriaServiceInstance);
			
			testTrovaOrdiniDiUnaCategoria(categoriaServiceInstance, articoloServiceInstance, ordineServiceInstance);
			
			testCercaTutteCategorieDiUnOrdine(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			
			testSommaPrezziArticoliDiUnaCategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserisciOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testInserisciOrdine inizio.............");

		Ordine ordineInstance = new Ordine("Jessica", "via Postera");
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testInserisciOrdine fallito ");

		System.out.println(".......testInserisciOrdine fine: PASSED.............");
	}

	private static void testInserimentoNuovoArticolo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovoArticolo inizio.............");

		long nowTimeMilliseconds = new Date().getTime();

		// prima mi creo l'ordine
		Ordine ordineInstance = new Ordine("Jessica" + nowTimeMilliseconds, "via Postera" + nowTimeMilliseconds);
		ordineServiceInstance.inserisciNuovo(ordineInstance);

		Articolo articoloInstance = new Articolo("Bottiglia"+nowTimeMilliseconds, 5,ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoArticolo fallito ");

		System.out.println(".......testInserimentoNuovoArticolo fine: PASSED.............");
	}

	private static void testCollegaCategoriaAArticolo(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance)
			throws Exception {
		System.out.println(".......testCollegaCATEGORIA A ARTICOLO inizio.............");

		long nowInMillisecondi = new Date().getTime();
		// inserisco un cd
		Ordine ordineInstance = new Ordine("Paola" + nowInMillisecondi, "via Po" + nowInMillisecondi);
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		Articolo articoloInstance = new Articolo("Bottiglia333"+nowInMillisecondi, 5, ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCollegaCATEGORIA A ARTICOLO fallito: inserimento cd non riuscito ");

		// inserisco un genere
		Categoria nuovaCategoria = new Categoria("Casa" + nowInMillisecondi);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);
		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testCollegaCATEGORIA A ARTICOLO fallito: genere non inserito ");

		// collego
		articoloServiceInstance.aggiungiCategoria(articoloInstance, nuovaCategoria);

		// ricarico eager per forzare il test
		Articolo articoloReloaded = articoloServiceInstance.caricaSingoloElementoEagerCategoria(articoloInstance.getId());
		if (articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException("testCollegaCATEGORIA A ARTICOLO fallito: genere non collegato ");

		System.out.println(".......testCollegaCATEGORIA A ARTICOLO fine: PASSED.............");
	}
	
	private static void testInserimentoNuovaCategoria(CategoriaService categoriaServiceInstance)
			throws Exception {
		System.out.println(".......testInserimentoNuovaCategoriaERicercaPerDescrizione inizio.............");

		
		String descrizioneCategoria = "Giardino" + new Date().getTime();
		Categoria nuovoCategoria = new Categoria(descrizioneCategoria);
		categoriaServiceInstance.inserisciNuovo(nuovoCategoria);
		if (nuovoCategoria.getId() == null)
			throw new RuntimeException(
					"testInserimentoNuovaCategoriaERicercaPerDescrizione fallito: genere non inserito ");

		System.out.println(".......testInserimentoNuovaCategoriaERicercaPerDescrizione fine: PASSED.............");
	}
	
	private static void testAggiornaCategoria(CategoriaService categoriaServiceInstance)
			throws Exception {
		System.out.println(".......testAggiornaCategoria inizio.............");

		
		Categoria categoriaInstance = new Categoria("Cucina");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);;
		String descrizioneDaAggiornare = "Forno";
		categoriaInstance.setDescrizione(descrizioneDaAggiornare);
		categoriaServiceInstance.aggiorna(categoriaInstance);

		if (!categoriaServiceInstance.caricaSingoloElemento(categoriaInstance.getId()).getDescrizione().equals(descrizioneDaAggiornare))
			throw new RuntimeException(
					"testAggiornaCategoria fallito: genere non aggiornato ");
		
		categoriaServiceInstance.rimuovi(categoriaInstance);
		if(categoriaServiceInstance.caricaSingoloElemento(categoriaInstance.getId()) != null)
			throw new RuntimeException(
					"testAggiornaCategoria fallito: eliminazione genere fallita ");
		System.out.println(".......testAggiornaCategoria fine: PASSED.............");
	}
	
	private static void testTrovaOrdiniDiUnaCategoria(CategoriaService categoriaServiceInstance, ArticoloService articoloServiceInstance, OrdineService ordineServiceInstance) throws Exception{
		System.out.println(".......testTrovaOrdiniDiUnaCategoria inizio.............");

		
		long nowInMillisecondi = new Date().getTime();
		Ordine ordineInstance = new Ordine("Aurora", "Tivoli");
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		
		Articolo articoloInstanceX = new Articolo("AGO" + nowInMillisecondi,500,ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstanceX);
		
		Categoria categoria1 = new Categoria("HOBBY" + nowInMillisecondi);
		categoriaServiceInstance.inserisciNuovo(categoria1);
		
		articoloServiceInstance.aggiungiCategoria(articoloInstanceX, categoria1);
		

		if(ordineServiceInstance.trovaOrdiniDiUnaCategoria(categoria1).size() == 0)		
			throw new RuntimeException("testTrovaOrdiniDiUnaCategoria fallito: ricerca non avvenuta con successo ");

		System.out.println(".......testTrovaOrdiniDiUnaCategoria fine: PASSED.............");
	}
	
	private static void testCercaTutteCategorieDiUnOrdine(ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance)
			throws Exception {
		System.out.println(".......testCercaTuttestCercaTutteCategorieDiUnOrdinetiByOrdineArticolo inizio.............");

		long nowInMillisecondi = new Date().getTime();
		
		Ordine ordineInstance = new Ordine("Simona", "Via Appia");
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		
		Articolo articolo1 = new Articolo("Pianta" + nowInMillisecondi,20,ordineInstance);
		articoloServiceInstance.inserisciNuovo(articolo1);
		
		Categoria categoria1 = new Categoria("Giardinaggio" + nowInMillisecondi);
		categoriaServiceInstance.inserisciNuovo(categoria1);
		articoloServiceInstance.aggiungiCategoria(articolo1, categoria1);
		
		System.out.println(categoriaServiceInstance.cercaCategorieDiUnOrdine(ordineInstance).size());
		
		if(categoriaServiceInstance.cercaCategorieDiUnOrdine(ordineInstance).size() == 0)		
			throw new RuntimeException("testCercaTutteCategorieDiUnOrdine fallito: ricerca non avvenuta con successo ");

		System.out.println(".......testCercaTutteCategorieDiUnOrdine fine: PASSED.............");
	}
	
	private static void testSommaPrezziArticoliDiUnaCategoria(ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance)
			throws Exception {
		System.out.println(".......testSommaPrezziArticoliDiUnaCategoria inizio.............");
		
		long nowInMillisecondi = new Date().getTime();
		
		Ordine ordineInstance = new Ordine("Cristian", "Via Appia");
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		
		Articolo articoloInstanceX = new Articolo("HiSense" + nowInMillisecondi,999,ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstanceX);
		
		Categoria categoria1 = new Categoria("SmartTV" + nowInMillisecondi);
		categoriaServiceInstance.inserisciNuovo(categoria1);
		
		articoloServiceInstance.aggiungiCategoria(articoloInstanceX, categoria1);
		
		if(articoloServiceInstance.cercaSommaTuttiPrezziDiUnaCategoria(categoria1) == null)		
			throw new RuntimeException("testSommaPrezziArticoliDiUnaCategoria fallito: ricerca non avvenuta con successo ");

		System.out.println(articoloServiceInstance.cercaSommaTuttiPrezziDiUnaCategoria(categoria1));
		System.out.println(".......testSommaPrezziArticoliDiUnaCategoria fine: PASSED.............");
	}
}
