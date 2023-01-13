package dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class Compte implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 30706849359910544L;

	
	private Long numCompte;
	
	private String prenomPropriertaire;
	
	private String nomProrietaire;
	
	private String adresse;
	
	private Double solde;
	
	private String libTypeCompte;
	
	private Date dateCreation;
	
	private Date dateFermeture;
	
}
