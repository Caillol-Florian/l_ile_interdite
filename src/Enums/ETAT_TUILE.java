/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author souliern
 */
public enum ETAT_TUILE {
    SECHE("Sèche"),
    COULEE("Coulée"),
    INONDEE("Inondée");
    
    private String name;
    
    ETAT_TUILE(String name){
        this.name = name;
    }
}
