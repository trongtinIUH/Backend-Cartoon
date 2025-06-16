package flim.backendcartoon.controllers;

import flim.backendcartoon.entities.Movie;
import flim.backendcartoon.services.MovieServices;
import flim.backendcartoon.services.S3Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private S3Services s3Service;

    @Autowired
    private MovieServices movieServices;

    @PostMapping(value = "/upload-video", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadMovie(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("userId") String userId,
            @RequestParam(value = "role", required = true) String role,
            @RequestParam(value = "genres", required = false) List<String> genres,
            @RequestPart("video") MultipartFile video,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        try {
            //kiểm tra quyền  admin được phép upload video
            if( role == null || !role.equals("ADMIN")) {
                return ResponseEntity.status(403).body("Chỉ admin mới có quyền upload video");
            }
            String videoUrl = s3Service.uploadVideo(video);
            String thumbnailUrl = s3Service.uploadThumbnail(thumbnail);

            Movie movie = new Movie();
            movie.setMovieId(UUID.randomUUID().toString());
            movie.setTitle(title);
            movie.setDescription(description);
            movie.setUserId(userId);
            movie.setGenres(genres);
            movie.setCreatedAt(Instant.now().toString());
            movie.setVideoUrl(videoUrl);
            movie.setThumbnailUrl(thumbnailUrl);

            movieServices.saveMovie(movie);

            return ResponseEntity.ok(movie);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to upload movie: " + e.getMessage());
        }
    }

    @PutMapping("/{movieId}/increament-view")
    public ResponseEntity<?> incrementViewCount(
            @PathVariable String movieId) {
        try {
            Long viewCount = movieServices.increaseViewCount(movieId);
            return ResponseEntity.ok("View count incremented successfully. New view count: " + viewCount);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to increment view count: " + e.getMessage());
        }
    }

    //find all moview
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        try {
            List<Movie> movies = movieServices.findAllMovies();
            if (movies.isEmpty()) {
                return ResponseEntity.noContent().build(); // HTTP 204 nếu danh sách rỗng
            }
            return ResponseEntity.ok(movies); // HTTP 200 và trả về danh sách phim
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    //find movie by id
    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieById(
            @PathVariable String movieId) {
        try {
            Movie movie = movieServices.findMovieById(movieId);
            if (movie == null) {
                return ResponseEntity.status(404).body("Movie not found with ID: " + movieId);
            }
            return ResponseEntity.ok(movie);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to retrieve movie: " + e.getMessage());
        }
    }

    //update movie
    @PutMapping("/{movieId}")
    public ResponseEntity<?> updateMovie(
            @PathVariable String movieId,
            @RequestBody Movie updatedMovie) {
        try {
            Movie existingMovie = movieServices.findMovieById(movieId);
            if (existingMovie == null) {
                return ResponseEntity.status(404).body("Movie not found with ID: " + movieId);
            }

            // Update fields
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setDescription(updatedMovie.getDescription());
            existingMovie.setGenres(updatedMovie.getGenres());
            existingMovie.setThumbnailUrl(updatedMovie.getThumbnailUrl());
            existingMovie.setVideoUrl(updatedMovie.getVideoUrl());

            movieServices.updateMovie(existingMovie);

            return ResponseEntity.ok(existingMovie);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to update movie: " + e.getMessage());
        }
    }

    //delete many movies by ids
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMoviesByIds(
            @RequestBody List<String> movieIds) {
        try {
            movieServices.deleteMoviesByIds(movieIds);
            return ResponseEntity.ok("Movies deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to delete movies: " + e.getMessage());
        }
    }

    
}
