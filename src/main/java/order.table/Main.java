package order.table;

import java.io.File;
import order.table.service.ReaderService;
import order.table.service.impl.OperationServiceImpl;
import order.table.service.impl.ReaderServiceImpl;
import order.table.service.WriterService;
import order.table.service.impl.WriterServiceImpl;

public class Main {
    public static final String INPUT_PATH = "input.txt";
    public static void main(String[] args) {
        OperationServiceImpl operationService = new OperationServiceImpl();
        WriterService writerService = new WriterServiceImpl();
        OperationsDispatcher operationsDispatcher = new OperationsDispatcher(operationService,
                writerService);
        ReaderService readerService = new ReaderServiceImpl(operationsDispatcher);
        File input = new File(INPUT_PATH);
        readerService.readFile(input);
    }
}
