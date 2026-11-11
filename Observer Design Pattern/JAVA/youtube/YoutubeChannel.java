package youtube;

import java.util.ArrayList;
import java.util.List;

public class YoutubeChannel {
    private List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }

    public void uploadVideo(String videoName){
        for(Observer observer: observers){
            observer.update(videoName);
        }
    }

    public static void main(String[] args) {
        YoutubeChannel channel = new YoutubeChannel();

        Subscriber alice = new Subscriber("Alice");
        Subscriber bob = new Subscriber("Bob");

        channel.subscribe(alice);
        channel.subscribe(bob);

        channel.uploadVideo("JAVA OOP");
        channel.unsubscribe(bob);
        channel.uploadVideo("Design patterns");
    }
}
