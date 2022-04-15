package data;

// Stores user data
public class User {
    private String name;
    private String accountNumber;
    private String contact;
    private String ifscCode;
    private int gender;
    private int availableBalance;
    private String email;

    public User(String name, String accountNumber, String contact, String ifscCode, int gender, int availableBalance, String email) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.contact = contact;
        this.ifscCode = ifscCode;
        this.gender = gender;
        this.availableBalance = availableBalance;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(int availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
