package sauprojects.sauvest.service;

import java.util.List;

import sauprojects.sauvest.dto.BondDTO;
import sauprojects.sauvest.dto.ShareDTO;

public interface InstrumentService {
	
	public ShareDTO getShareByFigi(String username, String figi);
	
	public ShareDTO getShareByTicker(String username, String ticker);

	public List<ShareDTO> getAllShares(String username);

	public BondDTO getBondByFigi(String username, String figi);

	public List<BondDTO> getAllBonds(String username);
	
}
