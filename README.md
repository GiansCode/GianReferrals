# Referrals

### Dependency
```groovy
repositories {
    maven {
        url = "https://repo.piggypiglet.me/repository/maven-releases/"
    }
}

dependencies {
    implementation "me.piggypiglet:referrals-PLATFORM:1.0.0"
}
```
> Make sure to replace `PLATFORM` with either `bukkit` or `bungee` depending
on what platform you're developing for.

### Usage
NOTE: You will need access to an instance of your main class for initialization
(JavaPlugin or Plugin instance).

First step is to populate a config instance with all your details. It's a bit
long so I won't explain it here, but all the methods are well documented,
so it should be relatively easy to follow. Here's an example if you get stuck:
```java
final Config config = Config.builder()
                .apiKey(apiKey)
                .ip("127.0.0.1")
                .domain("p1g.pw")
                .zone("p1g.pw")
                .mysql(MysqlDetails.builder()
                        .host("127.0.0.1")
                        .username("referrals")
                        .password("test1234")
                        .database("referrals")
                        .tablePrefix("bungee_")
                        .poolSize(10)
                        .port(3306)
                        .build())
                .expiry(ExpiryOptions.builder()
                        .expire(true)
                        .policy(ExpirationPolicy.ACCESSED)
                        .expiryCheckPeriodMinutes(1)
                        .expiryMinutes(TimeUnit.DAYS.toMinutes(30))
                        .build())
                .build();
```
It's extremely important that you relocate the referrals API and all of its
dependencies. The following lists are packages which need to be relocated
under your own package (e.g. me.piggypiglet.sample.dependencies). Common
needs to be relocated on both bukkit & bungee, then the platform lists
contain dependencies which only need to be relocated on specific platforms.<br/>
**Common:**
- co.aikar.idb
- com.google.inject
- com.mysql
- com.zaxxer.hikari
- eu.roboflax.cloudflare
- io.joshworks.restclient
- javax.inject
- org.aopalliance
- org.apache.commons.codec 
- org.apache.commons.logging
- org.apache.http
- org.json
- org.slf4j
- google
- com.google.protobuf
> Note If you use the referrals api in multiple plugins on the same server,
> you may also need to relocate it.

**Bukkit:**
- org.apache.commons.lang3

See the sample project(s) for an example.

Methods are named relatively concisely and specific to their function,
but there may be a degree of ambiguity, especially with the cloudflare
methods. Please check the javadoc of each method before supplying a value
(ctrl + q in intellij).

You can then pass the config instance into ReferralBootstrap#initialize,
like so:
```java
final Referrals api = ReferralsBootstrap.initialize(config, this);
```
> Replace `this` with the instance of your main class (extending JavaPlugin
> or Plugin)

### API
The Referrals instance you got above is what you need to keep. You can pass
this around via dependency injection, or make a dreaded singleton instance
yourself. Additionally, on bukkit the instance is passed to the service manager,
and the papi expansion need access. This was merely added for PAPI though,
it may change in the future, so I would not recommend relying on it.

The Referrals class is well documented with javadocs, and the method names are
relatively self-descriptive, there isn't much to cover here.

Oh there's also events though, 5 of them in fact.
- RecordCreateEvent
- RecordDeleteEvent
- ReferralAddendumEvent
- ReferralRemovalEvent
- ReferralSettingEvent

You can listen to these via your platform's event API (e.g. Listener & @EventHandler)

### PAPI
Only 2 placeholders, %referrals_joins% & %referrals_top%. Joins returns the
number of joins for the user parsing the placeholder, and top returns the top
users in the format `uuid - joins\n`.

To build the PAPI expansion, run
`./gradlew shadowJar`, the jar will be in `sample/servers/bukkit/plugins/PlaceholderAPI/expansions`