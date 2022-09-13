package value;

import org.jetbrains.annotations.NotNull;

public record Bool(boolean bool) implements Value {

    static public @NotNull Bool TRUE = new Bool(true);
    static public @NotNull Bool FALSE = new Bool(false);

    static public Bool makeBool(boolean b) {
        if (b) {
            return TRUE;
        }
        return FALSE;
    }
}
