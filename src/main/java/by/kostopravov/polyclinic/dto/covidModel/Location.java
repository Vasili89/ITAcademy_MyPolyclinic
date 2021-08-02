package by.kostopravov.polyclinic.dto.covidModel;

public class Location {

    private String state;
    private String country;
    private int lastTotalCases;
    private int delta;

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLastTotalCases() {
        return lastTotalCases;
    }

    public void setLastTotalCases(int lastTotalCases) {
        this.lastTotalCases = lastTotalCases;
    }

    @Override
    public String toString() {
        return "Location{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", lastTotalCases=" + lastTotalCases +
                ", delta=" + delta +
                '}';
    }
}
