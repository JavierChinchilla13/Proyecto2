
import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeTO;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitService;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitTO;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javi
 */

@ManagedBean(name = "permitController")
@SessionScoped
public class PermitController implements Serializable{
    
    private Date date1;
    private PermitTO selectedPermit = new PermitTO();
    private final PermitService pService = new PermitService();
    private boolean esNuevo;
    private Date fireDate;

    public Date getFireDate() {
        return fireDate;
    }

    public void setFireDate(Date fireDate) {
        this.fireDate = fireDate;
    }
    

    public java.sql.Date convertir(java.util.Date date) {
        return new java.sql.Date(date.getDate());
    }

    public void openNew() {
        this.esNuevo = true;
        this.selectedPermit = new PermitTO();
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }
    
    public PermitTO getSelectedPermit() {
        return selectedPermit;
    }

    public void setSelectedPermit(PermitTO selectedPermit) {
        this.selectedPermit = selectedPermit;
    }
    public PermitService getpService() {
        return pService;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date) {
        this.date1 = date;
    }
    
    public void showPermit(int id) throws Exception {

            this.pService.searchByEmployee(1);

    }
    
    public void onClick(Date date1) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(date1)));
        System.out.println(format.format(date1));
    }
    
    public void savePermit(int pk) throws Exception {

        boolean flag = true;
    
        Date date = this.selectedPermit.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = dateFormat.format(date);

        if (this.selectedPermit.getDate() == null || this.selectedPermit.getDate().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Date is empty"));
            flag = false;
        }

        
        
        if (flag){
            System.out.println("Saving permit");
            //Date day = (java.sql.Date) (java.sql.Date) selectedPermit.getDate();
            this.pService.insert(pk,this.selectedPermit.getDate(), this.selectedPermit.getDescription());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedPermit = new PermitTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }
    public List<PermitTO> searchByEmployee(int pk){
        try{
            return pService.searchByEmployee(pk);
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<PermitTO> list = new ArrayList<>();
        return list;
    }
    public List<PermitTO> getPermits(){
        try{
            return pService.getPermits();
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<PermitTO> list = new ArrayList<>();
        return list;
    }
    
    public void reiviewPermit() throws Exception {

        //boolean flag = true;
        
        this.redirect("/faces/reviewPermits.xhtml");

    }
    public void returnPermit() throws Exception {

        //boolean flag = true;
        
        this.redirect("/faces/permits.xhtml");

    }
    
    public void denyPermit() throws Exception {

        boolean flag = true;
        

        if (flag) {
            
            this.pService.update(selectedPermit, this.selectedPermit.getIdEmployee(), this.selectedPermit.getDate(), this.selectedPermit.getDescription(), 13,"");
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedPermit = new PermitTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }
    
    public java.util.Date getCalendarFireDate(){
         return (java.util.Date) this.selectedPermit.getDate();
     }
     
     public void setCalendarFireDate(java.util.Date fireDate){
         if(fireDate !=null){
             this.selectedPermit.setDate(new java.sql.Date(fireDate.getTime()));
         }
     }
     
     public void approvePermit() throws Exception {

        boolean flag = true;
        
        

         if (flag) {

            this.pService.update(selectedPermit, this.selectedPermit.getIdEmployee(), this.selectedPermit.getDate(), this.selectedPermit.getDescription(), 12,"");
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedPermit = new PermitTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }

    public void redirect(String rute) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + rute);
        } catch (Exception e) {

        }

    }
}
