package com.bridee.api.pattern.observer;

import com.bridee.api.entity.Convidado;
import com.bridee.api.entity.Convite;
import com.bridee.api.pattern.observer.dto.ConviteTopicDto;

import java.util.List;
import java.util.Map;

public interface ConviteSubject {

    void register(ConviteObserver observer);
    void unregister(ConviteObserver observer);
    void notifyObservers();
    List<ConviteTopicDto> getUpdate(ConviteObserver observer);

}
