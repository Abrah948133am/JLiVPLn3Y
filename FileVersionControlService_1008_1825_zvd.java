// 代码生成时间: 2025-10-08 18:25:56
package com.example.fileversioncontrol;

import play.mvc.Controller;
import play.mvc.Result;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FileVersionControlService provides functionalities for versioning files.
 * It allows users to save different versions of a file, retrieve specific versions,
 * and list all available versions of a file.
 */
public class FileVersionControlService extends Controller {

    private static final String VERSIONS_DIRECTORY = "versions";
    private static final String BASE_DIRECTORY = "files";

    public Result saveFileVersion(String filename, byte[] fileData) {
        try {
            Path directory = Paths.get(BASE_DIRECTORY, filename);
            Path versionDirectory = Paths.get(VERSIONS_DIRECTORY, filename);
            Files.createDirectories(directory);
            Files.createDirectories(versionDirectory);

            int versionNumber = countVersions(versionDirectory) + 1;
            Path versionFile = versionDirectory.resolve("v" + versionNumber + "_" + filename);
            Files.write(directory.resolve(filename), fileData);
            Files.copy(directory.resolve(filename), versionFile);

            return ok("File version saved successfully");
        } catch (IOException e) {
            return internalServerError("Error saving file version");
        }
    }

    public Result getFileVersion(String filename, int version) {
        try {
            Path versionFile = Paths.get(VERSIONS_DIRECTORY, filename, "v" + version + "_" + filename);
            if (Files.exists(versionFile)) {
                return ok(Files.readAllBytes(versionFile));
            } else {
                return notFound("File version not found");
            }
        } catch (IOException e) {
            return internalServerError("Error retrieving file version");
        }
    }

    public Result listFileVersions(String filename) {
        try {
            Path versionDirectory = Paths.get(VERSIONS_DIRECTORY, filename);
            if (Files.exists(versionDirectory) && Files.isDirectory(versionDirectory)) {
                Stream<Path> filesStream = Files.list(versionDirectory);
                List<String> versions = filesStream
                    .filter(path -> path.toString().endsWith(filename))
                    .map(path -> path.getFileName().toString().replace("_" + filename, ""))
                    .collect(Collectors.toList());

                return ok(views.html.versions.render(versions));
            } else {
                return notFound("No versions found for the given file");
            }
        } catch (IOException e) {
            return internalServerError("Error listing file versions");
        }
    }

    private int countVersions(Path versionDirectory) throws IOException {
        try (Stream<Path> paths = Files.list(versionDirectory)) {
            return (int) paths
                .filter(path -> path.getFileName().toString().startsWith("v") && path.getFileName().toString().endsWith(filename))
                .count();
        }
    }
}
