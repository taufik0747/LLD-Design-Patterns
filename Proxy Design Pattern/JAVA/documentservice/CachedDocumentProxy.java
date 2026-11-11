package documentservice;


import java.util.HashMap;
import java.util.Map;

public class CachedDocumentProxy implements DocumentRepository {
    private final DocumentRepository repository;
    private final Map<Integer, String> cache = new HashMap<>();

    public CachedDocumentProxy(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getDocument(int id) {
        if(cache.containsKey(id)){
            return cache.get(id);
        }
        String document = repository.getDocument(id);
        cache.put(id, document);
        return document;
    }
}
