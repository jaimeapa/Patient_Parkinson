package Pojos;

public class Symptons {
    private int id;
    private String name;

    // Constructor
    public Symptons(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter para id
    public int getId() {
        return id;
    }

    // Setter para id
    public void setId(int id) {
        this.id = id;
    }

    // Getter para nombre
    public String getName() {
        return name;
    }

    // Setter para nombre
    public void setNombre(String name) {
        this.name = name;
    }

    // Método toString
    @Override
    public String toString() {
        return "Sintomas{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
