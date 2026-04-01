//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Reporting;

import Reporting.Booking;
import Reporting.Customer;
import Reporting.Installments;
import com.sameer.utilities.pack.utils.StringUtils;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EbsRecordsAnalysisService {
	private static final String ebsUsersQuery = "SELECT * FROM FND_USER WHERE TO_DATE(LAST_UPDATE_DATE) = TO_DATE(SYSDATE-1)  ORDER BY USER_ID ASC";
    private static final String ebsCustomersQuery = "SELECT PARTY_ID, PARTY_NUMBER, ATTRIBUTE10 , CATEGORY_CODE FROM HZ_PARTIES WHERE CATEGORY_CODE IN(\'STUDENT\', \'GUARANTOR\', \'EMERGENCY_CONTACT\') AND CREATION_DATE >= trunc(sysdate)";
    private static final String ebsBookingsQuery = "SELECT * FROM OKC_K_HEADERS_ALL_B WHERE CREATION_DATE >= trunc(sysdate)";
    private static final String ebsEnableInstallmentPlans = "SELECT LOOKUP_CODE FROM FND_LOOKUP_VALUES where LOOKUP_TYPE=\'UNITE_BILLING_PROFILES\' and ENABLED_FLAG=\'Y\'\nand CREATION_DATE >= trunc(sysdate-1)";
    private static final String ebsDisableInstallmentPlans = "SELECT LOOKUP_CODE FROM FND_LOOKUP_VALUES where LOOKUP_TYPE=\'UNITE_BILLING_PROFILES\' and ENABLED_FLAG=\'N\'\nand CREATION_DATE >= trunc(sysdate-1)";
    private String ebsMissingGuarantorsQuery = "SELECT CHR_ID FROM OKC_K_PARTY_ROLES_B WHERE RLE_CODE= \'CUSTOMER\' AND CHR_ID NOT IN (SELECT CHR_ID  FROM OKC_K_PARTY_ROLES_B WHERE RLE_CODE= \'GUARANTOR\' AND ";
    private String ebsGuarantorAccetancesQuery = "SELECT CHR_ID FROM okc_k_party_roles_b WHERE ATTRIBUTE4 is null AND  RLE_CODE=\'GUARANTOR\' AND  ";
    private String ebsCustomerAccetancesQuery = "SELECT CHR_ID FROM okc_k_party_roles_b WHERE ATTRIBUTE4 is null AND  RLE_CODE=\'CUSTOMER\' AND  ";
    private static final int limitOnPassingListInEbs = 1000;

    public EbsRecordsAnalysisService() {
    }

    public List<User> getEbsUsers(Statement statement, List<User> ebsUsers) throws Exception {
        ResultSet ebsUserResultSet = statement.executeQuery(ebsUsersQuery);
        while(ebsUserResultSet.next()) {
            User user = new User();
            user.setUserId(Long.valueOf(ebsUserResultSet.getLong("USER_ID")));
            user.setUserName(ebsUserResultSet.getString("USER_NAME"));
            user.setCreateDate(ebsUserResultSet.getString("CREATION_DATE"));
            user.setLastUpdateDate(ebsUserResultSet.getString("LAST_UPDATE_DATE"));
            user.setEndDate(ebsUserResultSet.getString("END_DATE"));
            ebsUsers.add(user);
            
        }

        ebsUserResultSet.close();
        getEbsUserResponsibilities(statement,ebsUsers);
        return ebsUsers;
    }
    
    public void getEbsUserResponsibilities(Statement statement,List<User> ebsUsers) throws Exception {
    	
    	for(User user: ebsUsers) {
    		if(StringUtils.isBlankOrNull(user.getEndDate())) {
    			List<String> respId = new ArrayList<String>();
    			ResultSet ebsUserResponsibilitySet = statement.executeQuery("SELECT DISTINCT RESPONSIBILITY_ID FROM FND_USER_RESP_GROUPS WHERE USER_ID="+user.getUserId());
    			while(ebsUserResponsibilitySet.next()) {
    					respId.add(ebsUserResponsibilitySet.getString("RESPONSIBILITY_ID"));
    				
    	    	}
    				user.setRespId(respId);
    				user.setEndDate("YYYY-MM-DD");
    		}
    		else
    			user.setRespId(Arrays.asList("NA"));
    	}
    	
    }
    
    public List<Customer> getCustomers(Statement statement, List<Customer> ebsCustomers) throws Exception {
        ResultSet ebsCustomerResultSet = statement.executeQuery("SELECT PARTY_ID, PARTY_NUMBER, ATTRIBUTE10 , CATEGORY_CODE FROM HZ_PARTIES WHERE CATEGORY_CODE IN(\'STUDENT\', \'GUARANTOR\', \'EMERGENCY_CONTACT\') AND CREATION_DATE >= trunc(sysdate)");

        while(ebsCustomerResultSet.next()) {
            Customer customer = new Customer();
            customer.setEbsId(Long.valueOf(ebsCustomerResultSet.getLong("party_id")));
            customer.setCustomerNumber(ebsCustomerResultSet.getString("party_number"));
            customer.setId(Long.valueOf(ebsCustomerResultSet.getLong("attribute10")));
            customer.setCategoryCode(ebsCustomerResultSet.getString("CATEGORY_CODE"));
            ebsCustomers.add(customer);
        }

        ebsCustomerResultSet.close();
        return ebsCustomers;
    }

    public List<Booking> getBookings(Statement statement, List<Booking> ebsBookings) throws Exception {
        ResultSet ebsBookingResultSet = statement.executeQuery("SELECT * FROM OKC_K_HEADERS_ALL_B WHERE CREATION_DATE >= trunc(sysdate)");

        while(ebsBookingResultSet.next()) {
            Booking booking = new Booking();
            booking.setPadBookingId(Long.valueOf(ebsBookingResultSet.getLong("ATTRIBUTE24")));
            booking.setEbsBookingId(Long.valueOf(ebsBookingResultSet.getLong("ID")));
            booking.setBookingNumber(ebsBookingResultSet.getString("CONTRACT_NUMBER"));
            booking.setEbsBookingStatus(ebsBookingResultSet.getString("STS_CODE"));
            ebsBookings.add(booking);
        }

        ebsBookingResultSet.close();
        return ebsBookings;
    }

    public List<Installments> getEbsEnableInstallments(Statement statement, List<Installments> ebsInstallments) throws Exception {
        ResultSet ebsInstallmentResultSet = statement.executeQuery("SELECT LOOKUP_CODE FROM FND_LOOKUP_VALUES where LOOKUP_TYPE=\'UNITE_BILLING_PROFILES\' and ENABLED_FLAG=\'Y\'\nand CREATION_DATE >= trunc(sysdate-1)");

        while(ebsInstallmentResultSet.next()) {
            Installments installment = new Installments();
            installment.setEbsLookupCode(ebsInstallmentResultSet.getString("LOOKUP_CODE"));
            ebsInstallments.add(installment);
        }

        ebsInstallmentResultSet.close();
        return ebsInstallments;
    }

    public List<Installments> getEbsDisableInstallments(Statement statement, List<Installments> ebsInstallments) throws Exception {
        ResultSet ebsInstallmentResultSet = statement.executeQuery("SELECT LOOKUP_CODE FROM FND_LOOKUP_VALUES where LOOKUP_TYPE=\'UNITE_BILLING_PROFILES\' and ENABLED_FLAG=\'N\'\nand CREATION_DATE >= trunc(sysdate-1)");

        while(ebsInstallmentResultSet.next()) {
            Installments installment = new Installments();
            installment.setEbsLookupCode(ebsInstallmentResultSet.getString("LOOKUP_CODE"));
            ebsInstallments.add(installment);
        }

        ebsInstallmentResultSet.close();
        return ebsInstallments;
    }

    public List<Booking> getEbscontractForGuarantorAcceptanceIssue(Statement statement, List<Booking> padBookings, List<Booking> ebsContractOnGuarantorsAcceptance) throws Exception {
        ArrayList ebsContractIds = new ArrayList();
        padBookings.forEach((ebsBooking) -> {
            ebsContractIds.add(ebsBooking.getEbsBookingId());
        });
        String ebsContractIdAsList = StringUtils.listToDelimitedString(ebsContractIds, ",");
        if(ebsContractIds.size() > 1000) {
            ebsContractIdAsList = StringUtils.splitElementsIntoSqlLists(ebsContractIdAsList, 1000);
            ebsContractIdAsList = StringUtils.listToDelimitedString(StringUtils.delimitedStringToList(ebsContractIdAsList, "\n", false), "\r\n OR CHR_ID IN");
            ebsContractIdAsList = "(CHR_ID IN " + ebsContractIdAsList + ")";
        } else if(ebsContractIds.size() > 0) {
            ebsContractIdAsList = "CHR_ID IN ( " + ebsContractIdAsList + ")";
        }

        System.out.println("getEbscontractForGuarantorAcceptanceIssue ebsContractIdAsList: " + ebsContractIdAsList);
        this.ebsGuarantorAccetancesQuery = this.ebsGuarantorAccetancesQuery + ebsContractIdAsList;
        //System.out.println(" getEbscontractForGuarantorAcceptanceIssue ebsGuarantorAccetancesQuery: " + this.ebsGuarantorAccetancesQuery);
        if(ebsContractIds.size() > 0) {
            ResultSet ebsBookingResultSet = statement.executeQuery(this.ebsGuarantorAccetancesQuery);

            while(ebsBookingResultSet.next()) {
                Booking booking = new Booking();
                booking.setEbsBookingId(Long.valueOf(ebsBookingResultSet.getLong("CHR_ID")));
                ebsContractOnGuarantorsAcceptance.add(booking);
            }

            ebsBookingResultSet.close();
        }

        return ebsContractOnGuarantorsAcceptance;
    }
    
    public List<Booking> getEbscontractForCustomerAcceptanceIssue(Statement statement, List<Booking> padBookings, List<Booking> ebsContractOnCustomerAcceptance) throws Exception {
        ArrayList ebsContractIds = new ArrayList();
        padBookings.forEach((ebsBooking) -> {
            ebsContractIds.add(ebsBooking.getEbsBookingId());
        });
        String ebsContractIdAsList = StringUtils.listToDelimitedString(ebsContractIds, ",");
        if(ebsContractIds.size() > 1000) {
            ebsContractIdAsList = StringUtils.splitElementsIntoSqlLists(ebsContractIdAsList, 1000);
            ebsContractIdAsList = StringUtils.listToDelimitedString(StringUtils.delimitedStringToList(ebsContractIdAsList, "\n", false), "\r\n OR CHR_ID IN");
            ebsContractIdAsList = "(CHR_ID IN " + ebsContractIdAsList + ")";
        } else if(ebsContractIds.size() > 0) {
            ebsContractIdAsList = "CHR_ID IN ( " + ebsContractIdAsList + ")";
        }

        System.out.println("getEbscontractForCustomerAcceptanceIssue ebsContractIdAsList: " + ebsContractIdAsList);
        this.ebsCustomerAccetancesQuery = this.ebsCustomerAccetancesQuery + ebsContractIdAsList;
        //System.out.println(" getEbscontractForCustomerAcceptanceIssue ebsCustomerAccetancesQuery: " + this.ebsCustomerAccetancesQuery);
        if(ebsContractIds.size() > 0) {
            ResultSet ebsBookingResultSet = statement.executeQuery(this.ebsCustomerAccetancesQuery);

            while(ebsBookingResultSet.next()) {
                Booking booking = new Booking();
                booking.setEbsBookingId(Long.valueOf(ebsBookingResultSet.getLong("CHR_ID")));
                ebsContractOnCustomerAcceptance.add(booking);
            }

            ebsBookingResultSet.close();
        }

        return ebsContractOnCustomerAcceptance;
    }

    public List<Booking> getEbsContractFromPadcontractonGuarantor(Statement statement, List<Booking> padBookings, List<Booking> ebsContractOnGuarantors) throws Exception {
        ArrayList ebsContractIds = new ArrayList();
        padBookings.forEach((ebsBooking) -> {
            ebsContractIds.add(ebsBooking.getEbsBookingId());
        });
        String ebsContractIdAsList = StringUtils.listToDelimitedString(ebsContractIds, ",");
        if(ebsContractIds.size() > 1000) {
            ebsContractIdAsList = StringUtils.splitElementsIntoSqlLists(ebsContractIdAsList, 1000);
            ebsContractIdAsList = StringUtils.listToDelimitedString(StringUtils.delimitedStringToList(ebsContractIdAsList, "\n", false), "\r\n OR CHR_ID IN");
            ebsContractIdAsList = "(CHR_ID IN " + ebsContractIdAsList + ")";
        } else if(ebsContractIds.size() > 0) {
            ebsContractIdAsList = "CHR_ID IN ( " + ebsContractIdAsList + ")";
        }

        this.ebsMissingGuarantorsQuery = this.ebsMissingGuarantorsQuery + ebsContractIdAsList + " ) AND " + ebsContractIdAsList;
        //System.out.println(" ebsMissingGuarantorsQuery: " + this.ebsMissingGuarantorsQuery);
        if(ebsContractIds.size() > 0) {
            ResultSet ebsBookingResultSet = statement.executeQuery(this.ebsMissingGuarantorsQuery);

            while(ebsBookingResultSet.next()) {
                Booking booking = new Booking();
                booking.setEbsBookingId(Long.valueOf(ebsBookingResultSet.getLong("CHR_ID")));
                ebsContractOnGuarantors.add(booking);
            }

            ebsBookingResultSet.close();
        }

        return ebsContractOnGuarantors;
    }
}
