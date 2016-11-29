package pl.ppkwu.lab.api;

import java.util.Objects;

public class EncryptionKey {

    private String value;

    public EncryptionKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptionKey that = (EncryptionKey) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "EncryptionKey{" +
            "value='" + value + '\'' +
            '}';
    }
}
