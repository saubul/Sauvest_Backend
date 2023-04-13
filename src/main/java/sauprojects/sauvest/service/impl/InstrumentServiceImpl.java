package sauprojects.sauvest.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.contract.v1.InstrumentStatus;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import sauprojects.sauvest.dto.BondDTO;
import sauprojects.sauvest.dto.ShareDTO;
import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.service.InstrumentService;
import sauprojects.sauvest.service.UserService;

@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl implements InstrumentService{
	
	private final UserService userService;
	
	@Override
	public ShareDTO getShareByFigi(String username, String figi) {
		User user = userService.findUserByUsername(username);
		String userSSOToken =  user.getSsoToken();
		InvestApi investApi = UtilService.getInvestApi(userSSOToken);
		InstrumentsService instrumentsService = investApi.getInstrumentsService();
		Share share = instrumentsService.getShareByFigiSync(figi);
		ShareDTO shareDTO = this.convertShare(share);
		return shareDTO;
	}
	
	@Override
	public ShareDTO getShareByTicker(String username, String ticker) {
		User user = userService.findUserByUsername(username);
		String userSSOToken =  user.getSsoToken();
		InvestApi investApi = UtilService.getInvestApi(userSSOToken);
		InstrumentsService instrumentsService = investApi.getInstrumentsService();
		Share share = instrumentsService.getShareByTickerSync(ticker, "SPBXM");
		ShareDTO shareDTO = this.convertShare(share);
		return shareDTO;
	}

	@Override
	public List<ShareDTO> getAllShares(String username) {
		User user = userService.findUserByUsername(username);
		String userSSOToken =  user.getSsoToken();
		InvestApi investApi = UtilService.getInvestApi(userSSOToken);
		InstrumentsService instrumentsService = investApi.getInstrumentsService();
		List<Share> shares = instrumentsService.getSharesSync(InstrumentStatus.INSTRUMENT_STATUS_BASE);
		List<ShareDTO> shareDTOs = shares.stream().map(share -> {
			return convertShare(share);
		}).toList();
		return shareDTOs;
	}

	@Override	
	public BondDTO getBondByFigi(String username, String figi) {
		User user = userService.findUserByUsername(username);
		String userSSOToken =  user.getSsoToken();
		InvestApi investApi = UtilService.getInvestApi(userSSOToken);
		InstrumentsService instrumentsService = investApi.getInstrumentsService();
		Bond bond = instrumentsService.getBondByFigiSync(figi);
		BondDTO bondDTO = this.convertBond(bond);
														
		return bondDTO;
	}

	@Override
	public List<BondDTO> getAllBonds(String username) {
		User user = userService.findUserByUsername(username);
		String userSSOToken =  user.getSsoToken();
		InvestApi investApi = UtilService.getInvestApi(userSSOToken);
		InstrumentsService instrumentsService = investApi.getInstrumentsService();
		List<Bond> bonds = instrumentsService.getBondsSync(InstrumentStatus.INSTRUMENT_STATUS_BASE);
		List<BondDTO> bondDTOs = bonds.stream().map(bond -> {
			return convertBond(bond);
		}).toList();
		return bondDTOs;
	}
	
	private ShareDTO convertShare(Share share) {
		return ShareDTO.builder().instrumentType(share.getShareType().toString())
				  .figi(share.getFigi())
				  .ticker(share.getTicker())
				  .classCode(share.getClassCode())
				  .name(share.getName())
				  .build();
	}
	
	private BondDTO convertBond(Bond bond) {
		return BondDTO.builder()
				.name(bond.getName())
				.ticker(bond.getTicker())
				.classCode(bond.getClassCode())
				.figi(bond.getFigi())
				.isin(bond.getIsin())
				.build();
	}
	
}
