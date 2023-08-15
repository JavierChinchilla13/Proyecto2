
import com.mycompany.edu.ulatina.hth_db_connetion.ScheduleVacationService;
import com.mycompany.edu.ulatina.hth_db_connetion.ScheduleVacationTO;
import com.mycompany.edu.ulatina.hth_db_connetion.VacationService;
import com.mycompany.edu.ulatina.hth_db_connetion.VacationTO;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private int vacationDays;
    private int dayDifference;
    private int dayOff;

    public VacationController() {
    }

    public VacationController(boolean esNuevo, Date date, Date startDate, Date endDate) {
        this.esNuevo = esNuevo;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public VacationController(boolean esNuevo, Date date, Date startDate, Date endDate, int vacationDays, int dayDifference) {
        this.esNuevo = esNuevo;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.vacationDays = vacationDays;
        this.dayDifference = dayDifference;
    }

    public void openNew() {
        this.esNuevo = true;
        this.selectedSchedueleVacation = new ScheduleVacationTO();
    }

    public int getDayDifference() {
        return dayDifference;
    }

    public void setDayDifference(int dayDifference) {
        this.dayDifference = dayDifference;
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

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }
    
    public String getEmployeeName(int pk) {
        try {
            
            return sVService.getEmployeeName(pk);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving th list of employees"));

        }
        
        return "";
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

    public void dayDifference() {
        LocalDate startDate = this.selectedSchedueleVacation.getStartDate().toLocalDate();
        LocalDate endDate = this.selectedSchedueleVacation.getEndDate().toLocalDate();
        long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
        int days = (int) daysDifference;
        this.vacationDays = this.vacationDays - days;
    }

    public boolean dateBefore() {
        boolean flag = true;

        if (flag) {
            LocalDate startDate = this.selectedSchedueleVacation.getStartDate().toLocalDate();
            LocalDate endDate = this.selectedSchedueleVacation.getEndDate().toLocalDate();
            LocalDate currentDate = LocalDate.now();

            if (startDate.isBefore(currentDate) || endDate.isBefore(currentDate)) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Selected date is before the current date"));
                flag = false;
            }
            if (endDate.isBefore(startDate)) {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "End date can't be before start date"));
                flag = false;
            }
        }
        return flag;
    }

    public boolean verifyAmountOfVacations() {
        LocalDate startDate = this.selectedSchedueleVacation.getStartDate().toLocalDate();
        LocalDate endDate = this.selectedSchedueleVacation.getEndDate().toLocalDate();
        long daysDifference = ChronoUnit.DAYS.between(startDate, endDate);
        int days = (int) daysDifference;

        if (daysDifference > this.vacationDays) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "The amount of days selected is more than your vacation days left"));
            return false;
        } else {
            return true;
        }
    }

    public void saveSchedueleVacation(int pk) throws Exception {

        boolean flag2 = true;
        boolean flag = true;
        boolean flag3 = true;

        if (this.selectedSchedueleVacation.getStartDate() == null) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Date is empty"));
            flag = false;
        }
        if (this.selectedSchedueleVacation.getEndDate() == null) {
            //ERROR
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Date is empty"));
            flag = false;
        }

        if (flag) {
            flag2 = dateBefore();
            if (flag2) {
                flag3 = verifyAmountOfVacations();
                if (flag3) {
                    if (this.vacationDays <= 0) {
                        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "You don't have vacation days left"));
                    } else {
                        System.out.println("Saving Schedule Vacation");
                        this.sVService.insert(sVService.getVacationIdByEmployeeId(pk), this.selectedSchedueleVacation.getStartDate(), this.selectedSchedueleVacation.getEndDate(), 17, "");
                        dayDifference();
                        //vService.updateVacationDays(pk, this.vacationDays);
                        this.esNuevo = false;
                        this.selectedSchedueleVacation = new ScheduleVacationTO();
                        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
                    }
                }
            }
        }

    }

    public List<ScheduleVacationTO> getAllScheduleVacation() {
        try {
            return sVService.getScheduleVacation();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving all the list of vacations"));
        }
        List<ScheduleVacationTO> list = new ArrayList<>();
        return list;
    }

    public List<ScheduleVacationTO> getScheduleVacationOf(int PK) {
        try {
            return sVService.getScheduleVacationOf(PK);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of vacations of employee"));

        }
        List<ScheduleVacationTO> list = new ArrayList<>();
        return list;
    }

    public int getVacationDaysOfEmployee(int pk) {
        try {
            this.vacationDays = vService.getVacationDaysOf(pk);
            return this.vacationDays;
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of vacations of employee"));
        }
        return this.vacationDays = 0;
    }

    public int getVacationDaysOff(int pk) {
        int day = 0;
        try {

            day = sVService.getVacationDaysOff(pk);
            return day;
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of vacations of employee"));
        }
        return day;
    }

    public List<ScheduleVacationTO> getPendingVacationRequest() {
        try {
            return sVService.getScheduleVacationsPending();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of vacations with pending status"));

        }
        List<ScheduleVacationTO> list = new ArrayList<>();
        return list;
    }
    
    public List<ScheduleVacationTO> getPendingVacationRequestSupervisor(int supervisor) {
        try {
            return sVService.getScheduleVacationsSupervisor(supervisor);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of vacations with pending status"));

        }
        List<ScheduleVacationTO> list = new ArrayList<>();
        return list;
    }

    public java.util.Date getCalendarFireDate() {
        return (java.util.Date) this.selectedSchedueleVacation.getStartDate();
    }

    public void setCalendarFireDate(java.util.Date fireDate) {
        if (fireDate != null) {
            this.selectedSchedueleVacation.setStartDate(new java.sql.Date(fireDate.getTime()));
        }
    }

    public java.util.Date getCalendarFireDate2() {
        return (java.util.Date) this.selectedSchedueleVacation.getEndDate();
    }

    public void setCalendarFireDate2(java.util.Date fireDate) {
        if (fireDate != null) {
            this.selectedSchedueleVacation.setEndDate(new java.sql.Date(fireDate.getTime()));
        }
    }

    public void reviewVacationRequest() throws Exception {

        this.redirect("/faces/reviewVacationRequest.xhtml");

    }

    public void redirect(String rute) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + rute);
        } catch (Exception e) {

        }

    }

    public void approveVacation() throws Exception {

        this.sVService.update(selectedSchedueleVacation, this.selectedSchedueleVacation.getIdVacation(), this.selectedSchedueleVacation.getStartDate(), this.selectedSchedueleVacation.getEndDate(), 15, this.selectedSchedueleVacation.getDescription());
        this.esNuevo = false;
        this.selectedSchedueleVacation = new ScheduleVacationTO();
        PrimeFaces.current().executeScript("PF('approveDenyVacationDialog').hide()");

    }

    public void denyVacation() throws Exception {

        this.sVService.update(selectedSchedueleVacation, this.selectedSchedueleVacation.getIdVacation(), this.selectedSchedueleVacation.getStartDate(), this.selectedSchedueleVacation.getEndDate(), 16, this.selectedSchedueleVacation.getDescription());
        this.esNuevo = false;
        this.selectedSchedueleVacation = new ScheduleVacationTO();
        PrimeFaces.current().executeScript("PF('approveDenyVacationDialog').hide()");

    }

    public void returnToVacationPage() throws Exception {

        this.redirect("/faces/vacations.xhtml");

    }

    public ScheduleVacationTO getScheduleVacation(int PK) {
        ScheduleVacationTO foundVacation = null;
        try {
            boolean flag = true;
            
            if (sVService.searchScheduleVacationStatusPK(PK) == 15 || sVService.searchScheduleVacationStatusPK(PK) == 16) {
                //ERROR

                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "This vacation has already been responded"));
                flag = false;

            }
            if (flag) {
                foundVacation = sVService.searchByPK(PK);
            }
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in searching the user"));
        }
        return foundVacation;
    }

    public void deleteScheduleVacation(int PK) throws Exception {

        try {
            ScheduleVacationTO searchedVacation = this.getScheduleVacation(PK);
            if (searchedVacation != null) {
                sVService.delete(searchedVacation);
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in deleting the Schedule Vacation"));

        }

    }

    public List<ScheduleVacationTO> getNewScheduleVacations(int id) {
        try {
            return sVService.getNewScheduleVacation(id);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of New Schedule Vacations"));

        }
        List<ScheduleVacationTO> list = new ArrayList<>();
        return list;
    }

    public String statusToStrStatus(int status) {
        String result = "";
        switch (status) {
            case 15:
                result = "Approved";
                break;
            case 16:
                result = "Denied";
                break;
            case 17:
                result = "Pending";
                break;
        }
        return result;
    }

    public List<ScheduleVacationTO> getOldScheduleVacations(int id) {
        try {
            return sVService.getOldScheduleVacation(id);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error in retriving the list of Old Schedule Vacations"));

        }
        List<ScheduleVacationTO> list = new ArrayList<>();
        return list;
    }

}
