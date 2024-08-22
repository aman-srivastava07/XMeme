package com.crio.starter.controller;

import com.crio.starter.data.GreetingsEntity;
import com.crio.starter.exchange.GetMemesResponse;
import com.crio.starter.exchange.PostMemesRequest;
import com.crio.starter.exchange.PostMemesResponse;
import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.service.GreetingsService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GreetingsController 
{
  public static final String CREATE_MEMES = "/memes";
  public static final String SEARCH_BY_ID = "/memes/{id}";

  @Autowired
  private final GreetingsService memeService;

  // @GetMapping("/say-hello")
  // public ResponseDto sayHello(@RequestParam String messageId) {
  //   return greetingsService.getMessage(messageId);
  // }

  @PostMapping(CREATE_MEMES)
  public ResponseEntity<PostMemesResponse> addMeme(@Valid @RequestBody GreetingsEntity memeEntity)
  {
    // if(memeEntity.getName() == "" || memeEntity.getUrl() == "" || memeEntity.getCaption() == "")
    //   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);



    boolean isAlreadyExistInDatabase = memeService.addMemeInDatabase(memeEntity);  //Required GreetingsEntity object in parameter

    if(isAlreadyExistInDatabase == false)
    {
      PostMemesResponse postMemesResponse = new PostMemesResponse(memeEntity.getId());
      return new ResponseEntity<>(postMemesResponse, HttpStatus.CREATED);
    }
    else
    {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    } 
  }

  @GetMapping(CREATE_MEMES)
  public List<GreetingsEntity> fetchAllMemes()
  {
    //List<GreetingsEntity> allMemes = memeService.getAllMemes();  
    //return allMemes;   
    //Return Type is GetMemesResponse
    //return new GetMemesResponse(memeService.getAllMemes());
    //return memeService.getAllMemes();

    GetMemesResponse getMemesResponse = memeService.getAllMemes();
    return getMemesResponse.getListMemes();
  }

  @GetMapping(SEARCH_BY_ID)
  public ResponseEntity<GreetingsEntity> fetchById(@PathVariable String id)
  {
    Optional<GreetingsEntity> getMeme = memeService.getMemeById(id); //Return type is Optional<GreetingsEntity>
    if(getMeme.isEmpty())
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    else
    {
      return new ResponseEntity<>(getMeme.get(),HttpStatus.CREATED);
      //getMeme.get() returns GreetingsEntity
    }
    
  }

}
