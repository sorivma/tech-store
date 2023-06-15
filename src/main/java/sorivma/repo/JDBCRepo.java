package sorivma.repo;

import org.postgresql.ds.PGSimpleDataSource;
import sorivma.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCRepo {
    private Connection connection;

    private static JDBCRepo instance = null;

    public static synchronized JDBCRepo getInstance() {
        if (instance == null) {
            try {
                instance = new JDBCRepo();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        return instance;
    }

    private JDBCRepo() throws SQLException {
        final String url =
                "jdbc:postgresql://localhost:5432/tech-store";
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        connection = dataSource.getConnection(
                "postgres",
                "admin"
        );
    }

    public List<Customer> findUsers(int quantity) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT customer.name, customer.middle_name \n" +
                            "\n" +
                            "FROM customer \n" +
                            "\n" +
                            "JOIN orders ON customer.customer_id = orders.customer_id \n" +
                            "\n" +
                            "JOIN check_line ON orders.order_id = check_line.order_id \n" +
                            "\n" +
                            "WHERE check_line.quantity > ?; "
            );
            statement.setInt(1, quantity);
            ResultSet resultSet = statement.executeQuery();
            List<Customer> customers = new ArrayList<>();

            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString("name"), resultSet
                        .getString("middle_name")));
            }
            System.out.println(customers);
            return customers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
