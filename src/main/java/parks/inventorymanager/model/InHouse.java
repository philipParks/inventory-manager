package parks.inventorymanager.model;
/** This class creates an instance of Part made on sight. */
public class InHouse extends Part {

    private int machineId;

    /** This method is a constructor for InHouse.
     * @param id The unique number associated with a Part.
     * @param name The name of the Part.
     * @param price The monetary value assigned to the Part.
     * @param stock The current quantity of the Part.
     * @param min The minimum allowable inventory to retain.
     * @param max The maximum allowable inventory to retain.
     * @param machineId The ID assigned to the machine that produced the Part.
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Machine ID accessor.
     * @return ID of the machine used to manufacture the Part.
     * */
    public int getMachineId() {
        return machineId;
    }

    /** Machine ID mutator.
     * @param machineId The ID of the machine used to manufacture the Part.
     * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
