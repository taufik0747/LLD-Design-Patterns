package filesystemcomponent;

import java.util.ArrayList;
import java.util.List;

public class Folder implements FileSystemComponent{
    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
       children.add(component);
    }

    @Override
    public int getSize() {
        int totalSize = 0;

        for (FileSystemComponent component : children) {
            totalSize += component.getSize();
        }

        return totalSize;
    }
}
