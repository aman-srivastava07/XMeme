package com.crio.starter.exchange;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;
import com.crio.starter.data.GreetingsEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMemesResponse
{
    List<GreetingsEntity> listMemes;
}