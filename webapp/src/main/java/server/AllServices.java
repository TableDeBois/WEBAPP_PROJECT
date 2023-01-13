package server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.ActiviteRetourDTO;
import dto.Compte;
import dto.DateFinModifDTO;
import dto.EffectuerTransactionDTO;
import dto.NouveauClient;
import dto.NouveauCompte;
import dto.Transaction;
import services.BankService;
import services.BoredService;

import javax.ws.rs.PATCH;

@RestController
@Path("/All")
public class AllServices {

	private static final BankService BANK = new BankService();
	private static final BoredService BORED = new BoredService();
	
	@CrossOrigin
	@GetMapping("/bank/balance")
	@GET
	@Path(value = "/bank/balance")
	@Produces(MediaType.APPLICATION_JSON)
	public Double getBalance(@RequestParam(value="numCompte") Long numCompte) {
		System.err.println("Starting getBalance at " + Date.from(Instant.now()).toString());
		Double solde=null;
		try {
			solde = BANK.checkBalance(numCompte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return solde;
	}
	
	@CrossOrigin
	@GET
	@Path(value="/bank/activite")
	@Produces(MediaType.APPLICATION_JSON)
	@GetMapping("/bank/activite")
	public ActiviteRetourDTO getActivite(@RequestParam(value="typeActivite") String typeAct, @RequestParam(value="nbPersonnes") String nbPersonnes,
			@RequestParam(value="prix") String prix) {
		ActiviteRetourDTO obj = new ActiviteRetourDTO();
		try {
			obj =  BORED.iDontWant2BeBoredAnymore(typeAct,nbPersonnes,prix);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	@CrossOrigin
	@GET
	@Path(value="/bank/transactions")
	@Produces(MediaType.APPLICATION_JSON)
	@GetMapping("/bank/transactions")
	public List<Transaction> getTransaction(@RequestParam(value = "numCompte")
			Long numCompte) {
		List<Transaction> listeRetour = new ArrayList<>();
		try {
			listeRetour=BANK.getTransactions(numCompte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeRetour;
	}
	
	@CrossOrigin
	@GET
	@Path(value="/bank/info-compte")
	@Produces(MediaType.APPLICATION_JSON)
	@GetMapping("/bank/info-compte")
	public Compte getInfoCompte(@RequestParam(value="numCompte")
			Long numCompte) {
		Compte cpRetour = null;
		try {
			cpRetour = BANK.getInfoCompte(numCompte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cpRetour;
	}
	
	@GET
	@Path(value="/bank/comptes")
	@Produces(MediaType.APPLICATION_JSON)
	@GetMapping("/bank/comptes")
	public List<Compte> getComptes(@RequestParam(value="date")String date) {
		System.err.println("Debut recuperation des comptes");
		System.err.println("Avec date : "+ date);
		List<Compte> listeRetour = new ArrayList<>();
		try {
			listeRetour = BANK.getComptes(date,date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeRetour;
	}
	
	@PATCH
	@Path(value="/bank/faire-transaction")
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = "/bank/faire-transaction", method = RequestMethod.PATCH)
	public void doTransaction(@RequestBody EffectuerTransactionDTO transac) {
		try {
			BANK.faireTransaction(transac.getComptePayeur(), transac.getMontant(), (transac.getComptePaye() == null ? null : transac.getComptePaye()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@POST
	@Path(value="/bank/creer-compte")
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = "/bank/creer-compte", method = RequestMethod.POST)
	public void creerCompte(@RequestBody NouveauCompte cpt) {
		try {
			BANK.creerCompte(cpt.getIdClient(), cpt.getSoldeDeDepart());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PATCH
	@Path(value="/bank/modifier-date-fin")
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = "bank/modifier-date-fin", method = RequestMethod.PATCH)
	public void ajouterDateFin(@RequestBody DateFinModifDTO dto) {
		try {
			BANK.ajouterDateFermetureCompte(dto.getIdClient(), dto.getDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@POST
	@Path(value="/bank/creer-client")
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(value = "/bank/creer-client", method = RequestMethod.POST)
	public void creerClient(@RequestBody NouveauClient client) {
		try {
			BANK.creerClient(client.getNom(), client.getPrenom(), client.getAdresse());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
