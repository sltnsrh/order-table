package order.table.service;

public interface OperationService {
    void doUpdate(String[] lineValues);

    String doQuery(String[] lineValues);

    void doOrder(String[] lineValues);
}
