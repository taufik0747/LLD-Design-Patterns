package practice.librarymanagement;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        Book b1 = new Book("B1", "Clean Code");
        Book b2 = new Book("B2", "Effective Java");
        Book b3 = new Book("B3", "Design Patterns");
        Book b4 = new Book("B4", "Java Concurrency");

        Member m1 = new Member("M1", "Kevin");

        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);
        library.addBook(b4);

        library.addMember(m1);

        library.borrowBook("M1", "B1");

        System.out.println(m1.getBorrowedBooks().size());
        System.out.println(b1.getBookStatus());
    }
}
