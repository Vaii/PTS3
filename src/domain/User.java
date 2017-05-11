package domain;

/**
 * Created by bob on 10-5-17.
 */
public class User {
    private String name;
    private String className;
    private UserType userType;
    private int attribute;
    public Color color;

    public User(String name) {
        this.name = name;
    }

    public User(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String toString() {
        return this.name;
    }
}
