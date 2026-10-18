package practice.librarymanagement;

public class Book {

    private final String bookId;
    private final String title;
    private BookStatus bookStatus;

    public Book(String bookId, String title){
        if(bookId==null || bookId.isBlank()){
            throw new IllegalArgumentException("BookId is invalid");
        }

        if(title==null || title.isBlank()){
            throw new IllegalArgumentException("Book title is invalid");
        }
        this.bookId=bookId;
        this.title=title;
        this.bookStatus=BookStatus.AVAILABLE;
    }

    public String getBookId(){
        return bookId;
    }

    public String getTitle(){
        return title;
    }

    public BookStatus getBookStatus(){
        return bookStatus;
    }

    public void borrow(){
        if(bookStatus == BookStatus.BORROWED){
           throw new IllegalStateException("Book is currently not available");
        }
         bookStatus=BookStatus.BORROWED;
    }

    public void returnBook(){
        if(bookStatus==BookStatus.AVAILABLE){
            throw new IllegalStateException("Book is already available");
        }
        bookStatus=BookStatus.AVAILABLE;
    }


}
