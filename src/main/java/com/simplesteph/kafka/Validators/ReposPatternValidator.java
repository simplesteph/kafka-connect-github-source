package com.simplesteph.kafka.Validators;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;

public class ReposPatternValidator implements ConfigDef.Validator {
    @Override
    public void ensureValid(String name, Object value) {

        String []repos=((String) value).split(",");

        for(String repo:repos){
            //[A-Za-z0-9_.-] are the characters that are allowed for naming in github and [a-zA-Z0-9\._-] in kafka topics
            if(!repo.matches("[A-Za-z0-9_.-]{1,}/[A-Za-z0-9_.-]{1,}:[a-zA-Z0-9\\._-]{1,}"))
                throw new ConfigException(name,value,"'owner1/repo1:topic1,owner2/repo2:topic2' pattern to be followed for mentioning repositories");
        }
    }
}