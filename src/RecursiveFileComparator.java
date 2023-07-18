import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFileComparator {
    public static void compareFilesAndDirectoriesRecursive(String path1, String path2) {
        List<String> differences = new ArrayList<>();
        comparePaths(path1, path2, differences);

        if (differences.isEmpty()) {
            System.out.println("Both paths are identical.");
        } else {
            System.out.println("These directories have differences:\n");
            int i = 0;
            for (String difference : differences) {
                System.out.println(difference);
                i++;
                if (i % 2 == 0) {
                    System.out.println("\n");
                }
            }
        }
    }

    private static void comparePaths(String path1, String path2, List<String> differences) {
        File root1 = new File(path1);
        File root2 = new File(path2);

        if (!root1.exists() || !root2.exists() || !root1.isDirectory() || !root2.isDirectory()) {
            return;
        }

        File[] files1 = root1.listFiles();
        File[] files2 = root2.listFiles();

        if (files1 == null || files2 == null) {
            differences.add(path1);
            differences.add(path2);
            return;
        }

        int fileCount1 = 0;
        int directoryCount1 = 0;
        int fileCount2 = 0;
        int directoryCount2 = 0;

        for (File file : files1) {
            if (file.isFile()) {
                fileCount1++;
            } else if (file.isDirectory()) {
                directoryCount1++;
            }
        }

        for (File file : files2) {
            if (file.isFile()) {
                fileCount2++;
            } else if (file.isDirectory()) {
                directoryCount2++;
            }
        }

        if (fileCount1 != fileCount2 || directoryCount1 != directoryCount2) {
            differences.add(path1 + "   D --> " + directoryCount1 + "   F --> " + fileCount1);
            differences.add(path2 + "   D --> " + directoryCount2 + "   F --> " + fileCount2);
        }

        for (File file : files1) {
            if (file.isDirectory()) {
                String newPath1 = path1 + File.separator + file.getName();
                String newPath2 = path2 + File.separator + file.getName();
                comparePaths(newPath1, newPath2, differences);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You should pass 2 parameters that are paths that you want to compare them.");
            return;
        }
        String path1 = args[0];
        String path2 = args[1];
        compareFilesAndDirectoriesRecursive(path1, path2);
    }
}
