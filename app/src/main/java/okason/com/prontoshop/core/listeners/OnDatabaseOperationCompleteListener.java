package okason.com.prontoshop.core.listeners;

/**
 * Created by Valentine on 5/2/2016.
 */
public interface OnDatabaseOperationCompleteListener {
    void onDatabaseOperationFailed(String error);
    void onDatabaseOperationSucceded(String message);
}
