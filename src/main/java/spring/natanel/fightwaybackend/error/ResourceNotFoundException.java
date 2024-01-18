package spring.natanel.fightwaybackend.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//if we throw this exception -> if default status of the controller = 404
@ToString
@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends StoreException{

    private final String resourceName; //ex: Product / item
    private final String name; //ex: id/ProductName
    private final String value;// ex: 1/Gloves

    public ResourceNotFoundException(String resourceName, String name, String value) {
        super("%s not found with %s = %s".formatted(resourceName,name,value));
        this.resourceName = name;
        this.name = name;
        this.value = value;
    }

    public ResourceNotFoundException(String resourceName, String name, long value) {
        this(resourceName, name, String.valueOf(value));
    }
}

