package practice.librarymanagement;

import java.util.HashMap;
import java.util.Map;

public class Library {
    private final Map<String,Book> books;
    private final Map<String,Member> members;

    public Library(){
        this.books=new HashMap<>();
        this.members=new HashMap<>();
    }

    public void addBook(Book book){
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
      if(books.containsKey(book.getBookId())){
          throw new IllegalStateException("Book already exist in the libray");
      }
          books.put(book.getBookId(), book);
    }

    public void addMember(Member member){
        if(member==null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if(members.containsKey(member.getMemberId())){
            throw new IllegalStateException("Member already exist");
        }
            members.put(member.getMemberId(), member);
    }

    public void borrowBook(String memberId, String bookId) {

        Member member = members.get(memberId);
        Book book = books.get(bookId);

        if (member == null) {
            throw new IllegalArgumentException("Member does not exist");
        }

        if (book == null) {
            throw new IllegalArgumentException("Book does not exist");
        }

        BookStatus currentBookStatus = book.getBookStatus();
        if (currentBookStatus != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available");
        }

        if (!member.canBorrowMore()) {
            throw new IllegalStateException("Member reached borrowing limit");
        }

        if (member.hasBorrowed(book)) {
            throw new IllegalStateException("Member already borrowed this book");
        }

        member.addBorrowedBook(book);
        book.borrow();

    }

    public void returnBook(String memberId, String bookId) {

        Member member = members.get(memberId);
        Book book = books.get(bookId);

        if (member == null) {
            throw new IllegalArgumentException("Member does not exist");
        }

        if (book == null) {
            throw new IllegalArgumentException("Book does not exist");
        }

        BookStatus currentBookStatus = book.getBookStatus();
        if (currentBookStatus != BookStatus.BORROWED) {
            throw new IllegalStateException("Book is already available");
        }

       if(!member.hasBorrowed(book)){
           throw new IllegalStateException("Member does not have this book");
       }

       member.removeBorrowedBook(book);
       book.returnBook();

    }





}
