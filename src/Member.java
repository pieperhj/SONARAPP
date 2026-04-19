import java.util.Date;

public class Member {

    private int memberId;
    private String mFName;
    private String mLName;
    private String mEmail;
    private String major;
    private String status;
    private int addressId;
    private Date startDate;
    private Date endDate;

    public Member(int memberId, String mFName, String mLName, String mEmail, String major, String status,int addressId, Date startDate, Date endDate) {
        super();
        this.memberId = memberId;
        this.mFName = mFName;
        this.mLName = mLName;
        this.mEmail = mEmail;
        this.major = major;
        this.status = status;
        this.addressId = addressId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Member(String mFName, String mLName, String mEmail, String major, String status,int addressId, Date startDate, Date endDate) {
        super();
        this.memberId = -1;
        this.mFName = mFName;
        this.mLName = mLName;
        this.mEmail = mEmail;
        this.major = major;
        this.status = status;
        this.addressId = addressId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMFName() {
        return mFName;
    }

    public void setMFName(String mFName) {
        this.mFName = mFName;
    }

    public String getMLName() {
        return mLName;
    }

    public void setMLName(String mLName) {
        this.mLName = mLName;
    }

    public String getMEmail() {
        return mEmail;
    }

    public void setMEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    @Override
    public String toString() {
        return "Member [memberId=" + memberId + ", mFName=" + mFName + ", mLName=" + mLName + ", mEmail=" + mEmail +
                ", major=" + major + ", status=" + status+ ", addressId=" + addressId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}
