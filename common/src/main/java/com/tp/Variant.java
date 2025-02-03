<<<<<<< Updated upstream
package com.tp;

public enum Variant {
    CLASSIC,
    ONEVONE;

    public static Variant fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        switch (value.trim()) {
            case "1":
                return CLASSIC;
            case "2":
                return ONEVONE;
            default:
                for (Variant variant : Variant.values()) {
                    if (variant.name().equalsIgnoreCase(value)) {
                        return variant;
                    }
                }
                throw new IllegalArgumentException("No enum constant " + Variant.class.getCanonicalName() + " for input: " + value);
        }
    }
}
=======
package com.tp;

public enum Variant {
    CLASSIC,
    ONEVONE;

    public static Variant fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        switch (value.trim()) {
            case "1":
                return CLASSIC;
            case "2":
                return ONEVONE;
            default:
                for (Variant variant : Variant.values()) {
                    if (variant.name().equalsIgnoreCase(value)) {
                        return variant;
                    }
                }
                throw new IllegalArgumentException(
                        "No enum constant " + Variant.class.getCanonicalName() + " for input: " + value);
        }
    }
}
>>>>>>> Stashed changes
