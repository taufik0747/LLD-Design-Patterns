package practice.librarymanagement;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private static final int MAXIMUM_BORROWED_BOOKS=3;
    private final String memberId;
    private final String name;
    private final List<Book> borrowedBooks;

    public Member(String memberId, String name){
        if(memberId==null || memberId.isBlank()){
            throw new IllegalArgumentException("memberId is invalid");
        }

        if(name==null || name.isBlank()){
            throw new IllegalArgumentException("Name is invalid");
        }
        this.memberId=memberId;
        this.name=name;
        this.borrowedBooks=new ArrayList<>();
    }

    public String getMemberId(){
        return memberId;
    }

    public String getName(){
        return name;
    }

    public void addBorrowedBook(Book book){
        if(borrowedBooks.size()>=MAXIMUM_BORROWED_BOOKS){
            throw new IllegalStateException("Books size is already maximum");
        }
        if(borrowedBooks.contains(book)){
            throw new IllegalStateException("Book already exists");
        }
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book){
        if(borrowedBooks.isEmpty()){
            throw new IllegalStateException("Book size is zero");
        }
        if(!borrowedBooks.contains(book)){
            throw new IllegalStateException("Book can not be removed");
        }
        borrowedBooks.remove(book);
    }

    public boolean canBorrowMore(){
        return borrowedBooks.size() < MAXIMUM_BORROWED_BOOKS;
    }

    public boolean hasBorrowed(Book book){
        return borrowedBooks.contains(book);
    }

    public List<Book> getBorrowedBooks(){
    return List.copyOf(borrowedBooks);
    }

}
