import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class SILab2Test {

    @Test
    public void testEveryStatement() {
        // 1. Null list
        RuntimeException ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, "1234567890123456"));
        assertTrue(ex.getMessage().contains("allItems list can't be null!"));

        // 2. Item with null name
        List<Item> items1 = List.of(new Item(null, 1, 100, 0));
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items1, "1234567890123456"));
        assertTrue(ex.getMessage().contains("Invalid item!"));

        // 3. Valid item, no discount, normal values
        List<Item> items2 = List.of(new Item("Apple", 2, 100, 0));
        double total = SILab2.checkCart(items2, "1234567890123456");
        assertEquals(200, total);  // 100 * 2 = 200

        // 4. Item with discount
        List<Item> items3 = List.of(new Item("Banana", 2, 100, 0.1));
        total = SILab2.checkCart(items3, "1234567890123456");
        assertEquals(2 * 100 * (1 - 0.1) - 30, total);  // Discount + penalty

        // 5. Invalid card (wrong length)
        List<Item> items4 = List.of(new Item("Orange", 1, 100, 0));
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items4, "1234"));
        assertTrue(ex.getMessage().contains("Invalid card number!"));

        // 6. Card with invalid character
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items4, "1234abcd1234abcd"));
        assertTrue(ex.getMessage().contains("Invalid character in card number!"));
    }

    @Test
    public void testMultipleCondition() {
        // All combinations for: price > 300 || discount > 0 || quantity > 10

        // F F F
        Item i1 = new Item("Item1", 1, 100, 0);
        assertEquals(100, SILab2.checkCart(List.of(i1), "1234567890123456"));

        // F F T
        Item i2 = new Item("Item2", 11, 100, 0);
        assertEquals(11 * 100 - 30, SILab2.checkCart(List.of(i2), "1234567890123456"));

        // F T F
        Item i3 = new Item("Item3", 1, 100, 0.2);
        assertEquals(1 * 100 * (1 - 0.2) - 30, SILab2.checkCart(List.of(i3), "1234567890123456"));

        // F T T
        Item i4 = new Item("Item4", 11, 100, 0.2);
        assertEquals(11 * 100 * (1 - 0.2) - 30, SILab2.checkCart(List.of(i4), "1234567890123456"));

        // T F F
        Item i5 = new Item("Item5", 1, 400, 0);
        assertEquals(1 * 400 - 30, SILab2.checkCart(List.of(i5), "1234567890123456"));

        // T F T
        Item i6 = new Item("Item6", 11, 400, 0);
        assertEquals(11 * 400 - 30, SILab2.checkCart(List.of(i6), "1234567890123456"));

        // T T F
        Item i7 = new Item("Item7", 1, 400, 0.2);
        assertEquals(1 * 400 * (1 - 0.2) - 30, SILab2.checkCart(List.of(i7), "1234567890123456"));

        // T T T
        Item i8 = new Item("Item8", 11, 400, 0.2);
        assertEquals(11 * 400 * (1 - 0.2) - 30, SILab2.checkCart(List.of(i8), "1234567890123456"));
    }
}
