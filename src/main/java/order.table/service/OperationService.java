package order.table.service;

import order.table.OrderTable;

public interface OperationService {
    void doOperation(String[] lineValues, OrderTable orderTable);
}
