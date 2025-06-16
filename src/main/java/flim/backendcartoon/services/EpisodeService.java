package flim.backendcartoon.services;

import flim.backendcartoon.entities.Episode;

import java.util.List;

public interface EpisodeService {
    void saveEpisode(Episode episode);
    List<Episode> findEpisodesByMovieId(String movieId);
}
