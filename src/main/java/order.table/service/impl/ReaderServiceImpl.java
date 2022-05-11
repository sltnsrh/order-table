package order.table.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import order.table.service.ReaderService;

public class ReaderServiceImpl implements ReaderService {

    @Override
    public List<String> readFile(File file) {
        List<String> linesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while(line != null) {
                linesList.add(line);
                line = reader.readLine();
            }
            return linesList;
        } catch (IOException e) {
            throw new RuntimeException("Can't read a line from the file " + file, e);
        }
    }
}
