package sauprojects.sauvest.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import ru.tinkoff.piapi.contract.v1.CandleInterval;
import ru.tinkoff.piapi.contract.v1.HistoricCandle;

public interface CustomMarketDataService {
	
	public CompletableFuture<List<HistoricCandle>> getShareMarketData(String username, String figi, CandleInterval candleInterval);

}
