//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Reporting;

public class Booking {
    private Long padBookingId;
    private Long ebsBookingId;
    private String ebsBookingStatus;
    private String padBookingStatus;
    private String exceptionType;
    private String creation_date;
    private String bookingNumber;
    private String noms3PContractNumber;

    public Booking() {
    }
    
    

    public String getNoms3PContractNumber() {
		return noms3PContractNumber;
	}



	public void setNoms3PContractNumber(String noms3pContractNumber) {
		noms3PContractNumber = noms3pContractNumber;
	}



	public String getExceptionType() {
        return this.exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Long getPadBookingId() {
        return this.padBookingId;
    }

    public void setPadBookingId(Long padBookingId) {
        this.padBookingId = padBookingId;
    }

    public Long getEbsBookingId() {
        return this.ebsBookingId;
    }

    public void setEbsBookingId(Long ebsBookingId) {
        this.ebsBookingId = ebsBookingId;
    }

    public String getBookingNumber() {
        return this.bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getEbsBookingStatus() {
        return this.ebsBookingStatus;
    }

    public void setEbsBookingStatus(String ebsBookingStatus) {
        this.ebsBookingStatus = ebsBookingStatus;
    }

    public String getPadBookingStatus() {
        return this.padBookingStatus;
    }

    public void setPadBookingStatus(String padBookingStatus) {
        this.padBookingStatus = padBookingStatus;
    }

    public String getCreation_date() {
        return this.creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
