
import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeService;
import com.mycompany.edu.ulatina.hth_db_connetion.EmployeeTO;
import com.mycompany.edu.ulatina.hth_db_connetion.PermitTO;
import com.mycompany.edu.ulatina.hth_db_connetion.ProjectService;
import com.mycompany.edu.ulatina.hth_db_connetion.ProjectTO;
import com.mycompany.edu.ulatina.hth_db_connetion.ProjectXEmployeeService;
import com.mycompany.edu.ulatina.hth_db_connetion.ProjectXEmployeeTO;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "projectController")
@SessionScoped
public class ProjectController implements Serializable {

    private ProjectTO selectedProject = new ProjectTO();
    private ProjectXEmployeeTO selectedProjectXEmployee = new ProjectXEmployeeTO();
    private final ProjectService proService = new ProjectService();
    private final ProjectXEmployeeService pXEService = new ProjectXEmployeeService();
    private boolean esNuevo;
    private final EmployeeService empService = new EmployeeService();

    public ProjectXEmployeeTO getSelectedProjectXEmployee() {
        return selectedProjectXEmployee;
    }

    public void setSelectedProjectXEmployee(ProjectXEmployeeTO selectedProjectXEmployee) {
        this.selectedProjectXEmployee = selectedProjectXEmployee;
    }

    public ProjectTO getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(ProjectTO selectedProject) {
        this.selectedProject = selectedProject;
    }

    public boolean isProyectActive() {
        return selectedProject.getStatus() == 10;
    }

    public List<EmployeeTO> getEmployeesOfProyect() {
        try {
            return empService.getEmployeesFromProyect(selectedProject.getId());
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of employees of the project"));

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }

    public List<EmployeeTO> getEmployeesNotOnProyect() {
        try {
            return empService.getEmployeesNotOnProyect(selectedProject.getId());
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of employees of the project"));

        }
        List<EmployeeTO> list = new ArrayList<>();
        return list;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public void openNew() {
        this.esNuevo = true;
        this.selectedProject = new ProjectTO();
    }

    public void openNewPXE() {
        this.esNuevo = true;
        this.selectedProjectXEmployee = new ProjectXEmployeeTO();
    }

    public List<ProjectTO> getProjects() {
        try {
            return proService.getProjects();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<ProjectTO> list = new ArrayList<>();
        return list;
    }

    public List<ProjectXEmployeeTO> getPXEInfoFromEmployee(int pk) {
        try {
            return pXEService.getProjectEmployeeById(pk);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the project information"));

        }
        List<ProjectXEmployeeTO> list = new ArrayList<>();
        return list;
    }

    public String statusToStrStatus(int status) {
        String result = "";
        switch (status) {
            case 10:
                result = "Inprogress";
                break;
            case 11:
                result = "Completed";
                break;

        }
        return result;
    }

    public void saveProject() throws Exception {

        boolean flag = true;

        if (this.selectedProject.getName() == null || this.selectedProject.getName().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Name is empty"));
            flag = false;
        }
        if (this.selectedProject.getStatus() != 10 && this.selectedProject.getStatus() != 11) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "status is incorrect"));
            flag = false;
        }
        if (this.selectedProject.getStartingDate() == null) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Starting Date is empty"));
            flag = false;
        }

        if (flag) {
            System.out.println("Saving permit");
            //Date day = (java.sql.Date) (java.sql.Date) selectedPermit.getDate();
            ProjectTO i = new ProjectTO(0, this.selectedProject.getName(), this.selectedProject.getStatus(), this.selectedProject.getStartingDate(), this.selectedProject.getEndingDate());
            this.proService.insert(i);
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedProject = new ProjectTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }

    public void updateProject() throws Exception {

        boolean flag = true;

        if (flag) {

            this.proService.update(selectedProject, this.selectedProject.getName(), this.selectedProject.getStatus(), this.selectedProject.getStartingDate(), this.selectedProject.getEndingDate());
            //---this.servicioUsuario.listarUsuarios();
            //this.listaUsuarios.add(selectedEmployee);//para simular       
            this.esNuevo = false;
            this.selectedProject = new ProjectTO();
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        }

    }

    public java.util.Date getStartingDate() {
        return (java.util.Date) this.selectedProject.getStartingDate();
    }

    public void setStartingDate(java.util.Date startingDate) {
        if (startingDate != null) {
            this.selectedProject.setStartingDate(new java.sql.Date(startingDate.getTime()));
        }
    }

    public java.util.Date getEndingDate() {
        return (java.util.Date) this.selectedProject.getEndingDate();
    }

    public void setEndingDate(java.util.Date endingdate) {
        if (endingdate != null) {
            this.selectedProject.setEndingDate(new java.sql.Date(endingdate.getTime()));
        }
    }

    public ProjectTO getProject(int PK) {
        ProjectTO foundPro = null;
        try {

            foundPro = proService.searchByPK(PK);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in searching the user"));
        }
        return foundPro;
    }

    public void deleteProject(int PK) throws Exception {

        try {
            ProjectTO searched = this.getProject(PK);
            if (searched != null) {
                proService.delete(searched);
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in suspending the user"));

        }

    }

    public void reviewProjectFeedBack() throws Exception {

        this.redirect("/faces/reviewProjectFeedBack.xhtml");

    }

    public void redirect(String rute) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + rute);
        } catch (Exception e) {

        }

    }

    public ProjectXEmployeeTO getProyectFeedBack(int PK) {
        ProjectXEmployeeTO foundFeedBack = null;
        try {

            foundFeedBack = pXEService.searchPXEByPk(PK);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in searching the user"));
        }
        return foundFeedBack;
    }

    public void deleteProjectFeedBack(int PK) throws Exception {

        try {
            ProjectXEmployeeTO searchedFeedBack = this.getProyectFeedBack(PK);
            if (searchedFeedBack != null) {
                pXEService.delete(searchedFeedBack);
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in deleting the Schedule Vacation"));

        }

    }

    public void returnToProjectPage() throws Exception {

        this.redirect("/faces/projects.xhtml");

    }

    public void saveFeedBack() throws Exception {

        boolean flag = true;

        if (this.selectedProjectXEmployee.getFeedBack() == null || this.selectedProjectXEmployee.getFeedBack().equals("")) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "FeedBack is Empty"));
            flag = false;
        }

        if (flag) {

            this.pXEService.insert(this.selectedProject.getId(), this.selectedProjectXEmployee.getIdEmployee(), this.selectedProjectXEmployee.getHoursInvested(), this.selectedProjectXEmployee.getFeedBack());
            this.esNuevo = false;
            this.selectedProjectXEmployee = new ProjectXEmployeeTO();
            this.selectedProject = new ProjectTO();
            PrimeFaces.current().executeScript("PF('addFeedback').hide()");

        }

    }

    public void addCollaboratorToProject(int employeePK) {
        try {
            System.out.println("\n\n\n\n\n\n\n\n\n\n             -----------------------> NOM:  " + selectedProject.getName() + "  ---  ID: " + selectedProject.getId() + "     EMP: " + employeePK);
            proService.addCollaborator(selectedProject.getId(), employeePK);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in addingPersonToProject"));
        }
    }

    public String getHeaderForProject() {
        return "Members of Project: " + selectedProject.getName();
    }
    
    public void test(){
        System.out.println("\n\n\n\n\n\n Name: " + selectedProject.getName() + "\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n ID: " + selectedProject.getId()+ "\n\n\n\n\n\n");
    }

}
