package com.bridee.api.pattern.observer.impl;

import com.bridee.api.client.dto.request.WhatsappRequestDto;
import com.bridee.api.entity.Casal;
import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.mapper.request.WhatsappRequestMapper;
import com.bridee.api.pattern.observer.ConviteObserver;
import com.bridee.api.pattern.observer.ConviteSubject;
import com.bridee.api.pattern.observer.dto.ConviteTopicDto;
import com.bridee.api.service.WhatsappService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConviteSubscriber implements ConviteObserver {

    private ConviteSubject conviteSubject;
    private final WhatsappService whatsappService;
    private final WhatsappRequestMapper whatsappRequestMapper;

    @Override
    public void update() {
        List<ConviteTopicDto> convitesTopics = conviteSubject.getUpdate(this);
        convitesTopics.forEach(topic -> {
            WhatsappRequestDto requestDto = whatsappRequestMapper.toRequestDto(topic);
            whatsappService.sendMessage(requestDto);
        });

    }

    @Override
    public void setSubject(ConviteSubject conviteSubject) {
        this.conviteSubject = conviteSubject;
    }
}
