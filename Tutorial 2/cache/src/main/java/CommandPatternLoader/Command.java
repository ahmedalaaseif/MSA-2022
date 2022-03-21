package CommandPatternLoader;

import java.util.Map;

public abstract class Command implements Runnable {

    protected Map<String, Object> parameters;

    protected boolean shouldReturnResponse() {
        return true;
    }

    final public void run() {
      // Some Method
    }

    final public void init(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    protected abstract void execute(Map<String, Object> requestMapData) throws Exception;
}
