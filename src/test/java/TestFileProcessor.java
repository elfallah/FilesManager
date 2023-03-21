
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestFileProcessor {
    private static final String SOURCE_DIRECTORY_PATH = "src/test/resources/source";
    private static final String DESTINATION_DIRECTORY_PATH = "src/test/resources/destination";
    private static final List<String> SOURCE_FILE_NAMES = Arrays.asList("file1.txt", "file2.txt", "subdir/file3.txt");
    private static final List<String> DESTINATION_FILE_NAMES = Arrays.asList("file1.txt", "subdir/file3.txt", "file4.txt");

    @Before
    public void setUp() throws Exception {
        createTestFiles();
    }

    @After
    public void tearDown() throws Exception {
        deleteTestFiles();
    }

    @Test
    public void testCompareAndCopyMissedFiles() throws IOException {
        FileProcessor.compareAndCopyMissedFiles(SOURCE_DIRECTORY_PATH, DESTINATION_DIRECTORY_PATH);

        for (String fileName : SOURCE_FILE_NAMES) {
            assertTrue(new File(DESTINATION_DIRECTORY_PATH + "/" + fileName).exists());
        }

        assertTrue(new File(DESTINATION_DIRECTORY_PATH + "/file2.txt").exists());
        assertTrue(new File(DESTINATION_DIRECTORY_PATH + "/file4.txt").exists());
    }

    private void createTestFiles() throws IOException {
        new File(SOURCE_DIRECTORY_PATH + "/subdir").mkdirs();
        new File(DESTINATION_DIRECTORY_PATH + "/subdir").mkdirs();
        for (String fileName : SOURCE_FILE_NAMES) {
            Files.createFile(Paths.get(SOURCE_DIRECTORY_PATH, fileName));
        }
        for (String fileName : DESTINATION_FILE_NAMES) {
            Files.createFile(Paths.get(DESTINATION_DIRECTORY_PATH, fileName));
        }
    }

    private void deleteTestFiles() throws IOException {
        for (String fileName : SOURCE_FILE_NAMES) {
            Files.deleteIfExists(Paths.get(DESTINATION_DIRECTORY_PATH, fileName));
        }
        Files.deleteIfExists(Paths.get(DESTINATION_DIRECTORY_PATH, "file2.txt"));
        Files.deleteIfExists(Paths.get(DESTINATION_DIRECTORY_PATH, "file4.txt"));
        Files.deleteIfExists(Paths.get(SOURCE_DIRECTORY_PATH, "subdir", "file3.txt"));
        Files.deleteIfExists(Paths.get(SOURCE_DIRECTORY_PATH, "subdir"));
        Files.deleteIfExists(Paths.get(SOURCE_DIRECTORY_PATH, "file1.txt"));
        Files.deleteIfExists(Paths.get(SOURCE_DIRECTORY_PATH, "file2.txt"));
    }
}
