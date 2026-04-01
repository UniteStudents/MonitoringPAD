//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Reporting;

import Reporting.Booking;
import Reporting.PadRecordsAnalysisService;
import Reporting.SaveExcelReport;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookingRecordsProcessor {
    public BookingRecordsProcessor() {
    }

    public void processCheckedInIssueBookings(List<Booking> checkedInIssueBooking, String envName) {
        System.out.println("checked In Issue booking Records : " + checkedInIssueBooking.size());
        if(checkedInIssueBooking.size() > 0) {
            try {
				SaveExcelReport.saveCheckedIssueBooking(checkedInIssueBooking, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public void processDuplicateNoms3PContractRecords(List<Booking> duplicateNoms3PContract, String envName) {
        System.out.println("processDuplicateNoms3PContractRecords record size : " + duplicateNoms3PContract.size());
        if(duplicateNoms3PContract.size() > 0) {
            try {
				SaveExcelReport.duplicateNoms3PContractHeaderReport(duplicateNoms3PContract, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

    public void processBookings(List<Booking> padBookings, List<Booking> ebsBookings, String envName, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        List bookingCreatedInEbs = this.checkMissingBookingsFromEbs(padBookings, ebsBookings, envName, statement, padRecordsAnalysisService);
        this.checkNotSynedBookingsInPad(envName, bookingCreatedInEbs, ebsBookings, statement, padRecordsAnalysisService);
    }

    private List<Booking> checkMissingBookingsFromEbs(List<Booking> padBookings, List<Booking> ebsBookings, String envName, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        ArrayList bookingsNotCreatedInEbs = new ArrayList();
        ArrayList bookingCreatedInEbs = new ArrayList();
        bookingsNotCreatedInEbs.addAll(padBookings);
        bookingCreatedInEbs.addAll(padBookings);
        padBookings.forEach((padBooking) -> {
            ebsBookings.stream().filter((ebsbooking) -> {
                return padBooking.getBookingNumber().equals(ebsbooking.getBookingNumber());
            }).forEach((ebsBooking) -> {
                bookingsNotCreatedInEbs.remove(padBooking);
            });
        });
        bookingCreatedInEbs.removeAll(bookingsNotCreatedInEbs);
        System.out.println("created booking Records : " + padBookings.size());
        ArrayList updateListWithException = new ArrayList();
        if(bookingsNotCreatedInEbs.size() > 0) {
            try {
                padRecordsAnalysisService.updateListWithExeption(statement, updateListWithException, bookingsNotCreatedInEbs);
            } catch (Exception var10) {
                System.out.println("Exception occured while getting Exception from fallback: " + var10);
                var10.printStackTrace();
            }

            bookingsNotCreatedInEbs.forEach((createdBooking) -> {
                updateListWithException.stream().filter((ebsbooking) -> {
                    return ((Booking) createdBooking).getPadBookingId().equals(((Booking) ebsbooking).getPadBookingId());
                }).forEach((ebsbooking) -> {
                    ((Booking) createdBooking).setExceptionType(((Booking) ebsbooking).getExceptionType());
                });
            });
            System.out.println("not created booking record in ebs : " + bookingsNotCreatedInEbs.size());
            System.out.println("not created booking record in ebs : " + bookingsNotCreatedInEbs.toString());
            try {
				SaveExcelReport.saveBookingNotFoundInEBSReport(bookingsNotCreatedInEbs, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        return bookingCreatedInEbs;
    }

    private void checkNotSynedBookingsInPad(String envName, List<Booking> bookingCreatedInEbs, List<Booking> ebsBookings, Statement statement, PadRecordsAnalysisService padRecordsAnalysisService) {
        List bookingsNotSyncedInPad = (List)bookingCreatedInEbs.stream().filter((booking) -> {
            return booking.getEbsBookingId().longValue() == 0L;
        }).collect(Collectors.toList());
        System.out.println("not Synced Booking Records in PAD : " + bookingsNotSyncedInPad.size());
        bookingsNotSyncedInPad.forEach((createdBooking) -> {
            ebsBookings.stream().filter((ebsbooking) -> {
                return ((Booking) createdBooking).getBookingNumber().equals(ebsbooking.getBookingNumber());
            }).forEach((ebsbooking) -> {
                ((Booking) createdBooking).setEbsBookingId(ebsbooking.getEbsBookingId());
            });
        });
        ArrayList updateListWithException = new ArrayList();
        if(bookingsNotSyncedInPad.size() > 0) {
            try {
                padRecordsAnalysisService.updateListWithExeption(statement, updateListWithException, bookingsNotSyncedInPad);
            } catch (Exception var9) {
                System.out.println("Exception occured while getting Exception sync booking from fallback: " + var9);
                var9.printStackTrace();
            }

            bookingsNotSyncedInPad.forEach((createdBooking) -> {
                updateListWithException.stream().filter((ebsbooking) -> {
                    return ((Booking) createdBooking).getPadBookingId().equals(((Booking) ebsbooking).getPadBookingId());
                }).forEach((ebsbooking) -> {
                    ((Booking) createdBooking).setExceptionType(((Booking) ebsbooking).getExceptionType());
                });
            });
            try {
				SaveExcelReport.saveBookingEBSIdNotInPADReport(bookingsNotSyncedInPad, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

    private void checkBookingsStatusNotMatchInPad(List<Booking> ebsBookings, List<Booking> bookingCreatedInEbs, String envName) {
        ArrayList statusNotMatched = new ArrayList();
        statusNotMatched.addAll(bookingCreatedInEbs);
        bookingCreatedInEbs.forEach((padBooking) -> {
            ebsBookings.stream().filter((ebsBooking) -> {
                return padBooking.getBookingNumber().equals(ebsBooking.getBookingNumber()) && padBooking.getPadBookingStatus().equals(ebsBooking.getEbsBookingStatus());
            }).forEach((ebsbooking) -> {
                statusNotMatched.remove(padBooking);
            });
        });
        statusNotMatched.forEach((createdBooking) -> {
            ebsBookings.stream().filter((ebsbooking) -> {
                return ((Booking) createdBooking).getBookingNumber().equals(ebsbooking.getBookingNumber());
            }).forEach((ebsbooking) -> {
                ((Booking) createdBooking).setEbsBookingId(ebsbooking.getEbsBookingId());
                ((Booking) createdBooking).setEbsBookingStatus(ebsbooking.getEbsBookingStatus());
            });
        });
        if(statusNotMatched.size() > 0) {
            try {
				SaveExcelReport.saveBookingStatusNotUpdatedInPADReport(statusNotMatched, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }
}
