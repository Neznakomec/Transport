package com.sdimdev.nnhackaton.di;

import com.sdimdev.nnhackaton.HackatonApplication;
import com.sdimdev.nnhackaton.di.app.AppComponent;
import com.sdimdev.nnhackaton.di.app.AppModule;
import com.sdimdev.nnhackaton.di.app.DaggerAppComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by dzmitry (sdimdev) on 20.7.19
 */
public class DIManager {
    private static DIManager dIManager;
    private Map<Class, Object> weakComponents = new WeakHashMap<>();
    private Map<Class, Object> strongComponents = new HashMap<>();

    public DIManager(AppComponent appComponent) {
        putStrong(AppComponent.class, appComponent);
    }

    private static void init(HackatonApplication application) {
        dIManager = new DIManager(DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build());
    }

    public static DIManager get() {
        if (dIManager == null)
            DIManager.init(HackatonApplication.app);
        return dIManager;
    }

    public AppComponent getAppComponent() {
        return getStrongComponent(AppComponent.class);
    }

    public <T> T getComponent(Class<T> clazz) {
        return weakComponents.containsKey(clazz) ? (T) weakComponents.get(clazz) : null;
    }

    public void put(Class clazz, Object component) {
        weakComponents.put(clazz, component);
    }

    public <T> T getStrongComponent(Class<T> clazz) {
        return strongComponents.containsKey(clazz) ? (T) strongComponents.get(clazz) : null;
    }

    public void putStrong(Class clazz, Object component) {
        strongComponents.put(clazz, component);
    }

    public void clear(Class clazz) {
        weakComponents.remove(clazz);
    }
}
