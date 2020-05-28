import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) {
        final String user = "postgres";
        final String password = "mysecretpassword";
        final String url = "dao:postgresql://localhost:5432/itmo";

        try {
            final Connection conn = DriverManager.getConnection(url, user, password);
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM item;")) {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String title = resultSet.getString("Title");
                    String artist = resultSet.getString("Artist");
                    Date date = resultSet.getDate("ReleaseDate");
                    double listPrice = resultSet.getDouble("ListPrice");
                    System.out.printf("%s: %s, %s, %s\n", artist, title, date, listPrice);

                }
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
