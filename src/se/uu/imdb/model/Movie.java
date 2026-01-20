package se.uu.imdb.model;
import java.util.*;

/**
 * Represents a movie with a title and review score.
 */
public class Movie {
    private final String title;
    private final int reviewScore;

    /**
     * Creates a new movie.
     * @param title movie title, not blank
     * @param reviewScore review score in the range of 1-5
     */
    public Movie(String title, int reviewScore) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }
        if (reviewScore < 1 || reviewScore > 5) {
            throw new IllegalArgumentException("Review score must be between 1 and 5.");
        }
        this.title = title.trim();
        this.reviewScore = reviewScore;
    }

    public String getTitle() {
        return title;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    @Override
    public String toString() {
        return "Title: " + title + "Review score: " + reviewScore + "/5";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie other)) return false;
        return reviewScore = other.reviewScore && title.equalsIgnoreCase(other.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), reviewScore);
    }
}

