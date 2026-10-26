import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://183.96.148.41:3306/tutor", "tutor", "tutorp");
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM member WHERE age > 10");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM member WHERE age > ?");
        preparedStatement.setLong(1, 10L);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Member member = new Member(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getInt("age"));
            log.info("회원 {}", member);
        }
        connection.close();
    }
}
