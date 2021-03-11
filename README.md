# jQuestrade
[![](https://jitpack.io/v/mateimarica/j-questrade.svg)](https://jitpack.io/#mateimarica/j-questrade)

jQuestrade is a [Questrade API](https://www.questrade.com/api) wrapper written in Java. It aims to simplify access to Questrade's API.

<br>

## Features
* Automatic access token renewal
* Methods for all account calls and most market calls
* [Authorization relaying](https://github.com/mateimarica/j-questrade/wiki/Using-an-authorization-relay)
* Ability to interact with Questrade API responses as Java objects

<br>

## Example Usage

```java
Questrade q = new Questrade(refreshToken);

try {
	q.activate(); // This must be called before making API requests
	
	Account[] accs     = q.getAccounts();  // Get all accounts
	ZonedDateTime time = q.getTime();      // Get server time
	
	ZonedDateTime startTime = ZonedDateTime.now().minusMonths(1);
	ZonedDateTime endTime   = ZonedDateTime.now();
	
	// Get all orders for the first account in the last month
	Order[] orders = q.getOrders(accs[0].getNumber(), startTime, endTime);
	
} catch (RefreshTokenException e) { 
	// Prompt user to enter another refresh token
}
```

<br>

## Installation
### Gradle:
* Add the following into your `build.gradle` file:
```gradle
repositories {
	maven { url 'https://jitpack.io' }
}
```
```gradle
dependencies {
	implementation 'com.github.mateimarica:j-questrade:1.3'
}
```
<br>

### Maven:
* Add the following into your `pom.xml` file:
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```
```xml
<dependency>
	<groupId>com.github.mateimarica</groupId>
	<artifactId>j-questrade</artifactId>
	<version>1.3</version>
</dependency>
```

<br>

## Documentation
* [Javadoc](https://javadoc.jitpack.io/com/github/mateimarica/j-questrade/latest/javadoc/)
* [GitHub Wiki](https://github.com/mateimarica/j-questrade/wiki)

<br>

## Disclaimers

* This library is not affiliated, maintained, authorized, or endorsed by Questrade.

* This library has not been heavily tested. Use at your own risk.
