
import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeTO;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitService;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitTO;
import java.io.Serializable;
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
    
    private PermitTO selectedPermit = new PermitTO();
    private final PermitService pService = new PermitService();
    private boolean esNuevo;
    
    
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
    
    public void savePermit() throws Exception {

        boolean flag = true;
        
        if (this.selectedPermit.getDate() == null || this.selectedPermit.getDate().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Date is empty"));
            flag = false;
        }
        
        
        
        if (flag){
            System.out.println("Saving permit");
            this.pService.insert(this.selectedPermit);
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedPermit = new PermitTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }


    
}
