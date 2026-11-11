package documentservice;

import documentservice.CachedDocumentProxy;
import documentservice.DatabaseDocumentRepository;
import documentservice.DocumentRepository;

public class Main {
    public static void main(String[] args) {
        DocumentRepository realRepository =
                new DatabaseDocumentRepository();

        DocumentRepository proxy =
                new CachedDocumentProxy(realRepository);

        System.out.println(proxy.getDocument(102));
        System.out.println(proxy.getDocument(102));
        System.out.println(proxy.getDocument(103));
        System.out.println(proxy.getDocument(102));
    }
}