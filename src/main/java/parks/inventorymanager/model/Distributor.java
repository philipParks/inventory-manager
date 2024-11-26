package parks.inventorymanager.model;

/** Defines a distributor. */
public class Distributor {

    private int id;
    private String distributor;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String contact;
    private String phone;
    private String email;

    @Override
    public String toString() {
        return distributor;
    }

    /** Constructs a distributor.
     * @param id The id of the distributor.
     * @param distributor The name of the distributor.
     * @param street The street name and building number of the distributor.
     * @param city The city of the distributor.
     * @param state The state of the distributor.
     * @param zipcode The zipcode of the distributor.
     * @param contact The name of the contact at the distributor.
     * @param phone The phone number of the contact at the distributor.
     * @param email The email address of the contact at the distributor.*/
    public Distributor(int id, String distributor, String street, String city, String state,
                       String zipcode, String contact, String phone, String email) {
        this.id = id;
        this.distributor = distributor;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
    }

    /** Gets the distributor id.
     * @return The distributor id.*/
    public int getId() {
        return id;
    }

    /** Sets the distributor id.
     * @param id The distributor id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Gets the distributor name.
     * @return The distributor name.*/
    public String getDistributor() {
        return distributor;
    }

    /** Sets the distributor name.
     * @param distributor The distributor name. */
    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    /** Gets the distributor street name and building number.
     * @return The distributor street name and building number.*/
    public String getStreet() {
        return street;
    }

    /** Sets the distributor street name and building number.
     * @param street The distributor street name and building number. */
    public void setStreet(String street) {
        this.street = street;
    }

    /** Gets the distributor city.
     * @return The distributor city.*/
    public String getCity() {
        return city;
    }

    /** Sets the distributor city.
     * @param city The distributor city. */
    public void setCity(String city) {
        this.city = city;
    }

    /** Gets the distributor state.
     * @return The distributor state.*/
    public String getState() {
        return state;
    }

    /** Sets the distributor state.
     * @param state The distributor state. */
    public void setState(String state) {
        this.state = state;
    }

    /** Gets the distributor zipcode.
     * @return The distributor zipcode.*/
    public String getZipcode() {
        return zipcode;
    }

    /** Sets the distributor zipcode.
     * @param zipcode The distributor zipcode. */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /** Gets the name of the contact at the distributor.
     * @return The name of the contact at the distributor.*/
    public String getContact() {
        return contact;
    }

    /** Sets the name of the contact at the distributor.
     * @param contact The name of the contact at the distributor. */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /** Gets the phone number of the contact at the distributor.
     * @return The phone number of the contact at the distributor.*/
    public String getPhone() {
        return phone;
    }

    /** Sets the phone number of the contact at the distributor.
     * @param phone The phone number of the contact at the distributor. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Gets the email address of the contact at the distributor.
     * @return The email address of the contact at the distributor.*/
    public String getEmail() {
        return email;
    }

    /** Sets the email address of the contact at the distributor.
     * @param email The email address of the contact at the distributor. */
    public void setEmail(String email) {
        this.email = email;
    }

}
