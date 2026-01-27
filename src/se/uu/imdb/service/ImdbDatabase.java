package se.uu.imdb.service;
import se.uu.imdb.data.MovieRepository;
import se.uu.imdb.model.Movie;
import java.io.*;
import java.util.*;

/**
 * Application logic for working with movies in IMDB.
 */
public class ImdbDatabase {
    private final MovieRepository repository;
    private final List<Movie> movies;

    /**
     * Creates a database and loads movies from repository.
     * @param repository repository implementation
     * @throws IOException if loading fails
     */
    public ImdbDatabase(MovieRepository repository) throws IOException {
        this.repository = repository;
        this.movies = new ArrayList<>(repository.loadAll());
    }

    /**
     * Adds a movie and persists changes.
     * @param title movie title
     * @param score review score (1-5)
     * @throws IOException if saving fails
     */
    public void addMovie(String title, int score) throws IOException {
        movies.add(new Movie(title, score));
        save();
    }

    /**
     * Finds movies where title contains keyword (case-insensitive)
     * @param keyword search term
     * @return matching movies sorted by title
     */
    public List<Movie> searchByTitleKeyword(String keyword) {
        String k = (keyword == null) ? "" : keyword.trim().toLowerCase();
        List<Movie> result = new ArrayList<>();
        for(Movie m : movies) {
            if(m.getTitle().toLowerCase().contains(k)) {
                result.add(m);
            }
        }
        result.sort(Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    /**
     * Finds movies with review score >= minimumScore
     * @param minimumScore minimum score (1-5)
     * @return matching movies sorted by score desc then title
     */
    public List<Movie> searchByMinimumScore(int minimumScore) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getReviewScore() >= minimumScore) {
                result.add(m);
            }
        }
        result.sort(Comparator.comparingInt(Movie::getReviewScore).reversed().thenComparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    private void save() throws IOException {
        repository.saveAll(movies);
    }
}
