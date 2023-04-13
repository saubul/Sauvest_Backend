package sauprojects.sauvest.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.var;
import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;
import ru.tinkoff.piapi.contract.v1.Quotation;
import sauprojects.sauvest.dto.CandleDTO;
import sauprojects.sauvest.service.CustomMarketDataService;
import sauprojects.sauvest.service.impl.UtilService;

@RestController
@RequestMapping("/api/marketdata")
@RequiredArgsConstructor
public class MarketDataController {
	
	private final CustomMarketDataService customMarketDataService;
	private List<SseEmitter> emitters = new ArrayList<SseEmitter>();
	
	@GetMapping(value = "/getShareData", produces = "text/event-stream")
	public SseEmitter getShareMarketData(HttpServletRequest request, @RequestParam("figi") String figi,
			@RequestParam("interval") String interval) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		String username = UtilService.getUsernameFromJwt(authorizationHeader);
		CompletableFuture<List<HistoricCandle>> candles = customMarketDataService.getShareMarketData(username, figi, CandleInterval.valueOf(interval));
		SseEmitter sseEmitter = new SseEmitter();
	    ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
	    sseMvcExecutor.execute(() -> {

	            candles.whenComplete((res, ex) -> {
	            	SseEventBuilder event = SseEmitter.event()
	            			.data(res.stream().map(this::convertCandle).collect(Collectors.toList()))
	            			.name("message");
	            	try {
						sseEmitter.send(event);
					} catch (IOException e) {
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			        	sseEmitter.completeWithError(ex);
			        }
	            });
	    });
		
		return sseEmitter;
	}
	
	private CandleDTO convertCandle(HistoricCandle historicCandle) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		String date = dateFormat.format(new Date(historicCandle.getTime().getSeconds() * 1000));
		return CandleDTO.builder()
				.open(convertQuotation(historicCandle.getOpen()))
				.close(convertQuotation(historicCandle.getClose()))
				.low(convertQuotation(historicCandle.getLow()))
				.high(convertQuotation(historicCandle.getHigh()))
				.volume(String.valueOf(historicCandle.getVolume()))
				.date(date)
				.build();
	}
	
	private String convertQuotation(Quotation quotation) {
		return String.valueOf(quotation.getUnits()) + "." + String.valueOf(quotation.getNano());
	}
	
}
