
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

            this.pService.searchByEmployee(id);

    }
    
    public void onClick(Date date1) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(date1)));
        System.out.println(format.format(date1));
    }
    
    public boolean dateBefore() {
        boolean flag = true;

        if (flag) {
            LocalDate startDate = this.selectedPermit.getDate().toLocalDate();
            LocalDate currentDate = LocalDate.now();

            if (startDate.isBefore(currentDate)) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Selected date is before the current date"));
                flag = false;
            }
        }
        return flag;
    }

    public void savePermit(int pk) throws Exception {

        boolean flag = true;
        boolean flag2 = true;

        Date date = this.selectedPermit.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = dateFormat.format(date);

        if (this.selectedPermit.getDate() == null || this.selectedPermit.getDate().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Date is empty"));
            flag = false;
        }

        if (flag) {
            flag2 = dateBefore();
            if (flag2) {
                System.out.println("Saving permit");
                //Date day = (java.sql.Date) (java.sql.Date) selectedPermit.getDate();
                this.pService.insert(pk, this.selectedPermit.getDate(), this.selectedPermit.getDescription());
                //---this.servicioUsuario.listarUsuarios();
                //this.listaUsuarios.add(selectedEmployee);//para simular       
                this.esNuevo = false;
                this.selectedPermit = new PermitTO();
                PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
            }
        }
    }
    
    public String statusToStrStatus(int status) {
        String result = "";
        switch (status) {
            case 12:
                result = "Approved";
                break;
            case 13:
                result = "Denied";
                break;
            case 14:
                result = "Pending";
                break;
        }
        return result;
    }

    public List<PermitTO> searchByEmployee(int pk){
        try {
            return pService.searchByEmployee(pk);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<PermitTO> list = new ArrayList<>();
        return list;
    }
    
    public List<PermitTO> searchByStatus(int stat){
        try {
            return pService.searchByStatus(stat);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<PermitTO> list = new ArrayList<>();
        return list;
    }
    
    public List<PermitTO> searchBySupervisor(int stat, int supervisor){
        try {
            return pService.searchBySupervisor(stat, supervisor);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<PermitTO> list = new ArrayList<>();
        return list;
    }
    
    public List<PermitTO> getPermits() {
        try {
            return pService.getPermits();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<PermitTO> list = new ArrayList<>();
        return list;
    }

    public List<PermitTO> getNewPermits(int id ) {
        try {
            return pService.getNew(id);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<PermitTO> list = new ArrayList<>();
        return list;
    }
    
    public List<PermitTO> getOldPermits(int id) {
        try {
            return pService.getOld(id);
        } catch (Exception e) {
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

            this.pService.update(selectedPermit, this.selectedPermit.getIdEmployee(), this.selectedPermit.getDate(), this.selectedPermit.getDescription(), 13, this.selectedPermit.getResponse());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedPermit = new PermitTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }

    public java.util.Date getCalendarFireDate() {
        return (java.util.Date) this.selectedPermit.getDate();
    }

    public void setCalendarFireDate(java.util.Date fireDate) {
        if (fireDate != null) {
            this.selectedPermit.setDate(new java.sql.Date(fireDate.getTime()));
        }
    }

    public void approvePermit() throws Exception {

        boolean flag = true;

        if (flag) {

            this.pService.update(selectedPermit, this.selectedPermit.getIdEmployee(), this.selectedPermit.getDate(), this.selectedPermit.getDescription(), 12, this.selectedPermit.getResponse());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedPermit = new PermitTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }

    public PermitTO getPermit(int PK) {
        PermitTO foundPer = null;
        try {
            boolean flag = true;
            PermitService per= new PermitService();
            
            
            if (per.searchByPKStatus(PK) == 12 || per.searchByPKStatus(PK) == 13) {
                //ERROR

                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "This permit has already been responded"));
                flag = false;

            }
            if (flag) {
                foundPer = pService.searchByPK(PK);
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in searching the user"));
        }
        return foundPer;
    }

    public void deletePermit(int PK) throws Exception {

        try {

            PermitTO searched = this.getPermit(PK);
            if (searched != null) {
                pService.delete(searched);

            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in suspending the user"));

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
