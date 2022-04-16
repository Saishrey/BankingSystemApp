package data;

public class Transaction {
    private String fromAccount;
    private String fromName;
    private int fromGender;
    private String toAccount;
    private String toName;
    private int toGender;
    private int amountTransferred;
    private int status;
    private String datetime;

    public Transaction(String fromAccount, String fromName, int fromGender, String toAccount, String toName, int toGender, int amountTransferred, int status, String datetime) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amountTransferred = amountTransferred;
        this.status = status;
        this.fromName = fromName;
        this.fromGender = fromGender;
        this.toName = toName;
        this.toGender = toGender;
        this.datetime = datetime;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public int getAmountTransferred() {
        return amountTransferred;
    }

    public void setAmountTransferred(int amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFromGender() {
        return fromGender;
    }

    public int getToGender() {
        return toGender;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToName() {
        return toName;
    }

    public String getDatetime() {
        return datetime;
    }
}

