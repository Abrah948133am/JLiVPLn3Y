// 代码生成时间: 2025-09-21 22:33:25
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ImageResizer Controller
 * Handles image resizing functionality.
 */
public class ImageResizer extends Controller {

    /**
     * Resizes all images in the specified directory to the specified dimensions.
     * @param sourceDirectory The directory containing the images to resize.
     * @param targetDirectory The directory where the resized images will be saved.
     * @param width The new width for the images.
     * @param height The new height for the images.
     * @return A Result indicating the success or failure of the operation.
     */
    public static Result resizeImages(String sourceDirectory, String targetDirectory, int width, int height) {
        try {
            Path sourcePath = Paths.get(sourceDirectory);
            Path targetPath = Paths.get(targetDirectory);

            // Ensure source and target directories exist
            if (!Files.exists(sourcePath) || !Files.isDirectory(sourcePath)) {
                return badRequest("Source directory does not exist or is not a directory.");
            }
            if (!Files.exists(targetPath) || !Files.isDirectory(targetPath)) {
                Files.createDirectories(targetPath);
            }

            // Process each image file in the source directory
            List<File> imageFiles = listImageFiles(sourcePath);
            for (File imageFile : imageFiles) {
                BufferedImage originalImage = ImageIO.read(imageFile);
                BufferedImage resizedImage = resizeImage(originalImage, width, height);
                saveResizedImage(resizedImage, targetPath.resolve(imageFile.getName()));
            }

            return ok("Images resized successfully.");
        } catch (IOException e) {
            return internalServerError("Error resizing images: " + e.getMessage());
        }
    }

    /**
     * Lists all image files in the given directory.
     * @param directory The directory to list image files from.
     * @return A list of image files.
     */
    private static List<File> listImageFiles(Path directory) throws IOException {
        return Files.walk(directory)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith(".png"))
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    /**
     * Resizes an image to the specified dimensions.
     * @param originalImage The original image to resize.
     * @param width The new width for the image.
     * @param height The new height for the image.
     * @return The resized image.
     */
    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        // Create a new BufferedImage with the specified dimensions
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());

        // Draw the original image onto the new BufferedImage, scaling it to fit the new dimensions
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

        // Dispose of the graphics context to free up system resources
        resizedImage.getGraphics().dispose();

        return resizedImage;
    }

    /**
     * Saves a resized image to the specified file path.
     * @param resizedImage The resized image to save.
     * @param filePath The file path where the image will be saved.
     * @throws IOException If an I/O error occurs while saving the image.
     */
    private static void saveResizedImage(BufferedImage resizedImage, Path filePath) throws IOException {
        ImageIO.write(resizedImage, "png", filePath.toFile());
    }
}