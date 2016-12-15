package eas.com.exception;

/**
 * Own Exception of Quick Mart
 * Created by eduardo on 12/12/2016.
 */
public class QuickMartException extends Exception {

    public QuickMartException(String message) {
        super("[Error Detected] "+ message);
    }
}
