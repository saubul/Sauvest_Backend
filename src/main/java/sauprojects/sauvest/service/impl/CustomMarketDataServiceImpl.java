package sauprojects.sauvest.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;
import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.service.CustomMarketDataService;
import sauprojects.sauvest.service.UserService;

@Service
@RequiredArgsConstructor
public class CustomMarketDataServiceImpl implements CustomMarketDataService {
	
	private final UserService userService;
	
	public CompletableFuture<List<HistoricCandle>> getShareMarketData(String username, String figi, CandleInterval candleInterval) {
		User user = userService.findUserByUsername(username);
		String userSSOToken =  user.getSsoToken();
		InvestApi investApi = UtilService.getInvestApi(userSSOToken);
		MarketDataService marketDataService = investApi.getMarketDataService();
		Instant fromDate = Instant.now();
		if(candleInterval == CandleInterval.CANDLE_INTERVAL_DAY) {
			fromDate = fromDate.minus(364, ChronoUnit.DAYS);
		} else if(candleInterval == CandleInterval.CANDLE_INTERVAL_HOUR) {
			fromDate = fromDate.minus(7, ChronoUnit.DAYS);
		} else if(candleInterval == CandleInterval.CANDLE_INTERVAL_5_MIN) {
			fromDate = fromDate.minus(1, ChronoUnit.DAYS);
		}
		return marketDataService.getCandles(figi, fromDate, Instant.now(), candleInterval);
	}
	
}
