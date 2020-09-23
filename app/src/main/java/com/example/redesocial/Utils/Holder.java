package com.example.redesocial.Utils;

import androidx.annotation.NonNull;

public class Holder <T>{
    private T value = null;

    public Holder(){
    }

    public Holder (T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
