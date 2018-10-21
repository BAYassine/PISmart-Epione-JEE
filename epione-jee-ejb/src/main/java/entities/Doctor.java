package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Doctor extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String speciality;
	
	@OneToMany(mappedBy="doctor")
	private List<Availability> availabilities;
	
	@OneToMany(mappedBy="doctor")
	private List<Appointment> appointments;
	
	@OneToMany(mappedBy="doctor")
	private List<Path> paths;
	
	@OneToMany(mappedBy="doctor")
	private List<Message> messages;
	
	public Doctor() {
		super();
	}
	public Doctor(int id, String speciality) {
		super();
		this.speciality = speciality;
	}
	
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public List<Availability> getAvailabilities() {
		return availabilities;
	}
	public void setAvailability(List<Availability> availabilities) {
		this.availabilities = availabilities;
	}
	public List<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	public List<Path> getPaths() {
		return paths;
	}
	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	

}
