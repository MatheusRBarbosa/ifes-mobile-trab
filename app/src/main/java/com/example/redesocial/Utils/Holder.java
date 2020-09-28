package com.example.redesocial.Utils;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Holder <T>{
    private T value = null;
    private List<T> list = new ArrayList<>();

    public Holder(){}

    public Holder (T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void setList (List<T> list) {
        this.list = list;
    }

    public void addInList(T value) {
        this.list.add(value);
    }

    public List<T> getList() {
        return this.list;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
