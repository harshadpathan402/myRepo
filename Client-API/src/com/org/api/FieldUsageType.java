package com.org.api;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.sapienter.jbilling.server.metafields.DataType;

public enum FieldUsageType {
	ORGANIZATION {
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("Dummy@ORGANIZATION", dataType);
		}
	},
    ADDRESS1{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("U.S.A rt", dataType);
		}
	},
    ADDRESS2{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("U.S.A rt", dataType);
		}
	},
    CITY{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("New York", dataType);
		}
	},
    STATE_PROVINCE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("U.S.A", dataType);
		}
	},
    POSTAL_CODE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234", dataType);
		}
	},
    COUNTRY_CODE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234", dataType);
		}
	},

    FIRST_NAME{
		@Override
		public Object getDummyValue(DataType dataType) {
			return UpdateUser.LIST_OF_USER_FIRST_NAME.get(new Random().nextInt(UpdateUser.LIST_OF_USER_FIRST_NAME.size()));
		}
	},
    LAST_NAME{
		@Override
		public Object getDummyValue(DataType dataType) {
			return UpdateUser.LIST_OF_USER_LAST_NAME.get(new Random().nextInt(UpdateUser.LIST_OF_USER_LAST_NAME.size()));
		}
	},
	INITIAL{
		@Override
		public Object getDummyValue(DataType dataType) {
			return null;
		}
	},
    TITLE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return null;
		}
	},

    PHONE_COUNTRY_CODE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234", dataType);
		}
	},
    PHONE_AREA_CODE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234", dataType);
		}
	},
    PHONE_NUMBER {
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234567890", dataType);
		}
	},

    FAX_COUNTRY_CODE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234", dataType);
		}
	},
    FAX_AREA_CODE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234", dataType);
		}
	},
    FAX_NUMBER{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234", dataType);
		}
	},

    EMAIL{
		@Override
		public Object getDummyValue(DataType dataType) {
			return "abcd@xyz.com";
		}
	},
    
    BANK_NAME{
		@Override
		public Object getDummyValue(DataType dataType) {
			return "Dummy Bank";
		}
	},
    BANK_ACCOUNT_NUMBER{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("1234567801", dataType);
		}
	},
    BANK_ROUTING_NUMBER{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("111111118", dataType);
		}
	},
    BANK_ACCOUNT_TYPE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return "Savings";
		}
	},
	BANK_ACCOUNT_NUMBER_ENCRYPTED {
		@Override
		public Object getDummyValue(DataType dataType) {
			return null;
		}
	},
    
    DATE {
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue(new Date(), dataType);
		}
	},
    CHEQUE_NUMBER {
		@Override
		public Object getDummyValue(DataType dataType) {
			return "21212112312";
		}
	},

	PAYMENT_ID {
		@Override
		public Object getDummyValue(DataType dataType) {
			return null;
		}
	},
    PAYMENT_CARD_NUMBER{
		@Override
		public Object getDummyValue(DataType dataType) {
			return convertValue("4556019404727265", dataType);
		}
	},
	GATEWAY_KEY{
		@Override
		public Object getDummyValue(DataType dataType) {
			return null;
		}
	},
    CC_TYPE{
		@Override
		public Object getDummyValue(DataType dataType) {
			return "Visa";
		}
	},
   TRANSACTION_ID{
		@Override
		public Object getDummyValue(DataType dataType) {
			return null;
		}
	},
   AUTO_PAYMENT_LIMIT{
		@Override
		public Object getDummyValue(DataType dataType) {
			return null;
		}
	},
    BILLING_EMAIL{
		@Override
		public Object getDummyValue(DataType dataType) {
			return "abcd@xyz.com";
		}
	},
    AUTO_PAYMENT_AUTHORIZATION{
		@Override
		public Object getDummyValue(DataType dataType) {
			return null;
		}
	};
	
	public abstract Object getDummyValue(DataType dataType);
	
	
	private static Object convertValue(Object value, DataType dataType) {
			String val = value.toString();
		if(DataType.STRING==dataType) {
			value = val;
		}  else if(DataType.INTEGER==dataType) {
			value = Integer.valueOf(val);
		} else if(DataType.BOOLEAN==dataType) {
			try {
				value = Boolean.valueOf(val);
			}catch(Exception ex) {
				value = false;
			}
		} else if(DataType.DECIMAL==dataType) {
			value = BigDecimal.ZERO;
		} else if(DataType.DATE==dataType) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 2);
			value = cal.getTime();
		}
		
		return value;
	}
	
	public static List<String> exculdedUsageTypes() {
		return Arrays.asList("AUTO_PAYMENT_AUTHORIZATION","AUTO_PAYMENT_LIMIT","TRANSACTION_ID","PAYMENT_ID","BANK_ACCOUNT_NUMBER_ENCRYPTED","TITLE","INITIAL");
	}
	
}
