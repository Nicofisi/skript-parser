package io.github.syst3ms.skriptparser.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    public static final Pattern LEADING_WHITESPACE_PATTERN = Pattern.compile("(\\s+)\\S.+");
    public static final String MULTILINE_SYNTAX_TOKEN = "\\";

    public static List<String> readAllLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        FileReader in = new FileReader(file);
        BufferedReader reader = new BufferedReader(in);
        String line;
        StringBuilder multilineBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            if (line.replace("\\" + MULTILINE_SYNTAX_TOKEN, "\0")
                    .endsWith(MULTILINE_SYNTAX_TOKEN)) {
                multilineBuilder.append(line.substring(0, line.length() - 1)).append("\0");
            } else if (multilineBuilder.length() > 0) {
                multilineBuilder.append(line);
                lines.add(trimMultilineIndent(multilineBuilder.toString()));
                multilineBuilder.setLength(0);
            } else {
                lines.add(line);
            }
        }
        if (multilineBuilder.length() > 0) {
            multilineBuilder.deleteCharAt(multilineBuilder.length() - 1);
            lines.add(trimMultilineIndent(multilineBuilder.toString()));
        }
        return lines;
    }

    public static int getIndentationLevel(String line) {
        Matcher m = LEADING_WHITESPACE_PATTERN.matcher(line);
        if (m.matches()) {
            return StringUtils.count(m.group(1), "\t", "    ");
        } else {
            return 0;
        }
    }

    private static String trimMultilineIndent(String multilineText) {
        String[] lines = multilineText.split("\0");
        // Inspired from Kotlin's trimIndent() function
        int baseIndent = Arrays.stream(lines)
                               .skip(1) // First line's indent should be ignored
                               .mapToInt(FileUtils::getIndentationLevel)
                               .min()
                               .orElse(0);
        if (baseIndent == 0)
            return multilineText.replace("\0", "");
        Pattern pat = Pattern.compile("\\t| {4}");
        StringBuilder sb = new StringBuilder(lines[0]);
        for (String line : Arrays.copyOfRange(lines, 1, lines.length)) {
            Matcher m = pat.matcher(line);
            for (int i = 0; i < baseIndent && m.find(); i++) {
                line = line.replaceFirst(m.group(), "");
            }
            sb.append(line);
        }
        return sb.toString();
    }

    public  static void loadClasses(String pck, String... subpackages) throws ClassNotFoundException, IOException {
        for (String subpackage : subpackages) {
            String packageName = pck + "." + subpackage;
            final Enumeration<URL> resources = Thread.currentThread()
                                                     .getContextClassLoader()
                                                     .getResources(packageName.replace('.', '/'));
            while (resources.hasMoreElements()) {
                loadClasses(new File(resources.nextElement().getFile()), packageName);
            }
        }
    }

    private static void loadClasses(final File directory, final String packageName) throws ClassNotFoundException {
        if (!directory.exists()) {
            return;
        }
        File[] files = directory.listFiles();
        if (files == null)
            return;
        for (final File file : files) {
            final String name = file.getName();
            if (file.isDirectory()) {
                loadClasses(file, packageName + '.' + name);
            } else if (file.getName().endsWith(".class")) {
                Class.forName(packageName + '.' + name.substring(0, name.length() - 6), true, FileUtils.class.getClassLoader());
            }
        }
    }
}