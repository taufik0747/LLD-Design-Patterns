package filesystemcomponent;

public class Main {
    public static void main(String[] args) {
        Folder root = new Folder("root");
        Folder documents = new Folder("documents");

        FileSystemComponent obj = new Folder("root");
        FileSystemComponent obj2 = new FileItem("root", 1);
        obj.getSize();
        root.add(new FileItem("test.txt",10));
        root.getSize();

        documents.add(new FileItem("resume.pdf",20));
        documents.add(new FileItem("notes.txt",5));

        root.add(documents);
        System.out.println(root.getSize());
    }

}
