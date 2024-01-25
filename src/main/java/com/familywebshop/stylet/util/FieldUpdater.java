package com.familywebshop.stylet.util;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class FieldUpdater {
    public <T> void updateFieldIfNotNull(T newValue, Consumer<T> updater) {
        if (newValue != null) {
            updater.accept(newValue);
        }
    }
}
