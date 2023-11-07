package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.ParticipantDTO;

public interface EmailService {

    void sendEmail(ParticipantDTO participantDTO, String accountLink);
}
