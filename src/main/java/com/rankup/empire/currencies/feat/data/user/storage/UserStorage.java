package com.rankup.empire.currencies.feat.data.user.storage;

import com.rankup.empire.currencies.feat.database.MySQLProvider;
import lombok.val;
import com.rankup.empire.currencies.CurrenciesPlugin;
import com.rankup.empire.currencies.feat.data.ranking.Ranking;
import com.rankup.empire.currencies.feat.data.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserStorage {

    private MySQLProvider service = CurrenciesPlugin.getInstance().getMySQLProvider();

    public void insert(User user) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `empire_currencies` VALUES(?,?,?);")) {
                statement.setString(1, user.getUuid().toString());
                statement.setString(2, user.getUsername());
                statement.setDouble(3, user.getCoins());
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(User user) {

        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `empire_currencies` SET coins=? WHERE user_id=?;")) {
                statement.setDouble(1, user.getCoins());
                statement.setString(2, user.getUuid().toString());
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void delete(User user) {
    }

    public User find(String id) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `empire_currencies` WHERE user_id=?;")) {
                statement.setString(1, id);
                try (ResultSet set = statement.executeQuery()) {
                    if (!set.next()) return null;

                    val uuid = UUID.fromString(set.getString("user_id"));
                    val name = set.getString("user_name");
                    val coins = set.getDouble("coins");


                    return User.builder().uuid(uuid).username(name).coins(coins).build();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public List<Ranking> findAll() {
        List<Ranking> userList = new ArrayList<>();

        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `empire_currencies`;")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        val uuid = UUID.fromString(resultSet.getString("user_id"));
                        val name = resultSet.getString("user_name");

                        val coins = resultSet.getDouble("coins");

                        userList.add(Ranking.builder()
                                .uuid(uuid)
                                .username(name)
                                .coins(coins)
                                .build());

                    }
                    return userList;
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return userList;
    }
}