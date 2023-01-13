package dto;

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
public class ActiviteRetourDTO {

	private String activiteLabel;
	private Long accessibility;
	private String type;
	private Long participants;
	private Long price;
	private String error;
	
}
