package parks.inventorymanager.model;

/** Defines a part. */
public abstract class Part {

    private int id;
    private String name;
    private String location;

    Part(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    /** Gets the part id.
     * @return The part id. */
    public int getId() {
        return id;
    }

    /** Sets the part id.
     * @param id The part id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Gets the part name.
     * @return The part name. */
    public String getName() {
        return name;
    }

    /** Sets the part name.
     * @param name The part name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Get the part location.
     * @return The physical location of the part. */
    public String getLocation() {
        return location;
    }

    /** Set the part location.
     * @param location The physical location of the part. */
    public void setLocation(String location) {
        this.location = location;
    }
}