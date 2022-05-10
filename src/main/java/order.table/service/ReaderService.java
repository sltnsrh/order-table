package order.table.service;

import java.io.File;
import java.util.List;

public interface ReaderService {
    List<String> readFile(File file);
}
