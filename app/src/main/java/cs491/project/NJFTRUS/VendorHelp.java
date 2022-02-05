package cs491.project.NJFTRUS;

public class VendorHelp {
    String name, truckName, email, phoneNo;
    public VendorHelp(String name, String truckName, String email, String phoneNo) {
        this.name = name;
        this.truckName = truckName;
        this.email = email;
        this.phoneNo = phoneNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String gettruckName() {
        return truckName;
    }
    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

}