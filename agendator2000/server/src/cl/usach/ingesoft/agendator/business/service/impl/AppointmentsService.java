package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.dao.impl.AppointmentDao;
import cl.usach.ingesoft.agendator.business.service.IAppointmentsService;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentsService implements IAppointmentsService {
    @Autowired private AppointmentDao appointmentDao;

    @Transactional
    @Override
    public AppointmentEntity bookAppointment(AppointmentEntity appointment) {
        appointmentDao.save(appointment);
        appointmentDao.flush();
        return appointment;
    }

    @Transactional
    @Override
    public boolean cancelAppointment(AppointmentEntity appointment) {
        appointmentDao.delete(appointment);
        appointmentDao.flush();
        return (appointmentDao.findById(appointment.getId()) == null);
    }

    @Transactional
    @Override
    public AppointmentEntity updateAppointment(AppointmentEntity appointment) {
        appointmentDao.update(appointment);
        appointmentDao.flush();
        return appointment;
    }
}