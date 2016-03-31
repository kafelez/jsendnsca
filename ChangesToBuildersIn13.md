# Introduction #

The builders now longer have static methods so you need to new them up

```
import java.io.IOException;

import com.googlecode.jsendnsca.core.Level;
import com.googlecode.jsendnsca.core.MessagePayload;
import com.googlecode.jsendnsca.core.NagiosException;
import com.googlecode.jsendnsca.core.NagiosPassiveCheckSender;
import com.googlecode.jsendnsca.core.NagiosSettings;
import com.googlecode.jsendnsca.core.builders.MessagePayloadBuilder;
import com.googlecode.jsendnsca.core.builders.NagiosSettingsBuilder;

public class Snippet {
    
    public static void main(String[] args) {
        final NagiosSettings nagiosSettings = new NagiosSettingsBuilder()
                .withNagiosHost("localhost")
                .withPort(5667)
                .withConnectionTimeout(5000)
                .withResponseTimeout(15000)
                .withPassword("password")
                .create();
    
        final NagiosPassiveCheckSender passiveAlerter = new NagiosPassiveCheckSender(
                nagiosSettings);
    
        final MessagePayload payload = new MessagePayloadBuilder()
            .withHostname("localhost")
            .withLevel(Level.CRITICAL)
            .withServiceName("Test Service Name")
            .withMessage("Test Message")
            .create();
    
        try {
            passiveAlerter.send(payload);
        } catch (NagiosException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```