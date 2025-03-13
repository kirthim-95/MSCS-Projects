import org.junit.Test;
import rpg.RpgCharacterBuilder;
import rpg.RpgCharacterImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RpgCharacterBuilderTest {

    @Test
    public void testBuildCharacterWithValidAttributes() {
        RpgCharacterImpl character = new RpgCharacterBuilder()
                .setBaseAttack(10)
                .setBaseDefense(15)
                .build();

        assertEquals(10, character.getAttack());
        assertEquals(15, character.getDefense());
    }

    @Test
    public void testBuildCharacterWithInvalidAttributes() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RpgCharacterBuilder()
                    .setBaseAttack(-5) // Invalid base attack
                    .setBaseDefense(10)
                    .build();
        });
        assertEquals("Attack and defense must be non-negative!", exception.getMessage());
    }

    @Test
    public void testDefaultBuilderValues() {
        RpgCharacterImpl character = new RpgCharacterBuilder().build();

        // Assuming default attack and defense are set to 0
        assertEquals(0, character.getAttack());
        assertEquals(0, character.getDefense());
    }
}