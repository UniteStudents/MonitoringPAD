//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Reporting;

public class Customer {
    private Long id;
    private Long ebsId;
    private String customerNumber;
    private String categoryCode;
    private String firstName;
    private String middleName;
    private String lastName;
    private String creation_date;
    private String exceptionType;

    public Customer() {
    }

    public String getExceptionType() {
        return this.exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerNumber() {
        return this.customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Long getEbsId() {
        return this.ebsId;
    }

    public void setEbsId(Long ebsId) {
        this.ebsId = ebsId;
    }

    public String getCategoryCode() {
        return this.categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCreation_date() {
        return this.creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
