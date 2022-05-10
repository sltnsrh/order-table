package order.table;

import java.io.File;
import java.util.List;
import order.table.service.ReaderService;
import order.table.service.impl.ReaderServiceImpl;
import order.table.service.WriterService;
import order.table.service.impl.WriterServiceImpl;

public class Main {
    public static final String INPUT_PATH = "input.txt";
    public static void main(String[] args) {
        OrderTable orderTable = new OrderTable();
        OperationsDispatcher operationsDispatcher = new OperationsDispatcher(orderTable);
        ReaderService readerService = new ReaderServiceImpl(operationsDispatcher);
        File input = new File(INPUT_PATH);
        List<String> linesList = readerService.readFile(input);
        operationsDispatcher.doOperation(linesList);
    }
}
