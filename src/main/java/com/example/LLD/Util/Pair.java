package com.example.LLD.Util;

public class Pair<KeyType,ValueType> {
    KeyType first;
    ValueType second;

    public Pair(KeyType first, ValueType second) {
        this.first = first;
        this.second = second;
    }

    public KeyType getFirst() {
        return first;
    }

    public void setFirst(KeyType first) {
        this.first = first;
    }

    public ValueType getSecond() {
        return second;
    }

    public void setSecond(ValueType second) {
        this.second = second;
    }
}
