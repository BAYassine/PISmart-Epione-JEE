package interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import entities.Appointment;

@Local
public interface AppointmentServiceLocal {
	public int addAppointment(Appointment app,int idPatient) throws ParseException;
	public boolean cancelAppointment(int appId,int idP);
	public int updateAppointment(Appointment app);
	public void deleteAppointment(int idA);
	public Appointment getAppointmentById(int appointmentId);
	public List<Appointment> getAppointmentByDate(String dateapp) throws ParseException;
	public List<Appointment> getAppointmentsByPatient(int idPatient);
	public List<Appointment> getAppointmentsByDoctor(int idDoctor);
	public List<Appointment> getAllAppointments();
	public void affectConsultation(int idAppointment,int idConsultaion);

}
