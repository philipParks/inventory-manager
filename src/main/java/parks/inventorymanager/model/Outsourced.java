package parks.inventorymanager.model;
/** This class creates an instance of Part purchased from a third party. */
public class Outsourced extends Part {
    private String companyName;
    /** This method is a constructor for Outsourced.
     * @param id The unique number associated with a Part.
     * @param name The name of the Part.
     * @param price The monetary value assigned to the Part.
     * @param stock The current quantity of the Part.
     * @param min The minimum allowable inventory to retain.
     * @param max The maximum allowable inventory to retain.
     * @param companyName The name of the company that the Part was procured from.
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Company Name accessor.
     * @return The name of the company that the Part was procured from.
     * */
    public String getCompanyName() {
        return companyName;
    }

    /** Company Name mutator.
     * @param companyName The name of the company that the Part was procured from.
     * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
