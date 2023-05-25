package systemTests;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestResultLogger implements TestWatcher, AfterTestExecutionCallback {

    private static final Logger LOGGER = Logger.getLogger(TestResultLogger.class.getName());

    static {
        LOGGER.setLevel(Level.INFO); //Istenilen seviyede ayarlanabilir
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL); 
        LOGGER.addHandler(handler);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        LOGGER.log(Level.SEVERE, "Test Failed: " + context.getDisplayName(), cause);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isPresent()) {
            testFailed(context,context.getExecutionException().get());
        } 
    }
}
