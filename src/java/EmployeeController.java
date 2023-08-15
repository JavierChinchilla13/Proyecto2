
import com.mycompany.edu.ulatina.hth_db_connetion.DocumentService;
import com.mycompany.edu.ulatina.hth_db_connetion.DocumentTO;
import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeService;
import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeTO;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitService;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitTO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;


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
public class EmployeeController implements Serializable {

    private String user;
    private String pasword;

    private EmployeeTO em;
    private final EmployeeService service = new EmployeeService();
    private boolean esNuevo = false;
    private EmployeeTO selectedEmployee = new EmployeeTO();

    private boolean isAdmin = false;

    private boolean isManager = false;
    private boolean isEmployee = false;

    private final DocumentService docService = new DocumentService();
    private UploadedFile originalImageFile;

    private StreamedContent fileSC;

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

    public UploadedFile getOriginalImageFile() {
        return originalImageFile;
    }

    public void setOriginalImageFile(UploadedFile originalImageFile) {
        this.originalImageFile = originalImageFile;
    }

    public StreamedContent getFileSC() {
        return fileSC;
    }

    public void setFileSC(StreamedContent fileSC) {
        this.fileSC = fileSC;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public List<EmployeeTO> getEmployees() {
        try {
            return service.getNotSuspended();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }

    public List<EmployeeTO> getActiveEmployees() {
        try {
            return service.getActiveEmployees();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }

    public List<EmployeeTO> getSubordinates(int pk) {
        try {
            return service.getSubordinates(pk);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }

    public List<EmployeeTO> getSuspendedEmployees() {
        try {
            return service.getSuspendedEmployees();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }

    public List<EmployeeTO> getSupervisor() {
        try {

            return service.getSupervisor();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }

    public void viewSuspended() throws Exception {

        this.redirect("/faces/suspendedEmployees.xhtml");

    }

    public void returnUser() throws Exception {

        this.redirect("/faces/user.xhtml");

    }

    public void rehire(int PK) throws Exception {

        try {
            EmployeeService eServ = new EmployeeService();

            this.service.update(selectedEmployee, selectedEmployee.getFirstName(), this.selectedEmployee.getLastName(), this.selectedEmployee.getIdentification(), this.selectedEmployee.getEmail(), this.selectedEmployee.getPhone(), this.selectedEmployee.getType(), 5, this.selectedEmployee.getPassword(), this.selectedEmployee.getEmploymentDate(), this.selectedEmployee.getLayoffDate(), this.selectedEmployee.getIdSupervisor());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedEmployee = new EmployeeTO();
            eServ.rehire(PK);
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in deleting the Schedule Vacation"));

        }

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

    public String getIdentification() {
        return em.getIdentification();
    }

    public String getEmail() {
        return em.getEmail();
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

    public EmployeeService getService() {
        return service;
    }

    public EmployeeTO getEmployee(int PK) {
        EmployeeTO foundEmp = null;
        try {

            foundEmp = service.searchByPK(PK);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in searching the user"));
        }
        return foundEmp;
    }

    public String getEmployeeName(int PK) {
        String foundEmp = "";
        try {

            foundEmp = service.searchByPKName(PK);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in searching the user"));
        }
        return foundEmp;
    }

    public String typeToStrType(int type) {
        String result = "";
        switch (type) {
            case 1:
                result = "Admin";
                break;
            case 2:
                result = "Manager";
                break;
            case 3:
                result = "Employee";
                break;
        }
        return result;
    }

    public String statusToStrStatus(int status) {
        String result = "";
        switch (status) {
            case 4:
                result = "Inactive";
                break;
            case 5:
                result = "Active";
                break;
            case 6:
                result = "Suspended";
                break;
        }
        return result;
    }

    public boolean checkEmail(String email) {
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkPassword(String pass) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
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
        if (this.selectedEmployee.getEmail() == null || this.selectedEmployee.getEmail().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Email is empty"));
            flag = false;
        }
        if (!checkEmail(this.selectedEmployee.getEmail())) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Email format is incorrect"));
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
        if (checkPassword(this.selectedEmployee.getPassword())) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: Password format is incorrect", "The password must contain 4 to 8 characters and must contain numbers, lowercase and uppercase letters."));
            flag = false;
        }

        if (this.selectedEmployee.getType() == 1 && this.selectedEmployee.getIdSupervisor() != 0) {

            //ERROR      
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Admins dont have supervisor"));
            flag = false;

        }
        if (this.selectedEmployee.getType() != 1 && this.selectedEmployee.getIdSupervisor() == 0) {

            //ERROR      
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Supervisor empty"));
            flag = false;

        }

        if (flag) {
            System.out.println("Estoy salvando al usuario");
            this.service.insert(this.selectedEmployee);

            this.esNuevo = false;
            this.selectedEmployee = new EmployeeTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }

    public void updateUser() throws Exception {

        int status = 0;

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
        if (this.selectedEmployee.getEmail() == null || this.selectedEmployee.getEmail().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Mail is empty"));
            flag = false;
        }
        if (!checkEmail(this.selectedEmployee.getEmail())) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Email format is incorrect"));
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
        if (checkPassword(this.selectedEmployee.getPassword())) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: Password format is incorrect", "The password must contain 4 to 8 characters and must contain numbers, lowercase and uppercase letters."));
            flag = false;
        }
        if (this.selectedEmployee.getLayoffDate() == null || this.selectedEmployee.getLayoffDate().equals("")) {
            //ERROR
            flag = true;
        }

        if (this.selectedEmployee.getLayoffDate() != null) {
            //ERROR
            //FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Layoff date is empty"));
            flag = false;
            this.service.update(selectedEmployee, selectedEmployee.getFirstName(), this.selectedEmployee.getLastName(), this.selectedEmployee.getIdentification(), this.selectedEmployee.getEmail(), this.selectedEmployee.getPhone(), this.selectedEmployee.getType(), 6, this.selectedEmployee.getPassword(), this.selectedEmployee.getEmploymentDate(), this.selectedEmployee.getLayoffDate(), this.selectedEmployee.getIdSupervisor());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedEmployee = new EmployeeTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }
        if (this.selectedEmployee.getType() == 1 && this.selectedEmployee.getIdSupervisor() != 0) {

            //ERROR      
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Admins dont have supervisor"));
            flag = false;

        }
        if (this.selectedEmployee.getType() != 1 && this.selectedEmployee.getIdSupervisor() == 0) {

            //ERROR      
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Supervisor empty"));
            flag = false;

        }

        if (flag) {

            this.service.update(selectedEmployee, selectedEmployee.getFirstName(), this.selectedEmployee.getLastName(), this.selectedEmployee.getIdentification(), this.selectedEmployee.getEmail(), this.selectedEmployee.getPhone(), this.selectedEmployee.getType(), this.selectedEmployee.getStatus(), this.selectedEmployee.getPassword(), this.selectedEmployee.getEmploymentDate(), this.selectedEmployee.getLayoffDate(), this.selectedEmployee.getIdSupervisor());
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

            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            this.service.update(selectedEmployee, this.selectedEmployee.getFirstName(), this.selectedEmployee.getLastName(), this.selectedEmployee.getIdentification(), this.selectedEmployee.getEmail(), this.selectedEmployee.getPhone(), this.selectedEmployee.getType(), 6, this.selectedEmployee.getPassword(), this.selectedEmployee.getEmploymentDate(), date, this.selectedEmployee.getIdSupervisor());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedEmployee = new EmployeeTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }

    public void deleteUser(int PK) throws Exception {

        try {
            EmployeeTO searched = this.getEmployee(PK);
            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            if (searched != null) {
                service.update(searched, searched.getFirstName(), searched.getLastName(), searched.getIdentification(), searched.getEmail(), searched.getPhone(), searched.getType(), 6, searched.getPassword(), searched.getEmploymentDate(), date, searched.getIdSupervisor());
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in suspending the user"));

        }

    }

    public java.util.Date getCalendarFireDate() {
        return (java.util.Date) this.selectedEmployee.getLayoffDate();
    }

    public void setCalendarFireDate(java.util.Date fireDate) {
        if (fireDate != null) {
            this.selectedEmployee.setLayoffDate(new java.sql.Date(fireDate.getTime()));
        }
    }

    public java.util.Date getEmployment() {
        return (java.util.Date) this.selectedEmployee.getEmploymentDate();
    }

    public void setEmployment(java.util.Date employment) {
        if (employment != null) {
            this.selectedEmployee.setEmploymentDate(new java.sql.Date(employment.getTime()));
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

    public List<DocumentTO> getDocuments() {
        try {
            return docService.getDocuments();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of documents"));

        }
        List<DocumentTO> list = new ArrayList<>();
        return list;
    }

    public List<DocumentTO> getDocumentsOf(int PK) {
        try {
            return docService.getDocumentsOfEmployee(PK);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of documents of employee"));

        }
        List<DocumentTO> list = new ArrayList<>();
        return list;
    }

    public void insertDoc(int PK, String name, String path) {
        try {
            docService.insert(PK, name, path);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in inserting document in data base"));

        }
    }

    public String getDocumentPath() {
        String path = "C:\\ProyectoFinal\\Proyecto2\\web\\resources\\demo\\";
        return path + this.originalImageFile.getFileName();
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.originalImageFile = null;
            UploadedFile file = event.getFile();
            if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
                this.originalImageFile = file;
                this.copyFileInFileSystem(file.getInputStream(), "C:\\ProyectoFinal\\Proyecto2\\web\\resources\\demo\\", this.originalImageFile.getFileName());
                this.insertDoc(this.getId(), this.originalImageFile.getFileName(), this.getDocumentPath());
                FacesMessage msg = new FacesMessage("Successful", this.originalImageFile.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFileInFileSystem(InputStream input, String pathCopy, String fileName) throws FileNotFoundException, IOException {
        Path path = Paths.get(pathCopy, fileName);
        if (Files.exists(path.getParent())) {
            try {
                System.out.println("PROCESS FILE PATH===> " + path);
                Files.copy(input, path, REPLACE_EXISTING);
                //Insertar en la base de datos..
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //IOUtils.closeQuietly(input);
                //IOUtils.closeQuietly(output);
            }
        } else {
            try {
                System.out.println("PROCESS FILE PATH===> " + path);
                Files.createDirectories(path.getParent());
                try {
                    Files.copy(input, path, REPLACE_EXISTING);
                    //Insertar en la base de datos..
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //IOUtils.closeQuietly(input);
                    //IOUtils.closeQuietly(output);
                }
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }

    public void deleteDocument(int PK) {
        try {
            docService.delete(PK);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in deleating the document"));

        }
    }

    public StreamedContent getFile() {
        return fileSC;
    }

    public void setFile(StreamedContent fileSC) {
        this.fileSC = fileSC;
    }

    public DocumentTO searchDocument(int PK) {
        DocumentTO result = null;
        try {
            result = docService.searchByPK(PK);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in searching the document"));

        }
        return result;
    }

    public String getDocumentPath(int PK) {
        String path = "C:\\ProyectoFinal\\Proyecto2\\web\\resources\\demo\\";
        try {
            DocumentTO doc = docService.searchByPK(PK);
            path += doc.getName();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in searching the document"));

        }
        return path;
    }

    public StreamedContent prepareFileSC(int PK) {

        DocumentTO searched = this.searchDocument(PK);
        String docName = searched.getName();
        String pathFile = "/resources/demo/" + searched.getName();
        if (docName.contains(".pdf")) {
            this.fileSC = DefaultStreamedContent.builder()
                    .name(docName)
                    .contentType("application/pdf")
                    .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(pathFile))
                    .build();
        } else if (docName.contains(".docx")) {
            this.fileSC = DefaultStreamedContent.builder()
                    .name(docName)
                    .contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(pathFile))
                    .build();
        } else if (docName.contains(".jpeg") || docName.contains(".jpg")) {
            this.fileSC = DefaultStreamedContent.builder()
                    .name(docName)
                    .contentType("image/jpeg")
                    .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(pathFile))
                    .build();
        } else if (docName.contains(".png")) {
            this.fileSC = DefaultStreamedContent.builder()
                    .name(docName)
                    .contentType("image/png")
                    .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(pathFile))
                    .build();
        } else {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "File type errort"));
        }
        return this.fileSC;
    }

    public String idToStrStatus(int id) throws Exception {

        EmployeeService serv = new EmployeeService();
        EmployeeTO test = serv.searchByPK(id);
        this.esNuevo = false;
        this.selectedEmployee = new EmployeeTO();
        String result = (test.getFirstName() + " " + test.getLastName());
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");

        return result;
    }

    public void logOut() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + "/faces/index.xhtml?faces-redirect=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String idToStringEmployeeName(int employeeID) throws Exception {

        EmployeeService serv = new EmployeeService();
        EmployeeTO test = serv.searchByPK(employeeID);
        this.esNuevo = false;
        this.selectedEmployee = new EmployeeTO();
        String result = (test.getFirstName() + " " + test.getLastName());
        return result;
    }

    public String getPhone() {
        return em.getPhone();
    }

    public int getType() {
        return em.getType();
    }

    public Date getEmploymentDay() {
        return em.getEmploymentDate();
    }

    public int getIdSupervisor() {
        return em.getIdSupervisor();
    }

}
