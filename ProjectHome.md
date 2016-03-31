## Overview ##

JSend NSCA is Java API for sending passive checks to the [Nagios NSCA add-on](http://exchange.nagios.org/directory/Addons/Passive-Checks/NSCA--2D-Nagios-Service-Check-Acceptor/details).

By using JSend NSCA, you can easily integrate your Java applications into a Nagios monitored environment thereby notifying [Nagios](http://www.nagios.com/#ref=BZErMl)/[/Icinga](https://www.icinga.org)[/Opsview](http://www.opsview.com/) of problems and issues during the running of your application.

## Quick Start Example Code 2.0.1 ##

```
package com.googlecode.jsendnsca.quickstart;

import java.io.IOException;

import com.googlecode.jsendnsca.Level;
import com.googlecode.jsendnsca.MessagePayload;
import com.googlecode.jsendnsca.NagiosException;
import com.googlecode.jsendnsca.NagiosPassiveCheckSender;
import com.googlecode.jsendnsca.NagiosSettings;
import com.googlecode.jsendnsca.builders.MessagePayloadBuilder;
import com.googlecode.jsendnsca.builders.NagiosSettingsBuilder;
import com.googlecode.jsendnsca.encryption.Encryption;

public class QuickStart {

    public static void main(String[] args) {
        NagiosSettings settings = new NagiosSettingsBuilder()
            .withNagiosHost("nagiosHostNameOrIPAddress")
            .withPort(5667)
            .withEncryption(Encryption.XOR)
            .create();
        
        MessagePayload payload = new MessagePayloadBuilder()
            .withHostname("hostname of machine sending check")
            .withLevel(Level.OK)
            .withServiceName("Service Name")
            .withMessage("should work if everything set up OK")
            .create();
        
        NagiosPassiveCheckSender sender = new NagiosPassiveCheckSender(settings);
        
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

[click here for example code for 1.3 version](http://code.google.com/p/jsendnsca/wiki/QuickStart13)

## Updates ##

  * 2010-09-22 - Version 2.0.1 - fixed [Issue 32](https://code.google.com/p/jsendnsca/issues/detail?id=32) regarding no password set in NSCA.cfg, see [Changes 2.0.1](Changes201.md)
  * 2010-06-29 - Version 2.0.0 - now using Maven for build, using Java 5 such as enums, implement your own Encryptor, see [Issue 26](https://code.google.com/p/jsendnsca/issues/detail?id=26) and [Changes 2.0.0](Changes200.md)
  * 2010-01-18 - Version 1.3.1 - build now targeted to Java 1.5
  * 2010-01-13 - Version 1.3 - number of fixes, particularly to builders in order to make them thread safe and enhancements to non blocking sender. Many thanks to Claus Ibsen for raising and fixing these issues
  * 2009-05-03 - Version 1.2 - Core API now has support for triple-DES encryption and non blocking sender
  * 2009-02-03 - Version 1.1.1 - minor update released, main improvement is no longer any dependencies on commons-io and commons-lang for the Core API
  * 2008-11-30 - Version 1.1 - Codebase split into two projects, core API and CLI tool. Ant used as build tool instead of Maven.
  * 2008-11-18 - Version 1.0 - first release

## Background ##

JSend NSCA was developed as a company I worked for used Nagios to monitor applications and servers. For existing applications written in Perl and c, there are options available to send passive checks but for Java applications, the option available was to shell out and execute the send\_nsca command line tool.

Although send\_nsca worked in this manner, it’s ugly and we preferred having the code within our applications for better performance, testability, cleanliness...

A search on the internet revealed a few options such as the [NagiosAppender](http://nagiosappender.sourceforge.net/) for log4j but in the end we settled on writing our own client. This client is currently in use thus proving the feasibility of the approach.

On the back of this, I decided to write JSend NSCA from the ground up as an exercise in TDD and thought I would make it available as an open source project so other developers can benefit from the functionality.

## Acknowledgements ##

Thanks goes to the [NagiosAppender](http://nagiosappender.sourceforge.net/) project for details of the NSCA protocol and inspiration for this project

## Feedback Please ##

If you are using JSend NSCA and find it useful, or conversely have any suggestions or improvements, please email [jsend-nsca](mailto:jsend-nsca@googlegroups.com), would really appreciate any feedback, both good and bad

## Used By ##

  * [Apache Camel Nagios Component](http://camel.apache.org/nagios.html)
  * Groundwork Open Source [JDMA](https://kb.groundworkopensource.com/display/SUPPORT/Technical+Product+Description+for+JDMA)
  * [jAlarms](http://jalarms.sourceforge.net/)
  * [bischeck](http://gforge.ingby.com/gf/project/bischeck/)
  * [Cisco Prime Network](http://www.cisco.com/c/dam/en/us/td/docs/net_mgmt/prime/network/4-2/open_source/CiscoPrimeNetwork-4-2-OpenSource.pdf)

<a href='http://www.nagios.com/#ref=BZErMl&amp;a_bid=805a1194'><img src='http://affiliate.nagios.com/accounts/default1/banners/805a1194.png' alt='Nagios | The Industry Standard in IT Infrastructure Monitoring' title='Nagios | The Industry Standard in IT Infrastructure Monitoring' width='234' height='60' /></a><img src='http://affiliate.nagios.com/scripts/imp.php?ref=BZErMl&amp;a_bid=805a1194' alt='' width='1' height='1' />

<table>
<blockquote><td><wiki:gadget url="http://www.ohloh.net/p/50678/widgets/project_partner_badge.xml" height="53" border="0"/><br>
<blockquote></td>
</blockquote><td><wiki:gadget url="http://www.ohloh.net/p/50678/widgets/project_basic_stats.xml" height="220" border="1"/><br>
</td>
</table>