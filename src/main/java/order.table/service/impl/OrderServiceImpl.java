package order.table.service.impl;

import order.table.OrderTable;
import order.table.service.OperationService;

public class OrderServiceImpl implements OperationService {
    private static final int SIZE_INDEX = 2;
    private static final int ZERO = 0;
    private static final String SELL_TYPE = "sell";
    private static final String BUY_TYPE = "buy";
    private static final int ORDER_TYPE_INDEX = 1;

    @Override
    public void doOperation(String[] lineValues, OrderTable orderTable) {
        String orderType = lineValues[ORDER_TYPE_INDEX];
        int orderSize = Integer.parseInt(lineValues[SIZE_INDEX]);
        switch (orderType) {
            case SELL_TYPE -> {
                int bestBidPrice = orderTable.getBidMap().lastKey();
                if (orderTable.getBidMap().get(bestBidPrice) >= orderSize) {
                    orderTable.getBidMap().put(bestBidPrice,
                            orderTable.getBidMap().get(bestBidPrice) - orderSize);
                    break;
                }
                while (orderSize > ZERO) {
                    if (orderSize >= orderTable.getBidMap().get(bestBidPrice)) {
                        orderSize -= orderTable.getBidMap().get(bestBidPrice);
                        orderTable.getBidMap().put(bestBidPrice, ZERO);
                        bestBidPrice--;
                    } else {
                        orderTable.getBidMap().put(bestBidPrice,
                                orderTable.getBidMap().get(bestBidPrice) - orderSize);
                        break;
                    }
                }
            }
            case BUY_TYPE -> {
                int bestAskPrice = orderTable.getAskMap().firstKey();
                if (orderTable.getAskMap().get(bestAskPrice) >= orderSize) {
                    orderTable.getAskMap().put(bestAskPrice,
                            orderTable.getAskMap().get(bestAskPrice) - orderSize);
                    break;
                }
                while (orderSize > ZERO) {
                    if (orderSize >= orderTable.getAskMap().get(bestAskPrice)) {
                        orderSize -= orderTable.getAskMap().get(bestAskPrice);
                        orderTable.getAskMap().put(bestAskPrice, ZERO);
                        bestAskPrice++;
                    } else {
                        orderTable.getAskMap().put(bestAskPrice,
                                orderTable.getAskMap().get(bestAskPrice) - orderSize);
                        break;
                    }
                }
            }
        }
    }
}
