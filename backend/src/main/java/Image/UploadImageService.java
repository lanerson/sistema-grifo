package Image;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

}
