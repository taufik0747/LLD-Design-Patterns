package appconfig;

public class AppConfig {

    private final String appName;
    private static final AppConfig instance = new AppConfig();

    private AppConfig(){
        this.appName = "Java Academy";
        System.out.println("Singleton AppConfig");
    }

    public static AppConfig getInstance(){
        return instance;
    }

    public String getAppName(){
        return appName;
    }

    public static void main(String[] args) {
        AppConfig first = AppConfig.getInstance();
        AppConfig second = AppConfig.getInstance();

        System.out.println(first==second);
        System.out.println(first.getAppName());
        System.out.println(second.getAppName());
    }
}
