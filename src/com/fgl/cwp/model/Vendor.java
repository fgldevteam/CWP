package com.fgl.cwp.model;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class
 *      table = "vendor"
 *      proxy = "com.fgl.cwp.model.Vendor"
 */

public class Vendor {
     private Integer id;
     private String name;
     private String code;

     /**
     * @return
     * 
     * @hibernate.id
     *      column = "id"
     *      generator-class = "native"
     */
    public Integer getId() {
          return id;
     }

     /**
     * @param id
     */
    public void setId(Integer id) {
          this.id = id;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "name"
     */
    public String getName() {
          return name;
     }

     /**
     * @param name
     */
    public void setName(String name) {
          this.name = name;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "code"
     */
    public String getCode() {
          return code;
     }

     /**
     * @param code
     */
    public void setCode(String code) {
          this.code = code;
     }

}
