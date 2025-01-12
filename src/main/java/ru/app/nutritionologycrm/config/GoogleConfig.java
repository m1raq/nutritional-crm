package ru.app.nutritionologycrm.config;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.drive.Drive;
import com.google.api.services.forms.v1.Forms;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleConfig {

    @Bean
    public Forms formsService() throws GeneralSecurityException, IOException {
        return new Forms.Builder(GoogleNetHttpTransport.newTrustedTransport()
                , JacksonFactory.getDefaultInstance()
                , null)
                .setApplicationName("nutritionologycrm")
                .build();
    }

//    @Bean
//    public Drive driveService() throws GeneralSecurityException, IOException {
//        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport()
//                , JacksonFactory.getDefaultInstance()
//                , null)
//                .setApplicationName("nutritionologycrm")
//                .build();
//    }

}
