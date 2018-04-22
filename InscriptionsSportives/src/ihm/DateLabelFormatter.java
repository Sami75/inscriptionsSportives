package ihm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {

	private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    
	@Override
	public Object stringToValue(String date) throws ParseException {
		// TODO Auto-generated method stub
        return dateFormatter.parseObject(date);
	}

	@Override
	public String valueToString(Object date) throws ParseException {
		// TODO Auto-generated method stub
		 if (date != null) {
	            Calendar cal = (Calendar) date;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	}

}
