
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClazzController {

	@Autowired private AbstractClazz abstractClazz;

	@RequestMapping(value = "/productFinder", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> productFinder(Model model,
			@RequestParam(value = "g", required = true) String groupURL,
			@RequestParam(value = "a", required = false) long[] attr,
			@RequestParam(value = "l", required = false) long[] locs,
			@RequestParam(value = "p", required = false) List<String> priceRanges,
			@RequestParam(value = "r", required = false) List<String> reviews,
			@RequestParam(value = "i", required = false) List<String> avail,
			@RequestParam(value = "n", required = false) List<Boolean> newArrival,
			@RequestParam(value = "per", required = false) Integer per,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "page", required = false) Integer page, HttpServletRequest request) {


		String result = null;

		try {
			result = abstractClazz.getResults(groupURL, RequestUtility.getWebsite(request), arrayToList(attr), priceRanges, reviews, avail, newArrival, arrayToList(locs));
		} catch (Exception e) {
				//
		}
		if (null != result) {
			return new ResponseEntity<String>(result, getResponseHeaders(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private HttpHeaders getResponseHeaders() {
		final HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return responseHeaders;
	}
	
	private List<Long> arrayToList(final long[] arr) {
		final List<Long> list = new ArrayList<Long>();
		if (null != arr && arr.length > 0) {
			list.addAll(Arrays.asList(ArrayUtils.toObject(arr)));
		}
		return list;
	}
}
