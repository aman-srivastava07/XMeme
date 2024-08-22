package com.crio.starter.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;

//not used anywhere
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostMemesRequest 
{
    @NonNull
    private String name;

    @NonNull
    private String url;

    @NonNull
    private String caption;

}
