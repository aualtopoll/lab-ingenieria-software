package cl.usach.ingesoft.agendator.business.service.impl;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
import cl.usach.ingesoft.agendator.entity.ScheduleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessionalsService implements IProfessionalsService {

    @Transactional
    @Override
    public List<ProfessionalEntity> findProfessionalsByCareSession(CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Transactional
    @Override
    public ProfessionalCalendarBO getProfessionalCalendar(ProfessionalEntity professional,
            CareSessionEntity careSession) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Transactional
    @Override
    public ProfessionalCalendarBO setScheduleForProfessional(CareSessionEntity careSession,
            ProfessionalEntity professional, List<ScheduleEntity> schedules) {
        throw new RuntimeException("Not yet implemented.");
    }
}
