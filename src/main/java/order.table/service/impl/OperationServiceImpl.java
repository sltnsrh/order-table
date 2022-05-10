package order.table.service.impl;

import java.util.TreeMap;
import order.table.service.OperationService;

public class OperationServiceImpl implements OperationService {
    private static final int QUERY_PRICE_INDEX = 2;
    private static final int ORDER_UPDATE_TYPE_INDEX = 3;
    private static final int PRICE_INDEX = 1;
    private static final int SIZE_INDEX = 2;
    private static final String ASK_TYPE = "ask";
    private static final String BID_TYPE = "bid";
    public static final String BEST_BID_QUERY_TYPE = "best_bid";
    public static final String BEST_ASK_QUERY_TYPE = "best_ask";
    public static final int QUERY_TYPE_INDEX = 1;
    public static final String COMMA_SEPARATOR = ",";
    public static final int ZERO = 0;
    public static final String SELL_TYPE = "sell";
    public static final String BUY_TYPE = "buy";
    public static final int ORDER_TYPE_INDEX = 1;
    private final TreeMap<Integer, Integer> askMap;
    private final TreeMap<Integer, Integer> bidMap;

    public OperationServiceImpl() {
        this.askMap = new TreeMap<>();
        this.bidMap = new TreeMap<>();
    }

    @Override
    public void doUpdate(String[] lineValues) {
        int price = Integer.parseInt(lineValues[PRICE_INDEX]);
        int size = Integer.parseInt(lineValues[SIZE_INDEX]);
        String orderUpdateType = lineValues[ORDER_UPDATE_TYPE_INDEX];
        switch (orderUpdateType) {
            case ASK_TYPE -> {
                askMap.put(price, size);
                bidMap.remove(price);
            }
            case BID_TYPE -> {
                bidMap.put(price, size);
                askMap.remove(price);
            }
        }
    }

    @Override
    public String doQuery(String[] lineValues) {
        String queryType = lineValues[QUERY_TYPE_INDEX];
        switch (queryType) {
            case BEST_BID_QUERY_TYPE -> {
                int bestBid = bidMap.lastKey();
                int bidSize = bidMap.get(bestBid);
                while (bidSize == ZERO) {
                    bestBid--;
                    bidSize = bidMap.get(bestBid);
                }
                return bestBid + COMMA_SEPARATOR + bidSize;
            }
            case BEST_ASK_QUERY_TYPE -> {
                int bestAsk = askMap.firstKey();
                int askSize = askMap.get(bestAsk);
                while (askSize == ZERO) {
                    bestAsk++;
                    askSize = askMap.get(bestAsk);
                }
                return bestAsk + COMMA_SEPARATOR + askSize;
            }
            default -> {
                int priceFromQuery = Integer.parseInt(lineValues[QUERY_PRICE_INDEX]);
                return askMap.containsKey(priceFromQuery)
                        ? askMap.get(priceFromQuery).toString()
                        : bidMap.get(priceFromQuery).toString();
            }
        }
    }

    @Override
    public void doOrder(String[] lineValues) {
        String orderType = lineValues[ORDER_TYPE_INDEX];
        int orderSize = Integer.parseInt(lineValues[SIZE_INDEX]);
        switch (orderType) {
            case SELL_TYPE -> {
                int bestBidPrice = bidMap.lastKey();
                if (bidMap.get(bestBidPrice) >= orderSize) {
                    bidMap.put(bestBidPrice, bidMap.get(bestBidPrice) - orderSize);
                    break;
                }
                while (orderSize > ZERO) {
                    if (orderSize >= bidMap.get(bestBidPrice)) {
                        orderSize -= bidMap.get(bestBidPrice);
                        bidMap.put(bestBidPrice, ZERO);
                        bestBidPrice--;
                    } else {
                        bidMap.put(bestBidPrice, bidMap.get(bestBidPrice) - orderSize);
                        break;
                    }
                }
            }
            case BUY_TYPE -> {
                int bestAskPrice = askMap.firstKey();
                if (askMap.get(bestAskPrice) >= orderSize) {
                    askMap.put(bestAskPrice, askMap.get(bestAskPrice) - orderSize);
                    break;
                }
                while (orderSize > ZERO) {
                    if (orderSize >= askMap.get(bestAskPrice)) {
                        orderSize -= askMap.get(bestAskPrice);
                        bidMap.put(bestAskPrice, ZERO);
                        bestAskPrice++;
                    } else {
                        askMap.put(bestAskPrice, askMap.get(bestAskPrice) - orderSize);
                        break;
                    }
                }
            }
        }
    }
}
