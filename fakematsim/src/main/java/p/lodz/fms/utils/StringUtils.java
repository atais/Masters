package p.lodz.fms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String replaceGroup(String regex, String source,
	    int groupToReplace, String replacement) {
	return replaceGroup(regex, source, groupToReplace, 1, replacement);
    }

    public static String replaceGroup(String regex, String source,
	    int groupToReplace, int groupOccurrence, String replacement) {
	Matcher m = Pattern.compile(regex).matcher(source);
	for (int i = 0; i < groupOccurrence; i++)
	    if (!m.find())
		return source; // pattern not met, may also throw an exception
			       // here
	return new StringBuilder(source).replace(m.start(groupToReplace),
		m.end(groupToReplace), replacement).toString();
    }
}
