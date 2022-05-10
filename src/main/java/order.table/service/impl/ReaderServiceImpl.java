package order.table.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import order.table.OperationsDispatcher;
import order.table.service.ReaderService;

public class ReaderServiceImpl implements ReaderService {
    private final OperationsDispatcher operationsDispatcher;

    public ReaderServiceImpl(OperationsDispatcher operationsDispatcher) {
        this.operationsDispatcher = operationsDispatcher;
    }

    @Override
    public void readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String value = reader.readLine();
            while(value != null) {
                operationsDispatcher.getOperation(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read a line from the file " + file, e);
        }
    }
}
