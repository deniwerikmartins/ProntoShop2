package okason.com.prontoshop.core.listeners;

import okason.com.prontoshop.model.LineItem;

/**
 * Created by Valentine on 4/28/2016.
 */
public interface CartActionsListener {
    void onItemDeleted(LineItem item);
    void onItemQtyChange(LineItem item, int qty);
}
