package com.fgl.cwp.model;


/**
 * @author Jessica Wong
 * @hibernate.class 
 *      table = "colour_size_description"
 *      proxy = "com.fgl.cwp.model.ColourSize"
 * 
 */
public class ColourSize {

     private Integer codiCode;
     private String codiDescription;

     /**
     * @return
     * 
     * @hibernate.id
     *      column = "codi_code"
     *      generator-class = "native"
     */
    public Integer getCodiCode() {
          return codiCode;
     }

     /**
     * @param codiCode
     */
    public void setCodiCode(Integer codiCode) {
          this.codiCode = codiCode;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "codi_description"
     */
    public String getCodiDescription() {
          return codiDescription;
     }

     /**
     * @param codiDescription
     */
    public void setCodiDescription(String codiDescription) {
          this.codiDescription = codiDescription;
     }


}
