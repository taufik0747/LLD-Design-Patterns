package playlist;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

        Playlist playlist = new Playlist();
        playlist.addSong("Song A");
        playlist.addSong("Song B");
        playlist.addSong("Song C");

        Iterator<String> songs = playlist.iterator();
        for (String song : playlist) {
            System.out.println(song);
        }


    }
}
