package com.epam.epmcacm.msademo.resourcesrv.service;

import com.epam.epmcacm.msademo.resourcesrv.dto.ResourceDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RMQPublisherService {

    private final String publisherBinding = "output-1";

    @Autowired
    private StreamBridge streamBridge;


    public void publishChangingEvent(ResourceDto message) {
        streamBridge.send(publisherBinding, message);
    }
}
