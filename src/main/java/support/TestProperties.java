package support;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {

    public static Properties loadProperty(){
        ClassPathResource resource = new ClassPathResource( "test.properties" );
        Properties p = new Properties();
        InputStream inputStream;
        try {
            inputStream = resource.getInputStream();
            p.load( inputStream );
            inputStream.close();
        } catch ( IOException e ) {
            System.out.println( e.getMessage());
        }
        return p;
    }
}
