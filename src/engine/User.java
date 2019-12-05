package engine;

public class User implements UserChoice {
    String name;

    public User(int id) {
        this.name = "player" + id;
    }

    public User(String name) {
        this.name = name;
    }

    @Override
    public String textValue() {
        return null;
    }
}
