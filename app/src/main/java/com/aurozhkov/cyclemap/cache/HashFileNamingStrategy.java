package com.aurozhkov.cyclemap.cache;

/**
 * Created by anton on 01.12.14.
 */
public class HashFileNamingStrategy implements FileNamingStrategy {
    @Override
    public String getNameForUrl(String url) {
        return String.valueOf(url.hashCode());
    }
}
