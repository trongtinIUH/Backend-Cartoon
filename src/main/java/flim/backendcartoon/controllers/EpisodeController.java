package flim.backendcartoon.controllers;


import flim.backendcartoon.entities.Episode;
import flim.backendcartoon.services.EpisodeService;
import flim.backendcartoon.services.S3Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/episodes")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;
    @Autowired
    private S3Services s3Services;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadEpisode(
            @RequestParam("movieId") String movieId,
            @RequestParam("title") String title,
            @RequestParam("episodeNumber") Integer episodeNumber,
            @RequestPart("video") MultipartFile video
    ) {
        try {
            String videoUrl = s3Services.uploadVideo(video);

            Episode episode = new Episode();
            episode.setEpisodeId(UUID.randomUUID().toString());
            episode.setMovieId(movieId);
            episode.setTitle(title);
            episode.setEpisodeNumber(episodeNumber);
            episode.setVideoUrl(videoUrl);
            episode.setCreatedAt(Instant.now().toString());

            episodeService.saveEpisode(episode);

            return ResponseEntity.ok(episode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to upload episode: " + e.getMessage());
        }
    }


    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getEpisodesByMovie(@PathVariable String movieId) {
        List<Episode> episodes = episodeService.findEpisodesByMovieId(movieId);
        return ResponseEntity.ok(episodes);
    }

    //countEpisodesByMovieId
    @GetMapping("/count/{movieId}")
    public ResponseEntity<?> countEpisodesByMovieId(@PathVariable String movieId) {
        int count = episodeService.countEpisodesByMovieId(movieId);
        return ResponseEntity.ok(Map.of("count", count)); // ✅ bọc trong object JSON
    }
}
