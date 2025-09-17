// 代码生成时间: 2025-09-17 12:46:12
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// LogParser class to parse log files
public class LogParser {

    // Regular expression to match log entries
    private static final Pattern LOG_PATTERN = Pattern.compile("^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2},\s+(\w+),\s+(\d+),\s+(\d+).*");

    /**
     * Parses the log file line by line and prints out relevant information.
     *
     * @param filePath the path to the log file
     */
    public void parseLogFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = LOG_PATTERN.matcher(line);
                if (matcher.find()) {
                    String timestamp = matcher.group(1);
                    String level = matcher.group(2);
                    String threadId = matcher.group(3);
                    String message = line.substring(matcher.end()).trim();
                    System.out.printf("Timestamp: %s, Level: %s, Thread ID: %s, Message: %s%n", timestamp, level, threadId, message);
                } else {
                    System.out.println("Skipping unrecognized line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java LogParser <log file path>");
            System.exit(1);
        }

        LogParser logParser = new LogParser();
        logParser.parseLogFile(args[0]);
    }
}