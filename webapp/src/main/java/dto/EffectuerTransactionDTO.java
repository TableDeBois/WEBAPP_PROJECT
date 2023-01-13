package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EffectuerTransactionDTO {

	private Long comptePayeur;
	private Double montant;
	private Long comptePaye;
	
}
