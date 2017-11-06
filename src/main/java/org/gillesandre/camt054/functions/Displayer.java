/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gillesandre.camt054.functions;

import org.gillesandre.camt054.pojo.Choriste;
import java.util.Collection;
import java.util.function.Consumer;

/**
 *
 * @author gillesandre
 */
public class Displayer implements Consumer<Collection<Choriste>>{

  @Override
  public void accept(Collection<Choriste> choristes) {
    choristes.forEach((choriste) -> {
      System.out.println(choriste.toString());
    });
  }
}
