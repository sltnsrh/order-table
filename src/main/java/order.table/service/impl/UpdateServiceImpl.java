package order.table.service.impl;

import order.table.OrderTable;
import order.table.service.OperationService;

public class UpdateServiceImpl implements OperationService {
    private static final int ORDER_UPDATE_TYPE_INDEX = 3;
    private static final int PRICE_INDEX = 1;
    private static final int SIZE_INDEX = 2;
    private static final String ASK_TYPE = "ask";
    private static final String BID_TYPE = "bid";

    @Override
    public void doOperation(String[] lineValues, OrderTable orderTable) {
        int price = Integer.parseInt(lineValues[PRICE_INDEX]);
        int size = Integer.parseInt(lineValues[SIZE_INDEX]);
        String orderUpdateType = lineValues[ORDER_UPDATE_TYPE_INDEX];
        switch (orderUpdateType) {
            case ASK_TYPE -> {
                orderTable.getAskMap().put(price, size);
                orderTable.getBidMap().remove(price);
            }
            case BID_TYPE -> {
                orderTable.getBidMap().put(price, size);
                orderTable.getAskMap().remove(price);
            }
        }
    }
}
