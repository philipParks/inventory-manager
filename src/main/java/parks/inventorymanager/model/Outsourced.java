package parks.inventorymanager.model;

/** Defines an outsourced part. */
public class Outsourced extends Part {

    private String distributor;

    /** Constructs an outsourced part.
     * @param id The unique number associated with a part.
     * @param name The name of the part.
     * @param location The physical location of the part.
     * @param distributor The name of the company that the part was procured from.
     * */
    public Outsourced(int id, String name, String location, String distributor) {
        super(id, name, location);
        this.distributor = distributor;
    }

    /** Gets the part distributor.
     * @return The part distributor. */
    public String getDistributor() {
        return distributor;
    }

    /** Sets the part distributor.
     * @param distributor The part distributor. */
    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

}
