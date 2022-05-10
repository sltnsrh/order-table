package order.table.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import order.table.service.WriterService;

public class WriterServiceImpl implements WriterService {
    private static final String OUTPUT_PATH = "output.txt";
    private final File file;

    public WriterServiceImpl() {
        this.file = new File(OUTPUT_PATH);
    }

    @Override
    public void writeToFile(String queryResult) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(queryResult + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data: " + queryResult
                    + " to a file", e);
        }
    }
}
