package codes.tuton.urvesh.towinghelper.Model;

public class UserInfoModel {
    public UserInfoModel() {
    }

    String name,mobile_no,address;

    public UserInfoModel(String name, String mobile_no, String address) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
