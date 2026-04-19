import java.util.Date;

public class Due {
    int dueId;
    int memberId;
    double amount;
    Date date;

    public Due(int dueId, int memberId, double amount, Date date) {
        this.dueId = dueId;
        this.memberId = memberId;
        this.amount = amount;
        this.date = date;
    }

    public int getDueId() {
        return dueId;
    }

    public void setDueId(int dueId) {
        this.dueId = dueId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Dues{" +
                "dueId=" + dueId +
                ", memberId=" + memberId +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}

