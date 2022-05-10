package order.table;

import order.table.service.WriterService;
import order.table.service.impl.OperationServiceImpl;

public class OperationsDispatcher {
    private static final int OPERATION_INDEX = 0;
    private static final String COMMA_SEPARATOR = ",";
    private static final String UPGRADE_MARKER = "u";
    private static final String QUERY_MARKER = "q";
    private static final String ORDER_MARKER = "o";
    private final OperationServiceImpl operationService;
    private final WriterService writerService;

    public OperationsDispatcher(OperationServiceImpl operationService, WriterService writerService) {
        this.operationService = operationService;
        this.writerService = writerService;
    }

    public void getOperation(String lineText) {
        String[] lineValues = lineText.split(COMMA_SEPARATOR);
        switch (lineValues[OPERATION_INDEX]) {
            case UPGRADE_MARKER -> operationService.doUpdate(lineValues);
            case QUERY_MARKER -> {
                String queryResult = operationService.doQuery(lineValues);
                writerService.writeToFile(queryResult);
            }
            case ORDER_MARKER -> operationService.doOrder(lineValues);
        }
    }
}
