package com.crio.starter.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.boot.pring-boot-starter-validation

@Data                               //Getter, Setter, toString, RequiredConstructor
@Document(collection = "greetings")  //collection name inside mongo db
@NoArgsConstructor
public class GreetingsEntity 
{

  //private String extId;                    (already exists)
  //private String message;                  (already exists)

  @Id                                 // Now it acts as a primary key and it generates automatically
  private String id;

  //@NonNull
  //@NotBlank
  @NotNull
  private String name;

  //@NonNull
  //@NotBlank
  @NotNull
  private String url;

  //@NonNull
  //@NotBlank
  @NotNull
  private String caption;
}
