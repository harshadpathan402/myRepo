package com.org.api;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.sapienter.jbilling.server.metafields.DataType;
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS;
import com.sapienter.jbilling.server.order.OrderChangeWS;
import com.sapienter.jbilling.server.order.OrderWS;
import com.sapienter.jbilling.server.payment.PaymentInformationWS;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.util.api.JbillingAPI;
import com.sapienter.jbilling.server.util.api.JbillingAPIException;
import com.sapienter.jbilling.server.util.api.JbillingAPIFactory;

public class TestInvoicePDF  {
	static JbillingAPI api ;
	
	private final static String CC_AUTO_AUTHO = "autopayment.authorization";
	
	static
	{
		try {
			api = JbillingAPIFactory.getAPI("hessianApiClient");
		} catch (JbillingAPIException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		UserWS userWS = api.getUserWS(21644);
		System.out.println("userWS :: " + userWS);
		userWS.setPassword(null);
		List<PaymentInformationWS> paymentInformationWSList = userWS.getPaymentInstruments();
		PaymentInformationWS cc = paymentInformationWSList.get(0);
		MetaFieldValueWS[] metaFields = cc.getMetaFields();
		//cc.setMetaFields(metaFields);
		List<MetaFieldValueWS> list = new ArrayList<>(Arrays.asList(metaFields));
		addMetaField(list);
		cc.setMetaFields(list.toArray(new MetaFieldValueWS[list.size()]));
		paymentInformationWSList.add(cc);
		userWS.setPaymentInstruments(paymentInformationWSList);
		api.updateUser(userWS);
		userWS = api.getUserWS(21644);
		
		userWS.getAccountInfoTypeFieldsMap();
		
		System.out.println("Pranay haramkhor");
	}
	
	private static void addMetaField(List<MetaFieldValueWS> metaFields) {
		MetaFieldValueWS ws = new MetaFieldValueWS();
		ws.setFieldName(CC_AUTO_AUTHO);
		ws.setDisabled(false);
		ws.setMandatory(false);
		ws.setDataType(DataType.BOOLEAN);
		ws.setDisplayOrder(8);
		ws.setBooleanValue(Boolean.TRUE);

		metaFields.add(ws);	
	}
	
}
