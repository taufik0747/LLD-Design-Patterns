package playlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Playlist implements Iterable<String>{

    private final List<String> songs = new ArrayList<>();

    public void addSong(String song){
        songs.add(song);
    }

    @Override
    public Iterator<String> iterator() {
        return new SongIterator(songs);
    }
}
