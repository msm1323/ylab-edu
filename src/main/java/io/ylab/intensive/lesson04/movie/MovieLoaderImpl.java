package io.ylab.intensive.lesson04.movie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        try (Scanner scanner = new Scanner(Files.newInputStream(file.toPath()))) {
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] movieData = scanner.nextLine().split(";");

                Movie movie = new Movie();
                movie.setYear(movieData[0].isEmpty() ? null : Integer.parseInt(movieData[0]));
                movie.setLength(movieData[1].isEmpty() ? null : Integer.parseInt(movieData[1]));
                movie.setTitle(movieData[2].isEmpty() ? null : movieData[2]);
                movie.setSubject(movieData[3].isEmpty() ? null : movieData[3]);
                movie.setActors(movieData[4].isEmpty() ? null : movieData[4]);
                movie.setActress(movieData[5].isEmpty() ? null : movieData[5]);
                movie.setDirector(movieData[6].isEmpty() ? null : movieData[6]);
                movie.setPopularity(movieData[7].isEmpty() ? null : Integer.parseInt(movieData[7]));
                movie.setAwards(movieData[8].isEmpty() ? null : movieData[8].equals("Yes"));

                saveMovie(movie);
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }

    }

    private void saveMovie(Movie movie) throws SQLException {
        String insertQuery = "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            if (movie.getYear() != null) {
                preparedStatement.setInt(1, movie.getYear());
            } else {
                preparedStatement.setNull(1, Types.INTEGER);
            }
            if (movie.getLength() != null) {
                preparedStatement.setInt(2, movie.getLength());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }

            preparedStatement.setString(3, movie.getTitle());
            preparedStatement.setString(4, movie.getSubject());
            preparedStatement.setString(5, movie.getActors());
            preparedStatement.setString(6, movie.getActress());
            preparedStatement.setString(7, movie.getDirector());

            if (movie.getPopularity() != null) {
                preparedStatement.setInt(8, movie.getPopularity());
            } else {
                preparedStatement.setNull(8, Types.INTEGER);
            }
            if (movie.getAwards() != null) {
                preparedStatement.setBoolean(9, movie.getAwards());
            } else {
                preparedStatement.setNull(9, Types.BOOLEAN);
            }

            preparedStatement.executeUpdate();
        }
    }


}
