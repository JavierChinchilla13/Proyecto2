import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeService;
import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeTO;
import java.io.Serializable;
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
//HOLA SOY JAVIER

@ManagedBean(name = "employeeController")
@SessionScoped
public class EmployeeController implements Serializable{
    private String user;
    private String pasword;
    
    private EmployeeTO em;
    private final EmployeeService service = new EmployeeService();
    private boolean esNuevo;
    private EmployeeTO selectedEmployee = new EmployeeTO();
    
   private boolean isAdmin = false;
   private boolean isManager = false;
   private boolean isEmployee = false;
    
    public EmployeeController() {
    }

    public EmployeeController(String user, String pasword) {
        this.user = user;
        this.pasword = pasword;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }
    
    public List<EmployeeTO> getEmployees(){
        try{
            return service.getEmployees();
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));
            if (this.selectedEmployee.getType() == 1){
                
                
            }
            if (this.selectedEmployee.getType() == 2){
                
            }
            if (this.selectedEmployee.getType() == 3){
                
            }
            if (this.selectedEmployee.getStatus() == 4 ) {
                
            }
            if (this.selectedEmployee.getStatus() == 5 ) {
                
            }
            if (this.selectedEmployee.getStatus() == 6 ) {
                
            }

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }

    public List<EmployeeTO> getActiveEmployees(){
        try{
            return service.getActiveEmployees();
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }
    public int getId() {
        return em.getId();
    }

    public String getFirstName() {
        return em.getFirstName();
    }

    public String getLastname() {
        return em.getLastName();
    }

    public EmployeeTO getEm() {
        return em;
    }

    public void setEm(EmployeeTO em) {
        this.em = em;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public EmployeeTO getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(EmployeeTO selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public void openNew() {
        this.esNuevo = true;
        this.selectedEmployee = new EmployeeTO();
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public boolean isIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    
    

    public void saveUser() throws Exception {

        boolean flag = true;
        if (this.selectedEmployee.getFirstName() == null || this.selectedEmployee.getFirstName().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Name is empty"));
            flag = false;
        } 
        if (this.selectedEmployee.getLastName() == null || this.selectedEmployee.getLastName().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Last Name is empty"));
            flag = false;
        } 
        if (this.selectedEmployee.getIdentification() == null || this.selectedEmployee.getIdentification().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Identification is empty"));
            flag = false;
        } 
        if (this.selectedEmployee.getEmail()== null || this.selectedEmployee.getEmail().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Mail is empty"));
            flag = false;
        } 
        if (this.selectedEmployee.getPhone() == null || this.selectedEmployee.getPhone().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "phone is empty"));
            flag = false;
        } 
        if (this.selectedEmployee.getType() != 1 && this.selectedEmployee.getType() != 2 && this.selectedEmployee.getType() != 3 ) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Type is incorrect"));
            flag = false;
        } 
        if (this.selectedEmployee.getStatus() != 4 && this.selectedEmployee.getStatus() != 5 && this.selectedEmployee.getStatus() != 6) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "status is incorrect"));
            flag = false;
        } 
        if (this.selectedEmployee.getPassword() == null || this.selectedEmployee.getPassword().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Password is empty"));
            flag = false;
        } 
        
        if (flag){
            System.out.println("Estoy salvando al usuario");
            this.service.insert(this.selectedEmployee);
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedEmployee = new EmployeeTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }
    public void updateUser() throws Exception {

        boolean flag = true;
        if (this.selectedEmployee.getFirstName() == null || this.selectedEmployee.getFirstName().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Name is empty"));
            flag = false;
        } 
        if (this.selectedEmployee.getLastName() == null || this.selectedEmployee.getLastName().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Last Name is empty"));
            flag = false;
        } 
        if (this.selectedEmployee.getIdentification() == null || this.selectedEmployee.getIdentification().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Identification is empty"));
            flag = false;
        } 
        if (this.selectedEmployee.getEmail()== null || this.selectedEmployee.getEmail().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Mail is empty"));
            flag = false;
        }
        
        if (this.selectedEmployee.getType() != 1 && this.selectedEmployee.getType() != 2 && this.selectedEmployee.getType() != 3) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Type is incorrect"));
            flag = false;
        }
        if (this.selectedEmployee.getStatus() != 4 && this.selectedEmployee.getStatus() != 5 && this.selectedEmployee.getStatus() != 6) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "status is incorrect"));
            flag = false;
        }
        if (this.selectedEmployee.getPassword() == null || this.selectedEmployee.getPassword().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Password is empty"));
            flag = false;
        }

        if (flag) {

            this.service.update(selectedEmployee, selectedEmployee.getFirstName(), this.selectedEmployee.getLastName(), this.selectedEmployee.getIdentification(), this.selectedEmployee.getEmail(), this.selectedEmployee.getPhone(), this.selectedEmployee.getType(), this.selectedEmployee.getStatus(), this.selectedEmployee.getPassword(), this.selectedEmployee.getEmploymentDate(), this.selectedEmployee.getLayoffDate());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedEmployee = new EmployeeTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }
    public void deleteUser() throws Exception {

        boolean flag = true;
        

        if (flag) {

            this.service.update(selectedEmployee, this.selectedEmployee.getFirstName(), this.selectedEmployee.getLastName(), this.selectedEmployee.getIdentification(), this.selectedEmployee.getEmail(), this.selectedEmployee.getPhone(), this.selectedEmployee.getType(), 6, this.selectedEmployee.getPassword(),  this.selectedEmployee.getEmploymentDate(), this.selectedEmployee.getLayoffDate());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedEmployee = new EmployeeTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }

    public void logIn() {
        boolean flag = true;
        if (this.getUser() == null || this.getUser().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The Email is empty"));
            flag = false;
        }
        if (this.getPasword() == null || this.getPasword().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The password is empty"));
            flag = false;
        }
        if (flag) {
            boolean enter = false;
            setIsAdmin(false);
            setIsManager(false);
            setIsEmployee(false);
            try {
                String u = this.getUser();
                String pass = this.getPasword();
                em = service.login(u, pass);
                if (em != null) {
                    enter = true;
                    switch (em.getType()) {
                        case 1:

                            setIsAdmin(true);
                            break;

                        case 2:

                            setIsManager(true);
                            break;

                        case 3:

                            setIsEmployee(true);
                            break;

                        default:
                            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error"));
                            break;
                    }

                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in database"));
            }

            if (enter) {
                switch (em.getType()) {
                    case 1:
                        this.redirect("/faces/landingPage.xhtml");
                        break;
                    case 2:
                        this.redirect("/faces/landingPage.xhtml");
                        break;
                    case 3:
                        this.redirect("/faces/landingPage.xhtml");
                        break;
                    default:
                        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error"));
                        break;
                }

            } else {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The User or the password are invalid"));
            }
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

    public class ConfirmView {

        public void confirm() {
            addMessage("Confirmed", "You have accepted");
        }

        public void delete() {
            addMessage("Confirmed", "Record deleted");
        }

        public void addMessage(String summary, String detail) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public boolean createUserPermission() {
        

        return isAdmin;

    }
}
