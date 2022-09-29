package token;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public record Location(String sourceName, int lineNumber, int charNumber, int codePointNumber, int clusterNumber) {

    private static final Pattern clusterPattern = Pattern.compile("\\X");

    public Location increment(final String lexeme) {
        final int charCount = lexeme.length();
        final int codePointCount =  (int)lexeme.codePoints().count();
        final Matcher matcher = clusterPattern.matcher(lexeme);
        final int clusterCount = (int)matcher.results().count();
        return new Location(sourceName, lineNumber, charNumber+charCount,
                codePointNumber+codePointCount, clusterNumber+clusterCount);
    }

    public Location newLine() {
        return new Location(sourceName, lineNumber+1, 0, 0, 0);
    }
}
