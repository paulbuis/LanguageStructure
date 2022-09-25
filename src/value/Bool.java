package value;


public record Bool(boolean bool) implements Value {

    static public Bool TRUE = new Bool(true);
    static public Bool FALSE = new Bool(false);

    static public Bool makeBool(boolean b) {
        if (b) {
            return TRUE;
        }
        return FALSE;
    }
}
