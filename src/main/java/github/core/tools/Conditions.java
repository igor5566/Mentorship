package github.core.tools;

public interface Conditions<T> {
    boolean condition(T cond, String jsonKey);
}
