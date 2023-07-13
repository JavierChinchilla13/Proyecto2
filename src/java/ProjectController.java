
import com.mycompany.edu.ulatina.hth_db_connetion.PermitTO;
import com.mycompany.edu.ulatina.hth_db_connetion.ProjectService;
import com.mycompany.edu.ulatina.hth_db_connetion.ProjectTO;
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
import org.primefaces.PrimeFaces;


@ManagedBean(name = "projectController")
@SessionScoped
public class ProjectController implements Serializable{
    
    private ProjectTO selectedProject = new ProjectTO();
    private final ProjectService proService = new ProjectService();
    private boolean esNuevo;

    public ProjectTO getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(ProjectTO selectedProject) {
        this.selectedProject = selectedProject;
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
    
    public List<ProjectTO> getProjects(){
        try{
            return proService.getProjects();
        }catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<ProjectTO> list = new ArrayList<>();
        return list;
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
}
