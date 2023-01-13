package services;
import database.DbController;
import database.Queries;
import dto.Compte;
import dto.Transaction;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BankService {


	private static final DbController DB = new DbController();

	private static final Queries QUERIES = new Queries();

	public Double checkBalance(Long accountNumber) throws Exception {
		List<Map<Integer, Object>> rs = new ArrayList<Map<Integer, Object>>();
		rs = DbController.executeQuery2(Queries.QUERY_SOLDE, accountNumber);
		Double valRetour = null;
		for(Map<Integer,Object> row : rs) {
			valRetour= Double.valueOf(((Float)row.get(1)).toString());
		}
		return valRetour;
	}
  
	public List<Transaction> getTransactions(Long numCompte) throws Exception{
		//Connection conn = DriverManager.getConnection(DbController.getDbUrl());
		//PreparedStatement stmlt = conn.prepareStatement("SELECT transaction.id_transaction as ID,");
		List<Map<Integer, Object>> rs = new ArrayList<Map<Integer, Object>>();
		rs = DbController.executeQuery2(Queries.QUERY_TRANSACTION, numCompte);
		if(rs == null) {
			throw new Exception("ResultSet is null");
		}
		List<Transaction> listeRetour = new ArrayList<>();
		for(Map<Integer,Object> row : rs) {
			Transaction obj = new Transaction();
			
			obj.setIdTransaction(((Long)row.get(1)).longValue());
			obj.setTypeTransaction(String.valueOf(row.get(2)));
			obj.setMontant(Double.valueOf(((Float)row.get(3)).toString()));
			obj.setDateTransaction((Date)row.get(4));
			obj.setIdCompteDebite(((Long)row.get(5)).longValue());
			if(row.get(6)==null) {
				obj.setIdCompteCredite(null);
			}else {
				obj.setIdCompteCredite(((Long)row.get(6)).longValue());
			}
			
			if(String.valueOf(row.get(7)).equals("1")) {
				obj.setIndRetrait(true);
			}else {
				obj.setIndRetrait(false);
			}
			listeRetour.add(obj);
		}
		return listeRetour;
	}
  
	public Compte getInfoCompte(Long numCompte) throws Exception{
		List<Map<Integer, Object>> rs = new ArrayList<Map<Integer, Object>>();
		rs = DbController.executeQuery2(Queries.QUERY_INFOS_COMPTE, numCompte);
		if(rs == null) {
			throw new Exception("ResultSet is null");
		}
		Compte obj = new Compte();
		for(Map<Integer,Object> row : rs) {
			obj.setNumCompte(((Long)row.get(1)).longValue());
			obj.setLibTypeCompte(String.valueOf(row.get(2)));
			obj.setSolde(Double.valueOf(((Float)row.get(3)).toString()));
			obj.setDateCreation((Date)row.get(4));
			if(row.get(5)!=null) {
				obj.setDateFermeture((Date)row.get(5));
			}else {
				obj.setDateFermeture(null);
			}
			obj.setNomProrietaire(String.valueOf(row.get(6)));
			obj.setPrenomPropriertaire(String.valueOf(row.get(7)));
			obj.setAdresse(String.valueOf(row.get(8)));
		}
		return obj;
	}
	
	public List<Compte> getComptesClient(Long numClient) throws Exception{
		List<Compte> listeRetour = new ArrayList<>();
		List<Map<Integer, Object>> rs = new ArrayList<Map<Integer, Object>>();
		rs = DbController.executeQuery2(Queries.QUERY_COMPTES_CLIENT, numClient);
		if(rs == null) {
			throw new Exception("ResultSet is null");
		}
		System.err.println("Mapping objet retour");
		for(Map<Integer,Object> row : rs) {
			Compte obj = new Compte();
			obj.setNumCompte(((Long)row.get(1)).longValue());
			obj.setLibTypeCompte(String.valueOf(row.get(2)));
			obj.setSolde(Double.valueOf(((Float)row.get(3)).toString()));
			obj.setDateCreation((Date)row.get(4));
			if(row.get(5)!=null) {
				obj.setDateFermeture((Date)row.get(5));
			}else {
				obj.setDateFermeture(null);
			}
			obj.setNomProrietaire(String.valueOf(row.get(6)));
			obj.setPrenomPropriertaire(String.valueOf(row.get(7)));
			obj.setAdresse(String.valueOf(row.get(8)));
			listeRetour.add(obj);
		}
		return listeRetour;
	}
	
	public List<Compte> getComptes(String date1,String date2) throws Exception {
		List<Compte> listeRetour = new ArrayList<>();
		//ResultSet rs = null;
		List<Map<Integer, Object>> rs = new ArrayList<Map<Integer, Object>>();
		if(!date1.isBlank()) {
			System.err.println("Date non nulle");
			Date dateRecherche = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
			Date dateRecherche2 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
			rs = DbController.executeQuery2(Queries.QUERY_ALL_COMPTES_DATE, dateRecherche,dateRecherche2);
		}else {
			System.err.println("Date nulle");
			Object[] noParam= (Object[]) new Object();
			rs = DbController.executeQuery2(Queries.QUERY_ALL_COMPTES, noParam);
		}
		if(rs == null) {
			throw new Exception("ResultSet is null");
		}
		System.err.println("Mapping objet retour");
		for(Map<Integer,Object> row : rs) {
			Compte obj = new Compte();
			obj.setNumCompte(((Long)row.get(1)).longValue());
			obj.setLibTypeCompte(String.valueOf(row.get(2)));
			obj.setSolde(Double.valueOf(((Float)row.get(3)).toString()));
			obj.setDateCreation((Date)row.get(4));
			if(row.get(5)!=null) {
				obj.setDateFermeture((Date)row.get(5));
			}else {
				obj.setDateFermeture(null);
			}
			obj.setNomProrietaire(String.valueOf(row.get(6)));
			obj.setPrenomPropriertaire(String.valueOf(row.get(7)));
			obj.setAdresse(String.valueOf(row.get(8)));
			listeRetour.add(obj);
		}
		return listeRetour;
	}
	
	public void faireTransaction(Long comptePayeur, Double montant, Long comptePaye) throws Exception {
		Date dateEffet = FORMATYYYY_MM_DD(new Date());
	 	List<Map<Integer, Object>> rsSoldeCompte1 = DbController.executeQuery2(Queries.QUERY_SOLDE, comptePayeur); 
		Double soldeC1 = Double.valueOf(((Float) rsSoldeCompte1.get(0).get(1)).toString());
	 	soldeC1 = soldeC1 - montant;
	 	DbController.executeUpdate2(Queries.QUERY_MODIFIER_SOLDE, soldeC1,comptePayeur);
		Boolean rs = null;
	 	
		if(comptePaye==null) {
			
			rs =DbController.executeUpdate2(Queries.QUERY_EFFECTUER_TRANSACTION, 1,montant,dateEffet,comptePayeur,null,"1" );
		}else {
			List<Map<Integer, Object>> rsSoldeCompte2 = DbController.executeQuery2(Queries.QUERY_SOLDE, comptePaye); 
			Double soldeC2 = Double.valueOf(((Float) rsSoldeCompte2.get(0).get(1)).toString());
		 	soldeC2 = soldeC2 + montant;
		 	DbController.executeUpdate2(Queries.QUERY_MODIFIER_SOLDE, soldeC2,comptePaye);
			rs =DbController.executeUpdate2(Queries.QUERY_EFFECTUER_TRANSACTION, 2,montant,dateEffet,comptePayeur,comptePaye,"1" );
		}
		System.err.println("INSERT EXECUTED");
	}
	
	public void creerCompte(Long idClient, Double soldeDeDepart) throws Exception {
		Date dateEffet = FORMATYYYY_MM_DD(new Date());
		DbController.executeUpdate2(Queries.QUERY_AJOUTER_COMPTE, 1,soldeDeDepart,dateEffet,idClient);
	}
	
	public void creerClient(String nom,String prenom, String adresse) throws Exception {
		DbController.executeUpdate2(Queries.QUERY_AJOUTER_CLIENT, nom,prenom,adresse);
	}
	
	public void ajouterDateFermetureCompte(Long idCompte, String dateFin) throws Exception {
		Date dateRecherche = new SimpleDateFormat("yyyy-MM-dd").parse(dateFin);
		dateRecherche = FORMATYYYY_MM_DD(dateRecherche);
		DbController.executeUpdate2(Queries.QUERY_AJOUTER_DATE_FERMETURE_COMPTE, dateFin,idCompte);
	}
	
	private static final Date FORMATYYYY_MM_DD(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String s = format.format(date);
		String result = s;
		try {
			date = format.parse(result);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;
	}
	
}