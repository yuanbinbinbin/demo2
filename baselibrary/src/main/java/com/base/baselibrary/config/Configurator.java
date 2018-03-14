package com.base.baselibrary.config;

import android.os.Handler;
import java.util.HashMap;

/**
 * Created by yb on 2017/3/29
 */

public final class Configurator {

    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();

    private Configurator() {
        CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getConfigs() {
        return CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void build() {
        CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    public final Configurator withLogSwitch(boolean isOpen) {
        CONFIGS.put(ConfigKeys.LOG_SWITCH, isOpen);
        return this;
    }

    public final Configurator withHost(String host) {
        CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withCachePublicImage(String path) {
        CONFIGS.put(ConfigKeys.CACHE_PUBLIC_IMAGE, path);
        return this;
    }

    public final Configurator withCachePrivateImage(String path) {
        CONFIGS.put(ConfigKeys.CACHE_PRIVATE_IMAGE, path);
        return this;
    }

    public final Configurator withCachePublicTemp(String path) {
        CONFIGS.put(ConfigKeys.CACHE_PUBLIC_TEMP, path);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) CONFIGS.get(key);
    }
}
