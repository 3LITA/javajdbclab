package ru.dao.postgres;

import ru.dao.ItemDAO;
import ru.dao.MusicItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class PostgresDriver implements ItemDAO {
    private final String url;
    private final Properties properties;

    private Connection connection;

    public PostgresDriver(String database, int port, String host, String username, String password) {
        url = "jdbc:postgresql://" +
                host + ":" +
                port + "/" +
                database;
        properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
    }

    public void initConnection() {
        System.out.println("Connecting PostgreSQL JDBC Driver...");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found.");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver successfully connected.");

        System.out.println("Connecting to postgresql database...");
        try {
            connection = DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            System.out.println("Connection Failed.");
            e.printStackTrace();
            return;
        }
        System.out.println(connection == null? "Connection failed." : "Successfully connected to database.");
    }

    public MusicItem searchById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE item_id = (?);")) {
            statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("Title");
                String artist = resultSet.getString("Artist");
                Date date = resultSet.getDate("ReleaseDate");
                double listPrice = resultSet.getDouble("ListPrice");
                double price = resultSet.getDouble("price");
                int version = resultSet.getInt("version");

                statement.close();
                return new MusicItem(id, title, artist, date, listPrice, price, version);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<MusicItem> searchByKeyword(String keyword) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE title LIKE (?);")) {
            statement.setString(1, "%" + keyword + "%");
            final ResultSet resultSet = statement.executeQuery();
            ArrayList<MusicItem> queryResult = getAllFromResultSet(resultSet);
            statement.close();
            return queryResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void create(MusicItem musicItem) {
        String sql = "INSERT INTO item(title, artist, listprice, price, version)" +
                "VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, musicItem.getTitle());
            statement.setString(2, musicItem.getArtist());
            statement.setDouble(3, musicItem.getListPrice());
            statement.setDouble(4, musicItem.getPrice());
            statement.setInt(5, musicItem.getVersion());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(String title, String artist, java.util.Date date, double listPrice, double price, int version) {
        MusicItem musicItem = new MusicItem(title, artist, date, listPrice, price, version);
        this.create(musicItem);
    }

    public ArrayList<MusicItem> getAll() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM item")) {
            final ResultSet resultSet = statement.executeQuery();
            ArrayList<MusicItem> queryResult = getAllFromResultSet(resultSet);
            statement.close();
            return queryResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<MusicItem> getAllFromResultSet(ResultSet resultSet) {
        try {
            ArrayList<MusicItem> queryResult = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("item_id");
                String title = resultSet.getString("Title");
                String artist = resultSet.getString("Artist");
                Date date = resultSet.getDate("ReleaseDate");
                double listPrice = resultSet.getDouble("ListPrice");
                double price = resultSet.getDouble("price");
                int version = resultSet.getInt("version");

                queryResult.add(new MusicItem(id, title, artist, date, listPrice, price, version));
            }
            return queryResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Unable to close connection");
            e.printStackTrace();
        }
    }

}
