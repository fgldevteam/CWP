package com.fgl.cwp.model;

/**
 * @author Jessica Wong
 * 
 * @hibernate.class
 *      table = "hierarchy"
 *      proxy = "com.fgl.cwp.model.Hierarchy"
 * 
    @hibernate.query 
        name="getDepartments"
        query="SELECT    DISTINCT new com.fgl.cwp.model.HierarchyItem (hierarchy.deptID, hierarchy.deptDescription)
            FROM      Hierarchy hierarchy"
    
    @hibernate.query name="getSubDepartmentsByDepartment"
        query="SELECT    DISTINCT  new com.fgl.cwp.model.HierarchyItem (hierarchy.subDeptID, hierarchy.subDeptDescription)
            FROM      Hierarchy hierarchy
            WHERE     hierarchy.deptID = :dept"
    
    @hibernate.query name="getClassesBySubDepartment"
        query="SELECT    DISTINCT  new com.fgl.cwp.model.HierarchyItem (hierarchy.classID, hierarchy.classDescription)
            FROM      Hierarchy hierarchy
            WHERE     hierarchy.subDeptID = :subDept"
    
    @hibernate.query name="getSubClassesByClass"
        query="SELECT    DISTINCT  new com.fgl.cwp.model.HierarchyItem (hierarchy.subClassID, hierarchy.subClassDescription)
            FROM      Hierarchy hierarchy
        WHERE     hierarchy.classID = :class"
 */
public class Hierarchy {

     private Integer subClassID;
     private String subClassDescription;
     private Integer classID;
     private String classDescription;
     private Integer subDeptID;
     private String subDeptDescription;
     private Integer deptID;
     private String deptDescription;
     private Integer divisionID;
     private String divisionDescription;

     /**
     * @return
     * 
     * @hibernate.id
     *      column = "subclass_code"
     *      generator-class = "native"
     */
    public Integer getSubClassID() {
          return subClassID;
     }

     /**
     * @param subClassID
     */
    public void setSubClassID(Integer subClassID) {
          this.subClassID = subClassID;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "subclass_name"
     */
    public String getSubClassDescription() {
          return subClassDescription;
     }

     /**
     * @param subClassDescription
     */
    public void setSubClassDescription(String subClassDescription) {
          this.subClassDescription = subClassDescription;
     }

     /**
     * @return
     * 
     * @hibernate.property
     *      column = "class_code"
     */
    public Integer getClassID() {
          return classID;
     }

     /**
     * @param classID
     */
    public void setClassID(Integer classID) {
          this.classID = classID;
     }

     /**
     * @return the class Description
     * @hibernate.property
     *      column = "class_name"
     */
    public String getClassDescription() {
          return classDescription;
     }

     /**
     * @param classDescription
     */
    public void setClassDescription(String classDescription) {
          this.classDescription = classDescription;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "subdept_code"
     */
    public Integer getSubDeptID() {
          return subDeptID;
     }

     /**
     * @param subDeptID
     */
    public void setSubDeptID(Integer subDeptID) {
          this.subDeptID = subDeptID;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "subdept_name"
     */
    public String getSubDeptDescription() {
          return subDeptDescription;
     }

     /**
     * @param subDeptDescription
     */
    public void setSubDeptDescription(String subDeptDescription) {
          this.subDeptDescription = subDeptDescription;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "dept_code"
     */
    public Integer getDeptID() {
          return deptID;
     }

     /**
     * @param deptID
     */
    public void setDeptID(Integer deptID) {
          this.deptID = deptID;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "dept_name"
     */
    public String getDeptDescription() {
          return deptDescription;
     }

     /**
     * @param deptDescription
     */
    public void setDeptDescription(String deptDescription) {
          this.deptDescription = deptDescription;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "division_code"
     */
    public Integer getDivisionID() {
          return divisionID;
     }

     /**
     * @param divisionID
     */
    public void setDivisionID(Integer divisionID) {
          this.divisionID = divisionID;
     }

     /**
     * @return
     * 
     * @hibernate.property 
     *      column = "division_name"
     */
    public String getDivisionDescription() {
          return divisionDescription;
     }

     /**
     * @param divisionDescription
     */
    public void setDivisionDescription(String divisionDescription) {
          this.divisionDescription = divisionDescription;
     }
}
