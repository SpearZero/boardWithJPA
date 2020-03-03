package jpa.board.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class ParameterUtils {
	
	public static String getReqParameter(HttpServletRequest request, String param, String defaultValue) {
		String returnValue = StringUtils.isEmpty(request.getParameter(param)) ? defaultValue : request.getParameter(param);
		return returnValue;
	}
}
