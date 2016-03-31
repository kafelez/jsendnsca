# Changes for 2.0.0 #

  * [Issue 26](https://code.google.com/p/jsendnsca/issues/detail?id=26),
    * Using enums now only instead of integers which removes a load of validation to make sure people are setting valid levels, encryption etc
    * Exposed Encryptor interface so people can roll their own encryption as NSCA supports a whole host of which only a subset are currently implemented
    * Using nice features of Junit 4.7 such as expected exception rule and replaced IllegalArgument checks with Validate class from commons-lang
    * Maven now the build system for the project
    * No new version for jsendnsca-cli - may reconsider if demand
  * [Issue 28](https://code.google.com/p/jsendnsca/issues/detail?id=28), Provide NagiosSettingsFactory for creation of nagios settings from properties file

# Changes for 1.3 #

Many thanks the Claus Ibsen for identifying and providing fixes for the below issues and enhancements

  * [Issue 18](https://code.google.com/p/jsendnsca/issues/detail?id=18), [Issue 19](https://code.google.com/p/jsendnsca/issues/detail?id=19) - MessagePayloadBuilder and NagiosSettingsBuilder now no longer static and need to be newed up so they are thread safe
  * [Issue 21](https://code.google.com/p/jsendnsca/issues/detail?id=21) - MessagePayload now overrides toString() with contents of payload for ease of debugging and also now serializable so can be sent across the wire
  * [Issue 22](https://code.google.com/p/jsendnsca/issues/detail?id=22) - NonBlockingNagiosPassiveCheckSender now has shutdown method and also allows you to supply your own ExecutorService implementation

# Changes for 1.2 #

  * [Issue 11](https://code.google.com/p/jsendnsca/issues/detail?id=11) - implemented NonBlockingNagiosPassiveCheckSender which allows you to send passive checks in a fire and forget way
  * Added MessagePayloadBuilder and NagiosSettingsBuilder
  * [Issue 12](https://code.google.com/p/jsendnsca/issues/detail?id=12) - Now supports triple-DES encryption, thanks to krisajenkins.
  * [Issue 13](https://code.google.com/p/jsendnsca/issues/detail?id=13) - Improved stub for testing which now allows inspection of sent alerts

# Changes for 1.1.1 #

  * Removed dependencies on common-lang and common-io in Core API
  * New EncryptionUtils class for handling encryption
  * JUnit and Cobertura coverage reports being generated on Ant site target
  * General refactoring in places
  * Code coverage improved to 93%

# Changes for 1.1 #

  * Code split into two parts, core API and CLI projects
  * Connection timeout added
  * Ant used as build tool instead of maven
  * User and Developer guide created
  * MockNscaDaemon added for unit testing socket code
  * Encryption mode no longer hard coded to XOR, can now choose between no or XOR encryption
  * Checking of illegal arguments where required

# Known Issues #

  * Currently only supports no or XOR "encryption"