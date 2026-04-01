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
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class PadRecordsAnalysisService {
	private static final String padUsersQuery = "SELECT * FROM FND_USER WHERE LAST_UPDATE_DATE = CURDATE() - INTERVAL 1 DAY ORDER BY USER_ID ASC";
    private static final String padCustomersQuery = "SELECT * FROM HZ_PARTIES WHERE CATEGORY_CODE = \'STUDENT\' AND EBS_PARTY_ID IS NULL AND CREATION_DATE >\'2019-01-01\'ORDER BY CREATION_DATE DESC";
    private static final String padContractForGuarantorsQuery = "SELECT OKC_K_HEADERS_ALL_B.EBS_ID  FROM OKC_K_PARTY_ROLES_B INNER JOIN  OKC_K_HEADERS_ALL_B ON OKC_K_PARTY_ROLES_B.CHR_ID =OKC_K_HEADERS_ALL_B.ID WHERE OKC_K_HEADERS_ALL_B.STS_CODE IN(\'OFFERED\',\'COMPLETED\',\'PRE_CHECK_IN\') AND  OKC_K_HEADERS_ALL_B.ATTRIBUTE1 IN( \'STL\',\'DIRECT_LET\',\'NOMS_REFER\') AND  OKC_K_HEADERS_ALL_B.LAST_UPDATE_DATE>=CURRENT_DATE() -1  AND OKC_K_PARTY_ROLES_B.RLE_CODE= \'GUARANTOR\' AND  DATEDIFF( OKC_K_HEADERS_ALL_B.END_DATE , OKC_K_HEADERS_ALL_B.START_DATE) >=91";
    private static final String checkedInIssueQuery = "SELECT OKH.CONTRACT_NUMBER FROM CONTRACT_ROOM_INFORMATION CRI JOIN OKC_K_HEADERS_ALL_B OKH ON CRI.CONTRACT_NUM = OKH.CONTRACT_NUMBER WHERE OKH.STS_CODE = \'CHECKED_IN\' AND (CRI.CHECKED_IN = \'N\')";
    private static final String duplicateExternalPayerQuery = "SELECT EXT_PAYER_ID FROM IBY_EXTERNAL_PAYERS_ALL WHERE ACCT_SITE_USE_ID IN (SELECT  ACCT_SITE_USE_ID FROM IBY_EXTERNAL_PAYERS_ALL GROUP BY ACCT_SITE_USE_ID HAVING COUNT(*) >1)";
    private static final String padBookingsQuery = "SELECT * FROM OKC_K_HEADERS_ALL_B WHERE CREATION_DATE >\'2019-01-01\' AND STS_CODE = \'SUBMITTED\' ORDER BY CREATION_DATE DESC";
    private static final String ebsStatusQuery = "SELECT EBS_UP_STATUS FROM FUNCTIONAL_STATUS";
    private static final String padGuarantorAcceptanceQuery = "SELECT EBS_ID FROM OKC_K_HEADERS_ALL_B WHERE ID IN (SELECT CHR_ID FROM OKC_K_PARTY_ROLES_B WHERE ACCEPTANCE_DATE is not null AND RLE_CODE=\'GUARANTOR\'  AND ACCEPTANCE_DATE >=\nCURRENT_DATE() -1)";
    private static final String padEnableInstallmentPlans = "SELECT LOOKUP_CODE FROM FND_LOOKUP_VALUES where LOOKUP_TYPE=\'UNITE_BILLING_PROFILES\' and ENABLED_FLAG=\'Y\'\nand CREATION_DATE >= DATE_SUB(NOW(),INTERVAL 1 DAY)";
    private static final String padDisableInstallmentPlans = "SELECT LOOKUP_CODE FROM FND_LOOKUP_VALUES where LOOKUP_TYPE=\'UNITE_BILLING_PROFILES\' and ENABLED_FLAG=\'N\'\nand CREATION_DATE >= DATE_SUB(NOW(),INTERVAL 1 DAY)";
    private String queryToGetException = "SELECT PAD_MESSAGE_IDENTIFIER, EXCEPTION_TYPE FROM FALLBACK_ERROR_LOGS WHERE PAD_MESSAGE_IDENTIFIER IN( ";

    public PadRecordsAnalysisService() {
    }

    public List<User> getPadUsers(Statement statement, List<User> padUsers) throws Exception {
        ResultSet padUserResultSet = statement.executeQuery(padUsersQuery);
        while(padUserResultSet.next()) {
            User user = new User();
            user.setUserId(Long.valueOf(padUserResultSet.getLong("USER_ID")));
            user.setUserName(padUserResultSet.getString("USER_NAME"));
            user.setCreateDate(padUserResultSet.getString("CREATION_DATE"));
            user.setLastUpdateDate(padUserResultSet.getString("LAST_UPDATE_DATE"));
            user.setEndDate(padUserResultSet.getString("END_DATE"));
            padUsers.add(user);
            
        }

        padUserResultSet.close();
        getPadUserResponsibilities(statement,padUsers);
		 
        return padUsers;
    }
    
    public void getPadUserResponsibilities(Statement statement,List<User> padUsers) throws Exception {
    	
    	for(User user: padUsers) {
    		if(StringUtils.isBlankOrNull(user.getEndDate())) {
    			List<String> respId = new ArrayList<String>();
    			ResultSet padUserResponsibilitySet = statement.executeQuery("SELECT DISTINCT RESPONSIBILITY_ID FROM FND_USER_RESP_GROUPS WHERE USER_ID="+user.getUserId());
    			while(padUserResponsibilitySet.next()) {
    				
    					respId.add(padUserResponsibilitySet.getString("RESPONSIBILITY_ID"));
    				}
    				user.setRespId(respId);
    				user.setEndDate("YYYY-MM-DD");
    		}
    		else
    			user.setRespId(Arrays.asList("NA"));
    	}
    	
    }
    
    
    public List<Booking> getCheckedInIssue(Statement statement, List<Booking> checkedInIssueontractNumber) throws Exception {
        ResultSet checkedInResultSet = statement.executeQuery("SELECT OKH.CONTRACT_NUMBER FROM CONTRACT_ROOM_INFORMATION CRI JOIN OKC_K_HEADERS_ALL_B OKH ON CRI.CONTRACT_NUM = OKH.CONTRACT_NUMBER WHERE OKH.STS_CODE = \'CHECKED_IN\' AND (CRI.CHECKED_IN = \'N\')");

        while(checkedInResultSet.next()) {
            Booking booking = new Booking();
            booking.setBookingNumber(checkedInResultSet.getString("CONTRACT_NUMBER"));
            checkedInIssueontractNumber.add(booking);
        }

        checkedInResultSet.close();
        return checkedInIssueontractNumber;
    }

    public List<Booking> getDuplicateNoms3PContractRecord(Statement statement, List<Booking> duplicateNoms3PContract) throws Exception {
        ResultSet noms3PContractSet = statement.executeQuery("SELECT CONTRACT_NUMBER FROM OKC_K_HEADERS_ALL_B WHERE ATTRIBUTE1=\'NOMS_3RD_PARTY\' AND EBS_ID IS NOT NULL AND CREATION_DATE >= DATE_SUB(NOW(),INTERVAL 2 DAY) GROUP BY CONTRACT_NUMBER HAVING COUNT(CONTRACT_NUMBER)>1");

        while(noms3PContractSet.next()) {
            Booking booking = new Booking();
            booking.setNoms3PContractNumber(noms3PContractSet.getString("CONTRACT_NUMBER"));
            duplicateNoms3PContract.add(booking);
        }

        noms3PContractSet.close();
        return duplicateNoms3PContract;
    }
    
    public List<Booking> getExternalPayerIssue(Statement statement, List<Booking> externalPayer) throws Exception {
        ResultSet externalPayerSet = statement.executeQuery("SELECT EXT_PAYER_ID FROM IBY_EXTERNAL_PAYERS_ALL WHERE ACCT_SITE_USE_ID IN (SELECT  ACCT_SITE_USE_ID FROM IBY_EXTERNAL_PAYERS_ALL GROUP BY ACCT_SITE_USE_ID HAVING COUNT(*) >1)");

        while(externalPayerSet.next()) {
            Booking booking = new Booking();
            booking.setPadBookingId(Long.valueOf(externalPayerSet.getLong("EXT_PAYER_ID")));
            externalPayer.add(booking);
        }

        externalPayerSet.close();
        return externalPayer;
    }

    public List<Booking> updateListWithExeption(Statement statement, List<Booking> bookingUpdatedList, List<Booking> bookingList) throws Exception {
        ArrayList contractIds = new ArrayList();
        bookingList.forEach((booking) -> {
            contractIds.add(booking.getPadBookingId());
        });
        String contractIdsAsList = StringUtils.listToDelimitedString(contractIds, ",");
        String newQueryToGetException = this.queryToGetException + contractIdsAsList + ")  AND  EXCEPTION_TYPE NOT IN (\'missing record in fallback\') AND UPDATE_MESSAGE =\'N\'";
        System.out.println("updateListWithExeption : " + newQueryToGetException);
        ResultSet updateSet = statement.executeQuery(newQueryToGetException);

        while(updateSet.next()) {
            Booking booking = new Booking();
            booking.setPadBookingId(Long.valueOf(updateSet.getLong("PAD_MESSAGE_IDENTIFIER")));
            booking.setExceptionType(updateSet.getString("EXCEPTION_TYPE"));
            bookingUpdatedList.add(booking);
        }

        updateSet.close();
        return bookingUpdatedList;
    }

    public List<Customer> updateListWithExeptionCustomer(Statement statement, List<Customer> bookingUpdatedList, List<Customer> bookingList) throws Exception {
        ArrayList contractIds = new ArrayList();
        bookingList.forEach((booking) -> {
            contractIds.add(booking.getId());
        });
        String contractIdsAsList = StringUtils.listToDelimitedString(contractIds, ",");
        String newQueryToGetException = this.queryToGetException + contractIdsAsList + ")  AND  EXCEPTION_TYPE NOT IN (\'missing record in fallback\') AND UPDATE_MESSAGE =\'N\'";
        System.out.println("updateListWithExeption Customer: " + newQueryToGetException);
        ResultSet updateSet = statement.executeQuery(newQueryToGetException);

        while(updateSet.next()) {
            Customer booking = new Customer();
            booking.setId(Long.valueOf(updateSet.getLong("PAD_MESSAGE_IDENTIFIER")));
            booking.setExceptionType(updateSet.getString("EXCEPTION_TYPE"));
            bookingUpdatedList.add(booking);
        }

        updateSet.close();
        return bookingUpdatedList;
    }

    public List<Customer> getCustomers(Statement statement, List<Customer> padCustomers) throws Exception {
        ResultSet padCustomerResultSet = statement.executeQuery("SELECT * FROM HZ_PARTIES WHERE CATEGORY_CODE = \'STUDENT\' AND EBS_PARTY_ID IS NULL AND CREATION_DATE >\'2019-01-01\'ORDER BY CREATION_DATE DESC");

        while(padCustomerResultSet.next()) {
            Customer customer = new Customer();
            customer.setId(Long.valueOf(padCustomerResultSet.getLong("PARTY_ID")));
            customer.setCustomerNumber(padCustomerResultSet.getString("PARTY_NUMBER"));
            customer.setEbsId(Long.valueOf(padCustomerResultSet.getLong("EBS_PARTY_ID")));
            customer.setCategoryCode(padCustomerResultSet.getString("CATEGORY_CODE"));
            customer.setFirstName(padCustomerResultSet.getString("PERSON_FIRST_NAME"));
            customer.setMiddleName(padCustomerResultSet.getString("PERSON_MIDDLE_NAME"));
            customer.setLastName(padCustomerResultSet.getString("PERSON_LAST_NAME"));
            customer.setCreation_date(padCustomerResultSet.getString("CREATION_DATE"));
            padCustomers.add(customer);
        }

        padCustomerResultSet.close();
        return padCustomers;
    }

    public List<Booking> getPadContractForGuarantorAcceptance(Statement statement, List<Booking> padGuarantorAcceptance) throws Exception {
        ResultSet padGuarantorResultSet = statement.executeQuery("SELECT EBS_ID FROM OKC_K_HEADERS_ALL_B WHERE ID IN (SELECT CHR_ID FROM OKC_K_PARTY_ROLES_B WHERE ACCEPTANCE_DATE is not null AND RLE_CODE=\'GUARANTOR\'  AND ACCEPTANCE_DATE >=\nCURRENT_DATE() -1)");

        while(padGuarantorResultSet.next()) {
            Booking booking = new Booking();
            booking.setEbsBookingId(Long.valueOf(padGuarantorResultSet.getLong("EBS_ID")));
            padGuarantorAcceptance.add(booking);
        }

        padGuarantorResultSet.close();
        return padGuarantorAcceptance;
    }
    
    public List<Booking> getPadContractForCustomerAcceptance(Statement statement, List<Booking> padCustomerAcceptance) throws Exception {
        ResultSet padCustomerResultSet = statement.executeQuery("SELECT EBS_ID FROM OKC_K_HEADERS_ALL_B WHERE ID IN (SELECT CHR_ID FROM OKC_K_PARTY_ROLES_B WHERE ACCEPTANCE_DATE is not null AND RLE_CODE=\'CUSTOMER\'  AND ACCEPTANCE_DATE >=\nCURRENT_DATE() -1)");

        while(padCustomerResultSet.next()) {
            Booking booking = new Booking();
            booking.setEbsBookingId(Long.valueOf(padCustomerResultSet.getLong("EBS_ID")));
            padCustomerAcceptance.add(booking);
        }

        padCustomerResultSet.close();
        return padCustomerAcceptance;
    }

    public List<Installments> getPadEnableInstallmentPlan(Statement statement, List<Installments> padInstallments) throws Exception {
        ResultSet padInstallmentPlanResultSet = statement.executeQuery("SELECT LOOKUP_CODE FROM FND_LOOKUP_VALUES where LOOKUP_TYPE=\'UNITE_BILLING_PROFILES\' and ENABLED_FLAG=\'Y\'\nand CREATION_DATE >= DATE_SUB(NOW(),INTERVAL 1 DAY)");

        while(padInstallmentPlanResultSet.next()) {
            Installments installment = new Installments();
            installment.setPadLookupCode(padInstallmentPlanResultSet.getString("LOOKUP_CODE"));
            padInstallments.add(installment);
        }

        padInstallmentPlanResultSet.close();
        return padInstallments;
    }

    public List<Installments> getPadDisableInstallmentPlan(Statement statement, List<Installments> padInstallments) throws Exception {
        ResultSet padInstallmentPlanResultSet = statement.executeQuery("SELECT LOOKUP_CODE FROM FND_LOOKUP_VALUES where LOOKUP_TYPE=\'UNITE_BILLING_PROFILES\' and ENABLED_FLAG=\'N\'\nand CREATION_DATE >= DATE_SUB(NOW(),INTERVAL 1 DAY)");

        while(padInstallmentPlanResultSet.next()) {
            Installments installment = new Installments();
            installment.setPadLookupCode(padInstallmentPlanResultSet.getString("LOOKUP_CODE"));
            padInstallments.add(installment);
        }

        padInstallmentPlanResultSet.close();
        return padInstallments;
    }

    public List<Booking> getcontractOnGuarantors(Statement statement, List<Booking> padGuarantors) throws Exception {
        ResultSet padGuarantorResultSet = statement.executeQuery("SELECT OKC_K_HEADERS_ALL_B.EBS_ID  FROM OKC_K_PARTY_ROLES_B INNER JOIN  OKC_K_HEADERS_ALL_B ON OKC_K_PARTY_ROLES_B.CHR_ID =OKC_K_HEADERS_ALL_B.ID WHERE OKC_K_HEADERS_ALL_B.STS_CODE IN(\'OFFERED\',\'COMPLETED\',\'PRE_CHECK_IN\') AND  OKC_K_HEADERS_ALL_B.ATTRIBUTE1 IN( \'STL\',\'DIRECT_LET\',\'NOMS_REFER\') AND  OKC_K_HEADERS_ALL_B.LAST_UPDATE_DATE>=CURRENT_DATE() -1  AND OKC_K_PARTY_ROLES_B.RLE_CODE= \'GUARANTOR\' AND  DATEDIFF( OKC_K_HEADERS_ALL_B.END_DATE , OKC_K_HEADERS_ALL_B.START_DATE) >=91");

        while(padGuarantorResultSet.next()) {
            Booking booking = new Booking();
            booking.setEbsBookingId(Long.valueOf(padGuarantorResultSet.getLong("EBS_ID")));
            padGuarantors.add(booking);
        }

        padGuarantorResultSet.close();
        return padGuarantors;
    }

    public List<Booking> getBookings(Statement statement, List<Booking> padBookings) throws Exception {
        ResultSet padBookingResultSet = statement.executeQuery("SELECT * FROM OKC_K_HEADERS_ALL_B WHERE CREATION_DATE >\'2019-01-01\' AND STS_CODE = \'SUBMITTED\' ORDER BY CREATION_DATE DESC");

        while(padBookingResultSet.next()) {
            Booking booking = new Booking();
            booking.setPadBookingId(Long.valueOf(padBookingResultSet.getLong("ID")));
            booking.setPadBookingStatus(padBookingResultSet.getString("STS_CODE"));
            booking.setBookingNumber(padBookingResultSet.getString("CONTRACT_NUMBER"));
            booking.setEbsBookingId(Long.valueOf(padBookingResultSet.getLong("EBS_ID")));
            booking.setCreation_date(padBookingResultSet.getString("CREATION_DATE"));
            padBookings.add(booking);
        }

        padBookingResultSet.close();
        return padBookings;
    }

    public boolean ifEbsUp(Statement statement) throws Exception {
        String ebsStatus = null;

        ResultSet rs;
        for(rs = statement.executeQuery("SELECT EBS_UP_STATUS FROM FUNCTIONAL_STATUS"); rs.next(); ebsStatus = rs.getString("EBS_UP_STATUS")) {
            ;
        }

        rs.close();
        System.out.println("ebsStatus : " + ebsStatus);
        return "Y".equals(ebsStatus);
    }
}
