package parks.inventorymanager.model;

/** Defines a relationship between an engine and a part. */
public class EnginePart {

    private int enginePartId;
    private int engineId;
    private int partId;

    /** Constructs a relationship between an engine and a part.
     * @param enginePartId The id of the engine/part relationship.
     * @param engineId The engine id.
     * @param partId The part id.*/
    public EnginePart (int enginePartId, int engineId, int partId) {
        this.enginePartId = enginePartId;
        this.engineId = engineId;
        this.partId = partId;
    }

    /** Gets the id of the engine/part relationship.
     * @return The id of the engine/part relationship. */
    public int getEnginePartId() {
        return enginePartId;
    }

    /** Sets the id of the engine/part relationship.
     * @param enginePartId  The id of the engine/part relationship. */
    public void setEnginePartId(int enginePartId) {
        this.enginePartId = enginePartId;
    }

    /** Gets the engine id.
     * @return The engine id. */
    public int getEngineId() {
        return engineId;
    }

    /** Sets the engine id.
     * @param engineId The engine id. */
    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    /** Gets the part id.
     * @return The part id. */
    public int getPartId() {
        return partId;
    }

    /** Gets the part id.
     * @param partId The part id. */
    public void setPartId(int partId) {
        this.partId = partId;
    }
}
