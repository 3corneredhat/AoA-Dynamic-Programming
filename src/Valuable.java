/**
 *The Valuable class creates an object with the data from the input file.
 * @author xex967
 */
public class Valuable {
    public String description;
    public int price;
    public int value;

    public Valuable(String[] data) {
        description = data[0];
        price = Integer.parseInt(data[1]);
        value = Integer.parseInt(data[2]);
    }
    /**
     * Drescription getter.
     * @return The item description.
     */
    public String getDescription() {
        return description; 
    }
    /**
     * Price getter.
     * @return The item price.
     */
    public int getPrice(){
        return price;
    }
    /**
     * Value getter.
     * @return The item value.
     */
    public int getValue(){
        return value;
    }
    /**
     * Creates a string consisting of all the fields.
     * @return The item in a string.
     */
    public String toString(){
        String valuable = String.format("%s, %d, %d", description, price, value);
        return valuable;
    }
}