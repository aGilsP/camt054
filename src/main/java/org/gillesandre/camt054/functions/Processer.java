/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gillesandre.camt054.functions;

import org.gillesandre.camt054.pojo.Choriste;
import iso.std.iso._20022.tech.xsd.camt_054_001.AccountNotification7;
import iso.std.iso._20022.tech.xsd.camt_054_001.ActiveOrHistoricCurrencyAndAmount;
import iso.std.iso._20022.tech.xsd.camt_054_001.Document;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author gillesandre
 */
public class Processer implements Function<Collection<File>, Collection<Choriste>> {

  private final String PATTERN = "([0]{22})(\\d+)";
  private Pattern pattern = Pattern.compile(PATTERN);

  private JAXBContext jc;
  private Unmarshaller unmarshaller;
  private List<Choriste> choristes = new ArrayList<>();

  public Processer() throws JAXBException {
    jc = JAXBContext.newInstance("iso.std.iso._20022.tech.xsd.camt_054_001");
    unmarshaller = jc.createUnmarshaller();
  }

  private void processXml(File file) throws JAXBException {
    JAXBElement elt = (JAXBElement) unmarshaller.unmarshal(file);
    Document document = (Document) elt.getValue();

    List<AccountNotification7> ntfctns = document.getBkToCstmrDbtCdtNtfctn().getNtfctn();
    ntfctns.forEach((ntfctn) -> {
      ntfctn.getNtry().forEach((ntrys) -> {
        ntrys.getNtryDtls().forEach((ntryDtls) -> {
          ntryDtls.getTxDtls().stream().map((txDtl) -> {
//    for (AccountNotification7 ntfctn : ntfctns) {
//      for (ReportEntry4 ntrys : ntfctn.getNtry()) {
//        for (EntryDetails3 ntryDtls : ntrys.getNtryDtls()) {
//          for (EntryTransaction4 txDtl : ntryDtls.getTxDtls()) {
            ActiveOrHistoricCurrencyAndAmount amt = txDtl.getAmt();
            XMLGregorianCalendar calendar = txDtl.getRltdDts().getAccptncDtTm();
            LocalDate date = calendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
            //System.out.println(txDtl.getRltdPties().getDbtr().getNm() + " pour un montant de " + amt.getValue() + " : REF: " + ref.substring(22));
            Matcher matcher = pattern.matcher(txDtl.getRmtInf().getStrd().get(0).getCdtrRefInf().getRef());
            Choriste choriste = new Choriste();
            choriste.setDate(date);
            choriste.setMontant(amt.getValue().intValue());
            if (txDtl.getRltdPties() != null) {
              choriste.setNom(txDtl.getRltdPties().getDbtr().getNm());
            }
            
            if (matcher.matches()) {
              choriste.setReference(matcher.group(2).substring(0, 4));
            }
            return choriste;
          }).forEachOrdered((choriste) -> {
            choristes.add(choriste);
          });
        });
      });
    });
  }

  @Override
  public Collection<Choriste> apply(Collection<File> files) {
    try {
      if (files.isEmpty()) {
        processXml(new File("example2.xml"));
      } else {
        for (File file : files) {
          processXml(file);
        }
      }
    } catch (JAXBException ex) {
      Logger.getLogger(Processer.class.getName()).log(Level.SEVERE, null, ex);
    }
    return choristes;
  }

}
