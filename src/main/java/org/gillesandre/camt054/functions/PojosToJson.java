/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gillesandre.camt054.functions;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gillesandre.camt054.pojo.Choriste;

/**
 *
 * @author gillesandre
 */
public class PojosToJson implements Function<Collection<Choriste>, String> {

  private String pojoToJson(Choriste choriste) {
    String jsonInString = "";
    try {
      ObjectMapper mapper = new ObjectMapper();
      //mapper.writeValueAsString(choriste);

      //Convert object to JSON PATTERN and pretty print
      jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(choriste);
      System.out.println(jsonInString);
    } catch (IOException ex) {
      Logger.getLogger(PojosToJson.class.getName()).log(Level.SEVERE, null, ex);
    }
    return jsonInString;
  }
  
  @Override
  public String apply(Collection<Choriste> choristes) {
    StringBuilder stringBuilder = new StringBuilder();
    choristes.forEach((choriste) -> {
      stringBuilder.append(pojoToJson(choriste)).append(',');
    });
    return stringBuilder.deleteCharAt(stringBuilder.length()-1).insert(0, "[").append("]").toString();
  }

}
