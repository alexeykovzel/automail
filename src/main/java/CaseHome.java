import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CaseHome {
    public static Boolean isCaseIdValidity(int caseId) {
        DBWorker.getDBConnection();
        Connection dbConnection = null;
        try {
            dbConnection = DBWorker.getDBConnection();
            Statement statement = dbConnection.createStatement();
            statement.execute("select * from cases where id = \"" + caseId + "\"");
            ResultSet rs = statement.getResultSet();
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert dbConnection != null;
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void insertCase(int id, String senderAddress, String messageId, String threadId, String subject) {
        DBWorker.getDBConnection();
        Connection dbConnection = null;
        try {
            dbConnection = DBWorker.getDBConnection();

            Statement statement = dbConnection.createStatement();
            statement.executeUpdate("insert into cases (id, sender_address, message_id, thread_id, subject) values ('"
                    + id + "', '"
                    + senderAddress + "', '"
                    + messageId + "', '"
                    + threadId + "', '"
                    + subject + "')");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert dbConnection != null;
                dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
