package com.crio.starter.service;

import com.crio.starter.data.GreetingsEntity;
import com.crio.starter.exchange.GetMemesResponse;
import com.crio.starter.exchange.PostMemesRequest;
import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.repository.GreetingsRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreetingsService 
{
  @Autowired
  private final GreetingsRepository greetingsRepository;

  @Autowired
  private final MongoTemplate mongoTemplate;


  public Boolean addMemeInDatabase(GreetingsEntity memeEntity) //It is not accepting PostMemesRequest class object (Line 51 starts throwing error)
  {
    Query query = new Query();

    query.addCriteria(Criteria.where("name").is(memeEntity.getName()));
    query.addCriteria(Criteria.where("url").is(memeEntity.getUrl()));
    query.addCriteria(Criteria.where("caption").is(memeEntity.getCaption()));

    //To know if a document exists in Mongo without actually retrieving any details.
    //exists() method returns a boolean value
    boolean isExist = mongoTemplate.exists(query, "greetings");  //mongoTemplate.exists(query, collectionName)

    if(isExist)
      return true;
    
    else
      greetingsRepository.save(memeEntity);     //save(S entity)

    return false;
  }

  public GetMemesResponse getAllMemes()
  {
    //Query query = new Query();
    //query.with(Sort.by(Sort.Direction.ASC, "age"));
    //List<User> users = mongoTemplate.find(query,User.class);

    List<GreetingsEntity> allMemes = greetingsRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); //returns List<GreetingsEntity>
    //Sort imort from data domain library

    if(allMemes.size() < 100) 
    {
      return new GetMemesResponse(allMemes);
    }
    else 
    {
      //for getting a page of size 2
      //final Pageable pageableRequest = PageRequest.of(0, 2);
      //Query query = new Query();
      //query.with(pageableRequest);

      // final Pageable pageableRequest = PageRequest.of(0, 100);
      // Query query = new Query();
      // query.with(pageableRequest);

      //for lists, we can use streams to limit our response
      return new GetMemesResponse(allMemes.stream().limit(100).collect(Collectors.toList()));
    }
  }

  public Optional<GreetingsEntity> getMemeById(String id)
  {
    return greetingsRepository.findById(id); //Return type is Optional<GreetingsEntity>
  }

  // public ResponseDto getMessage(String id) {
  //   return new ResponseDto(greetingsRepository.findByExtId(id).getMessage());
  // }
}
