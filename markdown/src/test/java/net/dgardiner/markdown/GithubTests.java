package net.dgardiner.markdown;

import net.dgardiner.markdown.flavours.github.GithubFlavour;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static net.dgardiner.markdown.core.matchers.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(value = Parameterized.class)
public class GithubTests {
    private static final String TESTS_DIR = "/net.dgardiner.markdown/github";

    private MarkdownProcessor processor;

    private String dir;
    private String name;

    @Parameters(name = "{index}: {1}")
    public static List getTestFiles() {
        List list = new ArrayList<Object>();

        // Discover tests in directory
        discoverTests(list, new File(GithubTests.class.getResource(TESTS_DIR).getFile()));

        return list;
    }

    private static void discoverTests(List out, File path) {
        discoverTests(out, path, path);
    }

    private static void discoverTests(List out, File path, File root) {
        File[] children = path.listFiles();

        if(children == null) {
            return;
        }

        for(File item : children) {
            if(item.isDirectory()) {
                // Discover tests in directory
                discoverTests(out, item, root);
                continue;
            }

            // Parse file
            String filename = item.getName();

            if (!filename.endsWith(".md")) {
                continue;
            }

            // Add entry for test file
            out.add(new Object[]{
                item.getParent(),
                filename.substring(0, filename.lastIndexOf('.'))
            });
        }
    }

    public GithubTests(String dir, String name) {
        this.dir = dir;
        this.name = name;

        this.processor = new MarkdownProcessor();
        this.processor.setLineBreaks(true);
        this.processor.setCompactLists(false);
        this.processor.setFlavour(new GithubFlavour());
        this.processor.setIndentEmptyLines(false);
        this.processor.setIndentSize(4);

    }

    @Test
    public void runTest() throws IOException {
        String testText = slurp(dir + File.separator + name + ".md");
        String htmlText = slurp(dir + File.separator + name + ".html");

        assertThat(name, processor.process(testText).trim(), equalToIgnoringWhiteSpace(htmlText.trim()));
    }

    private String slurp(String fileName) throws IOException {
        File file = new File(URLDecoder.decode(fileName, "UTF-8"));
        FileReader in = new FileReader(file);

        StringBuffer sb = new StringBuffer();
        int ch;
        while ((ch = in.read()) != -1) {
            sb.append((char) ch);
        }

        return sb.toString();
    }
}
