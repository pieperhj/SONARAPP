public class Speaker {
    int speakerId;
    int addressId;
    String sFName;
    String sLName;
    String title;
    String industry;
    double cost;

    public Speaker(int speakerId, int addressId, String sFName, String sLName, String title, String industry, double cost) {
        this.speakerId = speakerId;
        this.addressId = addressId;
        this.sFName = sFName;
        this.sLName = sLName;
        this.title = title;
        this.industry = industry;
        this.cost = cost;
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

    public String getsFName() {
        return sFName;
    }

    public void setsFName(String sFName) {
        this.sFName = sFName;
    }

    public String getsLName() {
        return sLName;
    }

    public void setsLName(String sLName) {
        this.sLName = sLName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "speakerId=" + speakerId +
                ", addressId=" + addressId +
                ", sFName='" + sFName + '\'' +
                ", sLName='" + sLName + '\'' +
                ", title='" + title + '\'' +
                ", industry='" + industry + '\'' +
                ", cost=" + cost +
                '}';
    }
}

