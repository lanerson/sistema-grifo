package Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;


import java.io.IOException;

@Service
public class UploadImageService {

    @Autowired
    private ImageRepository imageRepository;

    public ImageUpload saveImage(MultipartFile file) throws IOException {
        ImageUpload image = new ImageUpload();
        image.setImageName(file.getOriginalFilename());
        image.setDataImage(file.getBytes());
        return imageRepository.save(image);
    }

    public ImageUpload getImage(UUID id) {
        return imageRepository.findById(id).orElse(null);
    }

    public List<UUID> getAllImageIds() {
        return imageRepository.findAll().stream()
                .map(ImageUpload::getImageId)  // Assumindo que você tem um método getId() na entidade Image
                .collect(Collectors.toList());
    }

}
