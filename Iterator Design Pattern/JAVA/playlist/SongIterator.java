package playlist;

import java.util.*;

public class SongIterator implements Iterator<String> {
    private final List<String> songs;
    private int index = 0;

    public SongIterator(List<String> songs) {
        this.songs = songs;
    }

    @Override
    public boolean hasNext() {
        return index < songs.size();
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return songs.get(index++);
    }


}
