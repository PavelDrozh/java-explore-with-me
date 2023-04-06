package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;
import ru.practicum.explorewithme.model.EventRequest;
import ru.practicum.explorewithme.model.RequestStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventRequestMapper {
    public ParticipationRequestDto toParticipationRequestDto(EventRequest eventRequest) {
        return ParticipationRequestDto.builder()
                .event(eventRequest.getEvent().getId())
                .requester(eventRequest.getRequester().getId())
                .created(eventRequest.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .id(eventRequest.getId())
                .status(eventRequest.getStatus())
                .build();
    }

    public EventRequest getEventRequest() {
        EventRequest request = new EventRequest();
        request.setCreated(LocalDateTime.now());
        return request;
    }

    public EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(List<EventRequest> requests) {
        List<ParticipationRequestDto> confirmed = requests.stream()
                .filter(req -> req.getStatus().equals(RequestStatus.CONFIRMED))
                .map(this::toParticipationRequestDto)
                .collect(Collectors.toList());
        List<ParticipationRequestDto> rejected = requests.stream()
                .filter(req -> req.getStatus().equals(RequestStatus.REJECTED))
                .map(this::toParticipationRequestDto)
                .collect(Collectors.toList());
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmed)
                .rejectedRequests(rejected)
                .build();
    }
}
