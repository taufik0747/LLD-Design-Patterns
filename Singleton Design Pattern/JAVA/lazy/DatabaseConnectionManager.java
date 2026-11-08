package lazy;

public class DatabaseConnectionManager {

    private static DatabaseConnectionManager databaseConnectionManager;
    private DatabaseConnectionManager(){

    }

    public static synchronized  DatabaseConnectionManager getInstance() {
        if (databaseConnectionManager == null) {
            databaseConnectionManager = new DatabaseConnectionManager();
        }
        return databaseConnectionManager;
    }

    public void connect() {
        System.out.println("Connecting to database...");
    }


    public static void main(String[] args) {
        System.out.println("Application started");
        DatabaseConnectionManager first =
                DatabaseConnectionManager.getInstance();

        DatabaseConnectionManager second =
                DatabaseConnectionManager.getInstance();

        System.out.println(first == second);
        first.connect();
    }

}
