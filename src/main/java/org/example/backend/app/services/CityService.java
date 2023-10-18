package org.example.backend.app.services;

import org.example.backend.app.models.City;
import lombok.AccessLevel;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CityService {
    @Setter(AccessLevel.PRIVATE)
    private List<City> cityData;

    public CityService() {
        setCityData(new ArrayList<>());
        cityData.add(new City(0, "Vienna", 1900000));
        cityData.add(new City(0, "Graz", 300000));
        cityData.add(new City(0, "Linz", 190000));
    }

    public City getCityById(int id) {
        String stat = "";


        City foundCity = cityData.stream()
                .filter(city -> id == city.getId())
                .findAny()
                .orElse(null);

        return foundCity;
    }

    public List<City> getCities() {
        return cityData;
    }

    public void addCity(City city) {
        System.out.println(city.getName());
        System.out.println(city.getPopulation());

        String stmt = "INSERT INTO cities (name, population) VALUES (?,?);";
        DatabaseService dbService = new DatabaseService(); // move to constructor
        Connection connection = dbService.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setInt(2,city.getPopulation());
            preparedStatement.executeQuery();
        } catch(Exception e) {
            e.printStackTrace();
        }

        cityData.add(city);
    }

    public void removeCity(int id) {
        cityData.removeIf(city -> id == city.getId());
    }
}
