import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {
    public static void compareAndCopyMissedFiles(String sourceDirectoryPath, String destinationDirectoryPath) throws IOException {
        List<File> sourceFiles = getFilesRecursively(new File(sourceDirectoryPath));
        List<File> destinationFiles = getFilesRecursively(new File(destinationDirectoryPath));

        for (File sourceFile : sourceFiles) {
            boolean fileExistsInDestination = false;
            for (File destinationFile : destinationFiles) {
                if (sourceFile.getName().equals(destinationFile.getName())) {
                    if (sourceFile.getParentFile().getAbsolutePath().contains(destinationFile.getParentFile().getAbsolutePath())) {
                        // Source file exists in a subdirectory of the destination directory - do not copy
                        fileExistsInDestination = true;
                        break;
                    }
                }
            }
            if (!fileExistsInDestination) {
                File destinationFile = new File(destinationDirectoryPath + "/" + sourceFile.getPath().substring(sourceDirectoryPath.length() + 1));
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private static List<File> getFilesRecursively(File directory) {
        List<File> files = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                files.add(file);
            } else {
                files.addAll(getFilesRecursively(file));
            }
        }
        return files;
    }
}
