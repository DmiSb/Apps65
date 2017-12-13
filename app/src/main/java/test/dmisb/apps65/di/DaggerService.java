package test.dmisb.apps65.di;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DaggerService {

    private final static Map<String, Object> componentMap = new HashMap<>();

    public static void registerComponent(String keyName, Object daggerComponent) {
        if (getComponent(keyName) == null)
            componentMap.put(keyName, daggerComponent);
    }

    @Nullable
    public static <T> T getComponent(String keyName) {
        Object component = componentMap.get(keyName);
        //noinspection unchecked
        return (T) component;
    }

    @SuppressWarnings("uncheked")
    public static void unregisterComponent(String keyName) {
        if (componentMap.containsKey(keyName)) {
            componentMap.remove(keyName);
        }
    }
}
