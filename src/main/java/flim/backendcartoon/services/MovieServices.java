package flim.backendcartoon.services;

import flim.backendcartoon.entities.Movie;

import java.util.List;

public interface MovieServices {
    void saveMovie(Movie movie);
    Movie findMovieById(String id);
    Movie findMovieByName(String name);
    List<Movie> findMoviesByGenre(String genre);
    void deleteMovieById(String id);
    void updateMovie(Movie movie);
    List<Movie> findAllMovies();
    Long increaseViewCount(String movieId);
    //xoa nhiu movie
    void deleteMoviesByIds(List<String> ids);
    //ti phim theo thể loại
    List<Movie> findAllMoviesByGenre(String genre);
    // tìm phim với title chứa từ khóa
    List<Movie> findMoviesByTitleContaining(String title);
}
