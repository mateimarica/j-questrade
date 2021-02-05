# jQuestrade
[![](https://jitpack.io/v/mateimarica/j-questrade.svg)](https://jitpack.io/#mateimarica/j-questrade)

jQuestrade is a [Questrade API](https://www.questrade.com/api) wrapper written in Java. It aims to simplify access to Questrade's API..

<br>

## Features
* Automatic access token renewal
* Authorization relaying

<br>

## Example Usage

```java
Questrade q = new Questrade(refreshToken);

try {
	q.activate(); // activate() must be called to make API requests
	
	Account[] accs     = q.getAccounts();  // Get all accounts
	ZonedDateTime time = q.getTime();      // Get server time
	
	ZonedDateTime startTime = ZonedDateTime.now().minusMonths(1);
	ZonedDateTime endTime   = ZonedDateTime.now();
	
	// Get all orders for the first account in the last month
	Order[] orders = q.getOrders(accs[0].getNumber(), startTime, endTime);
	
} catch (RefreshTokenException e) { 
	e.printStackTrace();
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
	implementation 'com.github.mateimarica:j-questrade:v1.0'
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
	<version>v1.0</version>
</dependency>
```

<br>

## Documentation
* [**Javadoc**](https://javadoc.jitpack.io/com/github/mateimarica/j-questrade/latest/javadoc/)

<br>

## Disclaimer

This library is not affiliated, maintained, authorized, or endorsed by Questrade. Use at your own risk.
