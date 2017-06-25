package whiteboard.Shared;

import java.io.Serializable;

/**
 * Created by Vai on 6/25/17.
 */
public class ClearEvent implements Serializable {

    private boolean clearAll;

    public ClearEvent(boolean clearAll){
        this.clearAll = clearAll;
    }

    public boolean isClearAll() {
        return clearAll;
    }
}
