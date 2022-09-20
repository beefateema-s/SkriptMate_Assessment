package com.scripted.utils;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/main/resources/WebServices/Environments/${env}.properties"})
public interface WebServiceEnvironment extends Config {

    String url();

    String username();

    String password();

}
