package lazy;

import java.util.concurrent.locks.ReentrantLock;

public class DatabaseConnectionManagerMutex {
    private static volatile DatabaseConnectionManagerMutex instance;
    private static final ReentrantLock mtx = new ReentrantLock();



    private DatabaseConnectionManagerMutex(){

    }

    private static DatabaseConnectionManagerMutex getInstance(){
        if(instance==null){
            mtx.lock();
            try {
                if(instance==null){
                    instance=new DatabaseConnectionManagerMutex();
                }
            }
            finally {
                mtx.unlock();
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        System.out.println("Application started");
        DatabaseConnectionManagerMutex first =
                DatabaseConnectionManagerMutex.getInstance();

        DatabaseConnectionManagerMutex second =
                DatabaseConnectionManagerMutex.getInstance();

        System.out.println(first == second);

    }
}
