package com.scripted.utils;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/main/resources/Web/Environments/dev.properties"})
public interface WebEnvironment extends Config {

    @Key("fb.url")
    String getFbUrl();

    @Key("sauce.url")
    String getSauceUrl();

    @Key("ustId")
    String getUstId();

    @Key(("input_username"))
    String getUsername();

    @Key("input_password")
    String getPassword();

}
