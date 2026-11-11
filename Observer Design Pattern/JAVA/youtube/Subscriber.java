package youtube;

public class Subscriber implements Observer{
    private String name;

    public Subscriber(String name){
        this.name=name;
    }

    @Override
    public void update(String videoName) {
        System.out.println("New Video Out "+videoName+ " to "+name);
    }
}
