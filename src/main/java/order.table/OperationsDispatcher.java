package order.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import order.table.service.OperationService;
import order.table.service.impl.OrderServiceImpl;
import order.table.service.impl.QueryServiceImpl;
import order.table.service.impl.UpdateServiceImpl;
import order.table.service.impl.WriterServiceImpl;

public class OperationsDispatcher {
    private static final int OPERATION_INDEX = 0;
    private static final String COMMA_SEPARATOR = ",";
    private static final Map<String, OperationService> operations;
    private final OrderTable orderTable;

    static {
        operations = new HashMap<>();
        operations.put("u", new UpdateServiceImpl());
        operations.put("q", new QueryServiceImpl(new WriterServiceImpl()));
        operations.put("o", new OrderServiceImpl());
    }

    public OperationsDispatcher(OrderTable orderTable) {
        this.orderTable = orderTable;
    }

    public void doOperation(List<String> linesList) {
        linesList.forEach(line -> {
            String[] lineValues = line.split(COMMA_SEPARATOR);
            operations.get(lineValues[OPERATION_INDEX]).doOperation(lineValues, orderTable);
        });
    }
}
