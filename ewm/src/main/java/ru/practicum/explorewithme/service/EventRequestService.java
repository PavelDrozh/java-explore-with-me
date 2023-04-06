package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;

import java.util.List;

public interface EventRequestService {

    List<ParticipationRequestDto> getRequestsByUserId(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getRequestsInUsersEvent(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest dto);

}
