package mainpack;

public class BookParam {
        private String isbn;
        private String titleBook;
        private String authors;
        private String year;

        public BookParam() {}

        @Override
        public String toString() {
            return "Book{" +
                    "isbn='" + isbn + '\'' +
                    ", title='" + titleBook + '\'' +
                    ", authors='" + authors + '\'' +
                    ", year='" + year + '\'' +
                    '}';
        }

        public String getIsbn() {return isbn;}
        public void setIsbn(String isbn) {this.isbn = isbn;}
        public String getBookTitle() {return titleBook;}
        public void setBookTitle(String title) {this.titleBook = title;}
        public String getAuthors() {return authors;}
        public void setAuthors(String authors) {this.authors = authors;}
        public String getYear() {return year;}
        public void setYear(String year) {this.year = year;}
    }

