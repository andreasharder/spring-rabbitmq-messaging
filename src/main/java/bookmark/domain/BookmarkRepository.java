/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Developer
 */

@RepositoryRestResource
public interface BookmarkRepository extends MongoRepository<Bookmark, String> {
   
    List<Bookmark> findByUrl(@Param("url") String url);
}