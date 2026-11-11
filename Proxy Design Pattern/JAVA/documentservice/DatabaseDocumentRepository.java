package documentservice;

public class DatabaseDocumentRepository implements DocumentRepository{
    @Override
    public String getDocument(int id) {
        System.out.println("Fetching document from DB: " + id);
        return "Document-" + id;
    }
}
