package flim.backendcartoon.init;

import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import flim.backendcartoon.entities.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;

@Component
public class DynamoDBInitializer implements CommandLineRunner {
    private final DynamoDbEnhancedClient enhancedClient;

    public DynamoDBInitializer(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @Override
    public void run(String... args) throws Exception {
        createTableIfNotExists(User.class, "User");

    }

    private <T> void createTableIfNotExists(Class<T> clazz, String tableName) {
        try {
            enhancedClient.table(tableName, TableSchema.fromBean(clazz))
                    .createTable(CreateTableEnhancedRequest.builder()
                            // Bạn có thể thêm các thông số như provisioned throughput hoặc sử dụng on-demand capacity nếu cần
                            .build());
            System.out.println("Table '" + tableName + "' created.");
        } catch (ResourceInUseException e) {
            System.out.println("Table '" + tableName + "' already exists.");
        } catch (Exception e) {
            System.err.println("Failed to create table '" + tableName + "': " + e.getMessage());
        }
    }
}
