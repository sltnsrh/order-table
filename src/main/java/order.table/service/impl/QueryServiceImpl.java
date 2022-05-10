package order.table.service.impl;

import order.table.OrderTable;
import order.table.service.OperationService;
import order.table.service.WriterService;

public class QueryServiceImpl implements OperationService {
    private static final int QUERY_PRICE_INDEX = 2;
    private static final String BEST_BID_QUERY_TYPE = "best_bid";
    private static final String BEST_ASK_QUERY_TYPE = "best_ask";
    private static final int QUERY_TYPE_INDEX = 1;
    private static final String COMMA_SEPARATOR = ",";
    private static final int ZERO = 0;
    private final WriterService writerService;

    public QueryServiceImpl(WriterService writerService) {
        this.writerService = writerService;
    }

    @Override
    public void doOperation(String[] lineValues, OrderTable orderTable) {
        String queryType = lineValues[QUERY_TYPE_INDEX];
        switch (queryType) {
            case BEST_BID_QUERY_TYPE -> {
                int bestBid = orderTable.getBidMap().lastKey();
                int bidSize = orderTable.getBidMap().get(bestBid);
                while (bidSize == ZERO) {
                    bestBid--;
                    bidSize = orderTable.getBidMap().get(bestBid);
                }
                writerService.writeToFile(bestBid + COMMA_SEPARATOR + bidSize);
            }
            case BEST_ASK_QUERY_TYPE -> {
                int bestAsk = orderTable.getAskMap().firstKey();
                int askSize = orderTable.getAskMap().get(bestAsk);
                while (askSize == ZERO) {
                    bestAsk++;
                    askSize = orderTable.getAskMap().get(bestAsk);
                }
                writerService.writeToFile(bestAsk + COMMA_SEPARATOR + askSize);
            }
            default -> {
                int priceFromQuery = Integer.parseInt(lineValues[QUERY_PRICE_INDEX]);
                writerService.writeToFile(orderTable.getAskMap().containsKey(priceFromQuery)
                        ? orderTable.getAskMap().get(priceFromQuery).toString()
                        : orderTable.getBidMap().get(priceFromQuery).toString());
            }
        }
    }
}
