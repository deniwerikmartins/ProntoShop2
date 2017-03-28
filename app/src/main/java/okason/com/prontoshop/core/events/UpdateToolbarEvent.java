package okason.com.prontoshop.core.events;

import okason.com.prontoshop.model.LineItem;

import java.util.List;

/**
 * Created by Valentine on 5/2/2016.
 */
public class UpdateToolbarEvent {
    private final List<LineItem> mLineItems;

    public UpdateToolbarEvent(List<LineItem> lineItems) {
        mLineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
        return mLineItems;
    }
}
