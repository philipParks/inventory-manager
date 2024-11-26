package parks.inventorymanager.model;

/** Defines an in house part made at a workstation. */
public class Inhouse extends Part {

    private String workstation;

    /** Constructs an in house part.
     * @param id The unique number associated with a part.
     * @param name The name of the part.
     * @param location The physical location of the part.
     * @param workstation The ID assigned to the workstation that produced the part.*/
    public Inhouse(int id, String name, String location, String workstation) {
        super(id, name, location);
        this.workstation = workstation;
    }

    /** Gets the workstation id.
     * @return The workstation id. */
    public String getWorkstation() {
        return workstation;
    }

    /** Sets the workstation id.
     * @param workstation The workstation nomenclature. */
    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }
}
