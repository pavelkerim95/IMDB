package se.uu.imdb.data;
import se.uu.imdb.model.Movie;
import java.io.IOException;
import java.util.*;

/**
 * Repository abstraction for movie persistence
 */
public interface MovieRepository {
    /**
     * Loads all movies from storage
     * @return list of movies
     * @throws IOException if reading fails
     */
    List<Movie> loadAll() throws IOException;

    /**
     * Saves all movies to storage
      * @param movies movies to persist
     * @throws IOException if writing fails
     */
    void saveAll(List<Movie> movies) throws IOException;
}
