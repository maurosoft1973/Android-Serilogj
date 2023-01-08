# Android Serilogj Library 

[![](https://jitpack.io/v/maurosoft1973/android-serilogj.svg)](https://jitpack.io/#maurosoft1973/android-serilogj)

_serilogj_ is a structured logger that is an almost 1-on-1 code conversion of [Serilog for .NET](https://serilog.net). 
Not everything has been converted, but the a lot of functionality is included in this conversion. 
Using this in combination with [Seq](https://datalust.co/seq) will make searching through log files a lot easier.

## Usage

Set up the logger using the desired settings, as such:

```java
Log.setLogger(new LoggerConfiguration()
	.writeTo(coloredConsole())
	.writeTo(rollingFile("test-{Date}.log"), LogEventLevel.Information)
	.writeTo(seq("http://localhost:5341/"))
	.setMinimumLevel(LogEventLevel.Verbose)
	.createLogger());
```

After this you can log using the following example code:

```java
User user = new User();
user.userId = 1234;
user.setUserName("blaat");

Log.verbose("Hello {world} {@user}", "wereld", user);
Log.debug("Hello {world} {@user}", "wereld", user);
Log.information("Hello {world} {@user}", "wereld", user);
Log.warning("Hello {world} {@user}", "wereld", user);
Log.error("Hello {world} {@user}", "wereld", user);
Log.fatal("Hello {world} {@user}", "wereld", user);
```

## Known issues

* Formatting is only partially supported (only for dates)
* Only a limited amount of sinks have been converted (colored console, rolling file and Seq)


