package ArangoDB;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigReader {
    private static String DBUser;
    private static String DBName;
    private static String DBPassword;
    private static String DBHost;
    private static String DBPort;
    private static String DBURL;

    public static void readConfFile() throws Exception {
        String file = System.getProperty("user.dir") + "/Postgres.conf";
        java.util.List<String> lines = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\[(.+)\\]");
        Matcher matcher;
        Stream<String> stream = Files.lines(Paths.get(file));
        lines = stream.filter(line -> !line.startsWith("#")).collect(Collectors.toList());

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("user")) {
                matcher = pattern.matcher(lines.get(i));
                if (matcher.find())
                    setDBUser(matcher.group(1));
                else
                    throw new Exception("empty user in Postgres.conf");
            }
            if (lines.get(i).startsWith("database")) {
                matcher = pattern.matcher(lines.get(i));
                if (matcher.find())
                    setDBName(matcher.group(1));
                else
                    throw new Exception("empty database name in Postgres.conf");
            }
            if (lines.get(i).startsWith("pass")) {
                matcher = pattern.matcher(lines.get(i));
                matcher.find();
                setDBPassword(matcher.group(1));
            }
            if (lines.get(i).startsWith("host")) {
                matcher = pattern.matcher(lines.get(i));
                if (matcher.find())
                    setDBHost(matcher.group(1));
                else
                    setDBHost("localhost");
            }
            if (lines.get(i).startsWith("port")) {
                matcher = pattern.matcher(lines.get(i));
                if (matcher.find())
                    setDBPort(matcher.group(1));
                else
                    setDBPort("5432");
            }
        }
        if (!formatURL()) {
            throw new Exception("Wrong Format in Postgres.conf");

        }
    }

    private static boolean formatURL() {
        setDBURL("jdbc://postgresql://" + DBHost + ":" + DBPort + "/" + DBName);
        Pattern pattern = Pattern.compile("^\\w+:\\w+:\\/{2}\\w+:\\d+\\/\\w+(?:\\W|\\w)*$");
        Matcher matcher = pattern.matcher(DBURL);
        return matcher.matches();
    }

    public static String getDBUser() {
        return DBUser;
    }

    public static void setDBUser(String DBUser) {
        ConfigReader.DBUser = DBUser;
    }

    public static String getDBName() {
        return DBName;
    }

    public static void setDBName(String DBName) {
        ConfigReader.DBName = DBName;
    }

    public static String getDBPassword() {
        return DBPassword;
    }

    public static void setDBPassword(String DBPassword) {
        ConfigReader.DBPassword = DBPassword;
    }

    public static String getDBHost() {
        return DBHost;
    }

    public static void setDBHost(String DBHost) {
        ConfigReader.DBHost = DBHost;
    }

    public static String getDBPort() {
        return DBPort;
    }

    public static void setDBPort(String DBPort) {
        ConfigReader.DBPort = DBPort;
    }

    public static String getDBURL() {
        return DBURL;
    }

    public static void setDBURL(String DBURL) {
        ConfigReader.DBURL = DBURL;
    }
}
