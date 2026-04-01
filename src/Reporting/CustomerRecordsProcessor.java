//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Reporting;

import Reporting.Booking;
import Reporting.Customer;
import Reporting.Installments;
import Reporting.PadRecordsAnalysisService;
import Reporting.SaveExcelReport;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomerRecordsProcessor {
    public CustomerRecordsProcessor() {
    }

    public void toCheckCustomerCreatedOrSync(List<Customer> padCustomers, List<Customer> ebsCustomers, String envName, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        List customerCreatedInEbs = this.checkMissingCustomersFromEbs(padCustomers, ebsCustomers, envName, statement, padRecordsAnalysisService);
        this.checkNotSynedCustomersInPad(envName, customerCreatedInEbs, ebsCustomers, statement, padRecordsAnalysisService);
    }

    public void verifyEnableInstallments(List<Installments> padInstallments, List<Installments> ebsInstallments, String envName, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        ArrayList installmentNotPresentInPad = new ArrayList();
        installmentNotPresentInPad.addAll(ebsInstallments);
        ebsInstallments.forEach((ebsInstallment) -> {
            padInstallments.stream().filter((padInstallment) -> {
                return padInstallment.getPadLookupCode().equals(ebsInstallment.getEbsLookupCode());
            }).forEach((padInstallment) -> {
                installmentNotPresentInPad.remove(ebsInstallment);
            });
        });
        System.out.println("installment not enable in PAD : " + installmentNotPresentInPad.size());
        if(installmentNotPresentInPad.size() > 0) {
            try {
				SaveExcelReport.generateEnableInstalmentReport(installmentNotPresentInPad, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

    public void verifyDisableInstallments(List<Installments> padInstallments, List<Installments> ebsInstallments, String envName, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        ArrayList installmentNotPresentInPad = new ArrayList();
        installmentNotPresentInPad.addAll(ebsInstallments);
        ebsInstallments.forEach((ebsInstallment) -> {
            padInstallments.stream().filter((padInstallment) -> {
                return padInstallment.getPadLookupCode().equals(ebsInstallment.getEbsLookupCode());
            }).forEach((padInstallment) -> {
                installmentNotPresentInPad.remove(ebsInstallment);
            });
        });
        System.out.println("installment not disable in PAD : " + installmentNotPresentInPad.size());
        if(installmentNotPresentInPad.size() > 0) {
            try {
				SaveExcelReport.generateDisableInstalmentReport(installmentNotPresentInPad, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

    public void processExternalPayerissue(List<Booking> duplicateExternalPayer, String envName) {
        System.out.println("processExternalPayerissue record size : " + duplicateExternalPayer.size());
        if(duplicateExternalPayer.size() > 0) {
            try {
				SaveExcelReport.duplicateExternalReport(duplicateExternalPayer, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

    public void processcontractForGuarantorAcceptanceIssueRecord(List<Booking> guarantor, String envName) {
        System.out.println("processcontractForGuarantorAcceptanceIssueRecord record size : " + guarantor.size());
        if(guarantor.size() > 0) {
            try {
				SaveExcelReport.processcontractForGuarantorAcceptanceIssueRecord(guarantor, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }
    
    public void processcontractForCustomerAcceptanceIssueRecord(List<Booking> customer, String envName) {
        System.out.println("processcontractForCustomerAcceptanceIssueRecord record size : " + customer.size());
        if(customer.size() > 0) {
            try {
				SaveExcelReport.processcontractForCustomerAcceptanceIssueRecord(customer, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

    public void processGuarantorIssue(List<Booking> guarantor, String envName) {
        System.out.println("processGuarantorIssue record size : " + guarantor.size());
        if(guarantor.size() > 0) {
            try {
				SaveExcelReport.missingGuarantorReportInEs(guarantor, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

    private void checkNotSynedCustomersInPad(String envName, List<Customer> customerCreatedInEbs, List<Customer> ebsCustomers, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        List customersNotSyncedInPad = (List)customerCreatedInEbs.stream().filter((customer) -> {
            return customer.getEbsId().longValue() == 0L;
        }).collect(Collectors.toList());
        System.out.println("not Synced Customer Records in PAD : " + customersNotSyncedInPad.size());
        customersNotSyncedInPad.forEach((createdCustomer) -> {
            ebsCustomers.stream().filter((ebsCustomer) -> {
                return ((Customer) createdCustomer).getCustomerNumber().equals(ebsCustomer.getCustomerNumber());
            }).forEach((ebsCustomer) -> {
                ((Customer) createdCustomer).setEbsId(ebsCustomer.getEbsId());
            });
        });
        ArrayList updateListWithException = new ArrayList();
        if(customersNotSyncedInPad.size() > 0) {
            try {
                padRecordsAnalysisService.updateListWithExeptionCustomer(statement, updateListWithException, customersNotSyncedInPad);
            } catch (Exception var9) {
                System.out.println("Exception occured while getting customer Exception from fallback: " + var9);
                var9.printStackTrace();
            }

            customersNotSyncedInPad.forEach((createdBooking) -> {
                updateListWithException.stream().filter((ebsbooking) -> {
                    return ((Customer) createdBooking).getId().equals(((Customer) ebsbooking).getId());
                }).forEach((ebsbooking) -> {
                    ((Customer) createdBooking).setExceptionType(((Customer) ebsbooking).getExceptionType());
                });
            });
            try {
				SaveExcelReport.generateCustomerNotSyncedInPadReport(customersNotSyncedInPad, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

    private List<Customer> checkMissingCustomersFromEbs(List<Customer> padCustomers, List<Customer> ebsCustomers, String envName, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        ArrayList customersNotCreatedInEbs = new ArrayList();
        ArrayList customerCreatedInEbs = new ArrayList();
        customersNotCreatedInEbs.addAll(padCustomers);
        customerCreatedInEbs.addAll(padCustomers);
        padCustomers.forEach((padCustomer) -> {
            ebsCustomers.stream().filter((ebsCustomer) -> {
                return padCustomer.getCustomerNumber().equals(ebsCustomer.getCustomerNumber());
            }).forEach((ebsCustomer) -> {
                customersNotCreatedInEbs.remove(padCustomer);
            });
        });
        System.out.println("created customer Records : " + customerCreatedInEbs.size());
        customerCreatedInEbs.removeAll(customersNotCreatedInEbs);
        System.out.println("not created customer record in ebs : " + customersNotCreatedInEbs.size());
        System.out.println("not created customer record in ebs : " + customersNotCreatedInEbs.toString());
        ArrayList updateListWithException = new ArrayList();
        if(customersNotCreatedInEbs.size() > 0) {
            try {
                padRecordsAnalysisService.updateListWithExeptionCustomer(statement, updateListWithException, customersNotCreatedInEbs);
            } catch (Exception var10) {
                System.out.println("Exception occured while getting customer Exception from fallback: " + var10);
                var10.printStackTrace();
            }

            customersNotCreatedInEbs.forEach((createdBooking) -> {
                updateListWithException.stream().filter((ebsbooking) -> {
                    return ((Customer) createdBooking).getId().equals(((Customer) ebsbooking).getId());
                }).forEach((ebsbooking) -> {
                    ((Customer) createdBooking).setExceptionType(((Customer) ebsbooking).getExceptionType());
                });
            });
            try {
				SaveExcelReport.generateCustomerNotFoundInEbsReport(customersNotCreatedInEbs, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        return customerCreatedInEbs;
    }
}
