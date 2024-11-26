package parks.inventorymanager.model;

/** Defines a workstation data access object. */
public class Workstation {

    private int id;
    private String workstation;

    @Override
    public String toString() {
        return workstation;
    }

    /** Constructs a workstation.
     * @param id The id of the workstation.
     * @param workstation The name of the workstation. */
    public Workstation(int id, String workstation) {
        this.id = id;
        this.workstation = workstation;
    }

    /** Get the workstation id.
     * @return The workstation id. */
    public int getId() {
        return id;
    }

    /** Set the workstation id.
     * @param id The workstation id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Get the workstation name.
     * @return The workstation name. */
    public String getWorkstation() {
        return workstation;
    }

    /** Set the workstation name.
     * @param workstation The workstation name. */
    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }
}
