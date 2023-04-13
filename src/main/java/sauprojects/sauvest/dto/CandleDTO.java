package sauprojects.sauvest.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandleDTO {

	private String open;
	private String close;
	private String low;
	private String high;
	private String volume;
	private String date;
	
}
