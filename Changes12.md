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