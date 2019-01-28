package biologicalparkticketsystem;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Class to format the output string of the logger system
 * @author Luis Varela
 */
public class CustomLoggerFormatter extends Formatter {
    
    private static final String FORMAT = "%1$tF %1$tT | %2$-7s | %3$s%4$s%n"; // format string for printing the log record
    private final Date dat = new Date();
    
    /**
     * method to format the output string to log file
     * @return a string with the formated output string
     */
    @Override
    public String format(LogRecord record) {
        dat.setTime(record.getMillis());
        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        return String.format(FORMAT,
                             dat,
                             record.getLevel().getLocalizedName(),
                             message,
                             throwable);
    }
    
}
