
import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeTO;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitService;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitTO;
import java.io.Serializable;
import java.sql.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    
    private Date date;
    private PermitTO selectedPermit = new PermitTO();
    private final PermitService pService = new PermitService();
    private boolean esNuevo;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void showPermit(int id) throws Exception {

            this.pService.searchByEmployee(1);

    }
    
    public void onClick(Date date) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(date)));
        System.out.println(format.format(date));
    }
    
    public void savePermit() throws Exception {

        boolean flag = true;
        
        if (this.selectedPermit.getDate() == null || this.selectedPermit.getDate().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Date is empty"));
            flag = false;
        }
        
        
        
        if (flag){
            System.out.println("Saving permit");
            //Date day = (java.sql.Date) (java.sql.Date) selectedPermit.getDate();
            this.pService.insert(3,Date.valueOf("2023-07-14"));
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
    


    
}
