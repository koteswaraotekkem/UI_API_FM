package apim.ui.portal.enums;

public enum LogMessages {
	

	INVALID_lOGIN_MSG("The specified username or password was invalid."), 
	MANDATORY_FIELD_MSG("This is a mandatory field."),
	FORGOT_PWD_MSG("We'll send you an email to reset your password."),
	CHECK_YOUR_MAIL_NOTIFICATION("We've sent a message to your email account. Click the link in the email to reset your password. If you have exceeded the attempts, try after an hour."),
	EMAIL_ADDRESS_MISSMATCH_MSG("Email addresses must match.");
   
	String value;

    LogMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
