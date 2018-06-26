import javax.servlet.http.HttpServletRequest;

public final class RequestUtility {

	private static final String key = "website";
	private static final String SITE_REGEX = "^[a-zA-Z0-9\\-.:]*$";
	
	public static String getWebsite(final HttpServletRequest request) {
		final String site = request.getHeader(key);
		if(null != site && site.matches(SITE_REGEX)){
			return site;
		} else {
			return "";
		}
	}
}
