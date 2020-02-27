package ae.dt.common.constants;

import ae.dt.common.exception.BusinessException;
import ae.dt.common.util.ErrorCodes;
import org.apache.commons.lang.StringUtils;

import java.util.EnumSet;

public class Constants {
	public static final String ENTITY_USER = "entityUser";
	public static final String ENTITY_PASSWORD = "entityPassword";

	public static final String REST_STATUS_SUCCESS = "success";
	public static final String REST_STATUS_FAIL = "fail";
	public static final String REST_STATUS_ERROR = "error";

	public static final String DT_REF = "DTREF";
	public static final int DEFAULT_REF_LENGTH = 9;

	public static final int VALID_VAL = 1;
	public static final String EN = "EN";
	public static final String AR = "AR";

	public static final String CLIENT_ID = "NEnMG6t9aYvq33btu88_fkGzaysa";
	public static final String CLIENT_SECRET = "Ywf4lO9TnZBCh_Ax7ZOVVpKuf_ga";

	public static final String USERDTO = "USERDTO";
	public static final String DT_REF_DEFAULT = "DTREF000DEFAULT";

	public static final String IMAGE_FILE = "image";

	public static final String PENDING = "PENDING";
	public static final String RECEIPT_PREFIX = "000000T";

	public static String ROSOOM_TRANS_ID = "cepgTransactionID";
	public static String FI_DATE = "fiDate";
	public static String PAYMENT_INSTRUMENT = "paymentInstrument";
	public static String SERVICE_COST = "serviceCost";
	public static String PROC_CHARGES = "processingCharges";
	public static String AMOUNT = "amount";
	public static String FI_REF_NO = "fiReferenceNumber";
	public static String FI_MESSAGE = "message";
	public static String STATUS = "cepgStatus";
	public static String PAY_SUCCESS = "SUCCESS";
	public static String PAY_FAILED = "FAILED";
	public static String PAY_PENDING = "PENDING";

	public static String SYSTEM = "SYSTEM";
	public static String YES = "Y";
	public static String NO = "N";
	public static String VESSEL_ETA = "Vessel ETA: ";
	public static String VESSEL_ATA = "Vessel ATA: ";
	public static String INVOICE_ISSUE_DATE = "Invoice Issue Date: ";
	public static String INVOICE_EXPIRY_DATE = "Invoice Expiry Date: ";
	public static final long ACTIVE_VAL = 1;
	public static final String FILE_REF = "FREF";
	public static final String INVOICE_FILE_DELIMITER = "INVOICE_FILE_DELIMITER";
	public static final String amend = "Amend";
	public static final String PAYMENT_PROOF = "PaymentProof";

	public static final String REQUESTDO_IMPORTER_SEARCH_STATUS = "NEW,CANCELLED,PARTIAL_PAYMENT";
	public static final String USER_DTO = "userDTO";
	
	public static String FINANCE_EMAILS = "FINANCE_EMAILS";

	public enum CreateAuthMessage {
		EN_SUCCESS("Login was Successful"), AR_SUCCESS("Login was Successful"),
		EN_SUCCESS_RPT("Login was already successful"), AR_SUCCESS_RPT("Login was already successful"),
		EN_EXIST("Account already exist"), AR_EXIST("Account already Exist");

		private String message;

		CreateAuthMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public static CreateAuthMessage getByFieldName(String fieldName) {
			return EnumSet.allOf(CreateAuthMessage.class).stream()
					.filter(msg -> StringUtils.equalsIgnoreCase(msg.name(), fieldName)).findFirst()
					.orElseThrow(() -> new BusinessException(ErrorCodes.INV_TYPE_CODE, "locale", "Unsupported Locale"));
		}
	}

	public enum CreateSuccessMessage {
		EN("Request Created Successfully"), AR("Request Created Successfully");

		private String message;

		CreateSuccessMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public static CreateSuccessMessage getByFieldName(String fieldName) {
			return EnumSet.allOf(CreateSuccessMessage.class).stream()
					.filter(msg -> StringUtils.equalsIgnoreCase(msg.name(), fieldName)).findFirst()
					.orElseThrow(() -> new BusinessException(ErrorCodes.INV_TYPE_CODE, "locale", "Unsupported Locale"));
		}
	}

	public enum UpdateteSuccessMessage {
		EN("Request Updated Successfully"), AR("Request Updated Successfully");

		private String message;

		UpdateteSuccessMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public static UpdateteSuccessMessage getByFieldName(String fieldName) {
			return EnumSet.allOf(UpdateteSuccessMessage.class).stream()
					.filter(msg -> StringUtils.equalsIgnoreCase(msg.name(), fieldName)).findFirst()
					.orElseThrow(() -> new BusinessException(ErrorCodes.INV_TYPE_CODE, "locale", "Unsupported Locale"));
		}
	}

	public enum BOL_STATUS {
		NEW("NEW", "NEW"), INITIATED("INITIATED", "INITIATED"), PARTIAL_PAYMENT("PARTIAL_PAYMENT", "PARTIAL_PAYMENT"),
		COMPLETED("COMPLETED", "COMPLETED"), DO_INITIATED("DO_INITIATED", "DO_INITIATED"), RETURNED("RETURNED","RETURNED"),
		DELETED("DELETED", "DELETED"), BOL_INITIATED("BOL_INITIATED", "BOL_INITIATED"), REJECTED("REJECTED","REJECTED");

		public String value;
		public String desc;

		private BOL_STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	public enum AGENT_TYPE {
		CLEARING_AGENT("C"), FREEZONE_LICENCE("F"), IMPORTER("I"), FRIEGHT_FORWARDER("FF"), SHIPPING_AGENT("A");

		public String value;

		private AGENT_TYPE(String value) {
			this.value = value;
		}
	}
	public enum USER_TYPE {
		C("C","CLEARING AGENT"),F("F","FREEZONE LICENCE"), I("I","IMPORTER"), FF("FF","FRIEGHT FORWARDER"), A("A","SHIPPING AGENT");

		public String value;
		public String desc;

		private USER_TYPE(String desc, String value) {
			this.value = value;
			this.desc = desc;
		}
	}

	public enum APPROVER {
		FRIEGHT_FORWARDER("FF"), SHIPPING_AGENT("A");

		public String value;

		private APPROVER(String value) {
			this.value = value;
		}
	}

	public enum INITIATOR {
		CLEARING_AGENT("C"), FREEZONE_LICENCE("F"), IMPORTER("I");

		public String value;

		private INITIATOR(String value) {
			this.value = value;
		}
	}

	public enum DO_STATUS {
		NEW("NEW", "NEW"), INITIATED("INITIATED", "INITIATED"), PARTIAL_PAYMENT("PARTIAL_PAYMENT", "PARTIAL_PAYMENT"),
		PENDING_FOR_APPROVAL("PENDING_FOR_APPROVAL", "PENDING_FOR_APPROVAL"), RETURNED("RETURNED", "RETURNED"),
		PAYMENT_PENDING_WITH_IMPORTER("PAYMENT_PENDING_WITH_IMPORTER", "PAYMENT_PENDING_WITH_IMPORTER"),
		DO_INITIATED("DO_INITIATED", "DO_INITIATED"),COMPLETED("COMPLETED","COMPLETED"),
		PENDING_BOL_APPROVAL("PENDING_BOL_APPROVAL", "PENDING_BOL_APPROVAL"),REJECTED("REJECTED","REJECTED");

		public String value;
		public String desc;

		private DO_STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	public enum BOLINVOICE_STATUS {
		PAYMENT_PENDING("PAYMENT_PENDING", "PAYMENT_PENDING"), PAYMENT_FAILED("PAYMENT_FAILED", "PAYMENT_FAILED"),
		PAYMENT_PENDING_WITH_IMPORTER("PAYMENT_PENDING_WITH_IMPORTER", "PAYMENT_PENDING_WITH_IMPORTER"),
		PAYMENT_SUCCESS("PAYMENT_SUCCESS", "PAYMENT_SUCCESS"), DELETED("DELETED", "DELETED"),
		PAYMENT_NOT_INITIATED("PAYMENT_NOT_INITIATED", "PAYMENT_NOT_INITIATED");

		public String value;
		public String desc;

		private BOLINVOICE_STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	public enum PAYMENTLOG_STATUS {
		PENDING("PENDING", "PENDING"), FAILED("FAILED", "FAILED"),
		PAYMENT_PENDING_WITH_IMPORTER("PAYMENT_PENDING_WITH_IMPORTER", "PAYMENT_PENDING_WITH_IMPORTER"),
		SUCCESS("SUCCESS", "SUCCESS");

		public String value;
		public String desc;

		private PAYMENTLOG_STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	public enum PAYMENT_TYPE {
		M("MASTER_CARD"), V("VISA");

		public String value;

		private PAYMENT_TYPE(String value) {
			this.value = value;
		}
	}

	public enum REQUEST_BOL_INVOICE_TYPE {
		BOL_REQUEST("BOL REQUEST"), INVOICE_REQUEST("INVOICE REQUEST");

		public String value;

		private REQUEST_BOL_INVOICE_TYPE(String value) {
			this.value = value;
		}
	}

	public enum DOAUTH_DOCS {
		AUTHORIZATION_LETTER("AUTHORIZATION_LETTER"), EMIRATES_ID("EMIRATES_ID"), BL_COPY("BL_COPY"),
		OTHER_DOCUMENT("OTHER_DOCUMENT");

		public String value;

		private DOAUTH_DOCS(String value) {
			this.value = value;
		}
	}

	public enum SEARCH_PARAM {
		// BOLNO("bol","BOL NUMBER"),INVOICENO("invoiceNumber","INVOICE NUMBER"),
		// BOLNO_INVOICENO("bol_invoiceNumber","BOL NUMBER and INVOICE NUMBER"),
		// BOLNO_IMPORTERCODE("bol_importerName","BOL NUMBER and IMPORTER NAME"),
		NO_RULE("NORULE", "NO RULE"),
		NO_SHIPPING_AGENT("NO_SHIPPING_AGENT", "On boarding of Shipping Agent For DO not done.");

		public String value;
		public String desc;

		private SEARCH_PARAM(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	public enum INVOICE_TYPE {
		FREIGHT_CHARGES("Freight Invoice", ""), LOCAL_CHARGES("Local Charges Invoice", ""),
		DETENNTION_CHARGES("Detention Invoice", ""), OTHER("Other", "");

		public String value;
		public String displayName;

		private INVOICE_TYPE(String value, String displayName) {
			this.value = value;
			this.displayName = displayName;
		}
	}

	public enum CONFIRM_MESSAGES {
		BOL_UPLOAD_SUCCESS("BOL uploaded Successfully. "), BOL_UPLOAD_FAILED("BOL uploading Failed.");

		public String value;
		public String displayName;

		private CONFIRM_MESSAGES(String displayName) {

			this.displayName = displayName;
		}
	}

	public enum PAYMENT_METHOD {
		ONLINE_PAYMENT("ONLINE_PAYMENT", "Online Payment", "ONLINE", "O"),
		CREDIT("CREDIT PAYMENT", "Credit Payment", "CREDIT PAYMENT", "C"),
		UPLOAD_PAYMENT_PROOF("UPLOAD_PAYMENT_PROOF", " Upload Payment Proof", "PAYMENT PROOF", "U");

		public String value;
		public String displayName;
		public String name;
		public String id;

		private PAYMENT_METHOD(String value, String displayName, String name, String id) {
			this.value = value;
			this.displayName = displayName;
			this.name = name;
			this.id = id;
		}
	}

	public enum EMAIL_TYPE {
		DO_REQUEST_SA("DO_REQUEST_SA"), DO_REQUEST_BOL("DO_REQUEST_BOL"), DO_REQUEST_DO("DO_REQUEST_DO"),
		DO_REQUEST_PARTIAL_PAYMENT("DO_REQUEST_PARTIAL_PAYMENT"), DO_REQUEST("DO_REQUEST"),REJECT_RETURN_DO("REJECT_RETURN_DO"),
		NEW_DO_REQUEST("NEW_DO_REQUEST"), BOL_REQUEST("BOL_REQUEST"), INVOICE_REQUEST("INVOICE_REQUEST"),
		UPLOAD_BOL("UPLOAD_BOL"), UPLOAD_BOL_SA("UPLOAD_BOL_SA"), UPLOAD_BOL_CONSIGNEE("UPLOAD_BOL_CONSIGNEE"), NOTIFY_PAYMENT_OF_PROOF("NOTIFY_PAYMENT_OF_PROOF");

		public String value;

		private EMAIL_TYPE(String value) {
			this.value = value;

		}
	}
	public enum BOL_TYPE {
		OBL("OBL"), EBL("EBL");

		public String value;

		private BOL_TYPE(String value) {
			this.value = value;
		}
	}
}
