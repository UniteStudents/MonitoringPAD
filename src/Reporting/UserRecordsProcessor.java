package Reporting;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRecordsProcessor {
	
	public void toCheckUserCreatedOrSync(List<User> padUsers, List<User> ebsUsers, String envName, Statement statement) {
        this.checkMissingUsersFromEbs(padUsers, ebsUsers, envName, statement);
        this.checkNotSyncedUsersInPad(envName, padUsers, ebsUsers, statement);
    }

	@SuppressWarnings("all")
	private void checkNotSyncedUsersInPad(String envName, List<User> userCreatedInPad, List<User> ebsUsers,
			Statement statement) {
		
	    List<User> userSyncInPad = userCreatedInPad.stream()
	    		.filter(ebsUser -> ebsUsers.stream()
	    		        .anyMatch(padUser -> {
	    					try {
	    						return padUser.getUserId().equals(ebsUser.getUserId()) 
	    						    && padUser.getEndDate().substring(0, 10).equals(ebsUser.getEndDate())
	    						    && (padUser.getRespId().contains("52360") || padUser.getRespId().equals(ebsUser.getRespId()))
	    						    && padUser.getUserName().equals(ebsUser.getUserName()	);
	    					} catch (Exception e) {
	    						e.printStackTrace();
	    						return false;
	    					}
	    				}))
	    .collect(Collectors.toList());
	    
	    List<User> userNotSyncInPad = userCreatedInPad;
	    userNotSyncInPad.removeAll(userSyncInPad);
	    System.out.println("not sync user record in PAD : " + userNotSyncInPad.size());
	    System.out.println(" sync user record in PAD : " + userSyncInPad.size());
	    if(userNotSyncInPad.size() > 0) {
            try {
				SaveExcelReport.generateUserNotSyncInPadReport(userNotSyncInPad, envName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

	@SuppressWarnings("all")
	private List<User> checkMissingUsersFromEbs(List<User> padUsers, List<User> ebsUsers, String envName,
			Statement statement) {
		ArrayList usersNotCreatedInPad = new ArrayList();
        ArrayList userCreatedInPad = new ArrayList();
        usersNotCreatedInPad.addAll(ebsUsers);
        userCreatedInPad.addAll(ebsUsers);
        ebsUsers.forEach((ebsUser) -> {
        	padUsers.stream().filter((padUser) -> {
                return ebsUser.getUserId().equals(padUser.getUserId());
            }).forEach((padUser) -> {
                usersNotCreatedInPad.remove(ebsUser);
            });
        });
        System.out.println("created user Records in EBS : " + userCreatedInPad.size());
        userCreatedInPad.removeAll(usersNotCreatedInPad);
        System.out.println("not created user record in PAD : " + usersNotCreatedInPad.size());
        
        if(usersNotCreatedInPad.size() > 0) {
            try {
				SaveExcelReport.generateUserNotFoundInPadReport(usersNotCreatedInPad, envName);
            	//usersNotCreatedInPad.forEach(user -> System.out.println(user.toString()));
            	//userCreatedInPad.forEach(user -> System.out.println(user.toString()));
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        

        return userCreatedInPad;
	}

}
