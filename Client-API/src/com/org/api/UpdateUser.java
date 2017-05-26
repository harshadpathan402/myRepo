package com.org.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.sapienter.jbilling.server.metafields.DataType;
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS;
import com.sapienter.jbilling.server.metafields.MetaFieldWS;
import com.sapienter.jbilling.server.user.AccountTypeWS;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.util.api.JbillingAPI;
import com.sapienter.jbilling.server.util.api.JbillingAPIException;
import com.sapienter.jbilling.server.util.api.JbillingAPIFactory;

public class UpdateUser {
	
	public static final File FILE_OF_USER_ID = new File("../Client-API/src/resources/userList.csv");
	public static final File FILE_FOR_SUCCESSFULL_USER = new File("../Client-API/src/resources/successfull_user.csv");
	public static final File FILE_FOR_UNSUCCESSFULL_USER = new File("../Client-API/src/resources/unsuccessfull_user.csv");
	public static final File FILE_FOR_USER_FIRST_NAME = new File("../Client-API/src/resources/FirstName.csv");
	public static final File FILE_FOR_USER_LAST_NAME = new File("../Client-API/src/resources/LastName.csv");
	public static final List<String> LIST_OF_USER_FIRST_NAME = populateValuesFromFile(FILE_FOR_USER_FIRST_NAME, true);
	public static final List<String> LIST_OF_USER_LAST_NAME = populateValuesFromFile(FILE_FOR_USER_LAST_NAME,true);
	public static final List<String> LIST_OF_USER_ID = populateValuesFromFile(FILE_OF_USER_ID, false);
	public static final List<String> LIST_OF_UPDATED_USER = getDoneUserList();
	public static final List<String> LIST_OF_SYSTEM_USER_ID = Arrays.asList("2188","108101","108102","108103","108104","108105","108106","108106","64","61","62","72","60","63");
	private static final Map<Integer, Map<Integer, List<MetaFieldWS>>> accountTypeIntotypeMetafieldMap  = 
			new ConcurrentHashMap<Integer, Map<Integer, List<MetaFieldWS>>>();

	public static void populateAccountTypeInfoMetaFieldMap() {
		try {
			JbillingAPI api = JbillingAPIFactory.getAPI("hessianApiClient");
			
			Map<Integer, List<MetaFieldWS>> infoMetaFieldMap = new HashMap<Integer, List<MetaFieldWS>>();
			for(AccountTypeWS accountType : api.getAllAccountTypes()) {
				for(Integer infoTypeId: accountType.getInformationTypeIds()) {
					if(infoTypeId == 25) {
					for(MetaFieldWS metaField: api.getAccountInformationType(infoTypeId).getMetaFields()) {
						List<MetaFieldWS> metaFields = infoMetaFieldMap.get(infoTypeId);
						if(null==metaFields) {
							metaFields = new ArrayList<MetaFieldWS>();
							infoMetaFieldMap.put(infoTypeId, metaFields);
						}
						metaFields.add(metaField);
					}
					}
					accountTypeIntotypeMetafieldMap.put(accountType.getId(), infoMetaFieldMap);
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}

	static <T> List<List<T>> chopped(List<T> list, final int L) {
	    List<List<T>> parts = new ArrayList<List<T>>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}

	
	public static void main(String[] args) throws JbillingAPIException, IOException {
		if(null!=LIST_OF_USER_ID && !LIST_OF_USER_ID.isEmpty()) {
		try {
			populateAccountTypeInfoMetaFieldMap();
			BigDecimal batch = BigDecimal.valueOf(LIST_OF_USER_ID.size()).divide(BigDecimal.valueOf(4)).setScale(0, BigDecimal.ROUND_UP);
			List<List<String>> userIds = chopped(LIST_OF_USER_ID, batch.intValue());

			ExecutorService service = Executors.newFixedThreadPool(4);
			List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();

			for(final List<String> userIdSubList: userIds) {
				Future<Boolean> future = service.submit(new Callable<Boolean>() {
					public Boolean call() throws Exception {
						updateUserWithDummyValue(userIdSubList);
						return true;
					}
				});
				futures.add(future);
			}

			for(Future<Boolean> future: futures) {
				future.get();
			}

			service.shutdown();
			//updateUserWithDummyValue(Arrays.asList("11642"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		}
		
		
	}

	public static void writeUserInFile(File file, String userId) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(userId+"\n");
			writer.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static List<String> getDoneUserList() {
		List<String> ids = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(FILE_FOR_SUCCESSFULL_USER));
			String userId;
			while ((userId = in.readLine()) != null) {
				ids.add(userId.trim());
			}
			in.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return ids;

	}

	public static void updateUserWithDummyValue(List<String> userIds) throws Exception{
		try {
			System.out.println(" updateUserWithDummyValue :: ");
			JbillingAPI api = JbillingAPIFactory.getAPI("hessianApiClient");
			long id = Thread.currentThread().getId();
			for(String userId: userIds) {
				if(!LIST_OF_UPDATED_USER.contains(userId) && !LIST_OF_SYSTEM_USER_ID.contains(userId)) {
					UserWS user =api.getUserWS(Integer.valueOf(userId.trim()));
					Integer accountTypeId = user.getAccountTypeId();
					System.out.println("user :: " + user);
					Object firstName = FieldUsageType.FIRST_NAME.getDummyValue(DataType.STRING);
					Object lastName = FieldUsageType.LAST_NAME.getDummyValue(DataType.STRING);
					user.setUserName(firstName +  " "+ lastName+" - "+id);
					
					List<MetaFieldValueWS> values = new ArrayList<>();
					accountTypeIntotypeMetafieldMap.get(accountTypeId)
												   .entrySet()
												   .stream()
												   .forEach(fieldNameEntry -> {
													   fieldNameEntry.getValue()
													   				 .stream()
													   				 .forEach(fieldName -> {
													   					 MetaFieldValueWS value = new MetaFieldValueWS(fieldName.getName(), 
													   							 fieldNameEntry.getKey(), fieldName.getDataType(), fieldName.isMandatory(),getMetaFieldDummyValue(fieldName.getName(), user.getAccountTypeId()));
													   					 values.add(value);
													   				 });
												   });
					
					MetaFieldValueWS[] fieldValueWSs =  user.getMetaFields();
					if(fieldValueWSs!=null && fieldValueWSs.length!=0) {
						for(MetaFieldValueWS metaFieldValue : user.getMetaFields()) {
							if(metaFieldValue.getFieldName().equalsIgnoreCase("First Name")) {
								metaFieldValue.setValue(firstName);
							} else if(metaFieldValue.getFieldName().equalsIgnoreCase("Last Name")) {
								metaFieldValue.setValue(lastName);
							} else if(metaFieldValue.getFieldName().equalsIgnoreCase("Subscriber URI")) {
								metaFieldValue.setValue("N/A");
							} else {
								Object value = getMetaFieldDummyValue(metaFieldValue.getFieldName(), user.getAccountTypeId());
								if(null!=value) {
									metaFieldValue.setValue(value);
								}
							}
						}
					} 
						// 	Updating Payment Instruments of User
					if(null!=user.getPaymentInstruments() && !user.getPaymentInstruments().isEmpty()) {
						for(MetaFieldValueWS paymentMetaField: user.getPaymentInstruments().get(0).getMetaFields()) {
							if(paymentMetaField.getFieldName().equals("Card Holder Name")) {
								paymentMetaField.setStringValue(firstName.toString());
							} else if(paymentMetaField.getFieldName().equals("Card Number")) {
								paymentMetaField.setStringValue("4556019404727265");
							} else if(paymentMetaField.getFieldName().equals("Expiry Date")) {
								paymentMetaField.setStringValue("01/2020");
							} else if(paymentMetaField.getFieldName().equals("Card Type")) {
								paymentMetaField.setStringValue("Visa");
							} if(paymentMetaField.getFieldName().equals("cc.gateway.key")) {
								paymentMetaField.setStringValue("");
							}

						}
					}
					try {
						user.setCustomerNotes(null);
						api.updateUser(user);
						writeUserInFile(FILE_FOR_SUCCESSFULL_USER, userId);
					} catch(Exception ex) {
						writeUserInFile(FILE_FOR_UNSUCCESSFULL_USER, userId);
						ex.printStackTrace();
					}
				} 
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally{
			System.out.println("Thread Name -> "+Thread.currentThread().getName() +" Is Done");
		}
	}

	public static Object getMetaFieldDummyValue(String fieldName, Integer accountTypeId)  {
		Object dummyValue=null;
		for(Entry<Integer, List<MetaFieldWS>> infoMetaFieldEntry: accountTypeIntotypeMetafieldMap.get(accountTypeId).entrySet()) {
			for(MetaFieldWS metaField: infoMetaFieldEntry.getValue()) {
				if(metaField.getName().equalsIgnoreCase(fieldName) && !FieldUsageType.exculdedUsageTypes().contains(metaField.getFieldUsage().name())) {
					dummyValue = FieldUsageType.valueOf(metaField.getFieldUsage().name()).getDummyValue(metaField.getDataType());
					break;
				}
			}
		}
		return dummyValue;
	}

	public static List<String> populateValuesFromFile(File file, boolean split) {
		try {
			List<String> container = new ArrayList<String>();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String value=null;
			while((value=reader.readLine())!=null) {
				if(split) {
					value = value.trim().split(",")[0];
				} else {
					value = value.trim();
				}
				container.add(value);
			}
			reader.close();
			return container;
		} catch(Exception ex) {
			ex.printStackTrace();
			return Collections.emptyList();
		}

	}

}
