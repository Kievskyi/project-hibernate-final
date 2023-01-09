package org.denysdudnik.handlers.impl;

import org.denysdudnik.dao.CityDao;
import org.denysdudnik.dao.CountryDao;
import org.denysdudnik.domain.City;
import org.denysdudnik.domain.Country;
import org.denysdudnik.domain.CountryLanguage;
import org.denysdudnik.handlers.Handler;
import org.denysdudnik.redis.CityCountry;
import org.denysdudnik.redis.Language;
import org.denysdudnik.service.MySQLService;
import org.denysdudnik.service.RedisClientService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataHandler implements Handler {
    private final CityDao cityDao;
    private final CountryDao countryDao;
    private final SessionFactory sessionFactory;
    private final RedisClientService redisClientService;
    private final MySQLService sqlService;

    public DataHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        cityDao = new CityDao(sessionFactory);
        countryDao = new CountryDao(sessionFactory);
        redisClientService = new RedisClientService(sessionFactory);
        sqlService = new MySQLService(sessionFactory);
    }

    @Override
    public void handle() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            List<City> allCities = fetchData();
            List<CityCountry> preparedData = transformData(allCities);
            redisClientService.pushToRedis(preparedData);

            sessionFactory.getCurrentSession().close();

            List<Integer> ids = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

            long startRedis = System.currentTimeMillis();
            redisClientService.testRedisData(ids);
            long stopRedis = System.currentTimeMillis();

            long startMysql = System.currentTimeMillis();
            sqlService.testMysqlData(ids);
            long stopMysql = System.currentTimeMillis();

            System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
            System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

            redisClientService.shutdown();
        }
    }

    private List<CityCountry> transformData(List<City> cities) {
        return cities.stream().map(city -> {
            CityCountry res = new CityCountry();
            res.setId(city.getId());
            res.setName(city.getName());
            res.setPopulation(city.getPopulation());
            res.setDistrict(city.getDistrict());

            Country country = city.getCountry();
            res.setAlternativeCountryCode(country.getAlternativeCode());
            res.setContinent(country.getContinent());
            res.setCountryCode(country.getCode());
            res.setCountryName(country.getName());
            res.setCountryPopulation(country.getPopulation());
            res.setCountryRegion(country.getRegion());
            res.setCountrySurfaceArea(country.getSurfaceArea());
            Set<CountryLanguage> countryLanguages = country.getLanguages();
            Set<Language> languages = countryLanguages.stream().map(cl -> {

                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setIsOfficial(cl.getIsOfficial());
                language.setPercentage(cl.getPercentage());

                return language;
            }).collect(Collectors.toSet());
            res.setLanguages(languages);

            return res;
        }).collect(Collectors.toList());
    }

    private List<City> fetchData() {
        List<City> allCities = new ArrayList<>();
        List<Country> countries = countryDao.getAll();

        Long totalCount = cityDao.getTotalCount();
        int step = 500;

        for (int i = 0; i < totalCount; i += step) {
            allCities.addAll(cityDao.getItems(i, step));
        }

        return allCities;
    }
}
