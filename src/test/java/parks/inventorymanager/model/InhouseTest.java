package parks.inventorymanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InhouseTest {

    @Test
    void inhousePartIdMutatorAccessorTest() {
        Inhouse part = new Inhouse(1, "part", "location", "workstation");
        part.setId(2);
        assertEquals(2, part.getId());
    }

    @Test
    void inhousePartNameMutatorAccessorTest() {
        Inhouse part = new Inhouse(2, "part", "location", "workstation");
        part.setName("bolt");
        assertEquals("bolt", part.getName());
    }

    @Test
    void inhousePartLocationMutatorAccessorTest() {
        Inhouse part = new Inhouse(2, "bolt", "location", "workstation");
        part.setLocation("bin 1");
        assertEquals("bin 1", part.getLocation());
    }

    @Test
    void inhousePartWorkstationMutatorAccessorTest() {
        Inhouse part = new Inhouse(2, "bolt", "bin 1", "workstation");
        part.setWorkstation("workstation 1");
        assertEquals("workstation 1", part.getWorkstation());
    }

}