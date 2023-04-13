package sauprojects.sauvest.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BondDTO {
	
	private String name;
	private String figi;
	private String ticker;
	private String classCode;
	private String isin;
}
