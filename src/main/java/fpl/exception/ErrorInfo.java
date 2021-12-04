/* (C)2021 */
package fpl.exception;

public class ErrorInfo {
    public final Object url;
    public final Object errorCode;

    public ErrorInfo(Object url, Object errorCode) {
        this.url = url;
        this.errorCode = errorCode;
    }
}