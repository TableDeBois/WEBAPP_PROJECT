package dto;

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
public class Transaction {
	
	private Long idTransaction;
	
	private Long idCompteDebite;
	
	private Long idCompteCredite;
	
	private Double montant;
	
	private Date dateTransaction;
	
	private String typeTransaction;
	
	private Boolean indRetrait;
}
