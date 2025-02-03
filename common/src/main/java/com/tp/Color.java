<<<<<<< Updated upstream
package com.tp;

/**
 * Enum representing different player colors.
 * Colors are numbered as shown in /colors.png.
 */
public enum Color {
    RED,    /** Represents the red color. */
    YELLOW, /** Represents the yellow color. */
    ORANGE, /** Represents the orange color. */
    GREEN,  /** Represents the green color. */
    BLUE,   /** Represents the blue color. */
    PURPLE; /** Represents the purple color. */

    /**
     * Converts the enum color to a corresponding JavaFX color for UI rendering.
     * 
     * @return The JavaFX color corresponding to the enum value.
     */
    public javafx.scene.paint.Color toPaintColor() {
        return switch (this) {
            case RED -> javafx.scene.paint.Color.RED;
            case YELLOW -> javafx.scene.paint.Color.YELLOW;
            case ORANGE -> javafx.scene.paint.Color.ORANGE;
            case GREEN -> javafx.scene.paint.Color.GREEN;
            case BLUE -> javafx.scene.paint.Color.LIGHTBLUE;
            case PURPLE -> javafx.scene.paint.Color.PURPLE;
            default -> javafx.scene.paint.Color.LIGHTGRAY;
        };
    }
}
=======
package com.tp;

/**
 * Enum representing different player colors.
 * Colors are numbered as shown in /colors.png.
 */
public enum Color {
    RED,
    /** Represents the red color. */
    YELLOW,
    /** Represents the yellow color. */
    ORANGE,
    /** Represents the orange color. */
    GREEN,
    /** Represents the green color. */
    BLUE,
    /** Represents the blue color. */
    PURPLE;

    /** Represents the purple color. */

    /**
     * Converts the enum color to a corresponding JavaFX color for UI rendering.
     * 
     * @return The JavaFX color corresponding to the enum value.
     */
    public javafx.scene.paint.Color toPaintColor() {
        return switch (this) {
            case RED -> javafx.scene.paint.Color.RED;
            case YELLOW -> javafx.scene.paint.Color.YELLOW;
            case ORANGE -> javafx.scene.paint.Color.ORANGE;
            case GREEN -> javafx.scene.paint.Color.GREEN;
            case BLUE -> javafx.scene.paint.Color.LIGHTBLUE;
            case PURPLE -> javafx.scene.paint.Color.PURPLE;
            default -> javafx.scene.paint.Color.LIGHTGRAY;
        };
    }
}
>>>>>>> Stashed changes
