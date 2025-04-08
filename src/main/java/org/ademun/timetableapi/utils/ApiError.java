package org.ademun.timetableapi.utils;

public record ApiError(int code, String message, String details) {
}
