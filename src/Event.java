import java.util.Date;

public class Event {
    int eventId;
    int speakerId;
    int addressId;
    String location;
    Date eDate;
    double cost;

    public Event(int eventId, int speakerId, int addressId, String location, Date eDate, double cost) {
        this.eventId = eventId;
        this.speakerId = speakerId;
        this.addressId = addressId;
        this.location = location;
        this.eDate = eDate;
        this.cost = cost;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date geteDate() {
        return eDate;
    }

    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", speakerId=" + speakerId +
                ", addressId=" + addressId +
                ", location='" + location + '\'' +
                ", eDate=" + eDate +
                ", cost=" + cost +
                '}';
    }
}

