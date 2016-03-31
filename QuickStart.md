## About JSend NSCA ##

JSend NSCA is an API for sending Nagios Passive Checks to the [Nagios](http://www.nagios.org/) [NSCA](http://www.nagios.org/faqs/index.php?section_id=5&expand=false&showdesc=true) add-on.

The current solution for sending Passive Checks is to use Runtime().exec(..) and call the send\_nsca tool. Not only is this not cross-platform as send\_nsca runs on nix platforms only but it is also slow and a bit ugly IMHO.

I'm using the basis of this code currently where work in a production environment so I guess you too can safely and reliably use this code.

Thanks goes to the [NagiosAppender](http://nagiosappender.sourceforge.net/) project which I used to work out the details of the Passive Check protocol.

## Quick Start ##

Download the distribution, extract and include the bin/jsendnsca-jar-with-dependencies.jar in your class path.

Alternatively, include the target/jsendnsca-1.0.jar in your classpath. Also include dependencies as listed in the pom.xml as required.

The sample code below is used to send a passive check

```
package com.googlecode.jsendnsca.sender;

import com.googlecode.jsendnsca.sender.MessagePayload;
import com.googlecode.jsendnsca.sender.NagiosException;
import com.googlecode.jsendnsca.sender.NagiosPassiveCheckSender;
import com.googlecode.jsendnsca.sender.NagiosSettings;

public class NagiosPassiveCheckSenderTest {

    public void sendPassiveAlert() {
        try {
            final NagiosSettings nagiosSettings = new NagiosSettings();
            nagiosSettings.setNagiosHost("localhost");
            nagiosSettings.setPassword("hasturrocks");
            
            final NagiosPassiveCheckSender passiveAlerter = new NagiosPassiveCheckSender(nagiosSettings);
            
            final MessagePayload payload = new MessagePayload();
            payload.setHostname("localhost");
            payload.setLevel(MessagePayload.LEVEL_CRITICAL);
            payload.setServiceName("Test Service Name");
            payload.setMessage("Test Message");
            
            passiveAlerter.send(payload);
        } catch (NagiosException ex) {
            ex.printStackTrace();
        }
    }
}
```

## Command Line Usage ##

In addition to using the API from your code for sending passive checks, a command line tool is available.

From the extracted bin directory you can use the scripts as follows:

1. (Unix) Add executable permissions to the jsend\_nsca script

```
rpatel@jinja:~/workspace/jsendnsca/bin$ chmod a+x jsend_nsca
```

2. Running the script with nor arguments displays the usage

```
rpatel@jinja:~/workspace/jsendnsca/bin$ ./jsend_nsca
usage: jsend-nsca [OPTIONS] OK|WARNING|CRITICAL servicename message
[OPTIONS]
     --alertinghost <alerting host>          the host sending the passive check, defaults to using
                                             the hostname of the machine
     --nagioshost <nagios host>              the host where nagios is running, defaults to localhost
     --password <nsca password>              the password configured in NSCA, defaults to
                                             hasturrocks
     --port <port>                           the port on which NSCA is running, defaults to 5667
     --timeout <send timeout>                the timeout to use when sending the passive check in
                                             ms, defaults to 10000
rpatel@jinja:~/workspace/jsendnsca/bin$ 
```

3. Example passive check

```
rpatel@jinja:~/workspace/jsendnsca/bin$ ./jsend_nsca --nagioshost mynagioshost --password secret --port 5667 --timeout 20000 --alertinghost localhost OK "test service name" "test message"
Sent Passive Check Successfully
```