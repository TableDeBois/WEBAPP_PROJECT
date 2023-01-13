package database;

public class Queries {

	
	public static final String QUERY_TRANSACTION = "SELECT DISTINCT\n"
			+ "transactions.id_transaction as ID,\n"
			+ "type_transaction.lib_type_transaction as libT,\n"
			+ "transactions.montant as montant,\n"
			+ "transactions.date_transaction as dateT,\n"
			+ "transactions.id_compte_debite as compteDebite,\n"
			+ "transactions.id_compte_receveur as compteReceveur,\n"
			+ "transactions.ind_retrait as indRetrait\n"
			+ "FROM\n"
			+ "TRANSACTIONS\n"
			+ "INNER JOIN TYPE_TRANSACTION ON type_transaction.id_type_transaction = transactions.id_type_transaction\n"
			+ "WHERE transactions.id_compte_debite = ?";

	
	public static final String QUERY_INFOS_COMPTE = "SELECT DISTINCT\n"
			+ "compte.id_compte,\n"
			+ "type_compte.lib_type_compte,\n"
			+ "compte.solde,\n"
			+ "compte.date_creation,\n"
			+ "compte.date_fermeture,\n"
			+ "client.nom_client,\n"
			+ "client.prenom_client,\n"
			+ "client.adresse_client\n"
			+ "FROM\n"
			+ "compte\n"
			+ "	INNER JOIN type_compte ON type_compte.id_type_compte=compte.id_type_compte\n"
			+ "	INNER JOIN client ON client.id_client=compte.id_client\n"
			+ "WHERE"
			+ "	compte.id_compte = ?";
	
	public static final String QUERY_ALL_COMPTES="SELECT DISTINCT\n"
			+ "compte.id_compte,\n"
			+ "type_compte.lib_type_compte,\n"
			+ "compte.solde,\n"
			+ "compte.date_creation,\n"
			+ "compte.date_fermeture,\n"
			+ "client.nom_client,\n"
			+ "client.prenom_client,\n"
			+ "client.adresse_client\n"
			+ "FROM\n"
			+ "compte\n"
			+ "	INNER JOIN type_compte ON type_compte.id_type_compte=compte.id_type_compte\n"
			+ "	INNER JOIN client ON client.id_client=compte.id_client\n";
	
	public static final String QUERY_ALL_COMPTES_DATE="SELECT DISTINCT\n"
			+ "compte.id_compte,\n"
			+ "type_compte.lib_type_compte,\n"
			+ "compte.solde,\n"
			+ "compte.date_creation,\n"
			+ "compte.date_fermeture,\n"
			+ "client.nom_client,\n"
			+ "client.prenom_client,\n"
			+ "client.adresse_client\n"
			+ "FROM\n"
			+ "compte\n"
			+ "	INNER JOIN type_compte ON type_compte.id_type_compte=compte.id_type_compte\n"
			+ "	INNER JOIN client ON client.id_client=compte.id_client\n"
			+ "WHERE"
			+ "	compte.date_creation <= ? AND (compte.date_fermeture IS NULL OR compte.date_fermeture > ?)";
	
	public static final String QUERY_COMPTES_CLIENT = "SELECT DISTINCT\n"
			+ "compte.id_compte,\n"
			+ "type_compte.lib_type_compte,\n"
			+ "compte.solde,\n"
			+ "compte.date_creation,\n"
			+ "compte.date_fermeture,\n"
			+ "client.nom_client,\n"
			+ "client.prenom_client,\n"
			+ "client.adresse_client\n"
			+ "FROM\n"
			+ "compte\n"
			+ "	INNER JOIN type_compte ON type_compte.id_type_compte=compte.id_type_compte\n"
			+ "	INNER JOIN client ON client.id_client=compte.id_client\n"
			+ "WHERE"
			+ "	client.id_client = ?";
	
	public static final String QUERY_SOLDE="SELECT compte.solde\n"
			+ "FROM compte\n"
			+ "WHERE compte.id_compte= ?";
	
	public static final String QUERY_EFFECTUER_TRANSACTION="INSERT INTO TRANSACTIONS(id_type_transaction,montant,date_transaction,id_compte_debite,id_compte_receveur,ind_retrait) VALUES(?,?,?,?,?,?)";
	
	public static final String QUERY_MODIFIER_SOLDE="UPDATE COMPTE\n"
			+ "SET\n"
			+ "solde = ?\n"
			+ "WHERE\n"
			+ "id_compte = ?";
	
	public static final String QUERY_AJOUTER_COMPTE="INSERT INTO COMPTE(id_type_compte,solde,date_creation,id_client) VALUES(?,?,?,?)";
	
	public static final String QUERY_AJOUTER_DATE_FERMETURE_COMPTE="UPDATE TABLE COMPTE\n"
			+ "SET\n"
			+ "date_fermeture = ?\n"
			+ "WHERE\n"
			+ "id_compte = ?";
	
	public static final String QUERY_AJOUTER_CLIENT="INSERT INTO CLIENT(nom_client,prenom_client,adresse_client) VALUES(?,?,?)";
	
	public static final String QUERY_ALL_CLIENTS="SELECT * FROM CLIENT";
}
