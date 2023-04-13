package sauprojects.sauvest.controller;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.BondDTO;
import sauprojects.sauvest.dto.InstrumentDTO;
import sauprojects.sauvest.dto.ShareDTO;
import sauprojects.sauvest.service.InstrumentService;
import sauprojects.sauvest.service.UserService;
import sauprojects.sauvest.service.impl.UtilService;

@RestController
@RequestMapping("/api/instrument")
@RequiredArgsConstructor
public class InstrumentController {
	
	private final InstrumentService instrumentService;
	private final UserService userService;
	
	@GetMapping("/getShare")
	public HttpEntity<ShareDTO> getShare(HttpServletRequest request, @RequestParam("content") String content) {
		
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		String username = UtilService.getUsernameFromJwt(authorizationHeader);
		
		ShareDTO shareDTO = null;
		if(content.length() == 4) {
			shareDTO = instrumentService.getShareByTicker(username, content);
		} else {
			shareDTO = instrumentService.getShareByFigi(username, content);
		}
		if(shareDTO == null) {
			return new ResponseEntity<ShareDTO>(shareDTO, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ShareDTO>(shareDTO, HttpStatus.OK);
	}
	
	@GetMapping("/getAllShares")
	public HttpEntity<List<ShareDTO>> getAllShares(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		String username = UtilService.getUsernameFromJwt(authorizationHeader);
		
		return new ResponseEntity<List<ShareDTO>>(instrumentService.getAllShares(username), HttpStatus.OK);
	}
	
	@GetMapping("/getBond")
	public HttpEntity<BondDTO> getBond(HttpServletRequest request, @RequestParam("figi") String figi) {
		
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		String username = UtilService.getUsernameFromJwt(authorizationHeader);
		
		return new ResponseEntity<BondDTO>(instrumentService.getBondByFigi(username, figi), HttpStatus.OK);
	}
	
	@GetMapping("/getAllBonds")
	public HttpEntity<List<BondDTO>> getAllBonds(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		String username = UtilService.getUsernameFromJwt(authorizationHeader);
		
		return new ResponseEntity<List<BondDTO>>(instrumentService.getAllBonds(username), HttpStatus.OK);
	}
	
	public HttpEntity<Void> evictInvestApi(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		String username = UtilService.getUsernameFromJwt(authorizationHeader);
		
		UtilService.evictInvestApi(userService.findUserByUsername(username).getSsoToken());
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
}
