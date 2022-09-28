package token;

public record Location(String sourceName, int lineNumber, int charNumber, int codePointNumber, int clusterNumber) {
    public Location increment(int charCount, int codePointCount, int clusterCount) {
        return new Location(sourceName, lineNumber, charNumber+charCount,
                codePointNumber+codePointCount, clusterNumber+clusterCount);
    }

    public Location newLine() {
        return new Location(sourceName, lineNumber+1, 0, 0, 0);
    }
}
