package flim.backendcartoon.entities.DTO;

import flim.backendcartoon.entities.Episode;
import flim.backendcartoon.entities.Movie;

import java.util.List;

public class MovieDetailDTO {
    private Movie movie;
    private List<Episode> episodes;

    public MovieDetailDTO(Movie movie, List<Episode> episodes) {
        this.movie = movie;
        this.episodes = episodes;
    }

    public Movie getMovie() { return movie; }
    public List<Episode> getEpisodes() { return episodes; }
}
