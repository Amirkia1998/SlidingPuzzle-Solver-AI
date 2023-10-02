public class Stats {

    private long totalDuration;
    private long totalOpenStates;
    private long totalCloseStates;
    private long n;
    private long pathLength;

    public Stats() {
        this.setN(0);
        this.setTotalDuration(0);
        this.setTotalCloseStates(0);
        this.setTotalOpenStates(0);
        this.setPathLength(0);
    }

    public long durationAVG() {
        return (totalDuration/n);
    }

    public long closeStatesAVG() {
        return (totalCloseStates/n);
    }

    public long openStatesAVG() {
        return (totalOpenStates/n);
    }

    public long pathLengthAVG() {
        return (pathLength/n);
    }

    //=================================================== GETTERS SETTERS
    public long getPathLength() {
        return pathLength;
    }
    public long getN() {
        return n;
    }
    public long getTotalCloseStates() {
        return totalCloseStates;
    }
    public long getTotalDuration() {
        return totalDuration;
    }
    public long getTotalOpenStates() {
        return totalOpenStates;
    }
    public void setPathLength(long pathLength) {
        this.pathLength = pathLength;
    }
    public void setN(long n) {
        this.n = n;
    }
    public void setTotalCloseStates(long totalCloseStates) {
        this.totalCloseStates = totalCloseStates;
    }
    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }
    public void setTotalOpenStates(long totalOpenStates) {
        this.totalOpenStates = totalOpenStates;
    }
}
