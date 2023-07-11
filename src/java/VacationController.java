
import com.mycompany.edu.ulatina.hth_db_connetion.ScheduleVacationService;
import com.mycompany.edu.ulatina.hth_db_connetion.ScheduleVacationTO;
import com.mycompany.edu.ulatina.hth_db_connetion.VacationService;
import com.mycompany.edu.ulatina.hth_db_connetion.VacationTO;
import java.io.Serializable;
import java.sql.Date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
 * @author Ale
 */
@ManagedBean(name = "vacationController")
@SessionScoped
public class VacationController implements Serializable {

    private ScheduleVacationTO selectedSchedueleVacation = new ScheduleVacationTO();
    private VacationTO selectedVacation = new VacationTO();
    private final ScheduleVacationService sVService = new ScheduleVacationService();
    private final VacationService vService = new VacationService();
    private EmployeeController e = new EmployeeController();
    private boolean esNuevo;
    private Date date;
    private Date startDate;
    private Date endDate;

    public VacationController() {
    }

    public VacationController(boolean esNuevo, Date date, Date startDate, Date endDate) {
        this.esNuevo = esNuevo;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void openNew() {
        this.esNuevo = true;
        this.selectedSchedueleVacation = new ScheduleVacationTO();
    }

    public EmployeeController getE() {
        return e;
    }

    public void setE(EmployeeController e) {
        this.e = e;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ScheduleVacationTO getSelectedSchedueleVacation() {
        return selectedSchedueleVacation;
    }

    public void setSelectedSchedueleVacation(ScheduleVacationTO selectedSchedueleVacation) {
        this.selectedSchedueleVacation = selectedSchedueleVacation;
    }

    public VacationTO getSelectedVacation() {
        return selectedVacation;
    }

    public void setSelectedVacation(VacationTO selectedVacation) {
        this.selectedVacation = selectedVacation;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ScheduleVacationService getsVService() {
        return sVService;
    }

    public VacationService getvService() {
        return vService;
    }

    public List<VacationTO> getVacation() {
        try {
            return vService.getVacations();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        List<VacationTO> list = new ArrayList<>();
        return list;
    }

    public void saveSchedueleVacation() throws Exception {
        System.out.println("Hola");
        boolean flag = true;

        if (this.selectedSchedueleVacation.getStartDate() == null) {
            // ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Start Date is empty"));
            flag = false;
        }

        if (this.selectedSchedueleVacation.getEndDate() == null) {
            // ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "End Date is empty"));
            flag = false;
        }
        
        

        if (flag) {
            System.out.println("Saving ScheduleVacation");
            if (this.e.getSelectedEmployee().getId() == sVService.getVacationIdByEmployeeId(e.getSelectedEmployee().getId()) && this.selectedVacation.getVacationDays() == 0) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "La cantidad de días no puede ser igual a 0."));
            } else {
                Instant instantStartDate = selectedSchedueleVacation.getStartDate().toInstant();
                LocalDate localStartDate = LocalDateTime.ofInstant(instantStartDate, ZoneId.systemDefault()).toLocalDate();

                Instant instantEndDate = selectedSchedueleVacation.getEndDate().toInstant();
                LocalDate localEndDate = LocalDateTime.ofInstant(instantEndDate, ZoneId.systemDefault()).toLocalDate();

                Date sqlStartDate = Date.valueOf(localStartDate);
                Date sqlEndDate = Date.valueOf(localEndDate);
                String desc = "hola";

                sVService.insert(sVService.getVacationIdByEmployeeId(e.getSelectedEmployee().getId()), sqlStartDate, sqlEndDate, 17, desc);
                this.esNuevo = false;
                this.selectedSchedueleVacation = new ScheduleVacationTO();
                PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
            }
        }
    }

    public List<ScheduleVacationTO> getAllScheduleVacation() {
        try {
            return sVService.getScheduleVacation();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of documents of employee"));
        }
        List<ScheduleVacationTO> list = new ArrayList<>();
        return list;
    }

    public List<ScheduleVacationTO> getScheduleVacationOf(int PK) {
        try {
            return sVService.getScheduleVacationOf(PK);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of documents of employee"));

        }
        List<ScheduleVacationTO> list = new ArrayList<>();
        return list;
    }
    /*
    public java.util.Date getCalendarFireDate(){
         return (java.util.Date) this.selectedVacation.getDate();
     }
     
     public void setCalendarFireDate(java.util.Date fireDate){
         if(fireDate !=null){
             this.selectedPermit.setDate(new java.sql.Date(fireDate.getTime()));
         }
     }*/
}
