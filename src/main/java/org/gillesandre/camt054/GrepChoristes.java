/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gillesandre.camt054;

import org.gillesandre.camt054.functions.Processer;
import org.gillesandre.camt054.functions.PojosToJson;
import org.gillesandre.camt054.functions.Displayer;
import org.gillesandre.camt054.functions.Searcher;
import java.io.File;
import java.util.Collection;
import javax.xml.bind.JAXBException;
import org.gillesandre.camt054.pojo.Choriste;

/**
 *
 * @author gillesandre
 */
public class GrepChoristes {

  private Searcher searcher;
  private Displayer displayer;
  private Processer processer;
  private PojosToJson pojoToJson;

  public GrepChoristes() {
    try {
      searcher = new Searcher();
      processer = new Processer();
      displayer = new Displayer();
      pojoToJson = new PojosToJson();

      Collection<File> files = searcher.get();

      Collection<Choriste> choristes = processer.apply(files);
      displayer.accept(choristes);
      String apply = pojoToJson.apply(choristes);

    } catch (JAXBException e) {
    }
  }
}
