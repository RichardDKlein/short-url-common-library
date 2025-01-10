/**
 * The Short URL Common Library
 * (Copyright 2024 by Richard Klein)
 */

package com.richarddklein.shorturlcommonlibrary.service.shorturlmappingservice.dto;

import java.util.List;

import com.richarddklein.shorturlcommonlibrary.service.shorturlmappingservice.entity.ShortUrlMapping;
import com.richarddklein.shorturlcommonlibrary.service.status.Status;

@SuppressWarnings("unused")
public class StatusAndShortUrlMappingArray {
    private Status status;
    private List<ShortUrlMapping> shortUrlMappings;

    public StatusAndShortUrlMappingArray() {
    }

    public StatusAndShortUrlMappingArray
            (Status status,
             List<ShortUrlMapping> shortUrlMappings) {

        this.status = status;
        this.shortUrlMappings = shortUrlMappings;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ShortUrlMapping> getShortUrlMappings() {
        return shortUrlMappings;
    }

    public void setShortUrlMappings(List<ShortUrlMapping> shortUrlMappings) {
        this.shortUrlMappings = shortUrlMappings;
    }
}
