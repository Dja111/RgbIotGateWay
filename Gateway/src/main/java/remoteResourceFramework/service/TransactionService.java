package remoteResourceFramework.service;

import remoteResourceFramework.exceptions.NoAvailableIDException;

import java.util.HashSet;
import java.util.Set;


public class TransactionService {

    private Set<Byte> usedTransactionIds;

    public TransactionService() {
        usedTransactionIds = new HashSet<>();
    }

    public synchronized Integer getNewTransactionID() throws NoAvailableIDException {
        for (byte transactionID = Byte.MIN_VALUE; transactionID <= Byte.MAX_VALUE; transactionID++) {
            if (!usedTransactionIds.contains(transactionID)) {
                usedTransactionIds.add(transactionID);
                return (int) transactionID;
            }
        }
        throw new NoAvailableIDException("no free transaction id available");
    }

    public synchronized void freeTransactionID(int transactionID) {
        usedTransactionIds.remove(transactionID);
    }
}