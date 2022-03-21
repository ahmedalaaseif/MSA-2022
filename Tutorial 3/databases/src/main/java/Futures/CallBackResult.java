package Futures;

public interface CallBackResult {
    void onData(Long data);
    void onError(Throwable cause);
}
