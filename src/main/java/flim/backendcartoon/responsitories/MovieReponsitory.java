package flim.backendcartoon.responsitories;


import flim.backendcartoon.entities.Movie;
import flim.backendcartoon.entities.User;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MovieReponsitory {
    private final DynamoDbTable<Movie> table;

    public MovieReponsitory(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("Movie", TableSchema.fromBean(Movie.class));
    }

    public void save(Movie movie) {
        System.out.println("Saving movie to DynamoDB: " + movie);
        table.putItem(movie);
    }

    public Movie findById(String id) {
        return table.getItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public Movie findByName(String name) {
        return table.scan().items().stream()
                .filter(movie -> movie.getTitle().equals(name))
                .findFirst()
                .orElse(null);
    }

    //tìm theo thể loại
    public Movie findByGenre(String genre) {
        return table.scan().items().stream()
                .filter(movie -> movie.getGenres() != null && movie.getGenres().contains(genre))
                .findFirst()
                .orElse(null);
    }

    //xóa phim theo id
    public void deleteById(String id) {
        Movie movie = findById(id);
        if (movie != null) {
            table.deleteItem(movie);
            System.out.println("Deleted movie with ID: " + id);
        } else {
            System.out.println("Movie with ID: " + id + " not found.");
        }
    }

    //cập nhật phim
    public void update(Movie movie) {
        Movie existingMovie = findById(movie.getMovieId());
        if (existingMovie != null) {
            table.updateItem(movie);
            System.out.println("Updated movie with ID: " + movie.getMovieId());
        } else {
            System.out.println("Movie with ID: " + movie.getMovieId() + " not found.");
        }
    }

    //hàm find all movies
    public List<Movie> findAllMovies() {
        return table.scan().items().stream().collect(Collectors.toList());
    }
}
