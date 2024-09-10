package Image;

import org.springframework.hateoas.RepresentationModel;
import java.util.UUID;

public class ImageIdDTO extends RepresentationModel<ImageIdDTO> {
    private UUID id;

    public ImageIdDTO(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}