
```
import java.io.IOException;

import com.googlecode.jsendnsca.core.*;
import com.googlecode.jsendnsca.core.builders.*;

public class QuickStart {
    
    public static void main(String[] args) {
        NagiosSettings nagiosSettings = new NagiosSettingsBuilder()
            .withNagiosHost("localhost")
            .withPort(5667)
            .withConnectionTimeout(5000)
            .withResponseTimeout(15000)
            .withPassword("password")
            .create();
    
        NagiosPassiveCheckSender sender = new NagiosPassiveCheckSender(
                nagiosSettings);
    
        MessagePayload payload = new MessagePayloadBuilder()
            .withHostname("localhost")
            .withLevel(Level.CRITICAL)
            .withServiceName("Test Service Name")
            .withMessage("Test Message")
            .create();
    
        try {
            sender.send(payload);
        } catch (NagiosException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```