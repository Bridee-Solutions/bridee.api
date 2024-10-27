package com.bridee.api.pattern.observer.impl;

import com.bridee.api.pattern.observer.ConviteObserver;
import com.bridee.api.pattern.observer.ConviteSubject;
import com.bridee.api.pattern.observer.dto.ConviteTopicDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ConviteTopic implements ConviteSubject {

    @Autowired
    private List<ConviteObserver> observers;
    private List<ConviteTopicDto> topicDtos;
    private boolean changed;
    private final Object MUTEX = new Object();

    @PostConstruct
    public void init(){
        observers.forEach(observer -> observer.setSubject(this));
    }

    @Override
    public void register(ConviteObserver observer) {
        if (Objects.isNull(observer)){
            throw new IllegalArgumentException("Observer informado possui valor nulo!");
        }
        synchronized (MUTEX){
            observers.add(observer);
        }
    }

    @Override
    public void unregister(ConviteObserver observer) {
        if (Objects.isNull(observer)){
            throw new IllegalArgumentException("Observer informado possui valor nulo!");
        }
        synchronized (MUTEX){
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        List<ConviteObserver> conviteObservers = null;
        synchronized (MUTEX){
            if (!changed) return;
            conviteObservers = new ArrayList<>(observers);
            changed = false;
        }
        conviteObservers.forEach(ConviteObserver::update);
    }

    @Override
    public List<ConviteTopicDto> getUpdate(ConviteObserver observer) {
        return this.topicDtos;
    }

    public void postMessage(List<ConviteTopicDto> conviteTopicDtos){
        topicDtos = conviteTopicDtos;
        changed = true;
        notifyObservers();
    }
}
