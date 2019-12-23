import com.summer.springboot.jaxrs.JaxRsApplication;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = JaxRsApplication.class)
public class TestAsciiDocs {

    @Test
    public void generateAsciiDocs() throws Exception {

        //生成asciidocs
        URL remoteSwaggerFile = new URL("http://localhost:8090/v2/api-docs");
        Path outputDirectory = Paths.get("src/docs/asciidoc/generated");

        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();


        Swagger2MarkupConverter.from(remoteSwaggerFile)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);


        //生成confluence
//        URL remoteSwaggerFile = new URL("http://localhost:8080/v2/api-docs");
//        Path outputDirectory = Paths.get("src/docs/confluence/generated");
//
//        //    输出Ascii格式
//        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
//                .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
//                .build();
//
//        Swagger2MarkupConverter.from(remoteSwaggerFile)
//                .withConfig(config)
//                .build()
//                .toFolder(outputDirectory);
    }
}
