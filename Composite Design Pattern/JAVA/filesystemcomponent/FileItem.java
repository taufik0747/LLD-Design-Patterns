package filesystemcomponent;

public class FileItem implements FileSystemComponent{
    private final String name;
    private final int size;

    public FileItem(String name, int size){
        this.name=name;
        this.size=size;
    }

    @Override
    public int getSize() {
        return size;
    }
}
