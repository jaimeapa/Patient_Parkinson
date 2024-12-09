package Pojos;
/**
 * This class represents a symptom associated with a patient.
 * It includes basic information such as the symptom's ID and name.
 */
public class Symptoms {
    private int id;
    private String name;

    public Symptoms (String name){
        this.name = name;
    }

    /**
     * Gets the unique identifier of the symptom.
     * @return the ID of the symptom.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the symptom.
     * @param id the ID to set for the symptom.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the symptom.
     * @return the name of the symptom.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the symptom.
     * @param name the name to set for the symptom.
     */
    public void setNombre(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the symptom.
     * @return the name of the symptom.
     */

    @Override
    public String toString() {
        return  name ;
    }
}
