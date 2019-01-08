/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author goldspy98
 */
public class CustomLoggerFormatter extends Formatter {
    
    // format string for printing the log record
    private static final String format = "%1$tF %1$tT | %2$-7s | %3$s%4$s%n";
    private final Date dat = new Date();

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
        return String.format(format,
                             dat,
                             record.getLevel().getLocalizedName(),
                             message,
                             throwable);
    }
    
}
