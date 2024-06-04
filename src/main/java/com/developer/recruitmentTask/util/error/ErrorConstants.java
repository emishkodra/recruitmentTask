package com.developer.recruitmentTask.util.error;

import java.net.URI;

public class ErrorConstants {

    private ErrorConstants(){
    }

    public static final String DEFAULT_BASE_STRING = "https://www.google.com/";
    public static final URI DEFAULT_TYPE = URI.create(DEFAULT_BASE_STRING + "error");

    public static final String BAD_REQUEST_KEY = "400";
    public static final String UNAUTHORIZED_ERROR_KEY = "401";
    public static final String NOT_FOUND_KEY = "404";
    public static final String NO_CONTENT_KEY = "204";
    public static final String SERVER_ERROR_KEY = "500";

    public static final String CONFLICT_ID_NOT_FOUND_MESSAGE = "ID can't be null";
    public static final String ID_NOT_FOUND_MESSAGE = "ID can't be 0 or empty";
    public static final String INVALID_ROLE_MESSAGE = "Invalid role specified";

    public static final String USER_NOT_FOUND_MESSAGE = "User not found";
    public static final String AUTHOR_NOT_FOUND_MESSAGE = "Author not found for the given ID";
    public static final String BOOK_NOT_FOUND_MESSAGE = "Book not found for the given ID";
    public static final String BORROW_RECORD_NOT_FOUND_MESSAGE = "Borrow Record not found for the given ID";
    public static final String INVALID_AUTHOR_MESSAGE = "Invalid author data";
    public static final String BOOK_ASSOCIATED_WITH_BORROW_RECORD = "Cannot delete book as it has associated borrow records.";
    public static final String BORROW_RECORD_ASSOCIATED_WITH_BOOK_MESSAGE = "Cannot delete borrow records as it has associated book.";

    public static final String INVALID_ISBN_MESSAGE = "Invalid ISBN format";
    public static final String INVALID_BOOK_MESSAGE = "Invalid book data";
    public static final String RETURN_DATE_AFTER_BORROW_DATE_MESSAGE = "Return date must be after the borrow date";

}
