/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gillesandre.camt054.pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author gillesandre
 */
public class Choriste {

  private String nom;
  private LocalDate date;
  private String reference;
  private int montant;

  /**
   * @return the nom
   */
  public String getNom() {
    return nom;
  }

  /**
   * @param nom the nom to set
   */
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * @return the date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @param reference the reference to set
   */
  public void setReference(String reference) {
    this.reference = reference;
  }

  /**
   * @return the montant
   */
  public int getMontant() {
    return montant;
  }

  /**
   * @param montant the montant to set
   */
  public void setMontant(int montant) {
    this.montant = montant;
  }

  @Override
  public String toString() {
    return "REF: " + getReference() + " - " + getDate().format(DateTimeFormatter.ISO_DATE) + ": " + getNom() + " pour un montant de " + getMontant()+"-";
  }
}
