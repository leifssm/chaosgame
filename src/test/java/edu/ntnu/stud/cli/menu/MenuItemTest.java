package edu.ntnu.stud.cli.menu;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MenuItemTest {

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {

    @Test
    @DisplayName("Constructor doesn't throw when given valid parameters")
    void constructorDoesntThrowWithValidParams() {
      // Arrange
      String name = "Test";
      Runnable action = () -> {
      };
      // Act
      // Assert
      assertDoesNotThrow(
          () -> new MenuItem(name, action),
          "Expected no exception"
      );
    }

    @Test
    @DisplayName("getName() returns the name of the item")
    void getNameReturnsItemName() {
      // Arrange
      final String name = "Test";
      Runnable action = () -> {
      };
      // Act
      MenuItem item = new MenuItem(name, action);
      // Assert
      assertEquals(name, item.getName());
    }

    @Test
    @DisplayName("toString() returns a string representation of the item")
    void toStringReturnsStringRepresentation() {
      // Arrange
      final String name = "Test";
      Runnable action = () -> {
      };
      // Act
      MenuItem item = new MenuItem(name, action);
      // Assert
      assertEquals(
          "MenuItem [name = 'Test']",
          item.toString()
      );
    }

    @Test
    @DisplayName("run() runs the associated action")
    void runRunsAction() {
      // Arrange
      AtomicBoolean ranFunction = new AtomicBoolean(false);

      String name = "Test";
      Runnable action = () -> ranFunction.set(true);

      MenuItem item = new MenuItem(name, action);
      // Act
      item.run();
      // Assert
      assertTrue(ranFunction.get());
    }
  }
}