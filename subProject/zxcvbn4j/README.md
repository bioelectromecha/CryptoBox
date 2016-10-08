
# zxcvbn4j [![Build Status](https://travis-ci.org/nulab/zxcvbn4j.svg?branch=master)](https://travis-ci.org/nulab/zxcvbn4j) [![Coverage Status](https://coveralls.io/repos/nulab/zxcvbn4j/badge.svg?branch=master&service=github)](https://coveralls.io/github/nulab/zxcvbn4j?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nulab-inc/zxcvbn/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nulab-inc/zxcvbn)

This is a java port of [zxcvbn](https://github.com/dropbox/zxcvbn), which is a JavaScript password strength generator.

## Update

The following version is a port of [zxcvbn 4.3.0](https://github.com/dropbox/zxcvbn/releases/tag/4.3.0)

* 2016/10/01 1.1.6 released.
* 2016/09/27 1.1.5 released.
* 2016/07/08 1.1.4 released.
* 2016/05/27 1.1.3 released.
* 2016/05/25 1.1.2 released.
* 2016/03/19 1.1.1 released.
* 2016/03/06 1.1.0 released.

The following version is a port of [zxcvbn 4.2.0](https://github.com/dropbox/zxcvbn/releases/tag/4.2.0)

* 2016/01/28 1.0.2 released.
* 2016/01/27 1.0.1 released.
* 2015/12/24 1.0.0 released.

## Special Feature

* It includes JIS keyboard layout in spatial matching.
* Localization feedback messages.

## Install

### gradle

```
compile 'com.nulab-inc:zxcvbn:1.1.6'
```

### maven

```
<dependency>
  <groupId>com.nulab-inc</groupId>
  <artifactId>zxcvbn</artifactId>
  <version>1.1.6</version>
</dependency>
```

## Build

To build:

```
$ git clone git@github.com:nulab/zxcvbn4j.git
$ cd zxcvbn4j/
$ ./gradlew build
```

## Usage

Basic Usage. This is also available Android.

``` java
Zxcvbn zxcvbn = new Zxcvbn();
Strength strength = zxcvbn.measure("This is password");
```

If you want to add your own dictionary, put the keyword list of List <String> type to the second argument.

``` java
List<String> sanitizedInputs = new ArrayList();
sanitizedInputs.add("nulab");
sanitizedInputs.add("backlog");
sanitizedInputs.add("cacoo");
sanitizedInputs.add("typetalk");

Zxcvbn zxcvbn = new Zxcvbn();
Strength strength = zxcvbn.measure("This is password", sanitizedInputs);
```

The return result is "Strength". It's almost the same as [zxcvbn](https://github.com/dropbox/zxcvbn).

```
# estimated guesses needed to crack password
strength.guesses

# order of magnitude of strength.guesses
strength.guessesLog10

# dictionary of back-of-the-envelope crack time
# estimations, in seconds, based on a few scenarios
strength.crackTimeSeconds
{
  # online attack on a service that ratelimits password auth attempts.
  onlineThrottling100PerHour

  # online attack on a service that doesn't ratelimit,
  # or where an attacker has outsmarted ratelimiting.
  onlineNoThrottling10PerSecond

  # offline attack. assumes multiple attackers,
  # proper user-unique salting, and a slow hash function
  # w/ moderate work factor, such as bcrypt, scrypt, PBKDF2.
  offlineSlowHashing1e4PerSecond

  # offline attack with user-unique salting but a fast hash
  # function like SHA-1, SHA-256 or MD5. A wide range of
  # reasonable numbers anywhere from one billion - one trillion
  # guesses per second, depending on number of cores and machines.
  # ballparking at 10B/sec.
  offlineFastHashing1e10PerSecond
}

# same keys as result.crack_time_seconds,
# with friendlier display string values:
# "less than a second", "3 hours", "centuries", etc.
strength.crackTimeDisplay

# Integer from 0-4 (useful for implementing a strength bar)
# 0 Weak        （guesses < ^ 3 10）
# 1 Fair        （guesses <^ 6 10）
# 2 Good        （guesses <^ 8 10）
# 3 Strong      （guesses < 10 ^ 10）
# 4 Very strong （guesses >= 10 ^ 10）
strength.score

# verbal feedback to help choose better passwords. set when score <= 2.
strength.feedback
{
  # explains what's wrong, eg. 'this is a top-10 common password'.
  # not always set -- sometimes an empty string
  warning

  # a possibly-empty list of suggestions to help choose a less
  # guessable password. eg. 'Add another word or two'
  suggestions
}

# the list of patterns that zxcvbn based the guess calculation on.
strength.sequence

# how long it took zxcvbn to calculate an answer, in milliseconds.
strength.calc_time
```

## Localization feedback messages

The zxcvbn4j can be localized localize the english feedback message to other languages.

``` java
// Get the Strength instance.
Zxcvbn zxcvbn = new Zxcvbn();
Strength strength = zxcvbn.measure("This is password");

// Get the ResourceBundle based on the name and locale of the property file(※).
ResourceBundle resourceBundle = ResourceBundle.getBundle("This is bundle name", Locale.JAPAN);

// Feedback to pass the ResourceBundle. And to generate a localized Feedback.
Feedback feedback = strength.getFeedback();
Feedback localizedFeedback = feedback.withResourceBundle(resourceBundle);

// getSuggestions() and getWarning() returns localized feedback message.
List<String> localizedSuggestions = localizedFeedback.getSuggestions();
String localizedWarning = localizedFeedback.getWarning();
```

Defined Key and the message in the properties file. Reference the [messages.properties](https://github.com/nulab/zxcvbn4j/blob/master/src/main/resources/com/nulabinc/zxcvbn/messages.properties).

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/nulab/zxcvbn4j/issues).

## License

MIT License

* http://www.opensource.org/licenses/mit-license.php

## Requires Java

* Java 1.7+

## Application using this library

- [Cacoo](https://cacoo.com/)
- [Typetalk](https://typetalk.in/)
- [JetBrains Hub](https://www.jetbrains.com/hub/)
