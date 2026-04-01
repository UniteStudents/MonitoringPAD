//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Reporting;

import Reporting.Booking;
import Reporting.Customer;
import Reporting.Installments;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaveExcelReport {
    private static ResourceBundle rb = ResourceBundle.getBundle("resources/filePath");
    private static final String CUSTOMER_FILE_HEADER = "PAD_PARTY_ID, PARTY_NUMBER, PARTY_NAME, CATEGORY_CODE , PAD_CREATION_DATE, EXCEPTION";
    private static final String ADDITONAL_NOT_SYNCED_CUSTOMER_FILE_HEADER = "EBS_PARTY_ID";
    private static final String ADDITONAL_NOT_SYNCED_BOOKING_FILE_HEADER = "EBS_CONTRACT_ID";
    private static final String ADDITONAL_NOT_SYNCED_BOOKING_STATUS_FILE_HEADER = "EBS_CONTRACT_STATUS";
    private static final String MISSING_GUARANTOR_HEADER = "EBS_CONTRACT_ID";
    private static final String BOOKING_FILE_HEADER = "PAD_CONTRACT_ID, CONTRACT_NUMBER, PAD_CONTRACT_STATUS, PAD_CREATION_DATE, EXCEPTION";
    private static final String EXTERNAL_PAYER_HEADER = "EXTERNAL_PAYER_ID";
    private static final String CHECKED_IN_HEADER = "CONTRACT_NUMBER";
    private static final String ENABLE_INSTALLMENT_HEADER = "MISSING_EBS_ENABLE_LOOKUP_CODE";
    private static final String DISABLE_INSTALLMENT_HEADER = "MISSING_EBS_DISABLE_LOOKUP_CODE";
    private static final String ENABLE_INSTALLMENT_SHEET_NAME = "MissingEnableInstallmentInPAD";
    private static final String DISABLE_INSTALLMENT_SHEET_NAME = "MissingDisableInstallmentInPAD";
    private static final String CUSTOMER_NOT_FOUND_SHEET_NAME = "CustomersNotFoundInEBS";
    private static final String CUSTOMER_NOT_SYNC_SHEET_NAME = "CustomersNotSyncedInPAD";
    private static final String BOOKING_NOT_FOUND_SHEET_NAME = "BookingsNotFoundInEBS";
    private static final String CHECKED_IN_ISSUE = "CheckedInIssues";
    private static final String EXTERNAL_PAYER_ISSUE = "DuplicateExternalPayerIssue";
    private static final String GUARANTOR_ACCEPTANCE_ISSUE = "GuarantorAcceptanceMissingInEbs";
    private static final String GUARANTOR_MISSING_ISSUE = "GuarantorMissingInEbs";
    private static final String BOOKING_NOT_SYNC_SHEET_NAME = "BookingsNotSyncedInPAD";
    private static final String BOOKING_STATUS_NOT_SYNC_SHEET_NAME = "BookingStatusNotUpdatedInPAD";
    private static final String DUPLICATE_NOMS3P_CONTRACT_IN_HEADERS = "DuplicateNoms3PContractHeader";

    public SaveExcelReport() {
    }
    
    public static void generateUserNotFoundInPadReport(List<User> users, String envName) throws Throwable {
        System.out.println("generateUserNotFoundInPadReport - Users list size : " + users.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "UsersNotFoundInPAD";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createUserReport(newSheet, users);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }

            System.out.println("Successfully created users not found in PAD report");
        } catch (Exception var17) {
            System.out.println("Exception while generatng missing users in PAD report: " + var17);
            var17.printStackTrace();
        }

    }
    
    public static void generateUserNotSyncInPadReport(List<User> users, String envName) throws Throwable {
        System.out.println("generateUserNotSyncInPadReport - Users list size : " + users.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "UsersNotSyncInPAD";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createUserSyncReport(newSheet, users);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }

            System.out.println("Successfully created users not sync in PAD report");
        } catch (Exception var17) {
            System.out.println("Exception while generatng sync users in PAD report: " + var17);
            var17.printStackTrace();
        }

    }
    
    public static void generateCustomerNotFoundInEbsReport(List<Customer> customers, String envName) throws Throwable {
        System.out.println("generateCustomerNotFoundInEbsReport - customers list size : " + customers.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "CustomersNotFoundInEBS";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createCustomerReport(newSheet, customers);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }

            System.out.println("Successffully created cusomer report");
        } catch (Exception var17) {
            System.out.println("Exception while generatng missng customers in EBS report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void generateEnableInstalmentReport(List<Installments> installments, String envName) throws Throwable {
        System.out.println("generateEnableInstalmentReport - installments list size : " + installments.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "MissingEnableInstallmentInPAD";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createEnableInstallmentReport(newSheet, installments);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }

            System.out.println("Successffully created Missing Enable Instalment report");
        } catch (Exception var17) {
            System.out.println("Exception while generatng Missing Enable Instalment report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void generateDisableInstalmentReport(List<Installments> installments, String envName) throws Throwable {
        System.out.println("generateDisableInstalmentReport - installments list size : " + installments.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "MissingDisableInstallmentInPAD";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createDisableInstallmentReport(newSheet, installments);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }

            System.out.println("Successffully created Missing Disable Instalment report");
        } catch (Exception var17) {
            System.out.println("Exception while generatng Missing Disable Instalment report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void generateCustomerNotSyncedInPadReport(List<Customer> customers, String envName) throws Throwable {
        System.out.println("generateCustomerNotSyncedInPadReport - customers list size : " + customers.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "CustomersNotSyncedInPAD";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createCustomerReport(newSheet, customers);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng missng customers in EBS report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void duplicateExternalReport(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("duplicateExternalReport - list size  " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "DuplicateExternalPayerIssue";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createExternalPayer(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng duplicate external payer report: " + var17);
            var17.printStackTrace();
        }

    }
    
    public static void duplicateNoms3PContractHeaderReport(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("duplicateNoms3PContractHeaderReport - list size  " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, DUPLICATE_NOMS3P_CONTRACT_IN_HEADERS, filePath);
            newSheet = workbook.createSheet(DUPLICATE_NOMS3P_CONTRACT_IN_HEADERS);
            createDuplicateNoms3PSheet(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng duplicate Noms3P Contract Header report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void processcontractForGuarantorAcceptanceIssueRecord(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("missingGuarantorReportInEs - list size  " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "GuarantorAcceptanceMissingInEbs";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createGuarantorSheet(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng missing guarantor report: " + var17);
            var17.printStackTrace();
        }

    }
    
    public static void processcontractForCustomerAcceptanceIssueRecord(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("missingCustomerReportInEs - list size  " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "CustomerAcceptanceMissingInEbs";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createCustomerSheet(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng missing customer report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void missingGuarantorReportInEs(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("missingGuarantorReportInEs - list size  " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "GuarantorMissingInEbs";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createGuarantorSheet(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng missing guarantor report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void saveBookingNotFoundInEBSReport(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("saveBookingNotFoundInEBSReport - Booking list size : " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "BookingsNotFoundInEBS";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createBookingReport(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng missng customers in EBS report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void saveCheckedIssueBooking(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("saveCheckedIssueBooking - Booking list size : " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "CheckedInIssues";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createCheckInReport(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng missng customers in EBS report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void saveBookingEBSIdNotInPADReport(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("saveBookingEBSIdNotInPADReport - Booking list size : " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "BookingsNotSyncedInPAD";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createBookingReport(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng missng customers in EBS report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void saveBookingStatusNotUpdatedInPADReport(List<Booking> bookings, String envName) throws Throwable {
        System.out.println("saveBookingStatusNotUpdatedInPADReport - Booking list size : " + bookings.size());
        XSSFWorkbook workbook = null;
        XSSFSheet newSheet = null;
        String filePath = getFilePathWithTimeStamp(envName);

        try {
            String e = "BookingStatusNotUpdatedInPAD";
            workbook = checkIfWorkbookExistAndRemoveOldTab(workbook, e, filePath);
            newSheet = workbook.createSheet(e);
            createBookingReport(newSheet, bookings);
            Throwable var6 = null;
            Object var7 = null;

            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);

                try {
                    workbook.write(outputStream);
                    workbook.close();
                } finally {
                    if(outputStream != null) {
                        outputStream.close();
                    }

                }
            } catch (Throwable var16) {
                if(var6 == null) {
                    var6 = var16;
                } else if(var6 != var16) {
                    var6.addSuppressed(var16);
                }

                throw var6;
            }
        } catch (Exception var17) {
            System.out.println("Exception while generatng missng customers in EBS report: " + var17);
            var17.printStackTrace();
        }

    }

    public static void createUserReport(XSSFSheet newSheet, List<User> users) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        String[] var9;
        int var8 = (var9 = "USER_ID, USER_NAME, CREATION_DATE, LAST_UPDATE_DATE , RESPONSIBILITY_ID".split(",")).length;

        Cell cell;
        for(int var7 = 0; var7 < var8; ++var7) {
            String user = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(user);
        }


        Iterator var13 = users.iterator();

        while(var13.hasNext()) {
            User var12 = (User)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getUserId()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getUserName()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getCreateDate()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getLastUpdateDate()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getRespId()));
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }
    
    public static void createUserSyncReport(XSSFSheet newSheet, List<User> users) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        String[] var9;
        int var8 = (var9 = "USER_ID, USER_NAME, CREATION_DATE".split(",")).length;

        Cell cell;
        for(int var7 = 0; var7 < var8; ++var7) {
            String user = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(user);
        }


        Iterator var13 = users.iterator();

        while(var13.hasNext()) {
            User var12 = (User)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getUserId()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getUserName()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getCreateDate()));
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }
    
    public static void createCustomerReport(XSSFSheet newSheet, List<Customer> customers) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        String[] var9;
        int var8 = (var9 = "PAD_PARTY_ID, PARTY_NUMBER, PARTY_NAME, CATEGORY_CODE , PAD_CREATION_DATE, EXCEPTION".split(",")).length;

        Cell cell;
        for(int var7 = 0; var7 < var8; ++var7) {
            String customer = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(customer);
        }

        if(((Customer)customers.get(0)).getEbsId() != null && ((Customer)customers.get(0)).getEbsId().longValue() > 0L) {
            cell = row.createCell(columnCount++);
            cell.setCellValue("EBS_PARTY_ID");
        }

        Iterator var13 = customers.iterator();

        while(var13.hasNext()) {
            Customer var12 = (Customer)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getId()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getCustomerNumber()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getFirstName()) + " " + var12.getMiddleName() + " " + var12.getLastName());
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getCategoryCode()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getCreation_date()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getExceptionType()));
            if(var12.getEbsId() != null && var12.getEbsId().longValue() > 0L) {
                cell = row.createCell(columnCount++);
                cell.setCellValue(String.valueOf(var12.getEbsId()));
            }
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }

    public static void createEnableInstallmentReport(XSSFSheet newSheet, List<Installments> installments) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        String[] var9;
        int var8 = (var9 = "MISSING_EBS_ENABLE_LOOKUP_CODE".split(",")).length;

        Cell cell;
        for(int var7 = 0; var7 < var8; ++var7) {
            String installment = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(installment);
        }

        Iterator var13 = installments.iterator();

        while(var13.hasNext()) {
            Installments var12 = (Installments)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getEbsLookupCode()));
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }

    public static void createDisableInstallmentReport(XSSFSheet newSheet, List<Installments> installments) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        String[] var9;
        int var8 = (var9 = "MISSING_EBS_DISABLE_LOOKUP_CODE".split(",")).length;

        Cell cell;
        for(int var7 = 0; var7 < var8; ++var7) {
            String installment = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(installment);
        }

        Iterator var13 = installments.iterator();

        while(var13.hasNext()) {
            Installments var12 = (Installments)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getEbsLookupCode()));
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }

    public static void createBookingReport(XSSFSheet newSheet, List<Booking> bookings) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        Cell cell = null;
        String[] var9;
        int var8 = (var9 = "PAD_CONTRACT_ID, CONTRACT_NUMBER, PAD_CONTRACT_STATUS, PAD_CREATION_DATE, EXCEPTION".split(",")).length;

        for(int var7 = 0; var7 < var8; ++var7) {
            String booking = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(booking);
        }

        if(((Booking)bookings.get(0)).getEbsBookingId() != null && ((Booking)bookings.get(0)).getEbsBookingId().longValue() > 0L) {
            cell = row.createCell(columnCount++);
            cell.setCellValue("EBS_CONTRACT_ID");
        }

        if(((Booking)bookings.get(0)).getEbsBookingStatus() != null) {
            cell = row.createCell(columnCount++);
            cell.setCellValue("EBS_CONTRACT_STATUS");
        }

        Iterator var13 = bookings.iterator();

        while(var13.hasNext()) {
            Booking var12 = (Booking)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getPadBookingId()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(var12.getBookingNumber().toString());
            cell = row.createCell(columnCount++);
            cell.setCellValue(var12.getPadBookingStatus().toString());
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getCreation_date()));
            cell = row.createCell(columnCount++);
            cell.setCellValue(String.valueOf(var12.getExceptionType()));
            if(var12.getEbsBookingId() != null && var12.getEbsBookingId().longValue() > 0L) {
                cell = row.createCell(columnCount++);
                cell.setCellValue(String.valueOf(var12.getEbsBookingId()));
            }

            if(var12.getEbsBookingStatus() != null) {
                cell = row.createCell(columnCount++);
                cell.setCellValue(String.valueOf(var12.getEbsBookingStatus()));
            }
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }

    public static void createCheckInReport(XSSFSheet newSheet, List<Booking> bookings) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        Cell cell = null;
        String[] var9;
        int var8 = (var9 = "CONTRACT_NUMBER".split(",")).length;

        for(int var7 = 0; var7 < var8; ++var7) {
            String booking = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(booking);
        }

        Iterator var13 = bookings.iterator();

        while(var13.hasNext()) {
            Booking var12 = (Booking)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(var12.getBookingNumber().toString());
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }

    public static void createExternalPayer(XSSFSheet newSheet, List<Booking> bookings) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        Cell cell = null;
        String[] var9;
        int var8 = (var9 = "EXTERNAL_PAYER_ID".split(",")).length;

        for(int var7 = 0; var7 < var8; ++var7) {
            String booking = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(booking);
        }

        Iterator var13 = bookings.iterator();

        while(var13.hasNext()) {
            Booking var12 = (Booking)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getPadBookingId()));
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }
    
    public static void createDuplicateNoms3PSheet(XSSFSheet newSheet, List<Booking> bookings) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        Cell cell = null;
        String[] var9;
        int var8 = (var9 = "CONTRACT_NUMBER".split(",")).length;

        for(int var7 = 0; var7 < var8; ++var7) {
            String booking = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(booking);
        }

        Iterator var13 = bookings.iterator();

        while(var13.hasNext()) {
            Booking var12 = (Booking)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getNoms3PContractNumber()));
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }

    public static void createGuarantorSheet(XSSFSheet newSheet, List<Booking> bookings) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        Cell cell = null;
        String[] var9;
        int var8 = (var9 = "EBS_CONTRACT_ID".split(",")).length;

        for(int var7 = 0; var7 < var8; ++var7) {
            String booking = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(booking);
        }

        Iterator var13 = bookings.iterator();

        while(var13.hasNext()) {
            Booking var12 = (Booking)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getEbsBookingId()));
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }
    
    public static void createCustomerSheet(XSSFSheet newSheet, List<Booking> bookings) throws IOException {
        byte rowCount = 0;
        int var10 = rowCount + 1;
        XSSFRow row = newSheet.createRow(rowCount);
        int columnCount = 0;
        Cell cell = null;
        String[] var9;
        int var8 = (var9 = "EBS_CONTRACT_ID".split(",")).length;

        for(int var7 = 0; var7 < var8; ++var7) {
            String booking = var9[var7];
            cell = row.createCell(columnCount++);
            cell.setCellValue(booking);
        }

        Iterator var13 = bookings.iterator();

        while(var13.hasNext()) {
            Booking var12 = (Booking)var13.next();
            row = newSheet.createRow(var10++);
            byte var11 = 0;
            columnCount = var11 + 1;
            cell = row.createCell(var11);
            cell.setCellValue(String.valueOf(var12.getEbsBookingId()));
        }

        IntStream.range(0, columnCount).forEach((nbr) -> {
            newSheet.autoSizeColumn(nbr);
        });
    }

    public static XSSFWorkbook checkIfWorkbookExistAndRemoveOldTab(XSSFWorkbook workbook, String sheetName, String filePath) throws IOException {
        File file = null;
        file = new File(filePath);
        if(file.exists()) {
            workbook = (XSSFWorkbook)WorkbookFactory.create(new FileInputStream(file));
        } else {
            workbook = new XSSFWorkbook();
        }

        for(int i = workbook.getNumberOfSheets() - 1; i >= 0; --i) {
            XSSFSheet tmpSheet = workbook.getSheetAt(i);
            String currentsheetname = tmpSheet.getSheetName();
            if(currentsheetname.equalsIgnoreCase(sheetName)) {
                workbook.removeSheetAt(i);
            }
        }

        return workbook;
    }

    public static String getFilePathWithTimeStamp(String envName) {
        String filePath = null;
        filePath = rb.getString("filesLocation");
        new SimpleDateFormat("ddMMyyyykkmm");
        String fileNameWithTimeStamp = filePath + "Reports/ReportFile_" + envName + ".xlsx";
        return fileNameWithTimeStamp;
    }

    public static void moveFile() {
        String filePathCurrent = rb.getString("filesLocation");
        File myfolder = new File(filePathCurrent + "/Reports");
        String fileNameWithTimeStamp = null;
        File[] file_array = myfolder.listFiles();

        for(int i = 0; i < file_array.length; ++i) {
            try {
                if(file_array[i].isFile()) {
                    fileNameWithTimeStamp = file_array[i].getName();
                    String e = rb.getString("filesLocation") + "/Reports/" + fileNameWithTimeStamp;
                    String filePathDestination = rb.getString("archiveFileLocation") + "/Archive/" + fileNameWithTimeStamp;
                    if(fileNameWithTimeStamp != null) {
                        try {
                            Files.move(Paths.get(e, new String[0]), Paths.get(filePathDestination, new String[0]), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                            System.out.println("File moved successfully");
                        } catch (Exception var8) {
                            System.out.println(var8);
                            System.out.println("Failed to move the file");
                        }
                    }
                }

                System.out.println("All Files move successfully");
            } catch (Exception var9) {
                System.out.println("No Old Files to Move");
            }
        }

    }

    public static void checkFolder() {
        String filePathCurrent = rb.getString("filesLocation") + "/Reports";
        String filePathDestination = rb.getString("archiveFileLocation") + "/Archive";
        File reportfolder = new File(filePathCurrent);
        File archivefolder = new File(filePathDestination);
        if(!reportfolder.exists()) {
            reportfolder.mkdir();
            System.out.println("Report Folder Created");
        } else {
            System.out.println("Report Folder Found");
        }

        if(!archivefolder.exists()) {
            archivefolder.mkdir();
            System.out.println("Archive Folder Created");
        } else {
            System.out.println("Archive Folder Found");
        }

    }

    public static void checkFile() {
        checkFolder();
        moveFile();
    }

    public static enum ObjectType {
        CUSTOMER,
        BOOKING;

        private ObjectType() {
        }
    }
}
