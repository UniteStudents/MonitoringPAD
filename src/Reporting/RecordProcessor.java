//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Reporting;

import Reporting.Booking;
import Reporting.BookingRecordsProcessor;
import Reporting.Customer;
import Reporting.CustomerRecordsProcessor;
import Reporting.EbsRecordsAnalysisService;
import Reporting.Installments;
import Reporting.PadRecordsAnalysisService;
import Reporting.SaveExcelReport;
import dbConnections.MySqlConnection;
import dbConnections.OracleConnection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecordProcessor {
    public static final String defaultEnvironment = "projdev";

    public RecordProcessor() {
    }

    public static void main(String[] args) {
        String envName = "prod";
        if(args != null && args.length != 0) {
            envName = args[0];
            System.out.println("passed envName as an argument: " + args[0]);
        }

        try {
            Statement se = MySqlConnection.getMySqlConnetion(envName);
            PadRecordsAnalysisService padRecordsAnalysisService = new PadRecordsAnalysisService();
            if(padRecordsAnalysisService.ifEbsUp(se)) {
                ArrayList padCustomers = new ArrayList();
                ArrayList padBookings = new ArrayList();
                getPadCustomersAndBookings(se, padRecordsAnalysisService, padCustomers, padBookings);
                ArrayList padUsers = new ArrayList();
                ArrayList ebsUsers = new ArrayList();
                ArrayList ebsCustomers = new ArrayList();
                ArrayList ebsBookings = new ArrayList();
                ArrayList checkedInIssueBooking = new ArrayList();
                ArrayList contractForGuarantorAcceptanceIssue = new ArrayList();
                ArrayList contractOnGuarantors = new ArrayList();
                ArrayList ebsContractOnGuarantors = new ArrayList();
                ArrayList ebsContractOnGuarantorsAcceptance = new ArrayList();
                ArrayList externalPayer = new ArrayList();
                ArrayList duplicateNoms3PContract = new ArrayList();
                ArrayList padEnableInstallments = new ArrayList();
                ArrayList ebsEnableInstallments = new ArrayList();
                ArrayList padDisableInstallments = new ArrayList();
                ArrayList ebsDisableInstallments = new ArrayList();
                ArrayList contractForCustomerAcceptanceIssue = new ArrayList();
                ArrayList ebsContractOnCustomerAcceptance = new ArrayList();
                //getPadUsers(se, padRecordsAnalysisService,padUsers);
                //getEbsUsers(envName,ebsUsers);
                //processUserRecords(envName, padUsers, ebsUsers, se);
                //getChekedInIssueBookings(se, padRecordsAnalysisService, checkedInIssueBooking);
                //getPadEnableInstallmentPlan(se, padRecordsAnalysisService, padEnableInstallments);
                //getPadDisableInstallmentPlan(se, padRecordsAnalysisService, padDisableInstallments);
                getExternalPayerIssue(se, padRecordsAnalysisService, externalPayer);
                //getDuplicateNoms3PContractRecord(se, padRecordsAnalysisService, duplicateNoms3PContract);
                //getPadContractFroGuarantors(se, padRecordsAnalysisService, contractOnGuarantors);
                //getPadContractForGuarantorAcceptance(se, padRecordsAnalysisService, contractForGuarantorAcceptanceIssue);
                //getPadContractForCustomerAcceptance(se, padRecordsAnalysisService, contractForCustomerAcceptanceIssue);
                //getEbsCustomersAndBookings(envName, ebsCustomers, ebsBookings);
                //getEbsContractFroGuarantors(envName, contractOnGuarantors, ebsContractOnGuarantors);
                //getEbsEnableInstallments(envName, ebsEnableInstallments);
                //getEbsDisableInstallments(envName, ebsDisableInstallments);
                //getEbscontractForGuarantorAcceptanceIssue(envName, contractForGuarantorAcceptanceIssue, ebsContractOnGuarantorsAcceptance);
                //getEbscontractForCustomerAcceptanceIssue(envName, contractForCustomerAcceptanceIssue, ebsContractOnCustomerAcceptance);
                SaveExcelReport.checkFile();
                
                //processEnableBillingScheduleRecords(envName, padEnableInstallments, ebsEnableInstallments, se, padRecordsAnalysisService);
                //processDisableBillingScheduleRecords(envName, padDisableInstallments, ebsDisableInstallments, se, padRecordsAnalysisService);
                //processCustomerRecords(envName, padCustomers, ebsCustomers, se, padRecordsAnalysisService);
                //processBookingRecords(envName, padBookings, ebsBookings, se, padRecordsAnalysisService);
                //processCheckedInIssueRecords(envName, checkedInIssueBooking);
                processExternalPayerIssueRecords(envName, externalPayer);
                //processDuplicateNoms3PContractRecords(envName, duplicateNoms3PContract);
                //processGurantorRecord(envName, ebsContractOnGuarantors);
                //processcontractForGuarantorAcceptanceIssueRecord(envName, ebsContractOnGuarantorsAcceptance);
                //processcontractForCustomerAcceptanceIssueRecord(envName, ebsContractOnCustomerAcceptance);
            }
        } catch (Exception var18) {
            System.out.println("Exception occured while processing records: " + var18);
            var18.printStackTrace();
        }

    }

    private static void processBookingRecords(String envName, List<Booking> padBookings, List<Booking> ebsBookings, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        BookingRecordsProcessor bookingRecordsProcessor = new BookingRecordsProcessor();
        bookingRecordsProcessor.processBookings(padBookings, ebsBookings, envName, statement, padRecordsAnalysisService);
    }

    private static void processCheckedInIssueRecords(String envName, List<Booking> checkedInIssueBooking) {
        BookingRecordsProcessor bookingRecordsProcessor = new BookingRecordsProcessor();
        bookingRecordsProcessor.processCheckedInIssueBookings(checkedInIssueBooking, envName);
    }
    
    private static void processDuplicateNoms3PContractRecords(String envName, List<Booking> duplicateNoms3PContract) {
        BookingRecordsProcessor bookingRecordsProcessor = new BookingRecordsProcessor();
        bookingRecordsProcessor.processDuplicateNoms3PContractRecords(duplicateNoms3PContract, envName);
    }

    private static void processExternalPayerIssueRecords(String envName, List<Booking> duplicateExternalPayer) {
        CustomerRecordsProcessor customerRecordsProcessor = new CustomerRecordsProcessor();
        customerRecordsProcessor.processExternalPayerissue(duplicateExternalPayer, envName);
    }

    private static void processcontractForGuarantorAcceptanceIssueRecord(String envName, List<Booking> ebsContractsForGuarantorAcceptance) {
        CustomerRecordsProcessor customerRecordsProcessor = new CustomerRecordsProcessor();
        customerRecordsProcessor.processcontractForGuarantorAcceptanceIssueRecord(ebsContractsForGuarantorAcceptance, envName);
    }
    
    private static void processcontractForCustomerAcceptanceIssueRecord(String envName, List<Booking> ebsContractsForCustomerAcceptance) {
        CustomerRecordsProcessor customerRecordsProcessor = new CustomerRecordsProcessor();
        customerRecordsProcessor.processcontractForCustomerAcceptanceIssueRecord(ebsContractsForCustomerAcceptance, envName);
    }

    private static void processGurantorRecord(String envName, List<Booking> duplicateExternalPayer) {
        CustomerRecordsProcessor customerRecordsProcessor = new CustomerRecordsProcessor();
        customerRecordsProcessor.processGuarantorIssue(duplicateExternalPayer, envName);
    }

    private static void processCustomerRecords(String envName, List<Customer> padCustomers, List<Customer> ebsCustomers, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        CustomerRecordsProcessor customerRecordsProcessor = new CustomerRecordsProcessor();
        customerRecordsProcessor.toCheckCustomerCreatedOrSync(padCustomers, ebsCustomers, envName, statement, padRecordsAnalysisService);
    }

    private static void processEnableBillingScheduleRecords(String envName, List<Installments> padInstallments, List<Installments> ebsInstallments, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        CustomerRecordsProcessor customerRecordsProcessor = new CustomerRecordsProcessor();
        customerRecordsProcessor.verifyEnableInstallments(padInstallments, ebsInstallments, envName, statement, padRecordsAnalysisService);
    }

    private static void processDisableBillingScheduleRecords(String envName, List<Installments> padInstallments, List<Installments> ebsInstallments, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        CustomerRecordsProcessor customerRecordsProcessor = new CustomerRecordsProcessor();
        customerRecordsProcessor.verifyDisableInstallments(padInstallments, ebsInstallments, envName, statement, padRecordsAnalysisService);
    }

    private static void getEbsEnableInstallments(String envName, List<Installments> ebsInstallments) throws Exception {
        try {
            Statement se = OracleConnection.getOracleConnetion(envName);
            EbsRecordsAnalysisService ebsRecordsAnalysisService = new EbsRecordsAnalysisService();
            ebsRecordsAnalysisService.getEbsEnableInstallments(se, ebsInstallments);
        } catch (Exception var7) {
            System.out.println("Exception while getting records from ebs enable Installments: " + var7);
        } finally {
            OracleConnection.close();
        }

    }

    private static void getEbsDisableInstallments(String envName, List<Installments> ebsInstallments) throws Exception {
        try {
            Statement se = OracleConnection.getOracleConnetion(envName);
            EbsRecordsAnalysisService ebsRecordsAnalysisService = new EbsRecordsAnalysisService();
            ebsRecordsAnalysisService.getEbsDisableInstallments(se, ebsInstallments);
        } catch (Exception var7) {
            System.out.println("Exception while getting records from ebs Disable Installments: " + var7);
        } finally {
            OracleConnection.close();
        }

    }

    private static void getEbscontractForGuarantorAcceptanceIssue(String envName, List<Booking> ebsBookings, List<Booking> ebsContractOnGuarantorsAcceptance) throws Exception {
        try {
            Statement se = OracleConnection.getOracleConnetion(envName);
            EbsRecordsAnalysisService ebsRecordsAnalysisService = new EbsRecordsAnalysisService();
            ebsRecordsAnalysisService.getEbscontractForGuarantorAcceptanceIssue(se, ebsBookings, ebsContractOnGuarantorsAcceptance);
        } catch (Exception var8) {
            System.out.println("Exception while getting records from EBS Acceptance: " + var8);
        } finally {
            OracleConnection.close();
        }

    }
    
    private static void getEbscontractForCustomerAcceptanceIssue(String envName, List<Booking> ebsBookings, List<Booking> ebsContractOnCustomerAcceptance) throws Exception {
        try {
            Statement se = OracleConnection.getOracleConnetion(envName);
            EbsRecordsAnalysisService ebsRecordsAnalysisService = new EbsRecordsAnalysisService();
            ebsRecordsAnalysisService.getEbscontractForCustomerAcceptanceIssue(se, ebsBookings, ebsContractOnCustomerAcceptance);
        } catch (Exception var8) {
            System.out.println("Exception while getting records from EBS Acceptance: " + var8);
        } finally {
            OracleConnection.close();
        }

    }

    private static void getEbsContractFroGuarantors(String envName, List<Booking> padBookings, List<Booking> ebsContractOnGuarantors) throws Exception {
        try {
            Statement se = OracleConnection.getOracleConnetion(envName);
            EbsRecordsAnalysisService ebsRecordsAnalysisService = new EbsRecordsAnalysisService();
            ebsRecordsAnalysisService.getEbsContractFromPadcontractonGuarantor(se, padBookings, ebsContractOnGuarantors);
        } catch (Exception var8) {
            System.out.println("Exception while getting records from EBS Guarantor: " + var8);
        } finally {
            OracleConnection.close();
        }

    }

    private static void getEbsCustomersAndBookings(String envName, List<Customer> ebsCustomers, List<Booking> ebsBookings) throws Exception {
        try {
            Statement se = OracleConnection.getOracleConnetion(envName);
            EbsRecordsAnalysisService ebsRecordsAnalysisService = new EbsRecordsAnalysisService();
            ebsRecordsAnalysisService.getCustomers(se, ebsCustomers);
            ebsRecordsAnalysisService.getBookings(se, ebsBookings);
        } catch (Exception var8) {
            System.out.println("getEbsCustomersAndBookings Exception while getting records from EBS: " + var8);
        } finally {
            OracleConnection.close();
        }

    }

    private static void getDuplicateNoms3PContractRecord(Statement statement, PadRecordsAnalysisService padRecordsAnalysisService, List<Booking> duplicateNoms3PContract) {
        try {
            padRecordsAnalysisService.getDuplicateNoms3PContractRecord(statement, duplicateNoms3PContract);
        } catch (Exception var7) {
            System.out.println("getDuplicateNoms3PContractRecord Exception while getting records from PAD: " + var7);
        } finally {
            OracleConnection.close();
        }

    }
    
    private static void getExternalPayerIssue(Statement statement, PadRecordsAnalysisService padRecordsAnalysisService, List<Booking> duplicateExternalPayerIssue) {
        try {
            padRecordsAnalysisService.getExternalPayerIssue(statement, duplicateExternalPayerIssue);
        } catch (Exception var7) {
            System.out.println("getExternalPayerIssue Exception while getting records from PAD: " + var7);
        } finally {
            OracleConnection.close();
        }

    }

    private static void getChekedInIssueBookings(Statement statement, PadRecordsAnalysisService padRecordsAnalysisService, List<Booking> checkedInIssue) {
        try {
            padRecordsAnalysisService.getCheckedInIssue(statement, checkedInIssue);
        } catch (Exception var7) {
            System.out.println("getChekedInIssueBookings Exception while getting records from PAD: " + var7);
        } finally {
            OracleConnection.close();
        }

    }

    private static void getPadCustomersAndBookings(Statement padStatement, PadRecordsAnalysisService padRecordsAnalysisService, List<Customer> padCustomers, List<Booking> padBookings) {
        try {
            padRecordsAnalysisService.getCustomers(padStatement, padCustomers);
            padRecordsAnalysisService.getBookings(padStatement, padBookings);
        } catch (Exception var5) {
            System.out.println("getPadCustomersAndBookings Exception while getting records from PAD: " + var5);
        }

    }

    private static void getPadContractForGuarantorAcceptance(Statement padStatement, PadRecordsAnalysisService padRecordsAnalysisService, List<Booking> contractOnGuarantorsAcceptance) {
        try {
            padRecordsAnalysisService.getPadContractForGuarantorAcceptance(padStatement, contractOnGuarantorsAcceptance);
        } catch (Exception var4) {
            System.out.println(" getPadContractForGuarantorAcceptance Exception while getting records from PAD: " + var4);
        }

    }
    
    private static void getPadContractForCustomerAcceptance(Statement padStatement, PadRecordsAnalysisService padRecordsAnalysisService, List<Booking> contractOnCustomerAcceptance) {
        try {
            padRecordsAnalysisService.getPadContractForCustomerAcceptance(padStatement, contractOnCustomerAcceptance);
        } catch (Exception var4) {
            System.out.println(" getPadContractForCustomerAcceptance Exception while getting records from PAD: " + var4);
        }

    }

    private static void getPadContractFroGuarantors(Statement padStatement, PadRecordsAnalysisService padRecordsAnalysisService, List<Booking> contractOnGuarantors) {
        try {
            padRecordsAnalysisService.getcontractOnGuarantors(padStatement, contractOnGuarantors);
        } catch (Exception var4) {
            System.out.println("Exception while getting records from PAD: " + var4);
        }

    }

    private static void getPadEnableInstallmentPlan(Statement padStatement, PadRecordsAnalysisService padRecordsAnalysisService, List<Installments> padInstallments) {
        try {
            padRecordsAnalysisService.getPadEnableInstallmentPlan(padStatement, padInstallments);
        } catch (Exception var4) {
            System.out.println(" getPadEnableInstallmentPlan Exception while getting records from PAD: " + var4);
        }

    }

    private static void getPadDisableInstallmentPlan(Statement padStatement, PadRecordsAnalysisService padRecordsAnalysisService, List<Installments> padInstallments) {
        try {
            padRecordsAnalysisService.getPadDisableInstallmentPlan(padStatement, padInstallments);
        } catch (Exception var4) {
            System.out.println(" getPadDisableInstallmentPlan Exception while getting records from PAD: " + var4);
        }

    }
    
    private static void getPadUsers(Statement padStatement, PadRecordsAnalysisService padRecordsAnalysisService, List<User> padUsers) {
        try {
            padRecordsAnalysisService.getPadUsers(padStatement, padUsers);
        } catch (Exception exForUsers) {
            System.out.println("getPadUsers Exception while getting records from PAD: " + exForUsers);
        }

    }
    
    private static void getEbsUsers(String envName, List<User> ebsUsers) throws Exception {
        try {
            Statement se = OracleConnection.getOracleConnetion(envName);
            EbsRecordsAnalysisService ebsRecordsAnalysisService = new EbsRecordsAnalysisService();
        	ebsRecordsAnalysisService.getEbsUsers(se, ebsUsers);
        } catch (Exception exForUsers) {
            System.out.println("getEbsUsers Exception while getting records from EBS: " + exForUsers);
        }
        finally {
            OracleConnection.close();
        }

    }
    
    private static void processUserRecords(String envName, List<User> padUsers, List<User> ebsUsers, Statement statement) {
    	UserRecordsProcessor userRecordsProcessor = new UserRecordsProcessor();
    	userRecordsProcessor.toCheckUserCreatedOrSync(padUsers, ebsUsers, envName, statement);
    }
}
