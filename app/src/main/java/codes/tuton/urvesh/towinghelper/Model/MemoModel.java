package codes.tuton.urvesh.towinghelper.Model;

public class MemoModel {
    public MemoModel() {
    }

    String id,vehicle_no,u_name,u_mobileno,u_address,p_name,p_mobileno,p_area,p_govid,towing_image,date,time,amount,status;

    public MemoModel(String vehicle_no, String u_name, String u_mobileno, String u_address, String p_name, String p_mobileno, String p_area, String p_govid, String towing_image, String date, String time, String amount, String status) {
        this.vehicle_no = vehicle_no;
        this.u_name = u_name;
        this.u_mobileno = u_mobileno;
        this.u_address = u_address;
        this.p_name = p_name;
        this.p_mobileno = p_mobileno;
        this.p_area = p_area;
        this.p_govid = p_govid;
        this.towing_image = towing_image;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_mobileno() {
        return u_mobileno;
    }

    public void setU_mobileno(String u_mobileno) {
        this.u_mobileno = u_mobileno;
    }

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_mobileno() {
        return p_mobileno;
    }

    public void setP_mobileno(String p_mobileno) {
        this.p_mobileno = p_mobileno;
    }

    public String getP_area() {
        return p_area;
    }

    public void setP_area(String p_area) {
        this.p_area = p_area;
    }

    public String getP_govid() {
        return p_govid;
    }

    public void setP_govid(String p_govid) {
        this.p_govid = p_govid;
    }

    public String getTowing_image() {
        return towing_image;
    }

    public void setTowing_image(String towing_image) {
        this.towing_image = towing_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
