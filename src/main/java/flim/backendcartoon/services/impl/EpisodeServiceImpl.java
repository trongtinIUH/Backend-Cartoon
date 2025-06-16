package flim.backendcartoon.services.impl;

import flim.backendcartoon.entities.Episode;
import flim.backendcartoon.responsitories.EpisodeRepository;
import flim.backendcartoon.services.EpisodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @Override
    public void saveEpisode(Episode episode) {
        episodeRepository.save(episode);
    }

    @Override
    public List<Episode> findEpisodesByMovieId(String movieId) {
        return episodeRepository.findByMovieId(movieId);
    }
}